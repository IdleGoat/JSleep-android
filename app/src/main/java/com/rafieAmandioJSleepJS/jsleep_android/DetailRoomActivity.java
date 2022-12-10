package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.rafieAmandioJSleepJS.jsleep_android.model.Facility;
import com.rafieAmandioJSleepJS.jsleep_android.model.Room;

public class DetailRoomActivity extends AppCompatActivity {

    public static Room clickedRoom;
    TextView nametext,addresstext,sizetext,pricetext,bedtype,city;
    ToggleButton ac,fridge, wifi, bathtub, balcony, restaurant, pool, fitness;
    Button bookbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        super.onCreate(savedInstanceState);
        clickedRoom = MainActivity.listRoom.get(MainActivity.roomIndex);
        setContentView(R.layout.activity_detail_room);
        nametext = findViewById(R.id.detail_nametext);
        addresstext = findViewById(R.id.detail_addresstext);
        sizetext = findViewById(R.id.detail_sizetext);
        ac = findViewById(R.id.detail_ac);
        fridge = findViewById(R.id.detail_refrigerator);
        wifi = findViewById(R.id.detail_wifi);
        bathtub = findViewById(R.id.detail_bathtub);
        balcony = findViewById(R.id.detail_balcony);
        restaurant = findViewById(R.id.detail_restaurant);
        pool = findViewById(R.id.detail_pool);
        fitness = findViewById(R.id.detail_fitness);
        pricetext = findViewById(R.id.detail_pricetext);
        bedtype = findViewById(R.id.detail_filltextviewbed);
        city = findViewById(R.id.detail_filltextviewcity);

        bedtype.setText(clickedRoom.bedType.toString());
        city.setText(clickedRoom.city.toString());

        if(clickedRoom.facility.contains(Facility.AC)){
            ac.setChecked(true);
        }
        if(clickedRoom.facility.contains(Facility.Refrigerator)){
            fridge.setChecked(true);
        }
        if(clickedRoom.facility.contains(Facility.WiFi)){
            wifi.setChecked(true);
        }
        if(clickedRoom.facility.contains(Facility.Bathtub)){
            bathtub.setChecked(true);
        }
        if(clickedRoom.facility.contains(Facility.Balcony)){
            balcony.setChecked(true);
        }
        if(clickedRoom.facility.contains(Facility.Restaurant)){
            restaurant.setChecked(true);
        }
        if(clickedRoom.facility.contains(Facility.SwimmingPool)){
            pool.setChecked(true);
        }
        if(clickedRoom.facility.contains(Facility.FitnessCenter)){
            fitness.setChecked(true);
        }

        String price = "Rp. " + clickedRoom.price.price + " / night";
        pricetext.setText(price);
        nametext.setText(clickedRoom.name);
        addresstext.setText(clickedRoom.address);
        String sizeText = String.valueOf(clickedRoom.size) + "m" ;
        sizetext.setText(sizeText);

        bookbutton = findViewById(R.id.detail_bookbutton);
        bookbutton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailRoomActivity.this, PaymentDetailActivity.class);
            startActivity(intent);
        });

    }
}