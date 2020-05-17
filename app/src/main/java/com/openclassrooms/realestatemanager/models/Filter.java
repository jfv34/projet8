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

    public void setTypes(ArrayList<Type> types) {
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

    public ArrayList<String> getInterestPoints() {
        return interestPoints;
    }

    public void setInterestPoints(ArrayList<String> interestPoints) {
        this.interestPoints = interestPoints;
    }

    public ArrayList<String> getAgentName() {
        return agentName;
    }

    public void setAgentName(ArrayList<String> agentName) {
        this.agentName = agentName;
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

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public int getNumberOfPhotosMaxi() {
        return numberOfPhotosMaxi;
    }

    public void setNumberOfPhotosMaxi(int numberOfPhotosMaxi) {
        this.numberOfPhotosMaxi = numberOfPhotosMaxi;
    }

    public int getNumberOfPhotosMini() {
        return numberOfPhotosMini;
    }

    public void setNumberOfPhotosMini(int numberOfPhotosMini) {
        this.numberOfPhotosMini = numberOfPhotosMini;
    }
}
