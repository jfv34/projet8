package com.openclassrooms.realestatemanager.models;

import android.net.Uri;

class PhotosURI {
    private Uri photoUri;
    private String photoDescription;

    public PhotosURI(Uri photoUri, String photoDescription) {
        this.photoUri = photoUri;
        this.photoDescription = photoDescription;
    }
}

