package com.openclassrooms.realestatemanager.ui.editProperty;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.List;

public class FormPropertyFragmentViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    private String[] TYPE_LIST = {"Duplex", "Loft", "Penthouse", "Manor"};
    LiveData<List<Property>> properties = repository.getProperties();
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

    public Photo setPhoto(Bitmap photoBM, String description, Context context) {
        this.photo = Utils.saveToInternalStorage(photoBM, description,context);
        return photo;
    }
    public Photo getPhoto() {
        return photo;
    }

    public String[] loadType() {
        return TYPE_LIST;
    }
}