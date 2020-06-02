package com.example.dressmeapp.Objetos.Structs;

public class PerfilBD
{
    public int id;
    public String usuario;
    public String clave;

    public PerfilBD(){}

    public PerfilBD(String text, int baseID)
    {
        String[] campos = text.split(";");

        id = Integer.parseInt(campos[0]) + baseID;
        usuario = campos[1];
        clave = campos[2];

    }

    public String toString()
    {
        return id + ";" + usuario + ";" + clave + "\n";
    }
}
