package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileUpdateActivity extends AppCompatActivity {
    public String[] categories={"Mechanic","Tire Shop", "Tow Truck","Car Wash" };
    private GoogleMap mMap;
    EditText uWorkspace;
    EditText uName;
    EditText uEmail;
    EditText uAddress;
    EditText uPhone;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;
    Button acceptButton;
    Button updateLocation;
    ImageView imageView;
    String category;
    LocationManager locationManager;
    LocationListener locationListener;
    Spinner spinner;
    ArrayAdapter<String> dataAdapterForCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        imageView = findViewById(R.id.imageview1);
        imageView.setImageResource(R.drawable.mechanic);
        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setBackgroundColor(Color.TRANSPARENT);
        dataAdapterForCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapterForCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapterForCategory);
        uName = findViewById(R.id.uName);
        uEmail = findViewById(R.id.uEmail);
        uAddress = findViewById(R.id.uAdress);
        uPhone = findViewById(R.id.uPhone);
        uWorkspace = findViewById(R.id.uWorkspace);
        acceptButton = findViewById(R.id.acceptButton);
        updateLocation = findViewById(R.id.updateLocation);



        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
               /* mMap.clear();
                LatLng userLocation =new LatLng(location.getLatitude(),location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).title("My Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));*/
/*
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                geocoder.getFromLocation();*/
            }
        };

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userId);
        documentReference.addSnapshotListener(this, (documentSnapshot, e) -> {
            if (documentSnapshot.exists()) {
                uName.setText(documentSnapshot.getString("name"));
                uEmail.setText(documentSnapshot.getString("email"));
                uAddress.setText(documentSnapshot.getString("address"));
                uPhone.setText(documentSnapshot.getString("phone"));
                uWorkspace.setText(documentSnapshot.getString("workplace"));
                boolean isAdmin = documentSnapshot.getBoolean("isAdmin");
                if (isAdmin!=true){
                    uWorkspace.setVisibility(View.INVISIBLE);
                    uAddress.setVisibility(View.INVISIBLE);
                    updateLocation.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                }
            } else {
                Log.d("tag", "onEvent: Document do not exists");
            }
        });
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference1 = db.collection("users").document(userId);
                Map<String, Object> editedUser = new HashMap<>();
                editedUser.put("name", uName.getText().toString());
                editedUser.put("email", uEmail.getText().toString());
                editedUser.put("address", uAddress.getText().toString());
                editedUser.put("category",category);
                editedUser.put("phone", uPhone.getText().toString());
                editedUser.put("workplace", uWorkspace.getText().toString());
                documentReference1.update(editedUser);
                Intent updateToProfile = new Intent(ProfileUpdateActivity.this,ProfileActivity.class);
                startActivity(updateToProfile);
            }
        });
        updateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("info","new");
                startActivity(intent);
               /* if (ActivityCompat.checkSelfPermission(ProfileUpdateActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(ProfileUpdateActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //permission
                    ActivityCompat.requestPermissions(ProfileUpdateActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                }
                //location
                else{
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                    Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(lastLocation !=null){
                        LatLng userLocation= new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                        DocumentReference documentReference2 = db.collection("users").document(userId);
                        documentReference2.update("location",userLocation);
                    }
                }*/
            }
        });

    }
}