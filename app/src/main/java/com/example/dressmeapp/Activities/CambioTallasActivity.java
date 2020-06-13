package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.RecyclerViewOnItemClickListener;
import com.example.dressmeapp.Objetos.TallaAdapter;
import com.example.dressmeapp.R;

import java.util.List;

public class CambioTallasActivity extends AppCompatActivity {
    Button bAddTalla;
    RecyclerView listaTallas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_tallas);
        getSupportActionBar().hide();
        enlazar_controles();
    }


    @Override
    protected void onResume() {
        super.onResume();

        listaTallas.removeAllViews();

        cargar_tallas();
    }

    private void enlazar_controles() {
        listaTallas = findViewById(R.id.lista_tallas);

        bAddTalla=findViewById(R.id.bAddTalla);
        bAddTalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aniadir_talla();
            }
        });
    }

    void cargar_tallas() {
        final List<String> tallas = GestorBD.get_nombres_tabla(this, "talla");

        listaTallas.setAdapter(new TallaAdapter(tallas, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                // no pasa nada al tocar la talla
            }
        }));

        listaTallas.setLayoutManager(new LinearLayoutManager(this));
    }

    private void aniadir_talla(){
        Intent nuevaTalla = new Intent(this,AniadirTallaActivity.class);
        startActivity(nuevaTalla);
    }
}
