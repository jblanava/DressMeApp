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

        }
        return resultado;
    }
    private static boolean UsuarioEstaEnBD(String nombre) {

        boolean encontrado = false;

        String sentenciaSQL = "SELECT * FROM PERFIL WHERE USUARIO='" + nombre + "'";
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);
        encontrado = cursor.moveToFirst();
        cursor.close();

        return encontrado;

    }

    private static boolean PassCorrecta(String usuario, String password) {

        boolean encontrado = false;

        String sentenciaSQL = "SELECT * FROM PERFIL WHERE USUARIO='"
                + usuario + "' AND PASSWORD='" + password + "'";
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);
        encontrado = cursor.moveToFirst();
        cursor.close();

        return encontrado;
    }

    private static void CrearPerfil(int id, String usuario, String contrasenia){

    }

    private static void CrearPerfil(String usuario, String contrasenia){

    }

    private static void BorrarPerfil(int id){

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
