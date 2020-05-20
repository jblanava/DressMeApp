package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dressmeapp.Activities.AniadirPrendaActivity;
import com.example.dressmeapp.Objetos.Prenda;

import java.util.ArrayList;
import java.util.List;


public class GestorBD {

    /**
     * El ID del perfil que tiene la sesión abierta actualmente.
     */
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

    /*
    public static void RegistroPerfil(String u, String p) {
        int id = obtenIDMaximoPerfil();

        String SentenciaSQL = "INSERT INTO PERFIL(ID, USUARIO, PASSWORD) VALUES(";
        SentenciaSQL += id + ",'" + u + "', '" + p + "')";

        BaseDatos bdh = new BaseDatos(contexto);
        SQLiteDatabase bd;
        bd = bdh.getWritableDatabase();
        bd.execSQL(SentenciaSQL);
        bd.close();
        bdh.close();
    }
    */

    /**
     * Devuelve el ID correspondiente al siguiente perfil que se cree.
     * (Este método debería ser privado, ya que solo se invoca al crear un perfil.)
     * @param contexto El contexto a usar.
     * @return El ID correspondiente al siguiente perfil que se cree (máximo + 1)
     */
    public static int obtenIDMaximoPerfil(Context contexto) {
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


    public static List<Prenda> Dar_Prendas(Context context) {

        String sentenciaSQL = "SELECT ID, NOMBRE, COLOR, TIPO, TALLA FROM PRENDA WHERE VISIBLE = 1";
        Cursor cursor;
        List<Prenda> res = new ArrayList<>();

        BaseDatos base = new BaseDatos(context);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                int id = LibreriaBD.CampoInt(cursor,"ID");
                String nombre= LibreriaBD.Campo(cursor, "NOMBRE");
                String color = LibreriaBD.Campo(cursor, "COLOR");
                int tipo = LibreriaBD.CampoInt(cursor,"TIPO");
                int talla = LibreriaBD.CampoInt(cursor, "TALLA");



                Prenda p = new Prenda(id, nombre,color,tipo,talla);
                res.add(p);

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        return res;
        }





    /**
     * Devuelve el ID correspondiente a la siguiente prenda que se cree.
     * (Este método quizás debería ser privado, ya que solo se invoca al crear un perfil.)
     * @param context El contexto a usar.
     * @return El ID correspondiente a la siguiente prenda que se cree.
     */
    public static int obtenIDMaximoPrenda(Context context){
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


    /**
     * Comprueba si un string corresponde a un nombre de perfil existente.
     * @param contexto El contexto en el que comprobar.
     * @param nombre El nombre a buscar
     * @return true sii el nombre corresponde a algún perfil
     */
    public static boolean UsuarioEstaEnBD(Context contexto, String nombre) {
        // clase Registro
        String sentenciaSQL;
        sentenciaSQL= "SELECT ID FROM PERFIL WHERE NOMBRE='" + nombre + "'";
        BaseDatos base = new BaseDatos(contexto);
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
     * Devuelve el ID de un perfil dado su nombre y contraseña.
     * @param contexto El contexto en el cual comprobar.
     * @param usuario El usuario cuyo ID buscar
     * @param password La contraseña cuyo ID buscar
     * @return El ID del perfil definido, o 0 si no existe un perfil con los datos dados
     */
    public static int IdPerfilAsociado(Context contexto, String usuario, String password) {

        int id = 0;
        String sentenciaSQL = "SELECT ID FROM PERFIL WHERE NOMBRE ='" + usuario + "' AND CONTRASENIA ='"+ password+ "'";

        Cursor cursor;
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if(cursor.moveToFirst()){
            id = LibreriaBD.CampoInt(cursor, "ID");
        }


        baseDatos.close();
        base.close();
        cursor.close();
        return id;


    }

    /**
     * Comprueba si el par (nombre de perfil, contraseña) dado corresponde a un usuario.
     * @param contexto El contexto en el cual comprobar.
     * @param usuario El nombre del perfil a comprobar
     * @param password La contraseña del perfil a comprobar.
     * @return true sii el par (nombre de perfil, contraseña) corresponden a un usuario.
     */
    public static boolean PassCorrecta(Context contexto, String usuario, String password) {

        boolean encontrado = false;

        String sentenciaSQL = "SELECT * FROM PERFIL WHERE NOMBRE='"
                + usuario + "' AND CONTRASENIA='" + password + "'";
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        Cursor cursor = baseDatos.rawQuery(sentenciaSQL, null);
        encontrado = cursor.moveToFirst();
        cursor.close();

        return encontrado;
    }

    /**
     * Crea un nuevo perfil.
     * @param contexto El contexto en el que crear el perfil
     * @param usuario El nombre del perfil
     * @param contrasenia La contraseña para el perfil.
     */
    public static void CrearPerfil(Context contexto, String usuario, String contrasenia){
        int id = obtenIDMaximoPerfil(contexto);
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO PERFIL (ID, NOMBRE,  CONTRASENIA) VALUES (";
        sentenciaSQL += id + ",'" + usuario.trim() + "', '" + contrasenia.trim() + "')";

        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos=base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    /**
     * Crea una nueva prenda.
     * @param contexto El contexto a usar.
     * @param nombre El nombre de la prenda.
     * @param color El color de la prenda.
     * @param tipo Qué tipo de prenda es.
     * @param talla La talla de la prenda.
     * @param visible Si la prenda será visible o no en la lista de prendas
     *                (siempre lo será en el historial)
     * @param id_perfil El ID del perfil que tendrá la prenda.
     */
    public static void crearPrenda(Context contexto, String nombre, String color, int tipo , int talla, int visible,int id_perfil){

        int id= obtenIDMaximoPrenda(contexto);
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO PRENDA (ID, NOMBRE, COLOR, TIPO, TALLA, VISIBLE, ID_PERFIL) VALUES (";
        sentenciaSQL += id +",'" + nombre.trim()+ "', '" + color.trim() + "', '" + tipo +
                "', '"+ talla +"', '"+visible+"', '"+id_perfil +"'";
            BaseDatos base = new BaseDatos(contexto);
            SQLiteDatabase baseDatos;
            baseDatos=base.getWritableDatabase();
            baseDatos.execSQL(sentenciaSQL);
            baseDatos.close();
            base.close();

    }

    public static void BorrarPerfil(Context contexto, int id){

        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM PERFIL WHERE ID = " + id;
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

        //  Hace falta ademas borrar las prendas, conjuntos e historial

    }

    /**
     * Pone el flag "visible" a 0 en una prenda, ocultándola en la lista de prendas y en
     * el algoritmo vestidor pero no en el historial.
     * @param contexto El contexto a usar.
     * @param idPrenda El ID de la prenda a borrar.
     */
    public static void CambiarVisibilidadPrenda(Context contexto, int idPrenda){ // este metodo es para cambiar la visibilidad, pero la prenda se mantiene en la base de datos

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

    /**
     * Borra definitivamente las prendas de un perfil (no las hace invisibles)
     * @param contexto El contexto a usar.
     * @param idPerfil El ID del perfil cuyas prendas borrar
     */
    public static void borrarPrenda(Context contexto, int idPerfil) {

        String SentenciaSQL;
        SentenciaSQL = "DELETE * FROM PRENDA WHERE ID_PERFIL =" + idPerfil;

        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(SentenciaSQL);
        baseDatos.close();
        base.close();
    }

    public static void BorrarHistorial(Context contexto, int idPerfil){

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

    public static void BorrarConjunto(int idConjunto){
        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM CONJUNTO WHERE ID = " + String.valueOf(idConjunto);
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    public static void ActualizarPerfil(Context contexto, int idPerfil, String password){

        String sentenciaSQL = "UPDATE PERFIL SET CONTRASENIA='"
                + password + "' WHERE ID=" + idPerfil;
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

    }
    public static String getUser(int idPerfil){
        String SentenciaSQL = "SELECT NOMBRE FROM PERFIL WHERE ID=" + idPerfil;
        String res="";
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos=base.getWritableDatabase();
        Cursor cursor=baseDatos.rawQuery(SentenciaSQL,null);
        if(cursor.moveToFirst()) res=LibreriaBD.Campo(cursor,"NOMBRE");
        base.close();
        baseDatos.close();
        cursor.close();
        return res;
    }
    public static  String getPass(int idPerfil){
        String SentenciaSQL = "SELECT CONTRASENIA FROM PERFIL WHERE ID=" + idPerfil;
        String res="";
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos=base.getWritableDatabase();
        Cursor cursor=baseDatos.rawQuery(SentenciaSQL,null);
        if(cursor.moveToFirst()) res=LibreriaBD.Campo(cursor,"CONTRASENIA");
        base.close();
        baseDatos.close();
        cursor.close();
        return res;

    }
    public static Prenda Obtener_Prenda(Context context,int id){
        String sentenciaSQL = "SELECT ID, NOMBRE, COLOR, TIPO, TALLA FROM PRENDA WHERE VISIBLE = 1 AND ID = " + id;
        Cursor cursor;


        BaseDatos base = new BaseDatos(context);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);
        Prenda p=null;

        if (cursor.moveToFirst())
        {
            String nombre= LibreriaBD.Campo(cursor, "NOMBRE");
            String color = LibreriaBD.Campo(cursor, "COLOR");
            int tipo = LibreriaBD.CampoInt(cursor,"TIPO");
            int talla = LibreriaBD.CampoInt(cursor, "TALLA");

            p = new Prenda(id, nombre,color,tipo,talla);

        }
        baseDatos.close();
        base.close();
        cursor.close();
        return p;
    }

    public static String Dar_Tipo (Context context,int tipo){
        String sentenciaSQL = "SELECT NOMBRE FROM TIPO WHERE ID = " + tipo;
        Cursor cursor;

        BaseDatos base = new BaseDatos(context);
        SQLiteDatabase baseDatos = base.getReadableDatabase();
        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        cursor.moveToFirst() ;

        String nombre= LibreriaBD.Campo(cursor, "NOMBRE");

        baseDatos.close();
        base.close();
        cursor.close();
        return nombre;
    }

    public static String Dar_Talla (Context context,int talla){
        String sentenciaSQL = "SELECT NOMBRE FROM TALLA WHERE ID = " + talla;
        Cursor cursor;

        BaseDatos base = new BaseDatos(context);
        SQLiteDatabase baseDatos = base.getReadableDatabase();
        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        cursor.moveToFirst() ;

        String nombre= LibreriaBD.Campo(cursor, "NOMBRE");
        baseDatos.close();
        base.close();
        cursor.close();
        return nombre;
    }

    public static List<String> getTipos (Context context){
        String sentenciaSQL = "SELECT NOMBRE FROM TIPO ";
        Cursor cursor;
        List<String> res = new ArrayList<>();

        BaseDatos base = new BaseDatos(context);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                String nombre= LibreriaBD.Campo(cursor, "NOMBRE");

                res.add(nombre);

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        return res;
    }

    public static List<String> getTallas (Context context){
        String sentenciaSQL = "SELECT NOMBRE FROM TALLA ";
        Cursor cursor;
        List<String> res = new ArrayList<>();

        BaseDatos base = new BaseDatos(context);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                String nombre= LibreriaBD.Campo(cursor, "NOMBRE");

                res.add(nombre);

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        return res;
    }

    public static void  Modificar_Prenda (Context context, Prenda p){
        CambiarVisibilidadPrenda(context, p.id );
        crearPrenda(context,p.nombre,p.color,p.tipo,p.talla,1,getIdPerfil());
    }
}
