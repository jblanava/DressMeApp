package com.example.dressmeapp.Objetos.Structs;

public class ColorBD
{
    public int id;
    public String nombre;
    public String hex;

    public ColorBD(){}

    public ColorBD(String text)
    {
        String[] campos = text.split(";");

        id = Integer.parseInt(campos[0]);
        nombre = campos[1];
        hex = campos[2];
    }

    public String toString()
    {
        return id + ";" + nombre + ";" + hex + "\n";
    }
}
