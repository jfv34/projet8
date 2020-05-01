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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment;
import com.openclassrooms.realestatemanager.ui.editProperty.FormPropertyFragment;
import com.openclassrooms.realestatemanager.ui.filter.FilterFragment;
import com.openclassrooms.realestatemanager.ui.filter.SharedPropertyViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment implements OnPropertyClickedListener {

    @BindView(R.id.fragment_main_recyclerView) RecyclerView recyclerView;
    @BindView(R.id.fragment_main_toolbar) Toolbar toolbar;

    private SharedPropertyViewModel sharedPropertyViewModel;
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
        PropertyDataBase.getInstance(getContext());

        sharedPropertyViewModel = new ViewModelProvider(requireActivity()).get(SharedPropertyViewModel.class);
        sharedPropertyViewModel.loadProperties();

        sharedPropertyViewModel.filter.observe(getViewLifecycleOwner(),filter -> {
            sharedPropertyViewModel.filter();
        });


        sharedPropertyViewModel.properties.observe(getViewLifecycleOwner(), properties -> {
            if (properties != null) {
                if (properties.isEmpty()) {
                    Utils.toast(getActivity(), "No datas to display");
                }
                recyclerView.setAdapter(new PropertyAdapter(properties, getContext(), MainFragment.this));
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
        replaceFragment(detailsFragment);
    }

    @OnClick(R.id.fragment_main_insert_property_fab)
    public void onInsertPropertyClicked() {
        Fragment formPropertyFragment = FormPropertyFragment.newInstance(-1);
        replaceFragment(formPropertyFragment);
    }

    @OnClick(R.id.fragment_main_search_button)
    public void onFilterClicked() {
        sharedPropertyViewModel.loadProperties();
        Fragment filterFragment = FilterFragment.newInstance();
        replaceFragment(filterFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        final boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            transaction.replace(R.id.activity_main_frame_layout_detail_large_screen, fragment).commit();
        } else {
            transaction.replace(R.id.frame_layout_main, fragment).commit();
        }
    }
    }

