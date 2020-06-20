package com.openclassrooms.realestatemanager.ui.editProperty;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.repositories.Constants;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.ArrayList;

import static com.openclassrooms.realestatemanager.Utils.convertDateToString;

public class FormPropertyFragmentViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    private String[] TYPE_LIST = Constants.TYPE_LIST;
    private String[] AVAILABILITY_LIST ={"Unspecified", "Available","Sold"} ;

    public MutableLiveData<ArrayList<Photo>> photos = new MutableLiveData<>();
    public MutableLiveData<Status> status = new MutableLiveData<>();
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
    public MutableLiveData<String> soldDate = new MutableLiveData<>();
    public MutableLiveData<String> entryDate = new MutableLiveData<>();

    public void loadProperty(int id) {
        AsyncTask.execute(() -> {
                    Property result = repository.getProperty(id);
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
                    entryDate.postValue(result.getEntryDate());
                    status.postValue(result.getStatus());
                }
        );
    }

    public void setProperty_in_database(Property property) {
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

    public void updatePhotoDescription(String description, int position) {
        ArrayList newPhotos = new ArrayList();
        if (photos.getValue() != null) newPhotos.addAll(photos.getValue());
        Photo newPhoto = (Photo) newPhotos.get(position);
        newPhoto.setDescription(description);
        newPhotos.set(position,newPhoto);
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
        if(photos.getValue()!=null)newPhotos.addAll(photos.getValue());
        newPhotos.remove(position);
        photos.postValue(newPhotos);
    }

    public String[] getAvailabilities() {
        return AVAILABILITY_LIST;
    }

    public String[] getTypes() {
        return TYPE_LIST;
    }

    public void setSoldDate(int year, int month, int dayOfMonth) {

        String formattedDate = convertDateToString(year, month, dayOfMonth);
        soldDate.setValue(formattedDate);
    }

    public void setAvailableDate(int year, int month, int dayOfMonth) {
        String formattedDate = convertDateToString(year, month, dayOfMonth);
        entryDate.setValue(formattedDate);
    }
}