package com.openclassrooms.realestatemanager.models;

import java.io.File;

public class Photo {
    private String path;
    private String fileName;
    private String photoDescription;

    public Photo(String path, String fileName, String photoDescription) {
        this.path = path;
        this.fileName = fileName;
        this.photoDescription = photoDescription;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPhotoFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPhotoDescription() {
        return photoDescription;
    }

    public void setPhotoDescription(String photoDescription) {
        this.photoDescription = photoDescription;
    }
}