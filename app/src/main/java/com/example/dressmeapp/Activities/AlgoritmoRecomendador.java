package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.dressmeapp.R;

public class AlgoritmoRecomendador extends AppCompatActivity {

    String[] formalidades = {"Formal", "Semi-formal", "Casual", "Deportivo", "Baño"};
    String[] temperaturas = {"Frio", "Normal", "Calor"};

    static EditText eNombre;
    Spinner sFormalidad;
    Spinner sTemperatura;
    Button bRecomiendame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritmo_recomendador);
        getSupportActionBar().hide(); //quita la barra superior, bastaria con descomentar esto para
        //que no la quite

        enlazar_controles();
    }

    void enlazar_controles()
    {
        eNombre = findViewById(R.id.editText_nombre_evento);
        sFormalidad = findViewById(R.id.spinner_formalidad_evento);
        sTemperatura = findViewById(R.id.spinner_temperatura_evento);
        bRecomiendame = findViewById(R.id.boton_recomiendame);

        ArrayAdapter<String> adapterFormalidades = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, formalidades);
        adapterFormalidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sFormalidad.setAdapter(adapterFormalidades);

        ArrayAdapter<String> adapterTemperaturas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, temperaturas);
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
    {
        int[] temperaturas = {1, 2, 4};
        int[] formalidades = {1, 2, 4, 8, 16};

        int form = formalidades[sFormalidad.getSelectedItemPosition()];
        int temp = temperaturas[sTemperatura.getSelectedItemPosition()];

        Intent resultado_recomendador = new Intent(this, ResultadoAlgortimoActivity.class);
        resultado_recomendador.putExtra("formalidad", form);
        resultado_recomendador.putExtra("temperatura", temp);
        startActivity(resultado_recomendador);
    }

    public static String getNombreEvento() {
        return eNombre.getText().toString();
    }
}


