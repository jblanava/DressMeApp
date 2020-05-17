package com.example.dressmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class VestuarioActivity extends AppCompatActivity {

    Button bOrdenar,bAgrupar,bAnydir, bBuscar;
    LinearLayout listaPrendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vestuario);

        enlazar_controles();
/*
        Prenda[] prendas = GestorBD.getPrendas();

        for(Prenda p : prendas)
        {
            añadir_elemento(p);
        }*/
    }

    void enlazar_controles()
    {
        bOrdenar = (Button) findViewById(R.id.boton_ordenar);
        bAgrupar = (Button) findViewById(R.id.boton_agrupar);
        bAnydir = (Button) findViewById(R.id.boton_añadir);
        bBuscar = (Button) findViewById(R.id.boton_buscar);

        listaPrendas = (LinearLayout) findViewById(R.id.lista_prendas);

        bAgrupar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir_a_agrupar();
            }
        });

        bOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir_a_ordenar();
            }
        });

        bAnydir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir_a_anydir();
            }
        });

        bBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadir_elemento(); // SOLO PARA PROBAR
            }
        });
    }

    void ir_a_ordenar()
    {
        Intent ordenar = new Intent(this, Ordenar.class);
        startActivity(ordenar);
    }

    void ir_a_agrupar()
    {
        Intent agrupar = new Intent(this, RecomendadorActivity.class); //CAMBIAR A AGRUPAR
        startActivity(agrupar);
    }

    void ir_a_anydir()
    {
        Intent anydir = new Intent(this, Anydir.class);
        startActivity(anydir);
    }


    void añadir_elemento()
    {
        listaPrendas.addView(getLayoutInflater().inflate(R.layout.activity_prenda_view, null));
    }
}
