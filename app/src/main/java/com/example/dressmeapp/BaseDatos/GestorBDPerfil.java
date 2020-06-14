package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GestorBDPerfil {
    /* Operaciones relacionadas con el perfil y la tabla PERFIL */


    /**
     * Comprueba si un string corresponde a un nombre de perfil existente.
     *
     * @param context El contexto en el que comprobar.
     * @param nombre  El nombre a buscar
     * @return true sii el nombre corresponde a algún perfil
     */
    public static boolean usuario_existe(Context context, String nombre) {
        // clase Registro
        String sentenciaSQL;
        sentenciaSQL = "SELECT ID FROM PERFIL WHERE NOMBRE='" + nombre + "'";
        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);

        // Compruebo que el cursor esté vacío. Si no, habrá traído algo de la BD
        boolean resultado = false;
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                resultado = true;
            }
            cursor.close();
        }

        return resultado;

    }

    /**
     * Comprueba si el par (nombre de perfil, contraseña) dado corresponde a un usuario.
     *
     * @param contexto El contexto en el cual comprobar.
     * @param usuario  El nombre del perfil a comprobar
     * @param password La contraseña del perfil a comprobar.
     * @return true sii el par (nombre de perfil, contraseña) corresponden a un usuario.
     */
    public static boolean pass_correcta(Context contexto, String usuario, String password) {

        boolean encontrado;

        String sentenciaSQL = "SELECT * FROM PERFIL WHERE NOMBRE='"
                + usuario + "' AND CONTRASENIA='" + password + "'";
        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);
        encontrado = cursor.moveToFirst();
        cursor.close();

        return encontrado;
    }

    /**
     * Crea un nuevo perfil.
     *
     * @param contexto    El contexto en el que crear el perfil
     * @param usuario     El nombre del perfil
     * @param contrasenia La contraseña para el perfil.
     */
    public static int crear_perfil(Context contexto, String usuario, String contrasenia) {
        int id = GestorBD.obtener_id_maximo(contexto, "perfil");
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO PERFIL (ID, NOMBRE,  CONTRASENIA) VALUES (";
        sentenciaSQL += id + ",'" + usuario.trim() + "', '" + contrasenia.trim() + "')";

        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

        return id;
    }

    public static void borrar_perfil(Context contexto, int id) {

        String borrarPerfiles = "DELETE FROM PERFIL WHERE ID = " + id;
        String borrarPrendas = "DELETE FROM PRENDA WHERE ID_PERFIL = " + id;
        String borrarConjuntos = "DELETE FROM CONJUNTO WHERE ID_PERFIL = " + id;

        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();

        baseDatos.execSQL(borrarPerfiles);
        baseDatos.execSQL(borrarPrendas);
        baseDatos.execSQL(borrarConjuntos);

        baseDatos.close();
        base.close();
    }

    public static void actualizar_perfil(Context contexto, int idPerfil, String password) {

        String sentenciaSQL = "UPDATE PERFIL SET CONTRASENIA='"
                + password + "' WHERE ID=" + idPerfil;
        BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

    }

    public static String get_usuario(Context context, int idPerfil) {
        String SentenciaSQL = "SELECT NOMBRE FROM PERFIL WHERE ID=" + idPerfil;
        String res = "";
        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getWritableDatabase();
        Cursor cursor = baseDatos.rawQuery(SentenciaSQL, null);
        if (cursor.moveToFirst()) res = LibreriaBD.Campo(cursor, "NOMBRE");
        base.close();
        baseDatos.close();
        cursor.close();
        return res;
    }

    public static String get_contrasenia(Context context, int idPerfil) {
        String SentenciaSQL = "SELECT CONTRASENIA FROM PERFIL WHERE ID=" + idPerfil;
        String res = "";
        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getWritableDatabase();
        Cursor cursor = baseDatos.rawQuery(SentenciaSQL, null);
        if (cursor.moveToFirst()) res = LibreriaBD.Campo(cursor, "CONTRASENIA");
        base.close();
        baseDatos.close();
        cursor.close();
        return res;

    }
}
