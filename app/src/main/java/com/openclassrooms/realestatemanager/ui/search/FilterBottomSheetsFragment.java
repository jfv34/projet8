package com.openclassrooms.realestatemanager.ui.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterBottomSheetsFragment extends BottomSheetDialogFragment {
    private FilterFragmentViewModel viewModel;
    private View root;


    @BindView(R.id.fragment_search_cities_textInputEditText)
    TextInputEditText search_cities;

    @BindView(R.id.fragment_filter_type_0_chip)
    Chip chip_type_0;
    @BindView(R.id.fragment_filter_type_1_chip)
    Chip chip_type_1;
    @BindView(R.id.fragment_filter_type_2_chip)
    Chip chip_type_2;
    @BindView(R.id.fragment_filter_type_3_chip)
    Chip chip_type_3;
    @BindView(R.id.fragment_filter_type_4_chip)
    Chip chip_type_4;


    public static FilterBottomSheetsFragment newInstance() {
        return new FilterBottomSheetsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_filter, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PropertyDataBase.getInstance(getContext());
        viewModel = new ViewModelProvider(this).get(FilterFragmentViewModel.class);

        String[] TYPES = viewModel.getTYPES();
        if (TYPES.length>0) {
            chip_type_0.setText(TYPES[0]);
            chip_type_0.setVisibility(View.VISIBLE);
        }
        if (TYPES.length>1) {
            chip_type_1.setText(TYPES[1]);
            chip_type_1.setVisibility(View.VISIBLE);
        }
        if (TYPES.length>2) {
            chip_type_2.setText(TYPES[2]);
            chip_type_2.setVisibility(View.VISIBLE);
        }
        if (TYPES.length>3) {
            chip_type_3.setText(TYPES[3]);
            chip_type_3.setVisibility(View.VISIBLE);
        }
        if (TYPES.length>4) {
            chip_type_4.setText(TYPES[4]);
            chip_type_4.setVisibility(View.VISIBLE);
        }


    }

    @OnClick(R.id.fragment_search_validate_fab)
    public void search_validate() {
        String cities = search_cities.getText().toString();
        dismiss();
        /*FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment resultSearchFragment = back_ResultSearchFragment.newInstance(cities);

        final boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            transaction.replace(R.id.activity_main_frame_layout_detail_large_screen, resultSearchFragment).commit();
        } else {
            transaction.replace(R.id.frame_layout_main, resultSearchFragment).commit();
        }
    */
    }
}
