package com.openclassrooms.realestatemanager.ui.filter;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Filter;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.List;

public class FilterFragmentViewModel extends ViewModel {

    public MutableLiveData<List<Property>> propertiesByCity = new MutableLiveData<>();
    public MutableLiveData<Filter> filter = new MutableLiveData<>();
    public MutableLiveData<Integer> pieces = new MutableLiveData<>();
    public MutableLiveData<Integer> numberOfPhotos = new MutableLiveData<>();


    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    private String[] TYPE_LIST = repository.getTypes();
    private String[] AVAILABILITY_LIST = repository.getAvailability();

    public void loadPropertyByCity(String city) {
        AsyncTask.execute(() -> {
                    propertiesByCity.postValue(repository.getPropertiesByCity(city));
                }
        );
    }

    public void initialize() {

        if (pieces.getValue() ==null) {
            pieces.postValue(0);
        }
        ;
        if (numberOfPhotos.getValue() == null) {
            numberOfPhotos.postValue(0);
        }
    }


    public String[] getTYPES() {
        return TYPE_LIST;
    }

    public String[] getAvailabilities() {return AVAILABILITY_LIST; }

    public void setPieces(int i) {
        if (pieces.getValue() != null) {
            if (pieces.getValue() + i >= 0) {
                pieces.postValue(pieces.getValue() + i);
            }
        }
    }

    public void setNumberOfPhotos(int i) {
        if (numberOfPhotos.getValue() != null) {
            if (numberOfPhotos.getValue() + i >= 0) {
                numberOfPhotos.postValue(numberOfPhotos.getValue() + i);
            }
        }
    }

    public void setFilter(Filter filter) {
        repository.setFilter(filter);
    }
}
