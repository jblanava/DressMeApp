package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dressmeapp.R;

public class RecomendadorActivity extends AppCompatActivity {
    private Button bRecomendador;
    private Button bHistorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_recomendador);
           enlazaControles();
    }
    private void enlazaControles(){
        bRecomendador= findViewById(R.id.bRecomendador);
        bHistorial = findViewById(R.id.bHistorial);

        bRecomendador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saltoAlgoritmo();
            }
        });
        bHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saltoHistorial();
            }
        });
    }
    private void saltoAlgoritmo(){
        Intent salto = new Intent(this,AlgoritmoRecomendador.class);
        startActivity(salto);
    }
    private  void saltoHistorial(){
        Intent salto = new Intent(this,HistorialActivity.class);
    }
}
