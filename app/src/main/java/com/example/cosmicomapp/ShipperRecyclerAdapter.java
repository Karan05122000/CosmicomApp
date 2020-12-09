package com.example.cosmicomapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShipperRecyclerAdapter extends RecyclerView.Adapter<ShipperRecyclerAdapter.RecyclerViewHolder> {

    List<ShipperResponse> list;
    boolean showButtons;
    Retrofit retrofit;
    UpdateShipperStatus updateShipperStatus;
    String status;

    public ShipperRecyclerAdapter(List<ShipperResponse> list, boolean showButtons,String status) {
        this.list = list;
        this.showButtons = showButtons;
        this.status=status;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_view, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.shipperName.setText(list.get(position).getName());
        holder.paymentMode.setText("Payment Mode :- " + list.get(position).getPaymentMode());
        holder.amount.setText("Amount :- " + list.get(position).getNetAmt());
        if (showButtons == false) {
            holder.tick.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        } else {
            Log.d("idls", list.get(position).getId());
            holder.tick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(view.getResources().getString(R.string.base_url))
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    updateShipperStatus = retrofit.create(UpdateShipperStatus.class);
                    String token = view.getContext().getSharedPreferences("authorization", Context.MODE_PRIVATE).getString("token", "");
                    Log.d("wtf", token);
                    Log.d("wtf", status);
                    token = "Bearer " + token;
                    Call<SimpleAdapter> simpleAdapterCall = updateShipperStatus.updateStatus(token, status, list.get(position).getId());
                    simpleAdapterCall.enqueue(new Callback<SimpleAdapter>() {
                        @Override
                        public void onResponse(Call<SimpleAdapter> call, Response<SimpleAdapter> response) {
                            if(response.isSuccessful()){
                                Log.d("abc", String.valueOf(response.code()));
                                if(response.code()==200){
                                    Toast.makeText(view.getContext(),"Status updated",Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleAdapter> call, Throwable t) {

                        }
                    });

                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(view.getResources().getString(R.string.base_url))
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    updateShipperStatus = retrofit.create(UpdateShipperStatus.class);
                    String token = view.getContext().getSharedPreferences("authorization", Context.MODE_PRIVATE).getString("token", "");
                    Log.d("abc", token);
                    token = "Bearer " + token;
                    Call<SimpleAdapter> simpleAdapterCall = updateShipperStatus.updateStatus(token, "cancelled", list.get(position).getId());
                    simpleAdapterCall.enqueue(new Callback<SimpleAdapter>() {
                        @Override
                        public void onResponse(Call<SimpleAdapter> call, Response<SimpleAdapter> response) {
                            if(response.isSuccessful()){
                                Log.d("abc", String.valueOf(response.code()));
                                if(response.code()==200){
                                    Toast.makeText(view.getContext(),"Status updated",Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleAdapter> call, Throwable t) {

                        }
                    });
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView shipperName;
        TextView amount;
        TextView paymentMode;
        ImageButton tick;
        ImageButton delete;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            shipperName = itemView.findViewById(R.id.shipperName);
            amount = itemView.findViewById(R.id.amount);
            paymentMode = itemView.findViewById(R.id.payment_mode);
            tick = itemView.findViewById(R.id.tick_button);
            delete = itemView.findViewById(R.id.delete_button);
        }

    }
}
