package com.openclassrooms.realestatemanager.ui.simulator;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Currency;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

public class SimulatorFragmentViewModel extends ViewModel {

    public MutableLiveData<String> price = new MutableLiveData<>();
    public MutableLiveData<String> rateInPercentage = new MutableLiveData<>();
    public MutableLiveData<String> duration = new MutableLiveData<>();
    public MutableLiveData<String> contribution = new MutableLiveData<>();
    public MutableLiveData<Boolean> isDurationYears = new MutableLiveData<>();
    public MutableLiveData<String> result = new MutableLiveData<>();
    private Currency currency = Currency.DOLLARS;

    public void init(String bundlePrice) {
        price.postValue(bundlePrice);
    }

    public void textChanged(@NonNull String form, String text) {
        switch (form) {
            case "PRICE":
                price.postValue(text);
                break;
            case "CONTRIBUTION":
                contribution.postValue(text);
                break;
            case "RATE":
                rateInPercentage.postValue(text);
                break;
            case "DURATION":
                duration.postValue(text);
                break;
        }
    }

    public void radioGroupChanged(boolean isDurationYears) {
        this.isDurationYears.postValue(isDurationYears);
    }

    public void calculation() {
        int newPrice = 0;
        int newContribution = 0;
        double newRate = 0;
        int newDuration = 0;
        boolean newIsDurationYears = true;

        if (price.getValue() != null && !price.getValue().isEmpty()) {
            newPrice = Integer.parseInt(price.getValue());
            if(currency==Currency.EUROS){newPrice= Utils.convertDollarToEuro(newPrice);}
        }
        if (contribution.getValue() != null && !contribution.getValue().isEmpty()) {
            newContribution = Integer.parseInt(contribution.getValue());
        }
        if (rateInPercentage.getValue() != null && !rateInPercentage.getValue().isEmpty()) {
            newRate = (Double.parseDouble(rateInPercentage.getValue())) / 100;
        }
        if (duration.getValue() != null && !duration.getValue().isEmpty()) {
            newDuration = Integer.parseInt(duration.getValue());
        }
        if (isDurationYears.getValue() != null) {
            newIsDurationYears = isDurationYears.getValue();
        }

        if (newIsDurationYears) {
            newDuration = newDuration * 12;
        }

        if (newPrice > 0 && newContribution >= 0 && newRate >= 0 && newDuration > 0) {

            int r = (int) (((newPrice - newContribution) * (newRate / 12)) /
                    (1 - (Math.pow(1 + (newRate / 12), -newDuration))));

            if (r <= 0) {
                result.postValue("");
            } else {
                result.postValue(String.valueOf(r));
            }
        } else result.postValue("");
    }

    public void setCurrency(Currency new_Currency) {
        currency = new_Currency;
    }
}