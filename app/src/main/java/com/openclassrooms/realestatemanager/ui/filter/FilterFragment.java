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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.clickedListener_interfaces.OnChipClickedListener;
import com.openclassrooms.realestatemanager.models.Filter;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.repositories.Constants;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.apptik.widget.MultiSlider;

import static android.content.Context.MODE_PRIVATE;


public class FilterFragment extends Fragment implements OnChipClickedListener {
    SharedPreferences sharedPreferences;
    private SharedFilterViewModel sharedFilterViewModel;
    private FilterFragmentViewModel filterFragmentViewModel;
    private View root;
    private static Calendar calendar = Calendar.getInstance();

    @BindView(R.id.fragment_filter_cities_textInputEditText)
    EditText cities_Et;
    @BindView(R.id.fragment_filter_state_textInputEditText)
    EditText states_Et;
    @BindView(R.id.fragment_filter_area_slidebar)
    CrystalRangeSeekbar area_slidebar;
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
    AutoCompleteTextView status_tv;
    @BindView(R.id.fragment_filter_availability_date_textInputEditText)
    EditText availableDate_Et;
    @BindView(R.id.fragment_filter_sold_date_textInputEditText)
    EditText soldDate_Et;
    @BindView(R.id.fragment_filter_numberOfPhotos_multiSlider)
    MultiSlider numberOfPhotos_multiSlider;
    @BindView(R.id.fragment_filter_numberOfphotos_numberMin_txt)
    TextView numberOfPhotoMin_txt;
    @BindView(R.id.fragment_filter_numberOfphotos_numberMax_txt)
    TextView numberOfPhotoMax_txt;
    @BindView(R.id.fragment_filter_types_chips_recyclerView)
    RecyclerView types_chips_rv;
    @BindView(R.id.fragment_filter_price_slidebar)
    CrystalRangeSeekbar price_slidebar;
    @BindView(R.id.fragment_filter_price_amoutMin_txt)
    TextView priceMin_tv;
    @BindView(R.id.fragment_filter_price_amountMax_txt)
    TextView priceMax_tv;
    @BindView(R.id.fragment_filter_toolbar)
    Toolbar toolbar;

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
        sharedFilterViewModel = new ViewModelProvider(requireActivity()).get(SharedFilterViewModel.class);
        filterFragmentViewModel = new ViewModelProvider(this).get(FilterFragmentViewModel.class);

        toolBar();
        types();
        status();
        soldeDate();
        availabilityDate();
        priceSlider();
        areaSlider();
        piecesSlider();
        numberOfPhotosSlider();
        load_savedFilters();
    }

    private void load_savedFilters() {
        load_types_chips();
        cities_Et.setText(sharedPreferences.getString("cities", ""));
        states_Et.setText(sharedPreferences.getString("states", ""));
        interestPoints_Et.setText(sharedPreferences.getString("interestPoints", ""));
        agent_Et.setText(sharedPreferences.getString("agent", ""));
        status_tv.setText(sharedPreferences.getString("status", ""));
        availableDate_Et.setText(sharedPreferences.getString("available_date",""));
        soldDate_Et.setText(sharedPreferences.getString("sold_date",""));
        load_prices_slider();
        load_area_slider();

        piecesMultiSlider.getThumb(0).setValue(sharedPreferences.getInt("pieces_mini",Constants.slider_price_minimum));
        piecesMultiSlider.getThumb(1).setValue(sharedPreferences.getInt("pieces_maxi",Constants.slider_price_maximum));
        numberOfPhotos_multiSlider.getThumb(0).setValue(sharedPreferences.getInt("numberPhotos_mini",Constants.slider_price_minimum));
        numberOfPhotos_multiSlider.getThumb(1).setValue(sharedPreferences.getInt("numberPhotos_maxi",Constants.slider_price_maximum));

    }

    private void load_area_slider() {
        filterFragmentViewModel.areaMin.setValue(sharedPreferences.getInt("area_mini", Constants.slider_area_minimum));
        filterFragmentViewModel.areaMax.setValue(sharedPreferences.getInt("area_maxi", Constants.slider_area_maximum));
        area_slidebar.setMinStartValue(sharedPreferences.getInt("area_mini", Constants.slider_area_minimum));
        area_slidebar.setMaxStartValue(sharedPreferences.getInt("area_maxi", Constants.slider_area_maximum));

        }

    private void load_prices_slider() {
        filterFragmentViewModel.priceMin.setValue(sharedPreferences.getInt("price_mini", Constants.slider_price_minimum));
        filterFragmentViewModel.priceMax.setValue(sharedPreferences.getInt("price_maxi", Constants.slider_price_maximum));
        price_slidebar.setMinStartValue(sharedPreferences.getInt("price_mini", Constants.slider_price_minimum));
        price_slidebar.setMaxStartValue(sharedPreferences.getInt("price_maxi", Constants.slider_price_maximum));
    }


    private void load_types_chips() {
        String isSelected_prefs = sharedPreferences.getString("types", "");
        if (!isSelected_prefs.isEmpty()) {
            List<Type> typesFilter = filterFragmentViewModel.getTypesFilter_prefs(isSelected_prefs);
            filterFragmentViewModel.typesFilter.postValue(typesFilter);
        }
    }

    private void soldeDate() {
        TextInputEditText soldeDate = root.findViewById(R.id.fragment_filter_sold_date_textInputEditText);

        soldeDate.setOnClickListener(v -> date_picker_click((view, year, month, dayOfMonth) ->
                filterFragmentViewModel.setSoldDate(year, month, dayOfMonth)
        ));
        filterFragmentViewModel.soldDate.observe(getViewLifecycleOwner(), soldeDate::setText);
    }

    private void availabilityDate() {
        TextInputEditText availabilityDate = root.findViewById(R.id.fragment_filter_availability_date_textInputEditText);

        availabilityDate.setOnClickListener(v -> date_picker_click((view, year, month, dayOfMonth) ->
                filterFragmentViewModel.setAvailableDate(year, month, dayOfMonth)
        ));
        filterFragmentViewModel.entryDate.observe(getViewLifecycleOwner(), availabilityDate::setText);
    }

    private void date_picker_click(DatePickerDialog.OnDateSetListener listener) {

        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void piecesSlider() {

        piecesMultiSlider.setMin(Constants.slider_pieces_minimum);
        piecesMultiSlider.setMax(Constants.slider_pieces_maximum);
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

    private void numberOfPhotosSlider() {
        numberOfPhotos_multiSlider.setMin(0);
        numberOfPhotos_multiSlider.setMax(Constants.slider_photos_maximum);
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

    private void areaSlider() {

        filterFragmentViewModel.areaMin.observe(getViewLifecycleOwner(), integer ->
                {
                    areaMin_txt.setText(integer + " m²");
                    if (area_slidebar.getSelectedMinValue().intValue() != integer) {
                        area_slidebar.setMinStartValue(integer).apply();
                    }
                }
        );

        filterFragmentViewModel.areaMax.observe(getViewLifecycleOwner(), integer ->
                {
                    areaMax_txt.setText(integer + " m²");
                    if (area_slidebar.getSelectedMaxValue().intValue() != integer) {
                        area_slidebar.setMaxStartValue(integer).apply();
                    }
                }
        );

        area_slidebar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            filterFragmentViewModel.areaMin.setValue(minValue.intValue());
            filterFragmentViewModel.areaMax.setValue(maxValue.intValue());

        });
    }

    private void priceSlider() {

        filterFragmentViewModel.priceMin.observe(getViewLifecycleOwner(), integer ->
        {
            priceMin_tv.setText(integer + " €");
            if (price_slidebar.getSelectedMinValue().intValue() != integer) {
                price_slidebar.setMinStartValue(integer).apply();
            }
        });


        filterFragmentViewModel.priceMax.observe(getViewLifecycleOwner(), integer ->
        {
            priceMax_tv.setText(integer + " €");
            if (price_slidebar.getSelectedMaxValue().intValue() != integer) {
                price_slidebar.setMaxStartValue(integer).apply();
            }
        });

        price_slidebar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            filterFragmentViewModel.priceMin.setValue(minValue.intValue());
            filterFragmentViewModel.priceMax.setValue(maxValue.intValue());

        });
    }

    private void types() {

        filterFragmentViewModel.initTypesFilter();
        types_chips_rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        types_chips_rv.setLayoutManager(layoutManager);

        filterFragmentViewModel.typesFilter.observe(getViewLifecycleOwner(),typesFilter->{
            if(typesFilter!=null){
                TypesChipsAdapter typesChipsAdapter = new TypesChipsAdapter(typesFilter, getActivity(), FilterFragment.this);
                types_chips_rv.setAdapter(typesChipsAdapter);
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
    public void filter_validate() {
// filterFragmentViewModel.validate();

        Filter filter = new Filter(
                filterFragmentViewModel.getTypesFilter(),
                filterFragmentViewModel.priceMax.getValue(),
                filterFragmentViewModel.priceMin.getValue(),
                filterFragmentViewModel.getFilterListInForm(cities_Et.getText().toString()),
                filterFragmentViewModel.getFilterListInForm(states_Et.getText().toString()),
                filterFragmentViewModel.areaMax.getValue(),
                filterFragmentViewModel.areaMin.getValue(),
                piecesMultiSlider.getThumb(1).getValue(),
                piecesMultiSlider.getThumb(0).getValue(),
                filterFragmentViewModel.getFilterListInForm(interestPoints_Et.getText().toString()),
                filterFragmentViewModel.getFilterListInForm(agent_Et.getText().toString()),
                statusFilter(),
                availableDate_Et.getText().toString(),
                soldDate_Et.getText().toString(),
                numberOfPhotos_multiSlider.getThumb(1).getValue(),
                numberOfPhotos_multiSlider.getThumb(0).getValue());

        filterFragmentViewModel.applyFilter(filter, sharedFilterViewModel);


                sharedPreferences
                        .edit()
                        .putString("types", filterFragmentViewModel.getTypeInString())
                        .putInt("price_mini", filter.getPriceMini())
                        .putInt("price_maxi", filter.getPriceMaxi())
                        .putString("cities", cities_Et.getText().toString())
                        .putString("states", states_Et.getText().toString())
                        .putInt("area_mini", filter.getAreaMini())
                        .putInt("area_maxi", filter.getAreaMaxi())
                        .putInt("pieces_mini", piecesMultiSlider.getThumb(0).getValue())
                        .putInt("pieces_maxi", piecesMultiSlider.getThumb(1).getValue())
                        .putString("interestPoints", interestPoints_Et.getText().toString())
                        .putString("agent", agent_Et.getText().toString())
                        .putString("status", status_tv.getText().toString())
                        .putString("available_date", availableDate_Et.getText().toString())
                        .putString("sold_date", soldDate_Et.getText().toString())
                        .putInt("numberPhotos_mini", numberOfPhotos_multiSlider.getThumb(0).getValue())
                        .putInt("numberPhotos_maxi", numberOfPhotos_multiSlider.getThumb(1).getValue())
                        .apply();

                //getActivity().onBackPressed();
                Utils.backToMainScreen(getActivity(), this);
            }

    @OnClick(R.id.fragment_filter_removeFilters_bt)
    public void removeFilters(){
        sharedFilterViewModel.isFiltred = false;
        filterFragmentViewModel.initTypesFilter();
        sharedPreferences.edit().clear().apply();
        FilterFragment filterFragment = new FilterFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        final boolean tabletSize = getActivity().getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            transaction.detach(this);
            transaction.replace(R.id.activity_main_frame_layout_detail_large_screen, filterFragment).commit();
        } else {
            transaction.detach(this);
            transaction.replace(R.id.frame_layout_main, filterFragment).commit();
        }
    }

    private Status statusFilter() {
        Status status;

        switch (status_tv.getText().toString()) {
            case "Sold": {
                status = Status.SOLD;
            }
            break;
            case "Available": {
                status = Status.AVAILABLE;
            }
            break;
            default: {
                status = Status.UNSPECIFIED;
            }
        }
        return status;
    }

    private void toolBar() {
        toolbar.setTitle("Filter properties");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> Utils.backToMainScreen(getActivity(),this));
    }

    @Override
    public void onChipClicked(int position, boolean selected) {
        filterFragmentViewModel.setType(position, selected);
    }
}