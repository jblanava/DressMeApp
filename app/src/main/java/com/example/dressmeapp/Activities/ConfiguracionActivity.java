package com.example.dressmeapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dressmeapp.R;

public class ConfiguracionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        getSupportActionBar().hide();
        enlazaControles();
    }

    private void enlazaControles() {
        Button botonCambioPerfil = findViewById(R.id.botonCambioPerfil);
        Button botonCambioTallas = findViewById(R.id.botonCambioTallas);
        Button botonTipoColor = findViewById(R.id.botonTipoColor);
        Button botonCombinaciones = findViewById(R.id.boton_nuevas_combinaciones);

        botonCambioPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiaPerfil();
            }
        });
        botonTipoColor.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiaColor();
            }
        }));
        botonCambioTallas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambioTallas();
            }
        });
        botonCombinaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiaCombinaciones();
            }
        });
    }


    private void cambioTallas() {
    Intent cambio = new Intent(this,CambioTallasActivity.class);
    startActivity(cambio);
    }

    private void cambiaColor() {
        Intent cambio = new Intent(this,CambioColorActivity.class);
        startActivity(cambio);
    }

    private void cambiaCombinaciones() {
        Intent cambio = new Intent(this,AniadirCombinacion.class);
        startActivity(cambio);
    }

    private void cambiaPerfil() {
        Intent cambio= new Intent(this,PerfilConfiguracionActivity.class);
        startActivity(cambio);
    }
}
