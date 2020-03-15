package com.openclassrooms.realestatemanager.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;

public class InsertPropertyFragment extends Fragment {

    public static Fragment newInstance() {
        InsertPropertyFragment insertPropertyFragment = new InsertPropertyFragment();
        return insertPropertyFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_insert, container, false);

        return root;
    }

}

