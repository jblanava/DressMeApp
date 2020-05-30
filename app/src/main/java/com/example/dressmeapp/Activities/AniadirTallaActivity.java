package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.R;

public class AniadirTallaActivity extends AppCompatActivity {
    EditText tallaNueva;
    Button bGuardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_talla);
        getSupportActionBar().hide();
        enlazarControles();
    }

    private void enlazarControles() {

        tallaNueva = findViewById(R.id.recogeTalla);

        bGuardar=findViewById(R.id.guardarTalla);
        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTalla();
            }
        });
    }

    private void guardarTalla() {
        String talla = tallaNueva.getText().toString();
        GestorBD.CrearTalla(this, talla);
        finish();
    }
}
