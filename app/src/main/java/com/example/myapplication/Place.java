package com.example.myapplication;

import java.io.Serializable;

public class Place implements Serializable {
    public Double latitude;
    public Double longitude;
    public String name;

    public Place(String name,Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name=name;

    }
}