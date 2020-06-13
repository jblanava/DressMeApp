package com.example.dressmeapp.BaseDatos;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.example.dressmeapp.Objetos.ColorPrenda;
import com.example.dressmeapp.Objetos.ComboColorPrenda;
import com.example.dressmeapp.Objetos.Prenda;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

public class GestorBDPrendas {
    /* Incluye operaciones sobre la BD relacionadas con las prendas y sus atributos*/

    /**
     * El ID del perfil que tiene la sesión abierta actualmente.
     */
    public static int idPerfil;

    private static String nombreBD = "dressmeapp26.db";

    public static void seleccionarBD(String nombreBD) {
        GestorBDPrendas.nombreBD = nombreBD;
    }

    public static int getIdPerfil() {
        return idPerfil;
    }

    public static void setIdPerfil(int nuevoId) {
        idPerfil = nuevoId;
    }
    /**
     * Crea una nueva prenda.
     *
     * @param contexto  El contexto a usar.
     * @param nombre    El nombre de la prenda.
     * @param color     El color de la prenda.
     * @param tipo      Qué tipo de prenda es.
     * @param talla     La talla de la prenda.
     * @param visible   Si la prenda será visible o no en la lista de prendas
     *                  (siempre lo será en el historial)
     * @param id_perfil El ID del perfil que tendrá la prenda.
     */


         @SuppressLint("DefaultLocale")
         public static int crearPrenda(Context contexto, String nombre, int color, int tipo, int talla, int visible, int id_perfil,int foto) {
        int id = GestorBD.obtener_id_maximo(contexto, "prenda");
        String sentenciaSQL;
        sentenciaSQL = String.format("INSERT INTO PRENDA VALUES (%d, '%s', '%d', %d, %d, %d, %d, %d)", id, nombre, color, tipo, talla, visible, id_perfil,foto);

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

        return id;
    }
    public static int crearPrenda(Context contexto, String nombre, int color, int tipo, int talla, int visible, int id_perfil) {
        return crearPrenda(contexto, nombre, color, tipo, talla, visible, id_perfil, 1);
    }

    public static List<Prenda> PrendasVisibles(Context context, String busqueda, String ordenacion)
    {
        String sentenciaSQL;

        String tabla = ordenacion.toUpperCase().trim();
        sentenciaSQL =  "SELECT PRENDA.ID , PRENDA.NOMBRE \"NOMBRE\", COLOR.NOMBRE \"COLOR\", TIPO.NOMBRE \"TIPO\", TALLA.NOMBRE \"TALLA\" ";
        sentenciaSQL += "FROM PRENDA, COLOR, TIPO, TALLA ";
        sentenciaSQL += "WHERE PRENDA.VISIBLE = 1 AND PRENDA.ID_PERFIL = " + idPerfil + " ";
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

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                int id = LibreriaBD.CampoInt(cursor, "ID");
                String nombre = LibreriaBD.Campo(cursor, "NOMBRE");
                String color = LibreriaBD.Campo(cursor, "COLOR");
                String tipo = LibreriaBD.Campo(cursor, "TIPO");
                String talla = LibreriaBD.Campo(cursor, "TALLA");

                res.add(new Prenda(id, nombre, color, tipo, talla));

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        return res;
    }

    /**
     * Pone el flag "visible" a 0 en una prenda, ocultándola en la lista de prendas y en
     * el algoritmo vestidor pero no en el historial.
     *
     * @param contexto El contexto a usar.
     * @param idPrenda El ID de la prenda a borrar.
     */
    public static void CambiarVisibilidadPrenda(Context contexto, int idPrenda) { // este metodo es para cambiar la visibilidad, pero la prenda se mantiene en la base de datos

        String SentenciaSQL;
        SentenciaSQL = "UPDATE PRENDA SET ";
        SentenciaSQL += "VISIBLE = '0' ";
        SentenciaSQL += "WHERE ID = " + idPrenda;

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(SentenciaSQL);
        baseDatos.close();
        base.close();
    }

    /**
     * Borra definitivamente la prenda indicada
     *
     * @param contexto El contexto a usar.
     * @param id       El ID de la prenda que quieres borrar
     */
    public static void borrarPrenda(Context contexto, int id) {
        String SentenciaSQL;
        SentenciaSQL = "DELETE FROM PRENDA WHERE ID = " + id;

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(SentenciaSQL);
        baseDatos.close();
        base.close();
    }

    public static void BorrarConjunto(Context context, int idConjunto) {
        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM CONJUNTO WHERE ID = " + idConjunto;
        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    public static List<ColorPrenda> ObtenerColores(Context context) {

        List<ColorPrenda> res = new ArrayList<>();

        String sentenciaSQL = "SELECT * FROM COLOR";
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                int id = LibreriaBD.CampoInt(cursor, "ID");
                String nombre = LibreriaBD.Campo(cursor, "NOMBRE");
                String hex = LibreriaBD.Campo(cursor, "HEX");

                res.add(new ColorPrenda(id, nombre, hex));

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        return res;

    }

    public static int CrearTalla(Context contexto, String talla) {
        int id = obtenIDMaximoTalla(contexto);
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO TALLA (ID, NOMBRE) VALUES (";
        sentenciaSQL += id + ",'" + talla.trim() + "')";

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

        return id;
    }

    private static int obtenIDMaximoTalla(Context context) {
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
    public static Prenda Obtener_Prenda(Context context, int id) {


        String sentenciaSQL =  "SELECT PRENDA.NOMBRE \"NOMBRE\", COLOR.NOMBRE \"COLOR\", TIPO.NOMBRE \"TIPO\", TALLA.NOMBRE \"TALLA\" ";
        sentenciaSQL += "FROM PRENDA, COLOR, TIPO, TALLA ";
        sentenciaSQL += "WHERE PRENDA.ID = " + id + " ";
        sentenciaSQL += "AND COLOR.ID = PRENDA.COLOR AND TIPO.ID = PRENDA.TIPO AND TALLA.ID = PRENDA.TALLA ";

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);
        Prenda p = null;

        if (cursor.moveToFirst()) {

            String nombre = LibreriaBD.Campo(cursor, "NOMBRE");
            String  color = LibreriaBD.Campo(cursor, "COLOR");
            String tipo = LibreriaBD.Campo(cursor, "TIPO");
            String talla = LibreriaBD.Campo(cursor, "TALLA");


            p = new Prenda(id, nombre, color, tipo, talla);

        }
        baseDatos.close();
        base.close();
        cursor.close();
        return p;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void Modificar_Prenda(Context context, Prenda p) {
        CambiarVisibilidadPrenda(context, p.id);

        int color = GestorBD.get_id_tabla(context, "color", p.color);
        int tipo = GestorBD.get_id_tabla(context, "tipo", p.tipo);
        int talla = GestorBD.get_id_tabla(context, "talla", p.talla);

        crearPrenda(context, p.nombre, color, tipo, talla, 1, getIdPerfil());
    }

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

    public static List<Integer> ColorCombo(Context context, int idColor)
    {
        String sentenciaSQL = "SELECT COLOR2 FROM COMBO_COLOR WHERE COLOR1 =  " +  idColor;

        Cursor cursor;
        List<Integer> colores = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                int combo = LibreriaBD.CampoInt(cursor, "COLOR2");

                colores .add(combo);

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();

        return colores;
    }
}
