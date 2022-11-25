package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rafieAmandioJSleepJS.jsleep_android.model.Room;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;
    Button next,prev;
    List<Room> roomdisp;
    List<Room> activitylist;
    int current;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        current = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        activitylist = getRoomList(current,7);
        next = findViewById(R.id.NextButton);
        prev = findViewById(R.id.PrevButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current += 1;
                activitylist = getRoomList(current,7);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current -= 1;
                activitylist = getRoomList(current,7);
            }
        });
//        ArrayList<String> listId = new ArrayList<>();
//        Gson gson = new Gson();
//        try {
//            InputStream filepath = getAssets().open("randomRoomList.json");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));
//            Room[] temp = gson.fromJson(reader, Room[].class);
//            Collections.addAll(ListRoom, temp);
//            for (Room r : ListRoom ) {
//                listId.add(r.name);
//            }
//            ArrayAdapter<String> roomArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listId);
//            ListView listView = findViewById(R.id.ListEntries);
//            listView.setAdapter(roomArrayAdapter);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile_button) {
            Intent move = new Intent(MainActivity.this, MeActivity.class);
            startActivity(move);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    protected List<Room> getRoomList(int page, int pageSize) {
        //System.out.println(pageSize);
        mApiService.getAllRoom(page, pageSize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    ArrayList<String> temp = new ArrayList<>();
                    roomdisp = response.body();
                    for(Room i : roomdisp){
                        temp.add(i.name);
                    }
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,temp);
                    ListView listView = (ListView) findViewById(R.id.ListEntries);
                    listView.setAdapter(itemAdapter);
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