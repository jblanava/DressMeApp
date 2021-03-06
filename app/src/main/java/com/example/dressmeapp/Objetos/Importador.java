package com.example.dressmeapp.Objetos;

import android.content.Context;
import android.os.Environment;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDAlgoritmo;
import com.example.dressmeapp.BaseDatos.GestorBDFotos;
import com.example.dressmeapp.BaseDatos.GestorBDPerfil;
import com.example.dressmeapp.BaseDatos.GestorBDPrendas;
import com.example.dressmeapp.Objetos.Structs.ColorBD;
import com.example.dressmeapp.Objetos.Structs.ComboColorBD;
import com.example.dressmeapp.Objetos.Structs.ConjuntoBD;
import com.example.dressmeapp.Objetos.Structs.PerfilBD;
import com.example.dressmeapp.Objetos.Structs.PrendaBD;
import com.example.dressmeapp.Objetos.Structs.TallaBD;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

            Map<Integer, Integer> mapa_perfiles = new HashMap<>();
            Map<Integer, Integer> mapa_colores = new HashMap<>();
            Map<Integer, Integer> mapa_tallas = new HashMap<>();
            Map<Integer, Integer> mapa_fotos = new HashMap<>();
            Map<Integer, Integer> mapa_prendas = new HashMap<>();

            List<String> prefiles = importar(br);
            List<String> colores = importar(br);
            List<String> combos = importar(br);
            List<String> tallas = importar(br);
            List<String> prendas = importar(br);
            List<String> conjuntos = importar(br);


            importar_fotos(context, mapa_fotos);

            for(String s : prefiles)
            {
                PerfilBD p = new PerfilBD(s);
                int nuevoId = GestorBDPerfil.crear_perfil(context, p.usuario, p.clave);

                mapa_perfiles.put(p.id, nuevoId);
            }

            for(String s : colores)
            {
                ColorBD c = new ColorBD(s);

                int nueva_posicion = GestorBD.get_id_tabla(context, "color", c.nombre);

                if (nueva_posicion == -1)
                {
                    nueva_posicion = GestorBDPrendas.crear_color(context, c.nombre, c.hex);
                }
                else if(!c.hex.equals(GestorBDPrendas.get_hex(context, nueva_posicion))) // si existe pero no tienen el mismo hex tambien lo creamos
                {
                    nueva_posicion = GestorBDPrendas.crear_color(context, c.nombre + "_" + c.hex, c.hex);
                }

                mapa_tallas.put(c.id, nueva_posicion);
            }

            for(String s : combos)
            {
                ComboColorBD c = new ComboColorBD(s);
                GestorBDPrendas.crear_combo_color(context, c.color1, c.color2);
            }

            for(String s : tallas)
            {
                TallaBD t = new TallaBD(s);

                int nueva_posicion = GestorBD.get_id_tabla(context, "talla", t.nombre);

                if (nueva_posicion == -1)   // si no existe lo creamos
                {
                    nueva_posicion = GestorBDPrendas.crear_talla(context, t.nombre);
                }

                mapa_tallas.put(t.id, nueva_posicion);
            }


            for(String s : prendas)
            {
                PrendaBD p = new PrendaBD(s);


                int nuevoId = GestorBDPrendas.crear_prenda(context, p.nombre, mapa_colores.get(p.color), p.tipo,
                        mapa_tallas.get(p.talla), p.visible, mapa_perfiles.get(p.perfil), mapa_fotos.get(p.foto));

                mapa_prendas.put(p.id, nuevoId);
            }

            for(String s : conjuntos)
            {
                ConjuntoBD p = new ConjuntoBD(s);
                Conjunto c = new Conjunto(p.nombre);

                for(int i : p.prendas)
                {
                    if(i == -1) c.add(-1);
                    else c.add(mapa_prendas.get(i));
                }

                GestorBDAlgoritmo.add_conjunto(context, c, p.favorito);
            }


        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importar_fotos(Context context, Map<Integer, Integer> Mapafotos) throws IOException {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/fotos");
        dir.mkdirs();

        for(File file : dir.listFiles())
        {
            byte[] fileData = new byte[(int) file.length()];
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            dis.readFully(fileData);
            dis.close();

            int id = Integer.parseInt(file.getName().split("[_.]")[1]);

            Mapafotos.put(id, GestorBDFotos.guardar_foto(context, fileData));
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
