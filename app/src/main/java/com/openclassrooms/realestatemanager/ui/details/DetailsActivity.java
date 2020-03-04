package com.openclassrooms.realestatemanager.ui.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.openclassrooms.realestatemanager.R;

public class DetailsActivity extends AppCompatActivity {

    String bundleProperty;

    public static void start(Activity activity, String property){
        Intent intent = new Intent(activity, DetailsActivity.class);
        Bundle b = new Bundle();
        b.putString("property", property);
        intent.putExtras(b);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle b = getIntent().getExtras();
        if(b != null) bundleProperty = b.getString("property");

        configureAndShowDetailFragment();
    }

    private void configureAndShowDetailFragment(){
        DetailsFragment detailFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_detail_frame_layout_detail);

        if (detailFragment == null) {
            detailFragment = DetailsFragment.newInstance("");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_detail_frame_layout_detail, detailFragment)
                    .commit();
        }
    }
}