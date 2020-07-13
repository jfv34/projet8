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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.Utils.Utils;
import com.openclassrooms.realestatemanager.models.Marker;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment;
import com.openclassrooms.realestatemanager.ui.editProperty.FormPropertyFragment;
import com.openclassrooms.realestatemanager.ui.Utils.SharedPropertiesViewModel;
import com.openclassrooms.realestatemanager.ui.main.MainFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback {

    @BindView(R.id.fragment_map_toolbar)
    androidx.appcompat.widget.Toolbar toolbar;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private static final int PERMISSIONS_REQUEST_CODE = 123;
    private SharedPropertiesViewModel sharedFilterViewModel;
    private ArrayList<Marker> markerList = new ArrayList<>();
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        connectionAndLoading();
    }

    private void connectionAndLoading() {
        if (Utils.isInternetAvailable(getActivity())) {
            loadMap();
            sharedFilterViewModel = new ViewModelProvider(requireActivity()).get(SharedPropertiesViewModel.class);
            if (sharedFilterViewModel.isFiltred = false) {
                sharedFilterViewModel.loadProperties();
            }
            toolBar();
        } else internetNotAvailable();
    }

    private void internetNotAvailable() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Internet is not available")
                .setMessage("No internet connection. Do you want to try again ?")
                .setPositiveButton("YES", (dialogInterface, i) -> connectionAndLoading())
                .setNegativeButton("NO", (dialog, which) -> {
                    MainFragment mainFragment = new MainFragment();
                    Utils.replaceFragmentInMainScreen(getActivity(), mainFragment);
                })
                .create()
                .show();
    }

    private void loadMap() {
        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);
    }

    protected synchronized void buildGoogleApiClient() {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationClickListener(this);

      locateUser();
        }

    private void locateUser() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(),location -> {
            if(location!=null){
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
                googleMap.animateCamera(cameraUpdate);}
    });

    }

    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setZoomGesturesEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            checkLocationPermission();
            buildGoogleApiClient();
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
        }
        observeFilterProperties();
    }

    private void checkLocationPermission() {
        //requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    PERMISSIONS_REQUEST_CODE);
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_CODE );
            }
        }
    }

    private void observeFilterProperties() {
        if (sharedFilterViewModel.properties.getValue() == null) {
            sharedFilterViewModel.loadProperties();
        }
        sharedFilterViewModel.properties.observe(getViewLifecycleOwner(), properties -> {

            if (properties.size() == 0) {
                Utils.toast(getActivity(), getString(R.string.noproperties));
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
            LatLng address_latLng = getLocationFromAddress(address);
            if (address_latLng != null) {
                marker(address_latLng.latitude, address_latLng.longitude, type);
                markerList.add(new Marker(property.getId(), address_latLng));
            }
        }
        markerClickListener();
    }

    private void markerClickListener() {
        googleMap.setOnMarkerClickListener(marker -> {
            for (int i = 0; i < markerList.size(); i++) {
                LatLng click_latlng = marker.getPosition();
                LatLng saved_latlng = markerList.get(i).getLatLng_marker();
                if (click_latlng.equals(saved_latlng)) {
                    int propertyId = markerList.get(i).getPropertyId();
                    Fragment detailsFragment = DetailsFragment.newInstance(propertyId);
                    Utils.addFragmentInDetailScreen(getActivity(), detailsFragment);
                }
            }
            return false;
        });
    }

    private LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;
        LatLng latLng = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latLng;
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




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        locateUser();

                    }
                } else {
                    Utils.toast(getActivity(), "No permission for location");
                }
            }
        }
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

    private void toolBar() {
        toolbar.setTitle("Real Estate Manager");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @OnClick(R.id.fragment_map_main_button)
    public void onMainClicked() {
        Fragment mainFragment = MainFragment.newInstance();
        Utils.replaceFragmentInMainScreen(getActivity(), mainFragment);
    }

    @OnClick(R.id.fragment_map_insert_property_fab)
    public void onInsertPropertyClicked() {
        Fragment formPropertyFragment = FormPropertyFragment.newInstance(-1);
        Utils.addFragmentInDetailScreen(getActivity(), formPropertyFragment);
    }



}