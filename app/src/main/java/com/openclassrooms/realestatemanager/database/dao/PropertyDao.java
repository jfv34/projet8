package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

public interface PropertyDao {

    @Query("SELECT * FROM Property WHERE id = :id")
    LiveData<List<Property>> getProperty(int id);

    @Insert
    int insertProperty(Property property);

    @Update
    int updateProperty(Property property);

    @Query("DELETE FROM Property WHERE id = :id")
    int deleteProperty(int id);
}
