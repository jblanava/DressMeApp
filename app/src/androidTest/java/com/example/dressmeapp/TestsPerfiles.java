package com.example.dressmeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.dressmeapp.BaseDatos.*;

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
public class TestsPerfiles {

    private static Context appContext;

    // ¡¡Hacer tests borra la base de datos!!

    @Before
    public void prepararTest() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appContext.deleteDatabase(BaseDatos.nombreBD);
    }

    @Test
    public void insertarPerfilFuncionaBien() {

        GestorBDPerfil.crear_perfil(appContext, "UsuarioPrueba", "ContraseñaPrueba");
        assertTrue(GestorBDPerfil.pass_correcta(appContext, "UsuarioPrueba", "ContraseñaPrueba"));

    }

    @Test
    public void obtenerIDMaximoPerfilFuncionaBien() {

        int maxCalculado = GestorBD.obtener_id_maximo(appContext, "PERFIL");
        int maxNuevoPerfil = GestorBDPerfil.crear_perfil(appContext, "UsuarioPrueba2", "ContraseñaPrueba2");

        assertEquals(maxCalculado, maxNuevoPerfil);

    }

    @Test
    public void borrarPerfilFuncionaBien() {

        int idNuevo = GestorBDPerfil.crear_perfil(appContext, "UsuarioPrueba3", "ContraseñaPrueba3");
        GestorBDPerfil.borrar_perfil(appContext, idNuevo);

        assertFalse(GestorBDPerfil.usuario_existe(appContext, "UsuarioPrueba3"));

    }

    @Test
    public void actualizarPerfilFuncionaBien() {


        int id = GestorBDPerfil.crear_perfil(appContext, "UsuarioPrueba4", "ContraseñaPrueba4");

        GestorBDPerfil.actualizar_perfil(appContext, id, "NuevaContraseña");

        assertTrue(GestorBDPerfil.pass_correcta(appContext, "UsuarioPrueba4", "NuevaContraseña"));

    }

    @Test
    public void getUserFuncionaBien() {
        int id = GestorBDPerfil.crear_perfil(appContext, "UsuarioPrueba5", "ContraseñaPrueba5");
        assertEquals("UsuarioPrueba5", GestorBDPerfil.get_usuario(appContext, id));
    }

    @Test
    public void getPassFuncionaBien() {
        int id = GestorBDPerfil.crear_perfil(appContext, "UsuarioPrueba6", "ContraseñaPrueba6");
        assertEquals("ContraseñaPrueba6", GestorBDPerfil.get_contrasenia(appContext, id));
    }

}
