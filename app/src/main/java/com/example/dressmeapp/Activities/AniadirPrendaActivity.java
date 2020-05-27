package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.R;

import java.util.List;

public class AniadirPrendaActivity extends AppCompatActivity {

    EditText Enombre;
    Spinner Scolor;
    Spinner Stipo;
    Spinner Stalla;

    Button Bcrear;
    Button Bcancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_prenda);

        enlazar_controles();
    }


    void enlazar_controles() // Enlaza los controles y rellena los Spinner con la lista de opciones
    {
        Enombre =  findViewById(R.id.rellena_nombre);
        Scolor = findViewById(R.id.spinner_color);
        Stipo = findViewById(R.id.spinner_tipo);
        Stalla = findViewById(R.id.spinner_talla);
        Bcrear =  findViewById(R.id.boton_crear);
        Bcancelar = findViewById(R.id.boton_cancelar);

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
        int color = Scolor.getSelectedItemPosition() + 1;
        int tipo = Stipo.getSelectedItemPosition() + 1;
        int talla = Stalla.getSelectedItemPosition() + 1;

        GestorBD.crearPrenda(this, nombre, color, tipo, talla, 1, GestorBD.getIdPerfil());

        finish();
    }

}
