package com.openclassrooms.realestatemanager.models;

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

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileNamePhoto() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}