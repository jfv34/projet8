package com.openclassrooms.realestatemanager.ui.main;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Filter;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());

    MutableLiveData<List<Property>> properties = new MutableLiveData<>();
    MutableLiveData<Filter> filter = new MutableLiveData<>();
    List<Property> filteredProperties;

    public void loadFilter(){
        AsyncTask.execute(() ->
                filter.postValue(repository.getFilter())
        );
        ;
    }

    public void loadProperties() {

        AsyncTask.execute(() ->
                properties.postValue(repository.getProperties())
        );
    }


    public void setProperty(Property property) {
        AsyncTask.execute(() ->
                repository.createProperty(property)
        );
    }

    public void loadFilteredProperties() {
        filteredProperties = new ArrayList<>();
        List<String> cities = filter.getValue().getCities();
        filteredProperties = repository.getFilteredProperties(cities);
        AsyncTask.execute(() ->
                properties.postValue(filteredProperties)
        );
    }
}