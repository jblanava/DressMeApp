package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dressmeapp.Activities.AniadirPrendaActivity;
import com.example.dressmeapp.Objetos.Conjunto;
import com.example.dressmeapp.Objetos.Prenda;

import java.util.ArrayList;
import java.util.List;


public class GestorBD {

    /**
     * El ID del perfil que tiene la sesión abierta actualmente.
     */
    public static int idPerfil;



    private static Context contexto; // TODO: eliminar en el futuro
    private static String nombreBD = "dressmeapp11.db";

    public GestorBD(Context context)  // TODO: Eliminar?
    {
        contexto = context;
    }

    public static void seleccionarBD(String nombreBD)
    {
        GestorBD.nombreBD = nombreBD;
    }







    ///////////////////////77
/// PERFIL

    public static int getIdPerfil()
    {
        return idPerfil;
    }

    public static void setIdPerfil(int nuevoId)
    {
        idPerfil = nuevoId;
    }





    /**
     * Devuelve el ID correspondiente al siguiente perfil que se cree.
     * (Este método debería ser privado, ya que solo se invoca al crear un perfil.)
     * @param context El contexto a usar.
     * @return El ID correspondiente al siguiente perfil que se cree (máximo + 1)
     */
    public static int obtenIDMaximoPerfil(Context context)
    {
        int resultado = 0;
        String sentenciaSQL = "SELECT MAX(ID) AS MAXID FROM PERFIL";

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
        BaseDatos base = new BaseDatos(context, nombreBD);
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
     * @param context El contexto en el que comprobar.
     * @param nombre El nombre a buscar
     * @return true sii el nombre corresponde a algún perfil
     */
    public static boolean UsuarioEstaEnBD(Context context, String nombre) {
        // clase Registro
        String sentenciaSQL;
        sentenciaSQL= "SELECT ID FROM PERFIL WHERE NOMBRE='" + nombre + "'";
        BaseDatos base = new BaseDatos(context, nombreBD);
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
     * @param context El contexto en el cual comprobar.
     * @param usuario El usuario cuyo ID buscar
     * @param password La contraseña cuyo ID buscar
     * @return El ID del perfil definido, o 0 si no existe un perfil con los datos dados
     */
    public static int IdPerfilAsociado(Context context, String usuario, String password) {

        int id = 0;
        String sentenciaSQL = "SELECT ID FROM PERFIL WHERE NOMBRE ='" + usuario + "' AND CONTRASENIA ='"+ password+ "'";

        Cursor cursor;
        BaseDatos base = new BaseDatos(context, nombreBD);
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
        BaseDatos base = new BaseDatos(contexto, nombreBD);
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

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos=base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    public static void BorrarPerfil(Context contexto, int id){

        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM PERFIL WHERE ID = " + id;
        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

        //  Hace falta ademas borrar las prendas, conjuntos e historial

    }

    public static void ActualizarPerfil(Context contexto, int idPerfil, String password){

        String sentenciaSQL = "UPDATE PERFIL SET CONTRASENIA='"
                + password + "' WHERE ID=" + idPerfil;
        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

    }
    public static String getUser(Context context, int idPerfil){
        String SentenciaSQL = "SELECT NOMBRE FROM PERFIL WHERE ID=" + idPerfil;
        String res="";
        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos=base.getWritableDatabase();
        Cursor cursor=baseDatos.rawQuery(SentenciaSQL,null);
        if(cursor.moveToFirst()) res=LibreriaBD.Campo(cursor,"NOMBRE");
        base.close();
        baseDatos.close();
        cursor.close();
        return res;
    }
    public static  String getPass(Context context, int idPerfil){
        String SentenciaSQL = "SELECT CONTRASENIA FROM PERFIL WHERE ID=" + idPerfil;
        String res="";
        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos=base.getWritableDatabase();
        Cursor cursor=baseDatos.rawQuery(SentenciaSQL,null);
        if(cursor.moveToFirst()) res=LibreriaBD.Campo(cursor,"CONTRASENIA");
        base.close();
        baseDatos.close();
        cursor.close();
        return res;

    }

    ///////////////////////////////////////////////////////////////
    //          PRENDAS                                        ////
    //////////////////////////////////////////////////////////////


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

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos=base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

    }


    public static List<Prenda> PrendasVisibles(Context context, String texto)
    {
        String sentenciaSQL = "SELECT ID, NOMBRE, COLOR, TIPO, TALLA FROM PRENDA WHERE VISIBLE = 1";
        Cursor cursor;
        List<Prenda> res = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                int id = LibreriaBD.CampoInt(cursor,"ID");
                String nombre= LibreriaBD.Campo(cursor, "NOMBRE");
                String color = LibreriaBD.Campo(cursor, "COLOR");
                int tipo = LibreriaBD.CampoInt(cursor,"TIPO");
                int talla = LibreriaBD.CampoInt(cursor, "TALLA");

                String Stipo = Dar_Tipo(context, tipo);
                String Stalla = Dar_Talla(context, talla);

                boolean cumpleFiltro = false;

                cumpleFiltro = cumpleFiltro || nombre.toUpperCase().contains(texto.toUpperCase());
                cumpleFiltro = cumpleFiltro || color.toUpperCase().contains(texto.toUpperCase());
                cumpleFiltro = cumpleFiltro || Stipo.toUpperCase().contains(texto.toUpperCase());
                cumpleFiltro = cumpleFiltro || Stalla.toUpperCase().contains(texto.toUpperCase());

                if(cumpleFiltro)
                {
                    Prenda p = new Prenda(id, nombre,color,tipo,talla);
                    res.add(p);
                }

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
     * @param contexto El contexto a usar.
     * @param idPrenda El ID de la prenda a borrar.
     */
    public static void CambiarVisibilidadPrenda(Context contexto, int idPrenda){ // este metodo es para cambiar la visibilidad, pero la prenda se mantiene en la base de datos

        String SentenciaSQL;
        SentenciaSQL = "UPDATE PRENDA SET ";
        SentenciaSQL+= "VISIBLE = '0' ";
        SentenciaSQL+= "WHERE ID = " + idPrenda;

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(SentenciaSQL);
        baseDatos.close();
        base.close();
    }

    /**
     * Borra definitivamente la prenda indicada
     * @param contexto El contexto a usar.
     * @param id El ID de la prenda que quieres borrar
     */
    public static void borrarPrenda(Context contexto, int id)
    {
        String SentenciaSQL;
        SentenciaSQL = "DELETE FROM PRENDA WHERE ID = " + id;

        BaseDatos base = new BaseDatos(contexto, nombreBD);
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
        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();



    }

    public static void BorrarConjunto(int idConjunto){
        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM CONJUNTO WHERE ID = " + String.valueOf(idConjunto);
        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }


    public static Prenda Obtener_Prenda(Context context,int id){
        String sentenciaSQL = "SELECT ID, NOMBRE, COLOR, TIPO, TALLA FROM PRENDA WHERE ID = " + id + " AND VISIBLE = 1";
        Cursor cursor;


        BaseDatos base = new BaseDatos(context, nombreBD);
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

        BaseDatos base = new BaseDatos(context, nombreBD);
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

    public static String Dar_Talla (Context context,int talla){
        String sentenciaSQL = "SELECT NOMBRE FROM TALLA WHERE ID = " + talla;
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, nombreBD);
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

    public static List<String> getTipos (Context context){
        String sentenciaSQL = "SELECT NOMBRE FROM TIPO ";
        Cursor cursor;
        List<String> res = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, nombreBD);
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

        BaseDatos base = new BaseDatos(context, nombreBD);
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


    public int num_conjuntos(Context context) {
        String sentenciaSQL = "SELECT COUNT (ID) AS NUM FROM CONJUNTO GROUP BY ID";
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        int cont = 0;
        if (cursor.moveToFirst()) {
            do {
             cont = LibreriaBD.CampoInt(cursor, "NUM");
            } while (cursor.moveToNext());
        }
        return cont;
    }

    public static  Conjunto resAlgoritmo(Context context){ //Este método debe devolver el resultado que arroje el algoritmo


        return null;
    }





    public static List<Conjunto>  ConjuntosEnBD(Context context){

        List<Conjunto> res = new ArrayList<>();


        String sentenciaSQL = "SELECT * FROM CONJUNTO";
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                Conjunto c = new Conjunto();

                int id =  LibreriaBD.CampoInt(cursor, "ID");
                int Abrigo =  LibreriaBD.CampoInt(cursor, "ABRIGO");
                int Sudadera =  LibreriaBD.CampoInt(cursor, "SUDADERA");
                int Camiseta =  LibreriaBD.CampoInt(cursor, "CAMISETA");
                int Pantalon =  LibreriaBD.CampoInt(cursor, "PANTALON");
                int Zapato =  LibreriaBD.CampoInt(cursor, "ZAPATO");
                int Complemento =  LibreriaBD.CampoInt(cursor, "COMPLEMENTO");

                c.add(id);
                c.add(Abrigo);
                c.add(Sudadera);
                c.add(Camiseta);
                c.add(Pantalon);
                c.add(Zapato);
                c.add(Complemento);


                res.add(c);

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        return res;

    }


    public static List<Prenda> Ordenar_Prendas (Context context, int num ){// 0 nombre, 1 color, 2 tipo, 3 talla
        String orden;
        if(num==0) orden="NOMBRE";
        else if(num == 1) orden ="COLOR";
        else if(num == 2) orden = "TIPO";
        else orden= "TALLA";
        String sentenciaSQL = "SELECT ID, NOMBRE, COLOR, TIPO, TALLA FROM PRENDA WHERE VISIBLE = 1 ORDER BY " + orden;
        Cursor cursor;
        List<Prenda> res = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, nombreBD);
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
}
