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

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestsTallas {

    private static Context appContext;

    // ¡¡Hacer tests borra la base de datos!!

    @Before
    public void prepararTest() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appContext.deleteDatabase(BaseDatos.nombreBD);
    }

    @Test
    public void insertarTallaFuncionaBien() {

        int numTallasAntes = GestorBD.get_ids_tabla(appContext, "TALLA").size();
        GestorBDPrendas.crear_talla(appContext, "XXXXXXXXXXXXXXXXXXXL");
        int numTallasDespues = GestorBD.get_ids_tabla(appContext, "TALLA").size();

        assertTrue(GestorBD.get_nombres_tabla(appContext, "TALLA").contains("XXXXXXXXXXXXXXXXXXXL"));
        assertEquals(numTallasDespues, numTallasAntes + 1);

    }

    @Test
    public void obtenerIDMaximoTallaFuncionaBien() {

        int maxIdAntes = GestorBD.obtener_id_maximo(appContext, "TALLA");
        GestorBDPrendas.crear_talla(appContext, "XXXXXXXXXXXXXXXXXXXL");
        int maxIdDespues = GestorBD.obtener_id_maximo(appContext, "TALLA");

        assertEquals(maxIdDespues, maxIdAntes + 1);

    }

}
