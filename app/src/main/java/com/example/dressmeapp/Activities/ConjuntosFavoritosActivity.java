package com.example.dressmeapp.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDAlgoritmo;
import com.example.dressmeapp.BaseDatos.GestorBDFotos;
import com.example.dressmeapp.BaseDatos.GestorBDPrendas;
import com.example.dressmeapp.Objetos.Conjunto;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.R;

import java.util.Collections;
import java.util.List;

public class ConjuntosFavoritosActivity extends AppCompatActivity {


    //Se rellenará usando métodos de GestorBD, y se irán sacando los conjuntos de allí.
    private LinearLayout listaPrendas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conjuntos_favoritos);
        getSupportActionBar().hide();
        enlaza_controles();
        mostrar_conjuntos();
    }



    private void enlaza_controles() {
        listaPrendas = findViewById(R.id.lista_prendas);

    }

    private void mostrar_conjuntos() {
        List<Conjunto> listaConjuntos= GestorBDAlgoritmo.get_conjuntos_favoritos(this);
        Collections.reverse(listaConjuntos); // esto voltea la lista

        for(Conjunto c : listaConjuntos)
        {
            //Crear un text view, mostrarlo por pantalla
            mostrar_conjunto(c.getNombre_conjunto());
            //Recorro el conjunto y muestro las prendas


            for(int j = 1; j < c.getSize(); j++){ // empezamos en 1, porque la pos 0 es para el propio ID del conjunto //
                //Voy mostrando todas las prendas por pantalla
                int idPrenda= c.obtenId(j);
                Prenda prenda = GestorBDPrendas.get_prenda(this,idPrenda);
                mostrar_prenda(prenda);
            }
        }


    }

    @SuppressLint("DefaultLocale")
    private void mostrar_conjunto(String nombreConj) {
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.activity_conjunto_view, null);

        TextView conjunto = v.findViewById(R.id.conjuntoView);
        conjunto.setText(nombreConj);
        listaPrendas.addView(v);
    }

    void mostrar_prenda(final Prenda prenda)
    {
        if(prenda == null) return;

        //@SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.activity_prenda_view, null);

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

        GestorBDFotos.cargar_foto(this, GestorBD.get_foto_de_prenda(this, prenda.id), imagen);

        listaPrendas.addView(v);
    }

}
