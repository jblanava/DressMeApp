package com.example.dressmeapp.Objetos;

import com.example.dressmeapp.BaseDatos.GestorBD;

import java.util.ArrayList;
import java.util.List;

public class Conjunto {

    private List<Integer> idPrendas;
    private String nombre_conjunto;
    int idPerfil;


    public Conjunto(String nombre_conjunto){
        idPerfil = GestorBD.idPerfil;
        idPrendas = new ArrayList<>();
        this.nombre_conjunto = nombre_conjunto;
    }
    public Conjunto() {
        this("");
    }

    /*
    Significado de posiciones del ArrayList:

    pos 0 -> ID conjunto
    pos 1 -> ID Abrigo
    pos 2 -> ID Sudadera
    pos 3 -> ID Camiseta
    pos 4 -> ID Pantalon
    pos 5 -> ID Zapatos
    pos 6 -> ID Complementos

    un valor de -1 significa que ese conjunto no tiene una prenda de ese tipo

     */

    public void add(int idPrenda){
        if(idPrendas.size()<=6){
            idPrendas.add(idPrenda);
        }
    }
    public boolean estaId(int id){
        return idPrendas.contains(id);
    }
    public int getSize(){
        return idPrendas.size();
    }
    public int obtenId(int pos){
        return this.idPrendas.get(pos);
    }
    public void remove(int idPrenda){
        idPrendas.remove(idPrenda);
    }
    public List<Integer> getPrendas(){
        return this.idPrendas;
    }
    public String getNombre_conjunto() { return nombre_conjunto; }

}
