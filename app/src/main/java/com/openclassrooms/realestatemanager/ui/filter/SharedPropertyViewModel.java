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

import java.util.ArrayList;
import java.util.List;

public class SharedPropertyViewModel extends ViewModel {

    public MutableLiveData<Filter> filter = new MutableLiveData<>();
    public MutableLiveData<Integer> pieces = new MutableLiveData<>();
    public MutableLiveData<Integer> numberOfPhotos = new MutableLiveData<>();
    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    private String[] TYPE_LIST = repository.getTypes();
    private String[] AVAILABILITY_LIST = repository.getAvailability();
    private ArrayList<Property> newProperties;


    public void loadProperties() {

        AsyncTask.execute(() ->
                properties.postValue(repository.getProperties())
        );
    }


    public void filter() {
        if (filter.getValue() != null && properties.getValue() != null) {

            newProperties = new ArrayList<>();
            newProperties.addAll(properties.getValue());

            filterByAgent();
            filterByCities();

            properties.postValue(newProperties);

        }
    }

    private void filterByCities() {
        ArrayList<String> citiesFilter = filter.getValue().getCities();
        for (int i = 0; i < properties.getValue().size(); i++) {
            Property property = properties.getValue().get(i);
            String city = property.getCity();
            boolean one_of_them = false;
            for (int j = 0; j < citiesFilter.size(); j++) {
                String cityFilter = citiesFilter.get(j);
                if (city.equals(cityFilter) && !cityFilter.equals("")) {
                    one_of_them = true;
                }
            }
            if (one_of_them == false) {
                newProperties.remove(property);
            }
        }
    }

    private void filterByAgent() {
        String agentFilter = filter.getValue().getAgentName();

        for (int i = 0; i < properties.getValue().size(); i++) {
            Property property = properties.getValue().get(i);
            String agent = property.getAgentName();
            if (!agent.equals(agentFilter) && !agentFilter.equals("")) {
                newProperties.remove(property);
            }
        }
    }


    public void setFilter(Filter newFilter) {
        filter.postValue(newFilter);
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
}
