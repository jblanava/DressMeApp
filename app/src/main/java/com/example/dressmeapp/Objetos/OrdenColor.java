package com.example.dressmeapp.Objetos;

import java.util.Comparator;

public class OrdenColor implements Comparator<Prenda> {
    @Override
    public int compare(Prenda o1, Prenda o2) {
        return o1.color.compareToIgnoreCase(o2.color);
    }

}