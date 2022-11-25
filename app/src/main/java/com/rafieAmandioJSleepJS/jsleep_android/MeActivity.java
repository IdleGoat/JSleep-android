package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rafieAmandioJSleepJS.jsleep_android.model.Account;
import com.rafieAmandioJSleepJS.jsleep_android.model.Renter;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeActivity extends AppCompatActivity {
    TextView name,email,balance;
    TextView Me_nameRenterEdit,Me_addressRenterEdit,Me_phoneRenterEdit;
    EditText nameRenterEdit,addressRenterEdit,phoneRenterEdit;
    Button cancelRenterBtn,registerRenterBtn,card1RegisterRenter;
    CardView cardRegister,cardDisplay;
    LinearLayout cardInput;
    BaseApiService mApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        name = findViewById(R.id.Me_nameEdit);
        email = findViewById(R.id.Me_emailEdit);
        balance = findViewById(R.id.Me_balanceEdit);

        name.setText(LoginActivity.loggedAcc.name);
        email.setText(LoginActivity.loggedAcc.email);
        balance.setText(String.valueOf(LoginActivity.loggedAcc.balance));

        nameRenterEdit = findViewById(R.id.nameRenter);
        addressRenterEdit = findViewById(R.id.addressRenter);
        phoneRenterEdit = findViewById(R.id.phoneRenter);


        cancelRenterBtn = findViewById(R.id.Me_cancelButton);
        registerRenterBtn = findViewById(R.id.Me_registerButton);

        card1RegisterRenter = findViewById(R.id.Me_registerRenterButton);
        cardRegister = findViewById(R.id.card2_LinearLayout);
        cardDisplay = findViewById(R.id.card2_cardView);
        cardInput = findViewById(R.id.card1_LinearLayout);

        Me_nameRenterEdit = findViewById(R.id.Me_nameRenterEdit);
        Me_addressRenterEdit = findViewById(R.id.Me_addressRenterEdit);
        Me_phoneRenterEdit = findViewById(R.id.Me_phoneNumberEdit);

        if(LoginActivity.loggedAcc.renter == null){
            cardRegister.setVisibility(View.VISIBLE);
            cardDisplay.setVisibility(View.INVISIBLE);
            cardInput.setVisibility(View.INVISIBLE);
            card1RegisterRenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardRegister.setVisibility(View.INVISIBLE);
                    cardDisplay.setVisibility(View.INVISIBLE);
                    cardInput.setVisibility(View.VISIBLE);
                }
            });
            registerRenterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Renter accountRenter = registerRenter();
                }
            });
            cancelRenterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardRegister.setVisibility(View.VISIBLE);
                    cardDisplay.setVisibility(View.INVISIBLE);
                    cardInput.setVisibility(View.INVISIBLE);
                }
            });
        }

        if(LoginActivity.loggedAcc.renter != null){
            Me_nameRenterEdit.setText(LoginActivity.loggedAcc.renter.username);
            Me_phoneRenterEdit.setText(LoginActivity.loggedAcc.renter.phoneNumber);
            Me_addressRenterEdit.setText(LoginActivity.loggedAcc.renter.address);
            cardRegister.setVisibility(View.INVISIBLE);
            cardDisplay.setVisibility(View.VISIBLE);
            cardInput.setVisibility(View.INVISIBLE);

        }

    }

    protected Renter registerRenter(){
        mApiService.registerRenter(
                LoginActivity.loggedAcc.id,
                nameRenterEdit.getText().toString(),
                addressRenterEdit.getText().toString(),
                phoneRenterEdit.getText().toString()).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if(response.isSuccessful()){
                    System.out.println("Berhasil Register Renter");
                    LoginActivity.loggedAcc.renter = response.body();
                    Intent move = new Intent(MeActivity.this,MeActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                Toast.makeText(mContext, "Fail to Register Renter", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(LoginActivity.loggedAcc.renter != null){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.top_menu, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_button) {
            Intent move = new Intent(MeActivity.this, CreateRoom.class);
            startActivity(move);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



