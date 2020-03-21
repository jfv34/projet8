package com.openclassrooms.realestatemanager.database;

import androidx.room.TypeConverter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.models.Photo;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<Photo> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Photo>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Photo> photos) {
        Gson gson = new Gson();
        return gson.toJson(photos);
    }
}

