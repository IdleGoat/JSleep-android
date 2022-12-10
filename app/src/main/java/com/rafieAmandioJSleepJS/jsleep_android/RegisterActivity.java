package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rafieAmandioJSleepJS.jsleep_android.model.Account;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    EditText name,email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        EditText email = findViewById(R.id.register_editemail);
        EditText pass = findViewById(R.id.register_editpassword);
        EditText name = findViewById(R.id.register_editname);
        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempemail = email.getText().toString();
                String temppass = pass.getText().toString();
                String tempname = name.getText().toString();
                Account account = requestRegister(tempemail,temppass,tempname);
            }
        });


    }


    protected Account requestRegister(String email, String password, String name ){
        System.out.println("tEST1");
        mApiService.register(email, password, name).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                    Intent move = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(move);
                    System.out.println("tEST2");
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Account Already Registered", Toast.LENGTH_SHORT).show();
                System.out.println("tEST3");
            }
        });
        return null;
    }



}