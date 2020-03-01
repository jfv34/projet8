package com.openclassrooms.realestatemanager.models;

import android.net.Uri;

public class PhotoURI {
    private Uri photoUri;
    private String photoDescription;

    public PhotoURI(Uri photoUri, String photoDescription) {
        this.photoUri = photoUri;
        this.photoDescription = photoDescription;
    }
}

