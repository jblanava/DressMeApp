package com.example.dressmeapp.BaseDatos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.dressmeapp.R;

import java.util.Random;

public class BaseDatos extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public static String nombreBD = "dressmeapp32.db";

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
        db.execSQL("INSERT INTO PERFIL VALUES (1  , '1', '2') "); // PERFIL DE PRUEBA

        crearPrendas(db);

        db.execSQL("INSERT INTO CONJUNTO VALUES (1  , 1,2,3,4,5,6, 1, 0, \"Conjunto1\") ");
        db.execSQL("INSERT INTO CONJUNTO VALUES (2  , 6,5,4,3,2,1, 1, 0, \"Conjunto2\") ");

    }

    private void crearPrendas(SQLiteDatabase db) {

        Random rng = new Random();

        for(int i = 1; i <= 120; i++)
        {


            int color = rng.nextInt(11) + 1;
            int tipo = rng.nextInt(24) + 1;
            int talla = rng.nextInt(5) + 1;

            @SuppressLint("DefaultLocale") String sentencia = String.format("INSERT INTO PRENDA VALUES (%d, '%s', %d, %d, %d, 1, 1, 1)", i, "Prueba" + i ,color, tipo, talla);
            db.execSQL(sentencia);
        }

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
        db.execSQL("INSERT INTO TIPO VALUES (14  , 'TENNIS', 1 | 2 | 4, 2 | 4 | 8 | 16  )");
        db.execSQL("INSERT INTO TIPO VALUES (15  , 'TRAJE', 1 | 2 | 4, 1 | 2) ");
        db.execSQL("INSERT INTO TIPO VALUES (16  ,'ZAPATOS/TACONES', 1 | 2 | 4, 1 | 2 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (17  ,'CHANDAL',  1 | 2, 4 | 8) ");
        db.execSQL("INSERT INTO TIPO VALUES (18  ,'BERMUDAS',  2 | 4, 2 | 4 | 8 | 16) ");
        db.execSQL("INSERT INTO TIPO VALUES (19  ,'SHORTS',  2 | 4, 4 | 8 | 16) ");
        db.execSQL("INSERT INTO TIPO VALUES (20  ,'VESTIDO',  1 | 2 | 4, 1 | 2) ");
        db.execSQL("INSERT INTO TIPO VALUES (21  ,'TOP',  2 | 4, 2 | 4 | 8 | 16) ");
        db.execSQL("INSERT INTO TIPO VALUES (22  ,'SANDALIAS', 2 | 4, 2 | 4 | 8 | 16) ");
        db.execSQL("INSERT INTO TIPO VALUES (23  ,'CAMISETA M.LARGA' , 1 | 2 , 2 | 4 | 8 ) ");
        db.execSQL("INSERT INTO TIPO VALUES (24  ,'ZAPATILLAS', 1 | 2 | 4, 2 | 4 | 8 | 16 ) ");
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

        db.execSQL( "INSERT INTO COLOR VALUES (1  , 'AZUL', '#0000FF') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (2  , 'AMARILLO', '#FFFF00') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (3  , 'BLANCO', '#FFFFFF') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (4  , 'GRIS', '#808080') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (5  , 'MARRON', '#804000') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (6  , 'MORADO', '#7D2787') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (7  , 'NARANJA', '#FC8F00') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (8  , 'NEGRO', '#000000') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (9  , 'ROJO', '#FF0000') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (10  , 'ROSA', '#F989C1') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (11  , 'VERDE', '#22ED0B') ") ;
        db.execSQL( "INSERT INTO COLOR VALUES (12 , 'VIOLETA', '#8827C4') ") ;

    }
    private void crearCombo(SQLiteDatabase db){

        db.execSQL("INSERT INTO COMBO_COLOR VALUES (1,2,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (2,4,5)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (3,3,5)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (4,10,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (5,3,2)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (6,5,4)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (7,5,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (8,3,10)");

        db.execSQL("INSERT INTO COMBO_COLOR VALUES (9,1,10)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (10,3,1)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (11,10,1)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (12,1,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (13,5,2)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (14,2,5)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (15,1,1)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (16,2,2)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (17,3,3)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (18,4,4)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (19,5,5)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (20,6,6)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (21,7,7)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (22,8,8)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (23,9,9)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (24,10,10)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (25,11,11)");
        db.execSQL("INSERT INTO COMBO_COLOR VALUES (26,12,12)");

    }
}
