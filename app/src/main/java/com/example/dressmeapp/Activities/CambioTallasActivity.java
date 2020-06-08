package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.ColorAdapter;
import com.example.dressmeapp.Objetos.RecyclerViewOnItemClickListener;
import com.example.dressmeapp.Objetos.TallaAdapter;
import com.example.dressmeapp.R;

import java.util.List;

public class CambioTallasActivity extends AppCompatActivity {
    Button bAddTalla;
    RecyclerView listaTallas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_tallas);
        getSupportActionBar().hide();
        enlazarControles();
    }


    @Override
    protected void onResume() {
        super.onResume();

        listaTallas.removeAllViews();

        cargarTallas();
    }

    private void enlazarControles() {
        listaTallas = findViewById(R.id.lista_tallas);

        bAddTalla=findViewById(R.id.bAddTalla);
        bAddTalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aniadirTalla();
            }
        });
    }

    void cargarTallas() {
        final List<String> tallas = GestorBD.get_nombres_tabla(this, "talla");

        listaTallas.setAdapter(new TallaAdapter(tallas, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                // no pasa nada al tocar la talla
            }
        }));

        listaTallas.setLayoutManager(new LinearLayoutManager(this));
    }

    private void aniadirTalla(){
        Intent nuevaTalla = new Intent(this,AniadirTallaActivity.class);
        startActivity(nuevaTalla);
    }
}
