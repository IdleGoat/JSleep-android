package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rafieAmandioJSleepJS.jsleep_android.model.Invoice;
import com.rafieAmandioJSleepJS.jsleep_android.model.Payment;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyerOrderActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    TextView buyerId, from,to,status;
    Payment payment;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_order);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        buyerId = findViewById(R.id.buyerorder_filltextviewidbuyer);
        from = findViewById(R.id.buyerorder_filltextviewfromdate);
        to = findViewById(R.id.buyerorder_filltextviewtodate);
        status = findViewById(R.id.buyerorder_filltextviewstatus);
        cancel = findViewById(R.id.buyerorder_buttoncancel);

        payment = BuyerSeeOrderActivity.paymentArrayList.get(BuyerSeeOrderActivity.orderIndex);

        buyerId.setText(String.valueOf(payment.buyerId));
        from.setText(payment.from.toString());
        to.setText(payment.to.toString());
        status.setText(payment.status.toString());


        if(payment.status.equals(Invoice.PaymentStatus.WAITING)){
            cancel.setVisibility(View.VISIBLE);
        }
        else{
            cancel.setVisibility(View.GONE);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder(payment.id);
            }
        });
    }

    protected Boolean cancelOrder(int id) {
        //System.out.println(pageSize);
        mApiService.cancel(id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if(response.body()){
                        Toast.makeText(mContext, "Cancel Order Success", Toast.LENGTH_SHORT).show();
                        Intent move = new Intent(BuyerOrderActivity.this,BuyerSeeOrderActivity.class);
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


}