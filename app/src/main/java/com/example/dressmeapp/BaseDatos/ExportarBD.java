package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dressmeapp.Objetos.Structs.ColorBD;
import com.example.dressmeapp.Objetos.Structs.ComboColorBD;
import com.example.dressmeapp.Objetos.Structs.ConjuntoBD;
import com.example.dressmeapp.Objetos.Structs.FotoBD;
import com.example.dressmeapp.Objetos.Structs.PerfilBD;
import com.example.dressmeapp.Objetos.Structs.PrendaBD;
import com.example.dressmeapp.Objetos.Structs.TallaBD;

import java.util.ArrayList;
import java.util.List;

public class ExportarBD {
    /* Operaciones para exportar la base de datos a un fichero que el usuario pueda tener */

    static public List<ColorBD> expColores(Context context)
    {
        String sentenciaSQL = "SELECT * FROM COLOR";

        Cursor cursor;
        List<ColorBD> colores = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                ColorBD c = new ColorBD();

                c.id = LibreriaBD.campo_int(cursor, "ID");
                c.nombre = LibreriaBD.campo_string(cursor, "NOMBRE");
                c.hex = LibreriaBD.campo_string(cursor, "HEX");

                colores.add(c);
            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();

        return colores;
    }

    static public List<ComboColorBD> expComboColor(Context context) {
        String sentenciaSQL = "SELECT * FROM COMBO_COLOR";

        Cursor cursor;
        List<ComboColorBD> combos = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                ComboColorBD cc = new ComboColorBD();

                cc.id = LibreriaBD.campo_int(cursor, "ID");
                cc.color1 = LibreriaBD.campo_int(cursor, "COLOR1");
                cc.color2 = LibreriaBD.campo_int(cursor, "COLOR2");

                combos.add(cc);
            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();

        return combos;
    }

    static public List<PerfilBD> expPerfil(Context context)
    {
        String sentenciaSQL = "SELECT * FROM PERFIL WHERE ID = " + GestorBD.idPerfil;

        Cursor cursor;
        List<PerfilBD> perfiles = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                PerfilBD c = new PerfilBD();

                c.id = LibreriaBD.campo_int(cursor, "ID");
                c.clave = LibreriaBD.campo_string(cursor, "CONTRASENIA");
                c.usuario = LibreriaBD.campo_string(cursor, "NOMBRE");

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
        String sentenciaSQL = "SELECT * FROM PRENDA WHERE ID_PERFIL = " + GestorBD.idPerfil;

        Cursor cursor;
        List<PrendaBD> prendas = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                PrendaBD c = new PrendaBD();

                c.id = LibreriaBD.campo_int(cursor, "ID");
                c.color = LibreriaBD.campo_int(cursor, "COLOR");
                c.nombre = LibreriaBD.campo_string(cursor, "NOMBRE");
                c.perfil = LibreriaBD.campo_int(cursor,"ID_PERFIL");
                c.talla = LibreriaBD.campo_int(cursor,"TALLA");
                c.tipo = LibreriaBD.campo_int(cursor,"TIPO");
                c.visible = LibreriaBD.campo_int(cursor,"VISIBLE");
                c.foto = LibreriaBD.campo_int(cursor,"FOTO");

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
        String sentenciaSQL = "SELECT * FROM TALLA";

        Cursor cursor;
        List<TallaBD> tallas = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                TallaBD c = new TallaBD();

                c.id = LibreriaBD.campo_int(cursor, "ID");

                c.nombre = LibreriaBD.campo_string(cursor, "NOMBRE");

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
        String sentenciaSQL = "SELECT * FROM CONJUNTO WHERE ID_PERFIL = " + GestorBD.idPerfil;

        Cursor cursor;
        List<ConjuntoBD> conjuntos = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                ConjuntoBD c = new ConjuntoBD();

                c.id = LibreriaBD.campo_int(cursor, "ID");
                c.prendas[0] = LibreriaBD.campo_int(cursor, "ABRIGO");
                c.prendas[1] = LibreriaBD.campo_int(cursor, "SUDADERA");
                c.prendas[2] = LibreriaBD.campo_int(cursor, "CAMISETA");
                c.prendas[3] = LibreriaBD.campo_int(cursor, "PANTALON");
                c.prendas[4] = LibreriaBD.campo_int(cursor, "ZAPATO");
                c.prendas[5] = LibreriaBD.campo_int(cursor, "COMPLEMENTO");
                c.perfil = LibreriaBD.campo_int(cursor,"ID_PERFIL");


                conjuntos.add(c);
            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();

        return conjuntos;
    }

    static public List<FotoBD> expFotos(Context context) {
        String sentenciaSQL = "SELECT * FROM FOTOS";

        Cursor cursor;
        List<FotoBD> fotos = new ArrayList<>();

        BaseDatos base = new BaseDatos(context, BaseDatos.nombreBD);
        SQLiteDatabase baseDatos = base.getReadableDatabase();

        cursor = baseDatos.rawQuery(sentenciaSQL, null);

        if (cursor.moveToFirst()) {
            do {
                FotoBD f = new FotoBD();

                f.id = LibreriaBD.campo_int(cursor, "ID");
                f.datos = cursor.getBlob(cursor.getColumnIndex("FOTO"));

                fotos.add(f);
            } while (cursor.moveToNext());
        }
        baseDatos.close();
        base.close();
        cursor.close();

        return fotos;
    }
}
