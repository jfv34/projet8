package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

public interface PropertyRepository {
    LiveData<List<Property>> getProperties();

    void createProperty(Property property);

    void suppressProperty(int id);
}

