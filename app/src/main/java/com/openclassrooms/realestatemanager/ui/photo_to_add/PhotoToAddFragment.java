package com.openclassrooms.realestatemanager.ui.photo_to_add;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Photo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private ArrayList<Photo> photos;
    private String pathPhoto;
    private String fileNamePhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photos = new ArrayList<>();
        pathPhoto = getArguments().getString("path");
        fileNamePhoto = getArguments().getString("fileNamePhoto");
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

        Bitmap photo = Utils.loadImageFromStorage(pathPhoto, fileNamePhoto);
        photoIV.setImageBitmap(photo);


    }

    @OnClick(R.id.fragment_photo_to_add_photoDescription_validate_bt)
    public void adding_description() {
        photoDescription.setVisibility(View.INVISIBLE);
        description_validate_bt.setVisibility(View.INVISIBLE);
        adding_textView.setVisibility(View.VISIBLE);
        adding_icon.setVisibility(View.VISIBLE);
        valide_all_photos_icon.setVisibility(View.VISIBLE);
        valide_all_photos_tv.setVisibility(View.VISIBLE);

        photos.add(new Photo(
                pathPhoto,
                fileNamePhoto,
                photoDescription.getText().toString())
        );
    }

    @OnClick(R.id.fragment_photo_to_add_adding_icon)
    public void a() {

    }

    @OnClick(R.id.fragment_photo_to_add_adding_textView)
    public void b() {

    }

    @OnClick(R.id.fragment_photo_to_add_validate_icon)
    public void c() {

    }

    @OnClick(R.id.fragment_photo_to_add_validate_textView)
    public void d() {


    }

}
