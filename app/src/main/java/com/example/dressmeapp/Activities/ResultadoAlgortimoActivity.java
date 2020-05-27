package com.example.dressmeapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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

        /*
        TODO: Eliminar

        String colorText = GestorBD.get_nombre_tabla(this, "color", prenda.color);
        String tipoText = GestorBD.get_nombre_tabla(this, "tipo", prenda.tipo);
        String tallaText = GestorBD.get_nombre_tabla(this, "talla", prenda.talla);

        nombre.setText(prenda.nombre);
        color.setText(colorText);
        tipo.setText(tipoText);
        talla.setText(tallaText);

         */

        nombre.setText(prenda.nombre);
        color.setText(prenda.color);
        tipo.setText(prenda.tipo);
        talla.setText(prenda.talla);

        TableLayout t = (TableLayout) v.findViewById(R.id.boton_prenda);


        listaPrendas.addView(v);
    }


}