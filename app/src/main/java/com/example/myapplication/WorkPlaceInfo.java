package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WorkPlaceInfo extends AppCompatActivity {

    TextView workPlace;
    TextView category;
    TextView phone;
    TextView email;
    Button mapsButton;
    Button requestButton;
    UserPlace userPlace;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_place_info);
        workPlace=findViewById(R.id.workPlaceText);
        category=findViewById(R.id.categoryText);
        phone=findViewById(R.id.phoneText);
        email=findViewById(R.id.emailText);
        mapsButton=findViewById(R.id.mapsButton);
        requestButton=findViewById(R.id.requestButton);
        Intent intent = getIntent();
        userPlace =(UserPlace) intent.getSerializableExtra("place");
        String uworkPlace= userPlace.name.toString();
        String uEmail= userPlace.email;
        String uPhone= userPlace.phone;
        String uCategory = userPlace.category;
        Double latitude= userPlace.latitude;
        Double longitude= userPlace.longitude;

        Place userLocation= new Place(uworkPlace,latitude,longitude);


        workPlace.setText(uworkPlace);
        phone.setText(uCategory);
        category.setText(uPhone);
        email.setText(uEmail);

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(WorkPlaceInfo.this,MapsActivity.class);
                intent1.putExtra("info","old");
                intent1.putExtra("userLocation",userLocation);
                startActivity(intent1);

            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkPlaceInfo.this,RequestForm.class);
                intent.putExtra("email",uEmail);
                startActivity(intent);

            }
        });


    }


}