package com.example.dressmeapp.Objetos.Structs;

public class ConjuntoBD
{
    public int id;
    public int[] prendas = new int[6];
    public int perfil;
    public int favorito;
    public String nombre;

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

        if(campos.length > 9)
            nombre = campos[9];
        else
            nombre = "Conjunto: " + id;
    }

    public String toString()
    {
        String pren = "";

        for(int p : prendas)
        {
            pren += p + ";";
        }

        return id + ";" + pren + perfil + ";" + favorito + ";" + nombre + "\n";
    }
}
