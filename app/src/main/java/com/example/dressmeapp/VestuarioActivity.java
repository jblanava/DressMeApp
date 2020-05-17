package com.example.dressmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class VestuarioActivity extends AppCompatActivity {

    Button bOrdenar,bAgrupar,bAnydir, bBuscar;
    LinearLayout listaPrendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vestuario);

        enlazar_controles();



        List<Prenda> prendas = GestorBD.Dar_Prendas(this);
/*
        List<Prenda> prendas = new ArrayList<>();

        prendas.add(new Prenda("Nombre", "Color", "Tipo", "Talla"));
        prendas.add(new Prenda("Nombre2", "Color2", "Tipo2", "Talla2"));
        prendas.add(new Prenda("Nombre3", "Color3", "Tipo3", "Talla3"));
        prendas.add(new Prenda("Nombre", "Color", "Tipo", "Talla"));
        prendas.add(new Prenda("Nombre2", "Color2", "Tipo2", "Talla2"));
        prendas.add(new Prenda("Nombre3", "Color3", "Tipo3", "Talla3"));
        prendas.add(new Prenda("Nombre", "Color", "Tipo", "Talla"));
        prendas.add(new Prenda("Nombre2", "Color2", "Tipo2", "Talla2"));
        prendas.add(new Prenda("Nombre3", "Color3", "Tipo3", "Talla3"));
        prendas.add(new Prenda("Nombre", "Color", "Tipo", "Talla"));
        prendas.add(new Prenda("Nombre2", "Color2", "Tipo2", "Talla2"));
        prendas.add(new Prenda("Nombre3", "Color3", "Tipo3", "Talla3"));
        prendas.add(new Prenda("Nombre", "Color", "Tipo", "Talla"));
        prendas.add(new Prenda("Nombre2", "Color2", "Tipo2", "Talla2"));
        prendas.add(new Prenda("Nombre3", "Color3", "Tipo3", "Talla3"));
*/
        for(Prenda p : prendas)
        {
            añadir_elemento(p);
        }
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
                buscar();
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
        Intent agrupar = new Intent(this, Agrupar.class); //CAMBIAR A AGRUPAR
        startActivity(agrupar);
    }

    void ir_a_anydir()
    {
        Intent anydir = new Intent(this, Anydir.class);
        startActivity(anydir);
    }

    void buscar()
    {
        // No se que hay que poner aqui aun
    }


    void añadir_elemento(Prenda prenda)
    {
        View v = getLayoutInflater().inflate(R.layout.activity_prenda_view, null);

        TextView nombre = (TextView) v.findViewById(R.id.prenda_nombre);
        TextView tipo = (TextView) v.findViewById(R.id.prenda_tipo);
        TextView color = (TextView) v.findViewById(R.id.prenda_color);
        TextView talla = (TextView) v.findViewById(R.id.prenda_talla);

        nombre.setText(prenda.nombre);
        tipo.setText(prenda.tipo);
        color.setText(prenda.color);
        talla.setText(prenda.talla);

        listaPrendas.addView(v);
    }
}
