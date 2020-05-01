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

    public ArrayList<String> getFilter(String list_txt) {
        ArrayList<String> filter = new ArrayList<>();

        int previous = 0;
        for (int i = 0; i < list_txt.length(); i++) {
            if(list_txt.charAt(i)==','){
                filter.add(list_txt.substring(previous, i).trim());
                previous=i+1;
            }
        }
        filter.add(list_txt.substring(previous).trim());
        return filter;
    }
}