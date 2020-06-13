package com.example.dressmeapp.Activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dressmeapp.BaseDatos.GestorBD;

import com.example.dressmeapp.BaseDatos.GestorBDPrendas;
import com.example.dressmeapp.Objetos.ComboColorAdapter;
import com.example.dressmeapp.Objetos.ComboColorPrenda;
import com.example.dressmeapp.Objetos.RecyclerViewOnItemClickListener;
import com.example.dressmeapp.R;

import java.util.List;

public class AniadirCombinacion extends AppCompatActivity {

    RecyclerView comboColorView;
    Spinner Scolor1;
    Spinner Scolor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_combinacion);
        getSupportActionBar().hide();
        enlazar_controles();
    }


    void enlazar_controles() {// Enlaza los controles y rellena los Spinner con la lista de opciones

        comboColorView = findViewById(R.id.comboColorView);

        Scolor1 = findViewById(R.id.spinner_color1);
        Scolor2 = findViewById(R.id.spinner_color2);
        Button Bguardar = findViewById(R.id.boton_guardar);


        List<String> colores = GestorBD.get_nombres_tabla(this, "color");
        ArrayAdapter<String> adapterColores = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colores);
        adapterColores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Scolor1.setAdapter(adapterColores);
        Scolor2.setAdapter(adapterColores);

        Bguardar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
    }

    void guardar()
    {
        int c1 = Scolor1.getSelectedItemPosition() + 1;
        int c2 = Scolor2.getSelectedItemPosition() + 1;

        if (GestorBDPrendas.crear_combo_color(this, c1, c2)) {
            Toast.makeText(AniadirCombinacion.this, "Se ha guardado la combinacion indicada", Toast.LENGTH_SHORT).show();
            mostrarCombos();
        } else {
            Toast.makeText(AniadirCombinacion.this, "Combinación de colores ya registrada", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarCombos();
    }

    private void mostrarCombos() {

        comboColorView.removeAllViews();

        final Context context = this;
        final List<ComboColorPrenda> combos = GestorBDPrendas.get_combos_colores(context);

        comboColorView.setAdapter(new ComboColorAdapter(combos, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) { }
        }));

        comboColorView.setLayoutManager(new LinearLayoutManager(this));

    }

}
