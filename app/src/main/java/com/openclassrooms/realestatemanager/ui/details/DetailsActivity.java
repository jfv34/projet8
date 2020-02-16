package com.openclassrooms.realestatemanager.ui.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.openclassrooms.realestatemanager.R;

public class DetailsActivity extends AppCompatActivity {

    private DetailsActivityContract.Presenter presenter;
    private DetailsFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        presenter = new DetailsActivityPresenter();

        configureAndShowDetailFragment();
    }

    private void configureAndShowDetailFragment(){
        detailFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        if (detailFragment == null) {
            detailFragment = new DetailsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_detail, detailFragment)
                    .commit();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}