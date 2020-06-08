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
    public Exportador(Context context)
    {
        try
        {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "vestuario.txt");
            FileWriter fw = new FileWriter(file);

            exportar(fw, GestorBD.expPerfil(context));
            exportar(fw, GestorBD.expColores(context));
            exportar(fw, GestorBD.expComboColor(context));
            exportar(fw, GestorBD.expTallas(context));
            exportar(fw, GestorBD.expPrendas(context));
            exportar(fw, GestorBD.expConjuntos(context));

            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void exportar(FileWriter fw, List<?> objects) throws IOException
    {
        for (Object o : objects)
        {
            fw.write(o.toString());
        }

        fw.write("\n");
    }

}
