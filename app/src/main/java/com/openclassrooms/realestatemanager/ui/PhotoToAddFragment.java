package com.openclassrooms.realestatemanager.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoToAddFragment extends Fragment {

    public static Fragment newInstance() {
        return new PhotoToAddFragment();
    }

    private ImageView photoIV;
    private String pathPhoto;
    private String fileNamePhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        photoIV = view.findViewById(R.id.fragment_photo_to_add_iv);
        Bitmap photo = Utils.loadImageFromStorage(pathPhoto, fileNamePhoto);
        photoIV.setImageBitmap(photo);
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
