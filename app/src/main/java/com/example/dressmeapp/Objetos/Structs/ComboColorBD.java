package com.example.dressmeapp.Objetos.Structs;

public class ComboColorBD
{
    public int id;
    public int color2;
    public int color1;

    public ComboColorBD(){}

    public ComboColorBD(String text, int baseID)
    {
        String[] campos = text.split(";");

        id = Integer.parseInt(campos[0]) + baseID;
        color2 = Integer.parseInt(campos[1]);
        color1 = Integer.parseInt(campos[2]);
    }

    public String toString()
    {
        return id + ";" + color2 + ";" + color1 + "\n";
    }
}
