package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.rafieAmandioJSleepJS.jsleep_android.model.Room;

public class DetailRoomActivity extends AppCompatActivity {

    Room clickedRoom = MainActivity.listRoom.get(MainActivity.roomIndex);
    TextView nametext,addresstext,sizetext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        nametext = findViewById(R.id.detail_nametext);
        addresstext = findViewById(R.id.detail_addresstext);
        sizetext = findViewById(R.id.detail_sizetext);

        nametext.setText(clickedRoom.name);
        addresstext.setText(clickedRoom.address);
        String sizeText = String.valueOf(clickedRoom.size) + "m" ;
        sizetext.setText(sizeText);


    }
}