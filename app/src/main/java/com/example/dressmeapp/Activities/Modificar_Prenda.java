package com.example.dressmeapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.R;

import java.util.List;

public class Modificar_Prenda extends AppCompatActivity {

    EditText Enombre;
    Spinner Scolor;
    Spinner Stipo;
    Spinner Stalla;

    Button Bguardar;
    Button Beliminar;
    Button Bintercambiar;


    Prenda prenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar__prenda);

        enlazar_controles();

        enlazar_prenda();
    }

    void enlazar_controles() {// Enlaza los controles y rellena los Spinner con la lista de opciones

        Enombre = findViewById(R.id.rellena_nombre);
        Scolor = findViewById(R.id.spinner_color);
        Stipo = findViewById(R.id.spinner_tipo);
        Stalla = findViewById(R.id.spinner_talla);
        Bguardar = findViewById(R.id.boton_guardar);
        Beliminar = findViewById(R.id.boton_eliminar);
        Bintercambiar = findViewById(R.id.boton_intercambiar);

        List<String> colores = GestorBD.get_nombres_tabla(this, "color");
        ArrayAdapter<String> adapterColores = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colores);
        adapterColores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Scolor.setAdapter(adapterColores);

        List<String> tipos = GestorBD.get_nombres_tabla(this, "tipo");
        ArrayAdapter<String> adapterTipos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipos);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Stipo.setAdapter(adapterTipos);

        List<String> tallas = GestorBD.get_nombres_tabla(this, "talla");
        ArrayAdapter<String> adapterTallas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tallas);
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
    }

    void eliminar() // TODO: Preguntar al usuario si está seguro
    {
        GestorBD.borrarPrenda(this, prenda.id);

        finish();
    }

    void intercambiar() {
        // TODO
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void guardar() {
        prenda.nombre = Enombre.getText().toString();
        prenda.color = GestorBD.get_nombre_tabla(this, "color", Scolor.getSelectedItemPosition() + 1);
        prenda.tipo = GestorBD.get_nombre_tabla(this, "tipo", Stipo.getSelectedItemPosition() + 1);
        prenda.talla = GestorBD.get_nombre_tabla(this, "talla", Stalla.getSelectedItemPosition() + 1);


        GestorBD.Modificar_Prenda(this, prenda);

        finish();
    }


    void enlazar_prenda() // Pone los valores de la interfaz para que coincidan por defecto con la prenda a modificar
    {
        Intent mIntent = getIntent();
        int id = mIntent.getIntExtra("intVariableName", 0);

        prenda = GestorBD.Obtener_Prenda(this, id);

        prenda.id = id;

        Enombre.setText(prenda.nombre);
        Scolor.setSelection(GestorBD.get_id_tabla(this, "color", prenda.color) - 1);
        Stipo.setSelection(GestorBD.get_id_tabla(this, "tipo", prenda.tipo) - 1);
        Stalla.setSelection(GestorBD.get_id_tabla(this, "talla", prenda.talla) - 1);
    }
}

