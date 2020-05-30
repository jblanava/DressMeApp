package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GestorBD2 {
    private static Context contexto; // TODO: eliminar en el futuro
    private static String nombreBD = "dressmeapp20.db"; // Antonio ha cambiado a la BD__17

    public GestorBD2(Context context)  // TODO: Eliminar?
    {
        contexto = context;
    }

    public static void crearColor(Context contexto, String nombre, String hex) {
        int id = obtenIDMaximoColor(contexto);
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO COLOR (ID, NOMBRE, HEX) VALUES (" + id + ", '" + nombre + "', '" + hex + "')";

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    public static int obtenIDMaximoColor(Context context) {
        int resultado = 0;
        String sentenciaSQL = "SELECT MAX(ID) AS MAXID FROM COLOR";

        Cursor cursor;
        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);
        if (cursor.moveToFirst()) {
            do {
                resultado = LibreriaBD.CampoInt(cursor, "MAXID");
            } while (cursor.moveToNext());
        }
        resultado++;
        baseDatos.close();
        base.close();
        cursor.close();
        return resultado;
    }

    public static void CrearTalla(Context contexto, String talla) {
        int id = obtenIDMaximoTalla(contexto);
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO TALLA (ID, TALLA) VALUES (";
        sentenciaSQL += id + ",'" + talla.trim() + "')";

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    public static int obtenIDMaximoTalla(Context context) {
        int resultado = 0;
        String sentenciaSQL = "SELECT MAX(ID) AS MAXID FROM TALLA";

        Cursor cursor;
        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);
        if (cursor.moveToFirst()) {
            do {
                resultado = LibreriaBD.CampoInt(cursor, "MAXID");
            } while (cursor.moveToNext());
        }
        resultado++;
        baseDatos.close();
        base.close();
        cursor.close();
        return resultado;
    }
}
