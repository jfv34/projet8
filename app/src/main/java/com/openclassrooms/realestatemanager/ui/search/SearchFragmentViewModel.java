package com.openclassrooms.realestatemanager.ui.search;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.List;

public class SearchFragmentViewModel extends ViewModel {

   public MutableLiveData<List<Property>> propertySearch = new MutableLiveData<>();

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());

    public void loadPropertyByCity(String city) {
        AsyncTask.execute(() -> {
                    List<Property> propertiesByCity = repository.getPropertiesByCity(city);
                    propertySearch.postValue(propertiesByCity);
                }
        );
    }
}
