package com.example.dressmeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class GestorBD {

    public static int idPerfil;
    private static Context contexto;
    private String LoginUsuario;
    private String LoginContrasena;
    private String RegistroUsuario;
    private String RegistroContrasena;

    public GestorBD(Context context) {
        contexto = context;

    }

    public static int getIdPerfil() {
        return idPerfil;
    }
    //PERFIL: int ID, String usuario, String password

    /*public static void IngresoPerfil(String usuario, String pass) {
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
                contrasenia = LibreriaBD.Campo(cursor, "CONTRASENIA");
            } while(cursor.moveToNext());
        }

        baseDatos.close();
        base.close();
        cursor.close();
    } */

    public static void RegistroPerfil(String u, String p) {
        int id = obtenIDMaximoPerfil();

        String SentenciaSQL = "INSERT INTO PERFIL(ID, USUARIO, PASSWORD) VALUES(";
        SentenciaSQL += String.valueOf(id) + ",'" + u + "', '" + p + "')";

        BaseDatos bdh = new BaseDatos(contexto);
        SQLiteDatabase bd;
        bd = bdh.getWritableDatabase();
        bd.execSQL(SentenciaSQL);
        bd.close();
        bdh.close();
    }

    protected static int obtenIDMaximoPerfil() {
        int resultado = 0;
        String sentenciaSQL = "SELECT MAX(ID) AS MAXID FROM PERFIL";

        Cursor cursor;
        BaseDatos base = new BaseDatos(contexto);
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

     public static List<Prenda> Dar_Prendas() {

         String sentenciaSQL = "SELECT * FROM PRENDA WHERE VISIBLE = 1";
         Cursor cursor;
         List<Prenda> res = new ArrayList<>();

         BaseDatos base = new BaseDatos(contexto);
         SQLiteDatabase baseDatos = base.getReadableDatabase();

         cursor = baseDatos.rawQuery(sentenciaSQL, null);

         if (cursor.moveToFirst()) {
             do {

                String nombre= LibreriaBD.Campo(cursor, "NOMBRE");
                String color = LibreriaBD.Campo(cursor, "COLOR");
                int tipo = LibreriaBD.CampoInt(cursor,"TIPO");
                int talla = LibreriaBD.CampoInt(cursor, "TALLA");



                Prenda p = new Prenda(nombre,color,"CAMISA","talla");
                res.add(p);

             } while (cursor.moveToNext());

         }
         baseDatos.close();
         base.close();
         cursor.close();
         return res;
     }

    protected static int obtenIDMaximoPrenda(){
        int resultado = 0;
        String sentenciaSQL = "SELECT MAX(ID) AS MAXID FROM PRENDA";

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

    protected static boolean UsuarioEstaEnBD(String nombre) {
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

    protected static int IdPerfilAsociado(String usuario, String password) {

        int id = 0;
        String sentenciaSQL = "SELECT ID FROM PERFIL WHERE USUARIO ='" + usuario + "' AND CONTRASENIA ='"+ password+ "'";

        Cursor cursor;
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        id = LibreriaBD.CampoInt(cursor, "ID");

        baseDatos.close();
        base.close();
        cursor.close();
        return id;


    }

    protected static boolean PassCorrecta(String usuario, String password) {

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


    protected static void CrearPerfil(String usuario, String contrasenia){
        int id = obtenIDMaximoPerfil();
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO PERFIL (ID, USUARIO,  CONTRASENIA) VALUES (";
        sentenciaSQL += id + ",'" + usuario.trim() + "', '" + contrasenia.trim() + "'";

        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos=base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }
    protected static void crearPrenda(String nombre, String color, String tipo , String talla, int visible,int id_perfil){
    int id= obtenIDMaximoPrenda();
    String sentenciaSQL;
    sentenciaSQL = "INSERT INTO PRENDA (ID, NOMBRE, COLOR, TIPO, TALLA, VISIBLE, ID_PERFIL) VALUES (";
    sentenciaSQL += id +",'" + nombre.trim()+ "', '" + color.trim() + "', '" + tipo.trim() +
            "', '"+talla.trim()+"', '"+visible+"', '"+id_perfil +"'";
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos=base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

    }
    protected static void BorrarPerfil(int id){

        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM PERFIL WHERE ID = " + String.valueOf(id);
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();


    }

    protected static void BorrarPrenda(int idPrenda){
        //No se borra la prenda simplemente se actualiza el flag visible a 0

        String SentenciaSQL;
        SentenciaSQL = "UPDATE PRENDA SET ";
        SentenciaSQL+= "VISIBLE = '0' ";
        SentenciaSQL+= "WHERE ID = " + idPrenda;

        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(SentenciaSQL);
        baseDatos.close();
        base.close();
    }

    protected static void BorrarHistorial(int idPerfil){

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

    protected static void BorrarConjunto(int idConjunto){
        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM CONJUNTO WHERE ID = " + String.valueOf(idConjunto);
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    protected static void ActualizarPerfil(int idPerfil, String usuario, String password){

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
