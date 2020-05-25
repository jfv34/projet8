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
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.repositories.Constants;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.openclassrooms.realestatemanager.Utils.convertDateToString;

public class FilterFragmentViewModel extends ViewModel {

    private PropertyRepository repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
    public MutableLiveData<String> soldDate = new MutableLiveData<>();
    public MutableLiveData<String> entryDate = new MutableLiveData<>();
    public MutableLiveData<List<Type>> typesFilter = new MutableLiveData<>();
    public MutableLiveData<Integer> priceMin = new MutableLiveData<>();
    public MutableLiveData<Integer> priceMax = new MutableLiveData<>();

    private Filter filter;
    private String[] TYPE_LIST = Constants.TYPE_LIST;
    private String[] AVAILABILITY_LIST = {"All", "Available","Sold"} ;

    ArrayList<Type> getTypesFilter() {
        return (ArrayList<Type>) typesFilter.getValue();
    }

    void initTypesFilter() {

        ArrayList<Type> newTypesFilter = new ArrayList<>();
        for (String s : TYPE_LIST) {
            newTypesFilter.add(new Type(s, false));
        }
        typesFilter.postValue(newTypesFilter);
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

    public ArrayList<Property> filter(Filter filter, List<Property> properties) {

        this.filter = filter;
        ArrayList<Property> filterProperties = new ArrayList<>(properties);
        filterByTypes(filterProperties);
        filterByPrice(filterProperties);
        filterByCities(filterProperties);
        filterByStates(filterProperties);
        filterByArea(filterProperties);
        filterByPieces(filterProperties);
        filterByInterestPoints(filterProperties);
        filterByAgent(filterProperties);
        filterByDates(filterProperties);
        filterByPhotos(filterProperties);
        filterByStatus(filterProperties);

        return filterProperties;
    }

    public void applyFilter(Filter filter, SharedFilterViewModel sharedFilterViewModel) {

        AsyncTask.execute(() -> {
            List<Property> properties = repository.getProperties();
            ArrayList<Property> filterProperties = filter(filter, properties);
            sharedFilterViewModel.properties.postValue(filterProperties);
            sharedFilterViewModel.isFiltred = true;

                }
        );
    }

    private void filterByInterestPoints(ArrayList<Property> properties) {
        ArrayList<String> interestPointsFilter = filter.getInterestPoints();
        if (!interestPointsFilter.get(0).equals("")) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                String interestPoint = property.getInterestPoint();
                filter_for_list(property, interestPoint, interestPointsFilter,properties);
            }
        }
    }

    private void filterByStates(ArrayList<Property> properties) {
        ArrayList<String> statesFilter = filter.getStates();
        if (!statesFilter.get(0).equals("")) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                String state = property.getState();
                filter_for_list(property, state, statesFilter,properties);
            }
        }
    }

    private void filterByTypes(ArrayList<Property> properties) {
        if (!filter.getTypes().isEmpty()) {
            ArrayList<String> typesFilter = new ArrayList<>();
            for (int i = 0; i < filter.getTypes().size(); i++) {
                if (filter.getTypes().get(i).isSelected()) {
                    typesFilter.add(TYPE_LIST[i]);
                }
            }
            if(!typesFilter.isEmpty()){
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                String type = property.getType();
                filter_for_list(property, type, typesFilter,properties);
            }
        };}
    }

    private void filterByCities(ArrayList<Property> properties) {
        ArrayList<String> citiesFilter = filter.getCities();
        if (!citiesFilter.get(0).equals("")) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                String city = property.getCity();
                filter_for_list(property, city, citiesFilter,properties);
            }
        }
    }

    private void filterByAgent(ArrayList<Property> properties) {
        ArrayList<String> agentsFilter = filter.getAgentName();
        if (!agentsFilter.get(0).equals("")) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                String agent = property.getAgentName();
                filter_for_list(property, agent, agentsFilter,properties);
            }
        }
    }

    private void filter_for_list(Property property, String data, ArrayList<String> dataListFilter, ArrayList<Property> properties) {
        boolean one_of_them = false;
        for (int j = 0; j < dataListFilter.size(); j++) {
            String listFilter = dataListFilter.get(j);
            if (data.toUpperCase().equals(listFilter.toUpperCase())) {
                one_of_them = true;
            }
        }
        if (!one_of_them) {
            properties.remove(property);
        }
    }

    private void filterByDates(ArrayList<Property> properties) {
        Date entryDateFilter = Utils.convertStringToDate(filter.getEntryDate());
        Date soldeDateFilter = Utils.convertStringToDate(filter.getSaleDate());

        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);
            Date entryDate = Utils.convertStringToDate(property.getEntryDate());
            Date soldeDate = Utils.convertStringToDate(property.getSaleDate());

            if (entryDateFilter != null && entryDate != null && entryDate.compareTo(entryDateFilter) < 0)
                properties.remove(property);

            if (soldeDateFilter != null && soldeDate != null)
                if (soldeDate.compareTo(soldeDateFilter) < 0)
                    properties.remove(property);
        }
    }

    private void filterByPhotos(ArrayList<Property> properties) {
        int nbOfPhotosMini = filter.getNumberOfPhotosMini();
        int nbOfPhotosMaxi = filter.getNumberOfPhotosMaxi();
        if (nbOfPhotosMini > 0 || nbOfPhotosMaxi < Constants.slider_photos_maximum) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                int nbOfPhotos;
                if (property.getPhotos() == null) {
                    nbOfPhotos = 0;
                } else {
                    nbOfPhotos = property.getPhotos().size();
                }
                if (nbOfPhotos < nbOfPhotosMini || nbOfPhotos > nbOfPhotosMaxi)
                    properties.remove(property);
            }
        }
    }

    private void filterByPieces(ArrayList<Property> properties) {
        int piecesMini = filter.getPiecesMini();
        int piecesMaxi = filter.getPiecesMaxi();
        if (piecesMini > Constants.slider_pieces_minimum || piecesMaxi < Constants.slider_pieces_maximum) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                if (!property.getPieces().isEmpty()) {
                    int pieces = Integer.parseInt(property.getPieces());
                    if (pieces < piecesMini || pieces > piecesMaxi)
                        properties.remove(property);
                }
            }
        }
    }

    private void filterByArea(ArrayList<Property> properties) {
        int areaMini = filter.getAreaMini();
        int areaMaxi = filter.getAreaMaxi();
        if (areaMini > Constants.slider_area_minimum || areaMaxi < Constants.slider_area_maximum) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                if (!property.getArea().isEmpty()) {
                    int area = Integer.parseInt(property.getArea());
                    if (area < areaMini || area > areaMaxi)
                        properties.remove(property);
                }
            }
        }
    }

    private void filterByPrice(ArrayList<Property> properties) {
        int priceMini = filter.getPriceMini();
        int priceMaxi = filter.getPriceMaxi();
        if (priceMini > Constants.slider_price_minimum || priceMaxi < Constants.slider_price_maximum)
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                if (!property.getPrice().isEmpty()) {
                    int price = Integer.parseInt(property.getPrice());
                    if (price < priceMini || price > priceMaxi)
                        properties.remove(property);
                }
            }
    }

    private void filterByStatus(ArrayList<Property> properties) {
        Status statusFilter = filter.getStatus();
        if(statusFilter!=Status.UNSPECIFIED){
        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);
            Status status = property.getStatus();
            if (status != statusFilter) {
                properties.remove(property);
            }
        }
    }}

    void setType(int position, boolean selected) {
        ArrayList<Type> newtypesFilter = (ArrayList<Type>) typesFilter.getValue();
        newtypesFilter.get(position).setSelected(selected);
        typesFilter.postValue(newtypesFilter);
    }

    String getTypeInString() {

        StringBuilder types = new StringBuilder();
        int size = typesFilter.getValue().size();
        for (int i = 0; i < size; i++) {
            types.append(typesFilter.getValue().get(i).isSelected());
            if (i < size - 1) {
                types.append(",");
            }
        }
        return types.toString();
    }

    public List<Type> getTypesFilter_prefs(String isSelected_prefs) {
        ArrayList<Type> typesFilter = new ArrayList<>();
        List<String> isSelected_st = getFilterListInForm(isSelected_prefs);
        for (int i = 0; i < TYPE_LIST.length; i++) {
            boolean isSelected = Boolean.parseBoolean(isSelected_st.get(i));
            Type type = new Type(TYPE_LIST[i], isSelected);
            typesFilter.add(type);
        }
        return typesFilter;
    }
}