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
import android.util.Log;
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
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;

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
    private ArrayList<Photo> photos = new ArrayList<>();
    @BindView(R.id.fragment_insert_property_toolbar)
    Toolbar toolbar;
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
        viewModel.properties.observe(getViewLifecycleOwner(), properties -> {
            if (properties != null) {
                property = viewModel.loadProperty(bundleProperty);

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
            }
        });
    }

    private void configure_autoCompleteTextView() {
        final String[] TYPE = new String[]{
                "Duplex", "Loft", "Penthouse", "Manor"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, TYPE);
        AutoCompleteTextView textView = root.findViewById(R.id.fragment_insert_property_TextField_type_dropdown);
        textView.setAdapter(adapter);
    }

    @OnClick(R.id.fragment_insert_property_button)
    public void insert_property() {

        if (bundleProperty == -1) {
            viewModel.setProperty(newProperty());
            Log.i("tag_update","no");
            notification_property_added();
        } else {
            newProperty();
            viewModel.updateProperty(newProperty(), property.getId());
            Log.i("tag_update","ok");
        }

        removeFragment();
    }

    @OnClick(R.id.fragment_insert_property_addingImages_button)
    public void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
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

            viewModel.setPhoto(photoBM,"",getActivity().getApplicationContext());

            if (photoBM != null) {

                photos.add(viewModel.getPhoto());
                    RecyclerView photosRecyclerView = root.findViewById(R.id.fragment_insert_property_photos_recyclerView);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),5);
                    photosRecyclerView.setLayoutManager(gridLayoutManager);
                    PhotoGridAdapter photoGridAdapter = new PhotoGridAdapter(getActivity(), photos);
                    photosRecyclerView.setAdapter(photoGridAdapter);
        }
    }}

    private void notification_property_added() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(), "1")
                .setSmallIcon(R.drawable.ic_home_24px)
                .setContentTitle("Property added")
                .setContentText("The new property has been added")
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
                ""
        );
    }

    private void removeFragment() {
        //Intent intent = new Intent(getActivity(), MainActivity.class);
        //startActivity(intent);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this).commit();
    }

    private void configureToolBar() {
        toolbar.setTitle("Adding a new property");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
    });
}}