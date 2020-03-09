package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

public class DataPropertiesRepository implements PropertyRepository {

    private final PropertyDao propertyDao;

    public DataPropertiesRepository(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }

    @Override
    public LiveData<List<Property>> getProperties() {
        return propertyDao.getProperties();
    }
}