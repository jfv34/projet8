package com.openclassrooms.realestatemanager.ui.simulator;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Currency;
import com.openclassrooms.realestatemanager.ui.Utils.Utils;

public class SimulatorFragmentViewModel extends ViewModel {

    public MutableLiveData<String> price = new MutableLiveData<>();
    public MutableLiveData<String> rate_in_percentage = new MutableLiveData<>();
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
                rate_in_percentage.postValue(text);
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
        int price_ = 0;
        int contribution_ = 0;
        double rate_ = 0;
        int duration_ = 0;
        boolean isDurationYears_ = true;

        if (price.getValue() != null && !price.getValue().isEmpty()) {
            price_ = Integer.parseInt(price.getValue());
            if(currency==Currency.EUROS){price_= Utils.convertDollarToEuro(price_);}
        }
        if (contribution.getValue() != null && !contribution.getValue().isEmpty()) {
            contribution_ = Integer.parseInt(contribution.getValue());
        }
        if (rate_in_percentage.getValue() != null && !rate_in_percentage.getValue().isEmpty()) {
            rate_ = (Double.parseDouble(rate_in_percentage.getValue())) / 100;
        }
        if (duration.getValue() != null && !duration.getValue().isEmpty()) {
            duration_ = Integer.parseInt(duration.getValue());
        }
        if (isDurationYears.getValue() != null) {
            isDurationYears_ = isDurationYears.getValue();
        }

        if (isDurationYears_) {
            duration_ = duration_ * 12;
        }

        if (price_ > 0 && contribution_ >= 0 && rate_ >= 0 && duration_ > 0) {

            int r = (int) (((price_ - contribution_) * (rate_ / 12)) /
                    (1 - (Math.pow(1 + (rate_ / 12), -duration_))));

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