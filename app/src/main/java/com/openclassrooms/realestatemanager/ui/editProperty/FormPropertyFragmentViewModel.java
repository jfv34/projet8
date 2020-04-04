package com.openclassrooms.realestatemanager.ui.editProperty;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.ArrayList;

public class FormPropertyFragmentViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    private String[] TYPE_LIST = {"Duplex", "Loft", "Penthouse", "Manor"};

    public MutableLiveData<Property> property = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Photo>> photos = new MutableLiveData<>();
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

    public void loadProperty(int id) {
        AsyncTask.execute(() -> {
                    Property result = repository.getProperty(id);
                    property.postValue(result);
                photos.postValue(result.getPhotos());
                }
        );
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