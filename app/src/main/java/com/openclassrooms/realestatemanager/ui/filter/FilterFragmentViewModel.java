package com.openclassrooms.realestatemanager.ui.filter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.repositories.Constants;

import java.util.ArrayList;

import static com.openclassrooms.realestatemanager.Utils.convertDateToString;

public class FilterFragmentViewModel extends ViewModel {

    public MutableLiveData<String> soldDate = new MutableLiveData<>();
    public MutableLiveData<String> entryDate = new MutableLiveData<>();
    private String[] TYPE_LIST = Constants.TYPE_LIST;
    private String[] AVAILABILITY_LIST = Constants.AVAILABILITY_LIST;

    public String[] getTYPES() {
        return TYPE_LIST;
    }

    public String[] getAvailabilities() {
        return AVAILABILITY_LIST;
    }

    public void setSoldDate(int year, int month, int dayOfMonth) {

        String formattedDate = convertDateToString(year, month, dayOfMonth);
        soldDate.setValue(formattedDate);
    }

    public void setAvailableDate(int year, int month, int dayOfMonth) {
        String formattedDate = convertDateToString(year, month, dayOfMonth);
        entryDate.setValue(formattedDate);
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