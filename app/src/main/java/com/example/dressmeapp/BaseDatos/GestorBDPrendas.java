package com.example.dressmeapp.BaseDatos;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dressmeapp.Objetos.ColorPrenda;
import com.example.dressmeapp.Objetos.ComboColorPrenda;
import com.example.dressmeapp.Objetos.Prenda;

import java.util.ArrayList;
import java.util.List;

public class GestorBDPrendas {
    /* Incluye operaciones sobre la BD relacionadas con las prendas y sus atributos*/

    @SuppressLint("DefaultLocale")
    public static int crear_prenda(Context contexto, String nombre, int color, int tipo, int talla, int visible, int id_perfil, int foto) {

        int id = GestorBD.obtener_id_maximo(contexto, "prenda");
        String sentenciaSQL;
        sentenciaSQL = String.format("INSERT INTO PRENDA VALUES (%d, '%s', %d, %d, %d, %d, %d, %d)",
                id, nombre, color, tipo, talla, visible, id_perfil, foto);

        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

        return id;
    }
    public static int crear_prenda(Context contexto, String nombre, int color, int tipo, int talla, int visible, int id_perfil) {
        return crear_prenda(contexto, nombre, color, tipo, talla, visible, id_perfil, 1);
    }

    public static List<Prenda> get_prendas_visibles(Context context, String busqueda, String ordenacion)
    {
        String sentenciaSQL;

        String tabla = ordenacion.toUpperCase().trim();
        sentenciaSQL =  "SELECT PRENDA.ID , PRENDA.NOMBRE \"NOMBRE\", COLOR.NOMBRE \"COLOR\", TIPO.NOMBRE \"TIPO\", TALLA.NOMBRE \"TALLA\" ";
        sentenciaSQL += "FROM PRENDA, COLOR, TIPO, TALLA ";
        sentenciaSQL += "WHERE PRENDA.VISIBLE = 1 AND PRENDA.ID_PERFIL = " + GestorBD.idPerfil + " ";
        sentenciaSQL += "AND COLOR.ID = PRENDA.COLOR AND TIPO.ID = PRENDA.TIPO AND TALLA.ID = PRENDA.TALLA ";
        sentenciaSQL += "AND (UPPER(PRENDA.NOMBRE) LIKE '%" + busqueda.toUpperCase() + "%' ";
        sentenciaSQL += "OR UPPER(COLOR.NOMBRE) LIKE '%" + busqueda.toUpperCase() + "%' ";
        sentenciaSQL += "OR UPPER(TIPO.NOMBRE) LIKE '%" + busqueda.toUpperCase() + "%' ";
        sentenciaSQL += "OR UPPER(TALLA.NOMBRE) LIKE '%" + busqueda.toUpperCase() + "%' )";

        if(ordenacion.equalsIgnoreCase("nombre"))
        {
            sentenciaSQL += "ORDER BY PRENDA.NOMBRE";
        }
        else if(ordenacion.length() != 0)
        {
            sentenciaSQL += "ORDER BY " + tabla + ".NOMBRE";
        }


        Cursor cursor;
        List<Prenda> res = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                int id = LibreriaBD.campo_int(cursor, "ID");
                String nombre = LibreriaBD.campo_string(cursor, "NOMBRE");
                String color = LibreriaBD.campo_string(cursor, "COLOR");
                String tipo = LibreriaBD.campo_string(cursor, "TIPO");
                String talla = LibreriaBD.campo_string(cursor, "TALLA");

                res.add(new Prenda(id, nombre, color, tipo, talla));

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        return res;
    }

    public static void ocultar_prenda(Context contexto, int idPrenda) { // este metodo es para cambiar la visibilidad, pero la prenda se mantiene en la base de datos

        String SentenciaSQL;
        SentenciaSQL = "UPDATE PRENDA SET ";
        SentenciaSQL += "VISIBLE = '0' ";
        SentenciaSQL += "WHERE ID = " + idPrenda;

        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(SentenciaSQL);
        baseDatos.close();
        base.close();
    }
    public static String get_hex(Context context, int idColor) {
        String SentenciaSQL = "SELECT HEX FROM COLOR WHERE ID=" + idColor;
        String res = "";
        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getWritableDatabase();
        Cursor cursor = baseDatos.rawQuery(SentenciaSQL, null);
        if (cursor.moveToFirst()) res = LibreriaBD.campo_string(cursor, "HEX");
        base.close();
        baseDatos.close();
        cursor.close();
        return res;

    }
    public static void borrar_prenda(Context contexto, int id) {
        String SentenciaSQL;

        SentenciaSQL = "SELECT FOTO FROM PRENDA WHERE ID = " + id;

        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();
        Cursor cursor = baseDatos.rawQuery(SentenciaSQL, null);

        int id_foto = -1;

        if (cursor.moveToFirst()) {
            do {

                id_foto = LibreriaBD.campo_int(cursor, "FOTO");

            } while (cursor.moveToNext());
        }

        if(id_foto > 1) GestorBDFotos.eliminar_foto(contexto, id_foto);

        SentenciaSQL = "DELETE FROM PRENDA WHERE ID = " + id;

        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(SentenciaSQL);
        baseDatos.close();
        base.close();
        cursor.close();
    }

    public static void borrar_conjunto(Context context, int idConjunto) {
        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM CONJUNTO WHERE ID = " + idConjunto;
        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    public static List<ColorPrenda> get_colores(Context context) {

        List<ColorPrenda> res = new ArrayList<>();

        String sentenciaSQL = "SELECT * FROM COLOR";
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                int id = LibreriaBD.campo_int(cursor, "ID");
                String nombre = LibreriaBD.campo_string(cursor, "NOMBRE");
                String hex = LibreriaBD.campo_string(cursor, "HEX");

                res.add(new ColorPrenda(id, nombre, hex));

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        return res;

    }

    public static int crear_talla(Context contexto, String talla) {
        int id = GestorBD.obtener_id_maximo(contexto, "TALLA");
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO TALLA (ID, NOMBRE) VALUES (";
        sentenciaSQL += id + ",'" + talla.trim() + "')";

        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

        return id;
    }

    public static Prenda get_prenda(Context context, int id) {


        String sentenciaSQL =  "SELECT PRENDA.NOMBRE \"NOMBRE\", COLOR.NOMBRE \"COLOR\", TIPO.NOMBRE \"TIPO\", TALLA.NOMBRE \"TALLA\" ";
        sentenciaSQL += "FROM PRENDA, COLOR, TIPO, TALLA ";
        sentenciaSQL += "WHERE PRENDA.ID = " + id + " ";
        sentenciaSQL += "AND COLOR.ID = PRENDA.COLOR AND TIPO.ID = PRENDA.TIPO AND TALLA.ID = PRENDA.TALLA ";

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);
        Prenda p = null;

        if (cursor.moveToFirst()) {

            String nombre = LibreriaBD.campo_string(cursor, "NOMBRE");
            String  color = LibreriaBD.campo_string(cursor, "COLOR");
            String tipo = LibreriaBD.campo_string(cursor, "TIPO");
            String talla = LibreriaBD.campo_string(cursor, "TALLA");


            p = new Prenda(id, nombre, color, tipo, talla);

        }
        baseDatos.close();
        base.close();
        cursor.close();
        return p;
    }

    public static int crear_color(Context contexto, String nombre, String hex) {
        int id = GestorBD.obtener_id_maximo(contexto, "color");
        int id_combo_reflexivo = GestorBD.obtener_id_maximo(contexto, "combo_color");
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO COLOR (ID, NOMBRE, HEX) VALUES (" + id + ", '" + nombre + "', '" + hex + "')";

        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);

        // Esto es para asegurarnos de que la relación COMBO_COLOR sea reflexiva
        baseDatos.execSQL("INSERT INTO COMBO_COLOR (ID, COLOR1, COLOR2)" +
                "VALUES (" + id_combo_reflexivo + ", " + id + ", " + id + ")");

        baseDatos.close();
        base.close();
        return id;
    }

    public static boolean crear_combo_color(Context contexto, int color1, int color2){

        boolean repetido = false;

        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
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

    public static List<ComboColorPrenda> get_combos_colores(Context contexto) {

        List<ComboColorPrenda> lista = new ArrayList<>();

        String sentenciaSQL =  "SELECT CC.ID AS COMBOID, C1.ID AS ID1, C1.NOMBRE AS NOMBRE1, C1.HEX AS HEX1," +
                "C2.ID AS ID2, C2.NOMBRE AS NOMBRE2, C2.HEX AS HEX2 ";
        sentenciaSQL += "FROM COLOR AS C1, COLOR AS C2, COMBO_COLOR AS CC ";
        sentenciaSQL += "WHERE C1.ID = CC.COLOR1 AND C2.ID = CC.COLOR2 AND C1.ID <= C2.ID ";
        sentenciaSQL += "ORDER BY C1.NOMBRE, C2.NOMBRE";

        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
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

    public static List<Integer> color_con_cuales_combina(Context context, int idColor)
    {
        String sentenciaSQL = "SELECT COLOR2 FROM COMBO_COLOR WHERE COLOR1 =  " +  idColor;

        Cursor cursor;
        List<Integer> colores = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                int combo = LibreriaBD.campo_int(cursor, "COLOR2");

                colores .add(combo);

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();

        return colores;
    }
}
