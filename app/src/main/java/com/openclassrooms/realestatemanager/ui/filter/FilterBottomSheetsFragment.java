package com.openclassrooms.realestatemanager.ui.filter;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Filter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterBottomSheetsFragment extends BottomSheetDialogFragment {
    private FilterFragmentViewModel viewModel;
    private View root;

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
    @BindView(R.id.fragment_filter_price_seekbar)
    SeekBar price_seekBar;
    @BindView(R.id.fragment_filter_cities_textInputEditText)
    EditText cities_Et;
    @BindView(R.id.fragment_filter_state_textInputEditText)
    EditText states_Et;
    @BindView(R.id.fragment_filter_area_seekbar)
    SeekBar area_seekBar;
    @BindView(R.id.fragment_filter_pieces_et)
    EditText pieces_Et;
    @BindView(R.id.fragment_filter_interestPoints_textInputEditText)
    EditText interestPoints_Et;
    @BindView(R.id.fragment_filter_agent_textInputEditText)
    EditText agent_Et;
    @BindView(R.id.fragment_filter_availability_status_dropdown)
    AutoCompleteTextView isSolded_tv;
    @BindView(R.id.fragment_filter_availability_date_textInputEditText)
    EditText availableDate_Et;
    @BindView(R.id.fragment_filter_sold_date_textInputEditText)
    EditText soldeDate_Et;
    @BindView(R.id.fragment_filter_numberOfPhotos_et)
    EditText numberOfPhotos_Et;

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

        viewModel.initialize();
        configureType();
        configureStatus();
    }

    @OnClick(R.id.fragment_filter_pieces_less_btn)
    public void pieces_less() {
        addPieces(-1);
    }

    @OnClick(R.id.fragment_filter_pieces_more_btn)
    public void pieces_more() {
        addPieces(1);
    }

    private void addPieces(int i) {
        viewModel.setPieces(i);
        viewModel.pieces.observe(getViewLifecycleOwner(), pieces ->
                {
                    pieces_Et.setText(String.valueOf(pieces));
                }
        );
    }

    @OnClick(R.id.fragment_filter_numberOfPhotos_less_btn)
    public void numberOfPhotos_less() {
        addNumberOfPhotos(-1);
    }

    @OnClick(R.id.fragment_filter_numberOfPhotos_more_btn)
    public void numberOfPhotos_more() {
        addNumberOfPhotos(1);
    }

    private void addNumberOfPhotos(int i) {
        viewModel.setNumberOfPhotos(i);
        viewModel.numberOfPhotos.observe(getViewLifecycleOwner(), numberOfPhotos ->
                {
                    numberOfPhotos_Et.setText(String.valueOf(numberOfPhotos));
                }
        );
    }

    private void configureType() {
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

    private void configureStatus() {
        final String[] AVAILABILITY = viewModel.getAvailabilities();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, AVAILABILITY);
        AutoCompleteTextView textView = root.findViewById(R.id.fragment_filter_availability_status_dropdown);
        textView.setAdapter(adapter);
        textView.setCursorVisible(false);
    }

    @OnClick(R.id.fragment_search_validate_fab)
    public void filter_validate() {

        String[] TYPES = viewModel.getTYPES();
        ArrayList<String> type = new ArrayList<>();

        if (chip_type_0.isChecked()) {
            type.add(TYPES[0]);
        }
        if (chip_type_1.isChecked()) {
            type.add(TYPES[1]);
        }
        if (chip_type_2.isChecked()) {
            type.add(TYPES[2]);
        }
        if (chip_type_3.isChecked()) {
            type.add(TYPES[3]);
        }

        boolean isSolded;
        if (isSolded_tv.getText().toString().equals(R.string.sold)) {
            isSolded = true;
        } else {
            isSolded = false;
        }

        ArrayList empty_for_test = new ArrayList();
        ArrayList cities_for_test = new ArrayList();
        cities_for_test.add("A");

        Filter filter = new Filter(
                type,
                0,
                1000000000,
                cities_for_test,
                empty_for_test,
                1000,
                0,
                Integer.parseInt(pieces_Et.getText().toString()),
                Integer.parseInt(pieces_Et.getText().toString()),
                interestPoints_Et.getText().toString(),
                agent_Et.getText().toString(),
                isSolded,
                availableDate_Et.getText().toString(),
                soldeDate_Et.getText().toString(),
                Integer.parseInt(numberOfPhotos_Et.getText().toString()));

        viewModel.setFilter(filter);
        dismiss();
    }
}
