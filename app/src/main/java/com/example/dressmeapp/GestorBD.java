package com.example.dressmeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class GestorBD {

    private static Context contexto;
    private String LoginUsuario;
    private String LoginContrasena;
    private String RegistroUsuario;
    private String RegistroContrasena;
    public GestorBD(Context context){
        contexto = context;

    }
    //PERFIL: int ID, String usuario, String password
    public static void IngresoPerfil(String u, String p) {
        // Clase Entrar
        String sentenciaSQL = "SELECT CONTRASENIA FROM PERFIL WHERE USUARIO=";
        sentenciaSQL+=u;
        String contrasenia;
        Cursor cursor;
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);
        if(cursor.moveToFirst()){
            do{
                contrasenia = LibreriaBD.Campo(cursor, p);
                //contrasenia = LibreriaBD.(cursor, "CONTRASENIA");
            } while(cursor.moveToNext());
        }

        baseDatos.close();
        base.close();
        cursor.close();
    }

    public static void RegistroPerfil(String u, String p) {
        // clase Registro
    }

    protected static int obtenIDMaximo(){
        int resultado = 0;
        String sentenciaSQL = "SELECT MAX(ID) AS MAXID FROM PERFIL";

        Cursor cursor;
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);
        if(cursor.moveToFirst()){
            do{
                resultado = LibreriaBD.CampoInt(cursor, "MAXID");
            } while(cursor.moveToNext());
        }
        resultado++;
        baseDatos.close();
        base.close();
        cursor.close();
        return resultado;
    }
    private static boolean UsuarioEstaEnBD(String nombre) {
        // clase Registro
        return false;
    }

    private static boolean PassCorrecta(String usario, String password) {
        // clase Entrar
        return false;
    }

    private static void CrearPerfil(int id, String usuario, String contrasenia){

    }

    private static void CrearPerfil(String usuario, String contrasenia){
        int id = obtenIDMaximo();
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO PERFIL (ID, USUARIO,  CONTRASENIA) VALUES (";
        sentenciaSQL += String.valueOf(id) + ",'" + usuario.trim() + "', '" + contrasenia.trim() + "'";

        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos=base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    private static void BorrarPerfil(int id){

        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM PERFIL WHERE ID = " + id;
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();


    }

    private static void BorrarPrenda(int idPrenda){

    }

    private static void BorrarHistorial(int idPerfil){

    }

    private static void BorrarConjunto(int idPerfil){

    }

    private static void ActualizarPerfil(int idPerfil, String usuario, String password){

    }
}
