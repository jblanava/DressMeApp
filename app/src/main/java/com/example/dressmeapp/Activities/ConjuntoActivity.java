package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.dressmeapp.R;

// TODO necesitamos esta clase o se puede eliminar?

public class ConjuntoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conjunto_view);
        getSupportActionBar().hide();
    }
}
