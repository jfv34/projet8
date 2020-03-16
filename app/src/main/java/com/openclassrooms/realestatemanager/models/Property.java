package com.openclassrooms.realestatemanager.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Property {

    @PrimaryKey(autoGenerate = true)
    private int id;
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
    private String photo;
    private boolean solded;
    private String entryDate;
    private String saleDate;
    private String agentName;
    //@Embedded
    //private ArrayList<PhotoURI> photosURI;
    private String photosURI;

    public Property(String type, String price, String address, String city, String state, String zip,
                    String area, String pieces, String interestPoint, String description,
                    String photo, boolean solded, String entryDate, String saleDate, String agentName
    ) {
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
        this.photo = photo;
        this.solded=solded;
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

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
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

    public void setPieces(String pieces) {
        this.pieces = pieces;
    }

    public String getInterestPoint() {
        return interestPoint;
    }

    public void setInterestPoint(String interestPoint) {
        this.interestPoint = interestPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isSolded() {
        return solded;
    }

    public void setSolded(boolean solded) {
        this.solded = solded;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getPhotosURI() {
        return photosURI;
    }

    public void setPhotosURI(String photosURI) {
        this.photosURI = photosURI;
    }
}

