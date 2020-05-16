package com.openclassrooms.realestatemanager.ui.filter;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Filter;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.repositories.Constants;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.openclassrooms.realestatemanager.Utils.convertDateToString;

public class FilterFragmentViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();
    public MutableLiveData<String> soldDate = new MutableLiveData<>();
    public MutableLiveData<String> entryDate = new MutableLiveData<>();
    public MutableLiveData<List<String>> typesFilter = new MutableLiveData<>();
    public MutableLiveData<Filter> savedFiltred = new MutableLiveData<>();

    private ArrayList<Property> filterProperties;
    private Filter filter;
    private String[] TYPE_LIST = Constants.TYPE_LIST;
    private String[] AVAILABILITY_LIST = {"All", "Available","Sold"} ;

    public String[] getTYPES() {
        return TYPE_LIST;
    }

    public String[] getAvailabilities() {
        return AVAILABILITY_LIST;
    }

    public void setSoldDate(int year, int month, int dayOfMonth) {

        String formattedDate = convertDateToString(year, month, dayOfMonth);
        soldDate.setValue(formattedDate);
    }

    public void setAvailableDate(int year, int month, int dayOfMonth) {
        String formattedDate = convertDateToString(year, month, dayOfMonth);
        entryDate.setValue(formattedDate);
    }

    public ArrayList<String> getFilterListInForm(String list_txt) {
        ArrayList<String> filter = new ArrayList<>();

        int previous = 0;
        for (int i = 0; i < list_txt.length(); i++) {
            if(list_txt.charAt(i)==','){
                filter.add(list_txt.substring(previous, i).trim());
                previous=i+1;
            }
        }
        filter.add(list_txt.substring(previous).trim());
        return filter;
    }

    public ArrayList<Property> filter(Filter filter) {

        this.filter = filter;

        if (properties.getValue() != null) {
            filterProperties = new ArrayList<>();
            filterProperties.addAll(properties.getValue());

            filterByTypes();
            filterByPrice();
            filterByCities();
            filterByStates();
            filterByArea();
            filterByPieces();
            filterByInterestPoints();
            filterByAgent();
            filterByDates();
            filterByPhotos();
            filterByStatus();
        }

        return filterProperties;
    }

    public void loadProperties() {

        AsyncTask.execute(() -> {
                    properties.postValue(repository.getProperties());
                }
        );
    }

    private void filterByInterestPoints() {
        ArrayList<String> interestPointsFilter = filter.getInterestPoints();
        if (!interestPointsFilter.get(0).equals("")) {
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                String interestPoint = property.getInterestPoint();
                filter_for_list(property, interestPoint, interestPointsFilter);
            }
        }
    }

    private void filterByStates() {
        ArrayList<String> statesFilter = filter.getStates();
        if (!statesFilter.get(0).equals("")) {
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                String state = property.getState();
                filter_for_list(property, state, statesFilter);
            }
        }
    }

    private void filterByTypes() {
        ArrayList<String> typesFilter = filter.getTypes();
        if (typesFilter.size() >0) {
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                String type = property.getType();
                filter_for_list(property, type, typesFilter);
            }
        }
    }

    private void filterByCities() {
        ArrayList<String> citiesFilter = filter.getCities();
        if (!citiesFilter.get(0).equals("")) {
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                String city = property.getCity();
                filter_for_list(property, city, citiesFilter);
            }
        }
    }

    private void filterByAgent() {
        ArrayList<String> agentsFilter = filter.getAgentName();
        if (!agentsFilter.get(0).equals("")) {
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                String agent = property.getAgentName();
                filter_for_list(property, agent, agentsFilter);
            }
        }
    }

    private void filter_for_list(Property property, String data, ArrayList<String> dataListFilter) {
        boolean one_of_them = false;
        for (int j = 0; j < dataListFilter.size(); j++) {
            String cityFilter = dataListFilter.get(j);
            if (data.toUpperCase().equals(cityFilter.toUpperCase())) {
                one_of_them = true;
            }
        }
        if (!one_of_them) {
            filterProperties.remove(property);
        }
    }

    private void filterByDates() {
        Date entryDateFilter = Utils.convertStringToDate(filter.getEntryDate());
        Date soldeDateFilter = Utils.convertStringToDate(filter.getSaleDate());

        for (int i = 0; i < properties.getValue().size(); i++) {
            Property property = properties.getValue().get(i);
            Date entryDate = Utils.convertStringToDate(property.getEntryDate());
            Date soldeDate = Utils.convertStringToDate(property.getSaleDate());

            if (entryDateFilter != null && entryDate != null && entryDate.compareTo(entryDateFilter) < 0)
                filterProperties.remove(property);

            if (soldeDateFilter != null && soldeDate != null)
                if (soldeDate.compareTo(soldeDateFilter) < 0)
                    filterProperties.remove(property);
        }
    }

    private void filterByPhotos() {
        int nbOfPhotosMini = filter.getNumberOfPhotosMini();
        int nbOfPhotosMaxi = filter.getNumberOfPhotosMaxi();
        if (nbOfPhotosMini > 0 || nbOfPhotosMaxi < Constants.slider_photos_maximum) {
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                int nbOfPhotos;
                if (property.getPhotos() == null) {
                    nbOfPhotos = 0;
                } else {
                    nbOfPhotos = property.getPhotos().size();
                }
                if (nbOfPhotos < nbOfPhotosMini || nbOfPhotos > nbOfPhotosMaxi)
                    filterProperties.remove(property);
            }
        }
    }

    private void filterByPieces() {
        int piecesMini = filter.getPiecesMini();
        int piecesMaxi = filter.getPiecesMaxi();
        if (piecesMini > Constants.slider_pieces_minimum || piecesMaxi < Constants.slider_pieces_maximum) {
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                if (!property.getPieces().isEmpty()) {
                    int pieces = Integer.parseInt(property.getPieces());
                    if (pieces < piecesMini || pieces > piecesMaxi)
                        filterProperties.remove(property);
                }
            }
        }
    }

    private void filterByArea() {
        int areaMini = filter.getAreaMini();
        int areaMaxi = filter.getAreaMaxi();
        if (areaMini > Constants.slider_area_minimum || areaMaxi < Constants.slider_area_maximum) {
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                if (!property.getArea().isEmpty()) {
                    int area = Integer.parseInt(property.getArea());
                    if (area < areaMini || area > areaMaxi)
                        filterProperties.remove(property);
                }
            }
        }
    }

    private void filterByPrice() {
        int priceMini = filter.getPriceMini();
        int priceMaxi = filter.getPriceMaxi();
        if (priceMini > Constants.slider_price_minimum || priceMaxi < Constants.slider_price_maximum)
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                if (!property.getPrice().isEmpty()) {
                    int price = Integer.parseInt(property.getPrice());
                    if (price < priceMini || price > priceMaxi)
                        filterProperties.remove(property);
                }
            }
    }

    private void filterByStatus() {
        Status statusFilter = filter.getStatus();
        if(statusFilter!=Status.UNSPECIFIED){
        for (int i = 0; i < properties.getValue().size(); i++) {
            Property property = properties.getValue().get(i);
            Status status = property.getStatus();
            if (status != statusFilter) {
                filterProperties.remove(property);
            }
        }
    }}

    public void setType(String types, boolean selected) {
        ArrayList<String> newTypes = (ArrayList<String>) typesFilter.getValue();

        if (!selected && newTypes.contains(types)) {
            newTypes.remove(types);
        }
        if (selected && !newTypes.contains(types)) {
            newTypes.add(types);
        }

        typesFilter.postValue(newTypes);
    }

    public void initTypesFilter() {
        ArrayList<String> emptyList = new ArrayList<>();
        typesFilter.postValue(emptyList);
    }

    public String getTypeInString(String toString) {
        // todo ...
        return null;
    }
}