package com.openclassrooms.realestatemanager.ui.filter;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.clickedListener_interfaces.OnChipClickedListener;
import com.openclassrooms.realestatemanager.models.Currency;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.repositories.Constants;
import com.openclassrooms.realestatemanager.ui.utils.SharedCurrencyViewModel;
import com.openclassrooms.realestatemanager.ui.utils.SharedPropertiesViewModel;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class FilterFragment extends Fragment implements OnChipClickedListener {
    SharedPreferences sharedPreferences;
    private SharedPropertiesViewModel sharedPropertiesViewModel;
    private SharedCurrencyViewModel sharedCurrencyViewModel;
    private FilterFragmentViewModel filterFragmentViewModel;
    private View root;
    private static Calendar calendar = Calendar.getInstance();

    @BindView(R.id.fragment_filter_cities_textInputEditText)
    EditText citiesEt;
    @BindView(R.id.fragment_filter_state_textInputEditText)
    EditText statesEt;
    @BindView(R.id.fragment_filter_area_slidebar)
    CrystalRangeSeekbar areaSlidebar;
    @BindView(R.id.fragment_filter_area_aeraMin_txt)
    TextView areaMinTxt;
    @BindView(R.id.fragment_filter_area_aeraMax_txt)
    TextView areaMaxTxt;
    @BindView(R.id.fragment_filter_pieces_numberMin_txt)
    TextView piecesMinTxt;
    @BindView(R.id.fragment_filter_pieces_numberMax_txt)
    TextView piecesMaxTxt;
    @BindView(R.id.fragment_filter_interestPoints_textInputEditText)
    EditText interestPointsEt;
    @BindView(R.id.fragment_filter_agent_textInputEditText)
    EditText agentEt;
    @BindView(R.id.fragment_filter_availability_status_dropdown)
    AutoCompleteTextView statusTv;
    @BindView(R.id.fragment_filter_availability_date_textInputEditText)
    EditText availableDateEt;
    @BindView(R.id.fragment_filter_sold_date_textInputEditText)
    EditText soldDateEt;
    @BindView(R.id.fragment_filter_numberOfphotos_numberMin_txt)
    TextView numberOfPhotoMinTxt;
    @BindView(R.id.fragment_filter_numberOfphotos_numberMax_txt)
    TextView numberOfPhotoMaxTxt;
    @BindView(R.id.fragment_filter_types_chips_recyclerView)
    RecyclerView typesChipsRv;
    @BindView(R.id.fragment_filter_price_slidebar)
    CrystalRangeSeekbar priceSlidebar;
    @BindView(R.id.fragment_filter_pieces_slidebar)
    CrystalRangeSeekbar piecesSlidebar;
    @BindView(R.id.fragment_filter_numberOfPhotos_slider)
    CrystalRangeSeekbar numberOfPhotosSlidebar;
    @BindView(R.id.fragment_filter_price_amoutMin_txt)
    TextView priceMinTv;
    @BindView(R.id.fragment_filter_price_amountMax_txt)
    TextView priceMaxTv;

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("PREFERENCES", MODE_PRIVATE);
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
        sharedPropertiesViewModel = new ViewModelProvider(requireActivity()).get(SharedPropertiesViewModel.class);
        sharedCurrencyViewModel = new ViewModelProvider(requireActivity()).get(SharedCurrencyViewModel.class);
        filterFragmentViewModel = new ViewModelProvider(this).get(FilterFragmentViewModel.class);

        types();
        status();
        soldeDate();
        availabilityDate();
        priceSlider();
        areaSlider();
        piecesSlider();
        numberOfPhotosSlider();
        loadSavedFilters();
    }

    private void loadSavedFilters() {
        loadTypesChips();
        citiesEt.setText(sharedPreferences.getString("cities", ""));
        statesEt.setText(sharedPreferences.getString("states", ""));
        interestPointsEt.setText(sharedPreferences.getString("interestPoints", ""));
        agentEt.setText(sharedPreferences.getString("agent", ""));
        statusTv.setText(sharedPreferences.getString("status", ""));
        availableDateEt.setText(sharedPreferences.getString("available_date",""));
        soldDateEt.setText(sharedPreferences.getString("sold_date",""));
        loadPricesSlider();
        loadAreaSlider();
        loadPiecesSlider();
        loadNumberOfPhotosSlider();
    }

    private void loadNumberOfPhotosSlider() {
        filterFragmentViewModel.numberOfPhotosMin.setValue(sharedPreferences.getInt("numberOfPhotos_mini", 0));
        filterFragmentViewModel.numberOfPhotosMax.setValue(sharedPreferences.getInt("numberOfPhotos_maxi", Constants.sliderPhotosMaximum));
        numberOfPhotosSlidebar.setMinStartValue(sharedPreferences.getInt("numberOfPhotos_mini", 0));
        numberOfPhotosSlidebar.setMaxStartValue(sharedPreferences.getInt("numberOfPhotos_maxi", Constants.sliderPhotosMaximum));

    }

    private void loadPiecesSlider() {
        filterFragmentViewModel.piecesMin.setValue(sharedPreferences.getInt("pieces_mini", Constants.sliderPiecesMinimum));
        filterFragmentViewModel.piecesMax.setValue(sharedPreferences.getInt("pieces_maxi", Constants.sliderPiecesMaximum));
        piecesSlidebar.setMinStartValue(sharedPreferences.getInt("pieces_mini", Constants.sliderPiecesMinimum));
        piecesSlidebar.setMaxStartValue(sharedPreferences.getInt("pieces_maxi", Constants.sliderPiecesMaximum));

    }

    private void loadAreaSlider() {
        filterFragmentViewModel.areaMin.setValue(sharedPreferences.getInt("area_mini", Constants.sliderAreaMinimum));
        filterFragmentViewModel.areaMax.setValue(sharedPreferences.getInt("area_maxi", Constants.sliderAreaMaximum));
        areaSlidebar.setMinStartValue(sharedPreferences.getInt("area_mini", Constants.sliderAreaMinimum));
        areaSlidebar.setMaxStartValue(sharedPreferences.getInt("area_maxi", Constants.sliderAreaMaximum));

        }

    private void loadPricesSlider() {
        filterFragmentViewModel.priceMin.setValue(sharedPreferences.getInt("price_mini", Constants.sliderPriceMinimum));
        filterFragmentViewModel.priceMax.setValue(sharedPreferences.getInt("price_maxi", Constants.sliderPriceMaximum));
        priceSlidebar.setMinStartValue(sharedPreferences.getInt("price_mini", Constants.sliderPriceMinimum));
        priceSlidebar.setMaxStartValue(sharedPreferences.getInt("price_maxi", Constants.sliderPriceMaximum));
        filterFragmentViewModel.priceBoundMin.postValue(Constants.sliderPriceMinimum);
        filterFragmentViewModel.priceBoundMax.postValue(Constants.sliderPriceMaximum);
    }


    private void loadTypesChips() {
        String isSelected_prefs = sharedPreferences.getString("types", "");
        if (!isSelected_prefs.isEmpty()) {
            List<Type> typesFilter = filterFragmentViewModel.getTypesFilterPrefs(isSelected_prefs);
            filterFragmentViewModel.typesFilter.postValue(typesFilter);
        }
    }

    private void soldeDate() {
        TextInputEditText soldeDate = root.findViewById(R.id.fragment_filter_sold_date_textInputEditText);

        soldeDate.setOnClickListener(v -> datePickerClick((view, year, month, dayOfMonth) ->
                filterFragmentViewModel.setSoldDate(year, month, dayOfMonth)
        ));
        filterFragmentViewModel.soldDate.observe(getViewLifecycleOwner(), soldeDate::setText);
    }

    private void availabilityDate() {
        TextInputEditText availabilityDate = root.findViewById(R.id.fragment_filter_availability_date_textInputEditText);

        availabilityDate.setOnClickListener(v -> datePickerClick((view, year, month, dayOfMonth) ->
                filterFragmentViewModel.setAvailableDate(year, month, dayOfMonth)
        ));
        filterFragmentViewModel.entryDate.observe(getViewLifecycleOwner(), availabilityDate::setText);
    }

    private void datePickerClick(DatePickerDialog.OnDateSetListener listener) {

        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void areaSlider() {

        filterFragmentViewModel.areaMin.observe(getViewLifecycleOwner(), integer ->
                {
                    areaMinTxt.setText(integer + " m²");
                    if (areaSlidebar.getSelectedMinValue().intValue() != integer) {
                        areaSlidebar.setMinStartValue(integer).apply();
                    }
                }
        );

        filterFragmentViewModel.areaMax.observe(getViewLifecycleOwner(), integer ->
                {
                    areaMaxTxt.setText(integer + " m²");
                    if (areaSlidebar.getSelectedMaxValue().intValue() != integer) {
                        areaSlidebar.setMaxStartValue(integer).apply();
                    }
                }
        );

        areaSlidebar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            filterFragmentViewModel.areaMin.setValue(minValue.intValue());
            filterFragmentViewModel.areaMax.setValue(maxValue.intValue());

        });
    }

    private void priceSlider() {

        filterFragmentViewModel.priceMin.observe(getViewLifecycleOwner(), integer ->
        {
            if (sharedCurrencyViewModel.currency.getValue() == Currency.EUROS) {
                priceMinTv.setText(integer + " €");
            } else {
                priceMinTv.setText("$ " + integer);
            }
            if (priceSlidebar.getSelectedMinValue().intValue() != integer) {
                priceSlidebar.setMinStartValue(integer).apply();
            }
        });

        filterFragmentViewModel.priceMax.observe(getViewLifecycleOwner(), integer ->
        {
            if (sharedCurrencyViewModel.currency.getValue() == Currency.EUROS) {
                priceMaxTv.setText(integer + " €");
            } else {
                priceMaxTv.setText("$ " + integer);
            }
            if (priceSlidebar.getSelectedMaxValue().intValue() != integer) {
                priceSlidebar.setMaxStartValue(integer).apply();
            }
        });

        priceSlidebar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            filterFragmentViewModel.priceMin.setValue(minValue.intValue());
            filterFragmentViewModel.priceMax.setValue(maxValue.intValue());

        });
    }

    private void piecesSlider() {

        filterFragmentViewModel.piecesMin.observe(getViewLifecycleOwner(), integer ->
        {
            piecesMinTxt.setText(integer.toString());
            if (piecesSlidebar.getSelectedMinValue().intValue() != integer) {
                piecesSlidebar.setMinStartValue(integer).apply();
            }
        });


        filterFragmentViewModel.piecesMax.observe(getViewLifecycleOwner(), integer ->
        {
            piecesMaxTxt.setText(integer.toString());
            if (piecesSlidebar.getSelectedMaxValue().intValue() != integer) {
                piecesSlidebar.setMaxStartValue(integer).apply();
            }
        });

        piecesSlidebar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            filterFragmentViewModel.piecesMin.setValue(minValue.intValue());
            filterFragmentViewModel.piecesMax.setValue(maxValue.intValue());

        });
    }

    private void numberOfPhotosSlider() {
        filterFragmentViewModel.numberOfPhotosMin.observe(getViewLifecycleOwner(), integer ->
        {
            numberOfPhotoMinTxt.setText(integer.toString());
            if (numberOfPhotosSlidebar.getSelectedMinValue().intValue() != integer) {
                numberOfPhotosSlidebar.setMinStartValue(integer).apply();
            }
        });


        filterFragmentViewModel.numberOfPhotosMax.observe(getViewLifecycleOwner(), integer ->
        {
            numberOfPhotoMaxTxt.setText(integer.toString());
            if (numberOfPhotosSlidebar.getSelectedMaxValue().intValue() != integer) {
                numberOfPhotosSlidebar.setMaxStartValue(integer).apply();
            }
        });

        numberOfPhotosSlidebar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            filterFragmentViewModel.numberOfPhotosMin.setValue(minValue.intValue());
            filterFragmentViewModel.numberOfPhotosMax.setValue(maxValue.intValue());
        });
    }

    private void types() {

        filterFragmentViewModel.initTypesFilter();
        typesChipsRv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        typesChipsRv.setLayoutManager(layoutManager);

        filterFragmentViewModel.typesFilter.observe(getViewLifecycleOwner(),typesFilter->{
            if(typesFilter!=null){
                TypesChipsAdapter typesChipsAdapter = new TypesChipsAdapter(typesFilter, getActivity(), FilterFragment.this);
                typesChipsRv.setAdapter(typesChipsAdapter);
            }
        });
    }

    private void status() {
        final String[] AVAILABILITY = filterFragmentViewModel.getAvailabilities();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, AVAILABILITY);
        AutoCompleteTextView textView = root.findViewById(R.id.fragment_filter_availability_status_dropdown);
        textView.setAdapter(adapter);
        textView.setCursorVisible(false);
    }

    @OnClick(R.id.fragment_filter_validate_fab)
    public void filterValidate() {
        filterFragmentViewModel.validate(
                citiesEt.getText().toString(),
                statesEt.getText().toString(),
                interestPointsEt.getText().toString(),
                agentEt.getText().toString(),
                availableDateEt.getText().toString(),
                soldDateEt.getText().toString(),
                statusTv.getText().toString(),
                sharedPropertiesViewModel,
                sharedCurrencyViewModel.currency.getValue()
        );

                sharedPreferences
                        .edit()
                        .putString("types", filterFragmentViewModel.getTypeInString())
                        .putInt("price_mini", priceSlidebar.getSelectedMinValue().intValue())
                        .putInt("price_maxi", priceSlidebar.getSelectedMaxValue().intValue())
                        .putString("cities", citiesEt.getText().toString())
                        .putString("states", statesEt.getText().toString())
                        .putInt("area_mini", areaSlidebar.getSelectedMinValue().intValue())
                        .putInt("area_maxi", areaSlidebar.getSelectedMaxValue().intValue())
                        .putInt("pieces_mini", piecesSlidebar.getSelectedMinValue().intValue())
                        .putInt("pieces_maxi", piecesSlidebar.getSelectedMaxValue().intValue())
                        .putString("interestPoints", interestPointsEt.getText().toString())
                        .putString("agent", agentEt.getText().toString())
                        .putString("status", statusTv.getText().toString())
                        .putString("available_date", availableDateEt.getText().toString())
                        .putString("sold_date", soldDateEt.getText().toString())
                        .putInt("numberOfPhotos_mini", numberOfPhotosSlidebar.getSelectedMinValue().intValue())
                        .putInt("numberOfPhotos_maxi", numberOfPhotosSlidebar.getSelectedMaxValue().intValue())
                        .apply();

        getActivity().onBackPressed();
            }

    @OnClick(R.id.fragment_filter_removeFilters_bt)
    public void removeFilters(){
        sharedPropertiesViewModel.isFiltered = false;
        filterFragmentViewModel.initTypesFilter();
        sharedPreferences.edit().clear().apply();
        FilterFragment filterFragment = new FilterFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this);
        Utils.addFragmentInDetailScreen(getActivity(),filterFragment);
    }

    @Override
    public void onChipClicked(int position, boolean selected) {
        filterFragmentViewModel.setType(position, selected);
    }
}