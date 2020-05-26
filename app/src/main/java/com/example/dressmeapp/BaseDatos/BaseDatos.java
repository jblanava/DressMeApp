package com.example.dressmeapp.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BaseDatos extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    /* TODO: Eliminar, es innecesario
        public BaseDatos (Context contexto) {
            this(contexto, NOMBRE_BASE_DATOS);
        }*/
    public BaseDatos(Context contexto, String nombrebd) {
        super(contexto, nombrebd, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        // TABLA PRENDA

        crearTablas(db);
        crearColor(db);
        crearTalla(db);
        crearTipos(db);
        crearCombo(db);

        db.execSQL("INSERT INTO PERFIL VALUES (1  , 'PEPE', 'LOTAD') ");

        db.execSQL("INSERT INTO PRENDA VALUES (1  , 'Prueba1', 4,1,3, 1, 1) ");
        db.execSQL("INSERT INTO PRENDA VALUES (2  , 'Prueba21', 2, 4, 2, 1, 1) ");

        db.execSQL("INSERT INTO PRENDA VALUES (3  , 'Prueba14', 3, 7,1, 1, 1) ");
        db.execSQL("INSERT INTO PRENDA VALUES (4  , 'Prueba22', 2, 4, 2, 1, 1) ");
        db.execSQL("INSERT INTO PRENDA VALUES (5  , 'Prueba23', 4, 7,1, 1, 1) ");
        db.execSQL("INSERT INTO PRENDA VALUES (6  , 'Prueba24', 1, 4, 2, 1, 1) ");
        db.execSQL("INSERT INTO PRENDA VALUES (7  , 'Prueba25', 1, 7,1, 1, 1) ");
        db.execSQL("INSERT INTO PRENDA VALUES (8  , 'Prueba26', 2, 4, 2, 1, 1) ");
        db.execSQL("INSERT INTO PRENDA VALUES (9  , 'Prueba27', 5, 7,1, 1, 1) ");

        db.execSQL("INSERT INTO CONJUNTO VALUES (1  , 1,2,3,4,5,6) ");
        db.execSQL("INSERT INTO CONJUNTO VALUES (2  , 6,5,4,3,2,1) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
        }
    }

    private void crearTablas(SQLiteDatabase db) {
        String vsql;
        vsql = "CREATE TABLE \"PERFIL\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(50) NOT NULL, \"CONTRASENIA\" VARCHAR(50) NOT NULL)";
        db.execSQL(vsql);

        vsql = "CREATE TABLE \"PRENDA\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(50) NOT NULL, \"COLOR\" INTEGER NOT NULL" +
                ", \"TIPO\" INTEGER NOT NULL, \"TALLA\" INTEGER NOT NULL,\"VISIBLE\" INTEGER NOT NULL, \"ID_PERFIL\" INTEGER NOT NULL )";

        db.execSQL(vsql);

        vsql = "CREATE TABLE \"ENTRADA_HISTORIAL\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(50) NOT NULL, \"FORMALIDAD\" INTEGER NOT NULL" +
                ", \"TIEMPO\" INTEGER NOT NULL, \"TEMPERATURA\" INTEGER NOT NULL,\"FECHA\" INTEGER NOT NULL, \"ID_PRENDA\" INTEGER NOT NULL  )";

        db.execSQL(vsql);

        vsql = "CREATE TABLE \"CONJUNTO\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"ABRIGO\" INTEGER ,\"SUDADERA\" INTEGER , \"CAMISETA\" INTEGER NOT NULL, " +
                "\"PANTALON\" INTEGER NOT NULL, \"ZAPATO\" INTEGER NOT NULL, \"COMPLEMENTO\" INTEGER )";
        db.execSQL(vsql);

        db.execSQL("CREATE TABLE \"COLOR\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(20) NOT NULL)");
        db.execSQL("CREATE TABLE \"TIPO\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(20) NOT NULL, \"TIEMPO\" INTEGER NOT NULL,\"ACTIVIDAD\" INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE \"TALLA\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"NOMBRE\" VARCHAR(20) NOT NULL)");

        db.execSQL("CREATE TABLE \"COMBO_COLOR\" (\"ID\" INTEGER PRIMARY KEY  NOT NULL , \"COLOR1\" INTEGER NOT NULL,\"COLOR2\" INTEGER NOT NULL)");
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

        int[] temperaturas = {1, 2, 4};
        int[] formalidades = {1, 2, 4, 8, 16};

         // Esquema:   (ID, TipoPrenda, Temperaturas(temp1 | temp 2), Formalidades( form1 | form2 ))

        db.execSQL("INSERT INTO TIPO VALUES (1  , 'ABRIGO', 1, 1 | 2 | 4) ");
        db.execSQL("INSERT INTO TIPO VALUES (2  ,'BAÑADOR/BIKINI', 4,  16) ");
        db.execSQL("INSERT INTO TIPO VALUES (3 , 'BLUSA', 1 | 2 | 4, 1 | 2 | 4) ");
        db.execSQL("INSERT INTO TIPO VALUES (4  , 'CAMISA', 1 | 2 | 4, 1 | 2) ");
        db.execSQL("INSERT INTO TIPO VALUES (5  ,  'CAMISETA M.CORTA' , 1 | 2 | 4, 2 | 4 | 8 | 16) ");
        db.execSQL("INSERT INTO TIPO VALUES (6  ,'CHANCLAS', 2 | 4,  4 | 16) ");
        db.execSQL("INSERT INTO TIPO VALUES (7 , 'CHAQUETA', 1 | 2 , 1 | 2 | 4) ");
        db.execSQL("INSERT INTO TIPO VALUES (8  ,'COMPLEMENTO' , 1 | 2 | 4, 1 | 2 | 4 | 8 | 16)");
        db.execSQL("INSERT INTO TIPO VALUES (9  , 'FALDA', 2 | 4, 2 | 4 | 8)");
        db.execSQL("INSERT INTO TIPO VALUES (10  ,'JERSEY', 1 | 2, 2 | 4) ");
        db.execSQL("INSERT INTO TIPO VALUES (11  ,'PANTALON', 1 | 2 | 4, 1 | 2 | 4 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (12 ,'POLO', 2 | 4, 1 | 2) ");
        db.execSQL("INSERT INTO TIPO VALUES (13 , 'SUDADERA', 1 | 2, 4 | 8) ");
        db.execSQL("INSERT INTO TIPO VALUES (14  , 'TENNIS', 1 | 2 | 4, 2 | 4 | 8  )");
        db.execSQL("INSERT INTO TIPO VALUES (15  , 'TRAJE', 1 | 2 | 4, 1 | 2) ");
        db.execSQL("INSERT INTO TIPO VALUES (16  ,'ZAPATOS/TACONES', 1 | 2 | 4, 1 | 2 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (17  ,'CHANDAL',  1 | 2, 4 | 8) ");
        db.execSQL("INSERT INTO TIPO VALUES (18  ,'BERMUDAS',  2 | 4, 2 | 4 | 8) ");
        db.execSQL("INSERT INTO TIPO VALUES (19  ,'SHORTS',  2 | 4, 4 | 8) ");
        db.execSQL("INSERT INTO TIPO VALUES (20  ,'VESTIDO',  1 | 2 | 4, 1 | 2) ");
        db.execSQL("INSERT INTO TIPO VALUES (21  ,'TOP',  2 | 4, 2 | 4 | 8) ");
        db.execSQL("INSERT INTO TIPO VALUES (22  ,'SANDALIAS', 2 | 4, 2 | 4) ");
        db.execSQL("INSERT INTO TIPO VALUES (23  ,'CAMISETA M.LARGA' , 1 | 2 , 2 | 4 | 8 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (24  ,'ZAPATILLAS', 1 | 2 | 4, 2 | 4 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (25  ,'POLAR', 1 , 8 ) ");



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


        db.execSQL("INSERT INTO COLOR VALUES (1  , 'AZUL') ");
        db.execSQL("INSERT INTO COLOR VALUES (2  , 'AMARILLO') ");
        db.execSQL("INSERT INTO COLOR VALUES (3  , 'BLANCO') ");
        db.execSQL("INSERT INTO COLOR VALUES (4  , 'GRIS') ");
        db.execSQL("INSERT INTO COLOR VALUES (5  , 'MARRON') ");
        db.execSQL("INSERT INTO COLOR VALUES (6  , 'MORADO') ");
        db.execSQL("INSERT INTO COLOR VALUES (7  , 'NARANJA') ");
        db.execSQL("INSERT INTO COLOR VALUES (8  , 'NEGRO') ");
        db.execSQL("INSERT INTO COLOR VALUES (9  , 'ROJO') ");
        db.execSQL("INSERT INTO COLOR VALUES (10  , 'ROSA') ");
        db.execSQL("INSERT INTO COLOR VALUES (11  , 'VERDE') ");
        db.execSQL("INSERT INTO COLOR VALUES (12 , 'VIOLETA') ");

    }
    private void crearCombo(SQLiteDatabase db){

        db.execSQL("INSERT INTO COMBO_COLOR VALUES (1,2,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (2,4,5)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (3,3,5)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (4,10,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (5,2,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (6,4,5)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (7,3,5)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (8,10,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (9,1,10)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (10,3,1)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (11,3,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (12,1,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (13,2,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (14,2,5)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (15,3,5)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (16,1,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (17,1,1)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (18,2,2)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (19,3,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (20,4,4)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (21,5,5)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (22,6,6)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (23,7,7)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (24,8,8)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (25,9,9)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (26,10,10)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (27,11,11)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (28,12,12)");

    }
}
