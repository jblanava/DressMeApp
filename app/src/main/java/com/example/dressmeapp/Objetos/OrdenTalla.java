package com.example.dressmeapp.Objetos;

import android.content.Context;

import com.example.dressmeapp.BaseDatos.GestorBD;

import java.util.Comparator;

public class OrdenTalla implements Comparator<Prenda> {

    Context context;

    public  OrdenTalla(Context context)
    {
        this.context = context;
    }

    @Override
    public int compare(Prenda o1, Prenda o2) {

        String a = GestorBD.Dar_Talla(context, o1.talla);
        String b = GestorBD.Dar_Talla(context, o2.talla);

        return a.compareToIgnoreCase(b);
    }

}