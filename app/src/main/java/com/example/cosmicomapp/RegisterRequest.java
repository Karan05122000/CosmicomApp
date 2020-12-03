package com.example.cosmicomapp;

import android.widget.SimpleAdapter;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterRequest {

    @FormUrlEncoded
    @POST("register")
    Call<SimpleAdapter> createPost(@FieldMap Map<String, String> fields);
}
