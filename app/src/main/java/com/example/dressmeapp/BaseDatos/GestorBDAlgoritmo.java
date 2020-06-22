package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.dressmeapp.Objetos.Conjunto;
import com.example.dressmeapp.Objetos.Prenda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.StringJoiner;

public class GestorBDAlgoritmo {
    /* Clase relacionada con las operaciones sobre la BD y el algoritmo */

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Conjunto get_conjunto_algoritmo(Context context, int tiempo, int actividad, String nombreEvento)
    {
        List<Integer> todos_los_colores = GestorBD.get_ids_tabla(context, "color");
        int[] tiposAbrigo = {1, 7, 29, 30};
        int[] tiposSudadera = {10, 13, 15};
        int[] tiposCamiseta = {3, 4, 5, 12, 20, 21, 23};
        int[] tiposPantalon = {2, 9, 11, 18, 19};
        int[] tiposZapatos = {6, 14, 16, 17, 22};
        int[] tiposComplementos = {8, 24, 25, 26, 27, 28};

        int idCamiseta = get_prenda_condiciones(context, tiempo, actividad, tiposCamiseta, todos_los_colores);

        if(idCamiseta == -1) return null;

        int color_principal = GestorBD.get_id_tabla(context, "color", GestorBDPrendas.get_prenda(context, idCamiseta).color);

        List<Integer> coloresCombo = GestorBDPrendas.color_con_cuales_combina(context, color_principal);

        int idAbrigo        = get_prenda_condiciones(context, tiempo, actividad, tiposAbrigo, coloresCombo);
        int idSudadera      = get_prenda_condiciones(context, tiempo, actividad, tiposSudadera, coloresCombo);
        int idPantalon      = get_prenda_condiciones(context, tiempo, actividad, tiposPantalon, coloresCombo);
        int idZapatos       = get_prenda_condiciones(context, tiempo, actividad, tiposZapatos, coloresCombo);
        int idComplementos  = get_prenda_condiciones(context, tiempo, actividad, tiposComplementos, coloresCombo);

        if(idPantalon == -1)  idPantalon = get_prenda_condiciones(context, tiempo, actividad, tiposPantalon, todos_los_colores);
        if(idZapatos == -1)  idZapatos   = get_prenda_condiciones(context, tiempo, actividad, tiposZapatos, todos_los_colores);

        if(idPantalon == -1 || idZapatos == -1)
        {
            return null;
        }

        Conjunto res = new Conjunto(nombreEvento);

        // ID 0 = ID conjunto
        res.add(GestorBD.obtener_id_maximo(context, "CONJUNTO"));

        // IDs 1-6 = IDs prendas del conjunto
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

        String sentenciaSQL = "SELECT ID, TIPO FROM PRENDA  WHERE " + codicionTipos + " AND " + codicionColores + " AND VISIBLE = 1 AND ID_PERFIL = " + GestorBD.idPerfil;



        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        Cursor cursor;
        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        List<Integer> listaPrendas = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                int id = LibreriaBD.campo_int(cursor, "ID");
                int tipo = LibreriaBD.campo_int(cursor, "TIPO");


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

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                res = LibreriaBD.campo_int(cursor, "ACTIVIDAD");
            } while (cursor.moveToNext());
        }
        return res;
    }

    private static int tipo_tiempo(Context context, int idTipo) {
        String sentenciaSQL = "SELECT TIEMPO FROM TIPO WHERE ID = " + idTipo;
        Cursor cursor;
        int res = -1;

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                res = LibreriaBD.campo_int(cursor, "TIEMPO");
            } while (cursor.moveToNext());
        }
        return res;
    }


    public static List<Conjunto> get_conjuntos(Context context) {

        List<Conjunto> res = new ArrayList<>();


        String sentenciaSQL = "SELECT * FROM CONJUNTO WHERE ID_PERFIL = " + GestorBD.idPerfil;
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                Conjunto c = new Conjunto(LibreriaBD.campo_string(cursor, "NOMBRE_EVENTO"));

                int id = LibreriaBD.campo_int(cursor, "ID");
                int Abrigo = LibreriaBD.campo_int(cursor, "ABRIGO");
                int Sudadera = LibreriaBD.campo_int(cursor, "SUDADERA");
                int Camiseta = LibreriaBD.campo_int(cursor, "CAMISETA");
                int Pantalon = LibreriaBD.campo_int(cursor, "PANTALON");
                int Zapato = LibreriaBD.campo_int(cursor, "ZAPATO");
                int Complemento = LibreriaBD.campo_int(cursor, "COMPLEMENTO");

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


        String sentenciaSQL = "SELECT * FROM CONJUNTO WHERE ID_PERFIL = " + GestorBD.idPerfil + " AND FAVORITO=1";
        Cursor cursor;

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {

                Conjunto c = new Conjunto(LibreriaBD.campo_string(cursor, "NOMBRE_EVENTO"));

                int id = LibreriaBD.campo_int(cursor, "ID");
                int Abrigo = LibreriaBD.campo_int(cursor, "ABRIGO");
                int Sudadera = LibreriaBD.campo_int(cursor, "SUDADERA");
                int Camiseta = LibreriaBD.campo_int(cursor, "CAMISETA");
                int Pantalon = LibreriaBD.campo_int(cursor, "PANTALON");
                int Zapato = LibreriaBD.campo_int(cursor, "ZAPATO");
                int Complemento = LibreriaBD.campo_int(cursor, "COMPLEMENTO");

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

    public static int add_conjunto(Context contexto, Conjunto conj, int flag) {

        int id = GestorBD.obtener_id_maximo(contexto, "CONJUNTO"); // ID

        List<Integer> idPrendas = conj.getPrendas();
        ListIterator<Integer> it = idPrendas.listIterator();


        int[] tiposAbrigo = {1, 7, 29, 30};
        int[] tiposSudadera = {10, 13, 15};
        int[] tiposCamiseta = {3, 4, 5, 12, 20, 21, 23};
        int[] tiposPantalon = {2, 9, 11, 18, 19};
        int[] tiposZapatos = {6, 14, 16, 17, 22};
        int[] tiposComplementos = {8, 24, 25, 26, 27, 28};

        int abrigo = -1;
        int sudadera = -1;
        int camiseta = -1;
        int zapato = -1;
        int complemento = -1;
        int pantalon = -1;

        it.next();

        while(it.hasNext()) {

            int id_prenda = it.next();
            Prenda aux = GestorBDPrendas.get_prenda(contexto, id_prenda);

            if (aux == null) continue;

            int tipo = GestorBD.get_id_tabla(contexto, "tipo", aux.tipo);


            if(Arrays.binarySearch(tiposAbrigo, tipo) >= 0)
            {
                abrigo = id_prenda;
            }
            else if(Arrays.binarySearch(tiposSudadera, tipo) >= 0)
            {
                sudadera = id_prenda;
            }
            else if(Arrays.binarySearch(tiposCamiseta, tipo) >= 0)
            {
                camiseta = id_prenda;
            }
            else if(Arrays.binarySearch(tiposPantalon, tipo) >= 0)
            {
                pantalon = id_prenda;
            }
            else if(Arrays.binarySearch(tiposZapatos, tipo) >= 0)
            {
                zapato = id_prenda;
            }
            else if(Arrays.binarySearch(tiposComplementos, tipo) >= 0)
            {
                complemento = id_prenda;
            }

        } // Fin de while

        if(abrigo != -1 || sudadera != -1 || camiseta != -1 || pantalon != -1 || zapato != -1 ||complemento != -1)
        {
            String sentenciaSQL;
            sentenciaSQL = "INSERT INTO CONJUNTO (ID, ABRIGO, SUDADERA, CAMISETA, PANTALON, ZAPATO, COMPLEMENTO, ID_PERFIL, FAVORITO, NOMBRE_EVENTO) VALUES ('";
            sentenciaSQL += id + "','" + abrigo + "', '" + sudadera + "', '" + camiseta + "','" + pantalon + "', '" + zapato + "', '" + complemento + "', '" + GestorBD.idPerfil + "', '" + flag + "', '"+ conj.getNombreCjto() +"' )";

            BaseDatos base = new BaseDatos(contexto, BaseDatos.nombreBD);
            SQLiteDatabase baseDatos;
            baseDatos = base.getWritableDatabase();
            baseDatos.execSQL(sentenciaSQL);
            baseDatos.close();
            base.close();
        }
        return id;

    }

}
