package com.example.try4;

import android.Manifest;
import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class PFragment<requestPermission> extends Fragment implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    //Location currentlocation;
    SearchView searchView;
    ImageButton ibtn;
    GoogleMap gmap;
    FusedLocationProviderClient client;

    private static final int REQUEST_CODE=101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //initialise view
        View view = inflater.inflate(R.layout.fragment_p, container, false);

        //initialise map fragment
        mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        //initialise location
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        ibtn = view.findViewById(R.id.book);
        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),confirmParking.class);
                startActivity(intent);
            }
        });

//        ibtn.findViewById(R.id.location).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //Check condition
//                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
//                            Manifest.permission.ACCESS_COARSE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
//                        //When permission is granted, call method
//                        getCurrentLocation();
//                    }
//                }
//            });

        searchView = view.findViewById(R.id.sv_location);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    gmap.addMarker(new MarkerOptions().position(latLng).title(location));
                    gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                String location = searchView.getQuery().toString();
//                List<Address> addressList = null;
//
//                if (location != null || !location.equals("")) {
//                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//                    try {
//                        addressList = geocoder.getFromLocationName(location,1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
//                    googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
        mapFragment.getMapAsync(this);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        //When clicked on map
                        //Initialise marker options
                        MarkerOptions markerOptions = new MarkerOptions();
                        //set position of marker
                        markerOptions.position(latLng);
                        //set title
                        markerOptions.title(latLng.latitude + ":" + latLng.longitude);
                        //Remove all marker
                        googleMap.clear();
                        //zoom the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 10
                        ));
                        //Adding marker on map
                        googleMap.addMarker(markerOptions);

                    }
                });
            }
        });

        return view;
    }
//
//    private void getCurrentLocation() {
//        //Initialise location manager
////        LocationManager locationManager = (LocationManager) getActivity()
////                .getSystemService(Context.LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]
//                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
//            return;
//        }
//        Task<Location> task = client.getLastLocation();
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    currentlocation = location;
//                    Toast.makeText(getContext(), currentlocation.getLatitude()+""+currentlocation.getLongitude(),
//                            Toast.LENGTH_SHORT).show();
//                    mapFragment.getMapAsync((OnMapReadyCallback) getActivity());
//                }
//            }
//        });





        //check condition
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                || locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
//            //When location service is enabled, get last location
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                @Override
//                public void onComplete(@NonNull Task<Location> task) {
//                    //Initialise location
//                    Location location = task.getResult();
//
//                    //check condition
//                    if (location != null) {
//                        //When not null, set latitude
//
//                    }
//                }
//            })
//        }
//    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        searchView.findViewById(R.id.sv_location);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                String location = searchView.getQuery().toString();
//                List<Address> addressList = null;
//
//                if (location != null || !location.equals("")) {
//                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//                    try {
//                        addressList = geocoder.getFromLocationName(location,1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
//                    googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//        mapFragment.getMapAsync(this);
//    }
//    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap = googleMap;
//        LatLng latLng = new LatLng(currentlocation.getLatitude(),currentlocation.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I Am Here!");
//        gmap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
//        gmap.addMarker(markerOptions);
    }

//    @Override
//        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.requestPermissions(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case REQUEST_CODE:
//                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getCurrentLocation();
//                }
//                break;
//        }
//    }




}