package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Property {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String type;
    private int city;
    private int price;
    private String description;


    public Property() {
    }  //  ?

    public Property(int id, String type, int city, int price, String description) {
        this.id = id;
        this.type = type;
        this.city = city;
        this.price = price;
        this.description = description;
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

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
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
}






