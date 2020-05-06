package com.openclassrooms.realestatemanager.ui.filter;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Property;

import java.util.ArrayList;

public class SharedFilterViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Property>> filterProperties = new MutableLiveData<>();

    public void setFilterProperties(ArrayList<Property> newFilterProperties) {
        AsyncTask.execute(() -> {
                    filterProperties.postValue(newFilterProperties);
                }
        );
    }
}