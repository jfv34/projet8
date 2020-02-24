package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

public class PropertyDataRepository {

    private final PropertyDao propertyDao;

    public PropertyDataRepository(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }

    // --- GET ---

    public LiveData<List<Property>> getProperties() {
        return this.propertyDao.getProperties();
    }

    // --- CREATE ---

    public void createItem(Property property) {
        propertyDao.insertProperty(property);
    }

    // --- DELETE ---
    public void deleteItem(int id) {
        propertyDao.deleteProperty(id);
    }

    // --- UPDATE ---
    public void updateItem(Property property) {
        propertyDao.updateProperty(property);
    }

}