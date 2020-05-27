package com.example.dressmeapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.Objetos.PrendaAdapter;
import com.example.dressmeapp.Objetos.RecyclerViewOnItemClickListener;
import com.example.dressmeapp.R;

import java.util.List;

public class VestuarioActivity extends AppCompatActivity {

    EditText Ebusqueda;
    Button bAnydir, bBuscar, bOrdenar, bAgrupar;
    RecyclerView listaPrendas;
    Spinner sOrdenar, sAgrupar;






    String busqueda = "";
    String ordenacion = "nombre";
    int agrupacion = 0;

    private final static String[] ordernarPor = {"Ordenar por:", "Nombre", "Color", "Tipo", "Talla"};
    private final static String[] agruparPor = {"Agrupar por:", "Color", "Tipo", "Talla"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vestuario);
        getSupportActionBar().hide();
        enlazar_controles();
    }


    @Override
    protected void onResume() {
        super.onResume();

        listaPrendas.removeAllViews();

        mostrar_prendas();
    }

    void enlazar_controles() {
        sOrdenar = findViewById(R.id.spinner_ordenar);
        sAgrupar = findViewById(R.id.spinner_agrupar);
        bAnydir = findViewById(R.id.boton_añadir);
        bBuscar = findViewById(R.id.boton_buscar);
        bOrdenar = findViewById(R.id.boton_ordenar);
        bAgrupar = findViewById(R.id.boton_agrupar);
        Ebusqueda = findViewById(R.id.editText_busqueda);


        listaPrendas = findViewById(R.id.lista_prendas);


        ArrayAdapter<String> adapterOrden = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ordernarPor);
        adapterOrden.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sOrdenar.setAdapter(adapterOrden);

        ArrayAdapter<String> adapterAgrupar = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, agruparPor);
        adapterAgrupar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sAgrupar.setAdapter(adapterAgrupar);


        bAnydir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir_a_anydir();
            }
        });

        bBuscar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                buscar();
            }
        });

        bOrdenar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ordenar();
            }
        });

        bAgrupar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                agrupar();
            }
        });
    }

    void ordenar() {
        int criterio = sOrdenar.getSelectedItemPosition();

        ordenacion = ordernarPor[criterio];

        mostrar_prendas();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void agrupar() {
        agrupacion = sAgrupar.getSelectedItemPosition();

        mostrar_prendas();
    }

    void ir_a_anydir() {
        Intent anydir = new Intent(this, AniadirPrendaActivity.class);

        startActivity(anydir);
    }

    void buscar() {
        busqueda = Ebusqueda.getText().toString();
        mostrar_prendas();
    }


    void mostrar_prendas() {
        listaPrendas.removeAllViews();

        final List<Prenda> prendas = GestorBD.PrendasVisibles(this, this.busqueda, this.ordenacion);

        final Context a = this;

        listaPrendas.setAdapter(new PrendaAdapter(prendas, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

                Intent modificar = new Intent(a, Modificar_Prenda.class);
                modificar.putExtra("intVariableName", prendas.get(position).id);
                startActivity(modificar);
            }
        }));

        listaPrendas.setLayoutManager(new LinearLayoutManager(this));

        /*
        TODO eliminar esto

        if (agrupacion == 0) {

            for (Prenda p : prendas) {
                añadir_elemento(p);
            }

        }
        else
        {
            String[] campos = {"Error", "color", "tipo", "talla"};

            String tabla = campos[agrupacion];

            List<String> tags = GestorBD.get_nombres_tabla(this, tabla);

            for(String tag : tags)
            {
                View v = getLayoutInflater().inflate(R.layout.activity_cabecera_agrupacion, null);
                TextView nombre = v.findViewById(R.id.Cabecera);
                nombre.setText(tag);
                listaPrendas.addView(v);

                for (Prenda p : prendas)
                {
                    String[] camposPrenda = {"", p.color, p.tipo, p.talla};

                    if(camposPrenda[agrupacion].equalsIgnoreCase(tag))
                    {
                        añadir_elemento(p);
                    }
                }
            }
        }
         */


    }

}
