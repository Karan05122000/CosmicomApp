package com.example.cosmicomapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonParser;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

class User {
    private String email, password, usertype;

    public User(String email, String password, String usertype) {
        this.email = email;
        this.password = password;
        this.usertype = usertype;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsertype() {
        return usertype;
    }
}

class LoginResponse {
    String token;

    public LoginResponse (String token) { this.token = token; }

    public String getToken() {
        return token;
    }
}

interface LoginRequest {
//    @POST("login")
//    Call<LoginResponse> createPost(@Body User user);
//    @FormUrlEncoded
//    @POST("login")
//    Call<LoginResponse> createPost(
//            @Field("email") String email,
//            @Field("usertype") String usertype,
//            @Field("password") String password
//    );
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> createPost(@FieldMap Map<String, String> fields);
}

public class Login extends Fragment {

    String usertype;
    TextInputEditText mEmail, mPassword;
    ProgressBar progressBar;
    LoginRequest loginRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        usertype = "User";
        String token = getActivity().getSharedPreferences("authorization", Context.MODE_PRIVATE).getString("token", "");
        if (token != null && !token.isEmpty()) {
            token = token.split("\\.")[1];
            String usertype = new JsonParser()
                    .parse(new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8))
                    .getAsJsonObject().get("usertype").getAsString();

            final NavController navController = Navigation.findNavController(this.getActivity(), R.id.my_nav_host_fragment);

            if (usertype.toLowerCase().contains("user")) {
                navController.navigate(R.id.login_to_user_landing);
            }
            else if (usertype.toLowerCase().contains("merchant")) {
                navController.navigate(R.id.action_loginFragment_to_merchant);
            }
            else if (usertype.toLowerCase().contains("shipper")) {
                navController.navigate(R.id.action_loginFragment_to_shipper);
            }
            else {
                navController.navigate(R.id.action_loginFragment_to_employee);
            }
        }
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmail = view.findViewById(R.id.email_et);
        mPassword = view.findViewById(R.id.password_et);
        progressBar = view.findViewById(R.id.progressBar);

        Spinner spinner = (Spinner) view.findViewById(R.id.user_type);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                usertype = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                usertype = "User";
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.user_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final NavController navController = Navigation.findNavController(this.getActivity(), R.id.my_nav_host_fragment);
        Button registerButton = view.findViewById(R.id.register_redirect);

        registerButton.setOnClickListener((view1) -> {
            navController.navigate(R.id.login_to_register);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginRequest = retrofit.create(LoginRequest.class);
        Button loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(buttonClickView -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is required");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            User user = new User(email, password, usertype);
            Map<String, String> fields = new HashMap<>();
            fields.put("email", email);
            fields.put("password", password);
            fields.put("usertype", usertype);
            Call<LoginResponse> call = loginRequest.createPost(fields);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d("wtf", response.body().getToken());
                        SharedPreferences prefs;
                        SharedPreferences.Editor edit;
                        prefs = getActivity().getSharedPreferences("authorization", Context.MODE_PRIVATE);
                        edit = prefs.edit();
                        edit.putString("token", response.body().getToken());
                        edit.apply();
                        if (usertype.toLowerCase().contains("user")) {
                            navController.navigate(R.id.login_to_user_landing);
                        }
                        else if (usertype.toLowerCase().contains("merchant")) {
                            navController.navigate(R.id.action_loginFragment_to_merchant);
                        }
                        else if (usertype.toLowerCase().contains("shipper")) {
                            navController.navigate(R.id.action_loginFragment_to_shipper);
                        }
                        else {
                            navController.navigate(R.id.action_loginFragment_to_employee);
                        }
                    }
                    else {
                        Log.d("wtf", response.errorBody().toString());
                        mPassword.setError("Invalid credentials");
                        Snackbar snackbar = Snackbar.make(buttonClickView, "Invalid credentials", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("wtf", t.getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        });
    }
}
