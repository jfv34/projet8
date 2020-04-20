package com.openclassrooms.realestatemanager.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.models.Filter;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

@Dao
public interface PropertyDao {

    @Query("SELECT * FROM Property")
    List<Property> getProperties();

    @Insert
    long insertProperty(Property property);

    @Update
    int updateProperty(Property property);

    @Query("DELETE FROM Property WHERE id = :id")
    int deleteProperty(int id);

    @Query("SELECT * FROM Property WHERE id = :id")
    Property getProperty(int id);

    @Query("SELECT * FROM Property WHERE city = :city")
    List<Property> getPropertiesByCity(String city);

    @Query("SELECT * FROM Property WHERE city IN (:cities)")
    List<Property> getFilteredProperties(List<String> cities);

}