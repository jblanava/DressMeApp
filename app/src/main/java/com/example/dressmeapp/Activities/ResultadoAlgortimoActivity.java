package com.example.dressmeapp.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dressmeapp.BaseDatos.GestorBDAlgoritmo;
import com.example.dressmeapp.BaseDatos.GestorBDFotos;
import com.example.dressmeapp.BaseDatos.GestorBDPrendas;
import com.example.dressmeapp.Objetos.Conjunto;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.R;


public class ResultadoAlgortimoActivity extends AppCompatActivity {
    private Conjunto conjunto = null;
    private LinearLayout lista_prendas;
    private Button bFavoritos;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_algoritmo);
        getSupportActionBar().hide();
        enlazar_controles();

        genera_conjunto();
    }

    private void enlazar_controles() {
        lista_prendas = findViewById(R.id.lista_prendas);
        Button bGuardar = findViewById(R.id.boton_guardar);
        Button bReintentar = findViewById(R.id.boton_reintentar);
        bFavoritos = findViewById(R.id.bAddFav);

        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        bReintentar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                reintentar();
            }
        });
        bFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aniade_favoritos();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void aniade_favoritos() {
        GestorBDAlgoritmo.add_conjunto(this, this.conjunto, 1);
        bFavoritos.setText("Conjunto añadido");
        Intent salto = new Intent(this, MenuPrincipalActivity.class);
        startActivity(salto);
        this.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void reintentar() {
        lista_prendas.removeAllViews();
        genera_conjunto();
    }

    private void guardar() {
        GestorBDAlgoritmo.add_conjunto(this,this.conjunto, 0);
        Intent salto = new Intent(this, MenuPrincipalActivity.class);
        startActivity(salto);
        this.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void genera_conjunto() {
        Intent mIntent = getIntent();
        int tiempo = mIntent.getIntExtra("temperatura", 0);
        int actividad = mIntent.getIntExtra("formalidad", 0);
        String nombreEvento = mIntent.getStringExtra("nombre");

        // TODO esta es la mayor chapuza de la historia

        conjunto = GestorBDAlgoritmo.get_conjunto_algoritmo(this, tiempo, actividad, nombreEvento);

        if(conjunto == null)
        {
            Toast.makeText(ResultadoAlgortimoActivity.this, "No hemos podido crear un conjunto que satisfaga las restricciones. Por favor introduzca más prendas", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Empezar en 1 porque el 0 es el ID del conjunto
        for (int j = 1; j < conjunto.getSize(); j++) {
            //Voy mostrando todas las prendas por pantalla
            int id_prenda = conjunto.obtenId(j);

            if(id_prenda != -1)
            {
                Prenda prenda = GestorBDPrendas.get_prenda(this, id_prenda);
                mostrar_prenda(prenda);
            }
        }


    }

    void mostrar_prenda(final Prenda prenda)
    {
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.activity_prenda_view, null);

        TextView nombre = v.findViewById(R.id.prenda_nombre);
        TextView tipo = v.findViewById(R.id.prenda_tipo);
        TextView color = v.findViewById(R.id.prenda_color);
        TextView talla = v.findViewById(R.id.prenda_talla);
        ImageView imagen = v.findViewById(R.id.imageView2);



        nombre.setText(prenda.nombre);
        color.setText(prenda.color);
        tipo.setText(prenda.tipo);
        talla.setText(prenda.talla);
        GestorBDFotos.cargar_foto(this, prenda.id,imagen);

        lista_prendas.addView(v);
    }


}