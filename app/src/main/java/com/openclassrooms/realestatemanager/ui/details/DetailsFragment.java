package com.openclassrooms.realestatemanager.ui.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.SharedViewModel;

import java.util.List;

public class DetailsFragment extends Fragment {

    SharedViewModel sharedViewModel;
    DetailsFragmentViewModel viewModel;
    String propertyClicked = "";

    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);


        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(DetailsFragmentViewModel.class);
        viewModel.photos.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if(!propertyClicked.isEmpty()){viewModel.loadPhotos(propertyClicked);
                    viewPager.setAdapter(new PhotosPageAdapter(strings, getContext()));
                }
            }
        });

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        sharedViewModel.propertyClicked.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                propertyClicked=s;
                viewModel.loadPhotos(propertyClicked);


            }
        });
        configureViewPager(view);
    }

    public void configureViewPager(View view) {
        viewPager = view.findViewById(R.id.fragment_detail_viewpager);
    }
}