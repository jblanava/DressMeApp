package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dressmeapp.Objetos.Prenda;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase con operaciones estáticas para gestionar la base de datos.
 *
 * Todos estos métodos estáticos toman el contexto y, opcionalmente (segundo parámetro),
 * el nombre del fichero base de datos que usar (por defecto, BaseDatos.getNombreBaseDatos()).
 * El nombre de base de datos se pasa solo para hacer pruebas. Se añadirá la sobrecarga
 * que recibe el nombre de base de datos según se hagan pruebas unitarias con JUnit.
 *
 */
public class GestorBD {

    /**
     * El ID del perfil que tiene la sesión abierta actualmente.
     * Usar el getter y el setter para saber el ID de la sesión actual
     * o cambiarlo.
     */
    private static int idPerfil;

    public static int getIdPerfil() {
        return idPerfil;
    }
    public static void setIdPerfil(int nuevoId) {
        idPerfil = nuevoId;
    }

    //PERFIL: int ID, String usuario, String password

    /*
    private String LoginUsuario;
    private String LoginContrasena;
    private String RegistroUsuario;
    private String RegistroContrasena;
     */

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

    /********************************************************************************
     PERFILES
    ********************************************************************************/

    /**
     * Devuelve el ID correspondiente al siguiente perfil que se cree.
     * (Este método debería ser privado, ya que solo se invoca al crear un perfil.)
     * @param contexto El contexto a usar.
     * @return El ID correspondiente al siguiente perfil que se cree (máximo + 1)
     */
    public static int obtenIDMaximoPerfil(Context contexto) {
        return obtenIDMaximoPerfil(contexto, BaseDatos.getNombreBaseDatos());
    }
    public static int obtenIDMaximoPerfil(Context contexto, String nombrebd) {
        int resultado = 0;
        String sentenciaSQL = "SELECT MAX(ID) AS MAXID FROM PERFIL";

        Cursor cursor;
        BaseDatos base = new BaseDatos(contexto, nombrebd);
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

    /**
     * Comprueba si un string corresponde a un nombre de perfil existente.
     * @param contexto El contexto en el que comprobar.
     * @param nombre El nombre a buscar
     * @return true sii el nombre corresponde a algún perfil
     */
    public static boolean UsuarioEstaEnBD(Context contexto, String nombre) {
        return UsuarioEstaEnBD(contexto, BaseDatos.getNombreBaseDatos(), nombre);
    }
    public static boolean UsuarioEstaEnBD(Context contexto, String nombrebd, String nombre) {
        // clase Registro
        String sentenciaSQL;
        sentenciaSQL= "SELECT ID FROM PERFIL WHERE NOMBRE='" + nombre + "'";
        BaseDatos base = new BaseDatos(contexto, nombrebd);
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
        return IdPerfilAsociado(contexto, BaseDatos.getNombreBaseDatos(), usuario, password);
    }
    public static int IdPerfilAsociado(Context contexto, String nombrebd, String usuario, String password) {

        int id = 0;
        String sentenciaSQL = "SELECT ID FROM PERFIL WHERE NOMBRE ='" + usuario + "' AND CONTRASENIA ='"+ password+ "'";

        Cursor cursor;
        BaseDatos base = new BaseDatos(contexto, nombrebd);
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
        return PassCorrecta(contexto, BaseDatos.getNombreBaseDatos(), usuario, password);
    }
    public static boolean PassCorrecta(Context contexto, String nombrebd, String usuario, String password) {

        boolean encontrado;

        String sentenciaSQL = "SELECT * FROM PERFIL WHERE NOMBRE='"
                + usuario + "' AND CONTRASENIA='" + password + "'";
        BaseDatos base = new BaseDatos(contexto, nombrebd);
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
    public static void CrearPerfil(Context contexto, String usuario, String contrasenia) {
        CrearPerfil(contexto, BaseDatos.getNombreBaseDatos(), usuario, contrasenia);
    }
    public static void CrearPerfil(Context contexto, String nombrebd, String usuario, String contrasenia){
        int id = obtenIDMaximoPerfil(contexto, nombrebd);
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO PERFIL (ID, NOMBRE,  CONTRASENIA) VALUES (";
        sentenciaSQL += id + ",'" + usuario.trim() + "', '" + contrasenia.trim() + "')";

        BaseDatos base = new BaseDatos(contexto, nombrebd);
        SQLiteDatabase baseDatos;
        baseDatos=base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    /**
     * Devuelve el ID correspondiente a la siguiente prenda que se cree.
     * (Este método quizás debería ser privado, ya que solo se invoca al crear un perfil.)
     * @param contexto El contexto a usar.
     * @return El ID correspondiente a la siguiente prenda que se cree.
     */
    public static int obtenIDMaximoPrenda(Context contexto) {
        return obtenIDMaximoPrenda(contexto, BaseDatos.getNombreBaseDatos());
    }
    public static int obtenIDMaximoPrenda(Context contexto, String nombrebd){
        int resultado = 0;
        String sentenciaSQL = "SELECT MAX(ID) AS MAXID FROM PRENDA";

        Cursor cursor;
        BaseDatos base = new BaseDatos(contexto, nombrebd);
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
     * Borra un perfil y su información asociada.
     * @param contexto El contexto a usar.
     * @param id El ID del perfil a borrar.
     */
    public static void BorrarPerfil(Context contexto, int id) {
        BorrarPerfil(contexto, BaseDatos.getNombreBaseDatos(), id);
    }
    public static void BorrarPerfil(Context contexto, String nombrebd, int id){

        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM PERFIL WHERE ID = " + id;
        BaseDatos base = new BaseDatos(contexto, nombrebd);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

        //  Hace falta ademas borrar las prendas, conjuntos e historial

    }

    /**
     * Cambia la contraseña de un perfil.
     * @param contexto El contexto a usar.
     * @param idPerfil El ID del perfil cuya contraseña cambiar
     * @param password La nueva contraseña.
     */
    public static void ActualizarPerfil(Context contexto, int idPerfil, String password) {
        ActualizarPerfil(contexto, BaseDatos.getNombreBaseDatos(), idPerfil, password);
    }
    public static void ActualizarPerfil(Context contexto, String nombrebd, int idPerfil, String password){

        String sentenciaSQL = "UPDATE PERFIL SET CONTRASENIA='"
                + password + "' WHERE ID=" + idPerfil;
        BaseDatos base = new BaseDatos(contexto, nombrebd);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

    }

    /**
     * Devuelve el nombre de usuario de un perfil.
     * @param contexto El contexto a usar.
     * @param idPerfil El ID del perfil cuyo nombre de usuario queremos.
     * @return El nombre del perfil con el ID dado.
     * Si el ID no corresponde a ningún perfil devuelve la cadena vacía.
     */
    public static String getUser(Context contexto, int idPerfil) {
        return getUser(contexto, BaseDatos.getNombreBaseDatos(), idPerfil);
    }
    public static String getUser(Context contexto, String nombrebd, int idPerfil) {
        String SentenciaSQL = "SELECT NOMBRE FROM PERFIL WHERE ID=" + idPerfil;
        String res="";
        BaseDatos base = new BaseDatos(contexto, nombrebd);
        SQLiteDatabase baseDatos=base.getWritableDatabase();
        Cursor cursor=baseDatos.rawQuery(SentenciaSQL,null);
        if(cursor.moveToFirst()) res=LibreriaBD.Campo(cursor,"NOMBRE");
        base.close();
        baseDatos.close();
        cursor.close();
        return res;
    }

    /**
     * Devuelve la contraseña de un perfil.
     * @param contexto El contexto a usar.
     * @param idPerfil El ID del perfil cuya contraseña queremos.
     * @return La contraseña del perfil con el ID dado.
     * Si el ID no corresponde a ningún perfil devuelve la cadena vacía.
     */
    public static String getPass(Context contexto, int idPerfil) {
        return getPass(contexto, BaseDatos.getNombreBaseDatos(), idPerfil);
    }
    public static String getPass(Context contexto, String nombrebd, int idPerfil){
        String SentenciaSQL = "SELECT CONTRASENIA FROM PERFIL WHERE ID=" + idPerfil;
        String res="";
        BaseDatos base = new BaseDatos(contexto, nombrebd);
        SQLiteDatabase baseDatos=base.getWritableDatabase();
        Cursor cursor=baseDatos.rawQuery(SentenciaSQL,null);
        if(cursor.moveToFirst()) res=LibreriaBD.Campo(cursor,"CONTRASENIA");
        base.close();
        baseDatos.close();
        cursor.close();
        return res;

    }

    /********************************************************************************
     PRENDAS Y CONJUNTOS
     ********************************************************************************/

    public static List<Prenda> PrendasVisibles(Context context) {

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
        sentenciaSQL = String.format("INSERT INTO PRENDA VALUES (%d, '%s', '%s', %d, %d, %d, %d)", id, nombre, color, tipo, talla, visible, id_perfil);

        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos=base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

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
     * Borra definitivamente una prenda.
     * @param contexto El contexto a usar.
     * @param id El ID de la prenda que quieres borrar
     */
    public static void borrarPrenda(Context contexto, int id) {
        String SentenciaSQL;
        SentenciaSQL = "DELETE FROM PRENDA WHERE ID = " + id;

        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(SentenciaSQL);
        baseDatos.close();
        base.close();
    }

    /**
     * Borra todas las entradas del historial de un perfil.
     * @param contexto El contexto a usar.
     * @param idPerfil El ID del perfil cuyo historial limpiar.
     */
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

    /**
     * Elimina un conjunto.
     * @param contexto El contexto a usar.
     * @param idConjunto El ID del conjunto a eliminar.
     */
    public static void BorrarConjunto(Context contexto, int idConjunto){
        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM CONJUNTO WHERE ID = " + String.valueOf(idConjunto);
        BaseDatos base = new BaseDatos(contexto);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }


    /**
     * Devuelve los atributos de una prenda visible.
     * @param context El contexto a usar.
     * @param id El ID de la prenda cuyos atributos obtener.
     * @return Un objeto Prenda con los atributos de la prenda cuyo ID es el dado.
     * Si no hay prendas visibles con dicho ID, devuelve null.
     */
    public static Prenda Obtener_Prenda(Context context, int id){
        String sentenciaSQL = "SELECT ID, NOMBRE, COLOR, TIPO, TALLA FROM PRENDA WHERE ID = " + id + " AND VISIBLE = 1";
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

    /**
     * Devuelve el nombre de un tipo de prenda.
     * @param context El contexto a usar.
     * @param tipo EL ID del tipo a obtener.
     * @return El nombre del tipo de prenda, o la cadena "Error" si no se encuentra.
     */
    public static String Dar_Tipo (Context context,int tipo){
        String sentenciaSQL = "SELECT NOMBRE FROM TIPO WHERE ID = " + tipo;
        Cursor cursor;

        BaseDatos base = new BaseDatos(context);
        SQLiteDatabase baseDatos = base.getReadableDatabase();
        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        String nombre = "Error";

        if(cursor.moveToFirst())
        {
             nombre = LibreriaBD.Campo(cursor, "NOMBRE");
        }


        baseDatos.close();
        base.close();
        cursor.close();
        return nombre;
    }

    /**
     * Devuelve el nombre de la talla de un tipo de prenda.
     * @param context El contexto a usar.
     * @param talla El ID de la talla.
     * @return El nombre de la talla, o la cadena "Error" si no se encuentra.
     */
    public static String Dar_Talla (Context context, int talla){
        String sentenciaSQL = "SELECT NOMBRE FROM TALLA WHERE ID = " + talla;
        Cursor cursor;

        BaseDatos base = new BaseDatos(context);
        SQLiteDatabase baseDatos = base.getReadableDatabase();
        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        String nombre = "Error";

        if(cursor.moveToFirst())
        {
            nombre = LibreriaBD.Campo(cursor, "NOMBRE");
        }

        baseDatos.close();
        base.close();
        cursor.close();
        return nombre;
    }

    /**
     * Devuelve una lista con todos los tipos de prendas existentes en el sistema.
     * @param context El contexto a usar.
     * @return Una lista con todos los tipos de prendas.
     */
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

    /**
     * Devuelve una lista con todas las tallas existentes en el sistema.
     * @param context El contexto a usar.
     * @return Una lista con todas las tallas.
     */
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

    /**
     * Crea una copia modificada de una prenda con visible=1 y oculta
     * la prenda original.
     * @param context El contexto a usar.
     * @param p La prenda con los cambios efectuados.
     */
    public static void  Modificar_Prenda (Context context, Prenda p){
        CambiarVisibilidadPrenda(context, p.id );
        crearPrenda(context,p.nombre,p.color,p.tipo,p.talla,1,getIdPerfil());
    }

}
