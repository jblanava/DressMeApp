package com.example.dressmeapp.Objetos;

import android.content.Context;
import android.util.Log;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBD2;
import com.example.dressmeapp.Objetos.Structs.ColorBD;
import com.example.dressmeapp.Objetos.Structs.ComboColorBD;
import com.example.dressmeapp.Objetos.Structs.ConjuntoBD;
import com.example.dressmeapp.Objetos.Structs.PerfilBD;
import com.example.dressmeapp.Objetos.Structs.PrendaBD;
import com.example.dressmeapp.Objetos.Structs.TallaBD;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<Integer, Integer> Mapaperfiles = new HashMap<>();
        Map<Integer, Integer> Mapacolores = new HashMap<>();
        Map<Integer, Integer> Mapatallas = new HashMap<>();
        Map<Integer, Integer> Mapaprendas = new HashMap<>();


        List<String> prefiles = importar("perfiles.txt");
        List<String> colores = importar("colores.txt");
        List<String> combos = importar("combo_colores.txt");
        List<String> tallas = importar("tallas.txt");
        List<String> prendas = importar("prendas.txt");
        List<String> conjuntos = importar("conjuntos.txt");


        for(String s : prefiles)
        {
            PerfilBD p = new PerfilBD(s);
            int nuevoId = GestorBD.CrearPerfil(context, p.usuario, p.clave);

            Mapaperfiles.put(p.id, nuevoId);
        }

        for(String s : colores)
        {
            ColorBD c = new ColorBD(s);

            if(c.id <= 12) continue; // Los primeros 12 son los que vienen por defecto y me los salto

            int nuevoId = GestorBD2.crearColor(context, c.nombre, c.hex);

            Mapacolores.put(c.id, nuevoId);
        }

        for(String s : combos)
        {
            ComboColorBD c = new ComboColorBD(s);
            GestorBD2.crearComboColor(context, c.color1, c.color2);
        }

        for(String s : tallas)
        {
            TallaBD t = new TallaBD(s);

            if(t.id <= 6) continue; // Las primeras 6 son las que vienen por defecto y me las salto

            int nuevoId = GestorBD.CrearTalla(context, t.nombre);

            Mapatallas.put(t.id, nuevoId);
        }


        for(String s : prendas)
        {
            PrendaBD p = new PrendaBD(s);

            int nuevoId = GestorBD.crearPrenda(context, p.nombre, Mapacolores.get(p.color), p.tipo, Mapatallas.get(p.talla), p.visible, Mapaperfiles.get(p.perfil));

            Mapaprendas.put(p.id, nuevoId);
        }

        for(String s : conjuntos)
        {
            ConjuntoBD p = new ConjuntoBD(s);
            Conjunto c = new Conjunto();

            for(int i : p.prendas)
            {
                c.add(Mapaprendas.get(i));
            }

            GestorBD.addConjunto(context, c);
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
