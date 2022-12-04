package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.rafieAmandioJSleepJS.jsleep_android.model.Account;
import com.rafieAmandioJSleepJS.jsleep_android.model.Room;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A main activity that shows the list of rooms.
 * @author Rafie
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {


    BaseApiService mApiService;
    Context mContext;
    ImageView next,prev;
    ImageView profile;
    List<Room> activitylist;
    int current;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        current = 0;
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);
        profile = findViewById(R.id.main_profileimage);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        activitylist = getRoomList(current);
        profile.setOnClickListener(v -> {
            Intent move = new Intent(MainActivity.this,MeActivity.class);
            startActivity(move);
        });
        next = findViewById(R.id.main_nextbutton);
        prev = findViewById(R.id.main_prevbutton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current += 1;
                activitylist = getRoomList(current);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current == 0){
                    Toast.makeText(mContext, "You are at the first page", Toast.LENGTH_SHORT).show();
                }
                else{
                    current -= 1;
                    activitylist = getRoomList(current);
                }
            }
        });


    }


    /**
     * This Function is used to get the list of room
     * @param page is the page number
     * @return the list of room
     * @see Room
     * @author Rafie Amandio Fauzan
     */

    protected List<Room> getRoomList(int page) {
        //System.out.println(pageSize);
        mApiService.getAllRoom(page, 5).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {

                    List<Room> roomdisp = response.body();
                    ArrayList<String> temp = new ArrayList<>();

                    assert roomdisp != null;
                    ArrayList<Room> listRoom = new ArrayList<Room>(roomdisp);

                    CustomListAdapter adapter = new CustomListAdapter(mContext, listRoom);
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,temp);
                    ListView listView = (ListView) findViewById(R.id.main_availableRoomsList);
                    listView.setAdapter(adapter);
                    System.out.println("Berhasil get Room");
                }
            }
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "get room failed", Toast.LENGTH_SHORT).show();
            }

        });
        return null;
    }
}