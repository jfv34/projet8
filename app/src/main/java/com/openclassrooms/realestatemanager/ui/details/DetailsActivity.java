package com.openclassrooms.realestatemanager.ui.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.openclassrooms.realestatemanager.R;

public class DetailsActivity extends AppCompatActivity {

    int bundleProperty;

    public static void start(Activity activity, int property){
        Intent intent = new Intent(activity, DetailsActivity.class);
        Bundle b = new Bundle();
        b.putInt("property", property);
        intent.putExtras(b);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle b = getIntent().getExtras();
        if(b != null) bundleProperty = b.getInt("property");
        configureAndShowDetailFragment();
    }

    private void configureAndShowDetailFragment(){
        DetailsFragment detailFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_detail_frame_layout_detail);

        if (detailFragment == null) {
            detailFragment = DetailsFragment.newInstance(bundleProperty);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_detail_frame_layout_detail, detailFragment)
                    .commit();
        }
    }
}