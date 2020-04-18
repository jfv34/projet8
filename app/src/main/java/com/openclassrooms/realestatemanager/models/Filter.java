package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Filter {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private ArrayList<String> types;
    private String priceMaxi;
    private String priceMini;
    private ArrayList<String> cities;
    private ArrayList<String> states;
    private String areaMaxi;
    private String areaMini;
    private String piecesMaxi;
    private String piecesMini;
    private String interestPoints;
    private String agentName;
    private boolean solded;
    private String entryDate;
    private String saleDate;
    private int numberOfPhotos;

    public Filter(ArrayList<String> types, String priceMaxi, String priceMini, ArrayList<String> cities,
                  ArrayList<String> states, String areaMaxi, String areaMini, String piecesMaxi, String piecesMini,
                  String interestPoints, String agentName, boolean solded, String entryDate, String saleDate,
                  int numberOfPhotos) {
        this.types = types;
        this.priceMaxi = priceMaxi;
        this.priceMini = priceMini;
        this.cities = cities;
        this.states = states;
        this.interestPoints = interestPoints;
        this.areaMaxi = areaMaxi;
        this.areaMini = areaMini;
        this.piecesMaxi = piecesMaxi;
        this.priceMini = piecesMini;
        this.agentName = agentName;
        this.solded = solded;
        this.entryDate = entryDate;
        this.saleDate = saleDate;
        this.numberOfPhotos = numberOfPhotos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public String getPriceMaxi() {
        return priceMaxi;
    }

    public void setPriceMaxi(String priceMaxi) {
        this.priceMaxi = priceMaxi;
    }

    public String getPriceMini() {
        return priceMini;
    }

    public void setPriceMini(String priceMini) {
        this.priceMini = priceMini;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public void setStates(ArrayList<String> states) {
        this.states = states;
    }

    public String getAreaMaxi() {
        return areaMaxi;
    }

    public void setAreaMaxi(String areaMaxi) {
        this.areaMaxi = areaMaxi;
    }

    public String getAreaMini() {
        return areaMini;
    }

    public void setAreaMini(String areaMini) {
        this.areaMini = areaMini;
    }

    public String getPiecesMaxi() {
        return piecesMaxi;
    }

    public void setPiecesMaxi(String piecesMaxi) {
        this.piecesMaxi = piecesMaxi;
    }

    public String getPiecesMini() {
        return piecesMini;
    }

    public void setPiecesMini(String piecesMini) {
        this.piecesMini = piecesMini;
    }

    public String getInterestPoints() {
        return interestPoints;
    }

    public void setInterestPoints(String interestPoints) {
        this.interestPoints = interestPoints;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
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

    public int getNumberOfPhotos() {
        return numberOfPhotos;
    }

    public void setNumberOfPhotos(int numberOfPhotos) {
        this.numberOfPhotos = numberOfPhotos;
    }
}
