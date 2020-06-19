package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dressmeapp.R;

public class RecomendadorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendador);
        getSupportActionBar().hide();
        enlazaControles();
    }
    private void enlazaControles(){
        Button bRecomendador = findViewById(R.id.bRecomendador);
        Button bHistorial = findViewById(R.id.bHistorial);
        Button bFavoritos = findViewById(R.id.bConjFavs);

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
        bFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saltoFavoritos();
            }
        });
    }

    private void saltoFavoritos() {
        Intent saltoFav = new Intent(this, ConjuntosFavoritosActivity.class);
        startActivity(saltoFav);
    }

    private void saltoAlgoritmo(){
        Intent salto = new Intent(this, AlgoritmoRecomendadorActivity.class);
        startActivity(salto);
    }
    private  void saltoHistorial(){
        Intent saltoH = new Intent(this,HistorialActivity.class);
        startActivity(saltoH);
    }
}
