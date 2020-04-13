package com.openclassrooms.realestatemanager.ui.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends Fragment {
    private SearchFragmentViewModel viewModel;
    private View root;

    @BindView(R.id.fragment_search_cities_textInputEditText)
    TextInputEditText search_cities;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PropertyDataBase.getInstance(getContext());
        viewModel = new ViewModelProvider(this).get(SearchFragmentViewModel.class);
    }

    @OnClick(R.id.fragment_search_validate_fab)
    public void search_validate() {
        String cities = search_cities.getText().toString();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment resultSearchFragment = ResultSearchFragment.newInstance(cities);

        final boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            transaction.replace(R.id.activity_main_frame_layout_detail_large_screen, resultSearchFragment).commit();
        } else {
            transaction.replace(R.id.frame_layout_main, resultSearchFragment).commit();
        }
    
    }
}
