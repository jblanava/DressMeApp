package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.dressmeapp.R;

// TODO necesitamos esta clase o se puede eliminar?

@SuppressLint("Registered")
public class PrendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenda_view);
        getSupportActionBar().hide();
    }
}
