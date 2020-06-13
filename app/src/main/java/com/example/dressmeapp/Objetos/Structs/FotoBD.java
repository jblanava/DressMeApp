package com.example.dressmeapp.Objetos.Structs;

import com.example.dressmeapp.Objetos.Prenda;

public class FotoBD {

    public int id;
    public byte[] datos;

    public FotoBD(){}

    public FotoBD(String text){
        String[] campos = text.split(";");
        datos = new byte[campos.length-1];
        id = Integer.parseInt(campos[0]);
        for(int i=1;i<campos.length;i++){

        }

    }

    public String toString(){
        return null;
    }

}
