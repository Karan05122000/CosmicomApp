package com.example.cosmicomapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShipperRecyclerAdapter extends RecyclerView.Adapter<ShipperRecyclerAdapter.RecyclerViewHolder> {

    List<ShipperResponse> list;

    public ShipperRecyclerAdapter(List<ShipperResponse> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_item_view,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.shipperName.setText(list.get(position).getName());
        holder.paymentMode.setText("Payment Mode :- " +list.get(position).getPaymentMode());
        holder.amount.setText("Amount :- "+list.get(position).getNetAmt());
        Log.d("idls",list.get(position).getId());
        holder.tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
            shipperName=itemView.findViewById(R.id.shipperName);
            amount=itemView.findViewById(R.id.amount);
            paymentMode=itemView.findViewById(R.id.payment_mode);
            tick=itemView.findViewById(R.id.tick_button);
            delete=itemView.findViewById(R.id.delete_button);
        }

    }
}
