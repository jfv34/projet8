package com.openclassrooms.realestatemanager.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.ui.InsertPropertyFragment;
import com.openclassrooms.realestatemanager.ui.details.DetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements OnPropertyClickedListener {

    @BindView(R.id.fragment_main_recyclerView) RecyclerView recyclerView;
    @BindView(R.id.fragment_main_toolbar) Toolbar toolbar;

    private MainFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,root);

        configureToolBar();
        configureRecyclerView();

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PropertyDataBase.getInstance(getContext());

        viewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

        viewModel.properties.observe(getViewLifecycleOwner(), properties -> {
            if (properties != null) {
                if (properties.isEmpty()){toast("No datas to display");}
                recyclerView.setAdapter(new PropertyAdapter(properties, getContext(), MainFragment.this));
            }

        });

        configure_test_button_for_replace_fragment(view);

    }

    private void configure_test_button_for_replace_fragment(View view) {
        Button button = view.findViewById(R.id.test_replace_fragment_button);
        button.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment insertPropertyFragment = InsertPropertyFragment.newInstance();
            transaction.replace(R.id.frame_layout_main, insertPropertyFragment).commit();
        });
    }

    public void configureRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void configureToolBar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onPropertyClicked(int property) {
        DetailsActivity.start(getActivity(), property);
    }

    private void toast(int message) {
        Toast toast = Toast.makeText(getActivity(), getString(message), Toast.LENGTH_LONG);
        toast.show();
    }
    private void toast(String message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}