package com.openclassrooms.realestatemanager.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.List;

public class MainFragmentViewModel extends ViewModel {

    private final PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    //private final PropertyRepository repository = new FakePropertiesRepository();

    //private final PropertyDataRepository propertyDataRepository;
    //private final Executor executor;
    LiveData<List<Property>> properties = repository.getProperties();

    public MainFragmentViewModel() {
        //this.propertyDataRepository = propertyDataRepository;
       //this.executor = executor;
    }

    public void loadProperties() {
        //List<Property> results = repository.getProperties();
        //properties.setValue(results);
    }
}