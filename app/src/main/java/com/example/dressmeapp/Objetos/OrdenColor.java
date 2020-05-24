package com.example.dressmeapp.Objetos;

import android.content.Context;

import java.util.Comparator;

import com.example.dressmeapp.BaseDatos.GestorBD;

import java.util.Comparator;

public class OrdenColor implements Comparator<Prenda> {

    Context context;

    public  OrdenColor(Context context)
    {
        this.context = context;
    }

    @Override
    public int compare(Prenda o1, Prenda o2) {

        String a = GestorBD.get_nombre_tabla(context, "color", o1.color);
        String b = GestorBD.get_nombre_tabla(context, "color", o2.color);

        return a.compareToIgnoreCase(b);
    }

}