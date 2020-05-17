package com.example.dressmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PerfilConfiguracionActivity extends AppCompatActivity {
    private Button cambioDatos;
    private Button cambioContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_configuracion);
        enlazarControles();
    }

    private void enlazarControles() {
        cambioDatos = findViewById(R.id.botonCambiaDatos);
        cambioContrasenia = findViewById(R.id.botonCambiaContrasenia);
        cambioContrasenia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irCambiarContrasenia();
            }
        });
    }

    protected void irCambiarContrasenia() {
        Intent cambioContra = new Intent(this, ConfiguracionContraseniaActivity.class);
        startActivity(cambioContra);
    }
}
