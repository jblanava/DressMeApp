package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Conjunto;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.R;

import java.util.Collections;
import java.util.List;

public class HistorialActivity extends AppCompatActivity {

        //Se rellenará usando métodos de GestorBD, y se irán sacando los conjuntos de allí.
        private LinearLayout listaPrendas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        enlazaControles();
        hagoCosas();


    }



    private void enlazaControles() {
        listaPrendas = findViewById(R.id.lista_prendas);

    }

    private void hagoCosas() {
        List<Conjunto> listaConjuntos= GestorBD.ConjuntosEnBD(this);
        Collections.reverse(listaConjuntos); // esto voltea la lista

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

        TextView conjunto = v.findViewById(R.id.conjuntoView);
        conjunto.setText("CONJUNTO "+cont);
        listaPrendas.addView(v);
    }

    void metodoChavales(final Prenda prenda)
    {
        if(prenda == null) return;      // TODO: porque cojones necesito esto aqui? PS: no quitar o peta el historial


        View v = getLayoutInflater().inflate(R.layout.activity_prenda_view, null);

        TextView nombre = v.findViewById(R.id.prenda_nombre);
        TextView tipo = v.findViewById(R.id.prenda_tipo);
        TextView color = v.findViewById(R.id.prenda_color);
        TextView talla = v.findViewById(R.id.prenda_talla);


        nombre.setText(prenda.nombre);
        color.setText(prenda.color);
        tipo.setText(prenda.tipo);
        talla.setText(prenda.talla);

        listaPrendas.addView(v);
    }
}
