package com.openclassrooms.realestatemanager.models;

import java.io.File;

public class Photo {
    private String path;
    private String fileName;
    private String description;

    public Photo(String path, String fileName, String photoDescription) {
        this.path = path;
        this.fileName = fileName;
        this.description = photoDescription;
    }

    public String getPath() {
        return path;
    }

    public String getFullPath(){
        return path+ File.separator+fileName;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getFileNamePhoto() {
        return fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}