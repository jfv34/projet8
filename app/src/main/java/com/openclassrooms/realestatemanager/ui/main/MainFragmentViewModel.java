package com.openclassrooms.realestatemanager.ui.main;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Filter;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.Constants;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainFragmentViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();
    private Filter filter;
    private ArrayList<Property> newProperties;
    private String[] TYPE_LIST = Constants.TYPE_LIST;
    private String[] AVAILABILITY_LIST = Constants.AVAILABILITY_LIST;

    public void loadProperties() {

        AsyncTask.execute(() -> {
                    properties.postValue(repository.getProperties());
                }
        );
    }

    public void filter() {
        Log.i("tag_filter ","ok");

        if (filter != null && properties.getValue() != null) {
            Log.i("tag_filter_propert ","ok");
            newProperties = new ArrayList<>();
            newProperties.addAll(properties.getValue());

            filterByPrice();
            filterByCities();
            filterByStates();
            filterByArea();
            filterByPieces();
            filterByInterestPoints();
            filterByAgent();
            filterByDates();
            filterByPhotos();

            properties.postValue(newProperties);
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
                newProperties.remove(property);

            if (soldeDateFilter != null && soldeDate != null)
                if (soldeDate.compareTo(soldeDateFilter) < 0)
                    newProperties.remove(property);
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
                    newProperties.remove(property);
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
                        newProperties.remove(property);
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
                        newProperties.remove(property);
                }
            }
        }
    }

    private void filterByPrice() {
        int priceMini = filter.getPriceMini();
        int priceMaxi = filter.getPriceMaxi();
        if (priceMini > Constants.slider_pieces_minimum || priceMaxi < Constants.slider_photos_maximum)
            for (int i = 0; i < properties.getValue().size(); i++) {
                Property property = properties.getValue().get(i);
                if (!property.getPrice().isEmpty()) {
                    int price = Integer.parseInt(property.getPrice());
                    if (price < priceMini || price > priceMaxi)
                        newProperties.remove(property);
                }
            }
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

    private void filterByCities() {
        Log.i("tag_filter mainfrvm 159", ">"+filter.getCities().get(0));
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
        Log.i("tag_filter_for_list ", "ok");
        for (int j = 0; j < dataListFilter.size(); j++) {
            String cityFilter = dataListFilter.get(j);
            Log.i("tag_test ", ">" + data+ " and "+cityFilter);
            if (data.toUpperCase().equals(cityFilter.toUpperCase())) {
                Log.i("tag_equal ", ">" + data+ " égal à filtre: "+cityFilter);
                one_of_them = true;
            }
        }
        if (!one_of_them) {
            newProperties.remove(property);
            Log.i("tag_remove ", "> supprime le " +property.getCity());
        }
    }

    public String[] getTYPES() {
        return TYPE_LIST;
    }

    public String[] getAvailabilities() {
        return AVAILABILITY_LIST;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
