package com.openclassrooms.realestatemanager.models;

import java.util.ArrayList;

public class Filter {

    private int id;
    private ArrayList<Type> types;
    private int priceMaxi;
    private int priceMini;
    private ArrayList<String> cities;
    private ArrayList<String> states;
    private int areaMaxi;
    private int areaMini;
    private int piecesMaxi;
    private int piecesMini;
    private ArrayList<String> interestPoints;
    private ArrayList<String> agentName;
    private Status status;
    private String entryDate;
    private String saleDate;
    private int numberOfPhotosMaxi;
    private int numberOfPhotosMini;

    public Filter(ArrayList<Type> types, int priceMaxi, int priceMini, ArrayList<String> cities,
                  ArrayList<String> states, int areaMaxi, int areaMini, int piecesMaxi, int piecesMini,
                  ArrayList<String> interestPoints, ArrayList<String> agentName, Status status, String entryDate, String saleDate,
                  int numberOfPhotosMaxi, int numberOfPhotosMini) {
        this.types = types;
        this.priceMaxi = priceMaxi;
        this.priceMini = priceMini;
        this.cities = cities;
        this.states = states;
        this.areaMaxi = areaMaxi;
        this.areaMini = areaMini;
        this.piecesMaxi = piecesMaxi;
        this.piecesMini = piecesMini;
        this.interestPoints = interestPoints;
        this.agentName = agentName;
        this.status = status;
        this.entryDate = entryDate;
        this.saleDate = saleDate;
        this.numberOfPhotosMaxi = numberOfPhotosMaxi;
        this.numberOfPhotosMini = numberOfPhotosMini;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public int getPriceMaxi() {
        return priceMaxi;
    }

    public int getPriceMini() {
        return priceMini;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public int getAreaMaxi() {
        return areaMaxi;
    }

    public int getAreaMini() {
        return areaMini;
    }

    public int getPiecesMaxi() {
        return piecesMaxi;
    }

    public int getPiecesMini() {
        return piecesMini;
    }

    public ArrayList<String> getInterestPoints() {
        return interestPoints;
    }

    public ArrayList<String> getAgentName() {
        return agentName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public int getNumberOfPhotosMaxi() {
        return numberOfPhotosMaxi;
    }

    public int getNumberOfPhotosMini() {
        return numberOfPhotosMini;
    }
}
