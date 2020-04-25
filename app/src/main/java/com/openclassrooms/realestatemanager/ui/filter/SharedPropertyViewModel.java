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

            filterByCities();
            filterByStates();
            filterByInterestPoints();
            filterByAgent();


            properties.postValue(newProperties);
        }
    }

    private void filterByInterestPoints() {
        ArrayList<String> InterestPointsFilter = filter.getValue().getInterestPoints();
        if (!InterestPointsFilter.get(0).equals("")) {
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                String InterestPoint = property.getInterestPoint();
                boolean one_of_them = false;
                for (int j = 0; j < InterestPointsFilter.size(); j++) {
                    String InterestPointFilter = InterestPointsFilter.get(j);
                    if (InterestPoint.toUpperCase().equals(InterestPointFilter.toUpperCase())) {
                        one_of_them = true;
                    }
                }
                if (!one_of_them) {
                    newProperties.remove(property);
                }
            }
        }
    }

    private void filterByStates() {
        ArrayList<String> statesFilter = filter.getValue().getStates();
        if (!statesFilter.get(0).equals("")) {
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                String state = property.getState();
                boolean one_of_them = false;
                for (int j = 0; j < statesFilter.size(); j++) {
                    String stateFilter = statesFilter.get(j);
                    if (state.toUpperCase().equals(stateFilter.toUpperCase())) {
                        one_of_them = true;
                    }
                }
                if (!one_of_them) {
                    newProperties.remove(property);
                }
            }
        }
    }

    private void filterByCities() {
        ArrayList<String> citiesFilter = filter.getValue().getCities();
        if (!citiesFilter.get(0).equals("")) {
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                String city = property.getCity();
                boolean one_of_them = false;
                for (int j = 0; j < citiesFilter.size(); j++) {
                    String cityFilter = citiesFilter.get(j);
                    if (city.toUpperCase().equals(cityFilter.toUpperCase())) {
                        one_of_them = true;
                    }
                }
                if (!one_of_them) {
                    newProperties.remove(property);
                }
            }
        }
    }

    private void filterByAgent() {
        ArrayList<String> agentsFilter = filter.getValue().getAgentName();
        if (!agentsFilter.get(0).equals("")) {
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                String agent = property.getAgentName();
                boolean one_of_them = false;
                for (int j = 0; j < agentsFilter.size(); j++) {
                    String agentFilter = agentsFilter.get(j);
                    if (agent.toUpperCase().equals(agentFilter.toUpperCase())) {
                        one_of_them = true;
                    }
                }
                if (!one_of_them) {
                    newProperties.remove(property);
                }
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
    public ArrayList<String> getFilter(String list_txt) {
        ArrayList<String> filter = new ArrayList<>();

        int previous = 0;
        for(int i=0;i<list_txt.length();i++){
            if(list_txt.charAt(i)==','){
                filter.add(list_txt.substring(previous, i).trim());
                previous=i+1;
            }
        }
        filter.add(list_txt.substring(previous).trim());
        return filter;
    }
}
