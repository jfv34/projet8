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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FormPropertyFragmentViewModel extends ViewModel {


    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    private String[] TYPE_LIST = {"Duplex", "Loft", "Penthouse", "Manor"};
    private String[] AVAILABILITY_LIST = {"Available", "Sold"};

    public MutableLiveData<Property> property = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Photo>> photos = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSold = new MutableLiveData<>();
    public MutableLiveData<String> soldDate = new MutableLiveData<>();
    public MutableLiveData<String> availableDate = new MutableLiveData<>();
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
            soldDate.postValue(result.getSaleDate());
                }
        );
    }

    public Boolean getIsSold() {
        if (isSold.getValue() == null) {
            isSold.postValue(false);
            return false;
        }
        return isSold.getValue();
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
    }

    public String[] getAvailabilities() {
        return AVAILABILITY_LIST;
    }

    public String[] getTypes() {
        return TYPE_LIST;
    }

    public void setSoldDate(int year, int month, int dayOfMonth) {

        StringBuilder frenchDate = new StringBuilder();
        frenchDate.append(dayOfMonth >= 10 ? dayOfMonth : "0" + dayOfMonth);
        frenchDate.append("/");
        frenchDate.append(month + 1);
        frenchDate.append("/");
        frenchDate.append(year);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        Date dateSelected = null;
        try {
            dateSelected = simpleDateFormat.parse(frenchDate.toString());
        } catch (ParseException e) {
        }

        String formattedDate = simpleDateFormat.format(dateSelected);

        soldDate.setValue(formattedDate);

    }

    public void setAvailableDate(int year, int month, int dayOfMonth) {

        StringBuilder frenchDate = new StringBuilder();
        frenchDate.append(dayOfMonth >= 10 ? dayOfMonth : "0" + dayOfMonth);
        frenchDate.append("/");
        frenchDate.append(month + 1);
        frenchDate.append("/");
        frenchDate.append(year);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        Date dateSelected = null;
        try {
            dateSelected = simpleDateFormat.parse(frenchDate.toString());
        } catch (ParseException e) {
        }

        String formattedDate = simpleDateFormat.format(dateSelected);

        availableDate.setValue(formattedDate);

    }
}