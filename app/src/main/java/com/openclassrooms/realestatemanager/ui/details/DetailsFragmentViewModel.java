package com.openclassrooms.realestatemanager.ui.details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
