package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.dressmeapp.BaseDatos.GestorBDPrendas;
import com.example.dressmeapp.Objetos.ColorAdapter;
import com.example.dressmeapp.Objetos.ColorPrenda;
import com.example.dressmeapp.Objetos.RecyclerViewOnItemClickListener;
import com.example.dressmeapp.R;

import java.util.List;

public class CambioColorActivity extends AppCompatActivity {

    private RecyclerView listaColores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_color);
        enlazar_controles();
        getSupportActionBar().hide();
    }

    private void enlazar_controles() {
        listaColores = findViewById(R.id.listaColores);
        mostrar_colores();

        Button btnNuevoColor = findViewById(R.id.btnNuevoColor);
        btnNuevoColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir_a__nuevo_color();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaColores.removeAllViews();
        mostrar_colores();
    }

    private void mostrar_colores() {

        final Context context = this;
        final List<ColorPrenda> colores = GestorBDPrendas.get_colores(context);

        listaColores.setAdapter(new ColorAdapter(colores, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                // no pasa nada al tocar el color
            }
        }));

        listaColores.setLayoutManager(new LinearLayoutManager(this));

    }

    private void ir_a__nuevo_color() {
        Intent intent = new Intent(this, NuevoColorActivity.class);
        startActivity(intent);
    }

}
