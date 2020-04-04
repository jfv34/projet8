package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

public interface PropertyRepository {
    List<Property> getProperties();

    void createProperty(Property property);

    void updateProperty(Property property);

    Property getProperty(int id);


}

