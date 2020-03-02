package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.PhotoURI;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.ArrayList;
import java.util.List;

public class PropertyDataRepository {

    private final PropertyDao propertyDao;

    public PropertyDataRepository(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }

    public ArrayList<PhotoURI> loadPhotos(int propertyId) {

        ArrayList<PhotoURI> photosURI = this.propertyDao.getProperties().getValue().get(propertyId).getPhotosURI();
        return photosURI;
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