package com.example.dressmeapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dressmeapp.R;

import androidx.appcompat.app.AppCompatActivity;

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
    }


    private void cambioTallas() {
    Intent cambio = new Intent(this,CambioTallasActivity.class);
    startActivity(cambio);
    }

    private void cambiaColor() {
        Intent cambio = new Intent(this,CambioColorActivity.class);
        startActivity(cambio);
    }

    private void cambiaPerfil() {
        Intent cambio= new Intent(this,PerfilConfiguracionActivity.class);
        startActivity(cambio);
    }
}
