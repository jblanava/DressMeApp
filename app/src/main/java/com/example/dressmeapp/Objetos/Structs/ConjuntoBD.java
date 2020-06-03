package com.example.dressmeapp.Objetos.Structs;

import com.example.dressmeapp.Objetos.Conjunto;

import java.util.Arrays;

public class ConjuntoBD
{
    public int id;
    public int[] prendas = new int[6];
    public int perfil;
    public int favorito;

    public ConjuntoBD(){}

    public ConjuntoBD(String text)
    {
        String[] campos = text.split(";");

        id = Integer.parseInt(campos[0]);
        //Esto creo que es así, puede que no
        for(int i = 1;i<7;i++)
        prendas[i - 1] = Integer.parseInt(campos[i]);
        perfil = Integer.parseInt(campos[7]);
        favorito = Integer.parseInt(campos[8]);
    }

    public String toString()
    {
        String pren = "";

        for(int p : prendas)
        {
            pren += p + ";";
        }

        return id + ";" + pren + perfil + ";" + favorito + "\n";
    }
}
