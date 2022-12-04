package com.rafieAmandioJSleepJS.jsleep_android.request;

import com.rafieAmandioJSleepJS.jsleep_android.model.Account;
import com.rafieAmandioJSleepJS.jsleep_android.model.City;
import com.rafieAmandioJSleepJS.jsleep_android.model.Facility;
import com.rafieAmandioJSleepJS.jsleep_android.model.Renter;
import com.rafieAmandioJSleepJS.jsleep_android.model.Room;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;



public interface BaseApiService {



    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("account/login")
    Call<Account> login (@Query("email") String email,@Query("password") String password);

    @POST("account/register")
    Call<Account> register(@Query("email") String email,@Query("password") String password,@Query("name") String name);

    @GET("room/{id}")
    Call<Room> getRoom (@Path("id") int id);

    @POST("account/{id}/topUp")
    Call<Boolean> topUp (@Path("id") int id, @Query("balance") double balance);

    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom(@Query("page") int page,@Query("pageSize") int pageSize);

    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id,
                                @Query("username") String username,
                                @Query("address") String address,
                                @Query("phoneNumber") String phoneNumber);

    @POST("room/create")
    Call<Room> createRoom(
            @Query("accountId") int accountId,
            @Query("name") String name,
            @Query("size") int size,
            @Query("price") int price,
            @Query("facility") ArrayList<Facility> facility,
            @Query("city") City city,
            @Query("address") String address
    );

}
