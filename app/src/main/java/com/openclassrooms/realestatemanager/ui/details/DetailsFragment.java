package com.openclassrooms.realestatemanager.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.ui.editProperty.FormPropertyFragment;
import com.openclassrooms.realestatemanager.ui.simulator.SimulatorFragment;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsFragment extends Fragment {

    public static DetailsFragment newInstance(int bundleProperty) {
        DetailsFragment detailsFragment = new DetailsFragment();

        Bundle args = new Bundle();
        args.putInt("property", bundleProperty);
        detailsFragment.setArguments(args);

        return detailsFragment;
    }

    private DetailViewModel detailViewModel;
    private int bundleProperty;

    @BindView(R.id.fragment_detail_appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.fragment_detail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.fragment_detail_viewpager)
    ViewPager viewPager;

    @BindView(R.id.fragment_detail_map_fab)
    ExtendedFloatingActionButton map_fab;

    @BindView(R.id.fragment_detail_type_tv)
    TextView typeTv;

    @BindView(R.id.fragment_detail_address_tv)
    TextView addressTv;

    @BindView(R.id.fragment_detail_location_iv)
    ImageView addressLabelIV;

    @BindView(R.id.fragment_detail_not_solded_iv)
    ImageView not_soldedIv;

    @BindView(R.id.fragment_detail_solded_iv)
    ImageView soldedIv;

    @BindView(R.id.fragment_detail_availability_tv)
    TextView availabilityTv;

    @BindView(R.id.fragment_detail_surfaceAndPieces_tv)
    TextView surfaceAndPiecesTv;

    @BindView(R.id.fragment_detail_interestPoints_tv)
    TextView interestsPointsTv;

    @BindView(R.id.fragment_detail_interestPoints_title_tv)
    TextView interestsPointsTitle_tv;

    @BindView(R.id.fragment_detail_description_tv)
    TextView descriptionTv;

    @BindView(R.id.fragment_detail_description_title_tv)
    TextView descriptionTitleTv;

    @BindView(R.id.fragment_detail_agent_tv)
    TextView agentTv;

    @BindView(R.id.fragment_detail_agent_iv)
    ImageView agentIcon;

    @BindView(R.id.fragment_detail_collapsingtoolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.fragment_detail_price_tv)
    TextView priceTv;

    @BindView(R.id.fragment_detail_static_map)
    ImageView mapIv;

    @BindView(R.id.fragment_detail_simulator_buton)
    ImageView simulatorIv;

    @BindView(R.id.fragment_detail_dotsIndicator)
    SpringDotsIndicator dotsIndicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundleProperty = getArguments().getInt("property", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        loadProperty();
    }

    private void loadProperty() {
        detailViewModel.loadProperty(bundleProperty);
        observePhotos();
        detailViewModel.property.observe(getViewLifecycleOwner(), property -> {

            displayToolbarTitle(property);
            displayDots(property);
            configureCollapsingToolBar(property);
            displayType(property);
            displayAddress(property);
            displayPrice(property);
            displayAvailability(property);
            displaySurfaceAndPieces(property);
            displayInterestsPoints(property);
            displayDescription(property);
            displayAgent(property);
            displayMap(property);
        });
    }

    private void displayToolbarTitle(Property property) {
        toolbar.setTitle(property.getType());
    }

    private void displayDots(Property property) {

        if (!(property.getPhotos() ==null)&&property.getPhotos().size()>1) {
            dotsIndicator.setViewPager(viewPager);
        }else {dotsIndicator.setVisibility(View.INVISIBLE);}
    }

    private void displayMap(Property property) {
        if (!property.getCity().isEmpty()) {
            String location = property.getAddress()
                    + "+" + property.getCity()
                    + "+" + property.getState();
            String url = "https://maps.googleapis.com/maps/api/staticmap?"
                    + "center=" + location
                    + "&zoom=18&scale=1"
                    + "&size=400x400"
                    + "&maptype=roadmap"
                    + "&key=AIzaSyBIr_Z4aE3uGusLp7sbRW0nNQCrhFehsKs"
                    + "&format=png"
                    + "&visual_refresh=true"
                    + "&markers=size:mid%7Ccolor:0xff8000%7C" + location;

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

            Glide.with(this).load(url).apply(options).into(mapIv);
        }
    }

        private void observePhotos() {

            detailViewModel.photos.observe(getViewLifecycleOwner(), photos -> {
                if(photos!=null){
                    viewPager.setAdapter(new PhotosPageAdapter(getActivity(), photos));}
            });
        }

        private void displayAgent(Property property) {
            if (!property.getAgentName().isEmpty()) {
                agentTv.setText(property.getAgentName());
            } else {
                agentIcon.setVisibility(View.INVISIBLE);
            }
        }

        private void displayDescription(Property property) {
            if (!property.getDescription().isEmpty()) {
                descriptionTv.setText(property.getDescription());
            } else {
                descriptionTitleTv.setVisibility(View.INVISIBLE);
            }
        }

        private void displayInterestsPoints(Property property) {
            String interestsPoints = property.getInterestPoint();
            if (interestsPoints.isEmpty()) {
                interestsPointsTitle_tv.setVisibility(View.INVISIBLE);
            } else {
                interestsPointsTv.setText(interestsPoints);
            }
        }

        private void displaySurfaceAndPieces(Property property) {
            String surfaceAndPieces = "";
            String area = property.getArea();
            String piece = property.getPieces();
            if (!area.isEmpty()) {
                surfaceAndPieces = area + " " + getString(R.string.square_meter);
            }
            if (!area.isEmpty() && !piece.isEmpty()) {
                surfaceAndPieces = surfaceAndPieces + ", ";
            }
            if (!piece.isEmpty()) {
                surfaceAndPieces = surfaceAndPieces + piece + " " + getString(R.string.pieces);
            }
            surfaceAndPiecesTv.setText(surfaceAndPieces);
        }

        private void displayAvailability(Property property) {

            switch (property.getStatus()) {
                case SOLD: {
                    not_soldedIv.setVisibility(View.INVISIBLE);
                    soldedIv.setVisibility(View.VISIBLE);
                    String availability = getString(R.string.solded_since) + " " + property.getSaleDate();
                    availabilityTv.setText(availability);

                }
                break;
                case AVAILABLE:{
                    not_soldedIv.setVisibility(View.VISIBLE);
                    soldedIv.setVisibility(View.INVISIBLE);
                    String availability = getString(R.string.available_since) + " " + property.getEntryDate();
                    availabilityTv.setText(availability);
                    }
            }
        }

        private void displayPrice(Property property) {
            if (!property.getPrice().isEmpty()) {
                String price = "$ " + property.getPrice();
                priceTv.setText(price);
            } else {
                simulatorIv.setVisibility(View.INVISIBLE);
            }
        }

        private void displayAddress(Property property) {

            if (property.getCity().isEmpty()) {
                addressLabelIV.setVisibility(View.INVISIBLE);
            } else {

                String address = property.getAddress() + ", " + property.getCity() + ", " + property.getState();
                addressTv.setText(address);
            }
        }

        private void displayType(Property property) {
            typeTv.setText(property.getType());
        }

        @OnClick(R.id.fragment_detail_fab)
        public void editPropertyclicked() {

            Fragment formPropertyFragment = FormPropertyFragment.newInstance(bundleProperty);
            Utils.addFragmentInDetailScreen(getActivity(), formPropertyFragment);
        }

    @OnClick(R.id.fragment_detail_map_fab)
    public void mapClicked() {
        if (detailViewModel.property != null) {
            Property property = detailViewModel.property.getValue();
            String url = "http://www.google.fr/maps/place/"
                    + property.getAddress()
                    + "+" + property.getCity()
                    + "+" + property.getState();

            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
            startActivity(intent);
        }
    }

    @OnClick(R.id.fragment_detail_simulator_buton)
    public void simulatorClicked() {
        Fragment simulatorFragment = SimulatorFragment.newInstance(detailViewModel.property.getValue().getPrice());
        Utils.addFragmentInDetailScreen(getActivity(), simulatorFragment);
    }
    private void configureCollapsingToolBar(Property property) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        toolbar.setCollapseIcon(getResources().getDrawable(R.drawable.ic_arrow_back_24px));
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if ((Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) || (property.getCity().isEmpty())){
                map_fab.setVisibility(View.INVISIBLE);
            } else {
                map_fab.setVisibility(View.VISIBLE);
            }
        });
        }
}