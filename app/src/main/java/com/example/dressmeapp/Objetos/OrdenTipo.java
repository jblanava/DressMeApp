package com.example.dressmeapp.Objetos;

import android.content.Context;

import com.example.dressmeapp.BaseDatos.GestorBD;

import java.util.Comparator;

public class OrdenTipo implements Comparator<Prenda> {

    Context context;

    public  OrdenTipo(Context context)
    {
        this.context = context;
    }

    @Override
    public int compare(Prenda o1, Prenda o2) {

        String a = GestorBD.get_nombre_tabla(context, "tipo", o1.tipo);
        String b = GestorBD.get_nombre_tabla(context, "tipo", o2.tipo);

        return a.compareToIgnoreCase(b);
    }

}