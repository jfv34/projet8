package com.openclassrooms.realestatemanager.ui.editProperty;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Property;

public class SharedPropertyViewModel extends ViewModel {

    public MutableLiveData<Property> property = new MutableLiveData<>();
}

