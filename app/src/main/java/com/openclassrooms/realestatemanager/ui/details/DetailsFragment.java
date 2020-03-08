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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundleProperty = getArguments().getInt("property", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        typeTv = root.findViewById(R.id.fragment_detail_type_tv);
        addressTv = root.findViewById(R.id.fragment_detail_address_tv);
        priceTv = root.findViewById(R.id.fragment_detail_price_tv);
        not_soldedIv = root.findViewById(R.id.fragment_detail_not_solded_iv);
        soldedIv = root.findViewById(R.id.fragment_detail_solded_iv);
        availabilityTv = root.findViewById(R.id.fragment_detail_availability_tv);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DetailsFragmentViewModel.class);

        viewModel.properties.observe(getViewLifecycleOwner(), properties -> {
            if (properties != null) {
                Property property = properties.get(bundleProperty);

                typeTv.setText(property.getType());
                addressTv.setText(property.getAddress());
                String price = "$ " + property.getPrice();
                priceTv.setText(price);

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



                // PhotoURI photoURI = property.getPhotosURI();
                // viewPager.setAdapter(new PhotosPageAdapter(photoURI, getContext()));
            }
        });

        configureViewPager(view);
    }

    public void configureViewPager(View view) {
        //viewPager = view.findViewById(R.id.fragment_detail_viewpager);
    }
}