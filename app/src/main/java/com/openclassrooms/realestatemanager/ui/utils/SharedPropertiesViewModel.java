package com.openclassrooms.realestatemanager.ui.utils;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.ArrayList;
import java.util.List;

public class SharedPropertiesViewModel extends ViewModel {

    private PropertyRepository repository;
    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();
    public boolean isFiltered = false;

    public void loadProperties() {
        repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
        AsyncTask.execute(() -> properties.postValue(repository.getProperties()));
    }

    public void setProperty(Property newProperty) {
        if (newProperty != null && properties.getValue() != null) {
            ArrayList<Property> newProperties = new ArrayList<>();
            for (int i = 0; i < properties.getValue().size(); i++) {
                if (properties.getValue().get(i).getId() == newProperty.id) {
                    newProperties.add(i, newProperty);
                }else {
                    newProperties.add(properties.getValue().get(i));
                }
            }
            properties.postValue(newProperties);
        }
    }
}