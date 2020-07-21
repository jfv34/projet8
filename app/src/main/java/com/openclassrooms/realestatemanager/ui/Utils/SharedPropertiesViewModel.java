package com.openclassrooms.realestatemanager.ui.Utils;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.List;

public class SharedPropertiesViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();
    public boolean isFiltered = false;

    public void loadProperties() {

        AsyncTask.execute(() -> {
            properties.postValue(repository.getProperties());
                }
        );
    }

    public void setProperty(Property newProperty, int bundleId) {
        if (newProperty != null && properties.getValue() != null) {
            List<Property> newProperties = properties.getValue();
            for (int i = 0; i < properties.getValue().size(); i++) {
                if (properties.getValue().get(i).getId() == bundleId) {
                    newProperties.remove(i);
                    newProperties.add(i, newProperty);
                }
            }
            properties.postValue(newProperties);
        }
    }
}