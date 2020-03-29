package com.openclassrooms.realestatemanager.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment implements OnPropertyClickedListener {

    @BindView(R.id.fragment_main_recyclerView) RecyclerView recyclerView;
    @BindView(R.id.fragment_main_toolbar) Toolbar toolbar;

    private MainFragmentViewModel viewModel;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PropertyDataBase.getInstance(getContext());

        viewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

        viewModel.properties.observe(getViewLifecycleOwner(), properties -> {
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

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment detailsFragment = DetailsFragment.newInstance(property);

        final float screenWidthInDp = Utils.getScreenWidthInDp(getActivity());

        if (screenWidthInDp > 600) {
            transaction.replace(R.id.activity_main_frame_layout_detail_large_screen, detailsFragment).commit();
        } else {
            transaction.replace(R.id.frame_layout_main, detailsFragment).commit();
        }
    }

    @OnClick(R.id.insert_property_button)
    public void onInsertPropertyClicked() {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment insertPropertyFragment = FormPropertyFragment.newInstance(-1);

        final float screenWidthInDp = Utils.getScreenWidthInDp(getActivity());

        if (screenWidthInDp > 600) {
            transaction.replace(R.id.activity_main_frame_layout_detail_large_screen, insertPropertyFragment).commit();
        } else {
            transaction.replace(R.id.frame_layout_main, insertPropertyFragment).commit();
        }
    }
    }
