package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class MainActivity extends AppCompatActivity {

    CircleMenu circleMenu;
    ConstraintLayout constraintLayout;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageview);
        imageView.setImageResource(R.drawable.mechanic);
        circleMenu = findViewById(R.id.circlemenu);
        constraintLayout = findViewById(R.id.ConstraintLayout);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Handler handler= new Handler();

        if(firebaseUser ==null){
            circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.ic_choices,R.drawable.ic_choices)
                    .addSubMenu(Color.parseColor("#88BEF5"),R.drawable.login)
                    .addSubMenu(Color.parseColor("#83E85A"),R.drawable.ic_register)
                    .addSubMenu(Color.parseColor("#FF4B32"),R.drawable.ic_location)
                    .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                        @Override
                        public void onMenuSelected(int index) {
                            if(index==0){
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent2 = new Intent(MainActivity.this,LoginActivity.class);
                                        startActivity(intent2);
                                    }
                                },900);
                            }
                            else if(index==1){
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent2 = new Intent(MainActivity.this,RegisterActivity.class);
                                        startActivity(intent2);
                                    }
                                },900);
                            }
                            else if(index==2){
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent3 = new Intent(MainActivity.this,MapsActivity.class);
                                        intent3.putExtra("info","new");
                                        startActivity(intent3);
                                    }
                                },900);
                            }
                        }
                    });
        }
        else{
            circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.ic_choices,R.drawable.ic_choices)
                    .addSubMenu(Color.parseColor("#83E85A"),R.drawable.ic_baseline_person_24)
                    .addSubMenu(Color.parseColor("#FF4B32"),R.drawable.ic_location)
                    .addSubMenu(Color.parseColor("#BA53DE"),R.drawable.ic_category)
                    .addSubMenu(Color.parseColor("#81D4FA"),R.drawable.ic_date)
                    .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                        @Override
                        public void onMenuSelected(int index) {
                            if(index==0){
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent2 = new Intent(MainActivity.this,ProfileActivity.class);
                                        startActivity(intent2);
                                    }
                                },900);
                            }
                            else if(index==1){
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent2 = new Intent(MainActivity.this,MapsActivity.class);
                                        intent2.putExtra("info","new");
                                        startActivity(intent2);
                                    }
                                },900);
                            }
                            else if(index==2){
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent3 = new Intent(MainActivity.this,CategoryActivity.class);
                                        startActivity(intent3);
                                    }
                                },900);
                            }
                            else if(index==3){
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent4 = new Intent(MainActivity.this, AppointmentsList.class);
                                        startActivity(intent4);
                                    }
                                },900);
                            }
                        }
                    });
        }
    }
}