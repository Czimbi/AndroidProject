package com.example.tattoshop;

public interface ReservationDAO {
    //Új időpont felvétele
    void insert();
    //Időpont törlése
    void delete();
    //Időpont lekérése
    void getReservedTime(String artist);
}
