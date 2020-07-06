package com.openclassrooms.realestatemanager.ui.filter;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.List;

public class SharedFilterViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();
    public boolean isFiltred = false;

    public void loadProperties() {

        AsyncTask.execute(() -> {
            properties.postValue(repository.getProperties());
                }
        );
    }

    public void setProperty_for_shared(Property newProperty) {
        if (newProperty != null && properties.getValue() != null) {
            List<Property> newProperties = properties.getValue();
            for (int i = 0; i < properties.getValue().size(); i++) {
                if (properties.getValue().get(i).getId() == newProperty.getId()) {
                    newProperties.remove(i);
                    newProperties.add(i, newProperty);
                }
            }
            properties.postValue(newProperties);
        }
    }
}