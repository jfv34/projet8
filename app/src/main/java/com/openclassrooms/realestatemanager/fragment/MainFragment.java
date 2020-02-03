package com.openclassrooms.realestatemanager.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.PropertyAdapter;
import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;

public class MainFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    ArrayList<String> testList = new ArrayList<>();
    public MainFragment() { }

    private OnButtonClickedListener callback;

    public interface OnButtonClickedListener {
        public void onButtonClicked(View view);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        root.findViewById(R.id.fragment_main_button_1).setOnClickListener(this);
        root.findViewById(R.id.fragment_main_button_2).setOnClickListener(this);
        recyclerView = root.findViewById(R.id.fragment_main_recyclerView);


        testList.add("Flat...");
        testList.add("Duplex...");
        testList.add("Flat...");
        testList.add("House...");

        displayTestList(testList);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createCallbackToParentActivity();
    }


    @Override
    public void onClick(View view) {
        callback.onButtonClicked(view);
    }

    private void createCallbackToParentActivity() {
        try {
            callback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement OnButtonClickedListener");
        }
    }

    public void displayTestList(ArrayList<String> testList) {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new PropertyAdapter(testList, getContext()));
    }
}