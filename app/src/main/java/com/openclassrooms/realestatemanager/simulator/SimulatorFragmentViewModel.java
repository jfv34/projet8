package com.openclassrooms.realestatemanager.simulator;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SimulatorFragmentViewModel extends ViewModel {

    MutableLiveData<String> price = new MutableLiveData<>();
    MutableLiveData<String> rate = new MutableLiveData<>();
    MutableLiveData<String> duration = new MutableLiveData<>();
    MutableLiveData<String> contribution = new MutableLiveData<>();
    MutableLiveData<Boolean> isDurationYears = new MutableLiveData<>();
    MutableLiveData<String> result = new MutableLiveData<>();

    public void calculation(String price_st, String contribution_st,
                            String rate_st, String duration_st, boolean isDurationYears) {

        int price = Integer.parseInt(price_st);
        int contribution;
        if (contribution_st.equals("")) {
            contribution = 0;
        } else {
            contribution = Integer.parseInt(contribution_st);
        }
        if (rate_st.equals("")) {
            rate_st = "0";
        }
        double rate = Double.parseDouble(rate_st) / 100;
        int duration;
        if (duration_st.equals("")) {
            duration_st = "0";
        }
        if (isDurationYears) {
            duration = Integer.parseInt(duration_st) * 12;
        } else {
            duration = Integer.parseInt(duration_st);
        }

        double r = ((price - contribution) * rate * Math.pow(1 + rate, duration)) /
                (Math.pow(1 + rate, duration) - 1);

/*        double r = ((price - contribution) * rate / 12)
                / (1 - Math.pow((1 + (rate / 12)), -duration));*/

        result.postValue(String.valueOf((int) r));
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
                rate.postValue(text);
                break;
            case "DURATION":
                duration.postValue(text);
                break;
        }

        String price_;
        String contribution_;
        String rate_;
        String duration_;
        boolean isDurationYears_;

        if (price.getValue() == null) {
            price_ = "";
        } else {
            price_ = price.getValue();
        }
        if (contribution.getValue() == null) {
            contribution_ = "";
        } else {
            contribution_ = contribution.getValue();
        }
        if (rate.getValue() == null) {
            rate_ = "";
        } else {
            rate_ = rate.getValue();
        }
        if (duration.getValue() == null) {
            duration_ = "";
        } else {
            duration_ = duration.getValue();
        }
        if (isDurationYears.getValue() == null) {
            isDurationYears_ = true;
        } else {
            isDurationYears_ = isDurationYears.getValue();
        }

        calculation(price_, contribution_, rate_, duration_, isDurationYears_);
    }

    public void radioGroupChanged(boolean isDurationYears) {
        this.isDurationYears.postValue(isDurationYears);
    }

    public void init(String bundlePrice) {
        price.postValue(bundlePrice);
    }
}