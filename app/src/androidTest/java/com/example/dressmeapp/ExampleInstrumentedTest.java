package com.example.dressmeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.dressmeapp.BaseDatos.BaseDatos;
import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Debug.Debug;

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
public class ExampleInstrumentedTest {

    private static Context appContext;
    private static String nombreBaseDatos;
    private static int contador = 0;

    @Before
    public void prepararTest() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        nombreBaseDatos = "test" + contador + ".db";
        contador++;
        Debug.borrarTodosLosPerfiles(appContext, nombreBaseDatos);
    }

    @Test
    public void insertarPerfilFuncionaBien() {

        GestorBD.CrearPerfil(appContext, nombreBaseDatos, "UsuarioPrueba", "ContraseñaPrueba");
        assertTrue(GestorBD.PassCorrecta(appContext, nombreBaseDatos, "UsuarioPrueba", "ContraseñaPrueba"));

    }

    @Test
    public void obtenerIDMaximoFuncionaBien() {

        int maxCalculado = GestorBD.obtenIDMaximoPerfil(appContext, nombreBaseDatos);
        GestorBD.CrearPerfil(appContext, nombreBaseDatos, "UsuarioPrueba2", "ContraseñaPrueba2");
        int maxNuevoPerfil = GestorBD.IdPerfilAsociado(appContext, nombreBaseDatos, "UsuarioPrueba2", "ContraseñaPrueba2");

        assertEquals(maxCalculado, maxNuevoPerfil);

    }

    @Test
    public void borrarPerfilFuncionaBien() {

        int perfilesAntes = 0;
        int perfilesDespues = 0;

        String sentencia = "SELECT COUNT(*) FROM PERFIL";
        BaseDatos bd = new BaseDatos(appContext, nombreBaseDatos);
        SQLiteDatabase sqLiteDatabase = bd.getReadableDatabase();

        GestorBD.CrearPerfil(appContext, nombreBaseDatos, "UsuarioPrueba3", "ContraseñaPrueba3");
        int maxNuevoPerfil = GestorBD.IdPerfilAsociado(appContext, nombreBaseDatos, "UsuarioPrueba3", "ContraseñaPrueba3");

        Cursor cur = sqLiteDatabase.rawQuery(sentencia, null);
        if (cur.moveToFirst()) {
            perfilesAntes = cur.getInt(0);
        }

        GestorBD.BorrarPerfil(appContext, nombreBaseDatos, maxNuevoPerfil);

        cur = sqLiteDatabase.rawQuery(sentencia, null);
        if (cur.moveToFirst()) {
            perfilesDespues = cur.getInt(0);
        }

        assertEquals(perfilesDespues, perfilesAntes - 1);

    }

    @Test
    public void actualizarPerfil() {

        GestorBD.CrearPerfil(appContext, nombreBaseDatos, "UsuarioPrueba4", "ContraseñaPrueba4");
        int maxNuevoPerfil = GestorBD.IdPerfilAsociado(appContext, nombreBaseDatos, "UsuarioPrueba4", "ContraseñaPrueba4");

        GestorBD.ActualizarPerfil(appContext, nombreBaseDatos, maxNuevoPerfil, "NuevaContraseña");

        assertTrue(GestorBD.PassCorrecta(appContext, nombreBaseDatos, "UsuarioPrueba4", "NuevaContraseña"));

    }

}
