package com.example.dressmeapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Conjunto;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.R;

import java.util.Collections;

public class ResultadoAlgortimoActivity extends AppCompatActivity {
    private Conjunto conjunto;
    private LinearLayout listaPrendas;
    private Button guardar;
    private Button reintentar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_algoritmo);
        getSupportActionBar().hide();
        enlazaControles();

        hagoCosas();
    }

    private void enlazaControles() {
        listaPrendas = (LinearLayout) findViewById(R.id.lista_prendas);
        guardar=findViewById(R.id.boton_guardar);
        reintentar=findViewById(R.id.boton_reintentar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardaConjunto();
            }
        });

        reintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryConjunto();
            }
        });
    }

    private void retryConjunto() {
    Intent salto = new Intent(this, AlgoritmoRecomendador.class);
    startActivity(salto);
    this.finish();

    }

    private void guardaConjunto() {
        GestorBD.addConjunto(this,this.conjunto);
        Intent salto = new Intent(this, MenuPrincipalActivity.class);
        startActivity(salto);
        this.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void hagoCosas() {
        Intent mIntent = getIntent();
        int tiempo = mIntent.getIntExtra("temperatura", 0);;
        int actividad = mIntent.getIntExtra("formalidad", 0);;

        conjunto = GestorBD.resAlgoritmo(this, tiempo, actividad);


        for (int j = 1; j < conjunto.getSize(); j++) { // empezamos en 1, porque la pos 0 es para el propio ID del conjunto //
            //Voy mostrando todas las prendas por pantalla
            int idPrenda = conjunto.obtenId(j);

            if(idPrenda != -1)
            {
                Prenda prenda = GestorBD.Obtener_Prenda(this, idPrenda);
                metodoChavales(prenda);
            }
        }


    }

    void metodoChavales(final Prenda prenda)
    {
        View v = getLayoutInflater().inflate(R.layout.activity_prenda_view, null);

        TextView nombre = (TextView) v.findViewById(R.id.prenda_nombre);
        TextView tipo = (TextView) v.findViewById(R.id.prenda_tipo);
        TextView color = (TextView) v.findViewById(R.id.prenda_color);
        TextView talla = (TextView) v.findViewById(R.id.prenda_talla);


        nombre.setText(prenda.nombre);
        color.setText(prenda.color);
        tipo.setText(prenda.tipo);
        talla.setText(prenda.talla);

        TableLayout t = (TableLayout) v.findViewById(R.id.boton_prenda);


        listaPrendas.addView(v);
    }


}