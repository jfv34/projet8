package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.ArrayList;
import java.util.List;

public class DataPropertiesRepository implements PropertyRepository {

    private final PropertyDao propertyDao;
    private ArrayList<Photo> photos;

    public DataPropertiesRepository(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }

    @Override
    public LiveData<List<Property>> getProperties() {
        return propertyDao.getProperties();
    }

    @Override
    public void createProperty(Property property){ propertyDao.insertProperty(property); }

    @Override
    public void updateProperty(Property property){ propertyDao.updateProperty(property); }

    @Override
    public void setPhotosTemporary(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public ArrayList<Photo> getPhotosTemporary() {
        return photos;
    }


}