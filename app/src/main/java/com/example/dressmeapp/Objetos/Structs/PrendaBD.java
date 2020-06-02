package com.example.dressmeapp.Objetos.Structs;

public class PrendaBD
{
    public int id;
    public String nombre;
    public int color;
    public int tipo;
    public int talla;
    public int visible;
    public int perfil;

    public PrendaBD(){}

    public PrendaBD(String text, int baseID)
    {
        String[] campos = text.split(";");

        id = Integer.parseInt(campos[0]) + baseID;
        nombre = campos[1];
        color = Integer.parseInt(campos[2]);
        tipo= Integer.parseInt(campos[3]);
        talla= Integer.parseInt(campos[4]);
        visible= Integer.parseInt(campos[5]);
        perfil = Integer.parseInt(campos[6]);
    }

    public String toString()
    {
        return id + ";" + nombre + ";" + color + ";"+tipo+";"+talla+";"+visible+";"+perfil+ "\n";
    }
}
