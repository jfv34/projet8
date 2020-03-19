package com.openclassrooms.realestatemanager.database;

import androidx.room.TypeConverter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.models.PhotoURI;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<PhotoURI> fromString(String value) {
        Type listType = new TypeToken<ArrayList<PhotoURI>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<PhotoURI> photos) {
        Gson gson = new Gson();
        return gson.toJson(photos);
    }
}

