package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GestorBD2 {
    private static String nombreBD = "dressmeapp26.db"; // Antonio V. ha cambiado a la BD__17
                                                        // Antonio ha cambiado a BD 26

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
    public static boolean crearComboColor(Context contexto, int color1, int color2){

        boolean repetido = false;

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatosLeer = base.getReadableDatabase();

        // Comprobar si se repite
        Cursor curRepetido = baseDatosLeer.rawQuery(
                "SELECT COUNT(*) FROM COMBO_COLOR WHERE COLOR1=" + color1 + " AND COLOR2=" + color2, null);
        if (curRepetido.moveToFirst()) {
            if (curRepetido.getInt(0) > 0) {
                repetido = true;
            }
        }
        curRepetido.close();
        baseDatosLeer.close();

        // Si no se repite, añadir el color
        if (!repetido) {

            SQLiteDatabase baseDatosEscribir = base.getReadableDatabase();

            // Si ColorA combina con ColorB entonces ColorB debe combinar con ColorA

            int id = obtenIDMaximoComboColor(contexto);
            String sentenciaSQL = "INSERT INTO COMBO_COLOR (ID, COLOR1, COLOR2) VALUES (" +id+", " + color1+","+ color2+")";
            baseDatosEscribir.execSQL(sentenciaSQL);

            id = obtenIDMaximoComboColor(contexto);
            sentenciaSQL = "INSERT INTO COMBO_COLOR (ID, COLOR1, COLOR2) VALUES (" +id+", " + color2+","+ color1+")";
            baseDatosEscribir.execSQL(sentenciaSQL);

            baseDatosEscribir.close();

        }

        base.close();

        return !repetido;

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
