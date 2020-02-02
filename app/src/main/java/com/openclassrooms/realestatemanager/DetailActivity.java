package com.openclassrooms.realestatemanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_BUTTON_TAG = "com.openclassrooms.realestatemanager.DetailActivity.EXTRA_BUTTON_TAG";

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
        // 3 - Call update method here because we are sure that DetailFragment is visible
        this.updateDetailFragmentTextViewWithIntentTag();
    }

    private void updateDetailFragmentTextViewWithIntentTag() {
        int buttonTag = getIntent().getIntExtra(EXTRA_BUTTON_TAG, 0);
        detailFragment.updateTextView(buttonTag);
    }


}