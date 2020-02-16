package com.openclassrooms.realestatemanager.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.DatasViewModel;
import com.openclassrooms.realestatemanager.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.details.DetailsActivity;
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> testList = new ArrayList<>();
    private DatasViewModel model;
    private MutableLiveData<String> currentName = new MutableLiveData<>();
    private DetailsFragment detailsFragment;

    public MainFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model= ViewModelProviders.of(this).get(DatasViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = root.findViewById(R.id.fragment_main_recyclerView);

        testList.add("Flat");
        testList.add("Duplex");
        testList.add("Flat");
        testList.add("House");
        testList.add("Flat");
        testList.add("Duplex");
        testList.add("Flat");
        testList.add("House");
        testList.add("Flat");
        testList.add("Duplex");
        testList.add("Flat");
        testList.add("House");
        testList.add("Flat");
        testList.add("Duplex");
        testList.add("Flat");
        testList.add("House");

        displayTestList(testList);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void displayTestList(ArrayList<String> testList) {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new PropertyAdapter(testList, getContext(), new OnPropertyClickedListener() {
            @Override
            public void onClicked(String property) {
                onPropertyClicked(property);
            }
        }));
    }

    private void onPropertyClicked(String property) {
        Log.i("tag_clicked",property);
        model.setCurrentName(property);
        detailsFragment = (DetailsFragment) getFragmentManager().findFragmentById(R.id.frame_layout_detail);
        if (detailsFragment != null && detailsFragment.isVisible()) {

        } else {
            Intent i = new Intent(getActivity(), DetailsActivity.class);
            startActivity(i);
        }
    }
}