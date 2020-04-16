package com.openclassrooms.realestatemanager.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class back_ResultSearchFragment extends Fragment implements OnPropertyClickedListener {

    @BindView(R.id.fragment_result_search_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_result_search_toolbar)
    Toolbar toolbar;

    private FilterFragmentViewModel viewModel;
    private String bundleCities;
    private View root;

    public static back_ResultSearchFragment newInstance(String bundleCities) {
        back_ResultSearchFragment resultSearchFragment = new back_ResultSearchFragment();
        Bundle args = new Bundle();
        args.putString("cities", bundleCities);
        resultSearchFragment.setArguments(args);
        return resultSearchFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundleCities = getArguments().getString("cities", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_result_search, container, false);
        ButterKnife.bind(this, root);

        configureToolBar();
        configureRecyclerView();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PropertyDataBase.getInstance(getContext());

        viewModel = new ViewModelProvider(this).get(FilterFragmentViewModel.class);
        viewModel.loadPropertyByCity(bundleCities);

        viewModel.propertiesByCity.observe(getViewLifecycleOwner(), propertiesByCity ->
                {
                    if (propertiesByCity != null) {
                        recyclerView.setAdapter(new back_SearchPropertyAdapter(propertiesByCity, getContext(), back_ResultSearchFragment.this));
                    } else {
                        Utils.toast(getActivity(), "No datas to display");
                        ;
                    }
                }
        );
    }

    public void configureRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void configureToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onPropertyClicked(int property) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment detailsFragment = DetailsFragment.newInstance(property);

        final boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            transaction.replace(R.id.activity_main_frame_layout_detail_large_screen, detailsFragment).commit();
        } else {
            transaction.replace(R.id.frame_layout_main, detailsFragment).commit();
        }
    }
}


