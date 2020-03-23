package com.openclassrooms.realestatemanager.ui.photo_to_add;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.ui.insertPropertyFragment.InsertPropertyFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class PhotoToAddFragment extends Fragment {

    public static Fragment newInstance() {
        return new PhotoToAddFragment();
    }

    @BindView(R.id.fragment_photo_to_add_iv)
    ImageView photoIV;

    @BindView(R.id.fragment_photo_to_add_photoDescription_ET)
    EditText photoDescription;

    @BindView(R.id.fragment_photo_to_add_photoDescription_validate_bt)
    Button description_validate_bt;

    @BindView(R.id.fragment_photo_to_add_adding_textView)
    TextView adding_textView;

    @BindView(R.id.fragment_photo_to_add_adding_icon)
    ImageButton adding_icon;

    @BindView(R.id.fragment_photo_to_add_validate_textView)
    TextView valide_all_photos_tv;

    @BindView(R.id.fragment_photo_to_add_validate_icon)
    ImageButton valide_all_photos_icon;

    private PhotoToAddFragmentViewModel viewModel;
    private ArrayList<Photo> photos;
    private Photo photo;
    private String pathPhoto;
    private String fileNamePhoto;
    private Bitmap photoBM = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photos = new ArrayList<>();
        selectImage();

    }


   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_photo_to_add, container, false);
       ButterKnife.bind(this, root);
       return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PhotoToAddFragmentViewModel.class);
        //Bitmap photo = Utils.loadImageFromStorage(pathPhoto, fileNamePhoto);
        //photoIV.setImageBitmap(photo);

    }

    @OnClick(R.id.fragment_photo_to_add_photoDescription_validate_bt)
    public void adding_photo() {
        photoDescription.setVisibility(View.INVISIBLE);
        description_validate_bt.setVisibility(View.INVISIBLE);
        adding_textView.setVisibility(View.VISIBLE);
        adding_icon.setVisibility(View.VISIBLE);
        valide_all_photos_icon.setVisibility(View.VISIBLE);
        valide_all_photos_tv.setVisibility(View.VISIBLE);

        pathPhoto = photo.getPath();
        fileNamePhoto = photo.getFileNamePhoto();
        photos.add(new Photo(
                pathPhoto,
                fileNamePhoto,
                photoDescription.getText().toString())
        );
    }

    @OnClick(R.id.fragment_photo_to_add_adding_icon)
    public void click_adding_icon() {
        selectImage();
    }

    @OnClick(R.id.fragment_photo_to_add_adding_textView)
    public void click_adding_textView() {
        selectImage();
    }

    @OnClick(R.id.fragment_photo_to_add_validate_textView)
    public void click_validate_tv() {
        savePhotosInDataBase();
        return_To_InsertPropertyFragment();
    }

    @OnClick(R.id.fragment_photo_to_add_validate_icon)
    public void click_validate_icon() {
        savePhotosInDataBase();
        return_To_InsertPropertyFragment();
    }

    private void savePhotosInDataBase() {
        viewModel.setPhotosTemporary(photos);
    }

    private void return_To_InsertPropertyFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment insertPropertyFragment = InsertPropertyFragment.newInstance();
        transaction.replace(R.id.frame_layout_main, insertPropertyFragment).commit();
    }

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
                photoIV.setImageBitmap(photoBM);
                adding_description();
            }
        }
    }

    private void adding_description() {
        photoDescription.setVisibility(View.VISIBLE);
        description_validate_bt.setVisibility(View.VISIBLE);
        adding_textView.setVisibility(View.INVISIBLE);
        adding_icon.setVisibility(View.INVISIBLE);
        valide_all_photos_icon.setVisibility(View.INVISIBLE);
        valide_all_photos_tv.setVisibility(View.INVISIBLE);

    }

}
