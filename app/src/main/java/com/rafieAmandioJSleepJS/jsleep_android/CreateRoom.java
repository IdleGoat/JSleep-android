package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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

public class CreateRoom extends AppCompatActivity {

    Context mContext;
    BaseApiService mApiService;
    Spinner Spinnercity, Spinnerbedtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApiService = UtilsApi.getApiService();
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        Spinnercity = findViewById(R.id.SpinnerCity);
        Spinnerbedtype = findViewById(R.id.SpinnerBedType);
        Spinnerbedtype.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));
        Spinnercity.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));

    }

    protected Room requestRoom(int id, String name, int size, int price, Facility facility, City city, String address, BedType bedType) {
        mApiService.createRoom(id,name,size,price,facility,city,address).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {

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

