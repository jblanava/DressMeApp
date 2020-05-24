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

        String a = GestorBD.get_nombre_tabla(context, "talla", o1.talla);
        String b = GestorBD.get_nombre_tabla(context, "talla", o2.talla);

        return a.compareToIgnoreCase(b);
    }

}