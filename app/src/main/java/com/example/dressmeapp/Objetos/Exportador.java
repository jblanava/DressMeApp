package com.example.dressmeapp.Objetos;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBD2;
import com.example.dressmeapp.Objetos.Structs.ColorBD;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class Exportador
{

    Context context;

    public  Exportador(Context context)
    {
        this.context = context;

        // TODO cuando se creen las funciones correspondientes substituir aqui
        exportar(GestorBD.expPerfiles(context), "perfiles.txt");
        exportar(GestorBD.expColores(context), "colores.txt");
        exportar(GestorBD.expComboColor(context), "combo_colores.txt");
        exportar(GestorBD.expTallas(context), "tallas.txt");
        exportar(GestorBD.expPrendas(context), "prendas.txt");
        exportar(GestorBD.expConjuntos(context), "conjuntos.txt");
    }

    public void exportar(List<?> objetos, String archivo)
    {
        try {

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), archivo);
            FileWriter myWriter = new FileWriter(file);
            for (Object o : objetos)
            {
                myWriter.write(o.toString());
            }

            myWriter.close();
            Log.i("Exportador", "Creado fichero " + archivo);
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /// VERSION CON UN SOLO ARCHIVO

    public void constructor2()
    {
        try
        {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "vestuario.txt");
            FileWriter fw = new FileWriter(file);

            exportar2(fw, GestorBD.expPerfiles(context));
            exportar2(fw, GestorBD.expColores(context));
            exportar2(fw, GestorBD.expComboColor(context));
            exportar2(fw, GestorBD.expTallas(context));
            exportar2(fw, GestorBD.expPrendas(context));
            exportar2(fw, GestorBD.expConjuntos(context));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void exportar2(FileWriter fw, List<?> objects) throws IOException
    {
        for (Object o : objects)
        {
            fw.write(o.toString());
        }

        fw.write("\n");
    }

}
