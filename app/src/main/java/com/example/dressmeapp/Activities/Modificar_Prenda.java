package com.example.dressmeapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDFotos;
import com.example.dressmeapp.BaseDatos.GestorBDPrendas;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.R;

import java.util.List;

public class Modificar_Prenda extends AppCompatActivity {

    EditText eNombre;
    Spinner sColor;
    Spinner sTipo;
    Spinner sTalla;
    Spinner sPerfiles;

    Button bGuardar;
    Button bEliminar;
    Button bFotos;

    ImageView imagen;

    String defaultText = "ENVIAR A PERFIL";

    Prenda prenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar__prenda);
        getSupportActionBar().hide();
        enlazar_controles();
        enlazar_prenda();
    }

    @Override
    protected void onResume(){
        super.onResume();
        cargar_foto(GestorBDFotos.fotoActual);
    }

    void enlazar_controles() {// Enlaza los controles y rellena los Spinner con la lista de opciones

        eNombre = findViewById(R.id.rellena_nombre);
        sColor = findViewById(R.id.spinner_color);
        sTipo = findViewById(R.id.spinner_tipo);
        sTalla = findViewById(R.id.spinner_talla);
        sPerfiles = findViewById(R.id.spinner_perfiles);
        bGuardar = findViewById(R.id.boton_guardar);
        bEliminar = findViewById(R.id.boton_eliminar);
        bFotos = findViewById(R.id.boton_foto);
        imagen = findViewById(R.id.prenda_foto);


        List<String> colores = GestorBD.get_nombres_tabla(this, "color");
        ArrayAdapter<String> adapterColores = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colores);
        adapterColores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sColor.setAdapter(adapterColores);

        List<String> tipos = GestorBD.get_nombres_tabla(this, "tipo");
        ArrayAdapter<String> adapterTipos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipos);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTipo.setAdapter(adapterTipos);

        List<String> tallas = GestorBD.get_nombres_tabla(this, "talla");
        ArrayAdapter<String> adapterTallas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tallas);
        adapterTallas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTalla.setAdapter(adapterTallas);

        List<String> perfiles = GestorBD.get_nombres_tabla(this, "perfil");

        ArrayAdapter<String> adapterPerfiles = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, perfiles);
        adapterPerfiles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPerfiles.setPrompt(defaultText);
        sPerfiles.setAdapter(adapterPerfiles);

        bEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrar_alerta_eliminar_prenda();
            }
        });

        bGuardar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                guardar();
            }
        });

        bFotos.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ir_a_fotos();
            }
        });
    }


    public void ir_a_fotos(){

        Intent mIntent = getIntent();
        int id = mIntent.getIntExtra("intVariableName", 0);

        Intent nuevaVentana = new Intent(this,Fotos.class);
        nuevaVentana.putExtra("id_modificar", String.valueOf(id));
        startActivity(nuevaVentana);
    }

    private void mostrar_alerta_eliminar_prenda() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Desea borrar la prenda?");
        alertDialog.setTitle("ATENCIÓN");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí, estoy seguro", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                eliminar();
                //código Java si se ha pulsado sí
            }
        });
        alertDialog.setNegativeButton("No, me lo he pensado mejor", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //No hace nada, se queda en la misma Activity
                //código java si se ha pulsado no
            }
        });

        alertDialog.show(); // esto es importante :D
    }


    void eliminar()
    {
        GestorBDPrendas.CambiarVisibilidadPrenda(this, prenda.id);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void guardar() {

        String nombre = eNombre.getText().toString();
        int color = sColor.getSelectedItemPosition() + 1;
        int tipo = sTipo.getSelectedItemPosition() + 1;
        int talla = sTalla.getSelectedItemPosition() + 1;
        int perfil = sPerfiles.getSelectedItemPosition() + 1;

        GestorBDPrendas.CambiarVisibilidadPrenda(this, prenda.id);
        //Esto peta porque lo ha modificado el otro grupo, lo dejo así de momento :D
        GestorBDPrendas.crearPrenda(this, nombre, color, tipo, talla, 1, perfil, GestorBDFotos.fotoActual);

        //GestorBD.Modificar_Prenda(this, prenda);

        finish();
    }


    void enlazar_prenda() // Pone los valores de la interfaz para que coincidan por defecto con la prenda a modificar
    {

        Intent mIntent = getIntent();
        int id = mIntent.getIntExtra("intVariableName", 0);

        GestorBDFotos.fotoActual = GestorBD.get_foto_de_prenda(this, id);

        prenda = GestorBDPrendas.Obtener_Prenda(this, id);

        prenda.id = id;

        eNombre.setText(prenda.nombre);
        sColor.setSelection(GestorBD.get_id_tabla(this, "color", prenda.color) - 1);
        sTipo.setSelection(GestorBD.get_id_tabla(this, "tipo", prenda.tipo) - 1);
        sTalla.setSelection(GestorBD.get_id_tabla(this, "talla", prenda.talla) - 1);
        sPerfiles.setSelection(GestorBD.getIdPerfil() - 1);
        cargar_foto(GestorBDFotos.fotoActual);
    }

    protected void cargar_foto(int idfoto){
        GestorBD.cargarFoto(this, idfoto, imagen);
    }





}

