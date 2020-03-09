package com.openclassrooms.realestatemanager.models;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PropertyAndAddressProperty {
    @Embedded
    public AddressProperty addressProperty;
    @Relation(
            parentColumn = "id",
            entityColumn = "addressId"
    )
    public Property property;

}
