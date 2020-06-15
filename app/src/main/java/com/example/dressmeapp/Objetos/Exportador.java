package com.example.dressmeapp.Objetos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.dressmeapp.BaseDatos.ExportarBD;
import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Structs.FotoBD;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Exportador
{
    public Exportador(Context context)
    {
        try
        {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "vestuario.txt");
            FileWriter fw = new FileWriter(file);

            exportar_fotos(context);

            exportar(fw, ExportarBD.expPerfil(context));
            exportar(fw, ExportarBD.expColores(context));
            exportar(fw, ExportarBD.expComboColor(context));
            exportar(fw, ExportarBD.expTallas(context));
            exportar(fw, ExportarBD.expPrendas(context));
            exportar(fw, ExportarBD.expConjuntos(context));

            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void exportar_fotos(Context context) throws IOException
    {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/fotos");
        dir.mkdirs();

        for (FotoBD f : ExportarBD.expFotos(context))
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(f.datos, 0 , f.datos.length);

            String fileName = String.format("foto_%d.jpg", f.id);
            File outFile = new File(dir, fileName);

            FileOutputStream outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

            outStream.flush();
            outStream.close();
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
