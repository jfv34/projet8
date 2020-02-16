package com.openclassrooms.realestatemanager.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.openclassrooms.realestatemanager.DatasViewModel;
import com.openclassrooms.realestatemanager.R;

import com.openclassrooms.realestatemanager.ui.details.DetailsActivity;
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment;

public class MainActivity extends AppCompatActivity {

    private MainActivityContract.Presenter presenter;
    private DatasViewModel model;
    private Toolbar toolbar;
    private MainFragment mainFragment;
    private MutableLiveData<String> currentName;
    private DetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter();

        configureAndShowMainFragment();
        configureAndShowDetailFragment();
        toolbar = findViewById(R.id.toolbar_main);

        model = ViewModelProviders.of(this).get(DatasViewModel.class);
        model.getCurrentName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
Log.i("tag_changed", "ok: "+s);

            }
        });
    }

    private void configureAndShowMainFragment() {

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, mainFragment)
                    .commit();
        }
    }

    private void configureAndShowDetailFragment() {
        detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);
        if (detailsFragment == null && findViewById(R.id.frame_layout_detail) != null) {
            detailsFragment = new DetailsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_detail, detailsFragment)
                    .commit();
        }
    }
}