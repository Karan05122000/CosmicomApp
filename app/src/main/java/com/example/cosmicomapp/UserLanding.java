package com.example.cosmicomapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

class UserRecyclerModel {
    private int imageView;
    private String title;
    private String subTitle;
    private Double random;

    public UserRecyclerModel(int imageView, String title, String subTitle) {
        this.imageView = imageView;
        this.title = title;
        this.subTitle = subTitle;
        random = Math.random();
    }

    public int getImageView() { return imageView; }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public Double getRandom() {
        return random;
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<UserRecyclerModel> userRecyclerModelList;
    private Context mContext;

    public MyAdapter(List<UserRecyclerModel> modelList, Context context) {
        userRecyclerModelList = modelList;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_landing_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(userRecyclerModelList.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return userRecyclerModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView cardImageView;
        public TextView titleTextView;
        public TextView subTitleTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.user_card_image);
            titleTextView = itemView.findViewById(R.id.user_card_title);
            subTitleTextView = itemView.findViewById(R.id.user_card_subtitle);
        }

        public void bindData(UserRecyclerModel userRecyclerModel, Context context) {
            String imgURL = String.format("https://picsum.photos/seed/%s/500", userRecyclerModel.getRandom());
            Glide.with(context)
                    .load(imgURL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cardImageView);
            titleTextView.setText(userRecyclerModel.getTitle());
            subTitleTextView.setText(userRecyclerModel.getSubTitle());
        }
    }
}

class UserProducts {
    private String id, name, description;
    private int quantity, price;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}

interface UserProductRequest {
    @GET("users/products/{id}")
    Call<List<UserProducts>> getUserProducts(@Header ("Authorization") String authorization, @Path("id") int page);
}

public class UserLanding extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View userView = inflater.inflate(R.layout.user_landing_fragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return userView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton imageButton = view.findViewById(R.id.imageFilterButton);
        imageButton.setOnClickListener(imgBtnView -> {
            DrawerLayout drawer = view.findViewById(R.id.user_drawer);
            drawer.openDrawer(GravityCompat.START);
        });

        mRecyclerView = view.findViewById(R.id.user_recycler_view);

        List<UserRecyclerModel> userRecyclerModelList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserProductRequest userProductRequest = retrofit.create(UserProductRequest.class);
        String token = getActivity().getSharedPreferences("authorization", Context.MODE_PRIVATE).getString("token", "");
        Call<List<UserProducts>> callableUPR = userProductRequest.getUserProducts(
                "Bearer " + token,
                1
        );

        callableUPR.enqueue(new Callback<List<UserProducts>>() {
            @Override
            public void onResponse(Call<List<UserProducts>> call, Response<List<UserProducts>> response) {

                if (!response.isSuccessful()) {
                    return;
                }
                List<UserProducts> userProducts = response.body();

                for (UserProducts userProduct: userProducts) {
                    Log.d("wtf", userProduct.getName() + " " + userProduct.getDescription());
                    userRecyclerModelList.add(new UserRecyclerModel(
                            R.id.user_card_image,
                            userProduct.getName(),
                            userProduct.getDescription()
                    ));
                }

                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new MyAdapter(userRecyclerModelList, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<UserProducts>> call, Throwable t) {
                Log.d("wtf", "Request failed!");
            }
        });

    }

}