package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.R;

import java.util.List;

public class CambioTallasActivity extends AppCompatActivity {
    Button bAddTalla;
    LinearLayout listaTallas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_tallas);
        getSupportActionBar().hide();
        enlazarControles();
    }


    @Override
    protected void onResume() {
        super.onResume();

        listaTallas.removeAllViews();

        cargarTallas();
    }

    private void enlazarControles() {
        listaTallas = findViewById(R.id.lista_tallas);

        bAddTalla=findViewById(R.id.bAddTalla);
        bAddTalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aniadirTalla();
            }
        });
    }

    void cargarTallas()
    {
        List<String> tallas = GestorBD.get_nombres_tabla(this, "talla");

        for(String t : tallas)
        {
            TextView textView = new TextView(this);
            textView.setText(t);
            listaTallas.addView(textView);
        }
    }

    private void aniadirTalla(){
        Intent nuevaTalla = new Intent(this,AniadirTallaActivity.class);
        startActivity(nuevaTalla);
    }
}
