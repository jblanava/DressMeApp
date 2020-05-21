package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dressmeapp.BaseDatos.BaseDatos;
import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.R;

import java.util.List;

public class AniadirPrendaActivity extends AppCompatActivity {

    Spinner Stipo;
    Spinner Stalla;
    EditText Enombre;
    EditText Ecolor;

    Button Bcrear;
    Button Bcancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anydir);

        enlazar_controles();
    }


    void enlazar_controles() // Enlaza los controles y rellena los Spinner con la lista de opciones
    {
        Stipo = (Spinner) findViewById(R.id.rellena_tipo);
        Stalla = (Spinner) findViewById(R.id.rellena_talla);
        Enombre = (EditText) findViewById(R.id.rellena_nombre);
        Ecolor = (EditText) findViewById(R.id.rellena_color);
        Bcrear = (Button) findViewById(R.id.boton_crear);
        Bcancelar = (Button) findViewById(R.id.boton_cancelar);

        List<String> tipos = GestorBD.getTipos(this);
        ArrayAdapter<String> adapterTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Stipo.setAdapter(adapterTipos);

        List<String> tallas = GestorBD.getTallas(this);
        ArrayAdapter<String> adapterTallas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tallas);
        adapterTallas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Stalla.setAdapter(adapterTallas);

        Bcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });



        Bcrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crear();
            }
        });
    }

    void cancelar()
    {
        finish();
    }

    void crear()
    {
        String nombre = Enombre.getText().toString();
        String color = Ecolor.getText().toString();
        int tipo = Stipo.getSelectedItemPosition() + 1;
        int talla = Stalla.getSelectedItemPosition() + 1;

        GestorBD.crearPrenda(this, nombre, color, tipo, talla, 1, GestorBD.getIdPerfil());

        finish();
    }

}
