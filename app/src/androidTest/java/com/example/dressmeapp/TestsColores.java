package com.example.dressmeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.dressmeapp.BaseDatos.BaseDatos;
import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDPrendas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
        GestorBDPrendas.crear_color(appContext, "ColorPrueba", "#123456");
        int numColoresDespues = GestorBD.get_ids_tabla(appContext, "COLOR").size();

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

        int idCol1 = GestorBD.obtener_id_maximo(appContext, "COLOR");
        GestorBDPrendas.crear_color(appContext, "ColorPrueba1", "#123456");
        int idCol2 = GestorBD.obtener_id_maximo(appContext, "COLOR");
        GestorBDPrendas.crear_color(appContext, "ColorPrueba2", "#789012");

        Cursor cur = sqldb.rawQuery(sentencia, null);
        int combosAntes = 0;
        if (cur.moveToFirst()) {
            combosAntes = cur.getInt(0);
        }
        cur.close();

        GestorBDPrendas.crear_combo_color(appContext, idCol1, idCol2);

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

        int idCol1 = GestorBD.obtener_id_maximo(appContext, "COLOR");
        GestorBDPrendas.crear_color(appContext, "ColorPrueba1", "#123456");
        int idCol2 = GestorBD.obtener_id_maximo(appContext, "COLOR");
        GestorBDPrendas.crear_color(appContext, "ColorPrueba2", "#789012");

        GestorBDPrendas.crear_combo_color(appContext, idCol1, idCol2);

        assertFalse(GestorBDPrendas.crear_combo_color(appContext, idCol1, idCol2));

    }

}
