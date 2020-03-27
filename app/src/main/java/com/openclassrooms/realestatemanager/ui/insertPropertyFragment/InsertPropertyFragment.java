package com.openclassrooms.realestatemanager.ui.insertPropertyFragment;

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
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.openclassrooms.realestatemanager.ui.main.MainActivity;
import com.openclassrooms.realestatemanager.ui.main.MainFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class InsertPropertyFragment extends Fragment {

    private InsertPropertyFragmentViewModel viewModel;
    private View root;
    private Bitmap photoBM = null;
    private Photo photo;
    private ArrayList<Photo> photos = new ArrayList<>();

    @BindView(R.id.fragment_insert_property_TextField_type)
    TextInputLayout newProperty_type;
    @BindView(R.id.fragment_insert_property_TextField_price)
    TextInputLayout newProperty_price;
    @BindView(R.id.fragment_insert_property_TextField_address)
    TextInputLayout newProperty_address;
    @BindView(R.id.fragment_insert_property_TextField_city)
    TextInputLayout newProperty_city;
    @BindView(R.id.fragment_insert_property_TextField_state)
    TextInputLayout newProperty_state;
    @BindView(R.id.fragment_insert_property_TextField_zip)
    TextInputLayout newProperty_zip;
    @BindView(R.id.fragment_insert_property_TextField_area)
    TextInputLayout newProperty_area;
    @BindView(R.id.fragment_insert_property_TextField_pieces)
    TextInputLayout newProperty_pieces;
    @BindView(R.id.fragment_insert_property_TextField_interestPoints)
    TextInputLayout newProperty_interestPoints;
    @BindView(R.id.fragment_insert_property_TextField_description)
    TextInputLayout newProperty_description;


    public static Fragment newInstance() {
        return new InsertPropertyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_insert_property, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PropertyDataBase.getInstance(getContext());

        viewModel = new ViewModelProvider(this).get(InsertPropertyFragmentViewModel.class);
        configure_autoCompleteTextView();

    }
    private void configure_autoCompleteTextView() {
        final String[] TYPE = new String[]{
                "Duplex", "Loft", "Penthouse", "Manor"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, TYPE);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                root.findViewById(R.id.fragment_insert_property_TextField_type_dropdown);
        textView.setAdapter(adapter);
    }

    @OnClick(R.id.fragment_insert_property_button)
    public void insert_property() {

        viewModel.setProperty(newProperty());
        notification_property_added();
        displayMainFragment();
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
            photo = Utils.saveToInternalStorage(photoBM, "", getActivity().getApplicationContext());
            if (photoBM != null) {

                photos.add(photo);
                    RecyclerView photosRecyclerView = root.findViewById(R.id.fragment_insert_property_photos_recyclerView);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.HORIZONTAL,false);
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

        String type = newProperty_type.getEditText().getText().toString();
        String price = newProperty_price.getEditText().getText().toString();
        String address = newProperty_address.getEditText().getText().toString();
        String city = newProperty_city.getEditText().getText().toString();
        String state = newProperty_state.getEditText().getText().toString();
        String zip = newProperty_zip.getEditText().getText().toString();
        String area = newProperty_area.getEditText().getText().toString();
        String pieces = newProperty_pieces.getEditText().getText().toString();
        String interestPoints = newProperty_interestPoints.getEditText().getText().toString();
        String description = newProperty_description.getEditText().getText().toString();

        ArrayList<Photo> photos = viewModel.getPhotosTemporary();

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

    private void displayMainFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment mainFragment = MainFragment.newInstance();
        transaction.replace(R.id.frame_layout_main, mainFragment).commit();
    }
}