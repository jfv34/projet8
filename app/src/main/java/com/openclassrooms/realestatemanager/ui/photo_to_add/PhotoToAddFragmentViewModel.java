package com.openclassrooms.realestatemanager.ui.photo_to_add;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.List;

public class PhotoToAddFragmentViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());

    LiveData<List<Property>> properties = repository.getProperties();



    public void updateProperty(Property property) {
        AsyncTask.execute(() ->
                repository.updateProperty(property)
        );
    }

    public void setProperty(Property property) {
        AsyncTask.execute(() ->
                repository.createProperty(property)
        );
    }
}