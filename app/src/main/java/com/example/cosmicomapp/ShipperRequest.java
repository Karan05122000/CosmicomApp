package com.example.cosmicomapp;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ShipperRequest {
    @GET("shipper/ordered")
    Call<List<ShipperResponse>> getOrdered(@Header("Authorization") String authorization);

}
