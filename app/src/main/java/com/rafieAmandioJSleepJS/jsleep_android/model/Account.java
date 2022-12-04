
package com.rafieAmandioJSleepJS.jsleep_android.model;

import androidx.annotation.NonNull;


/**
 * Represent an account in the system
 * @author Rafie
 * @version 1.0
 */
public class Account extends Serializable {

    /**
     * The account's name
     */
    public String name;
    /**
     * The account's password
     */
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
