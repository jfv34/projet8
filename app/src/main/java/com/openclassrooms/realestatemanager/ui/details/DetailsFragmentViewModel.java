package com.openclassrooms.realestatemanager.ui.details;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragmentViewModel extends ViewModel {

    MutableLiveData<List<String>> photos = new MutableLiveData<>();


    public void loadPhotos(String propertyClicked) {
        if(!propertyClicked.isEmpty()){
        ArrayList<String> newPhotos = new ArrayList<>();
        newPhotos.add("photo 1 of property number " + propertyClicked);
        newPhotos.add("photo 2");
        newPhotos.add("photo 3");
        newPhotos.add("photo 4");
        newPhotos.add("photo 5");
        newPhotos.add("photo 6");
        newPhotos.add("photo 7");
        newPhotos.add("photo 8");

        photos.postValue(newPhotos);}
    }
}
