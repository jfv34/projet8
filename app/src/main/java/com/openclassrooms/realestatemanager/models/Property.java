package com.openclassrooms.realestatemanager.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Property {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String type;
    private int price;
    private String address;
    private int surface;
    private int numberOfPieces;
    private String description;
    private String interestPoint;
    private boolean solded;
    private String entryDate;
    private String saleDate;
    private String agentName;
    //@Embedded
    //private ArrayList<PhotoURI> photosURI;
    private String photosURI;

    public Property(final int id, String type, int price, final String address, int surface, int numberOfPieces,
                    String description, String interestPoint, boolean solded, String entryDate,
                    String saleDate, String agentName, String photosURI) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.address = address;
        this.surface=surface;
        this.numberOfPieces = numberOfPieces;
        this.description = description;
        this.interestPoint = interestPoint;
        this.solded=solded;
        this.entryDate=entryDate;
        this.saleDate=saleDate;
        this.agentName=agentName;
        this.photosURI = photosURI;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    public void setNumberOfPieces(int numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInterestPoint() {
        return interestPoint;
    }

    public void setInterestPoint(String interestPoint) {
        this.interestPoint = interestPoint;
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

