package com.openclassrooms.realestatemanager.ui.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.insertProperty.InsertPropertyFragment;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    int bundleProperty;
    String action;

    public static void start(Activity activity, int property, String action) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        Bundle b = new Bundle();
        b.putInt("property", property);
        b.putString("action",action);
        intent.putExtras(b);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            bundleProperty = b.getInt("property");
            action = b.getString("action");

            switch(Objects.requireNonNull(b.getString("action"))){
                case "DISPLAY_PROPERTY": configureAndShowDetailFragment();
                break;
                case "CREATE_PROPERTY": configureAndShowInsertPropertyFragment();
            }
        }
    }

    private void configureAndShowInsertPropertyFragment() {
        InsertPropertyFragment insertPropertyFragment = (InsertPropertyFragment) getSupportFragmentManager().findFragmentById(R.id.activity_detail_frame_layout_detail);

        if (insertPropertyFragment == null) {
            insertPropertyFragment = InsertPropertyFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_detail_frame_layout_detail, insertPropertyFragment)
                    .commit();
        }
    }

    private void configureAndShowDetailFragment(){
        Boolean large_screen = findViewById(R.id.activity_main_frame_layout_detail_large_screen).isEnabled();


        DetailsFragment detailFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_detail_frame_layout_detail_small_screen);

        if (detailFragment == null) {
            detailFragment = DetailsFragment.newInstance(bundleProperty);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_detail_frame_layout_detail, detailFragment)
                    .commit();
        }
    }
}