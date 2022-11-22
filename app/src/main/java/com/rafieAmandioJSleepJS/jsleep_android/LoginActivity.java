package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rafieAmandioJSleepJS.jsleep_android.model.Account;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText email,password;
    Context mContext;
    public static Account loggedAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        TextView register = findViewById(R.id.register);
        email = findViewById(R.id.EditEmail);
        password = findViewById(R.id.EditPassword);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(move);
            }
        });
        Button loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtemp = email.getText().toString();
                String passtemp = password.getText().toString();
                Account account = requestLogin(emailtemp,passtemp);
                //Account account1 = requestAccount();

            }
        });
    }

    protected Account requestAccount(){
        mApiService.getAccount(0).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "no Account id = 0", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    protected Account requestLogin(String email,String password){
        mApiService.login(email,password).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    loggedAcc = account;
                    System.out.println(account.toString());
                    Intent move = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}

