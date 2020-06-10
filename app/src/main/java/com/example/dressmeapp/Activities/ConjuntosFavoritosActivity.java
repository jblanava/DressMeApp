package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
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
        enlazaControles();
        hagoCosas();
    }



    private void enlazaControles() {
        listaPrendas = findViewById(R.id.lista_prendas);

    }

    private void hagoCosas() {
        List<Conjunto> listaConjuntos= GestorBD.ConjuntosFavoritosEnBD(this);
        Collections.reverse(listaConjuntos); // esto voltea la lista

        for(Conjunto c : listaConjuntos)
        {
            //Crear un text view, mostrarlo por pantalla
            metodoMisPanas(c.getNombreCjto());
            //Recorro el conjunto y muestro las prendas


            for(int j = 1; j < c.getSize(); j++){ // empezamos en 1, porque la pos 0 es para el propio ID del conjunto //
                //Voy mostrando todas las prendas por pantalla
                int idPrenda= c.obtenId(j);
                Prenda prenda = GestorBD.Obtener_Prenda(this,idPrenda);
                metodoChavales(prenda);
            }
        }


    }

    @SuppressLint("DefaultLocale")
    private void metodoMisPanas(String nombreConj) {
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.activity_conjunto_view, null);

        TextView conjunto = v.findViewById(R.id.conjuntoView);
        conjunto.setText(nombreConj);
        listaPrendas.addView(v);
    }

    void metodoChavales(final Prenda prenda)
    {
        if(prenda == null) return;      // TODO: porque cojones necesito esto aqui? PS: no quitar o peta el historial

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
        GestorBD.cargarFoto(this, String.valueOf(prenda.id),imagen);

        listaPrendas.addView(v);
    }

}
