package com.openclassrooms.realestatemanager.ui.details;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.DatasViewModel;
import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;

public class DetailsFragment extends Fragment {

    DetailsFragmentContract.Presenter presenter;
    RecyclerView recyclerView;
    ArrayList<String> testPhotos = new ArrayList<>();
    private DatasViewModel model;
    private MutableLiveData<String> currentName = new MutableLiveData<>();




    public DetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        presenter = new DetailsFragmentPresenter();

        model = ViewModelProviders.of(this).get(DatasViewModel.class);
        model.getCurrentName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        recyclerView = root.findViewById(R.id.fragment_detail_photos_rv);

        testPhotos.add("photo 1");
        testPhotos.add("photo 2");
        testPhotos.add("photo 3");
        testPhotos.add("photo 4");
        testPhotos.add("photo 5");
        testPhotos.add("photo 6");
        testPhotos.add("photo 7");
        testPhotos.add("photo 8");

        displayTestPhotos(testPhotos);

        return root;
    }

    public void displayTestPhotos(ArrayList<String> testPhotos) {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new PhotoAdapter(testPhotos, getContext()));
    }
}
