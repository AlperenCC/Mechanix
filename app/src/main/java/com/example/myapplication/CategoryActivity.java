package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity {

    RelativeLayout tireShopRelative;
    RelativeLayout mechanicRelative;
    RelativeLayout carWashRelative;
    RelativeLayout towTruckRelative;
    TextView tireShopText;
    TextView mechanicText;
    TextView towTruckText;
    TextView carWashText;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        tireShopText= findViewById(R.id.tireShopText);
        mechanicText=findViewById(R.id.mechanicText);
        towTruckText=findViewById(R.id.towTruckText);
        carWashText= findViewById(R.id.carWashText);

        tireShopRelative=findViewById(R.id.tireShopRelative);
        mechanicRelative=findViewById(R.id.mechanicRelative);
        carWashRelative=findViewById(R.id.carWashRelative);
        towTruckRelative=findViewById(R.id.towTruckRelative);

        tireShopRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=tireShopText.getText().toString();
                Intent intent = new Intent(CategoryActivity.this,ListActivity.class);
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });
        mechanicRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=mechanicText.getText().toString();
                Intent intent = new Intent(CategoryActivity.this,ListActivity.class);
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });
        carWashRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=carWashText.getText().toString();
                Intent intent = new Intent(CategoryActivity.this,ListActivity.class);
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });
        towTruckRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=towTruckText.getText().toString();
                Intent intent = new Intent(CategoryActivity.this,ListActivity.class);
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });

    }
}