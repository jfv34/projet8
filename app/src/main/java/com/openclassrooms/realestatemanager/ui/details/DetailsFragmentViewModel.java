package com.openclassrooms.realestatemanager.ui.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.PhotoURI;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragmentViewModel extends ViewModel {

    MutableLiveData<ArrayList<PhotoURI>> photos = new MutableLiveData<>();

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    LiveData<List<Property>> properties = repository.getProperties();


    public void loadPhotos(int propertyClicked) {

        //return PropertyDataRepository.loadPhotos(propertyClicked);

        if (propertyClicked != 0) {
            // photos.postValue(newPhotos);}
        }
    }




}
