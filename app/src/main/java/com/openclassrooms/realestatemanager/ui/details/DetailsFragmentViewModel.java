package com.openclassrooms.realestatemanager.ui.details;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.PhotoURI;

import java.util.ArrayList;

public class DetailsFragmentViewModel extends ViewModel {

    MutableLiveData<ArrayList<PhotoURI>> photos = new MutableLiveData<>();


    public void loadPhotos(int propertyClicked) {

        //return PropertyDataRepository.loadPhotos(propertyClicked);

        if (propertyClicked != 0) {
            // photos.postValue(newPhotos);}
        }
    }
}
