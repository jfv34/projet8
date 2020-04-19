package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

//@Entity
public class Filter {

    //@PrimaryKey(autoGenerate = true)
    private int id;
    private ArrayList<String> types;
    private int priceMaxi;
    private int priceMini;
    private ArrayList<String> cities;
    private ArrayList<String> states;
    private int areaMaxi;
    private int areaMini;
    private int piecesMaxi;
    private int piecesMini;
    private String interestPoints;
    private String agentName;
    private boolean isSolded;
    private String entryDate;
    private String saleDate;
    private int numberOfPhotos;

    public Filter(ArrayList<String> types, int priceMaxi, int priceMini, ArrayList<String> cities,
                  ArrayList<String> states, int areaMaxi, int areaMini, int piecesMaxi, int piecesMini,
                  String interestPoints, String agentName, boolean isSolded, String entryDate, String saleDate,
                  int numberOfPhotos) {
        this.types = types;
        this.priceMaxi = priceMaxi;
        this.priceMini = priceMini;
        this.cities = cities;
        this.states = states;
        this.areaMaxi = areaMaxi;
        this.areaMini = areaMini;
        this.piecesMaxi = piecesMaxi;
        this.priceMini = piecesMini;
        this.interestPoints = interestPoints;
        this.agentName = agentName;
        this.isSolded = isSolded;
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

    public int getPriceMaxi() {
        return priceMaxi;
    }

    public void setPriceMaxi(int priceMaxi) {
        this.priceMaxi = priceMaxi;
    }

    public int getPriceMini() {
        return priceMini;
    }

    public void setPriceMini(int priceMini) {
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

    public int getAreaMaxi() {
        return areaMaxi;
    }

    public void setAreaMaxi(int areaMaxi) {
        this.areaMaxi = areaMaxi;
    }

    public int getAreaMini() {
        return areaMini;
    }

    public void setAreaMini(int areaMini) {
        this.areaMini = areaMini;
    }

    public int getPiecesMaxi() {
        return piecesMaxi;
    }

    public void setPiecesMaxi(int piecesMaxi) {
        this.piecesMaxi = piecesMaxi;
    }

    public int getPiecesMini() {
        return piecesMini;
    }

    public void setPiecesMini(int piecesMini) {
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
        return isSolded;
    }

    public void setIsSolded(boolean isSolded) {
        this.isSolded = isSolded;
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
