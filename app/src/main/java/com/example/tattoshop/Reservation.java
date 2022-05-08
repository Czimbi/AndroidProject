package com.example.tattoshop;


import java.util.HashMap;
import java.util.Map;

public class Reservation {
    private String artist;
    private Map<String, Object> reservedTimes = new HashMap<>();

    public Reservation(String artist, Map<String, Object> reservedTimes){
        this.artist = artist;
        this.reservedTimes= reservedTimes;
    }

}
