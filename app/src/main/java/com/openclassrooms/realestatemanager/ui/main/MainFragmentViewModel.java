package com.openclassrooms.realestatemanager.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentViewModel extends ViewModel {

    MutableLiveData<List<String>> properties = new MutableLiveData<>();

    public void loadProperties() {
        ArrayList<String> result = new ArrayList<>();

        result.add("Flat");
        result.add("Duplex");
        result.add("Flat");
        result.add("House");
        result.add("Flat");
        result.add("Duplex");
        result.add("Flat");
        result.add("House");
        result.add("Flat");
        result.add("Duplex");
        result.add("Flat");
        result.add("House");
        result.add("Flat");
        result.add("Duplex");
        result.add("Flat");
        result.add("House");

        properties.setValue(result);
    }
}
