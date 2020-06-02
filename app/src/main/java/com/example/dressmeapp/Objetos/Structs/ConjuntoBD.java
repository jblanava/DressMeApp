package com.example.dressmeapp.Objetos.Structs;

import com.example.dressmeapp.Objetos.Conjunto;

import java.util.Arrays;

public class ConjuntoBD
{
    public int id;
    public int[] prendas = new int[6];
    public int perfil;

    public ConjuntoBD(){}

    public ConjuntoBD(String text, int baseID)
    {
        String[] campos = text.split(";");

        id = Integer.parseInt(campos[0]) + baseID;
        //Esto creo que es así, puede que no
        for(int i =1;i<7;i++)
        prendas[i] = Integer.parseInt(campos[i]);
        perfil = Integer.parseInt(campos[7]);
    }

    public String toString()
    {
        return id + ";" + Arrays.toString(prendas) + ";" + perfil + "\n";
    }
}
