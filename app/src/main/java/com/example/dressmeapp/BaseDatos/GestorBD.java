package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GestorBD {

    /**
     * El ID del perfil que tiene la sesión abierta actualmente.
     */
    public static int idPerfil;

    public static int obtener_id_maximo(Context context, String tabla)
    {
        int resultado = 0;
        String sentenciaSQL = "SELECT MAX(ID) AS MAXID FROM " + tabla.trim().toUpperCase();

        Cursor cursor;
        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);
        if (cursor.moveToFirst()) {
            do {
                resultado = LibreriaBD.campo_int(cursor, "MAXID");
            } while (cursor.moveToNext());
        }
        resultado++;
        baseDatos.close();
        base.close();
        cursor.close();
        return resultado;
    }


    public static int get_foto_de_prenda(Context contexto, int idPrenda) {
        String sentenciaSQL =  "SELECT FOTO FROM PRENDA WHERE ID=" + idPrenda;
        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);

        int res = 1;
        if (cursor.moveToFirst()) {
            res = cursor.getInt(cursor.getColumnIndex("FOTO"));
        }

        cursor.close();
        baseDatos.close();
        base.close();

        return res;
    }


    public static List<String> get_nombres_tabla(Context context, String tabla)
    {
        String sentenciaSQL = "SELECT NOMBRE FROM " + tabla.toUpperCase();

        Cursor cursor;
        List<String> res = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                String nombre = LibreriaBD.campo_string(cursor, "NOMBRE");

                res.add(nombre);

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        return res;
    }

    public static int get_id_tabla(Context context, String tabla, String nombre)
    {
        String sentenciaSQL = String.format("SELECT ID FROM %s WHERE UPPER(NOMBRE) LIKE '%s'", tabla.toUpperCase(), nombre.toUpperCase());
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();
        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        int id = -1;

        if (cursor.moveToFirst()) {
            id = LibreriaBD.campo_int(cursor, "ID");
        }

        baseDatos.close();
        base.close();
        cursor.close();
        return id;
    }

    public static List<Integer> get_ids_tabla(Context context, String tabla)
    {
        String sentenciaSQL = "SELECT ID FROM " + tabla.toUpperCase();

        Cursor cursor;
        List<Integer> res = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                int id = LibreriaBD.campo_int(cursor, "ID");

                res.add(id);

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        return res;
    }



} // Fin de clase