package com.openclassrooms.realestatemanager.activity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.openclassrooms.realestatemanager.DatasViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.fragment.DetailFragment;
import com.openclassrooms.realestatemanager.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnButtonClickedListener {

    private DatasViewModel model;
    private Toolbar toolbar;
    private MainFragment mainFragment;
    private DetailFragment detailFragment;
    private MutableLiveData<String> currentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureAndShowMainFragment();
        configureAndShowDetailFragment();
        toolbar = findViewById(R.id.toolbar_main);

        //model = new ViewModelProvider(this).get(DatasViewModel.class);


        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        };
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
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);
        if (detailFragment == null && findViewById(R.id.frame_layout_detail) != null) {
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_detail, detailFragment)
                    .commit();
        }
    }

    @Override
    public void onButtonClicked(View view) {

        int buttonTag = Integer.parseInt(view.getTag().toString());
        if (detailFragment != null && detailFragment.isVisible()) {
            detailFragment.updateTextView(buttonTag);
        } else {
            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra(DetailActivity.EXTRA_BUTTON_TAG, buttonTag);
            startActivity(i);
        }
    }
}