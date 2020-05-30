package com.openclassrooms.realestatemanager.ui.main;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment;

import java.util.List;

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

      List fragments = getSupportFragmentManager().getFragments();
        String currentFragmentPath = fragments.get(fragments.size() - 1).getClass().getName();
        String currentFragment = currentFragmentPath.substring(currentFragmentPath.lastIndexOf(".") + 1);

        Fragment fragmentToDisplay = null;
        Log.i("tag_fragment", currentFragment);

        switch (currentFragment) {
            case "FilterFragment":
            case "MapFragment":
            case "DetailsFragment":
                fragmentToDisplay = mainFragment;
                break;
            case "FormPropertyFragment":
                // ???
        }

        Utils.replaceFragmentInDetailScreen(this, fragmentToDisplay);



    }
}