package com.openclassrooms.realestatemanager.ui.main;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class PropertyViewModel extends ViewModel {

    // REPOSITORIES
    private final PropertyDataRepository propertyDataRepository;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<Property> properties;

    public PropertyViewModel(PropertyDataRepository propertyDataRepository, Executor executor) {
        this.propertyDataRepository = propertyDataRepository;
        this.executor = executor;
    }

    public LiveData<List<Property>> getProperties() {
        return propertyDataRepository.getProperties();
    }

    public void createProperty(final Property property) {
        executor.execute(() -> {
            propertyDataRepository.createItem(property);
        });
    }

    public void deleteItem(int id) {
        executor.execute(() -> {
            propertyDataRepository.deleteItem(id);
        });
    }

    public void updateItem(Property property) {
        executor.execute(() -> {
            propertyDataRepository.updateItem(property);
        });
    }
}
