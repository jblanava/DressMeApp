package com.example.dressmeapp.Objetos.Structs;

public class TallaBD
{
    public int id;
    public String nombre;

    public TallaBD(){}

    public TallaBD(String text)
    {
        String[] campos = text.split(";");

        id = Integer.parseInt(campos[0]);
        nombre = campos[1];

    }

    public String toString()
    {
        return id + ";" + nombre + "\n";
    }
}
