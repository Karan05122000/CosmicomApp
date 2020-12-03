package com.example.cosmicomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();

        if (fm.findFragmentById(R.id.userLanding).isVisible() ||
                fm.findFragmentById(R.id.merchant).isVisible() ||
                fm.findFragmentById(R.id.shipper).isVisible() ||
                fm.findFragmentById(R.id.employee).isVisible()
        ) {
            Toast.makeText(getApplicationContext(), "Log out to go back", Toast.LENGTH_LONG).show();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }
}