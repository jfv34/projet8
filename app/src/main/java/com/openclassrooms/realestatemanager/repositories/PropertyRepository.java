package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.ArrayList;
import java.util.List;

public interface PropertyRepository {
    LiveData<List<Property>> getProperties();

    void createProperty(Property property);

    void updateProperty(Property property);

}

