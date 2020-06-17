package com.example.dressmeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.dressmeapp.BaseDatos.BaseDatos;
import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDPerfil;
import com.example.dressmeapp.BaseDatos.GestorBDPrendas;
import com.example.dressmeapp.Objetos.Prenda;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestsPrendas {

    private static Context appContext;

    // ¡¡Hacer tests borra la base de datos!!

    @Before
    public void prepararTest() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appContext.deleteDatabase(BaseDatos.nombreBD);
    }

    @Test
    public void insertarPrendaFuncionaBien() {

        String sentencia = "SELECT COUNT(*) FROM PRENDA";
        BaseDatos bd = new BaseDatos(appContext, BaseDatos.nombreBD);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        Cursor cur = sqldb.rawQuery(sentencia, null);
        int countAntes = 0;
        if (cur.moveToFirst()) {
            countAntes = cur.getInt(0);
        }
        cur.close();


        GestorBDPerfil.crear_perfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.get_id_tabla(appContext, "PERFIL", "foo");
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // M


        GestorBDPrendas.crear_prenda(appContext,
                "PrendaFoo",
                idColor,
                idTipo,
                idTalla,
                1,
                idPerfil
        );

        cur = sqldb.rawQuery(sentencia, null);
        int countDespues = 0;
        if (cur.moveToFirst()) {
            countDespues = cur.getInt(0);
        }
        cur.close();

        bd.close();
        sqldb.close();

        assertEquals(countDespues, countAntes + 1);

    }

    @Test
    public void obtenerIDMaximoPrendaFuncionaBien() {

        // Extraer ID máximo, que debería tener la prenda
        int maxId = GestorBD.obtener_id_maximo(appContext, "PRENDA");

        // Añadir la prenda

        GestorBDPerfil.crear_perfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.get_id_tabla(appContext, "PERFIL", "foo");
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // M

        GestorBDPrendas.crear_prenda(appContext,
                "PrendaFoo",
                idColor,
                idTipo,
                idTalla,
                1,
                idPerfil
        );


        assertEquals(maxId + 1, GestorBD.obtener_id_maximo(appContext, "PRENDA"));

    }

    @Test
    public void borrarPrendaVisibleFuncionaBien() {

        String sentencia = "SELECT COUNT(*) FROM PRENDA WHERE VISIBLE=1";
        BaseDatos bd = new BaseDatos(appContext, BaseDatos.nombreBD);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        GestorBDPerfil.crear_perfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.get_id_tabla(appContext, "PERFIL", "foo");
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // M

        int idPrenda = GestorBD.obtener_id_maximo(appContext, "PRENDA");
        GestorBDPrendas.crear_prenda(appContext,
                "PrendaFoo",
                idColor,
                idTipo,
                idTalla,
                1,
                idPerfil
        );

        Cursor cur = sqldb.rawQuery(sentencia, null);
        int countAntes = 0;
        if (cur.moveToFirst()) {
            countAntes = cur.getInt(0);
        }
        cur.close();

        GestorBDPrendas.ocultar_prenda(appContext, idPrenda);

        cur = sqldb.rawQuery(sentencia, null);
        int countDespues = 0;
        if (cur.moveToFirst()) {
            countDespues = cur.getInt(0);
        }
        cur.close();

        bd.close();
        sqldb.close();

        assertEquals(countDespues, countAntes - 1);

    }

    @Test
    public void borrarPrendaDefinitivaFuncionaBien() {

        String sentencia = "SELECT COUNT(*) FROM PRENDA";
        BaseDatos bd = new BaseDatos(appContext, BaseDatos.nombreBD);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        GestorBDPerfil.crear_perfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.get_id_tabla(appContext, "PERFIL", "foo");
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // M

        int idPrenda = GestorBD.obtener_id_maximo(appContext, "PRENDA");
        GestorBDPrendas.crear_prenda(appContext,
                "PrendaFoo",
                idColor,
                idTipo,
                idTalla,
                1,
                idPerfil
        );

        Cursor cur = sqldb.rawQuery(sentencia, null);
        int countAntes = 0;
        if (cur.moveToFirst()) {
            countAntes = cur.getInt(0);
        }
        cur.close();

        GestorBDPrendas.borrar_prenda(appContext, idPrenda);

        cur = sqldb.rawQuery(sentencia, null);
        int countDespues = 0;
        if (cur.moveToFirst()) {
            countDespues = cur.getInt(0);
        }
        cur.close();

        bd.close();
        sqldb.close();

        assertEquals(countDespues, countAntes - 1);

    }

    @Test
    public void borrarPrendaVisibleNoBorraDefinitivamente() {

        String sentencia = "SELECT COUNT(*) FROM PRENDA";
        BaseDatos bd = new BaseDatos(appContext, BaseDatos.nombreBD);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        GestorBDPerfil.crear_perfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.get_id_tabla(appContext, "PERFIL", "foo");
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // M

        int idPrenda = GestorBD.obtener_id_maximo(appContext, "PRENDA");
        GestorBDPrendas.crear_prenda(appContext,
                "PrendaFoo",
                idColor,
                idTipo,
                idTalla,
                1,
                idPerfil
        );

        Cursor cur = sqldb.rawQuery(sentencia, null);
        int countAntes = 0;
        if (cur.moveToFirst()) {
            countAntes = cur.getInt(0);
        }
        cur.close();

        GestorBDPrendas.ocultar_prenda(appContext, idPrenda);

        cur = sqldb.rawQuery(sentencia, null);
        int countDespues = 0;
        if (cur.moveToFirst()) {
            countDespues = cur.getInt(0);
        }
        cur.close();

        bd.close();
        sqldb.close();

        assertEquals(countDespues, countAntes);

    }

    @Test
    public void obtenerPrendaFuncionaBien() {

        GestorBDPerfil.crear_perfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.get_id_tabla(appContext, "PERFIL", "foo");
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // M

        int idPrenda = GestorBD.obtener_id_maximo(appContext, "PRENDA");
        GestorBDPrendas.crear_prenda(appContext,
                "PrendaFoo",
                idColor,
                idTipo,
                idTalla,
                1,
                idPerfil
        );

        Prenda prenda = GestorBDPrendas.get_prenda(appContext, idPrenda);

        assertEquals(prenda.nombre, "PrendaFoo");
        assertEquals(prenda.color, "AZUL");
        assertEquals(prenda.tipo, "ABRIGO");
        assertEquals(prenda.talla, "M");

    }

}
