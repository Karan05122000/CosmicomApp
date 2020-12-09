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
import retrofit2.http.Path;

public interface ShipperRequest {
    @GET("shipper/{status}")
    Call<List<ShipperResponse>> getOrdered(@Header("Authorization") String authorization, @Path("status") String status);

}
