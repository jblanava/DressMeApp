package com.example.dressmeapp.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDPrendas;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.Objetos.PrendaAdapter;
import com.example.dressmeapp.Objetos.RecyclerViewOnItemClickListener;
import com.example.dressmeapp.R;

import java.util.ArrayList;
import java.util.List;

public class VestuarioActivity extends AppCompatActivity {

    EditText eBusqueda;
    Button bAnydir, bBuscar, bOrdenar, bAgrupar;
    LinearLayout lista_prendas;
    Spinner sOrdenar, sAgrupar;



    String busqueda = "";
    String ordenacion = "";
    String agrupacion = "";


    private final static String[] ordernar_por = {"Ordenar por:", "Nombre", "Color", "Tipo", "Talla"};
    private final static String[] agrupar_por = {"Agrupar por:", "Color", "Tipo", "Talla"};

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

        lista_prendas.removeAllViews();

        mostrar_prendas();
    }

    void enlazar_controles() {
        sOrdenar = findViewById(R.id.spinner_ordenar);
        sAgrupar = findViewById(R.id.spinner_agrupar);
        bAnydir = findViewById(R.id.boton_añadir);
        bBuscar = findViewById(R.id.boton_buscar);
        bOrdenar = findViewById(R.id.boton_ordenar);
        bAgrupar = findViewById(R.id.boton_agrupar);
        eBusqueda = findViewById(R.id.editText_busqueda);


        lista_prendas = findViewById(R.id.lista_prendas);


        ArrayAdapter<String> adapter_orden = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ordernar_por);
        adapter_orden.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sOrdenar.setAdapter(adapter_orden);

        ArrayAdapter<String> adapter_agrupar = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, agrupar_por);
        adapter_agrupar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sAgrupar.setAdapter(adapter_agrupar);


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

        ordenacion = criterio == 0 ? "" : ordernar_por[criterio];

        mostrar_prendas();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void agrupar() {
        int criterio = sAgrupar.getSelectedItemPosition();

        agrupacion = criterio == 0 ? "" : agrupar_por[criterio];

        mostrar_prendas();
    }

    void ir_a_anydir()
    {
        Intent anydir = new Intent(this, AniadirPrendaActivity.class);
        startActivity(anydir);
    }

    void buscar() {
        quitar_teclado(eBusqueda);
        busqueda = eBusqueda.getText().toString();
        mostrar_prendas();
    }


    @SuppressLint("SetTextI18n")
    void mostrar_prendas() {
        lista_prendas.removeAllViews();

        final List<Prenda> prendas = GestorBDPrendas.get_prendas_visibles(this, this.busqueda, this.ordenacion);

        final Context a = this;

        if(!this.agrupacion.equals(""))
        {
            List<String> tags =  GestorBD.get_nombres_tabla(this, this.agrupacion);

            for(String tag : tags)
            {
                final List<Prenda> prendas_con_tag = new ArrayList<>();

                for (Prenda prenda : prendas)
                {
                    if(agrupacion.equalsIgnoreCase("color"))
                    {
                        if(prenda.color.equalsIgnoreCase(tag))
                        {
                            prendas_con_tag.add(prenda);
                        }
                    }
                    else if(agrupacion.equalsIgnoreCase("tipo"))
                    {
                        if(prenda.tipo.equalsIgnoreCase(tag))
                        {
                            prendas_con_tag.add(prenda);
                        }
                    }
                    else if(agrupacion.equalsIgnoreCase("talla"))
                    {
                        if(prenda.talla.equalsIgnoreCase(tag))
                        {
                            prendas_con_tag.add(prenda);
                        }
                    }
                }

                if(prendas_con_tag.size() == 0) continue;

                @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.activity_agrupar_view, null);

                TextView texto = view.findViewById(R.id.texto_tag);
                RecyclerView rv = view.findViewById(R.id.prendas_agrupadas);

                texto.setText(tag);

                rv.setAdapter(new PrendaAdapter(prendas_con_tag, new RecyclerViewOnItemClickListener() {
                    @Override
                    public void onClick(View v, int position) {

                        Intent modificar = new Intent(a, Modificar_Prenda.class);
                        modificar.putExtra("intVariableName", prendas_con_tag.get(position).id);
                        startActivity(modificar);
                    }
                }));


                rv.setLayoutManager(new LinearLayoutManager(this));

                lista_prendas.addView(view);
            }
        }
        else
        {
            @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.activity_agrupar_view, null);

            TextView texto = view.findViewById(R.id.texto_tag);
            RecyclerView rv = view.findViewById(R.id.prendas_agrupadas);

            texto.setText("Tus prendas");

            rv.setAdapter(new PrendaAdapter(prendas, new RecyclerViewOnItemClickListener() {
                @Override
                public void onClick(View v, int position) {

                    Intent modificar = new Intent(a, Modificar_Prenda.class);
                    modificar.putExtra("intVariableName", prendas.get(position).id);
                    startActivity(modificar);
                }
            }));


            rv.setLayoutManager(new LinearLayoutManager(this));

            lista_prendas.addView(view);
        }

    }

    private void quitar_teclado(View v) {
        InputMethodManager teclado = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        teclado.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
