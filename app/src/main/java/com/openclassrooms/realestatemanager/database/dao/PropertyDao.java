package com.openclassrooms.realestatemanager.database.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM Property WHERE id = :id")
    Property getProperty(int id);

    @Query("SELECT * FROM Property WHERE id = :id")
    Cursor getPropertyWithCursor(int id);
}