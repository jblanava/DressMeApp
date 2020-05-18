package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.R;

import java.util.List;

public class VestuarioActivity extends AppCompatActivity {

    Button bOrdenar,bAgrupar,bAnydir, bBuscar;
    LinearLayout listaPrendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vestuario);

        enlazar_controles();

        List<Prenda> prendas = GestorBD.Dar_Prendas(this);

        prendas.add(new Prenda("hola", "galleta", "BAÑADOR/BIKINI", "tutifruti"));

        for(Prenda p : prendas)
        {
            añadir_elemento(p);
        }
    }

    void enlazar_controles()
    {
        bOrdenar = (Button) findViewById(R.id.boton_ordenar);
        bAgrupar = (Button) findViewById(R.id.boton_agrupar);
        bAnydir = (Button) findViewById(R.id.boton_añadir);
        bBuscar = (Button) findViewById(R.id.boton_buscar);

        listaPrendas = (LinearLayout) findViewById(R.id.lista_prendas);

        bAgrupar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir_a_agrupar();
            }
        });

        bOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir_a_ordenar();
            }
        });

        bAnydir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir_a_anydir();
            }
        });

        bBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });
    }

    void ir_a_ordenar()
    {
        Intent ordenar = new Intent(this, OrdenarPrendasActivity.class);
        startActivity(ordenar);
    }

    void ir_a_agrupar()
    {
        Intent agrupar = new Intent(this, AgruparPrendasActivity.class); //CAMBIAR A AGRUPAR
        startActivity(agrupar);
    }

    void ir_a_anydir()
    {
        Intent anydir = new Intent(this, AniadirPrendaActivity.class);
        startActivity(anydir);
    }

    void buscar()
    {
        // No se que hay que poner aqui aun
    }


    void añadir_elemento(final Prenda prenda)
    {
        View v = getLayoutInflater().inflate(R.layout.activity_prenda_view, null);

        TextView nombre = (TextView) v.findViewById(R.id.prenda_nombre);
        TextView tipo = (TextView) v.findViewById(R.id.prenda_tipo);
        TextView color = (TextView) v.findViewById(R.id.prenda_color);
        TextView talla = (TextView) v.findViewById(R.id.prenda_talla);

        nombre.setText(prenda.nombre);
        tipo.setText(prenda.tipo);
        color.setText(prenda.color);
        talla.setText(prenda.talla);

        TableLayout t = (TableLayout) v.findViewById(R.id.boton_prenda);

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "Boton pulsado, prenda con nombre: " + prenda.nombre);
            }
        });

        listaPrendas.addView(v);
    }
}
