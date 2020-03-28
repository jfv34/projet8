package com.openclassrooms.realestatemanager.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.ui.insertProperty.InsertPropertyFragment;

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

    private DetailsFragmentViewModel viewModel;
    private int bundleProperty;
    private Property property;

    @BindView(R.id.fragment_detail_viewpager)
    ViewPager viewPager;

    @BindView(R.id.fragment_detail_type_tv)
    TextView typeTv;

    @BindView(R.id.fragment_detail_address_tv)
    TextView addressTv;

    @BindView(R.id.fragment_detail_price_tv)
    TextView priceTv;

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

    @BindView(R.id.fragment_detail_description_tv)
    TextView descriptionTv;

    @BindView(R.id.fragment_detail_agent_tv)
    TextView agentTv;


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

        viewModel = new ViewModelProvider(this).get(DetailsFragmentViewModel.class);
        viewModel.properties.observe(getViewLifecycleOwner(), properties -> {
            if (properties != null) {
                property = properties.get(bundleProperty);
                loadProperty(property);
            }
        });
    }

    private void loadProperty(Property property) {
        if(property.getPhotos()!=null){displayPhotos(property);}
        displayType(property);
        displayAddress(property);
        displayPrice(property);
        displayAvailability(property);
        displaySurfaceAndPieces(property);
        displayInterestsPoints(property);
        displayDescription(property);
        displayAgent(property);
    }

    private void displayPhotos(Property property) {

        viewPager.setAdapter(new PhotosPageAdapter(getActivity(),property.getPhotos()));
    }

    private void displayAgent(Property property) {
        agentTv.setText(property.getAgentName());
    }

    private void displayDescription(Property property) {
        descriptionTv.setText(property.getDescription());
    }

    private void displayInterestsPoints(Property property) {
        String interestsPoints = getString(R.string.interests_points) + " " + property.getInterestPoint();
        interestsPointsTv.setText(interestsPoints);
    }

    private void displaySurfaceAndPieces(Property property) {
        String surfaceAndPieces = property.getArea() + " " + getString(R.string.square_meter)
                + " " + property.getPieces() + " " + getString(R.string.pieces);
        surfaceAndPiecesTv.setText(surfaceAndPieces);
    }

    private void displayAvailability(Property property) {
        if (property.isSolded()) {
            not_soldedIv.setVisibility(View.INVISIBLE);
            soldedIv.setVisibility(View.VISIBLE);
            String availability = getString(R.string.solded_since) + " " + property.getSaleDate();
            availabilityTv.setText(availability);

        } else {
            not_soldedIv.setVisibility(View.VISIBLE);
            soldedIv.setVisibility(View.INVISIBLE);
            String availability = getString(R.string.available_since) + " " + property.getEntryDate();
            availabilityTv.setText(availability);
        }
    }

    private void displayPrice(Property property) {
        String price = "$ " + property.getPrice();
        priceTv.setText(price);
    }

    private void displayAddress(Property property) {
        String address = property.getAddress()+", "+ property.getCity()+", "+property.getState();
        addressTv.setText(address);
    }

    private void displayType(Property property) {
        typeTv.setText(property.getType());
    }

    @OnClick(R.id.fragment_detail_edit_bt)
    public void onUpdatePropertyclicked() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment insertPropertyFragment = InsertPropertyFragment.newInstance(property.getId());

        final float screenWidthInDp = Utils.getScreenWidthInDp(getActivity());

        if (screenWidthInDp > 600) {
            transaction.replace(R.id.activity_main_frame_layout_detail_large_screen, insertPropertyFragment).commit();
        } else {
            transaction.replace(R.id.frame_layout_main, insertPropertyFragment).commit();
        }
    }
}