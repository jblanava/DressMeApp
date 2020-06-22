package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public static String nombreBD = "DRESSMEAPP.db";

    public BaseDatos(Context contexto, String nombrebd) {
        super(contexto, nombrebd, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        crearTablas(db);
        crearColor(db);
        crearTalla(db);
        crearTipos(db);
        crearCombo(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void crearTablas(SQLiteDatabase db) {
        String vsql;
        vsql = "CREATE TABLE \"PERFIL\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(50) NOT NULL, \"CONTRASENIA\" VARCHAR(50) NOT NULL)";
        db.execSQL(vsql);

        vsql = "CREATE TABLE \"PRENDA\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(50) NOT NULL, \"COLOR\" INTEGER NOT NULL" +
                ", \"TIPO\" INTEGER NOT NULL, \"TALLA\" INTEGER NOT NULL,\"VISIBLE\" INTEGER NOT NULL, \"ID_PERFIL\" INTEGER NOT NULL, \"FOTO\" INTEGER NOT NULL DEFAULT 1 )";

        db.execSQL(vsql);

        vsql = "CREATE TABLE \"CONJUNTO\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"ABRIGO\" INTEGER ,\"SUDADERA\" INTEGER , \"CAMISETA\" INTEGER NOT NULL, " +
                "\"PANTALON\" INTEGER NOT NULL, \"ZAPATO\" INTEGER NOT NULL, \"COMPLEMENTO\" INTEGER, \"ID_PERFIL\" INTEGER, \"FAVORITO\" INTEGER," +
                "\"NOMBRE_EVENTO\" VARCHAR(50))";
        db.execSQL(vsql);

        db.execSQL("CREATE TABLE \"COLOR\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(20) NOT NULL, \"HEX\" VARCHAR(7) NOT NULL)");
        db.execSQL("CREATE TABLE \"TIPO\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(20) NOT NULL, \"TIEMPO\" INTEGER NOT NULL,\"ACTIVIDAD\" INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE \"TALLA\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(20) NOT NULL)");

        db.execSQL("CREATE TABLE \"COMBO_COLOR\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"COLOR1\" INTEGER NOT NULL,\"COLOR2\" INTEGER NOT NULL)");

        // TABLA FOTOS
        vsql = "CREATE TABLE \"FOTOS\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"FOTO\" BLOB) ";

        db.execSQL(vsql);

    }

    private void crearTipos(SQLiteDatabase db) {
        // ACTUALIZAR LOS VALORES
        /*LEYENDA:
        TIEMPO:
            1.FRIO
            2.MEDIO
            3.CALOR


        ACTIVIDAD:
            1.FORMAL
            2.SEMIFORMAL
            3.CASUAL
            4.DEPORTIVO
            5.BAÑO

         */

         // Esquema:   (ID, TipoPrenda, Temperaturas(temp1 | temp 2), Formalidades( form1 | form2 ))

        db.execSQL("INSERT INTO TIPO VALUES (1  , 'ABRIGO', 1, 1 | 2 | 4) ");
        db.execSQL("INSERT INTO TIPO VALUES (2  ,'BAÑADOR/BIKINI', 2 | 4,  16) ");
        db.execSQL("INSERT INTO TIPO VALUES (3 , 'BLUSA', 1 | 2 | 4, 1 | 2 | 4) ");
        db.execSQL("INSERT INTO TIPO VALUES (4  , 'CAMISA', 1 | 2 | 4, 1 | 2) ");
        db.execSQL("INSERT INTO TIPO VALUES (5  ,  'CAMISETA M.CORTA' , 1 | 2 | 4, 2 | 4 | 8 | 16) ");
        db.execSQL("INSERT INTO TIPO VALUES (6  ,'CHANCLAS', 2 | 4,  4 | 16) ");
        db.execSQL("INSERT INTO TIPO VALUES (7 , 'CHAQUETA', 1 | 2 , 1 | 2 | 4) ");
        db.execSQL("INSERT INTO TIPO VALUES (8  ,'RELOJ' , 1 | 2 | 4 , 1 | 2 | 4 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (9  , 'FALDA', 2 | 4, 2 | 4 | 8)");
        db.execSQL("INSERT INTO TIPO VALUES (10  ,'JERSEY', 1 | 2, 2 | 4) ");
        db.execSQL("INSERT INTO TIPO VALUES (11  ,'PANTALON', 1 | 2 | 4, 1 | 2 | 4 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (12 ,'POLO', 2 | 4, 1 | 2) ");
        db.execSQL("INSERT INTO TIPO VALUES (13 , 'SUDADERA', 1 | 2, 4 | 8) ");
        db.execSQL("INSERT INTO TIPO VALUES (14  , 'TENNIS', 1 | 2 | 4, 2 | 4 | 8)");
        db.execSQL("INSERT INTO TIPO VALUES (15  ,'POLAR', 1 , 8 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (16  ,'ZAPATOS/TACONES', 1 | 2 | 4, 1 | 2 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (17  ,'ZAPATILLAS', 1 | 2 | 4, 2 | 4 | 8) ");
        db.execSQL("INSERT INTO TIPO VALUES (18  ,'BERMUDAS',  2 | 4, 2 | 4 | 8) ");
        db.execSQL("INSERT INTO TIPO VALUES (19  ,'SHORTS',  2 | 4, 4 | 8) ");
        db.execSQL("INSERT INTO TIPO VALUES (20  ,'VESTIDO',  1 | 2 | 4, 1 | 2) ");
        db.execSQL("INSERT INTO TIPO VALUES (21  ,'TOP',  2 | 4, 2 | 4 | 8 | 16) ");
        db.execSQL("INSERT INTO TIPO VALUES (22  ,'SANDALIAS', 2 | 4, 2 | 4 | 8 | 16) ");
        db.execSQL("INSERT INTO TIPO VALUES (23  ,'CAMISETA M.LARGA' , 1 | 2 , 2 | 4 | 8 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (24  ,'COLGANTE' , 1 | 2 | 4 , 1 | 2 | 4 | 8 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (25  ,'PULSERA' , 1 | 2 | 4 , 1 | 2 | 4 | 8 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (26  ,'SOMBRERO' , 1 | 2 | 4 , 1 | 2 | 4 | 8 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (27  ,'GORRA' , 1 | 2 | 4 , 1 | 2 | 4 | 8 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (28  ,'PENDIENTES' , 1 | 2 | 4 , 1 | 2 | 4 | 8 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (29  ,'GABARDINA', 1, 1 | 2) ");
        db.execSQL("INSERT INTO TIPO VALUES (30  ,'CORTAVIENTOS', 1 | 2, 2 | 4 | 8) ");


    }

    private void crearTalla(SQLiteDatabase db) {

        db.execSQL("INSERT INTO TALLA VALUES (1  , 'M') ");
        db.execSQL("INSERT INTO TALLA VALUES (2  , 'L') ");
        db.execSQL("INSERT INTO TALLA VALUES (3  , 'S') ");
        db.execSQL("INSERT INTO TALLA VALUES (4  , 'XL') ");
        db.execSQL("INSERT INTO TALLA VALUES (5  , 'XS') ");
        db.execSQL("INSERT INTO TALLA VALUES (6  , 'XXL') ");

    }

    private void crearColor(SQLiteDatabase db) {

        db.execSQL( "INSERT INTO COLOR VALUES (1  , 'AZUL', '#38D5F5') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (2  , 'AMARILLO', '#F0F266') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (3  , 'BLANCO', '#FFFFFF') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (4  , 'GRIS', '#808080') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (5  , 'MARRON', '#A05000') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (6  , 'MORADO', '#A116AB') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (7  , 'NARANJA', '#F7BB4A') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (8  , 'NEGRO', '#000000') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (9  , 'ROJO', '#FF3333') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (10  , 'ROSA', '#E277E6') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (11  , 'VERDE', '#92FF70') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (12 , 'VIOLETA', '#DAB1FA') ") ;

    }
    private void crearCombo(SQLiteDatabase db)
    {
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (1,2,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (2,10,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (3,3,2)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (4,3,10)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (5,1,10)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (6,3,1)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (7,10,1)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (8,1,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (9,1,1)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (10,2,2)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (11,3,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (12,4,4)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (13,5,5)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (14,6,6)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (15,7,7)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (16,8,8)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (17,9,9)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (18,10,10)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (19,11,11)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (20,12,12)");
    }
}
