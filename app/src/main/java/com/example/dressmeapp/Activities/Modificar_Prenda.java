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

public class Modificar_Prenda extends AppCompatActivity {

    Spinner Stipo;
    Spinner Stalla;
    EditText Enombre;
    EditText Ecolor;

    Button Bguardar;
    Button Beliminar;
    Button Bintercambiar;


    Prenda prenda;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar__prenda);

        enlazar_controles();

     //   enlazar_prenda();
    }

    void enlazar_controles() {// Enlaza los controles y rellena los Spinner con la lista de opciones

        /*
        Stipo = (Spinner) findViewById(R.id.rellena_tipo);
        Stalla = (Spinner) findViewById(R.id.rellena_talla);
        Enombre = (EditText) findViewById(R.id.rellena_nombre);
        Ecolor = (EditText) findViewById(R.id.rellena_color);
        Bguardar = (Button) findViewById(R.id.boton_guardar);
        Beliminar = (Button) findViewById(R.id.boton_eliminar);
        Bintercambiar = (Button) findViewById(R.id.boton_intercambiar);

        List<String> tipos = GestorBD.getTipos(this);
        ArrayAdapter<String> adapterTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Stipo.setAdapter(adapterTipos);

        List<String> tallas = GestorBD.getTallas(this);
        ArrayAdapter<String> adapterTallas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tallas);
        adapterTallas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Stalla.setAdapter(adapterTallas);

        Beliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        Bintercambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intercambiar();
            }
        });

        Bguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
    }

    void eliminar()
    {
        GestorBD.borrarPrenda(this, prenda.id);

        finish();
    }

    void intercambiar()
    {
        // TODO
    }

    void guardar()
    {
        prenda.nombre = Enombre.getText().toString();
        prenda.color = Ecolor.getText().toString();
        prenda.tipo = Stipo.getSelectedItemPosition() + 1;
        prenda.talla = Stalla.getSelectedItemPosition() + 1;

        GestorBD.Modificar_Prenda(this, prenda);

        finish();
    }


    void enlazar_prenda() // Pone los valores de la interfaz para que coincidan por defecto con la prenda a modificar
    {
        Intent mIntent = getIntent();
        int id = mIntent.getIntExtra("intVariableName", 0);

        prenda = GestorBD.Obtener_Prenda(this, id);

        prenda.id = id;

        Stipo.setSelection(prenda.tipo - 1);
        Stalla.setSelection(prenda.talla - 1);
        Enombre.setText(prenda.nombre);
        Ecolor.setText(prenda.color);
        */

    }
}

