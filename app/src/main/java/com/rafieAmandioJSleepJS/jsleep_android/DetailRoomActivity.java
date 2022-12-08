package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.rafieAmandioJSleepJS.jsleep_android.model.Room;

public class DetailRoomActivity extends AppCompatActivity {

    public static Room clickedRoom = MainActivity.listRoom.get(MainActivity.roomIndex);
    TextView nametext,addresstext,sizetext;
    Button bookbutton;

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

        bookbutton = findViewById(R.id.detail_bookbutton);
        bookbutton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailRoomActivity.this, PaymentDetailActivity.class);
            startActivity(intent);
        });

    }
}