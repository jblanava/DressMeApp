package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.*;
import com.example.dressmeapp.R;

public class AlgoritmoRecomendador extends AppCompatActivity {
    private LinearLayout listaPrendas;
    private Conjunto conjunto;
    private GestorBD gestor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritmo_recomendador);
        gestor= new GestorBD(this);
        muestraConjuntos();
    }

    private void muestraConjuntos(){
    if(conjunto != null) {
        for (int i = 0; i < conjunto.getSize(); i++) {
            Prenda prenda = gestor.Obtener_Prenda(this, conjunto.obtenId(i));
            metodoChavales(prenda);
        }//PROBABLEMENTE EL FOR NO CIERRE AQUÍ :D
    }


    }
    private void metodoChavales(final Prenda prenda){
        View v = getLayoutInflater().inflate(R.layout.activity_prenda_view, null);

        TextView nombre = (TextView) v.findViewById(R.id.prenda_nombre);
        TextView tipo = (TextView) v.findViewById(R.id.prenda_tipo);
        TextView color = (TextView) v.findViewById(R.id.prenda_color);
        TextView talla = (TextView) v.findViewById(R.id.prenda_talla);

        String tipoText = GestorBD.Dar_Tipo(this, prenda.tipo);
        String tallaText = GestorBD.Dar_Talla(this, prenda.talla);

        nombre.setText(prenda.nombre);
        tipo.setText(tipoText);
        color.setText(prenda.color);
        talla.setText(tallaText);

        TableLayout t = (TableLayout) v.findViewById(R.id.boton_prenda);

        final Context a = this;

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "Boton pulsado, prenda con nombre: " + prenda.nombre);

                Intent modificar = new Intent(a, Modificar_Prenda.class);
                modificar.putExtra("intVariableName", prenda.id);
                startActivity(modificar);
            }
        });

        listaPrendas.addView(v);
    }
    }


