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
       String myValue = this.getArguments().getString("message");

        return inflater.inflate(R.layout.fragment_photo_to_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photoIV = view.findViewById(R.id.fragment_photo_to_add_iv);
        Bitmap photo = Utils.loadImageFromStorage(pathPhoto, fileNamePhoto);
        photoIV.setImageBitmap(photo);

    }
}
