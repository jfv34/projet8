package com.openclassrooms.realestatemanager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class DatasViewModel extends ViewModel {

    private MutableLiveData<String> currentName=new MutableLiveData<>();

    public LiveData<String> getCurrentName() {
        return currentName;
    }
}
