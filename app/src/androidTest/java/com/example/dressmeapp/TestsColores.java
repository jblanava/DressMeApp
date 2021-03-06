package com.example.dressmeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.dressmeapp.BaseDatos.BaseDatos;
import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDPrendas;
import com.example.dressmeapp.Objetos.ColorPrenda;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestsColores {

    private static Context appContext;

    // ¡¡Hacer tests borra la base de datos!!

    @Before
    public void prepararTest() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appContext.deleteDatabase(BaseDatos.nombreBD);
    }

    @Test
    public void insertarColorFuncionaBien() {

        int numColoresAntes = GestorBD.get_ids_tabla(appContext, "COLOR").size();
        int idNuevo = GestorBDPrendas.crear_color(appContext, "ColorPrueba", "#123456");
        int numColoresDespues = GestorBD.get_ids_tabla(appContext, "COLOR").size();

        List<ColorPrenda> colores = GestorBDPrendas.get_colores(appContext);

        assertTrue(colores.contains(new ColorPrenda(idNuevo, "ColorPrenda", "#123456")));
        assertEquals(numColoresDespues, numColoresAntes + 1);

    }

    @Test
    public void obtenerIDMaximoColorFuncionaBien() {

        int maxIdAntes = GestorBD.obtener_id_maximo(appContext, "COLOR");
        GestorBDPrendas.crear_color(appContext, "ColorPrueba", "#123456");
        int maxIdDespues = GestorBD.obtener_id_maximo(appContext, "COLOR");

        assertEquals(maxIdDespues, maxIdAntes + 1);

    }

    @Test
    public void insertarComboColorFuncionaBien() {

        String sentencia = "SELECT COUNT(*) FROM COMBO_COLOR";
        BaseDatos bd = new BaseDatos(appContext, BaseDatos.nombreBD);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        int idCol1 = GestorBDPrendas.crear_color(appContext, "ColorPrueba1", "#123456");
        int idCol2 = GestorBDPrendas.crear_color(appContext, "ColorPrueba2", "#789012");

        Cursor cur = sqldb.rawQuery(sentencia, null);
        int combosAntes = 0;
        if (cur.moveToFirst()) {
            combosAntes = cur.getInt(0);
        }
        cur.close();

        assertTrue(GestorBDPrendas.crear_combo_color(appContext, idCol1, idCol2));

        cur = sqldb.rawQuery(sentencia, null);
        int combosDespues = 0;
        if (cur.moveToFirst()) {
            combosDespues = cur.getInt(0);
        }
        cur.close();

        sqldb.close();
        bd.close();

        assertEquals(combosDespues, combosAntes + 2);

    }

    @Test
    public void insertarComboColorRepetidoFuncionaBien() {

        int idCol1 = GestorBDPrendas.crear_color(appContext, "ColorPrueba1", "#123456");
        int idCol2 = GestorBDPrendas.crear_color(appContext, "ColorPrueba2", "#789012");

        GestorBDPrendas.crear_combo_color(appContext, idCol1, idCol2);

        assertFalse(GestorBDPrendas.crear_combo_color(appContext, idCol1, idCol2));

    }

    @Test
    public void comboColorSimetrica() {

        BaseDatos bd = new BaseDatos(appContext, BaseDatos.nombreBD);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        String sentencia = "SELECT COUNT(*) FROM COMBO_COLOR;";
        Cursor cur = sqldb.rawQuery(sentencia, null);
        int combosTotales = 0;
        if (cur.moveToFirst()) {
            combosTotales = cur.getInt(0);
        }
        cur.close();

        sentencia = "SELECT COUNT(*) FROM COMBO_COLOR AS C " +
                "WHERE EXISTS (SELECT * FROM COMBO_COLOR AS D " +
                "WHERE D.COLOR1 = C.COLOR2 AND D.COLOR2 = C.COLOR1)";
        cur = sqldb.rawQuery(sentencia, null);
        int combosSimetricos = 0;
        if (cur.moveToFirst()) {
            combosSimetricos = cur.getInt(0);
        }
        cur.close();

        sqldb.close();
        bd.close();

        assertEquals(combosTotales, combosSimetricos);

    }

}
