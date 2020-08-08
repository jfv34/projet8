package com.openclassrooms.realestatemanager.ui.editProperty;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.clickedListener_interfaces.OnPhotoDeleteClickedListener;
import com.openclassrooms.realestatemanager.clickedListener_interfaces.OnPhotoDescriptionClickedListener;
import com.openclassrooms.realestatemanager.models.Currency;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.ui.utils.SharedCurrencyViewModel;
import com.openclassrooms.realestatemanager.ui.utils.SharedPropertiesViewModel;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class FormPropertyFragment extends Fragment implements OnPhotoDeleteClickedListener, OnPhotoDescriptionClickedListener {

    private static Calendar calendar = Calendar.getInstance();
    private int bundlePropertyId;
    private FormPropertyFragmentViewModel viewModel;
    private SharedPropertiesViewModel sharedPropertiesViewModel;
    private SharedCurrencyViewModel sharedCurrencyViewModel;
    private View root;
    private Bitmap photoBM = null;

    @BindView(R.id.fragment_insert_property_photos_recyclerView)
    RecyclerView photosRecyclerView;
    @BindView(R.id.fragment_insert_property_TextField_type)
    TextInputLayout propertyType;
    @BindView(R.id.fragment_insert_property_TextField_price)
    TextInputLayout propertyPriceTextInputLayout;
    @BindView(R.id.fragment_insert_property_price_textInputEditText)
    TextInputEditText propertyPriceTextInputEditText;
    @BindView(R.id.fragment_insert_property_TextField_address)
    TextInputLayout propertyAddress;
    @BindView(R.id.fragment_insert_property_TextField_city)
    TextInputLayout propertyCity;
    @BindView(R.id.fragment_insert_property_TextField_state)
    TextInputLayout propertyState;
    @BindView(R.id.fragment_insert_property_TextField_zip)
    TextInputLayout propertyZip;
    @BindView(R.id.fragment_insert_property_TextField_area)
    TextInputLayout propertyArea;
    @BindView(R.id.fragment_insert_property_TextField_pieces)
    TextInputLayout propertyPieces;
    @BindView(R.id.fragment_insert_property_TextField_interestPoints)
    TextInputLayout propertyInterestPoints;
    @BindView(R.id.fragment_insert_property_TextField_description)
    TextInputLayout propertyDescription;
    @BindView(R.id.fragment_insert_property_TextField_agent)
    TextInputLayout propertyAgent;
    @BindView(R.id.fragment_form_property_availability_status)
    TextInputLayout propertyAvailabilityStatus;
    @BindView(R.id.fragment_form_property_availability_dropdown)
    AutoCompleteTextView propertyAvailabilityDropdown;
    @BindView(R.id.fragment_form_property_availability_date)
    TextInputLayout propertyEntryDate;
    @BindView(R.id.fragment_form_property_sold_date)
    TextInputLayout propertySoldDate;

    public static FormPropertyFragment newInstance(int bundleProperty) {
        FormPropertyFragment formPropertyFragment = new FormPropertyFragment();

        Bundle args = new Bundle();
        args.putInt("property", bundleProperty);
        formPropertyFragment.setArguments(args);
        return formPropertyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundlePropertyId = getArguments().getInt("property", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_form_property, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FormPropertyFragmentViewModel.class);
        sharedPropertiesViewModel = new ViewModelProvider(requireActivity()).get(SharedPropertiesViewModel.class);
        sharedCurrencyViewModel = new ViewModelProvider(requireActivity()).get(SharedCurrencyViewModel.class);

        configureAutoCompleteTypes();
        configureAvailabilityStatus();
        configureAvailabilityDate();
        configureSoldDate();
        if (bundlePropertyId != -1) {
            loadProperty();
        }
        propertyAvailabilityDropdown.setOnKeyListener((v, keyCode, event) -> {
            propertyAvailabilityStatus.getEditText().setText("");
            return false;
        });
    }

    private void configureSoldDate() {
        TextInputLayout textInputLayout = root.findViewById(R.id.fragment_form_property_sold_date);
        textInputLayout.getEditText().setOnClickListener(v -> datePickerClick((view, year, month, dayOfMonth) ->
                viewModel.setSoldDate(year, month, dayOfMonth)
        ));
        viewModel.soldDate.observe(getViewLifecycleOwner(), soldDate ->
                textInputLayout.getEditText().setText(soldDate)
        );
    }

    private void configureAvailabilityDate() {
        TextInputLayout availabilityDate = root.findViewById(R.id.fragment_form_property_availability_date);

        availabilityDate.getEditText().setOnClickListener(v -> datePickerClick((view, year, month, dayOfMonth) ->
                viewModel.setAvailableDate(year, month, dayOfMonth)
        ));
        viewModel.entryDate.observe(getViewLifecycleOwner(), soldDate ->
                availabilityDate.getEditText().setText(soldDate)
        );
    }

    private void loadProperty() {
        viewModel.loadProperty(bundlePropertyId);
        observePhotos();
        observeStatusAvailability();
        observePrice();
        observeType();
        observeCity();
        observeAddress();
        observeState();
        observeZip();
        observeArea();
        observePieces();
        observeInterestPoints();
        observeDescription();
        observeAgent();
        observeSoldDate();
        observeEntryDate();
        observeCurrency();
    }

    private void observePrice() {
        viewModel.dollarsPrice.observe(getViewLifecycleOwner(), dollarsPrice ->
        {
            if (dollarsPrice != null) {
                displayPriceInCurrentCurrency(dollarsPrice);
            }
        });
    }

    private void observeCurrency() {
        sharedCurrencyViewModel.currency.observe(getViewLifecycleOwner(), currency -> {
            if (viewModel.dollarsPrice.getValue() != null) {
                displayPriceInCurrentCurrency(viewModel.dollarsPrice.getValue());
            }
        })
        ;
    }

    private void displayPriceInCurrentCurrency(String dollarsPrice) {
        if (sharedCurrencyViewModel.currency.getValue() == Currency.DOLLARS) {
            propertyPriceTextInputLayout.getEditText().setText(dollarsPrice);
            propertyPriceTextInputLayout.setStartIconDrawable(R.drawable.ic_money_24px);
        } else {

            propertyPriceTextInputLayout.getEditText().setText(String.valueOf(Utils.convertDollarToEuro(Integer.parseInt(dollarsPrice))));
            propertyPriceTextInputLayout.setStartIconDrawable(R.drawable.ic_euro_24px);
        }
    }

    private void observeEntryDate() {
        viewModel.entryDate.observe(getViewLifecycleOwner(), entryDate -> {
            propertyEntryDate.getEditText().setText(entryDate);
        });
    }

    private void observeSoldDate() {
        viewModel.soldDate.observe(getViewLifecycleOwner(), soldDate -> {
            propertySoldDate.getEditText().setText(soldDate);
        });
    }

    private void observeAgent() {
        viewModel.agent.observe(getViewLifecycleOwner(), agent -> {
            propertyAgent.getEditText().setText(agent);
        });
    }

    private void observeDescription() {
        viewModel.description.observe(getViewLifecycleOwner(), description -> {
            propertyDescription.getEditText().setText(description);
        });
    }

    private void observeInterestPoints() {
        viewModel.interestpoints.observe(getViewLifecycleOwner(), interestPoint -> {
            propertyInterestPoints.getEditText().setText(interestPoint);
        });
    }

    private void observePieces() {
        viewModel.pieces.observe(getViewLifecycleOwner(), pieces -> {
            propertyPieces.getEditText().setText(pieces);
        });
    }

    private void observeArea() {
        viewModel.area.observe(getViewLifecycleOwner(), area -> {
            propertyArea.getEditText().setText(area);
        });
    }

    private void observeZip() {
        viewModel.zip.observe(getViewLifecycleOwner(), zip -> {
            propertyZip.getEditText().setText(zip);
        });
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            propertyState.getEditText().setText(state);
        });
    }

    private void observeCity() {
        viewModel.city.observe(getViewLifecycleOwner(), city -> {
            propertyCity.getEditText().setText(city);
        });
    }

    private void observeAddress() {
        viewModel.address.observe(getViewLifecycleOwner(), address -> {
            propertyAddress.getEditText().setText(address);
        });
    }

    private void observeType() {
        viewModel.type.observe(getViewLifecycleOwner(), type -> {
            propertyType.getEditText().setText(type);
        });
    }

    private void observePhotos() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount());
        photosRecyclerView.setLayoutManager(gridLayoutManager);
        viewModel.photos.observe(getViewLifecycleOwner(), photos ->
        {
            if (photos != null) {
                PhotoGridAdapter photoGridAdapter = new PhotoGridAdapter(getActivity(), photos, FormPropertyFragment.this,
                        FormPropertyFragment.this);
                photosRecyclerView.setAdapter(photoGridAdapter);
            }
        });
    }

    private void configureAutoCompleteTypes() {

        final String[] TYPE = viewModel.getTypes();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, TYPE);
        AutoCompleteTextView textView = root.findViewById(R.id.fragment_form_property_type_dropdown);
        textView.setAdapter(adapter);
        textView.setCursorVisible(false);
    }

    private void configureAvailabilityStatus() {
        final String[] AVAILABILITY = viewModel.getAvailabilities();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, AVAILABILITY);
        AutoCompleteTextView textView = root.findViewById(R.id.fragment_form_property_availability_dropdown);
        textView.setAdapter(adapter);
        textView.setCursorVisible(false);
    }

    public void datePickerClick(DatePickerDialog.OnDateSetListener listener) {

        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    @OnClick(R.id.fragment_form_property_photos_bt)
    public void selectImage() {
        final CharSequence[] options = {getString(R.string.takephoto), getString(R.string.choosefromgallery), getString(R.string.cancel)};

        AlertDialog.Builder alertdialogPhotoBuilder = new AlertDialog.Builder(getActivity());
        alertdialogPhotoBuilder.setTitle(R.string.chooseyourprofilepicture);

        alertdialogPhotoBuilder.setItems(options, (dialog, item) -> {

            if (options[item].equals(getString(R.string.takephoto))) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);

            } else if (options[item].equals(R.string.choosefromgallery)) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);

            } else if (options[item].equals(R.string.cancel)) {
                dialog.dismiss();
            }
        });
        alertdialogPhotoBuilder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        photoBM = (Bitmap) data.getExtras().get("data");
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                photoBM = BitmapFactory.decodeFile(picturePath);
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
            if (photoBM != null) {
                descriptionAlertDialog(-1);
            }
        }
    }

    private void descriptionAlertDialog(int position) {
        AlertDialog.Builder alertDialogDescriptionPhotoBuilder = new AlertDialog.Builder(getActivity());

        alertDialogDescriptionPhotoBuilder.setTitle(R.string.photo_description);
        final EditText input = new EditText(getActivity());
        if (position != -1) {
            input.setHint(viewModel.getPhoto(position).getDescription());
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(35);
        input.setFilters(fArray);
        alertDialogDescriptionPhotoBuilder.setView(input);
        alertDialogDescriptionPhotoBuilder.setPositiveButton("OK", (dialogInterface, i) -> {
            Toast.makeText(getActivity(), R.string.photoanddescriptionadded, Toast.LENGTH_SHORT).show();
            String description = input.getText().toString();
            if (position != -1) {
                viewModel.updatePhotoDescription(description, position);
            } else {
                Photo photo = Utils.saveToInternalStorage(photoBM, description, getActivity().getApplicationContext());
                viewModel.setPhoto(photo);
            }
        });
        observePhotos();
        alertDialogDescriptionPhotoBuilder.show();
    }

    private int spanCount() {
        final boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            return 5;
        } else return 3;
    }

    private void notificationPropertyAdded() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(), "1")
                .setSmallIcon(R.drawable.ic_home_24px)
                .setContentTitle(getString(R.string.propertyadded))
                .setContentText(getString(R.string.thenewpropertyhasbeenadded))
                .setPriority(Notification.PRIORITY_MAX);
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    @OnClick(R.id.fragment_form_property_validate_fab)
    public void insertProperty() {

        if (bundlePropertyId == -1) {
            viewModel.setPropertyInDatabase(newProperty());
            sharedPropertiesViewModel.setProperty(newProperty());
            notificationPropertyAdded();
        } else {
            if (!propertySoldDate.getEditText().equals("") && propertyAvailabilityStatus.getEditText().toString() == "Sold") {
                Utils.toast(getActivity(), getString(R.string.errorsolddate));
            }
            viewModel.updateProperty(newProperty());
            sharedPropertiesViewModel.setProperty(newProperty());
        }
        getActivity().onBackPressed();
    }

    private Property newProperty() {
        String type = propertyType.getEditText().getText().toString();
        String priceBeforeConversion = propertyPriceTextInputLayout.getEditText().getText().toString();
        String dollars_price;
        if (sharedCurrencyViewModel.currency.getValue() == Currency.EUROS) {
            dollars_price = String.valueOf(Utils.convertEuroToDollar(Integer.parseInt(priceBeforeConversion)));
        } else {
            dollars_price = priceBeforeConversion;
        }
        String address = propertyAddress.getEditText().getText().toString();
        String city = propertyCity.getEditText().getText().toString();
        String state = propertyState.getEditText().getText().toString();
        String zip = propertyZip.getEditText().getText().toString();
        String area = propertyArea.getEditText().getText().toString();
        String pieces = propertyPieces.getEditText().getText().toString();
        String interestPoints = propertyInterestPoints.getEditText().getText().toString();
        String description = propertyDescription.getEditText().getText().toString();
        String agent = propertyAgent.getEditText().getText().toString();
        String entryDate = propertyEntryDate.getEditText().getText().toString();
        String soldDate = propertySoldDate.getEditText().getText().toString();
        ArrayList<Photo> photos = viewModel.getPhotos();

        Status status = Status.UNSPECIFIED;

        switch (propertyAvailabilityStatus.getEditText().getText().toString()) {
            case "Sold": {
                status = Status.SOLD;
            }
            break;
            case "Available": {
                status = Status.AVAILABLE;
            }
        }

        return new Property(
                bundlePropertyId,
                type,
                dollars_price,
                address,
                city,
                state,
                zip,
                area,
                pieces,
                interestPoints,
                description,
                photos,
                status,
                entryDate,
                soldDate,
                agent
        );
    }

    @Override
    public void onPhotoDeleteClicked(int photo) {
        viewModel.deletePhoto(photo);
    }

    @Override
    public void onPhotoDescriptionClicked(int position) {
        Photo photo = viewModel.getPhoto(position);
        descriptionAlertDialog(position);
    }

    private void observeStatusAvailability() {
        propertyAvailabilityDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1: {
                        viewModel.status.postValue(Status.AVAILABLE);
                    }
                    break;
                    case 2: {
                        viewModel.status.postValue(Status.SOLD);
                    }
                }
                configureAvailabilityStatus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewModel.status.observe(getViewLifecycleOwner(), status ->

                {
                    switch (status) {
                        case SOLD: {
                            propertyAvailabilityStatus.getEditText().setText(R.string.sold);
                            propertySoldDate.setVisibility(View.VISIBLE);
                        }
                        break;
                        case AVAILABLE: {
                            propertyAvailabilityStatus.getEditText().setText(R.string.available);
                            propertySoldDate.setVisibility(View.INVISIBLE);
                        }
                        break;
                        default: {
                            propertyAvailabilityStatus.getEditText().setText("");
                            propertySoldDate.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
    }

    @OnClick(R.id.fragment_form_property_availability_dropdown)
    public void availailityStatus() {
        configureAvailabilityStatus();
    }

    @OnClick(R.id.fragment_form_property_type_dropdown)
    public void types() {
        configureAutoCompleteTypes();
    }
}