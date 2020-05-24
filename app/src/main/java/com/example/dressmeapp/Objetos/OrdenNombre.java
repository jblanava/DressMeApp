package com.example.dressmeapp.Objetos;

import java.util.Comparator;

public class OrdenNombre implements Comparator<Prenda> {
    @Override
    public int compare(Prenda o1, Prenda o2) {
        return o1.nombre.compareToIgnoreCase(o2.nombre);
    }

}