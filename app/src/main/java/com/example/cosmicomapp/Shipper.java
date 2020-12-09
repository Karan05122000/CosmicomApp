package com.example.cosmicomapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        ProgressBar progressBar=view.findViewById(R.id.progress_action);
        TextView textView=view.findViewById(R.id.noItems);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        BottomNavigationView bottomNavigationView=view.findViewById(R.id.bottom_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shipperRequest = retrofit.create(ShipperRequest.class);
        String token = getActivity().getSharedPreferences("authorization", Context.MODE_PRIVATE).getString("token", "");
        Log.d("abc",token);
        token="Bearer "+token;
        Call<List<ShipperResponse>>listCall=shipperRequest.getOrdered(token,"ordered");
        listCall.enqueue(new Callback<List<ShipperResponse>>() {
            @Override
            public void onResponse(Call<List<ShipperResponse>> call, Response<List<ShipperResponse>> response) {
                Log.d("abc", String.valueOf(response.code()));
                if(response.isSuccessful()){
                    Log.d("abcd", "success");
                    Log.d("abcd", String.valueOf(response.code()));
                    Log.d("abcd", String.valueOf(response.body().size()));
                    if(response.body().size()==0){
                        progressBar.setVisibility(View.INVISIBLE);
                        textView.setVisibility(View.VISIBLE);
                    }else{
                        textView.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new ShipperRecyclerAdapter(response.body(),true,"packed"));}
                }
            }

            @Override
            public void onFailure(Call<List<ShipperResponse>> call, Throwable t) {

            }
        });
        String finalToken = token;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.ordered){
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Call<List<ShipperResponse>>listCall=shipperRequest.getOrdered(finalToken,"ordered");
                    listCall.enqueue(new Callback<List<ShipperResponse>>() {
                        @Override
                        public void onResponse(Call<List<ShipperResponse>> call, Response<List<ShipperResponse>> response) {
                            Log.d("abc", String.valueOf(response.code()));
                            if(response.isSuccessful()){
                                Log.d("abcd", "success");
                                Log.d("abcd", String.valueOf(response.code()));
                                Log.d("abcd", String.valueOf(response.body().size()));
                                if(response.body().size()==0){
                                    progressBar.setVisibility(View.INVISIBLE);
                                    textView.setVisibility(View.VISIBLE);
                                }else{
                                textView.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                                recyclerView.setAdapter(new ShipperRecyclerAdapter(response.body(),true,"packed"));}
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ShipperResponse>> call, Throwable t) {

                        }
                    });
                    return true;
                }else if(item.getItemId()==R.id.packed){
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Call<List<ShipperResponse>>listCall=shipperRequest.getOrdered(finalToken,"packed");
                    listCall.enqueue(new Callback<List<ShipperResponse>>() {
                        @Override
                        public void onResponse(Call<List<ShipperResponse>> call, Response<List<ShipperResponse>> response) {
                            Log.d("abc", String.valueOf(response.code()));
                            if(response.isSuccessful()){
                                Log.d("abcd", "success");
                                Log.d("abcd", String.valueOf(response.code()));
                                Log.d("abcd", String.valueOf(response.body().size()));
                                if(response.body().size()==0){
                                    progressBar.setVisibility(View.INVISIBLE);
                                    textView.setVisibility(View.VISIBLE);
                                }else{
                                    textView.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(new ShipperRecyclerAdapter(response.body(),true,"dispatched"));}
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ShipperResponse>> call, Throwable t) {

                        }
                    });
                    return true;
                }else if(item.getItemId()==R.id.delivered){
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Call<List<ShipperResponse>>listCall=shipperRequest.getOrdered(finalToken,"delivered");
                    listCall.enqueue(new Callback<List<ShipperResponse>>() {
                        @Override
                        public void onResponse(Call<List<ShipperResponse>> call, Response<List<ShipperResponse>> response) {
                            Log.d("abc", String.valueOf(response.code()));
                            if(response.isSuccessful()){
                                Log.d("abcd", "success");
                                Log.d("abcd", String.valueOf(response.code()));
                                Log.d("abcd", String.valueOf(response.body().size()));
                                if(response.body().size()==0){
                                    progressBar.setVisibility(View.INVISIBLE);
                                    textView.setVisibility(View.VISIBLE);
                                }else{
                                    textView.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(new ShipperRecyclerAdapter(response.body(),false,"abc"));}
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ShipperResponse>> call, Throwable t) {

                        }
                    });
                    return true;
                }else if(item.getItemId()==R.id.dispatched){
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Call<List<ShipperResponse>>listCall=shipperRequest.getOrdered(finalToken,"dispatched");
                    listCall.enqueue(new Callback<List<ShipperResponse>>() {
                        @Override
                        public void onResponse(Call<List<ShipperResponse>> call, Response<List<ShipperResponse>> response) {
                            Log.d("abc", String.valueOf(response.code()));
                            if(response.isSuccessful()){
                                Log.d("abcd", "success");
                                Log.d("abcd", String.valueOf(response.code()));
                                Log.d("abcd", String.valueOf(response.body().size()));
                                if(response.body().size()==0){
                                    progressBar.setVisibility(View.INVISIBLE);
                                    textView.setVisibility(View.VISIBLE);
                                }else{
                                    textView.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(new ShipperRecyclerAdapter(response.body(),true,"delivered"));}
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ShipperResponse>> call, Throwable t) {

                        }
                    });
                    return true;
                }
                return true;
            }
        });
    }
}
