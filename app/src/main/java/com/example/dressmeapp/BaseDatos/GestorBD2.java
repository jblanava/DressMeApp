package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GestorBD2 {
    private static String nombreBD = "dressmeapp25.db"; // Antonio ha cambiado a la BD__17

    public static int crearColor(Context contexto, String nombre, String hex) {
        int id = obtenIDMaximoColor(contexto);
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO COLOR (ID, NOMBRE, HEX) VALUES (" + id + ", '" + nombre + "', '" + hex + "')";

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
        return id;
    }
    public static void crearComboColor(Context contexto, int color1, int color2){
        int id=obtenIDMaximoComboColor(contexto);
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO COMBO_COLOR (ID, COLOR1, COLOR2) VALUES (" +id+", " + color1+","+ color2+")";

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

    private static int obtenIDMaximoComboColor(Context contexto){
        int resultado = 0;
        String sentenciaSQL = "SELECT MAX(ID) AS MAXID FROM COMBO_COLOR";

        Cursor cursor;
        BaseDatos base = new BaseDatos(contexto, nombreBD);
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
