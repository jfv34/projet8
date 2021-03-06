package com.openclassrooms.realestatemanager.ui.details;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.ArrayList;

public class DetailViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    public MutableLiveData<ArrayList<Photo>> photos = new MutableLiveData<>();
    public MutableLiveData<Property> property = new MutableLiveData<>();

    void loadProperty(int bundleProperty) {

        AsyncTask.execute(() -> {
            Property result = repository.getProperty(bundleProperty);
            if (result != null) {
                photos.postValue(result.getPhotos());
                property.postValue(result);
            }
        });
    }
}
