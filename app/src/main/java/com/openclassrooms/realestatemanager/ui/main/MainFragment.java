package com.openclassrooms.realestatemanager.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.ui.details.DetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements OnPropertyClickedListener {

    @BindView(R.id.fragment_main_recyclerView)
    RecyclerView recyclerView;

    private MainFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,root);

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


    }

    public void configureRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onPropertyClicked(String property) {

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