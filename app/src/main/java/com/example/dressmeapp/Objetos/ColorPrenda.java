package com.example.dressmeapp.Objetos;

import android.graphics.Color;

public class ColorPrenda {

    private int id;
    private String nombre;

    private int rojo;
    private int verde;
    private int azul;
    private String hex;
    private int androidColor;

    private ColorPrenda(int id, String nombre, int rojo, int verde, int azul) {
        setIdNombre(id, nombre);
        setRGB(rojo, verde, azul);
    }
    private ColorPrenda(int id, String nombre, int androidColor) {
        this(id, nombre, Color.red(androidColor), Color.green(androidColor), Color.blue(androidColor));
    }
    public ColorPrenda(int id, String nombre, String hex) {
        this(id, nombre, Color.parseColor(hex));
    }

    public void setRojo(int val) {
        rojo = val;
        actualizarHexadecimal();
    }
    public void setVerde(int val) {
        verde = val;
        actualizarHexadecimal();
    }
    public void setAzul(int val) {
        azul = val;
        actualizarHexadecimal();
    }
    private void setRGB(int rojo, int verde, int azul) {
        this.rojo = rojo;
        this.verde = verde;
        this.azul = azul;
        actualizarHexadecimal();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getRojo() { return rojo; }
    public int getVerde() { return verde; }
    public int getAzul() { return azul; }
    public String getHex() { return hex; }
    public int getAndroidColor() { return androidColor; }

    public String toString() {
        return nombre + hex;
    }

    private void setIdNombre(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    private void actualizarHexadecimal() {
        hex = String.format("#%02X%02X%02X", rojo, verde, azul);
        androidColor = Color.parseColor(hex);
    }

    public boolean equals(Object otro) {
        boolean res = false;
        if (otro instanceof ColorPrenda) {
            ColorPrenda otroColor = (ColorPrenda)otro;
            res = id == otroColor.id;
        }
        return res;
    }
    public int hashCode() {
        return id;
    }

}
