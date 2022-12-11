package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rafieAmandioJSleepJS.jsleep_android.model.Invoice;
import com.rafieAmandioJSleepJS.jsleep_android.model.Payment;
import com.rafieAmandioJSleepJS.jsleep_android.model.Room;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Order Detail
 * @author Rafie Amandio
 */
public class DetailOrderActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    TextView buyerId, from,to,status;
    Payment payment;
    Button accept,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        payment = RenterSeeOrderActivity.orderListData.get(RenterSeeOrderActivity.orderIndex);
        buyerId = findViewById(R.id.orderdetail_filltextviewidbuyer);
        from = findViewById(R.id.orderdetail_filltextviewfromdate);
        to = findViewById(R.id.orderdetail_filltextviewtodate);
        status = findViewById(R.id.orderdetail_filltextviewstatus);
        accept = findViewById(R.id.orderdetail_buttonaccept);
        cancel = findViewById(R.id.orderdetail_buttoncancel);

        buyerId.setText(String.valueOf(payment.buyerId));
        from.setText(payment.from.toString());
        to.setText(payment.to.toString());
        status.setText(payment.status.toString());

        if(payment.status.equals(Invoice.PaymentStatus.WAITING)){
            accept.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
        }
        else{
            accept.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptOrder(payment.id);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder(payment.id);
            }
        });
    }

    protected Boolean acceptOrder(int id) {
        //System.out.println(pageSize);
        mApiService.accept(id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if(response.body()){
                        Toast.makeText(mContext, "Accept Order Success", Toast.LENGTH_SHORT).show();
                        Intent move = new Intent(DetailOrderActivity.this,RenterSeeOrderActivity.class);
                        startActivity(move);
                        }else {
                        Toast.makeText(mContext, "Accept Order Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "Accept Order Failed", Toast.LENGTH_SHORT).show();
            }

        });
        return null;
    }

    protected Boolean cancelOrder(int id) {
        //System.out.println(pageSize);
        mApiService.cancel(id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if(response.body()){
                        Toast.makeText(mContext, "Cancel Order Success", Toast.LENGTH_SHORT).show();
                        Intent move = new Intent(DetailOrderActivity.this,RenterSeeOrderActivity.class);
                        startActivity(move);
                    }else {
                        Toast.makeText(mContext, "Cancel Order Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "Cancel Order Failed", Toast.LENGTH_SHORT).show();
            }

        });
        return null;
    }

//    protected Room getRoom(int roomId){
//        System.out.println("Callback");
//
//        mApiService.getRoom(roomId).enqueue(new Callback<Room>() {
//
//            @Override
//            public void onResponse(@NonNull Call<Room> call, @NonNull Response<Room> response) {
//                if(response.isSuccessful()){
//                    System.out.println("Success");
//                    temproom = response.body();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Room> call, @NonNull Throwable t) {
//                Toast.makeText(mContext, "Create Payment Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//        return null;
//    }

}