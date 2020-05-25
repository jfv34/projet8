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

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public LatLng getLatLng_marker() {
        return latLng_marker;
    }

    public void setLatLng_marker(LatLng latLng_marker) {
        this.latLng_marker = latLng_marker;
    }
}

