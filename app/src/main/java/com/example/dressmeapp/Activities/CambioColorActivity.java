package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.ColorAdapter;
import com.example.dressmeapp.Objetos.ColorPrenda;
import com.example.dressmeapp.Objetos.PrendaAdapter;
import com.example.dressmeapp.Objetos.RecyclerViewOnItemClickListener;
import com.example.dressmeapp.R;

import java.util.List;

public class CambioColorActivity extends AppCompatActivity {

    private RecyclerView listaColores;
    private Button btnNuevoColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_color);
        enlazarControles();
        getSupportActionBar().hide();
    }

    private void enlazarControles() {
        listaColores = findViewById(R.id.listaColores);
        mostrarColores();

        btnNuevoColor = findViewById(R.id.btnNuevoColor);
        btnNuevoColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irANuevoColor();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        listaColores.removeAllViews();
        mostrarColores();
    }

    private void mostrarColores() {

        final Context context = this;
        final List<ColorPrenda> colores = GestorBD.ObtenerColores(context);

        for (ColorPrenda color : colores) {
            Log.i("COLOR", color.getHex());
        }
        Log.i("NUMERO COLORES", "" + colores.size());

        listaColores.setAdapter(new ColorAdapter(colores, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

                //Intent modificar = new Intent(context, Modificar_Prenda.class);
                //modificar.putExtra("intVariableName", colores.get(position).id);
                //startActivity(modificar);
            }
        }));

        listaColores.setLayoutManager(new LinearLayoutManager(this));

    }

    private void irANuevoColor() {
        Intent intent = new Intent(this, NuevoColorActivity.class);
        startActivity(intent);
    }

}
