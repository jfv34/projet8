package com.openclassrooms.realestatemanager.ui.editProperty;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
    MutableLiveData<ArrayList<Photo>> photos= new MutableLiveData<>();
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
        ArrayList newPhotos = new ArrayList();
        int size;
        if (photos.getValue() == null) {size = 0;}
        else {
            size = photos.getValue().size();
        }


        for (int i = 0; i < size; i++) {
            newPhotos.add(photos.getValue().get(i));
        }
        newPhotos.add(photo);
        photos.postValue(newPhotos);
    }

    public Photo getPhoto() {
        return photo;
    }

    public String[] loadType() {
        return TYPE_LIST;
    }


    public ArrayList<Photo> getPhotos() {
        return photos.getValue();
    }

    public void loadPhotos(int property) {
        ArrayList<Photo> photosToAdd;
        photosToAdd = loadProperty(property).getPhotos();
        photos.setValue(photosToAdd);
    }

    public void deletePhoto(int position) {
        ArrayList newPhotos = new ArrayList();
        int size = photos.getValue().size();

        for(int i=0; i< size;i++){
            if (i != position) {
                newPhotos.add(photos.getValue().get(i));
            }
        photos.postValue(newPhotos);
    }
}}