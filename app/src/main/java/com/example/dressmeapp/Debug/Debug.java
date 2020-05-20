package com.example.dressmeapp.Debug;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dressmeapp.BaseDatos.BaseDatos;
import com.example.dressmeapp.BaseDatos.LibreriaBD;

import java.sql.ResultSet;

public class Debug {

    public static String getListaPerfiles(Context contexto, String nombrebd) {

        String sentenciaSQL = "SELECT * FROM PERFIL";
        BaseDatos base = new BaseDatos(contexto, nombrebd);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        StringBuilder sb = new StringBuilder();
        sb.append("LISTA DE PERFILES:\n");

        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                sb.append("ID = ");
                sb.append(cursor.getInt(cursor.getColumnIndex("ID")));

                sb.append("   NOMBRE = ");
                sb.append(cursor.getString(cursor.getColumnIndex("NOMBRE")));

                sb.append("   CONTRASENIA = ");
                sb.append(cursor.getString(cursor.getColumnIndex("CONTRASENIA")));
                sb.append("\n");

                cursor.moveToNext();
            }
        }
        cursor.close();

        return sb.toString();

    }

    public static void borrarTodosLosPerfiles(Context contexto, String nombrebd) {

        String sentenciaSQL = "DELETE FROM PERFIL";
        BaseDatos base = new BaseDatos(contexto, nombrebd);
        SQLiteDatabase baseDatos = base.getWritableDatabase();

        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

    }

}
