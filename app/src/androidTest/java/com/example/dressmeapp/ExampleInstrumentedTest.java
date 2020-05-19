package com.example.dressmeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.dressmeapp.BaseDatos.BaseDatos;
import com.example.dressmeapp.BaseDatos.GestorBD;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.dressmeapp", appContext.getPackageName());
    }

    @Test
    public void insertarPerfilFuncionaBien() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        GestorBD.CrearPerfil(appContext, "UsuarioPrueba", "ContraseñaPrueba");
        assertTrue(GestorBD.PassCorrecta(appContext, "UsuarioPrueba", "ContraseñaPrueba"));

    }

    @Test
    public void obtenerIDMaximoFuncionaBien() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        int maxCalculado = GestorBD.obtenIDMaximoPerfil(appContext);
        GestorBD.CrearPerfil(appContext, "UsuarioPrueba2", "ContraseñaPrueba2");
        int maxNuevoPerfil = GestorBD.IdPerfilAsociado(appContext, "UsuarioPrueba2", "ContraseñaPrueba2");

        assertEquals(maxCalculado, maxNuevoPerfil);

    }

    @Test
    public void borrarPerfilFuncionaBien() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        int perfilesAntes = 0;
        int perfilesDespues = 0;

        String sentencia = "SELECT COUNT(*) FROM PERFIL";
        BaseDatos bd = new BaseDatos(appContext);
        SQLiteDatabase sqLiteDatabase = bd.getReadableDatabase();

        GestorBD.CrearPerfil(appContext, "UsuarioPrueba3", "ContraseñaPrueba3");
        int maxNuevoPerfil = GestorBD.IdPerfilAsociado(appContext, "UsuarioPrueba3", "ContraseñaPrueba3");

        Cursor cur = sqLiteDatabase.rawQuery(sentencia, null);
        if (cur.moveToFirst()) {
            perfilesAntes = cur.getInt(0);
        }

        GestorBD.BorrarPerfil(appContext, maxNuevoPerfil);

        cur = sqLiteDatabase.rawQuery(sentencia, null);
        if (cur.moveToFirst()) {
            perfilesDespues = cur.getInt(0);
        }

        assertEquals(perfilesDespues, perfilesAntes - 1);

    }

}
