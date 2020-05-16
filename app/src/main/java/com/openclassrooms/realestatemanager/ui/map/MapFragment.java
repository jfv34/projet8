package com.openclassrooms.realestatemanager.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.openclassrooms.realestatemanager.R;

public class MapFragment extends Fragment {

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private static final int PERMISSIONS_REQUEST_CODE = 123;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }


}
