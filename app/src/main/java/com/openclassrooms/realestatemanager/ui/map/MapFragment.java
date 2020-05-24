package com.openclassrooms.realestatemanager.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.ui.filter.SharedFilterViewModel;

import java.util.List;

public class MapFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        LocationListener,
        OnMapReadyCallback {

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private static final int PERMISSIONS_REQUEST_CODE = 123;
    private SharedFilterViewModel sharedFilterViewModel;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        loadMap();
        sharedFilterViewModel = new ViewModelProvider(requireActivity()).get(SharedFilterViewModel.class);
        if (sharedFilterViewModel.isFiltred = false) {
            sharedFilterViewModel.loadProperties();
        }
    }

    private void observeFilterProperties() {
        if (sharedFilterViewModel.properties.getValue() == null) {
            sharedFilterViewModel.loadProperties();
        }
        sharedFilterViewModel.properties.observe(getViewLifecycleOwner(), properties -> {

            if (properties.size() == 0) {
                Utils.toast(getActivity(), "No properties in database");
            } else {
                displayProperties(properties);
            }
        });
    }

    private void displayProperties(List<Property> properties) {
        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);
            String type = property.getType();
            String address = property.getAddress() + "," + property.getCity() + "," + property.getState() + "," + property.getZip();
            LatLng address_latLng = getLocationFromAddress(getActivity(), address);
            marker(address_latLng.latitude, address_latLng.longitude, type);
            ;
        }
    }

    private void marker(double latitude, double longitude, String title_Marker) {

        int drawable;
        drawable = R.drawable.ic_marker_housse;

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(title_Marker)
                .icon(bitmapDescriptorFromVector(requireContext(), drawable)));
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int drawable) {
        Drawable background = ContextCompat.getDrawable(context, drawable);
        assert background != null;
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void loadMap() {
        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);

            map.getMapAsync(googleMap -> {
                this.googleMap = googleMap;

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Location Permission already granted
                        googleMap.setMyLocationEnabled(true);
                        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                    } else {
                        //Request Location Permission
                        checkLocationPermission();
                    }
                } else {
                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                }
            });
    }

    private void checkLocationPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        googleMap.setMyLocationEnabled(true);
                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {

        googleMap=map;
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationClickListener(this);

        LatLng latLng = new LatLng(37.4220, -122.0840);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(16).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        observeFilterProperties();

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p1;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public boolean onMyLocationButtonClick() {

        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}