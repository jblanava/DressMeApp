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
import com.example.dressmeapp.Objetos.Conjunto;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.R;

import java.util.List;
import java.util.Stack;

public class HistorialActivity extends AppCompatActivity {
        private GestorBD gestor;
        private List<Conjunto> listaConjuntos;
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
        listaPrendas = (LinearLayout) findViewById(R.id.lista_prendas);
    }

    private void hagoCosas() {
        listaConjuntos= gestor.ConjuntosEnBD(this);
        Conjunto aux;
        int contador=0;
        for(int i=listaConjuntos.size();i>0;i--){
            contador++;
            //Crear un text view, mostrarlo por pantalla
            metodoMisPanas(contador);
            //Recorro el conjunto y muestro las prendas

            aux=listaConjuntos.get(i);
            for(int j=0;j<aux.getSize();j++){
                //Voy mostrando todas las prendas por pantalla
            int idPrenda= aux.obtenId(j);
            Prenda prenda = gestor.Obtener_Prenda(this,idPrenda);
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
