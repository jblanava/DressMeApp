package com.example.dressmeapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class BaseDatos extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NOMBRE_BASE_DATOS = "dressmeapp2.db";


    public BaseDatos (Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        // TABLA PRENDA

        crearTablas(db);
        crearColor(db);
        crearTalla(db);
        crearTipos(db);

        db.execSQL( "INSERT INTO PERFIL VALUES (1  , 'PEPE', 'LOTAD') ") ;
        db.execSQL( "INSERT INTO PRENDA VALUES (6  ,'CAMISETA_REXULONA','AZUL',1,1,1,1) ") ;
        db.execSQL( "INSERT INTO PRENDA VALUES (3  ,'patalon','w3eZUL',1,1,1,1) ") ;
        db.execSQL( "INSERT INTO PRENDA VALUES (1  ,'PRIMERA',1,1,1,1,1) ") ;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) { }
    }

    private void crearTablas(SQLiteDatabase db){
        String vsql;
        vsql = "CREATE TABLE \"PERFIL\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(50) NOT NULL, \"CONTRASENIA\" VARCHAR(50) NOT NULL)";
        db.execSQL(vsql);
        vsql = "CREATE TABLE \"PRENDA\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(50) NOT NULL, \"COLOR\" VARCHAR(50) NOT NULL" +
                ", \"TIPO\" INTEGER NOT NULL, \"TALLA\" INTEGER NOT NULL,\"VISIBLE\" INTEGER NOT NULL, \"ID_PERFIL\" INTEGER NOT NULL )";

        db.execSQL(vsql);

        vsql = "CREATE TABLE \"ENTRADA_HISTORIAL\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(50) NOT NULL, \"FORMALIDAD\" INTEGER NOT NULL" +
                ", \"TIEMPO\" INTEGER NOT NULL, \"TEMPERATURA\" INTEGER NOT NULL,\"FECHA\" INTEGER NOT NULL, \"ID_PRENDA\" INTEGER NOT NULL  )";

        db.execSQL(vsql);

        vsql = "CREATE TABLE \"CONJUNTO\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"ABRIGO\" INTEGER ,\"SUDADERA\" INTEGER , \"CAMISETA\" INTEGER NOT NULL, " +
                "\"PANTALON\" INTEGER NOT NULL, \"ZAPATO\" INTEGER NOT NULL, \"COMPLEMENTO\" INTEGER )";

        db.execSQL(vsql);

        db.execSQL("CREATE TABLE \"COLOR\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(20) NOT NULL)");
        db.execSQL("CREATE TABLE \"TIPO\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(20) NOT NULL)");
        db.execSQL("CREATE TABLE \"TALLA\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(20) NOT NULL)");
    }

    private void crearTipos(SQLiteDatabase db){
        // ACTUALIZAR LOS VALORES


        db.execSQL( "INSERT INTO TIPO VALUES (1  , 'ABRIGO') ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (2  , 'BLUSA') ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (3 , 'BAÑADOR/BIKINI') ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (4  , 'CHAQUETA') ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (5  , 'CAMISA'  ) ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (6  , 'CAMISETA' ) ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (7 , 'CHANCLAS') ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (8  , 'FALDA' ) ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (9  , 'PANTALON') ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (10  , 'POLO') ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (11  , 'SUDADERA') ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (12 , 'TRAJE') ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (13 , 'TENNIS') ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (14  , 'JERSEY') ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (15  , 'ZAPATOS' ) ") ;
        db.execSQL( "INSERT INTO TIPO VALUES (16  , 'COMPLEMENTO') ") ;

    }

    private void crearTalla(SQLiteDatabase db){

        db.execSQL( "INSERT INTO TALLA VALUES (1  , 'XS') ") ;
        db.execSQL( "INSERT INTO TALLA VALUES (2  , 'S') ") ;
        db.execSQL( "INSERT INTO TALLA VALUES (3  , 'M') ") ;
        db.execSQL( "INSERT INTO TALLA VALUES (4  , 'L') ") ;
        db.execSQL( "INSERT INTO TALLA VALUES (5  , 'XL') ") ;
        db.execSQL( "INSERT INTO TALLA VALUES (6  , '2XL') ") ;

    }

    private void crearColor(SQLiteDatabase db){


        db.execSQL( "INSERT INTO COLOR VALUES (1  , 'AZUL') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (2  , 'AMARILLO') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (3  , 'BLANCO') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (4  , 'GRIS') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (5  , 'MARRON') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (6  , 'MORADO') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (7  , 'NARANJA') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (8  , 'NEGRO') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (9  , 'ROJO') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (10  , 'ROSA') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (11  , 'VERDE') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (12 , 'VIOLETA') ") ;

    }

}
