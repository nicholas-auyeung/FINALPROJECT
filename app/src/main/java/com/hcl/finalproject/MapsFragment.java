package com.hcl.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsFragment extends Fragment {

    private String lat;
    private String lng;
    private String name;
    private Boolean myGeo;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public MapsFragment(String lat, String lng, String name, Boolean myGeo) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.myGeo = myGeo;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        private GoogleMap googleMap;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            this.googleMap = googleMap;
            if(myGeo){
                updateUI();
                getDeviceLocation();
            }else {
                LatLng geo = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                googleMap.addMarker(new MarkerOptions().position(geo).title("Geo of " + name));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(geo));
            }
            googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    getDeviceLocation();
                    return false;
                }
            });
        }

        public void updateUI(){
            try {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            }catch (SecurityException e){
                Log.e("Exception: %s", e.getMessage());
            }
        }

        public void getDeviceLocation(){
            try{
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                lat = Double.toString(locationResult.getResult().getLatitude());
                                lng = Double.toString(locationResult.getResult().getLongitude());
                            }
                        });
            }catch(SecurityException e){
                Log.e("Exception: %s", e.getMessage());
            }
        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

    }

}