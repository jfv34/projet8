package com.openclassrooms.realestatemanager.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.clickedListener_interfaces.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.models.Currency;
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment;
import com.openclassrooms.realestatemanager.ui.editProperty.FormPropertyFragment;
import com.openclassrooms.realestatemanager.ui.utils.SharedCurrencyViewModel;
import com.openclassrooms.realestatemanager.ui.utils.SharedPropertiesViewModel;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment implements OnPropertyClickedListener {

    @BindView(R.id.fragment_main_recyclerView) RecyclerView recyclerView;

    private SharedPropertiesViewModel sharedPropertiesViewModel;
    private SharedCurrencyViewModel sharedCurrencyViewModel;
    private PropertyAdapter propertyAdapter;
    public static final String PREFS_CURRENCY = "PREFS_CURRENCY";
    SharedPreferences sharedPreferences;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(PREFS_CURRENCY, MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);
        configureRecyclerView();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPropertiesViewModel = new ViewModelProvider(requireActivity()).get(SharedPropertiesViewModel.class);
        if (sharedPropertiesViewModel.isFiltered = false) {
            sharedPropertiesViewModel.loadProperties();
        }
        observeProperties();

        sharedCurrencyViewModel = new ViewModelProvider(requireActivity()).get(SharedCurrencyViewModel.class);
        String prefs_currency = sharedPreferences.getString(PREFS_CURRENCY, "dollars");
        if (prefs_currency.equals("euros")) {
            sharedCurrencyViewModel.currency.postValue(Currency.EUROS);
        } else {
            sharedCurrencyViewModel.currency.postValue(Currency.DOLLARS);
        }
    }

    private void observeProperties() {
        if (sharedPropertiesViewModel.properties.getValue() == null) {
            sharedPropertiesViewModel.loadProperties();
        }
        sharedPropertiesViewModel.properties.observe(getViewLifecycleOwner(), properties -> {
            if (properties.size() == 0) {
                Utils.toast(getActivity(), R.string.nodatatodisplay);
            }

            if (propertyAdapter == null) {
                Currency currency = sharedCurrencyViewModel.currency.getValue();
                propertyAdapter = new PropertyAdapter(properties, currency, getContext(), MainFragment.this);

                recyclerView.setAdapter(propertyAdapter);
            } else {
                propertyAdapter.updateProperties(properties);
            }
        });
    }

    public void configureRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onPropertyClicked(int property) {
        Fragment detailsFragment = DetailsFragment.newInstance(property);
        Utils.replaceFragmentInDetailScreen(getActivity(), detailsFragment);
    }

    @OnClick(R.id.fragment_main_insert_property_fab)
    public void onInsertPropertyClicked() {
        Fragment formPropertyFragment = FormPropertyFragment.newInstance(-1);
        Utils.addFragmentInDetailScreen(getActivity(), formPropertyFragment);
    }
}