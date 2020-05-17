package com.example.dressmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PerfilConfiguracionActivity extends AppCompatActivity {
    private Button cambioDatos;
    private Button cambioContrasenia;
    private Button borrarPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_configuracion);
        enlazarControles();
    }

    private void enlazarControles() {
        cambioDatos = findViewById(R.id.botonCambiaDatos);
        cambioContrasenia = findViewById(R.id.botonCambiaContrasenia);
        borrarPerfil = findViewById(R.id.botonBorrarPerfil);
        cambioContrasenia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irCambiarContrasenia();
            }
        });

        cambioDatos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irCambiarDatos();
            }
        });

        borrarPerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irBorrarPerfil();
            }
        });
    }

    protected void irBorrarPerfil() {
        Intent borrado = new Intent(this, MainActivity.class);
        startActivity(borrado);
    }

    protected void irCambiarDatos() {

    }

    protected void irCambiarContrasenia() {
        Intent cambioContra = new Intent(this, ConfiguracionContraseniaActivity.class);
        startActivity(cambioContra);
    }
}
