package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Conjunto;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.R;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class HistorialActivity extends AppCompatActivity {

        private List<Conjunto> listaConjuntos;
        //Se rellenará usando métodos de GestorBD, y se irán sacando los conjuntos de allí.
        private LinearLayout listaPrendas;
        private TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        enlazaControles();
        hagoCosas();


    }



    private void enlazaControles() {
        listaPrendas = (LinearLayout) findViewById(R.id.lista_prendas);

    }

    private void hagoCosas() {
        listaConjuntos= GestorBD.ConjuntosEnBD(this);
        Collections.reverse(listaConjuntos); // esto voltea la lista
        Conjunto aux;
        int contador=0;
        for(Conjunto c : listaConjuntos){
            contador++;
            //Crear un text view, mostrarlo por pantalla
            metodoMisPanas(contador);
            //Recorro el conjunto y muestro las prendas


            for(int j = 1; j < c.getSize(); j++){ // empezamos en 1, porque la pos 0 es para el propio ID del conjunto //
                //Voy mostrando todas las prendas por pantalla
            int idPrenda= c.obtenId(j);
            Prenda prenda = GestorBD.Obtener_Prenda(this,idPrenda);
            metodoChavales(prenda);
            }
        }


    }

    private void metodoMisPanas(int cont) {
        View v = getLayoutInflater().inflate(R.layout.activity_conjunto_view, null);

        TextView conjunto = (TextView) v.findViewById(R.id.conjuntoView);
        conjunto.setText("CONJUNTO "+cont);
        listaPrendas.addView(v);
    }

    void metodoChavales(final Prenda prenda)
    {
        View v = getLayoutInflater().inflate(R.layout.activity_prenda_view, null);

        TextView nombre = (TextView) v.findViewById(R.id.prenda_nombre);
        TextView tipo = (TextView) v.findViewById(R.id.prenda_tipo);
        TextView color = (TextView) v.findViewById(R.id.prenda_color);
        TextView talla = (TextView) v.findViewById(R.id.prenda_talla);

        String tipoText = GestorBD.get_nombre_tabla(this, "tipo", prenda.tipo);
        String tallaText = GestorBD.get_nombre_tabla(this, "talla", prenda.talla);

        nombre.setText(prenda.nombre);
        tipo.setText(tipoText);
        color.setText(prenda.color);
        talla.setText(tallaText);

        TableLayout t = (TableLayout) v.findViewById(R.id.boton_prenda);


        listaPrendas.addView(v);
    }
}
