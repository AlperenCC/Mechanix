package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;
    DocumentReference documentReference5;
    boolean isAdmin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);



        Intent intent= getIntent();
        String info = intent.getStringExtra("info");
        if (info.matches("new")){
            //Haritada kullanıcı konumu
            locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener= new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {

                    SharedPreferences sharedPreferences = MapsActivity.this.getSharedPreferences("com.example.maps",MODE_PRIVATE);
                    boolean trackBoolean =sharedPreferences.getBoolean("trackBoolean",false);
                    if (trackBoolean == false) {
                        LatLng userLocation=new LatLng(location.getLatitude(),location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));
                        sharedPreferences.edit().putBoolean("trackBoolean",true).apply();
                    }


                }
            };
            //konum için kullanıcıdan izin almak
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }else
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,10,locationListener);
                //bir sorun karşısında son bilinen konumu almak için
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastLocation!= null){
                    LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));

                }
            }

        }else{
            //VERİ TABANINDAN ÇEKİLECEK VERİLER
            mMap.clear();
           Place place=(Place) intent.getSerializableExtra("userLocation");
           LatLng latLng = new LatLng(place.latitude,place.longitude);
           String placeName= place.name;

           mMap.addMarker(new MarkerOptions().position(latLng).title(placeName));
           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));


        }


    }
    // kullanıcı izini ilk defa alındığında
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0){
            if(requestCode==1){
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,10,10,locationListener);

                    Intent intent =getIntent();
                    String info = intent.getStringExtra("info");
                    if (info.matches("new")){
                        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (lastLocation!= null){
                            LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));
                        }

                    }else{
                        //Veri Tabanından çekilecek veriler
                        mMap.clear();
                        Place place=(Place) intent.getSerializableExtra("userLocation");
                        LatLng latLng = new LatLng(place.latitude,place.longitude);
                        String placeName= place.name;

                        mMap.addMarker(new MarkerOptions().position(latLng).title(placeName));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                    }
                }
            }
        }

    }
    //tıklanan yeri işaretleme ve adresleri almak
    @Override
    public void onMapClick(LatLng latLng) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            userId = firebaseAuth.getCurrentUser().getUid();
            documentReference5 = db.collection("users").document(userId);
            documentReference5.addSnapshotListener(this, (documentSnapshot, e) -> {
                if (documentSnapshot.exists()) {
                    isAdmin = documentSnapshot.getBoolean("isAdmin");
                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            });
        }
        if(isAdmin){
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().title("Your Place").position(latLng));
            String name = " ";
            Double latitude = latLng.latitude;
            Double longitude = latLng.longitude;
            LatLng selectedLocation = new LatLng(latitude,longitude);
            Place place = new Place(name,latitude,longitude);

            AlertDialog.Builder alertDialog  = new AlertDialog.Builder(MapsActivity.this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Are you sure?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    documentReference5.update("location", place);
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
        }
        else{
            locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener= new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {

                    SharedPreferences sharedPreferences = MapsActivity.this.getSharedPreferences("com.example.maps",MODE_PRIVATE);
                    boolean trackBoolean =sharedPreferences.getBoolean("trackBoolean",false);
                    if (trackBoolean == false) {
                        LatLng userLocation=new LatLng(location.getLatitude(),location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));
                        sharedPreferences.edit().putBoolean("trackBoolean",true).apply();

                    }


                }
            };

        }
    }
}