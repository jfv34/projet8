package com.openclassrooms.realestatemanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.openclassrooms.realestatemanager.fragment.DetailFragment;
import com.openclassrooms.realestatemanager.R;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_BUTTON_TAG = "com.openclassrooms.realestatemanager.activity.DetailActivity.EXTRA_BUTTON_TAG";

    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        configureAndShowDetailFragment();
    }

    private void configureAndShowDetailFragment(){
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        if (detailFragment == null) {
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_detail, detailFragment)
                    .commit();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        //this.updateDetailFragmentTextViewWithIntentTag();
    }

   /* private void updateDetailFragmentTextViewWithIntentTag() {
        int buttonTag = getIntent().getIntExtra(EXTRA_BUTTON_TAG, 0);
        detailFragment.updateTextView(buttonTag);
    }*/
}