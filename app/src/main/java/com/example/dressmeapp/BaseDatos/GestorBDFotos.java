package com.example.dressmeapp.BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.dressmeapp.R;

public class GestorBDFotos {
    /* Operaciones relacionadas con la base de datos y el manejo de la tabla FOTOS */

    public static int fotoActual = 1;

    public static int guardar_foto(Context context, byte[] img){

        int id = GestorBD.obtener_id_maximo(context, "FOTOS");

        BaseDatos bdh = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase bd;
        bd = bdh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("FOTO", img);
        cv.put("ID", id);
        bd.insert("FOTOS",null, cv);

        bd.close();
        bdh.close();

        return id;

    }

    public static void eliminar_foto(Context context, int id){
        String vsql = "DELETE FROM FOTOS WHERE ID = " + id;

        BaseDatos bdh = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase bd;
        bd = bdh.getWritableDatabase();
        bd.execSQL(vsql);
        bd.close();
        bdh.close();
    }


    public static void cargar_foto(Context context, int id_modificar, ImageView imagen){
        String vsql = "SELECT * FROM FOTOS WHERE ID = " + id_modificar;

        BaseDatos bdh =  new BaseDatos(context, BaseDatos.nombreBD);

        SQLiteDatabase bd = bdh.getReadableDatabase();
        Cursor rs = bd.rawQuery(vsql,null);

        if(rs.moveToNext())
        {
            byte[] image = rs.getBlob(1);
            Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
            try{

                imagen.setImageBitmap(bmp);


            }catch (Exception e){
                imagen.setImageResource(R.drawable.logologo);
            }


        } else {
            imagen.setImageResource(R.drawable.logologo);

        }

        rs.close();
        bd.close();
    }

    public static Bitmap get_foto(Context context, int id_modificar){
        String vsql = "SELECT * FROM FOTOS WHERE ID = " + id_modificar;

        BaseDatos bdh =  new BaseDatos(context, BaseDatos.nombreBD);

        SQLiteDatabase bd = bdh.getReadableDatabase();
        Cursor rs = bd.rawQuery(vsql,null);

        Bitmap bmp = null;

        if(rs.moveToNext())
        {
            byte[] image = rs.getBlob(1);
            bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);

        }

        rs.close();
        bd.close();

        return bmp;
    }
}
