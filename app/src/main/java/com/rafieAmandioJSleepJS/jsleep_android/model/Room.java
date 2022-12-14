package com.rafieAmandioJSleepJS.jsleep_android.model;

import java.util.ArrayList;
import java.util.Date;

public class Room extends Serializable{

    public int accountId;
    public ArrayList<Date> booked;
    public String name;
    public String address;
    public Price price;
    public City city;
    public int size;
    public BedType bedType;
    public ArrayList<Facility> facility;


    public Room(int id) {
        super(id);
    }
}
