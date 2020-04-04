package com.openclassrooms.realestatemanager.ui.editProperty;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.ui.main.MainFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class FormPropertyFragment extends Fragment {

    private int bundleProperty;
    private FormPropertyFragmentViewModel viewModel;
    private View root;
    private Bitmap photoBM = null;
    private Property property;
    //private ArrayList<Photo> photos = new ArrayList<>();

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
    @BindView(R.id.fragment_insert_property_TextField_status_notSolded_tv)
    TextInputLayout property_status_notSolded_iv;
    @BindView(R.id.fragment_insert_property_TextField_status_solded_tv)
    TextInputLayout property_status_solded_iv;

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
        configure_autoCompleteTextView();
        if (bundleProperty != -1) {
            loadProperty();
        }
    }

    private void loadProperty() {
        viewModel.loadProperty(bundleProperty);
        observePhotos();
        viewModel.property.observe(getViewLifecycleOwner(), property -> {
            if (property != null) {


                property_type.getEditText().setText(property.getType());
                property_price.getEditText().setText(property.getPrice());
                property_address.getEditText().setText(property.getAddress());
                property_city.getEditText().setText(property.getCity());
                property_state.getEditText().setText(property.getState());
                property_zip.getEditText().setText(property.getZip());
                property_area.getEditText().setText(property.getArea());
                property_pieces.getEditText().setText(property.getPieces());
                property_interestPoints.getEditText().setText(property.getInterestPoint());
                property_description.getEditText().setText(property.getDescription());
                property_agent.getEditText().setText(property.getAgentName());
                if (property.isSolded()) {
                    property_status_solded_iv.setVisibility(View.VISIBLE);
                    property_status_notSolded_iv.setVisibility(View.INVISIBLE);
                }
                if (!property.isSolded()) {
                    property_status_solded_iv.setVisibility(View.INVISIBLE);
                    property_status_notSolded_iv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void configure_autoCompleteTextView() {

        final String[] TYPE = viewModel.loadType();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, TYPE);
        AutoCompleteTextView textView = root.findViewById(R.id.fragment_insert_property_TextField_type_dropdown);
        textView.setAdapter(adapter);
    }

    @OnClick(R.id.fragment_form_property_validate_bt)
    public void insert_property() {

        if (bundleProperty == -1) {
            viewModel.setProperty(newProperty());
            notification_property_added();
        } else {
            newProperty();
            viewModel.updateProperty(newProperty(), property.getId());
        }

        backToMain();
    }

    private void backToMain() {
        final boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            removeFragment();
        } else {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment mainFragment = MainFragment.newInstance();
            transaction.replace(R.id.frame_layout_main, mainFragment).commit();}
    }

    @OnClick(R.id.fragment_form_property_photos_bt)
    public void selectImage() {
        final CharSequence[] options = {getString(R.string.takephoto), getString(R.string.choosefromgallery), getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.chooseyourprofilepicture);

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals(getString(R.string.takephoto))) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals(R.string.choosefromgallery)) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals(R.string.cancel)) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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
                Photo photo = Utils.saveToInternalStorage(photoBM, "", getActivity().getApplicationContext());
                viewModel.setPhoto(photo);
                observePhotos();
        }
    }}

    private void observePhotos() {
        viewModel.photos.observe(getViewLifecycleOwner(),photos ->
        {
            if (!photos.isEmpty()) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount());
                photosRecyclerView.setLayoutManager(gridLayoutManager);
                PhotoGridAdapter photoGridAdapter = new PhotoGridAdapter(getActivity(), photos);
                photosRecyclerView.setAdapter(photoGridAdapter);
            }
        });
    }

    @OnClick(R.id.fragment_form_property_available_radioButton)
    public void available_radiobutton() {
        property_status_notSolded_iv.setVisibility(View.VISIBLE);
        property_status_solded_iv.setVisibility(View.INVISIBLE);
        String text;
        if (property.getEntryDate().isEmpty()) {
            text = "Available";
        } else {
            text = "Available since " + property.getEntryDate();
        }
        property_status_notSolded_iv.getEditText().setText(text);
    }

    @OnClick(R.id.fragment_form_property_solded_radioButton)
    public void solded_radiobutton() {
        property_status_notSolded_iv.setVisibility(View.INVISIBLE);
        property_status_solded_iv.setVisibility(View.VISIBLE);
        String text;
        if (property.getSaleDate().isEmpty()) {
            text = "Solded";
        } else {
            text = "Solded since " + property.getSaleDate();
        }
        property_status_solded_iv.getEditText().setText(text);
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
        ArrayList<Photo> photos = viewModel.getPhotos();

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
                false,
                "",
                "",
                agent
        );
    }

    private void removeFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this).commit();
    }

    private void configureToolBar() {
        toolbar.setTitle("Adding a new property");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> backToMain());
}}