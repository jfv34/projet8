package com.openclassrooms.realestatemanager.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.SharedViewModel;
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements OnPropertyClickedListener {

    @BindView(R.id.fragment_main_recyclerView) RecyclerView recyclerView;

    private MainFragmentViewModel viewModel;
    private SharedViewModel sharedViewModel;
    private DetailsFragment detailsFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,root);

        configureRecyclerView();

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);


       viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);

        viewModel.properties.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                recyclerView.setAdapter(new PropertyAdapter(strings, getContext(), MainFragment.this));
            }
        });

        viewModel.loadProperties();
    }

    public void configureRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onPropertyClicked(String property) {
        Log.i("tag_clicked",property);
        sharedViewModel.propertyClicked.setValue(property);

        detailsFragment = (DetailsFragment) getFragmentManager().findFragmentById(R.id.frame_layout_detail);
        if (detailsFragment != null && detailsFragment.isVisible()) {

        } else {
            detailsFragment = new DetailsFragment();

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout_main, detailsFragment)
                    .commit();
        }
    }
}