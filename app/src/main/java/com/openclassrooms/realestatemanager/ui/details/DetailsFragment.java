package com.openclassrooms.realestatemanager.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.openclassrooms.realestatemanager.R;

public class DetailsFragment extends Fragment {

    public static DetailsFragment newInstance(String bundleProperty) {
        DetailsFragment detailsFragment = new DetailsFragment();

        Bundle args = new Bundle();
        args.putString("property", bundleProperty);
        detailsFragment.setArguments(args);

        return detailsFragment;
    }

    DetailsFragmentViewModel viewModel;
    Integer bundleProperty;

    private ViewPager viewPager;

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

        viewModel = new ViewModelProvider(this).get(DetailsFragmentViewModel.class);
        viewModel.photos.observe(getViewLifecycleOwner(), strings -> {

            //  viewPager.setAdapter(new PhotosPageAdapter(PhotoURI, getContext()));

        });
        viewModel.loadPhotos(bundleProperty);
        configureViewPager(view);
    }

    public void configureViewPager(View view) {
        //viewPager = view.findViewById(R.id.fragment_detail_viewpager);
    }
}