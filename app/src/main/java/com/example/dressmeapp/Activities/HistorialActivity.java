package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Conjunto;
import com.example.dressmeapp.R;

import java.util.List;

public class HistorialActivity extends AppCompatActivity {
        private GestorBD gestor;
        private List<Conjunto> listaConjuntos;  //Se rellenará usando métodos de GestorBD, y se irán sacando los conjuntos de allí.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
    }

}
