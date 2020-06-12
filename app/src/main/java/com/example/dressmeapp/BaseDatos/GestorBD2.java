package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dressmeapp.Objetos.ColorPrenda;
import com.example.dressmeapp.Objetos.ComboColorPrenda;

import java.util.ArrayList;
import java.util.List;

public class GestorBD2 {
    private static String nombreBD = "dressmeapp26.db"; // Antonio V. ha cambiado a la BD__17
                                                        // Antonio ha cambiado a BD 26

    public static int crearColor(Context contexto, String nombre, String hex) {
        int id = GestorBD.obtener_id_maximo(contexto, "color");
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

    /**
     * Crea una nuevo par de colores que el algoritmo vestidor considera que
     * combinan bien.
     * @param contexto El contexto a usar.
     * @param color1 El primer color.
     * @param color2 El segundo color.
     * @return True si ha habido éxito (no se repite en la BD)
     */
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

            int id = GestorBD.obtener_id_maximo(contexto, "combo_color");
            String sentenciaSQL = "INSERT INTO COMBO_COLOR (ID, COLOR1, COLOR2) VALUES (" +id+", " + color1+","+ color2+")";
            baseDatosEscribir.execSQL(sentenciaSQL);

            id = GestorBD.obtener_id_maximo(contexto, "combo_color");
            sentenciaSQL = "INSERT INTO COMBO_COLOR (ID, COLOR1, COLOR2) VALUES (" +id+", " + color2+","+ color1+")";
            baseDatosEscribir.execSQL(sentenciaSQL);

            baseDatosEscribir.close();

        }

        base.close();

        return !repetido;

    }

    public static List<ComboColorPrenda> getCombosColores(Context contexto) {

        List<ComboColorPrenda> lista = new ArrayList<>();

        String sentenciaSQL =  "SELECT CC.ID AS COMBOID, C1.ID AS ID1, C1.NOMBRE AS NOMBRE1, C1.HEX AS HEX1," +
                "C2.ID AS ID2, C2.NOMBRE AS NOMBRE2, C2.HEX AS HEX2 ";
        sentenciaSQL += "FROM COLOR AS C1, COLOR AS C2, COMBO_COLOR AS CC ";
        sentenciaSQL += "WHERE C1.ID = CC.COLOR1 AND C2.ID = CC.COLOR2 AND C1.ID <= C2.ID ";
        sentenciaSQL += "ORDER BY C1.NOMBRE, C2.NOMBRE";

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                int comboid = cursor.getInt(cursor.getColumnIndex("COMBOID"));

                int id1 = cursor.getInt(cursor.getColumnIndex("ID1"));
                String nombre1 = cursor.getString(cursor.getColumnIndex("NOMBRE1"));
                String hex1 = cursor.getString(cursor.getColumnIndex("HEX1"));

                int id2 = cursor.getInt(cursor.getColumnIndex("ID2"));
                String nombre2 = cursor.getString(cursor.getColumnIndex("NOMBRE2"));
                String hex2 = cursor.getString(cursor.getColumnIndex("HEX2"));

                ColorPrenda col1 = new ColorPrenda(id1, nombre1, hex1);
                ColorPrenda col2 = new ColorPrenda(id2, nombre2, hex2);

                lista.add(new ComboColorPrenda(comboid, col1, col2));

                cursor.moveToNext();

            }
        }

        baseDatos.close();
        base.close();
        cursor.close();
        return lista;

    }

}
