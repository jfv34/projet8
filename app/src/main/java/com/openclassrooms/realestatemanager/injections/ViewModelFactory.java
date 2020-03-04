package com.openclassrooms.realestatemanager.injections;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.ui.main.PropertyViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final PropertyDataRepository propertyDataRepository;
    private final Executor executor;

    public ViewModelFactory(PropertyDataRepository propertyDataRepository, Executor executor) {
        this.propertyDataRepository = propertyDataRepository;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PropertyViewModel.class)) {
            return (T) new PropertyViewModel(propertyDataRepository, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}