package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

public class DataPropertiesRepository implements PropertyRepository {

    private final PropertyDao propertyDao;
    private final String[] TYPE_LIST = {"Duplex", "Loft", "Penthouse", "Manor"};
    private final String[] AVAILABILITY_LIST = {"available","sold"};

    public DataPropertiesRepository(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }

    @Override
    public List<Property> getProperties() {
        return propertyDao.getProperties();
    }

    @Override
    public void createProperty(Property property){ propertyDao.insertProperty(property); }

    @Override
    public void updateProperty(Property property){ propertyDao.updateProperty(property); }

    @Override
    public Property getProperty(int id) {

        return propertyDao.getProperty(id);
    }

    public String[] getTypes() {
        return TYPE_LIST;
    }

    public String[] getAvailability() {
        return AVAILABILITY_LIST;
    }
}