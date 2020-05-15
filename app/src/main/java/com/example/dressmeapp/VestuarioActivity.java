package com.example.dressmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VestuarioActivity extends AppCompatActivity {

    Button bOrdenar,bAgrupar,bAnydir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vestuario);

        bOrdenar = (Button) findViewById(R.id.boton_ordenar);
        bAgrupar = (Button) findViewById(R.id.boton_agrupar);
        bAnydir = (Button) findViewById(R.id.boton_añadir);

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
    }

    void ir_a_ordenar()
    {
        Intent ordenar = new Intent(this, Ordenar.class);
        startActivity(ordenar);
    }

    void ir_a_agrupar()
    {
        Intent agrupar = new Intent(this, RecomendadorActivity.class);
        startActivity(agrupar);
    }

    void ir_a_anydir()
    {
        Intent anydir = new Intent(this, Anydir.class);
        startActivity(anydir);
    }
}
