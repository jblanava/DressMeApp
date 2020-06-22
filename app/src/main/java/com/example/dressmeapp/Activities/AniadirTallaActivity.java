package com.example.dressmeapp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dressmeapp.BaseDatos.GestorBD;
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

        int nueva_posicion = GestorBD.get_id_tabla(this, "talla", talla);

        if (nueva_posicion == -1)   // si no existe lo creamos
        {
            GestorBDPrendas.crear_talla(this, talla);
        }
        else
        {
            Toast.makeText(AniadirTallaActivity.this, "Ya existe esa talla", Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
