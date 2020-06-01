package com.example.dressmeapp.Objetos;

import android.content.Context;
import android.util.Log;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBD2;
import com.example.dressmeapp.Objetos.Structs.ColorBD;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Importador
{

    Context context;

    public  Importador(Context context)
    {
        this.context = context;

        /*
        Mi idea es tener un Map<int,int> que relacione los antiguos ID con los nuevos, para ello necesito que cada vez
        que se meta un color, talla, prenda en la base de datos, me devuelva el nuevo ID asociado. Cada vez que quiera meter
        algo en la base de datos consultaré en el mapa cual es el nuevo ID, puedo hacer esto porque tengo el ID original
        y estoy siguiendo un orden (antes de meter las prendas tengo que haber metido los perfiles, los colores y las talla
        porque sino los mapas estarán vacios y no sabré que ID ponerle en esos campos)
         */

        List<String> prefiles = importar("perfiles.txt");
        List<String> colores = importar("colores.txt");
        List<String> combos = importar("combo_colores.txt");
        List<String> tallas = importar("tallas.txt");
        List<String> prendas = importar("prendas.txt");

        int baseID = 0; // Todo: GestorBD.get_id_maximo_tabla(context, "color");
        for(String s : colores)
        {
            ColorBD c = new ColorBD(s, baseID);
            GestorBD2.crearColor(context, c.nombre, c.hex);
        }


    }

    public List<String> importar(String archivo) {

        List<String> res = new ArrayList<>();

        try {
            InputStream inputStream = context.openFileInput(archivo);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ((receiveString = bufferedReader.readLine()) != null) {
                    res.add(receiveString);
                }

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return res;
    }
}
