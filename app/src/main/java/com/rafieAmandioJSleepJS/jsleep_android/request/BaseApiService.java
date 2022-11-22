package com.rafieAmandioJSleepJS.jsleep_android.request;

import com.rafieAmandioJSleepJS.jsleep_android.model.Account;
import com.rafieAmandioJSleepJS.jsleep_android.model.Room;

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

}
