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

import java.util.List;
import java.util.Random;

public class AlgoritmoRecomendador extends AppCompatActivity {
    private LinearLayout listaPrendas;
    private Conjunto conjunto;
    private Context contexto;
    private GestorBD gestor;
     //La idea es de cada vez que se genere un conjunto se añada a la lista, que luego
                                                    //se le pasará a Historial
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritmo_recomendador);
        contexto = getApplicationContext();
        gestor=new GestorBD(contexto);
        rellenaConjunto();
        gestor.addConjunto();//Esta funcion añadirá un conjunto a la Base de Datos
        muestraConjuntos();
    }

    private void muestraConjuntos(){
    if(conjunto != null) {
        for (int i = 0; i < conjunto.getSize(); i++) {
            Prenda prenda = GestorBD.Obtener_Prenda(contexto, conjunto.obtenId(i));
            metodoChavales(prenda);
        }//PROBABLEMENTE EL FOR NO CIERRE AQUÍ :D
    }
    }

    private void rellenaConjunto(){
        //Esto es provisional porque no se me ocurre nada mejor de momento para rellenar conjuntos:D
        int limite= GestorBD.obtenIDMaximoPrenda(contexto);
        Random rnd = new Random();
        int id;
        for(int i=0;i<6;i++){
        id=rnd.nextInt(limite);
        conjunto.add(id);
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


