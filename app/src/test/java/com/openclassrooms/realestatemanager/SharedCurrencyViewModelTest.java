package com.openclassrooms.realestatemanager;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.openclassrooms.realestatemanager.models.Currency;
import com.openclassrooms.realestatemanager.ui.utils.SharedCurrencyViewModel;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

public class SharedCurrencyViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void when_setCurrency_is_euros_currency_is_euros() {

        SharedCurrencyViewModel sharedCurrencyViewModel = new SharedCurrencyViewModel();
        sharedCurrencyViewModel.setCurrency(Currency.EUROS);
        Assert.assertEquals(Currency.EUROS, sharedCurrencyViewModel.currency.getValue());
    }

    @Test
    public void when_setCurrency_is_dollars_currency_is_euros() {

        SharedCurrencyViewModel sharedCurrencyViewModel = new SharedCurrencyViewModel();
        sharedCurrencyViewModel.setCurrency(Currency.DOLLARS);
        Assert.assertEquals(Currency.DOLLARS, sharedCurrencyViewModel.currency.getValue());
    }
}