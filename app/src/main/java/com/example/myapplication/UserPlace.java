package com.example.myapplication;

import java.io.Serializable;

public class UserPlace implements Serializable {

    public String name;
    public String email;
    public Double longitude;
    public Double latitude;
    public String phone;
    public String category;

    public UserPlace(String name,String email,String category,String phone, Double latitude, Double longitude){

        this.name=name;
        this.email=email;
        this.category=category;
        this.phone=phone;
        this.latitude=latitude;
        this.longitude=longitude;
    }
}
