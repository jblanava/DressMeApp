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

}
