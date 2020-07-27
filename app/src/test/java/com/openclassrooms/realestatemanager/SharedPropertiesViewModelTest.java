package com.openclassrooms.realestatemanager;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.ui.utils.SharedPropertiesViewModel;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.ArrayList;

public class SharedPropertiesViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void property_changed_in_properties_by_setProperty() {

        SharedPropertiesViewModel sharedPropertiesViewModel = new SharedPropertiesViewModel();
        Photo photo1 = new Photo("testPath1", "testFileName1", "TestDescription1");
        Photo photo2 = new Photo("testPath2", "testFileName2", "TestDescription2");
        ArrayList<Photo> photos = new ArrayList<>();
        photos.add(photo1);
        photos.add(photo2);
        Status status = Status.SOLD;

        Property property_A = new Property(
                100,
                "manor",
                "1000",
                "PROPERTY_A",
                "testCity",
                "testState",
                "testZip",
                "testArea",
                "testPieces",
                "testInterestPoint",
                "testDescription",
                photos,
                status,
                "testEntryDate",
                "testSaleDate",
                "testAgent"
        );
        Property property_B = new Property(
                101,
                "manor",
                "1000",
                "PROPERTY_B",
                "testCity",
                "testState",
                "testZip",
                "testArea",
                "testPieces",
                "testInterestPoint",
                "testDescription",
                photos,
                status,
                "testEntryDate",
                "testSaleDate",
                "testAgent"
        );
        Property property_C = new Property(
                102,
                "manor",
                "1000",
                "PROPERTY_C",
                "testCity",
                "testState",
                "testZip",
                "testArea",
                "testPieces",
                "testInterestPoint",
                "testDescription",
                photos,
                status,
                "testEntryDate",
                "testSaleDate",
                "testAgent"
        );
        Property property_added = new Property(
                101,
                "manor",
                "1000",
                "PROPERTY_ADDED",
                "testCity",
                "testState",
                "testZip",
                "testArea",
                "testPieces",
                "testInterestPoint",
                "testDescription",
                photos,
                status,
                "testEntryDate",
                "testSaleDate",
                "testAgent"
        );

        ArrayList<Property> properties = new ArrayList<>();
        properties.add(property_A);
        properties.add(property_B);
        properties.add(property_C);
        sharedPropertiesViewModel.properties.postValue(properties);

        Assert.assertEquals(properties.get(0).id, 100);
        Assert.assertEquals(properties.get(1).id, 101);
        Assert.assertEquals(properties.get(2).id, 102);

        sharedPropertiesViewModel.setProperty(property_added, 101);
        Assert.assertEquals(properties.get(1).getAddress(), "PROPERTY_ADDED");
        Assert.assertEquals(properties.get(1), property_added);
    }
}