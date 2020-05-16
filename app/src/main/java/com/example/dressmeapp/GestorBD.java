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
    
    /*public static void IngresoPerfil(String usuario, String pass) {
        // Clase Entrar
        if(PassCorrecta(usuario, pass) && UsuarioEstaEnBD(usuario)){

        }
    } */


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
       String sentenciaSQL;
       sentenciaSQL= "SELECT ID FROM PERFIL WHERE USUARIO=";
       sentenciaSQL+= nombre;
       BaseDatos base = new BaseDatos(contexto);
       SQLiteDatabase baseDatos = base.getReadableDatabase();

       Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);
       if(cursor == null && cursor.getCount() == 0){ //Aquí comrpuebo si el cursor está vacío, en otro casa habrá traído algo de la BD
              return false;

       }else{
           return true;
       }
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


    public static void CrearPerfil(String usuario, String contrasenia){
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
        sentenciaSQL = "DELETE FROM PERFIL WHERE ID = " + String.valueOf(id);
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();


    }

    private static void BorrarPrenda(int idPrenda){ // El borrar prenda realmente no es borrarla, es actualizar el flag
        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM PRENDA WHERE ID = " + String.valueOf(idPrenda);
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    private static void BorrarHistorial(int idPerfil){

        // Definir cómo se hará una entrada en el historia (información de salida + prendas sugeridas)
        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM ENTRADA_HISTORIAL WHERE ID = " + String.valueOf(idPerfil);
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();



    }

    private static void BorrarConjunto(int idConjunto){
        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM CONJUNTO WHERE ID = " + String.valueOf(idConjunto);
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    private static void ActualizarPerfil(int idPerfil, String usuario, String password){

        if (!UsuarioEstaEnBD(usuario)) {
            String sentenciaSQL = "UPDATE PERFIL SET USUARIO='" + usuario + "', PASS='"
                    + password + "' WHERE idPerfil=" + idPerfil;
            BaseDatos base = new BaseDatos(contexto);
            SQLiteDatabase baseDatos;
            baseDatos = base.getWritableDatabase();
            baseDatos.execSQL(sentenciaSQL);
            baseDatos.close();
            base.close();
        }


    }
}
