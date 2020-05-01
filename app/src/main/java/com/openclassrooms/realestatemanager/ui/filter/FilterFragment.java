package com.openclassrooms.realestatemanager.ui.filter;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Filter;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.apptik.widget.MultiSlider;

public class FilterFragment extends Fragment {
    private SharedPropertyViewModel sharedPropertyViewModel;
    private View root;
    private static Calendar calendar = Calendar.getInstance();

  /*  @BindView(R.id.fragment_filter_type_0_chip)
    Chip chip_type_0;
    @BindView(R.id.fragment_filter_type_1_chip)
    Chip chip_type_1;
    @BindView(R.id.fragment_filter_type_2_chip)
    Chip chip_type_2;
    @BindView(R.id.fragment_filter_type_3_chip)
    Chip chip_type_3;
    @BindView(R.id.fragment_filter_type_4_chip)
    Chip chip_type_4;*/
    @BindView(R.id.fragment_filter_price_seekbar)
    MultiSlider price_MultiSlider;
    @BindView(R.id.fragment_filter_price_amoutMin_txt)
    TextView priceMin_txt;
    @BindView(R.id.fragment_filter_price_amountMax_txt)
    TextView priceMax_txt;
    @BindView(R.id.fragment_filter_cities_textInputEditText)
    EditText cities_Et;
    @BindView(R.id.fragment_filter_state_textInputEditText)
    EditText states_Et;
    @BindView(R.id.fragment_filter_area_multiSlider)
    MultiSlider areaMultislider;
    @BindView(R.id.fragment_filter_area_aeraMin_txt)
    TextView areaMin_txt;
    @BindView(R.id.fragment_filter_area_aeraMax_txt)
    TextView areaMax_txt;
    @BindView(R.id.fragment_filter_pieces_multiSlider)
    MultiSlider piecesMultiSlider;
    @BindView(R.id.fragment_filter_pieces_numberMin_txt)
    TextView numberOfPiecesMin_txt;
    @BindView(R.id.fragment_filter_pieces_numberMax_txt)
    TextView numberOfPiecesMax_txt;
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
    @BindView(R.id.fragment_filter_numberOfPhotos_multiSlider)
    MultiSlider numberOfPhotos_multiSlider;
    @BindView(R.id.fragment_filter_numberOfphotos_numberMin_txt)
    TextView numberOfPhotoMin_txt;
    @BindView(R.id.fragment_filter_numberOfphotos_numberMax_txt)
    TextView numberOfPhotoMax_txt;
    @BindView(R.id.fragment_filter_types_chips_recyclerView)
    RecyclerView types_chips_rv;


    public static FilterFragment newInstance() {
        return new FilterFragment();
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
        sharedPropertyViewModel = new ViewModelProvider(requireActivity()).get(SharedPropertyViewModel.class);

        configureType();
        configureStatus();
        configure_soldeDate();
        configure_availabilityDate();
        priceMultiSlider();
        areaMultiSlider();
        piecesMultiSlider();
        numberOfPhotos_multislider();

    }

    private void configure_soldeDate() {
        TextInputEditText soldeDate = root.findViewById(R.id.fragment_filter_sold_date_textInputEditText);

        soldeDate.setOnClickListener(v -> date_picker_click((view, year, month, dayOfMonth) ->
                sharedPropertyViewModel.setSoldDate(year, month, dayOfMonth)
        ));
        sharedPropertyViewModel.soldDate.observe(getViewLifecycleOwner(), soldeDate::setText
        );
    }

    private void configure_availabilityDate() {
        TextInputEditText availabilityDate = root.findViewById(R.id.fragment_filter_availability_date_textInputEditText);

        availabilityDate.setOnClickListener(v -> date_picker_click((view, year, month, dayOfMonth) ->
                sharedPropertyViewModel.setAvailableDate(year, month, dayOfMonth)
        ));
        sharedPropertyViewModel.entryDate.observe(getViewLifecycleOwner(), availabilityDate::setText
        );
    }

    public void date_picker_click(DatePickerDialog.OnDateSetListener listener) {

        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void piecesMultiSlider() {
        piecesMultiSlider.setMin(1);
        piecesMultiSlider.setMax(30);
        piecesMultiSlider.setStep(1);
        piecesMultiSlider.setStepsThumbsApart(0);
        piecesMultiSlider.removeThumb(1);
        piecesMultiSlider.addThumb(piecesMultiSlider.getMax());
        numberOfPiecesMin_txt.setText(String.valueOf(piecesMultiSlider.getMin()));
        numberOfPiecesMax_txt.setText(String.valueOf(piecesMultiSlider.getMax()));
        piecesMultiSlider.setOnThumbValueChangeListener((multiSlider, thumb, thumbIndex, value) -> {
            if (thumbIndex == 0) {
                numberOfPiecesMin_txt.setText(String.valueOf(value));
            }
            if (thumbIndex == 1) {
                numberOfPiecesMax_txt.setText(String.valueOf(value));
            }

        });
    }

    private void numberOfPhotos_multislider() {
        numberOfPhotos_multiSlider.setMin(0);
        numberOfPhotos_multiSlider.setMax(50);
        numberOfPhotos_multiSlider.setStep(1);
        numberOfPhotos_multiSlider.setStepsThumbsApart(0);
        numberOfPhotos_multiSlider.removeThumb(1);
        numberOfPhotos_multiSlider.addThumb(numberOfPhotos_multiSlider.getMax());
        numberOfPhotoMin_txt.setText(String.valueOf(numberOfPhotos_multiSlider.getMin()));
        numberOfPhotoMax_txt.setText(String.valueOf(numberOfPhotos_multiSlider.getMax()));

        numberOfPhotos_multiSlider.setOnThumbValueChangeListener((multiSlider, thumb, thumbIndex, value) -> {
            if (thumbIndex == 0) {
                numberOfPhotoMin_txt.setText(String.valueOf(value));
            }
            if (thumbIndex == 1) {
                numberOfPhotoMax_txt.setText(String.valueOf(value));
            }

        });
    }

    private void areaMultiSlider() {
        areaMultislider.setMin(10);
        areaMultislider.setMax(1000);
        areaMultislider.setStep(10);
        areaMultislider.setStepsThumbsApart(0);
        areaMultislider.removeThumb(1);
        areaMultislider.addThumb(areaMultislider.getMax());
        areaMin_txt.setText(areaMultislider.getMin() + " m²");
        areaMax_txt.setText((areaMultislider.getMax() + " m²"));

        areaMultislider.setOnThumbValueChangeListener((multiSlider, thumb, thumbIndex, value) -> {
            if (thumbIndex == 0) {
                areaMin_txt.setText(value + " m²");
            }
            if (thumbIndex == 1) {
                areaMax_txt.setText(value + " m²");
            }

        });
    }

    private void priceMultiSlider() {

        price_MultiSlider.setMin(0);
        price_MultiSlider.setMax(100000);
        price_MultiSlider.setStep(100);
        price_MultiSlider.setStepsThumbsApart(0);
        price_MultiSlider.removeThumb(1);
        price_MultiSlider.addThumb(price_MultiSlider.getMax());
        priceMin_txt.setText(price_MultiSlider.getMin() + " €");
        priceMax_txt.setText(price_MultiSlider.getMax() + " €");

        price_MultiSlider.setOnThumbValueChangeListener((multiSlider, thumb, thumbIndex, value) -> {

            if (thumbIndex == 0) {
                priceMin_txt.setText(value + " €");
            }
            if (thumbIndex == 1) {
                priceMax_txt.setText(value + " €");
            }

        });
    }

    private void configureType() {

        types_chips_rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        types_chips_rv.setLayoutManager(layoutManager);

        if (sharedPropertyViewModel.getTYPES().length >0) {
            TypesChipsAdapter typesChipsAdapter = new TypesChipsAdapter(sharedPropertyViewModel.getTYPES(), getActivity());
            types_chips_rv.setAdapter(typesChipsAdapter);
        }
        /*
        String[] TYPES = sharedPropertyViewModel.getTYPES();
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
            chip_type_4.setVisibility(View.VISIBLE);*/
        }


    private void configureStatus() {
        final String[] AVAILABILITY = sharedPropertyViewModel.getAvailabilities();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, AVAILABILITY);
        AutoCompleteTextView textView = root.findViewById(R.id.fragment_filter_availability_status_dropdown);
        textView.setAdapter(adapter);
        textView.setCursorVisible(false);
    }

    @OnClick(R.id.fragment_search_validate_fab)
    public void filter_validate() {
        Log.i("tag_price ","mini "+price_MultiSlider.getThumb(1).getValue());
        Log.i("tag_price", "maxi "+price_MultiSlider.getThumb(0).getValue());

        ArrayList empty_for_test = new ArrayList();

        Filter filter = new Filter(
                typesFilter(),
                price_MultiSlider.getThumb(1).getValue(),
                price_MultiSlider.getThumb(0).getValue(),
                sharedPropertyViewModel.getFilter(cities_Et.getText().toString()),
                sharedPropertyViewModel.getFilter(states_Et.getText().toString()),
                areaMultislider.getThumb(1).getValue(),
                areaMultislider.getThumb(0).getValue(),
                piecesMultiSlider.getThumb(1).getValue(),
                piecesMultiSlider.getThumb(0).getValue(),
                sharedPropertyViewModel.getFilter(interestPoints_Et.getText().toString()),
                sharedPropertyViewModel.getFilter(agent_Et.getText().toString()),
                isSoldedFilter(),
                availableDate_Et.getText().toString(),
                soldeDate_Et.getText().toString(),
                numberOfPhotos_multiSlider.getThumb(1).getValue(),
                numberOfPhotos_multiSlider.getThumb(0).getValue());

        sharedPropertyViewModel.setFilter(filter);

        //dismiss();

    }

    private boolean isSoldedFilter() {
        boolean isSolded;
        if (isSolded_tv.getText().toString().equals(R.string.sold)) {
            isSolded = true;
        } else {
            isSolded = false;
        }
        return isSolded;
    }

    private ArrayList<String> typesFilter() {
        String[] TYPES = sharedPropertyViewModel.getTYPES();
        ArrayList<String> type = new ArrayList<>();

   /*     if (chip_type_0.isChecked()) {
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
        }*/
        return type;
    }
}