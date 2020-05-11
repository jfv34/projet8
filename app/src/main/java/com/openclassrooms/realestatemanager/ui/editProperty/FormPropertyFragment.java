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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.interfaces_clickedListener.OnPhotoDeleteClickedListener;
import com.openclassrooms.realestatemanager.interfaces_clickedListener.OnPhotoDescriptionClickedListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class FormPropertyFragment extends Fragment implements OnPhotoDeleteClickedListener, OnPhotoDescriptionClickedListener {

    private static Calendar calendar = Calendar.getInstance();
    private int bundleProperty;
    private FormPropertyFragmentViewModel viewModel;
    private View root;
    private Bitmap photoBM = null;

    @BindView(R.id.fragment_insert_property_toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_insert_property_photos_recyclerView)
    RecyclerView photosRecyclerView;
    @BindView(R.id.fragment_insert_property_TextField_type)
    TextInputLayout property_type;
    @BindView(R.id.fragment_insert_property_TextField_price)
    TextInputLayout property_price;
    @BindView(R.id.fragment_insert_property_TextField_address)
    TextInputLayout property_address;
    @BindView(R.id.fragment_insert_property_TextField_city)
    TextInputLayout property_city;
    @BindView(R.id.fragment_insert_property_TextField_state)
    TextInputLayout property_state;
    @BindView(R.id.fragment_insert_property_TextField_zip)
    TextInputLayout property_zip;
    @BindView(R.id.fragment_insert_property_TextField_area)
    TextInputLayout property_area;
    @BindView(R.id.fragment_insert_property_TextField_pieces)
    TextInputLayout property_pieces;
    @BindView(R.id.fragment_insert_property_TextField_interestPoints)
    TextInputLayout property_interestPoints;
    @BindView(R.id.fragment_insert_property_TextField_description)
    TextInputLayout property_description;
    @BindView(R.id.fragment_insert_property_TextField_agent)
    TextInputLayout property_agent;
    @BindView(R.id.fragment_form_property_availability_status)
    TextInputLayout property_availability_status;
    @BindView(R.id.fragment_form_property_availability_dropdown)
    AutoCompleteTextView property_availability_dropdown;
    @BindView(R.id.fragment_form_property_availability_date)
    TextInputLayout property_entry_date;
    @BindView(R.id.fragment_form_property_sold_date)
    TextInputLayout property_sold_date;

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
        bundleProperty = getArguments().getInt("property", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_form_property, container, false);
        ButterKnife.bind(this, root);
        configureToolBar();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PropertyDataBase.getInstance(getContext());

        viewModel = new ViewModelProvider(this).get(FormPropertyFragmentViewModel.class);
        configure_autoComplete_types();
        configure_availability_status();
        configure_availabilityDate();
        configure_soldDate();
        if (bundleProperty != -1) {
            loadProperty();
        }
        property_availability_dropdown.setOnKeyListener((v, keyCode, event) -> {
            property_availability_status.getEditText().setText("");
            return false;
        });
    }

    private void configure_soldDate() {
        TextInputLayout textInputLayout = root.findViewById(R.id.fragment_form_property_sold_date);
        textInputLayout.getEditText().setOnClickListener(v -> date_picker_click((view, year, month, dayOfMonth) ->
                viewModel.setSoldDate(year, month, dayOfMonth)
        ));
        viewModel.soldDate.observe(getViewLifecycleOwner(), soldDate ->
                textInputLayout.getEditText().setText(soldDate)
        );
    }

    private void configure_availabilityDate() {
        TextInputLayout availabilityDate = root.findViewById(R.id.fragment_form_property_availability_date);

        availabilityDate.getEditText().setOnClickListener(v -> date_picker_click((view, year, month, dayOfMonth) ->
                viewModel.setAvailableDate(year, month, dayOfMonth)
        ));
        viewModel.entryDate.observe(getViewLifecycleOwner(), soldDate ->
                availabilityDate.getEditText().setText(soldDate)
        );
    }

    private void loadProperty() {
        viewModel.loadProperty(bundleProperty);
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
    }

    private void observeEntryDate() {
        viewModel.entryDate.observe(getViewLifecycleOwner(), entryDate -> {
            property_entry_date.getEditText().setText(entryDate);
        });
    }

    private void observeSoldDate() {
        viewModel.soldDate.observe(getViewLifecycleOwner(), soldDate -> {
            property_sold_date.getEditText().setText(soldDate);
        });
    }

    private void observeAgent() {
        viewModel.agent.observe(getViewLifecycleOwner(), agent -> {
            property_agent.getEditText().setText(agent);
        });
    }

    private void observeDescription() {
        viewModel.description.observe(getViewLifecycleOwner(), description -> {
            property_description.getEditText().setText(description);
        });
    }

    private void observeInterestPoints() {
        viewModel.interestpoints.observe(getViewLifecycleOwner(), interestPoint -> {
            property_interestPoints.getEditText().setText(interestPoint);
        });
    }

    private void observePieces() {
        viewModel.pieces.observe(getViewLifecycleOwner(), pieces -> {
            property_pieces.getEditText().setText(pieces);
        });
    }

    private void observeArea() {
        viewModel.area.observe(getViewLifecycleOwner(), area -> {
            property_area.getEditText().setText(area);
        });
    }

    private void observeZip() {
        viewModel.zip.observe(getViewLifecycleOwner(), zip -> {
            property_zip.getEditText().setText(zip);
        });
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            property_state.getEditText().setText(state);
        });
    }

    private void observeCity() {
        viewModel.city.observe(getViewLifecycleOwner(), city -> {
            property_city.getEditText().setText(city);
        });
    }

    private void observeAddress() {
        viewModel.address.observe(getViewLifecycleOwner(), address -> {
            property_address.getEditText().setText(address);
        });
    }

    private void observeType() {
        viewModel.type.observe(getViewLifecycleOwner(), type -> {
            property_type.getEditText().setText(type);
        });
    }

    private void observePrice() {
        viewModel.price.observe(getViewLifecycleOwner(), price -> {
            property_price.getEditText().setText(price);
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

    private void configure_autoComplete_types() {

        final String[] TYPE = viewModel.getTypes();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, TYPE);
        AutoCompleteTextView textView = root.findViewById(R.id.fragment_insert_property_TextField_type_dropdown);
        textView.setAdapter(adapter);
    }

    private void configure_availability_status() {
        final String[] AVAILABILITY = viewModel.getAvailabilities();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, AVAILABILITY);
        AutoCompleteTextView textView = root.findViewById(R.id.fragment_form_property_availability_dropdown);
        textView.setAdapter(adapter);
        textView.setCursorVisible(false);
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

    @OnClick(R.id.fragment_form_property_validate_fab)
    public void insert_property() {

        if (bundleProperty == -1) {
            viewModel.setProperty(newProperty());
            notification_property_added();
        } else {
            viewModel.updateProperty(newProperty(), bundleProperty);
        }
        Utils.backToMainScreen(getActivity(),this);
    }

    @OnClick(R.id.fragment_form_property_photos_bt)
    public void selectImage() {
        final CharSequence[] options = {getString(R.string.takephoto), getString(R.string.choosefromgallery), getString(R.string.cancel)};

        AlertDialog.Builder alertDialog_Photo_builder = new AlertDialog.Builder(getActivity());
        alertDialog_Photo_builder.setTitle(R.string.chooseyourprofilepicture);

        alertDialog_Photo_builder.setItems(options, (dialog, item) -> {

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
        alertDialog_Photo_builder.show();
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
        AlertDialog.Builder alertDialog_descriptionPhoto_builder = new AlertDialog.Builder(getActivity());

        alertDialog_descriptionPhoto_builder.setTitle(R.string.photo_description);
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
        alertDialog_descriptionPhoto_builder.setView(input);
        alertDialog_descriptionPhoto_builder.setPositiveButton("OK", (dialogInterface, i) -> {
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
        alertDialog_descriptionPhoto_builder.show();
    }

    private int spanCount() {
        final boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            return 5;
        } else return 3;
    }

    private void notification_property_added() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(), "1")
                .setSmallIcon(R.drawable.ic_home_24px)
                .setContentTitle(getString(R.string.propertyadded))
                .setContentText(getString(R.string.thenewpropertyhasbeenadded))
                .setPriority(Notification.PRIORITY_MAX);
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    private Property newProperty() {
        String type = property_type.getEditText().getText().toString();
        String price = property_price.getEditText().getText().toString();
        String address = property_address.getEditText().getText().toString();
        String city = property_city.getEditText().getText().toString();
        String state = property_state.getEditText().getText().toString();
        String zip = property_zip.getEditText().getText().toString();
        String area = property_area.getEditText().getText().toString();
        String pieces = property_pieces.getEditText().getText().toString();
        String interestPoints = property_interestPoints.getEditText().getText().toString();
        String description = property_description.getEditText().getText().toString();
        String agent = property_agent.getEditText().getText().toString();
        String entryDate = property_entry_date.getEditText().getText().toString();
        String soldDate = property_sold_date.getEditText().getText().toString();
        ArrayList<Photo> photos = viewModel.getPhotos();

        boolean isSolded;

        if (property_availability_status.getEditText().getText().toString().equals(getString(R.string.sold))) {
            isSolded = true;
        } else {
            isSolded = false;
        } ;

        return new Property(
                type,
                price,
                address,
                city,
                state,
                zip,
                area,
                pieces,
                interestPoints,
                description,
                photos,
                isSolded,
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
        photoBM = Utils.loadImageFromStorage(photo.getPath(),photo.getFileNamePhoto());
        descriptionAlertDialog(position);
    }

    private void observeStatusAvailability() {
        viewModel.isSold.observe(getViewLifecycleOwner(), isSold -> {
                    if (isSold) {
                        property_availability_status.getEditText().setText(R.string.sold);
                    } else {
                        property_availability_status.getEditText().setText(R.string.available);
                    }
                }
        );
    }

    @OnClick(R.id.fragment_form_property_availability_dropdown)
    public void availailityStatus() {
        configure_availability_status();
    }

    private void configureToolBar() {
        if(bundleProperty==-1){toolbar.setTitle("Adding a new property");}
        else{toolbar.setTitle("Edit property");}
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> Utils.backToMainScreen(getActivity(),this));
    }
}