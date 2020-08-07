package com.openclassrooms.realestatemanager.models;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Property {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String type;
    private String price;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String area;
    private String pieces;
    private String interestPoint;
    private String description;
    private ArrayList<Photo> photos;
    private Status status;
    private String entryDate;
    private String saleDate;
    private String agentName;

    public Property(
            int id,
            String type,
            String price,
            String address,
            String city,
            String state,
            String zip,
            String area,
            String pieces,
            String interestPoint,
            String description,
            ArrayList<Photo> photos,
            Status status,
            String entryDate,
            String saleDate,
            String agentName
    ) {
        this.id=id;
        this.type = type;
        this.price = price;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.area = area;
        this.pieces = pieces;
        this.interestPoint = interestPoint;
        this.description = description;
        this.photos = photos;
        this.status = status;
        this.entryDate=entryDate;
        this.saleDate=saleDate;
        this.agentName=agentName;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPieces() {
        return pieces;
    }

    public String getInterestPoint() {
        return interestPoint;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhoto(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public Status getStatus() {
        if (status == null) {
            status = Status.UNSPECIFIED;
        }
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEntryDate() {
        if (entryDate == null) {
            return "";
        } else {
            return entryDate;
        }
    }

    public String getSaleDate() {
        if (saleDate == null) {
            return "";
        } else {
            return saleDate;
        }
    }

    public String getAgentName() {
        return agentName;
    }

    public static Property fromContentValues(ContentValues values) {
        final Property property = new Property(0, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null);

        if (values.containsKey("id")) property.setId(values.getAsInteger("id"));
        if (values.containsKey("type")) property.setType(values.getAsString("type"));
        if (values.containsKey("price")) property.setPrice(values.getAsString("price"));
        if (values.containsKey("address")) property.setAddress(values.getAsString("address"));
        if (values.containsKey("city")) property.setCity(values.getAsString("city"));
        if (values.containsKey("state")) property.setCity(values.getAsString("state"));
        if (values.containsKey("zip")) property.setCity(values.getAsString("zip"));
        if (values.containsKey("aera")) property.setArea(values.getAsString("aera"));
        if (values.containsKey("pieces")) property.setCity(values.getAsString("pieces"));
        if (values.containsKey("interestPoint"))
            property.setCity(values.getAsString("interestPoint"));
        if (values.containsKey("description")) property.setCity(values.getAsString("description"));
        if (values.containsKey("photos")) property.setCity(values.getAsString("photos"));
        if (values.containsKey("status")) property.setAddress(values.getAsString("status"));
        if (values.containsKey("entryDate")) property.setAddress(values.getAsString("entryDate"));
        if (values.containsKey("saleDate")) property.setAddress(values.getAsString("saleDate"));
        if (values.containsKey("agentName")) property.setAddress(values.getAsString("agentName"));
        return property;
    }
    public Bundle compareTo(Property other){
        Bundle diff = new Bundle();
        if (!getPrice().equals(other.getPrice()))
            diff.putString("price", other.getPrice());
        if (!getCity().equals(other.getCity()))
            diff.putString("city", other.getCity());
        if (!getType().equals(other.getType()))
            diff.putString("type", other.getType());
        if (getPhotos() != null && getPhotos().size() == other.getPhotos().size() && !other.getPhotos().isEmpty() && !getPhotos().get(0).getFullPath().equals(other.getPhotos().get(0).getFullPath())) {
            diff.putString("photoPath", other.getPhotos().get(0).getFullPath());
        }
        return diff;
    }
}