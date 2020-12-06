package com.example.cosmicomapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Shipper extends Fragment {


    ShipperRequest shipperRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shipper_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        shipperRequest = retrofit.create(ShipperRequest.class);
        String token = getActivity().getSharedPreferences("authorization", Context.MODE_PRIVATE).getString("token", "");
        Log.d("abc",token);
        token="Bearer "+token;
        Call<List<ShipperResponse>>listCall=shipperRequest.getOrdered(token);
        listCall.enqueue(new Callback<List<ShipperResponse>>() {
            @Override
            public void onResponse(Call<List<ShipperResponse>> call, Response<List<ShipperResponse>> response) {
                Log.d("abc", String.valueOf(response.body()));
                Log.d("abc", String.valueOf(response.code()));
                if(response.isSuccessful()){
                    Log.d("abcd", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<ShipperResponse>> call, Throwable t) {

            }
        });


    }
}
