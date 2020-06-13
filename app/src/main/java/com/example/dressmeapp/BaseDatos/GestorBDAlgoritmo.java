package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.example.dressmeapp.Objetos.Conjunto;
import com.example.dressmeapp.Objetos.Prenda;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.StringJoiner;

import androidx.annotation.RequiresApi;

public class GestorBDAlgoritmo {
    /* Clase relacionada con las operaciones sobre la BD y el algoritmo */
    /**
     * El ID del perfil que tiene la sesión abierta actualmente.
     */
    public static int idPerfil;

    private static String nombreBD = "dressmeapp30.db";

    public static void seleccionarBD(String nombreBD) {
        GestorBDAlgoritmo.nombreBD = nombreBD;
    }

    public static int getIdPerfil() {
        return idPerfil;
    }

    public static void setIdPerfil(int nuevoId) {
        idPerfil = nuevoId;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Conjunto get_conjunto_algoritmo(Context context, int tiempo, int actividad)
    {
        List<Integer> todos_los_colores = GestorBD.get_ids_tabla(context, "color");
        int[] tiposAbrigo = {1, 7};
        int[] tiposSudadera = {10, 13};
        int[] tiposCamiseta = {3, 4, 5, 12};
        int[] tiposPantalon = {9, 11};
        int[] tiposZapatos = {6, 14, 16};
        int[] tiposComplementos = {8};

        int idCamiseta = get_prenda_condiciones(context, tiempo, actividad, tiposCamiseta, todos_los_colores);

        int color_principal = GestorBD.get_id_tabla(context, "color", GestorBDPrendas.get_prenda(context, idCamiseta).color);

        List<Integer> coloresCombo = GestorBDPrendas.ColorCombo(context, color_principal);

        int idAbrigo        = get_prenda_condiciones(context, tiempo, actividad, tiposAbrigo, coloresCombo);
        int idSudadera      = get_prenda_condiciones(context, tiempo, actividad, tiposSudadera, coloresCombo);
        int idPantalon      = get_prenda_condiciones(context, tiempo, actividad, tiposPantalon, coloresCombo);
        int idZapatos       = get_prenda_condiciones(context, tiempo, actividad, tiposZapatos, coloresCombo);
        int idComplementos  = get_prenda_condiciones(context, tiempo, actividad, tiposComplementos, coloresCombo);

        if(idPantalon == -1)  idPantalon = get_prenda_condiciones(context, tiempo, actividad, tiposPantalon, todos_los_colores);
        if(idZapatos == -1)  idZapatos   = get_prenda_condiciones(context, tiempo, actividad, tiposZapatos, todos_los_colores);

        if(idPantalon == -1 || idZapatos == -1)
        {
            return null; // TODO esto es una chapuza que luego abra que solucionar
        }

        Conjunto res = new Conjunto();

        res.add(idAbrigo);
        res.add(idSudadera);
        res.add(idCamiseta);
        res.add(idPantalon);
        res.add(idZapatos);
        res.add(idComplementos);

        return res;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private static int get_prenda_condiciones(Context context, int tiempo, int actividad, int[] tipos, List<Integer> colores)
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


                int pActividad = tipo_actividad(context, tipo);
                int pTiempo = tipo_tiempo(context, tipo);

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



    private static int tipo_actividad(Context context, int idTipo) {
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

    private static int tipo_tiempo(Context context, int idTipo) {
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


    public static List<Conjunto> get_conjuntos(Context context) {

        List<Conjunto> res = new ArrayList<>();


        String sentenciaSQL = "SELECT * FROM CONJUNTO WHERE ID_PERFIL = " + idPerfil;
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                Conjunto c = new Conjunto(LibreriaBD.Campo(cursor, "NOMBRE_EVENTO"));

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

    public static List<Conjunto> get_conjuntos_favoritos(Context context) {

        List<Conjunto> res = new ArrayList<>();


        String sentenciaSQL = "SELECT * FROM CONJUNTO WHERE ID_PERFIL = " + idPerfil + " AND FAVORITO=1";
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                Conjunto c = new Conjunto(LibreriaBD.Campo(cursor, "NOMBRE_EVENTO"));

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

    public static void add_conjunto(Context contexto, Conjunto conj, int flag) {

        int id = GestorBD.obtener_id_maximo(contexto, "CONJUNTO"); // ID

        List<Integer> idPrendas = conj.getPrendas();
        ListIterator<Integer> it = idPrendas.listIterator();


        int abrigo = -1;
        int sudadera = -1;
        int camiseta = -1;
        int zapato = -1;
        int complemento = -1;
        int pantalon = -1;




        while(it.hasNext()) {

            Prenda aux = GestorBDPrendas.get_prenda(contexto, it.next());

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
            sentenciaSQL = "INSERT INTO CONJUNTO (ID, ABRIGO, SUDADERA, CAMISETA, PANTALON, ZAPATO, COMPLEMENTO, ID_PERFIL, FAVORITO, NOMBRE_EVENTO) VALUES ('";
            sentenciaSQL += id + "','" + abrigo + "', '" + sudadera + "', '" + camiseta + "','" + pantalon + "', '" + zapato + "', '" + complemento + "', '" + idPerfil + "', '" + flag + "', '"+ conj.getNombreCjto() +"' )";

            BaseDatos base = new BaseDatos(contexto, nombreBD);
            SQLiteDatabase baseDatos;
            baseDatos = base.getWritableDatabase();
            baseDatos.execSQL(sentenciaSQL);
            baseDatos.close();
            base.close();
        }

    }

}
