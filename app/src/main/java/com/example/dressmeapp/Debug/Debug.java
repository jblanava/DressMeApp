package com.example.dressmeapp.Debug;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dressmeapp.BaseDatos.BaseDatos;

public class Debug {

    public static String get_tabla(Context contexto, String tabla, String columnas, String where) {

        if (columnas == null) {
            columnas = "*";
        }
        String sentenciaSQL = "SELECT " + columnas + " FROM " + tabla.trim().toUpperCase();
        if (where != null && where.length() > 0) {
            sentenciaSQL += " WHERE " + where;
        }
        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        StringBuilder sb = new StringBuilder();
        sb.append("TABLA ").append(tabla.trim().toUpperCase()).append(" WHERE ").append(where).append(":\n");



        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);
        if (cursor.moveToFirst()) {
            String[] colNames = cursor.getColumnNames();
            while (!cursor.isAfterLast()) {
                for (String col : colNames) {
                    sb.append("  ").append(col).append(" = ").append(cursor.getString(cursor.getColumnIndex(col)));
                }
                sb.append("\n");
                cursor.moveToNext();
            }
        }
        cursor.close();
        base.close();
        baseDatos.close();
        return sb.toString();

    }

}
