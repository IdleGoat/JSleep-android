package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class CreateRoomActivity extends AppCompatActivity {

    Context mContext;
    BaseApiService mApiService;
    Button btnCreateRoom;
    ToggleButton ac;
    EditText etName, etSize, etPrice, etAddress;
    ArrayList<Facility> facility = new ArrayList<Facility>();
    CheckBox  fridge, wifi, bathub, balcony, restaurant, pool, fitness;
    Spinner Spinnercity, Spinnerbedtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApiService = UtilsApi.getApiService();
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        Spinnercity = findViewById(R.id.SpinnerCity);
        Spinnerbedtype = findViewById(R.id.SpinnerBedType);
        btnCreateRoom = findViewById(R.id.room_createbutton);

        ac = findViewById(R.id.CheckAC);
        fridge = findViewById(R.id.CheckRefrigirator);
        wifi = findViewById(R.id.CheckWiFi);
        bathub = findViewById(R.id.CheckBathTub);
        balcony = findViewById(R.id.CheckBalcony);
        restaurant = findViewById(R.id.CheckRestaurant);
        pool = findViewById(R.id.CheckSwimming);
        fitness = findViewById(R.id.CheckFitness);

        etName = findViewById(R.id.NameHotel);
        etSize = findViewById(R.id.SizeHotel);
        etPrice = findViewById(R.id.PriceHotel);
        etAddress = findViewById(R.id.AddressHotel);

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

        mApiService.createRoom(id,name,size,price,facility,city,address).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    System.out.println("Request Berhasil");
                    Room responseq = response.body();
                    System.out.println(responseq.toString());

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

