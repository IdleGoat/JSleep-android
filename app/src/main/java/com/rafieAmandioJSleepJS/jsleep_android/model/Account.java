package com.rafieAmandioJSleepJS.jsleep_android.model;

public class Account extends Serializable {

    public String name;
    public String password;
    public Renter renter;
    public String email;
    public double balance;

    public Account(int id) {
        super(id);
    }
}
