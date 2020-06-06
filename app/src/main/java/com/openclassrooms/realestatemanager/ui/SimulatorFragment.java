package com.openclassrooms.realestatemanager.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;

import butterknife.ButterKnife;

public class SimulatorFragment extends Fragment {

    public static SimulatorFragment newInstance() {
        SimulatorFragment simulatorFragment = new SimulatorFragment();
        return simulatorFragment;
    }

    private View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_simulator, container, false);
        ButterKnife.bind(this, root);
        return root;
    }
}
