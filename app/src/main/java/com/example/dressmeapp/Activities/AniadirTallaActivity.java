package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.dressmeapp.BaseDatos.GestorBDPrendas;
import com.example.dressmeapp.R;

public class AniadirTallaActivity extends AppCompatActivity {
    EditText tallaNueva;
    Button bGuardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_talla);
        getSupportActionBar().hide();
        enlazar_controles();
    }

    private void enlazar_controles() {

        tallaNueva = findViewById(R.id.recogeTalla);

        bGuardar=findViewById(R.id.guardarTalla);
        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar_talla();
            }
        });
    }

    private void guardar_talla() {
        String talla = tallaNueva.getText().toString();
        GestorBDPrendas.CrearTalla(this, talla);
        finish();
    }
}
