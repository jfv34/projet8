package com.openclassrooms.realestatemanager.simulator;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class SimulatorFragmentViewModel extends ViewModel {

    public void validate(String price_st, String contribution_st,
                         String rate_st, String duration_st, boolean isDurationYears) {

        int price = Integer.parseInt(price_st);
        int contribution;
        if (contribution_st.equals("")) {
            contribution = 0;
        } else {
            contribution = Integer.parseInt(contribution_st);
        }
        double rate = Double.parseDouble(rate_st) / 100;
        int duration;
        if (isDurationYears) {
            duration = Integer.parseInt(duration_st) * 12;
        } else {
            duration = Integer.parseInt(duration_st);
            ;
        }

        double result = ((price - contribution) * rate / 12)
                / (1 - Math.pow((1 + (rate / 12)), -duration));

        Log.i("tag_result", String.valueOf(result));
    }
}
