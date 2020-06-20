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
import java.util.List;

public class SharedDetailViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Photo>> photos = new MutableLiveData<>();
    public MutableLiveData<Property> property = new MutableLiveData<>();

    void loadProperty(int bundleProperty) {

        AsyncTask.execute(() -> {
            Property result = repository.getProperty(bundleProperty);
            photos.postValue(result.getPhotos());
            property.postValue(result);
        });
    }

    public void setProperty_for_shared(Property newProperty) {
        property.postValue(newProperty);
    }
}
