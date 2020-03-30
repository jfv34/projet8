package com.openclassrooms.realestatemanager.ui.editProperty;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.ArrayList;
import java.util.List;

public class FormPropertyFragmentViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    private String[] TYPE_LIST = {"Duplex", "Loft", "Penthouse", "Manor"};
    LiveData<List<Property>> properties = repository.getProperties();
    MutableLiveData<List<Photo>> photos= new MutableLiveData<>();
    Photo photo;

    public void setProperty(Property property) {
        AsyncTask.execute(() ->
                repository.createProperty(property)
        );
    }

    public void updateProperty(Property property, int id) {
        property.setId(id);
        AsyncTask.execute(() ->
                repository.updateProperty(property)
        );
    }

    public Property loadProperty(int id){
        return properties.getValue().get(id);
    }

    public void setPhoto(Photo photo) {
        photos.getValue().add(photo);
    }
    public Photo getPhoto() {
        return photo;
    }

    public String[] loadType() {
        return TYPE_LIST;
    }


    public List<Photo> getPhotos() {
        return photos.getValue();
    }

    public void loadPhotos(int property) {
        List<Photo> photosToAdd;
        photosToAdd = loadProperty(property).getPhotos();
        photos.setValue(photosToAdd);
    }
}