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

import com.openclassrooms.realestatemanager.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment;
import com.openclassrooms.realestatemanager.ui.editProperty.FormPropertyFragment;
import com.openclassrooms.realestatemanager.ui.filter.FilterFragment;
import com.openclassrooms.realestatemanager.ui.filter.SharedFilterViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment implements OnPropertyClickedListener {

    @BindView(R.id.fragment_main_recyclerView) RecyclerView recyclerView;
    @BindView(R.id.fragment_main_toolbar) Toolbar toolbar;

    private SharedFilterViewModel sharedPropertyViewModel;
    private MainFragmentViewModel mainFragmentViewModel;
    private View root;
    private boolean alreadyFiltred = false;

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

        sharedPropertyViewModel = new ViewModelProvider(requireActivity()).get(SharedFilterViewModel.class);
        mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

        mainFragmentViewModel.loadProperties();

        mainFragmentViewModel.properties.observe(getViewLifecycleOwner(), properties -> {
            if (properties != null) {
                if (properties.isEmpty()) {
                    Utils.toast(getActivity(), "No datas to display");
                }

                if(!alreadyFiltred && !properties.isEmpty()){
                    sharedPropertyViewModel.filter.observe(getViewLifecycleOwner(), filter -> {

                        mainFragmentViewModel.setFilter(filter);
                        mainFragmentViewModel.filter();
                        alreadyFiltred=true;
                    });
                    ;}

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
        Utils.replaceFragmentInDetailScreen(getActivity(), detailsFragment);
    }

    @OnClick(R.id.fragment_main_insert_property_fab)
    public void onInsertPropertyClicked() {
        Fragment formPropertyFragment = FormPropertyFragment.newInstance(-1);
        Utils.replaceFragmentInDetailScreen(getActivity(), formPropertyFragment);
    }

    @OnClick(R.id.fragment_main_search_button)
    public void onFilterClicked() {
        //mainFragmentViewModel.loadProperties();
        Fragment filterFragment = FilterFragment.newInstance();
        Utils.replaceFragmentInDetailScreen(getActivity(), filterFragment);
    }


}

