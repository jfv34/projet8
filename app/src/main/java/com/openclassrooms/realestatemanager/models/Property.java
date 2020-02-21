package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Property {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String type;
    private String city;
    private int price;
    private String description;
    @Embedded
    private ArrayList<PhotosURI> photosList;

    public Property(int id, String type, String city, int price, String description,
                    ArrayList<PhotosURI> photosList) {
        this.id = id;
        this.type = type;
        this.city = city;
        this.price = price;
        this.description = description;
        this.photosList = photosList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<PhotosURI> getPhotosList() {
        return photosList;
    }

    public void setPhotosList(ArrayList<PhotosURI> photosList) {
        this.photosList = photosList;
    }
}






