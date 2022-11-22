package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MeActivity extends AppCompatActivity {
    TextView name,email,balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        name = findViewById(R.id.me_name3);
        email = findViewById(R.id.me_name4);
        balance = findViewById(R.id.me_name5);
        name.setText(LoginActivity.loggedAcc.name);
        email.setText(LoginActivity.loggedAcc.email);
        balance.setText(String.valueOf(LoginActivity.loggedAcc.balance));
    }
}