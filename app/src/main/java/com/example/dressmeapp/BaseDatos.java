package com.example.dressmeapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class BaseDatos extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NOMBRE_BASE_DATOS = "dressmeapp1.db";


    private Context contexto;

    public BaseDatos (Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear todas las tablas...
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) { }
    }

}
