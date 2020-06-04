package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dressmeapp.R;

public class MenuPrincipalActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        getSupportActionBar().hide();
        Button bVestuario = findViewById(R.id.boton_vestuario);
        Button bRecomendador = findViewById(R.id.boton_recomendador);
        Button bConfiguracion = findViewById(R.id.boton_configuracion);

        bVestuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir_a_vestuario();
            }
        });

        bRecomendador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir_a_recomendador();
            }
        });

        bConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir_a_configuracion();
            }
        });

    }

    void ir_a_vestuario()
    {
        Intent vestuario = new Intent(this, VestuarioActivity.class);
        startActivity(vestuario);
    }

    void ir_a_recomendador()
    {
        Intent recomendador = new Intent(this, RecomendadorActivity.class);
        startActivity(recomendador);
    }

    void ir_a_configuracion()
    {
        Intent configuracion = new Intent(this, ConfiguracionActivity.class);
        startActivity(configuracion);
    }
}
