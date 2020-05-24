package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.*;
import com.example.dressmeapp.R;

import java.util.List;
import java.util.Random;

public class AlgoritmoRecomendador extends AppCompatActivity {

    String[] formalidades = {"Formal", "Semi-formal", "Casual", "Deportivo", "Baño"};
    String[] temperaturas = {"Frio", "Normal", "Calor"};

    EditText eNombre;
    Spinner sFormalidad;
    Spinner sTemperatura;
    Button bRecomiendame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritmo_recomendador);

        enlazar_controles();
    }

    void enlazar_controles()
    {
        eNombre = (EditText) findViewById(R.id.editText_nombre_evento);
        sFormalidad = (Spinner) findViewById(R.id.spinner_formalidad_evento);
        sTemperatura = (Spinner) findViewById(R.id.spinner_temperatura_evento);
        bRecomiendame = (Button) findViewById(R.id.boton_recomiendame);

        ArrayAdapter<String> adapterFormalidades = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, formalidades);
        adapterFormalidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sFormalidad.setAdapter(adapterFormalidades);

        ArrayAdapter<String> adapterTemperaturas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, temperaturas);
        adapterTemperaturas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTemperatura.setAdapter(adapterTemperaturas);


        bRecomiendame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir_a_resultado_activity();
            }
        });

    }

    void ir_a_resultado_activity()
    {/*
        Intent resultado_recomendador = new Intent(this, ResultadoAlgoritmoActivity.class);
        startActivity(resultado_recomendador);*/
    }


}


