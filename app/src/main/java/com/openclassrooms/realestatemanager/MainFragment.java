package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment implements View.OnClickListener {

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
}