package com.openclassrooms.realestatemanager.ui.editProperty;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.R;
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
    private String[] AVAILABILITY_LIST = {"available","sold"};

    public MutableLiveData<Property> property = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Photo>> photos = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSold = new MutableLiveData<>();
    public MutableLiveData<String> price = new MutableLiveData<>();
    public MutableLiveData<String> type = new MutableLiveData<>();
    public MutableLiveData<String> city = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<String> state = new MutableLiveData<>();
    public MutableLiveData<String> zip = new MutableLiveData<>();
    public MutableLiveData<String> area = new MutableLiveData<>();
    public MutableLiveData<String> pieces = new MutableLiveData<>();
    public MutableLiveData<String> interestpoints = new MutableLiveData<>();
    public MutableLiveData<String> description = new MutableLiveData<>();
    public MutableLiveData<String> agent = new MutableLiveData<>();
    public MutableLiveData<String> entrydate = new MutableLiveData<>();
    public MutableLiveData<String> soldDate = new MutableLiveData<>();
    public MutableLiveData<String> availableDate = new MutableLiveData<>();
    Photo photo;

    public void loadProperty(int id) {
        AsyncTask.execute(() -> {
                    Property result = repository.getProperty(id);
                    property.postValue(result);
                    photos.postValue(result.getPhotos());
                    price.postValue(result.getPrice());
                    type.postValue(result.getType());
                    city.postValue(result.getCity());
                    address.postValue(result.getAddress());
                    state.postValue(result.getState());
                    zip.postValue(result.getZip());
                    area.postValue(result.getArea());
                    pieces.postValue(result.getPieces());
                    interestpoints.postValue(result.getInterestPoint());
                    description.postValue(result.getDescription());
                    agent.postValue(result.getAgentName());
                    soldDate.postValue(result.getSaleDate());
                    entrydate.postValue(result.getEntryDate());
                }
        );
    }

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

    public void updatePhotoDescription(Photo photo, int position) {
        ArrayList newPhotos = new ArrayList();
        int size;
        if (photos.getValue() == null) {size = 0;}
        else {
            size = photos.getValue().size();
        }
        for (int i = 0; i < size; i++) {
            if(i!=position){newPhotos.add(photos.getValue().get(i));}
            else {newPhotos.add(photo);}
        }
        photos.postValue(newPhotos);
    }

    public Photo getPhoto(int position) {
        return photos.getValue().get(position);
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

        String formattedDate = convertDate(year, month, dayOfMonth);
        soldDate.setValue(formattedDate);
    }

    public void setAvailableDate(int year, int month, int dayOfMonth) {
        String formattedDate = convertDate(year, month, dayOfMonth);
        availableDate.setValue(formattedDate);
    }

    private String convertDate(int year, int month, int dayOfMonth) {
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

        return simpleDateFormat.format(dateSelected);
    }
}