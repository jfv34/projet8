package com.openclassrooms.realestatemanager.ui.filter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Filter;

import java.util.ArrayList;

public class SharedFilterViewModel extends ViewModel {

    public MutableLiveData<Filter> filter = new MutableLiveData<>();

    public void setFilter(Filter newFilter) {
        filter.postValue(newFilter);
    }


}