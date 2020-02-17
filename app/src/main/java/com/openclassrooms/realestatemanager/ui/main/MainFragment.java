package com.openclassrooms.realestatemanager.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.ui.details.DetailsActivity;
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    PropertyDataBase propertyDataBase;
    MainFragmentContract.Presenter presenter;
    RecyclerView recyclerView;
    ArrayList<String> testList = new ArrayList<>();

    private DatasViewModel model;
    private MutableLiveData<String> currentName = new MutableLiveData<>();
    private DetailsFragment detailsFragment;

    public MainFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainFragmentPresenter();

        model= ViewModelProviders.of(this).get(DatasViewModel.class);

        propertyDataBase = new PropertyDataBase() {
            @NonNull
            @Override
            protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
                return null;
            }

            @NonNull
            @Override
            protected InvalidationTracker createInvalidationTracker() {
                return null;
            }

            @Override
            public void clearAllTables() {

            }

            @Override
            public PropertyDao propertyDao() {
                return null;
            }
        };}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = root.findViewById(R.id.fragment_main_recyclerView);

        testList = presenter.getMainDatas();

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