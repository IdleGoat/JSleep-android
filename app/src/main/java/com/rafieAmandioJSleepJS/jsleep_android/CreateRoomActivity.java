package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.rafieAmandioJSleepJS.jsleep_android.model.BedType;
import com.rafieAmandioJSleepJS.jsleep_android.model.City;
import com.rafieAmandioJSleepJS.jsleep_android.model.Facility;
import com.rafieAmandioJSleepJS.jsleep_android.model.Room;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity to create Room
 * @author Rafie Amandio
 */
public class CreateRoomActivity extends AppCompatActivity {

    Context mContext;
    BaseApiService mApiService;
    Button btnCreateRoom;
    ToggleButton ac;
    EditText etName, etSize, etPrice, etAddress;
    ArrayList<Facility> facility = new ArrayList<Facility>();
    ToggleButton  fridge, wifi, bathub, balcony, restaurant, pool, fitness;
    Spinner Spinnercity, Spinnerbedtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        mApiService = UtilsApi.getApiService();
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        Spinnercity = findViewById(R.id.createroom_spinner_city);
        Spinnerbedtype = findViewById(R.id.createroom_spinner_bed);
        btnCreateRoom = findViewById(R.id.createroom_button);

        ac = findViewById(R.id.createroom_ac);
        fridge = findViewById(R.id.createroom_refrigerator);
        wifi = findViewById(R.id.createroom_wifi);
        bathub = findViewById(R.id.createroom_bathtub);
        balcony = findViewById(R.id.createroom_balcony);
        restaurant = findViewById(R.id.createroom_restaurant);
        pool = findViewById(R.id.createroom_pool);
        fitness = findViewById(R.id.createroom_fitness);

        etName = findViewById(R.id.createroom_name);
        etSize = findViewById(R.id.createroom_size);
        etPrice = findViewById(R.id.createroom_price);
        etAddress = findViewById(R.id.createroom_address);

        Spinnerbedtype.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));
        Spinnercity.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));

        btnCreateRoom.setOnClickListener(v -> {

            if (ac.isChecked()) {
                facility.add(Facility.AC);
            }
            if (fridge.isChecked()) {
                facility.add(Facility.Refrigerator);
            }
            if (wifi.isChecked()) {
                facility.add(Facility.WiFi);
            }
            if (bathub.isChecked()) {
                facility.add(Facility.Bathtub);
            }
            if (balcony.isChecked()) {
                facility.add(Facility.Balcony);
            }
            if (restaurant.isChecked()) {
                facility.add(Facility.Restaurant);
            }
            if (pool.isChecked()) {
                facility.add(Facility.SwimmingPool);
            }
            if (fitness.isChecked()) {
                facility.add(Facility.FitnessCenter);
            }
            String bed = Spinnerbedtype.getSelectedItem().toString();
            String cityStr = Spinnercity.getSelectedItem().toString();

            BedType bedType = BedType.valueOf(bed);
            City city = City.valueOf(cityStr);

            int size = Integer.parseInt(etSize.getText().toString());
            int price = Integer.parseInt(etPrice.getText().toString());

            requestRoom(LoginActivity.loggedAcc.id, etName.getText().toString(), size,price, facility, city, etAddress.getText().toString(), bedType);
        });

    }

    /**
     * A method that create a new room.
     * @param id the id of the renter.
     * @param name the name of the room.
     * @param size the size of the room.
     * @param price the price of the room.
     * @param facility the facility of the room.
     * @param city the city of the room.
     * @param address the address of the room.
     * @param bedType the bed type of the room.
     * @return
     */
    protected Room requestRoom(int id, String name, int size, int price, ArrayList<Facility> facility, City city, String address, BedType bedType) {

        mApiService.createRoom(id,name,size,price,facility,city,address,bedType).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    System.out.println("Request Berhasil");
                    Room responseq = response.body();
                    System.out.println(responseq.toString());
                    Toast.makeText(mContext, "Room Created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateRoomActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(mContext, "Fail To Create Room", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }


}

