package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dressmeapp.R;

public class CambioTallasActivity extends AppCompatActivity {
    Button bAddTalla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_tallas);
        getSupportActionBar().hide();
        enlazarControles();
    }

    private void enlazarControles() {
        bAddTalla=findViewById(R.id.bAddTalla);
        bAddTalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aniadirTalla();
            }
        });
    }

    private void aniadirTalla(){
        Intent nuevaTalla = new Intent(this,AniadirTallaActivity.class);
        startActivity(nuevaTalla);
    }
}
