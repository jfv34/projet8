package com.openclassrooms.realestatemanager;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.openclassrooms.realestatemanager.ui.simulator.SimulatorFragmentViewModel;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

public class SimulatorFragmentViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void When_textChanged_elements_updated() {

        SimulatorFragmentViewModel simulatorFragmentViewModel = new SimulatorFragmentViewModel();
        simulatorFragmentViewModel.textChanged("PRICE", "testPrice");
        simulatorFragmentViewModel.textChanged("CONTRIBUTION", "testContribution");
        simulatorFragmentViewModel.textChanged("RATE", "testRate");
        simulatorFragmentViewModel.textChanged("DURATION", "testDuration");

        String price = simulatorFragmentViewModel.price.getValue();
        String contribution = simulatorFragmentViewModel.contribution.getValue();
        String rate = simulatorFragmentViewModel.rate_in_percentage.getValue();
        String duration = simulatorFragmentViewModel.duration.getValue();

        Assert.assertEquals(price, "testPrice");
        Assert.assertEquals(contribution, "testContribution");
        Assert.assertEquals(rate, "testRate");
        Assert.assertEquals(duration, "testDuration");
    }

    @Test
    public void When_radioGroupChanged_by_true_isDurationYears_updated_by_true() {

        SimulatorFragmentViewModel simulatorFragmentViewModel = new SimulatorFragmentViewModel();
        simulatorFragmentViewModel.radioGroupChanged(true);

        boolean isDurationYears = simulatorFragmentViewModel.isDurationYears.getValue();
        Assert.assertTrue(isDurationYears);
    }

    @Test
    public void result_is_787_when_calculation_with_some_data_() {

        SimulatorFragmentViewModel simulatorFragmentViewModel = new SimulatorFragmentViewModel();

        simulatorFragmentViewModel.price.postValue("100000");
        simulatorFragmentViewModel.contribution.postValue("10000");
        simulatorFragmentViewModel.duration.postValue("12");
        simulatorFragmentViewModel.rate_in_percentage.postValue("4");

        simulatorFragmentViewModel.calculation();

        String result = simulatorFragmentViewModel.result.getValue();

        Assert.assertEquals(result, "787");
    }
}