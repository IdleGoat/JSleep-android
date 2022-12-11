package com.rafieAmandioJSleepJS.jsleep_android.request;

import com.rafieAmandioJSleepJS.jsleep_android.model.Account;
import com.rafieAmandioJSleepJS.jsleep_android.model.BedType;
import com.rafieAmandioJSleepJS.jsleep_android.model.City;
import com.rafieAmandioJSleepJS.jsleep_android.model.Facility;
import com.rafieAmandioJSleepJS.jsleep_android.model.Payment;
import com.rafieAmandioJSleepJS.jsleep_android.model.Renter;
import com.rafieAmandioJSleepJS.jsleep_android.model.Room;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * A base API service that contains all the API calls.
 * @author Rafie Amandio
 */
public interface BaseApiService {


    /**
     * A method that calls the API to create a payment.
     * @param buyerId the buyer's id
     * @param renterId the renter's id
     * @param roomId the room's id
     * @param from the start date
     * @param to the end date
     * @return a call to the API
     */
    @POST("payment/create")
    Call<Payment> createPayment(@Query("buyerId") int buyerId,
                                @Query("renterId") int renterId,
                                @Query("roomId") int roomId,
                                @Query("from") String from,
                                @Query("to") String to);

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
            @Query("address") String address,
            @Query("bedType")BedType bedType
            );

    @POST("payment/{id}/accept")
    Call<Boolean> accept(@Path("id") int id);

    @POST("payment/{id}/cancel")
    Call<Boolean> cancel(@Path("id") int id);

    @GET("payment/getOrderForRenter")
    Call<List<Payment>> getOrderForRenter(@Query("renterId") int renterId,@Query("page") int page,@Query("pageSize") int pageSize);

    @GET("payment/getOrderForBuyer")
    Call<List<Payment>> getOrderForBuyer(@Query("buyerId") int buyerId);

    @GET("room/collectByName")
    Call<List<Room>> collectByName(@Query("name") String name);

    @GET("room/collectByPrice")
    Call<List<Room>> collectByPrice(@Query("min") int min,@Query("max") int max);

    @GET("room/filterByCity")
    Call<List<Room>> collectByCity(@Query("city") City city);

}
