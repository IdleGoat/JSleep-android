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


/**
 * A login screen that offers login via email and password.
 * @author Rafie
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText email,password;
    Context mContext;
    public static Account loggedAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_login);

        mApiService = UtilsApi.getApiService();
        mContext = this;
        TextView register = findViewById(R.id.login_register);



        email = findViewById(R.id.login_editemail);
        password = findViewById(R.id.login_editpassword);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(move);
            }
        });



        Button loginbutton = findViewById(R.id.login_button);




        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtemp = email.getText().toString();
                String passtemp = password.getText().toString();



                Account account = requestLogin(emailtemp,passtemp);


            }
        });
    }

    /**
     * This function is used to request login to the server
     * @param email email of the user
     * @param password password of the user
     * @return Account object
     * @author Rafie Amandio Fauzan
     * @see Account
     */
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

