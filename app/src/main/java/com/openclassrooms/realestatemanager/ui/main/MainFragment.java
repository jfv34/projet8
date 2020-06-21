package com.openclassrooms.realestatemanager.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.clickedListener_interfaces.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment;
import com.openclassrooms.realestatemanager.ui.details.SharedDetailViewModel;
import com.openclassrooms.realestatemanager.ui.editProperty.FormPropertyFragment;
import com.openclassrooms.realestatemanager.ui.filter.FilterFragment;
import com.openclassrooms.realestatemanager.ui.filter.SharedFilterViewModel;
import com.openclassrooms.realestatemanager.ui.map.MapFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment implements OnPropertyClickedListener {

    @BindView(R.id.fragment_main_recyclerView) RecyclerView recyclerView;
    @BindView(R.id.fragment_main_toolbar) Toolbar toolbar;

    private SharedFilterViewModel sharedFilterViewModel;
    private SharedDetailViewModel sharedDetailViewModel;
    private PropertyAdapter propertyAdapter;
    private Property lastProperty;
    private View root;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,root);
        configureToolBar();
        configureRecyclerView();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedFilterViewModel = new ViewModelProvider(requireActivity()).get(SharedFilterViewModel.class);
        if (sharedFilterViewModel.isFiltred = false) {
            sharedFilterViewModel.loadProperties();
        }
        observeFilterProperties();

        sharedDetailViewModel = new ViewModelProvider(requireActivity()).get(SharedDetailViewModel.class);
        observeLastDetailProperty();
    }

    private void observeLastDetailProperty() {
        lastProperty = sharedDetailViewModel.property.getValue();
        sharedDetailViewModel.property.observe(getViewLifecycleOwner(), property -> {
            List<Property> properties;

            if (sharedFilterViewModel.properties.getValue() != null) {
                properties = sharedFilterViewModel.properties.getValue();
                if (properties.size() == 0) {
                    Utils.toast(getActivity(), "No data to display");
                } else {
                    if (lastProperty != null) {
                        List<Property> oldProperties=new ArrayList<>();
                        oldProperties=properties;
                        properties.remove(lastProperty.getId()-1);
                        Property newProperty = sharedDetailViewModel.property.getValue();
                        properties.add(newProperty);
                        lastProperty = newProperty;
                        propertyAdapter.updateProperties(properties,oldProperties);

                      } else {
                        lastProperty = sharedDetailViewModel.property.getValue();
                    }
                }
                ;
            }
        });
    }
    private void observeFilterProperties() {
        if (sharedFilterViewModel.properties.getValue() == null) {
            sharedFilterViewModel.loadProperties();
        }
        sharedFilterViewModel.properties.observe(getViewLifecycleOwner(), properties -> {

            if (properties.size() == 0) {
                    Utils.toast(getActivity(), "No data to display");
                } else {
                propertyAdapter = new PropertyAdapter(properties, getContext(), MainFragment.this);
                recyclerView.setAdapter(propertyAdapter);
                }
        });
    }

    public void configureRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void configureToolBar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onPropertyClicked(int property) {

        Fragment detailsFragment = DetailsFragment.newInstance(property);
        Utils.addFragmentInDetailScreen(getActivity(), detailsFragment);
    }

    @OnClick(R.id.fragment_main_insert_property_fab)
    public void onInsertPropertyClicked() {
        Fragment formPropertyFragment = FormPropertyFragment.newInstance(-1);
        Utils.addFragmentInDetailScreen(getActivity(), formPropertyFragment);
    }

    @OnClick(R.id.fragment_main_filter_button)
    public void onFilterClicked() {
        Fragment filterFragment = FilterFragment.newInstance();
        Utils.addFragmentInDetailScreen(getActivity(), filterFragment);
    }

    @OnClick(R.id.fragment_main_map_button)
    public void onMapClicked() {
        Fragment mapFragment = MapFragment.newInstance();
        Utils.replaceFragmentInMainScreen(getActivity(), mapFragment);
    }
}