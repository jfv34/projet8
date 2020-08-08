package com.openclassrooms.realestatemanager.models;

import com.google.android.gms.maps.model.LatLng;

public class Marker {
    private int propertyId;
    private LatLng latLng_marker;

    public Marker(int propertyId, LatLng latLng_marker) {
        this.propertyId = propertyId;
        this.latLng_marker = latLng_marker;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public LatLng getLatLngMarker() {
        return latLng_marker;
    }
}

