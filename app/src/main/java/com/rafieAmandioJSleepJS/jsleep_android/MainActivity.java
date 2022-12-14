package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    ConstraintLayout lihatSemua;
    Context mContext;
    ImageView next,prev,go;
    ImageView profile,history,search,profilemenu;
    EditText etPage;
    ListView listView;
    List<Room> activitylist;
    public static ArrayList<Room> listRoom;
    public static int roomIndex;
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
        search = findViewById(R.id.main_searchicon);
        history = findViewById(R.id.main_historyicon);
        profilemenu = findViewById(R.id.main_profileicon);
        lihatSemua = findViewById(R.id.main_lihatsemuaLayout);
        lihatSemua.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SearchRoomActivity.class);
            startActivity(intent);
        });
        profilemenu.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MeActivity.class);
            startActivity(intent);
        });
        search.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchRoomActivity.class);
            startActivity(intent);
        });
        history.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BuyerSeeOrderActivity.class);
            startActivity(intent);
        });
        profile = findViewById(R.id.main_profileimage);
        etPage = findViewById(R.id.main_pageNumber);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        activitylist = getRoomList(current);
        profile.setOnClickListener(v -> {
            Intent move = new Intent(MainActivity.this,MeActivity.class);
            startActivity(move);
        });
        next = findViewById(R.id.main_nextbutton);
        prev = findViewById(R.id.main_prevbutton);
        go = findViewById(R.id.main_gobutton);
        listView = (ListView) findViewById(R.id.main_availableRoomsList);
        listView.setOnItemClickListener(this::onItemClick);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activitylist = getRoomListGo(Integer.parseInt(etPage.getText().toString()));
            }
        });
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
                    //etPage.setText(String.valueOf(current));
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
        mApiService.getAllRoom(page, 4).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {

                    activitylist = response.body();
                    if(activitylist.size() == 0){
                        Toast.makeText(mContext, "No more room", Toast.LENGTH_SHORT).show();
                        current -= 1;
                    }
                    else{
                        etPage.setText(String.valueOf(page+1));
                        listRoom = new ArrayList<Room>(activitylist);
                        CustomListAdapter adapter = new CustomListAdapter(mContext, listRoom);
                        listView.setAdapter(adapter);
                        System.out.println("Berhasil get Room");
                    }
                    assert activitylist != null;




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

    protected List<Room> getRoomListGo(int page) {
        //System.out.println(pageSize);

        mApiService.getAllRoom(page-1, 4).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {

                    activitylist = response.body();
                    if(activitylist.size() == 0){
                        Toast.makeText(mContext, "No Room in that page", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        current = page-1;
                        listRoom = new ArrayList<Room>(activitylist);
                        CustomListAdapter adapter = new CustomListAdapter(mContext, listRoom);
                        listView.setAdapter(adapter);
                        etPage.setText(String.valueOf(current+1));
                        System.out.println("Berhasil get Room");
                    }
                    assert activitylist != null;




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

    /**
     *
     * @param v is the view
     * @param position is the position of the room
     * @param id is the id of the room
     * @author Rafie Amandio Fauzan
     */

    public void onItemClick (AdapterView<?> l, View v, int position, long id){
        System.out.println("onItemClick Success");
        Log.i("ListView", "You clicked Item np : " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent intent = new Intent();
        roomIndex = position;

        intent.setClass(mContext, DetailRoomActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}