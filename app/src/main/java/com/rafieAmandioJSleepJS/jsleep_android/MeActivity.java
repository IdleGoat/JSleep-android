package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rafieAmandioJSleepJS.jsleep_android.model.Renter;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeActivity extends AppCompatActivity {
    TextView name,email,balance;
    TextView Me_nameRenterEdit,Me_addressRenterEdit,Me_phoneRenterEdit;
    EditText nameRenterEdit,addressRenterEdit,phoneRenterEdit,me_topUpEdit;

    Button cancelRenterBtn,registerRenterBtn,card1RegisterRenter,AddRoomBtn,TopUpBtn,SeeOrderBtn;
    ConstraintLayout norenter,yesrenter,me_norenter,me_renterregister,me_renterdisplay;
    BaseApiService mApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        TopUpBtn = findViewById(R.id.me_topupbutton);
        //find name,email,balance TextView
        name = findViewById(R.id.me_name);
        email = findViewById(R.id.me_email);
        balance = findViewById(R.id.me_balance);

        //Set name,email,balance TextView
        name.setText(LoginActivity.loggedAcc.name);
        email.setText(LoginActivity.loggedAcc.email);
        String balanceText = "Rp. " + String.valueOf(LoginActivity.loggedAcc.balance);
        balance.setText( balanceText );

        norenter = findViewById(R.id.me_norenterlayout);
        yesrenter = findViewById(R.id.me_yesrenter);

        nameRenterEdit = findViewById(R.id.me_renter_register_editname);
        addressRenterEdit = findViewById(R.id.me_renter_register_editaddress);
        phoneRenterEdit = findViewById(R.id.me_renter_register_editphone);


        cancelRenterBtn = findViewById(R.id.me_renter_register_cancelbutton);
        registerRenterBtn = findViewById(R.id.me_renter_register_registerbutton);

        card1RegisterRenter = findViewById(R.id.me_renter_norenter_button);
        me_norenter = findViewById(R.id.me_renter_norenter);
        me_renterdisplay = findViewById(R.id.me_renter_display);
        me_renterregister = findViewById(R.id.me_renter_register);

        Me_nameRenterEdit = findViewById(R.id.me_renter_display_name);
        Me_addressRenterEdit = findViewById(R.id.me_renter_display_address);
        Me_phoneRenterEdit = findViewById(R.id.me_renter_display_phone);

        AddRoomBtn = findViewById(R.id.me_renter_display_addbutton);
        SeeOrderBtn = findViewById(R.id.me_renter_display_seeorderbutton);
        me_topUpEdit = findViewById(R.id.me_topupedit);

        TopUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopUp(LoginActivity.loggedAcc.id,Double.parseDouble(me_topUpEdit.getText().toString()));
            }
        });

        if(LoginActivity.loggedAcc.renter == null){
            norenter.setVisibility(View.VISIBLE);
            yesrenter.setVisibility(View.INVISIBLE);
            me_norenter.setVisibility(View.VISIBLE);
            me_renterdisplay.setVisibility(View.INVISIBLE);
            me_renterregister.setVisibility(View.INVISIBLE);
            card1RegisterRenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    me_norenter.setVisibility(View.INVISIBLE);
                    me_renterdisplay.setVisibility(View.INVISIBLE);
                    me_renterregister.setVisibility(View.VISIBLE);
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
                    me_norenter.setVisibility(View.VISIBLE);
                    me_renterdisplay.setVisibility(View.INVISIBLE);
                    me_renterregister.setVisibility(View.INVISIBLE);
                }
            });
        }

        if(LoginActivity.loggedAcc.renter != null){
            norenter.setVisibility(View.INVISIBLE);
            yesrenter.setVisibility(View.VISIBLE);
            Me_nameRenterEdit.setText(LoginActivity.loggedAcc.renter.username);
            Me_phoneRenterEdit.setText(LoginActivity.loggedAcc.renter.phoneNumber);
            Me_addressRenterEdit.setText(LoginActivity.loggedAcc.renter.address);
            me_norenter.setVisibility(View.INVISIBLE);
            me_renterdisplay.setVisibility(View.VISIBLE);
            me_renterregister.setVisibility(View.INVISIBLE);

            AddRoomBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MeActivity.this, CreateRoomActivity.class);
                    startActivity(intent);
                }
            });

            SeeOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MeActivity.this, RenterSeeOrderActivity.class);
                    startActivity(intent);
                }
            });

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
                    recreate();
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
            Intent move = new Intent(MeActivity.this, CreateRoomActivity.class);
            startActivity(move);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected Renter TopUp(int id, double balance){
        mApiService.topUp(id,balance).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Top Up Success", Toast.LENGTH_SHORT).show();
                    LoginActivity.loggedAcc.balance = LoginActivity.loggedAcc.balance + balance;
                    recreate();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Top Up Fail", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}



