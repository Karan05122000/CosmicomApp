package com.example.cosmicom;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserInterface {
    @POST("login")
    Call<User> createPost(@Body User user);
    @FormUrlEncoded
    @POST("login")
    Call<User> createPost(
            @Field("email") String email,
            @Field("user_type") String user_type,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("login")
    Call<User> createPost(@FieldMap Map<String, String> fields);
}
