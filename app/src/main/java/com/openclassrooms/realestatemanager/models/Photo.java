package com.openclassrooms.realestatemanager.models;

import android.graphics.Bitmap;

public class Photo {
    private Bitmap photoBMP;
    private String photoDescription;

    public Photo(Bitmap photoBMP, String photoDescription) {
        this.photoBMP = photoBMP;
        this.photoDescription = photoDescription;
    }

    public Bitmap getPhotoBMP() {
        return photoBMP;
    }

    public void setPhotoBMP(Bitmap photoBMP) {
        this.photoBMP = photoBMP;
    }

    public String getPhotoDescription() {
        return photoDescription;
    }

    public void setPhotoDescription(String photoDescription) {
        this.photoDescription = photoDescription;
    }
}

