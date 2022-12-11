package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.rafieAmandioJSleepJS.jsleep_android.model.BedType;
import com.rafieAmandioJSleepJS.jsleep_android.model.Payment;
import com.rafieAmandioJSleepJS.jsleep_android.model.Room;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRoomActivity extends AppCompatActivity {

    Spinner filterChoose;
    EditText filterInput;
    Button filterButton;
    ListView roomList;
    ArrayList<Room> roomListArray;
    BaseApiService mApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_room);

        mApiService = UtilsApi.getApiService();
        mContext = this;
        filterChoose = findViewById(R.id.spinnerfilter);
        filterInput = findViewById(R.id.search_edittext);
        filterButton = findViewById(R.id.search_button_inside);
        roomList = findViewById(R.id.search_listview);
        filterChoose.setAdapter(new ArrayAdapter<Filter>(this, android.R.layout.simple_spinner_item, Filter.values()));

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( filterChoose.getSelectedItem().equals(Filter.NAME) ) {
                    collectByName(filterInput.getText().toString());
                }
            }
        });
    }


    enum Filter{
        NAME, CITY, PRICE, FACILITY
    }


    protected void collectByName(String name){

        mApiService.collectByName(name).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    List<Room> orderlist = response.body();
                    assert orderlist != null;
                    roomListArray = new ArrayList<Room>(orderlist);
                    Toast.makeText(mContext, "Filter By Name Success", Toast.LENGTH_SHORT).show();
                    CustomListAdapter adapter = new CustomListAdapter(mContext,roomListArray);
                    roomList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Filter By Name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}