package com.openclassrooms.realestatemanager.ui.main;

import java.util.ArrayList;

public class MainFragmentPresenter implements MainFragmentContract.Presenter {

    private ArrayList<String> testList = new ArrayList<>();

    @Override
    public ArrayList<String> getMainDatas() {

        testList.add("Flat");
        testList.add("Duplex");
        testList.add("Flat");
        testList.add("House");
        testList.add("Flat");
        testList.add("Duplex");
        testList.add("Flat");
        testList.add("House");
        testList.add("Flat");
        testList.add("Duplex");
        testList.add("Flat");
        testList.add("House");
        testList.add("Flat");
        testList.add("Duplex");
        testList.add("Flat");
        testList.add("House");

        return testList;
    }
}
