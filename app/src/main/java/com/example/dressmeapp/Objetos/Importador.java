package com.example.dressmeapp.Objetos;

import android.content.Context;
import android.os.Environment;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Importador
{
    public  Importador(Context context)
    {
        try
        {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "vestuario.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));

            Map<Integer, Integer> Mapaperfiles = new HashMap<>();
            Map<Integer, Integer> Mapacolores = new HashMap<>();
            Map<Integer, Integer> Mapatallas = new HashMap<>();
            Map<Integer, Integer> Mapaprendas = new HashMap<>();

            List<String> prefiles = importar(br);
            List<String> colores = importar(br);
            List<String> combos = importar(br);
            List<String> tallas = importar(br);
            List<String> prendas = importar(br);
            List<String> conjuntos = importar(br);

            for(String s : prefiles)
            {
                PerfilBD p = new PerfilBD(s);
                int nuevoId = GestorBD.CrearPerfil(context, p.usuario, p.clave);

                Mapaperfiles.put(p.id, nuevoId);
            }

            for(String s : colores)
            {
                ColorBD c = new ColorBD(s);

                if(c.id <= 12)
                {
                    Mapacolores.put(c.id, c.id);
                    continue; // Los primeros 12 son los que vienen por defecto y me los salto
                }

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

                if(t.id <= 6)
                {
                    Mapatallas.put(t.id, t.id);
                    continue; // Las primeras 6 son las que vienen por defecto y me las salto
                }

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

                GestorBD.addConjunto(context, c, p.favorito);
            }


        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> importar(BufferedReader br) throws IOException {

        List<String> res = new ArrayList<>();

        String receiveString = "";

        while ((receiveString = br.readLine()) != null)
        {
            if(receiveString.length() == 0) return  res;
            res.add(receiveString);
        }

        return res;
    }
}
