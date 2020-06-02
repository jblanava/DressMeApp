package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompatSideChannelService;

import com.example.dressmeapp.Activities.AniadirPrendaActivity;
import com.example.dressmeapp.Objetos.ColorPrenda;
import com.example.dressmeapp.Objetos.Conjunto;
import com.example.dressmeapp.Objetos.Prenda;
import com.example.dressmeapp.Objetos.Structs.ColorBD;
import com.example.dressmeapp.Objetos.Structs.ConjuntoBD;
import com.example.dressmeapp.Objetos.Structs.PerfilBD;
import com.example.dressmeapp.Objetos.Structs.PrendaBD;
import com.example.dressmeapp.Objetos.Structs.TallaBD;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.StringJoiner;


public class GestorBD {

    /**
     * El ID del perfil que tiene la sesión abierta actualmente.
     */
    public static int idPerfil;


    private static Context contexto; // TODO: eliminar en el futuro
    private static String nombreBD = "dressmeapp21.db"; // Antonio ha cambiado a la BD__17

    public GestorBD(Context context)  // TODO: Eliminar?
    {
        contexto = context;
    }

    public static void seleccionarBD(String nombreBD) {
        GestorBD.nombreBD = nombreBD;
    }


    ///////////////////////77
/// PERFIL

    public static int getIdPerfil() {
        return idPerfil;
    }

    public static void setIdPerfil(int nuevoId) {
        idPerfil = nuevoId;
    }


    /**
     * Devuelve el ID correspondiente al siguiente perfil que se cree.
     * (Este método debería ser privado, ya que solo se invoca al crear un perfil.)
     *
     * @param context El contexto a usar.
     * @return El ID correspondiente al siguiente perfil que se cree (máximo + 1)
     */
    public static int obtenIDMaximoPerfil(Context context) {
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
     *
     * @param context El contexto a usar.
     * @return El ID correspondiente a la siguiente prenda que se cree.
     */
    public static int obtenIDMaximoPrenda(Context context) {
        int resultado = 0;
        String sentenciaSQL = "SELECT MAX(ID) AS MAXID FROM PRENDA";

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
     * Comprueba si un string corresponde a un nombre de perfil existente.
     *
     * @param context El contexto en el que comprobar.
     * @param nombre  El nombre a buscar
     * @return true sii el nombre corresponde a algún perfil
     */
    public static boolean UsuarioEstaEnBD(Context context, String nombre) {
        // clase Registro
        String sentenciaSQL;
        sentenciaSQL = "SELECT ID FROM PERFIL WHERE NOMBRE='" + nombre + "'";
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
     *
     * @param context  El contexto en el cual comprobar.
     * @param usuario  El usuario cuyo ID buscar
     * @param password La contraseña cuyo ID buscar
     * @return El ID del perfil definido, o 0 si no existe un perfil con los datos dados
     */
    public static int IdPerfilAsociado(Context context, String usuario, String password) {

        int id = 0;
        String sentenciaSQL = "SELECT ID FROM PERFIL WHERE NOMBRE ='" + usuario + "' AND CONTRASENIA ='" + password + "'";

        Cursor cursor;
        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            id = LibreriaBD.CampoInt(cursor, "ID");
        }


        baseDatos.close();
        base.close();
        cursor.close();
        return id;
    }

    /**
     * Comprueba si el par (nombre de perfil, contraseña) dado corresponde a un usuario.
     *
     * @param contexto El contexto en el cual comprobar.
     * @param usuario  El nombre del perfil a comprobar
     * @param password La contraseña del perfil a comprobar.
     * @return true sii el par (nombre de perfil, contraseña) corresponden a un usuario.
     */
    public static boolean PassCorrecta(Context contexto, String usuario, String password) {

        boolean encontrado;

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
     *
     * @param contexto    El contexto en el que crear el perfil
     * @param usuario     El nombre del perfil
     * @param contrasenia La contraseña para el perfil.
     */
    public static int CrearPerfil(Context contexto, String usuario, String contrasenia) {
        int id = obtenIDMaximoPerfil(contexto);
        String sentenciaSQL;
        sentenciaSQL = "INSERT INTO PERFIL (ID, NOMBRE,  CONTRASENIA) VALUES (";
        sentenciaSQL += id + ",'" + usuario.trim() + "', '" + contrasenia.trim() + "')";

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
        return id;
    }

    public static void BorrarPerfil(Context contexto, int id) {

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

    public static void ActualizarPerfil(Context contexto, int idPerfil, String password) {

        String sentenciaSQL = "UPDATE PERFIL SET CONTRASENIA='"
                + password + "' WHERE ID=" + idPerfil;
        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();

    }

    public static String getUser(Context context, int idPerfil) {
        String SentenciaSQL = "SELECT NOMBRE FROM PERFIL WHERE ID=" + idPerfil;
        String res = "";
        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getWritableDatabase();
        Cursor cursor = baseDatos.rawQuery(SentenciaSQL, null);
        if (cursor.moveToFirst()) res = LibreriaBD.Campo(cursor, "NOMBRE");
        base.close();
        baseDatos.close();
        cursor.close();
        return res;
    }

    public static String getPass(Context context, int idPerfil) {
        String SentenciaSQL = "SELECT CONTRASENIA FROM PERFIL WHERE ID=" + idPerfil;
        String res = "";
        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getWritableDatabase();
        Cursor cursor = baseDatos.rawQuery(SentenciaSQL, null);
        if (cursor.moveToFirst()) res = LibreriaBD.Campo(cursor, "CONTRASENIA");
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
    public static int crearPrenda(Context contexto, String nombre, int color, int tipo, int talla, int visible, int id_perfil) {

        int id = obtenIDMaximoPrenda(contexto);
        String sentenciaSQL;
        sentenciaSQL = String.format("INSERT INTO PRENDA VALUES (%d, '%s', '%d', %d, %d, %d, %d)", id, nombre, color, tipo, talla, visible, id_perfil);

        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
        return id;
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

    public static void BorrarConjunto(int idConjunto) {
        String sentenciaSQL;
        sentenciaSQL = "DELETE FROM CONJUNTO WHERE ID = " + String.valueOf(idConjunto);
        BaseDatos base = new BaseDatos(contexto, nombreBD);
        SQLiteDatabase baseDatos;
        baseDatos = base.getWritableDatabase();
        baseDatos.execSQL(sentenciaSQL);
        baseDatos.close();
        base.close();
    }

    public static Prenda Obtener_Prenda(Context context, int id) {


        String sentenciaSQL =  "SELECT PRENDA.NOMBRE \"NOMBRE\", COLOR.NOMBRE \"COLOR\", TIPO.NOMBRE \"TIPO\", TALLA.NOMBRE \"TALLA\" ";
        sentenciaSQL += "FROM PRENDA, COLOR, TIPO, TALLA ";
        sentenciaSQL += "WHERE PRENDA.VISIBLE = 1 AND PRENDA.ID = " + id + " ";
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

    public static String get_nombre_tabla(Context context, String tabla, int id)
    {
        String sentenciaSQL = String.format("SELECT NOMBRE FROM %s WHERE ID = %d", tabla.toUpperCase(), id);
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();
        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        String nombre = "Error";

        if (cursor.moveToFirst()) {
            nombre = LibreriaBD.Campo(cursor, "NOMBRE");
        }

        baseDatos.close();
        base.close();
        cursor.close();
        return nombre;
    }

    public static List<String> get_nombres_tabla(Context context, String tabla)
    {
        String sentenciaSQL = "SELECT NOMBRE FROM " + tabla.toUpperCase();

        Cursor cursor;
        List<String> res = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                String nombre = LibreriaBD.Campo(cursor, "NOMBRE");

                res.add(nombre);

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        return res;
    }

    public static int get_id_tabla(Context context, String tabla, String nombre)
    {
        String sentenciaSQL = String.format("SELECT ID FROM %s WHERE UPPER(NOMBRE) LIKE '%s'", tabla.toUpperCase(), nombre.toUpperCase());
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();
        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        int id = -1;

        if (cursor.moveToFirst()) {
            id = LibreriaBD.CampoInt(cursor, "ID");
        }

        baseDatos.close();
        base.close();
        cursor.close();
        return id;
    }

    public static List<Integer> get_ids_tabla(Context context, String tabla)
    {
        String sentenciaSQL = "SELECT ID FROM " + tabla.toUpperCase();

        Cursor cursor;
        List<Integer> res = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                int id = LibreriaBD.CampoInt(cursor, "ID");

                res.add(id);

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void Modificar_Prenda(Context context, Prenda p) {
        CambiarVisibilidadPrenda(context, p.id);

        int color = GestorBD.get_id_tabla(context, "color", p.color);
        int tipo = GestorBD.get_id_tabla(context, "tipo", p.tipo);
        int talla = GestorBD.get_id_tabla(context, "talla", p.talla);

        crearPrenda(context, p.nombre, color, tipo, talla, 1, getIdPerfil());
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














    ///// Algoritmo


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Conjunto resAlgoritmo(Context context, int tiempo, int actividad) {

        List<Integer> colores = get_ids_tabla(context, "color");

        Random rng = new Random();


        int[] tiposAbrigo = {1, 7};
        int[] tiposSudadera = {10, 13};
        int[] tiposCamiseta = {3, 4, 5, 12};
        int[] tiposPantalon = {9, 11};
        int[] tiposZapatos = {6, 14, 16};
        int[] tiposComplementos = {8};


        Conjunto res = new Conjunto();

        int prendasAdicionales = rng.nextInt(3);

        int idColor = colores.get(rng.nextInt(colores.size()));

        List<Integer> coloresCombo = ColorCombo(context, idColor);

        int idAbrigo = NombreTemporal(context, tiempo, actividad, tiposAbrigo, coloresCombo);
        if(idAbrigo == -1)
        {
            idAbrigo = NombreTemporal(context, tiempo, actividad, tiposAbrigo, colores);
        }
        int idSudadera = NombreTemporal(context, tiempo, actividad, tiposSudadera, coloresCombo);
        if(idSudadera == -1)
        {
            idSudadera = NombreTemporal(context, tiempo, actividad, tiposSudadera, colores);
        }
        int idCamiseta = NombreTemporal(context, tiempo, actividad, tiposCamiseta, coloresCombo);
        if(idCamiseta == -1)
        {
            idCamiseta = NombreTemporal(context, tiempo, actividad, tiposCamiseta, colores);
        }
        int idPantalon = NombreTemporal(context, tiempo, actividad, tiposPantalon, coloresCombo);
        if(idPantalon == -1)
        {
            idPantalon = NombreTemporal(context, tiempo, actividad, tiposPantalon, colores);
        }
        int idZapatos = NombreTemporal(context, tiempo, actividad, tiposZapatos, coloresCombo);
        if(idZapatos == -1)
        {
            idZapatos = NombreTemporal(context, tiempo, actividad, tiposZapatos, colores);
        }
        int idCompementos = NombreTemporal(context, tiempo, actividad, tiposComplementos, coloresCombo);
        if(idCompementos == -1)
        {
            idCompementos = NombreTemporal(context, tiempo, actividad, tiposComplementos, colores);
        }


        res.add(idAbrigo);
        res.add(idSudadera);
        res.add(idCamiseta);
        res.add(idPantalon);
        res.add(idZapatos);
        res.add(idCompementos);

        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int NombreTemporal(Context context, int tiempo, int actividad, int[] tipos, List<Integer> colores) // TODO: Buscarle un nombre a esto
    {
        StringJoiner sj = new StringJoiner(" OR ", "( ", " )");

        for (int tipo : tipos) {
            sj.add("TIPO = " + tipo);
        }

        String codicionTipos = sj.toString();

        sj = new StringJoiner(" OR ", "( ", " )");

        for (int color : colores) {
            sj.add("COLOR = " + color);
        }

        String codicionColores = sj.toString();

        String sentenciaSQL = "SELECT ID, TIPO FROM PRENDA  WHERE " + codicionTipos + " AND " + codicionColores + " AND VISIBLE = 1 AND ID_PERFIL = " + idPerfil;



        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        Cursor cursor;
        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        List<Integer> listaPrendas = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                int id = LibreriaBD.CampoInt(cursor, "ID");
                int tipo = LibreriaBD.CampoInt(cursor, "TIPO");


                int pActividad = tipoActividad(context, tipo);
                int pTiempo = tipoTiempo(context, tipo);

                if((pActividad & actividad) != 0 && (pTiempo & tiempo) != 0)
                {
                    listaPrendas.add(id);
                }

            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();
        Random r = new Random();

        if(listaPrendas.size() == 0)
        {
            return -1;
        }


        int i = r.nextInt(listaPrendas.size());


        return listaPrendas.get(i);

    }

    public static List<Integer> ColorCombo (Context context, int idColor)
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

    public static int tipoActividad(Context context, int idTipo) {
        String sentenciaSQL = "SELECT ACTIVIDAD FROM TIPO WHERE ID = " + idTipo;
        Cursor cursor;
        int res = -1;

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                res = LibreriaBD.CampoInt(cursor, "ACTIVIDAD");
            } while (cursor.moveToNext());
        }
        return res;
    }

    public static int tipoTiempo(Context context, int idTipo) {
        String sentenciaSQL = "SELECT TIEMPO FROM TIPO WHERE ID = " + idTipo;
        Cursor cursor;
        int res = -1;

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                res = LibreriaBD.CampoInt(cursor, "TIEMPO");
            } while (cursor.moveToNext());
        }
        return res;
    }


    public static List<Conjunto> ConjuntosEnBD(Context context) {

        List<Conjunto> res = new ArrayList<>();


        String sentenciaSQL = "SELECT * FROM CONJUNTO WHERE ID_PERFIL = " + idPerfil;
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                Conjunto c = new Conjunto();

                int id = LibreriaBD.CampoInt(cursor, "ID");
                int Abrigo = LibreriaBD.CampoInt(cursor, "ABRIGO");
                int Sudadera = LibreriaBD.CampoInt(cursor, "SUDADERA");
                int Camiseta = LibreriaBD.CampoInt(cursor, "CAMISETA");
                int Pantalon = LibreriaBD.CampoInt(cursor, "PANTALON");
                int Zapato = LibreriaBD.CampoInt(cursor, "ZAPATO");
                int Complemento = LibreriaBD.CampoInt(cursor, "COMPLEMENTO");

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

    public static int obtenIDMaximoConjunto(Context context) {
        int resultado = 0;
        String sentenciaSQL = "SELECT MAX(ID) AS MAXID FROM CONJUNTO";

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


        public static  int getTipoID (Context context, int idPrenda){
            int resultado = -1;
            String sentenciaSQL = "SELECT TIPO.ID  FROM TIPO, PRENDA   WHERE PRENDA.TIPO = TIPO.NOMBRE AND PRENDA.ID = " + idPrenda;

            Cursor cursor;
            BaseDatos base = new BaseDatos(context, nombreBD);
            SQLiteDatabase baseDatos = base.getReadableDatabase();

            cursor = baseDatos.rawQuery(sentenciaSQL, null);
            if (cursor.moveToFirst()) {
                do {
                    resultado = LibreriaBD.CampoInt(cursor, "TIPO.ID");
                } while (cursor.moveToNext());
            }

            baseDatos.close();
            base.close();
            cursor.close();
            return resultado;
        }


        public static int addConjunto(Context contexto, Conjunto conj) {

            int id = obtenIDMaximoConjunto(contexto); // ID

            List<Integer> idPrendas = conj.getPrendas();
            ListIterator<Integer> it = idPrendas.listIterator();


            int abrigo = -1;
            int sudadera = -1;
            int camiseta = -1;
            int zapato = -1;
            int complemento = -1;
            int pantalon = -1;




            while(it.hasNext()) {

                Prenda aux = Obtener_Prenda(contexto, it.next());

                if(aux != null){

                    //Abrigo:
                    if(aux.tipo.equalsIgnoreCase("abrigo") || aux.tipo.equalsIgnoreCase("chaqueta")) {
                        abrigo = aux.id;
                    }
                    // Sudadera
                    else if(aux.tipo.equalsIgnoreCase("jersey") || aux.tipo.equalsIgnoreCase("sudadera")|| aux.tipo.equalsIgnoreCase("polar") ) {
                        sudadera = aux.id;
                    }
                    //Camiseta
                    else if(aux.tipo.equalsIgnoreCase("blusa") || aux.tipo.equalsIgnoreCase("camisa") || aux.tipo.equalsIgnoreCase("camiseta m.corta") || aux.tipo.equalsIgnoreCase("polo") || aux.tipo.equalsIgnoreCase("traje") || aux.tipo.equalsIgnoreCase("chandal") || aux.tipo.equalsIgnoreCase("vestido") || aux.tipo.equalsIgnoreCase("camiseta m.larga") || aux.tipo.equalsIgnoreCase("top")  ) {
                        camiseta = aux.id;
                    }
                    //Pantalon
                    else if(aux.tipo.equalsIgnoreCase("bañador/bikini") || aux.tipo.equalsIgnoreCase("falda") || aux.tipo.equalsIgnoreCase("pantalon") || aux.tipo.equalsIgnoreCase("shorts") || aux.tipo.equalsIgnoreCase("bermudas") ) {
                        pantalon = aux.id;
                    }
                    //Zapatos
                    else if(aux.tipo.equalsIgnoreCase("chanclas") || aux.tipo.equalsIgnoreCase("tennis") || aux.tipo.equalsIgnoreCase("zapatos/tacones") || aux.tipo.equalsIgnoreCase("sandalias") || aux.tipo.equalsIgnoreCase("zapatillas") ) {
                        zapato = aux.id;
                    }
                    //Complemento
                    else if(aux.tipo.equalsIgnoreCase("complemento")) {
                        complemento = aux.id;
                    }

                } // Fin de if


            } // Fin de while

            if(abrigo != -1 || sudadera != -1 || camiseta != -1 || pantalon != -1 || zapato != -1 ||complemento != -1)
            {
                String sentenciaSQL;
                sentenciaSQL = "INSERT INTO CONJUNTO (ID, ABRIGO, SUDADERA, CAMISETA, PANTALON, ZAPATO, COMPLEMENTO, ID_PERFIL) VALUES ('";
                sentenciaSQL += id + "','" + abrigo + "', '" + sudadera + "', '" + camiseta + "','" + pantalon + "', '" + zapato + "', '" + complemento + "', '" + idPerfil + "' )";

                BaseDatos base = new BaseDatos(contexto, nombreBD);
                SQLiteDatabase baseDatos;
                baseDatos = base.getWritableDatabase();
                baseDatos.execSQL(sentenciaSQL);
                baseDatos.close();
                base.close();
            }
            return id;
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

    public static int obtenIDMaximoTalla(Context context) {
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









    // Funciones para exportar TODO hacer las funciones que falten

    static public List<ColorBD> expColores(Context context)
    {
        String sentenciaSQL = "SELECT * FROM COLOR";

        Cursor cursor;
        List<ColorBD> colores = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                ColorBD c = new ColorBD();

                c.id = LibreriaBD.CampoInt(cursor, "ID");
                c.nombre = LibreriaBD.Campo(cursor, "NOMBRE");
                c.hex = LibreriaBD.Campo(cursor, "HEX");

                colores.add(c);
            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();

        return colores;
    }

    static public List<PerfilBD> expPerfiles(Context context)
    {
        String sentenciaSQL = "SELECT * FROM PERFIL";

        Cursor cursor;
        List<PerfilBD> perfiles = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                PerfilBD c = new PerfilBD();

                c.id = LibreriaBD.CampoInt(cursor, "ID");
                c.clave = LibreriaBD.Campo(cursor, "CONTRASENIA");
                c.usuario = LibreriaBD.Campo(cursor, "NOMBRE");

                perfiles.add(c);
            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();

        return perfiles;
    }

    static public List<PrendaBD> expPrendas(Context context)
    {
        String sentenciaSQL = "SELECT * FROM PRENDA";

        Cursor cursor;
        List<PrendaBD> prendas = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                PrendaBD c = new PrendaBD();

                c.id = LibreriaBD.CampoInt(cursor, "ID");
                c.color = LibreriaBD.CampoInt(cursor, "COLOR");
                c.nombre = LibreriaBD.Campo(cursor, "NOMBRE");
                c.perfil = LibreriaBD.CampoInt(cursor,"ID_PERFIL");
                c.talla = LibreriaBD.CampoInt(cursor,"TALLA");
                c.tipo = LibreriaBD.CampoInt(cursor,"TIPO");
                c.visible = LibreriaBD.CampoInt(cursor,"VISIBLE");

                prendas.add(c);
            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();

        return prendas;
    }
    static public List<TallaBD> expTallas(Context context)
    {
        String sentenciaSQL = "SELECT * FROM PRENDA";

        Cursor cursor;
        List<TallaBD> tallas = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                TallaBD c = new TallaBD();

                c.id = LibreriaBD.CampoInt(cursor, "ID");

                c.nombre = LibreriaBD.Campo(cursor, "NOMBRE");

                tallas.add(c);
            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();

        return tallas;
    }

    static public List<ConjuntoBD> expConjuntos(Context context)
    {
        String sentenciaSQL = "SELECT * FROM PRENDA";

        Cursor cursor;
        List<ConjuntoBD> conjuntos = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                ConjuntoBD c = new ConjuntoBD();

                c.id = LibreriaBD.CampoInt(cursor, "ID");
                c.prendas[0] = LibreriaBD.CampoInt(cursor, "ABRIGO");
                c.prendas[1] = LibreriaBD.CampoInt(cursor, "SUDADERA");
                c.prendas[2] = LibreriaBD.CampoInt(cursor, "CAMISETA");
                c.prendas[3] = LibreriaBD.CampoInt(cursor, "PANTALON");
                c.prendas[4] = LibreriaBD.CampoInt(cursor, "ZAPATO");
                c.prendas[5] = LibreriaBD.CampoInt(cursor, "COMPLEMENTO");
                c.perfil = LibreriaBD.CampoInt(cursor,"ID_PERFIL");


                conjuntos.add(c);
            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();

        return conjuntos;
    }





} // Fin de clase