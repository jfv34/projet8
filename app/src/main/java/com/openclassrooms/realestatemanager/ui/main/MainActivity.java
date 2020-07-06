package com.openclassrooms.realestatemanager.ui.main;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.openclassrooms.realestatemanager.R;

public class MainActivity extends AppCompatActivity {

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureAndShowMainFragment();
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
    @Override
    public void onBackPressed() {
        switch (getSupportFragmentManager().getBackStackEntryCount()) {

            case 0:
                final boolean tabletSize = getApplicationContext().getResources().getBoolean(R.bool.isTablet);
                final boolean orientation_landScape = getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
                if (tabletSize || orientation_landScape) {
                    //todo remove fragment ?
                }
                super.onBackPressed();
                break;
            default:
                getSupportFragmentManager().popBackStack();
        }
    }
}