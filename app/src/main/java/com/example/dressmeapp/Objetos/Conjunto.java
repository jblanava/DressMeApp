package com.example.dressmeapp.Objetos;

import java.util.ArrayList;
import java.util.List;

public class Conjunto {
    private List<Integer> idPrendas;

    public Conjunto(){
        idPrendas= new ArrayList<Integer>();
    }

    public void add(int idPrenda){
        if(idPrendas.size()<6){
            idPrendas.add(idPrenda);
        }
    }
    public boolean estaId(int id){
        if(idPrendas.contains(id)){
            return true;
        }
        else{
            return false;
        }
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


}
