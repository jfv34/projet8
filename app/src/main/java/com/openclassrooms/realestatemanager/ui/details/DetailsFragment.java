package com.openclassrooms.realestatemanager.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Property;

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
    private ViewPager viewPager;
    private TextView typeTv;
    private TextView addressTv;
    private TextView priceTv;
    private ImageView not_soldedIv;
    private ImageView soldedIv;
    private TextView availabilityTv;
    private TextView surfaceAndPiecesTv;
    private TextView interestsPointsTv;
    private TextView descriptionTv;
    private TextView agentTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundleProperty = getArguments().getInt("property", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureView(view);

        viewModel = new ViewModelProvider(this).get(DetailsFragmentViewModel.class);
        viewModel.properties.observe(getViewLifecycleOwner(), properties -> {
            if (properties != null) {
                Property property = properties.get(bundleProperty);
                loadProperty(property);
            }
        });
    }

    private void configureView(View view) {
        viewPager = view.findViewById(R.id.fragment_detail_viewpager);
        typeTv = view.findViewById(R.id.fragment_detail_type_tv);
        addressTv = view.findViewById(R.id.fragment_detail_address_tv);
        priceTv = view.findViewById(R.id.fragment_detail_price_tv);
        not_soldedIv = view.findViewById(R.id.fragment_detail_not_solded_iv);
        soldedIv = view.findViewById(R.id.fragment_detail_solded_iv);
        availabilityTv = view.findViewById(R.id.fragment_detail_availability_tv);
        surfaceAndPiecesTv = view.findViewById(R.id.fragment_detail_surfaceAndPieces_tv);
        interestsPointsTv = view.findViewById(R.id.fragment_detail_interestPoints_tv);
        descriptionTv = view.findViewById(R.id.fragment_detail_description_tv);
        agentTv = view.findViewById(R.id.fragment_detail_agent_tv);
    }

    private void loadProperty(Property property) {
        displayPhotos(property);
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
        // PhotoURI photoURI = property.getPhotosURI();
        // viewPager.setAdapter(new PhotosPageAdapter(photoURI, getContext()));
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
        addressTv.setText(property.getAddress());
    }

    private void displayType(Property property) {
        typeTv.setText(property.getType());
    }
}