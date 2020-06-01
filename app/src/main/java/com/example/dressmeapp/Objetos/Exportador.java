package com.example.dressmeapp.Objetos;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Structs.ColorBD;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class Exportador
{

    Context context;

    public  Exportador(Context context)
    {
        this.context = context;

        exportar(GestorBD.expColores(context), "perfiles.txt");
        exportar(GestorBD.expColores(context), "colores.txt");
        exportar(GestorBD.expColores(context), "combo_colores.txt");
        exportar(GestorBD.expColores(context), "tallas.txt");
        exportar(GestorBD.expColores(context), "prendas.txt");
    }

    public void exportar(List<?> objetos, String archivo)
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(archivo, Context.MODE_PRIVATE));

            for (Object o : objetos)
            {
                outputStreamWriter.write(o.toString());
            }

            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
