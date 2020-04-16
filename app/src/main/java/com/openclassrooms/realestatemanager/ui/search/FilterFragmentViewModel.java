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

public class FilterFragmentViewModel extends ViewModel {

   public MutableLiveData<List<Property>> propertiesByCity = new MutableLiveData<>();

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    private String[] TYPE_LIST = repository.getTypes();

    public void loadPropertyByCity(String city) {
        AsyncTask.execute(() -> {
                    propertiesByCity.postValue(repository.getPropertiesByCity(city));
                }
        );
    }

    public String[] getTYPES() {
        return TYPE_LIST;
    }
}
