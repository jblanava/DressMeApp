package com.example.dressmeapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.OrdenColor;
import com.example.dressmeapp.Objetos.OrdenNombre;
import com.example.dressmeapp.Objetos.OrdenTalla;
import com.example.dressmeapp.Objetos.OrdenTipo;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.R;

import java.util.Comparator;
import java.util.List;

public class VestuarioActivity extends AppCompatActivity {

    EditText Ebusqueda;
    Button bAnydir, bBuscar, bOrdenar, bAgrupar;
    LinearLayout listaPrendas;
    Spinner sOrdenar, sAgrupar;


    String busqueda = "";
    Comparator<Prenda> comparator = new OrdenNombre();
    int agrupacion = 0;

    private final static String[] ordernarPor = {"Ordenar por:", "Nombre", "Color", "Tipo", "Talla"};
    private final static String[] agruparPor = {"Agrupar por:", "Color", "Tipo", "Talla"};

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vestuario);

        enlazar_controles();

        mostrar_prendas();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    void ordenar() {
        int criterio = sOrdenar.getSelectedItemPosition();

        if (criterio == 1) {
            comparator = new OrdenNombre();
        } else if (criterio == 2) {
            comparator = new OrdenColor(this);// TODO: Este comparator es muy lento
        } else if (criterio == 3) {
            comparator = new OrdenTipo(this); // TODO: Este comparator es muy lento
        } else if (criterio == 4) {
            comparator = new OrdenTalla(this);// TODO: Este comparator es muy lento
        }

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    void buscar() {
        busqueda = Ebusqueda.getText().toString();
        mostrar_prendas();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    void mostrar_prendas() {
        listaPrendas.removeAllViews();

        List<Prenda> prendas = GestorBD.PrendasVisibles(this, this.busqueda);

        prendas.sort(this.comparator);

        if (agrupacion == 0) {
            for (Prenda p : prendas) {
                añadir_elemento(p);
            }
        }
        else
        {
            String[] campos = {"Error", "color", "tipo", "talla"};

            String tabla = campos[agrupacion];

            List<Integer> tags = GestorBD.get_ids_tabla(this, tabla);

            for(int tag : tags)
            {
                View v = getLayoutInflater().inflate(R.layout.activity_cabecera_agrupacion, null);
                TextView nombre = v.findViewById(R.id.Cabecera);
                String nombreTag = GestorBD.get_nombre_tabla(this, tabla, tag);
                nombre.setText(nombreTag);
                listaPrendas.addView(v);

                for (Prenda p : prendas)
                {
                    int[] camposPrenda = {-1, p.color, p.tipo, p.talla};

                    if(camposPrenda[agrupacion] == tag)
                    {
                        añadir_elemento(p);
                    }
                }
            }
        }

    }

    void añadir_elemento(final Prenda prenda) {
        View v = getLayoutInflater().inflate(R.layout.activity_prenda_view, null);

        TextView nombre = v.findViewById(R.id.prenda_nombre);
        TextView tipo = v.findViewById(R.id.prenda_tipo);
        TextView color = v.findViewById(R.id.prenda_color);
        TextView talla = v.findViewById(R.id.prenda_talla);

        String colorText = GestorBD.get_nombre_tabla(this, "color", prenda.color);
        String tipoText = GestorBD.get_nombre_tabla(this, "tipo", prenda.tipo);
        String tallaText = GestorBD.get_nombre_tabla(this, "talla", prenda.talla);

        nombre.setText(prenda.nombre);
        color.setText(colorText);
        tipo.setText(tipoText);
        talla.setText(tallaText);

        TableLayout t = v.findViewById(R.id.boton_prenda);

        final Context a = this;

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modificar = new Intent(a, Modificar_Prenda.class);
                modificar.putExtra("intVariableName", prenda.id);
                startActivity(modificar);
            }
        });

        listaPrendas.addView(v);
    }
}
