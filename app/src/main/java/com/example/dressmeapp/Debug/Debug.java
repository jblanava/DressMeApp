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

    public static String getListaConjuntos(Context contexto, String nombrebd) {

        String sentenciaSQL = "SELECT * FROM CONJUNTO";
        BaseDatos base = new BaseDatos(contexto, nombrebd);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        StringBuilder sb = new StringBuilder();
        sb.append("LISTA DE CONJUNTOS:\n");

        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                sb.append("ID = ");
                sb.append(cursor.getInt(cursor.getColumnIndex("ID")));

                sb.append("   ID_PERFIL = ");
                sb.append(cursor.getInt(cursor.getColumnIndex("ID_PERFIL")));

                sb.append("   ABRIGO = ");
                sb.append(cursor.getInt(cursor.getColumnIndex("ABRIGO")));

                sb.append("   SUDADERA = ");
                sb.append(cursor.getInt(cursor.getColumnIndex("SUDADERA")));

                sb.append("   CAMISETA = ");
                sb.append(cursor.getInt(cursor.getColumnIndex("CAMISETA")));

                sb.append("   PANTALON = ");
                sb.append(cursor.getInt(cursor.getColumnIndex("ABRIGO")));

                sb.append("   ZAPATO = ");
                sb.append(cursor.getInt(cursor.getColumnIndex("ABRIGO")));

                sb.append("   COMPLEMENTO = ");
                sb.append(cursor.getInt(cursor.getColumnIndex("ABRIGO")));

                sb.append("\n");

                cursor.moveToNext();
            }
        }
        cursor.close();

        return sb.toString();

    }

    public static String getTabla(Context contexto, String nombrebd, String tabla, String where) {

        String sentenciaSQL = "SELECT * FROM " + tabla;
        if (where != null && where.length() > 0) {
            sentenciaSQL += " WHERE " + where;
        }
        BaseDatos base = new BaseDatos(contexto, nombrebd);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        StringBuilder sb = new StringBuilder();
        sb.append("TABLA ").append(tabla).append(" WHERE ").append(where).append(":\n");



        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);
        if (cursor.moveToFirst()) {
            String[] colNames = cursor.getColumnNames();
            while (!cursor.isAfterLast()) {
                for (String col : colNames) {
                    sb.append("  " + col + " = " + cursor.getString(cursor.getColumnIndex(col)));
                }
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
