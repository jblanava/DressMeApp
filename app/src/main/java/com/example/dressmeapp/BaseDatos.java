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
        String vsql;

        // TABLA PRENDA
        vsql = "CREATE TABLE \"PERFIL\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(50) NOT NULL, \"CONTRASENIA\" VARCHAR(50) NOT NULL)";
        db.execSQL(vsql);
        vsql = "CREATE TABLE \"PRENDA\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(50) NOT NULL, \"COLOR\" VARCHAR(50) NOT NULL" +
                ", \"TIPO\" INTEGER NOT NULL, \"TALLA\" INTEGER NOT NULL,\"VISIBLE\" INTEGER NOT NULL, FOREIGN KEY (\"ID_PERFIL\") REFERENCES \"PERFIL\" (\"ID\") )";

        db.execSQL(vsql);



        db.execSQL( "INSERT INTO PERFIL VALUES (1  , 'PEPE', 'LOTAD') ") ;
        db.execSQL( "INSERT INTO PRENDA VALUES (1  ,'CAMISETA_REXULONA','AZUL',1,1,1,1) ") ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) { }
    }

}
