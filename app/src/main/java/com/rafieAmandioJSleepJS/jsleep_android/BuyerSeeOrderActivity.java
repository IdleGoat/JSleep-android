package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.rafieAmandioJSleepJS.jsleep_android.model.Payment;
import com.rafieAmandioJSleepJS.jsleep_android.model.Room;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity to see all buyer order
 * @author Rafie
 *
 */
public class BuyerSeeOrderActivity extends AppCompatActivity {

    ListView buyerSeeOrderListView;
    public static ArrayList<Payment> paymentArrayList;
    public static int orderIndex;
    Context mContext;
    BaseApiService mApiService;
    ImageView home,history,search,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_see_order);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        home = findViewById(R.id.main_homeicon);
        history = findViewById(R.id.main_historyicon);
        search = findViewById(R.id.main_searchicon);
        profile = findViewById(R.id.main_profileicon);
        home.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
        });
        search.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SearchRoomActivity.class);
            startActivity(intent);
        });
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MeActivity.class);
            startActivity(intent);
        });
        buyerSeeOrderListView = findViewById(R.id.buyerseeorder_ListView);
        getOrderForBuyer(LoginActivity.loggedAcc.id);
        buyerSeeOrderListView.setOnItemClickListener(this::onItemClick);
    }

    /**
     * API request for order with buyer Id
     * @param buyerId buyer id that we want to search
     */
    protected void getOrderForBuyer(int buyerId){
        System.out.println("masuk");
        System.out.println(buyerId);
        mApiService.getOrderForBuyer(buyerId).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if(response.isSuccessful()){
                    List<Payment> orderlist = response.body();
                    assert orderlist != null;
                    paymentArrayList = new ArrayList<Payment>(orderlist);
                    Toast.makeText(mContext, "Get Order Success", Toast.LENGTH_SHORT).show();
                    OrderListAdapter adapter = new OrderListAdapter(mContext,paymentArrayList);
                    buyerSeeOrderListView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void onItemClick (AdapterView<?> l, View v, int position, long id){
        System.out.println("onItemClick Success");
        Log.i("ListView", "You clicked Item np : " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent intent = new Intent();
        orderIndex = position;
        System.out.println("clicked");

        intent.setClass(mContext, BuyerOrderActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("id", id);
        startActivity(intent);
    }


}