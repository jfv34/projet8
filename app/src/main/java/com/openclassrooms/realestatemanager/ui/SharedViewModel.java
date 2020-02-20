package com.openclassrooms.realestatemanager.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    public MutableLiveData<String> propertyClicked = new MutableLiveData<>();
}

