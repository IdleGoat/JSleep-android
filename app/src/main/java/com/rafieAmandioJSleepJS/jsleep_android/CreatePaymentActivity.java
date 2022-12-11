package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.annotation.NonNull;
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
import com.rafieAmandioJSleepJS.jsleep_android.model.Payment;
import com.rafieAmandioJSleepJS.jsleep_android.model.Room;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class CreatePaymentActivity extends AppCompatActivity{
    BaseApiService mApiService;
    Payment payment;
    Context mContext;
    Button createbutton;
    TextView createpayment_from,createpayment_to,createpayment_title_name,createpayment_title_address,createpayment_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        mApiService = UtilsApi.getApiService();
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_payment);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date enddate = dateFormat.parse(PaymentDetailActivity.enddate);
            Date startdate = dateFormat.parse(PaymentDetailActivity.startdate);
            long diff = enddate.getTime() - startdate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            createpayment_title_name = findViewById(R.id.createpayment_title_name);
            createpayment_title_address = findViewById(R.id.createpayment_title_address);
            createpayment_title_name.setText(DetailRoomActivity.clickedRoom.name);
            createpayment_title_address.setText(DetailRoomActivity.clickedRoom.address);
            createpayment_price = findViewById(R.id.createpayment_price);

            createbutton = findViewById(R.id.createpayment_button);
            createpayment_from = findViewById(R.id.createpayment_from);
            createpayment_to = findViewById(R.id.createpayment_to);
            createpayment_price.setText(String.valueOf(((double)diffDays) * DetailRoomActivity.clickedRoom.price.price));
            createpayment_from.setText(PaymentDetailActivity.startdate);
            createpayment_to.setText(PaymentDetailActivity.enddate);

            createbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Clicked");
                    createPayment(LoginActivity.loggedAcc.id,
                            DetailRoomActivity.clickedRoom.accountId,
                            DetailRoomActivity.clickedRoom.id,
                            PaymentDetailActivity.startdate,
                            PaymentDetailActivity.enddate);
                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }




    }


    protected Payment createPayment(int buyerId,
                                    int renterId,
                                    int roomId,
                                    String from,
                                    String to){
        System.out.println("Callback");
        //print all parameter
        System.out.println(buyerId);
        System.out.println(renterId);
        System.out.println(roomId);
        System.out.println(from);
        System.out.println(to);
        mApiService.createPayment(buyerId, renterId, roomId, from, to).enqueue(new Callback<Payment>() {

            @Override
            public void onResponse(@NonNull Call<Payment> call, @NonNull Response<Payment> response) {
                if(response.isSuccessful()){
                    System.out.println("Success");
                    payment = response.body();
                    System.out.println(payment);
                    Intent move = new Intent(CreatePaymentActivity.this,MainActivity.class);
                    startActivity(move);
                    Toast.makeText(mContext, "Payment created", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Payment> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Create Payment Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }





}