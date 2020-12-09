package com.example.cosmicomapp;

import android.widget.SimpleAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface UpdateShipperStatus {
    @PATCH("shipper/orders/{status}/{id}")
    Call<SimpleAdapter> updateStatus(@Header("Authorization") String authorization, @Path("status") String status, @Path("id") String id);

}
