package com.openclassrooms.realestatemanager.ui.filter;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.base.BaseApplication;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Currency;
import com.openclassrooms.realestatemanager.models.Filter;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.repositories.Constants;
import com.openclassrooms.realestatemanager.repositories.DataPropertiesRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.ui.utils.SharedPropertiesViewModel;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.openclassrooms.realestatemanager.ui.utils.Utils.convertDateToString;

public class FilterFragmentViewModel extends ViewModel {

    private PropertyRepository repository;
    MutableLiveData<String> soldDate = new MutableLiveData<>();
    MutableLiveData<String> entryDate = new MutableLiveData<>();
    MutableLiveData<List<Type>> typesFilter = new MutableLiveData<>();
    MutableLiveData<Integer> priceMin = new MutableLiveData<>();
    MutableLiveData<Integer> priceMax = new MutableLiveData<>();
    MutableLiveData<Integer> priceBoundMin = new MutableLiveData<>();
    MutableLiveData<Integer> priceBoundMax = new MutableLiveData<>();
    MutableLiveData<Integer> areaMin = new MutableLiveData<>();
    MutableLiveData<Integer> areaMax = new MutableLiveData<>();
    MutableLiveData<Integer> numberOfPhotosMin = new MutableLiveData<>();
    MutableLiveData<Integer> numberOfPhotosMax = new MutableLiveData<>();
    MutableLiveData<Integer> piecesMin = new MutableLiveData<>();
    MutableLiveData<Integer> piecesMax = new MutableLiveData<>();

    public Filter filter;
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
            if (list_txt.charAt(i) == ',' || list_txt.charAt(i) == ';') {
                filter.add(list_txt.substring(previous, i).trim());
                previous = i + 1;
            }
        }
        filter.add(list_txt.substring(previous).trim());
        return filter;
    }

    public void applyFilter(Filter filter, SharedPropertiesViewModel sharedFilterViewModel) {
        repository = new DataPropertiesRepository(PropertyDataBase.getInstance(BaseApplication.getAppContext()).propertyDao());
        AsyncTask.execute(() -> {
                    ArrayList<Property> properties = (ArrayList<Property>) repository.getProperties();
                    ArrayList<Property> filterProperties = filter(filter, properties);
                    sharedFilterViewModel.properties.postValue(filterProperties);
                    sharedFilterViewModel.isFiltered = true;

                }
        );
    }

    public ArrayList<Property> filter(Filter filter, ArrayList<Property> properties) {

        this.filter = filter;
        properties = filterByTypes(properties);
        properties = filterByPrice(properties);
        properties = filterByCities(properties);
        properties = filterByStates(properties);
        properties = filterByArea(properties);
        properties = filterByPieces(properties);
        properties = filterByInterestPoints(properties);
        properties = filterByAgent(properties);
        properties = filterByStatus(properties);

        return properties;
    }

    private ArrayList<Property> filterByInterestPoints(ArrayList<Property> properties) {
        ArrayList<Property> newProperties = new ArrayList<>();
        ArrayList<String> interestPointsFilter = filter.getInterestPoints();
        if (!interestPointsFilter.get(0).equals("")) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                String interestPoint = property.getInterestPoint();
                boolean isSelected = filterForList(interestPoint, interestPointsFilter);
                if (isSelected) {
                    newProperties.add(property);
                }
            }
            return newProperties;
        } else return properties;
    }

    public ArrayList<Property> filterByStates(ArrayList<Property> properties) {
        ArrayList<Property> newProperties = new ArrayList<>();
        ArrayList<String> statesFilter = filter.getStates();
        if (!statesFilter.get(0).equals("")) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                String state = property.getState();
                boolean isSelected = filterForList(state, statesFilter);
                if (isSelected) {
                    newProperties.add(property);
                }
            }
            return newProperties;
        } else return properties;
    }

    private ArrayList<Property> filterByTypes(ArrayList<Property> properties) {
        ArrayList<Property> newProperties = new ArrayList<>();
        if (!filter.getTypes().isEmpty()) {
            ArrayList<String> typesFilter = new ArrayList<>();
            for (int i = 0; i < filter.getTypes().size(); i++) {
                if (filter.getTypes().get(i).isSelected()) {
                    typesFilter.add(TYPE_LIST[i]);
                }
            }
            if (!typesFilter.isEmpty()) {
                for (int i = 0; i < properties.size(); i++) {
                    Property property = properties.get(i);
                    String type = property.getType();
                    if (typesFilter.contains(type)) {
                        newProperties.add(property);
                    }
                }
                return newProperties;
            } else return properties;
        }
        return properties;
    }

    private ArrayList<Property> filterByCities(ArrayList<Property> properties) {
        ArrayList<Property> newProperties = new ArrayList<>();
        ArrayList<String> citiesFilter = filter.getCities();
        if (!citiesFilter.get(0).equals("")) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                String city = property.getCity();
                boolean isSelected = filterForList(city, citiesFilter);
                if (isSelected) {
                    newProperties.add(property);
                }
            }
            return newProperties;
        } else return properties;
    }

    private ArrayList<Property> filterByAgent(ArrayList<Property> properties) {
        ArrayList<Property> newProperties = new ArrayList<>();
        ArrayList<String> agentsFilter = filter.getAgentName();
        if (!agentsFilter.get(0).equals("")) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                String agent = property.getAgentName();
                boolean is_selected = filterForList(agent, agentsFilter);
                if (is_selected) {
                    newProperties.add(property);
                }
            }
            return newProperties;
        } else return properties;
    }

    private ArrayList<Property> filterByPieces(ArrayList<Property> properties) {
        ArrayList<Property> newProperties = new ArrayList<>();
        int piecesMini = filter.getPiecesMini();
        int piecesMaxi = filter.getPiecesMaxi();
        if (piecesMini > Constants.sliderPiecesMinimum || piecesMaxi < Constants.sliderPiecesMaximum) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                if (!property.getPieces().isEmpty()) {
                    int pieces = Integer.parseInt(property.getPieces());
                    if (pieces > piecesMini && pieces < piecesMaxi)
                        newProperties.add(property);
                }
            }
        }
        return newProperties;
    }

    private ArrayList<Property> filterByArea(ArrayList<Property> properties) {
        ArrayList<Property> newProperties = new ArrayList<>();
        int areaMini = filter.getAreaMini();
        int areaMaxi = filter.getAreaMaxi();
        if (areaMini > Constants.sliderAreaMinimum || areaMaxi < Constants.sliderAreaMaximum) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                if (!property.getArea().isEmpty()) {
                    int area = Integer.parseInt(property.getArea());
                    if (area > areaMini && area < areaMaxi)
                        newProperties.add(property);
                }
            }
            return newProperties;
        }else return properties;
    }

    public ArrayList<Property> filterByPrice(ArrayList<Property> properties) {
        ArrayList<Property> newProperties = new ArrayList<>();
        int priceMini = filter.getPriceMini();
        int priceMaxi = filter.getPriceMaxi();
        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);
            if (!property.getPrice().isEmpty()) {
                int price = Integer.parseInt(property.getPrice());
                if (price > priceMini && price < priceMaxi) {
                    newProperties.add(property);
                }
            }
        }
        return newProperties;
    }

    private ArrayList<Property> filterByStatus(ArrayList<Property> properties) {
        ArrayList<Property> newProperties = new ArrayList<>();
        Status statusFilter = filter.getStatus();
        if (statusFilter != Status.UNSPECIFIED) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                Status status = property.getStatus();
                if (status == statusFilter) {
                    newProperties.add(property);
                }
            }
            return newProperties;
        } else return properties;
    }

    private boolean filterForList(String data, ArrayList<String> dataListFilter) {
        boolean oneOfThem = false;
        for (int j = 0; j < dataListFilter.size(); j++) {
            String listFilter = dataListFilter.get(j);
            if (data.toUpperCase().equals(listFilter.toUpperCase())) {
                oneOfThem = true;
            }
        }

        return oneOfThem;
    }

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

    public List<Type> getTypesFilterPrefs(String isSelectedPrefs) {
        ArrayList<Type> typesFilter = new ArrayList<>();
        List<String> isSelectedSt = getFilterListInForm(isSelectedPrefs);
        for (int i = 0; i < TYPE_LIST.length; i++) {
            boolean isSelected = Boolean.parseBoolean(isSelectedSt.get(i));
            Type type = new Type(TYPE_LIST[i], isSelected);
            typesFilter.add(type);
        }
        return typesFilter;
    }

    public void validate(String cities, String states, String interestPoints, String agent,
                         String availableDate, String soldDate, String status,
                         SharedPropertiesViewModel sharedFilterViewModel, Currency currency) {

        Integer dollarsPriceMax =0;
        Integer dollarsPriceMin= 0;
        if(currency==Currency.EUROS){
            dollarsPriceMax=Utils.convertEuroToDollar(priceMax.getValue());
            dollarsPriceMin=Utils.convertEuroToDollar(priceMin.getValue());
        }
        else{
            dollarsPriceMax=priceMax.getValue();
            dollarsPriceMin=priceMin.getValue();
        }

        Filter filter = new Filter(
                getTypesFilter(),
                dollarsPriceMax,
               dollarsPriceMin,
               getFilterListInForm(cities),
                getFilterListInForm(states),
                areaMax.getValue(),
                areaMin.getValue(),
                piecesMax.getValue(),
                piecesMin.getValue(),
                getFilterListInForm(interestPoints),
                getFilterListInForm(agent),
                statusFilter(status),
                availableDate,
                soldDate,
                numberOfPhotosMax.getValue(),
                numberOfPhotosMin.getValue()
        );

        applyFilter(filter, sharedFilterViewModel);
    }

    private Status statusFilter(String s) {
        Status status;
        switch (s) {
            case "Sold": {
                status = Status.SOLD;
            }
            break;
            case "Available": {
                status = Status.AVAILABLE;
            }
            break;
            default: {
                status = Status.UNSPECIFIED;
            }
        }
        return status;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}