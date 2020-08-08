package com.openclassrooms.realestatemanager.ui.utils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Currency;

public class SharedCurrencyViewModel extends ViewModel {
    public MutableLiveData<Currency> currency = new MutableLiveData<>();

    public void setCurrency(Currency newCurrency) {
        currency.postValue(newCurrency);
    }
}
