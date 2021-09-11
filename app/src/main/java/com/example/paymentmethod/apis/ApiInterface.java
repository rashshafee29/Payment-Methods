package com.example.paymentmethod.apis;

import com.example.paymentmethod.models.ListResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("listresult.json")
    Call<ListResult>getListData();
}
