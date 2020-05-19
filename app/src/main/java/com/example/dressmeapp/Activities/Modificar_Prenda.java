package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.R;

import java.util.List;

public class Modificar_Prenda extends AppCompatActivity {

    Spinner Stipo;
    Spinner Stalla;
    EditText Enombre;
    EditText Ecolor;

    Prenda prenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar__prenda);

        enlazar_controles();


    }

    void enlazar_prenda()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            prenda.id = extras.getInt("ID_Prenda");
        }

        //prenda = GestorBD.ObtenerPrenda(this, prenda.id);

        Stipo.setSelection(0);
        Stalla.setSelection(0);
        Enombre.setText(prenda.nombre);
        Ecolor.setText(prenda.color);
    }

    void enlazar_controles()
    {
        Stipo = (Spinner) findViewById(R.id.rellena_tipo);
        Stalla = (Spinner) findViewById(R.id.rellena_talla);
        Enombre = (EditText) findViewById(R.id.rellena_nombre);
        Ecolor = (EditText) findViewById(R.id.rellena_color);
        /*
        List<String> tipos = GestorBD.getTipos();
        ArrayAdapter<String> adapterTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Stipo.setAdapter(adapterTipos);

        List<String> tallas = GestorBD.getTallas();
        ArrayAdapter<String> adapterTallas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos);
        adapterTallas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Stipo.setAdapter(adapterTallas);
        */
    }

}
