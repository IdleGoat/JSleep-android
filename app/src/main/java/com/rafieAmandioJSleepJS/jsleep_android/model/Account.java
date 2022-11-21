
package com.rafieAmandioJSleepJS.jsleep_android.model;

import androidx.annotation.NonNull;

public class Account extends Serializable {

    public String name;
    public String password;
    public Renter renter;
    public String email;
    public double balance;

    @NonNull
    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", email=" + email + '\'' +
                ", name=" + name + '\'' +
                ", password=" + password + '\'' +
                ", renter=" + renter +
                '}';


    }

    public Account(int id) {
        super(id);
    }
}
