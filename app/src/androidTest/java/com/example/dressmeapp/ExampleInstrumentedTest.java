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

    /********************************************************************************
     PERFILES
     ********************************************************************************/

    @Test
    public void insertarPerfilFuncionaBien() {

        GestorBD.CrearPerfil(appContext, nombreBaseDatos, "UsuarioPrueba", "ContraseñaPrueba");
        assertTrue(GestorBD.UsuarioEstaEnBD(appContext, nombreBaseDatos, "UsuarioPrueba"));
        assertTrue(GestorBD.PassCorrecta(appContext, nombreBaseDatos, "UsuarioPrueba", "ContraseñaPrueba"));

    }

    @Test
    public void obtenerIDMaximoPerfilFuncionaBien() {

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
    public void actualizarPerfilFuncionaBien() {

        GestorBD.CrearPerfil(appContext, nombreBaseDatos, "UsuarioPrueba4", "ContraseñaPrueba4");
        int id = GestorBD.IdPerfilAsociado(appContext, nombreBaseDatos, "UsuarioPrueba4", "ContraseñaPrueba4");

        GestorBD.ActualizarPerfil(appContext, nombreBaseDatos, id, "NuevaContraseña");

        assertTrue(GestorBD.PassCorrecta(appContext, nombreBaseDatos, "UsuarioPrueba4", "NuevaContraseña"));

    }

    @Test
    public void getUserFuncionaBien() {

        GestorBD.CrearPerfil(appContext, nombreBaseDatos, "UsuarioPrueba5", "ContraseñaPrueba5");
        int id = GestorBD.IdPerfilAsociado(appContext, nombreBaseDatos, "UsuarioPrueba5", "ContraseñaPrueba5");

        assertEquals("UsuarioPrueba5", GestorBD.getUser(appContext, nombreBaseDatos, id));

    }

    @Test
    public void getPassFuncionaBien() {

        GestorBD.CrearPerfil(appContext, nombreBaseDatos, "UsuarioPrueba6", "ContraseñaPrueba6");
        int id = GestorBD.IdPerfilAsociado(appContext, nombreBaseDatos, "UsuarioPrueba6", "ContraseñaPrueba6");

        assertEquals("ContraseñaPrueba6", GestorBD.getPass(appContext, nombreBaseDatos, id));

    }

    /********************************************************************************
     PRENDAS Y CONJUNTOS
     ********************************************************************************/

    @Test
    public void insertarPrendaFuncionaBien() {

        String sentencia = "SELECT COUNT(*) FROM PRENDA";
        BaseDatos bd = new BaseDatos(appContext, nombreBaseDatos);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        Cursor cur = sqldb.rawQuery(sentencia, null);
        int countAntes = 0;
        if (cur.moveToFirst()) {
            countAntes = cur.getInt(0);
        }
        cur.close();


        GestorBD.CrearPerfil(appContext, nombreBaseDatos, "foo", "bar");
        int idPerfil = GestorBD.IdPerfilAsociado(appContext, nombreBaseDatos, "foo", "bar");
        int idTipo = 1; // abrigo
        int idTalla = 1; // XS

        GestorBD.crearPrenda(appContext, nombreBaseDatos,
                "PrendaFoo",
                "rojo",
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
        int maxId = GestorBD.obtenIDMaximoPrenda(appContext, nombreBaseDatos);

        // Añadir la prenda

        GestorBD.CrearPerfil(appContext, nombreBaseDatos, "foo", "bar");
        int idPerfil = GestorBD.IdPerfilAsociado(appContext, nombreBaseDatos, "foo", "bar");
        int idTipo = 1; // abrigo
        int idTalla = 1; // XS

        int idNuevaPrenda = GestorBD.crearPrenda(appContext, nombreBaseDatos,
                "PrendaFoo",
                "rojo",
                idTipo,
                idTalla,
                1,
                idPerfil
        );

        assertEquals(maxId, idNuevaPrenda);

    }

            /*
    @Test
    public void borrarPrendaFuncionaBien() {

        // Añadir la prenda

        GestorBD.CrearPerfil(appContext, nombreBaseDatos, "foo", "bar");
        int idPerfil = GestorBD.IdPerfilAsociado(appContext, nombreBaseDatos, "foo", "bar");
        int idTipo = 1; // abrigo
        int idTalla = 1; // XS

        GestorBD.crearPrenda(appContext, nombreBaseDatos,
                "PrendaFoo",
                "rojo",
                idTipo,
                idTalla,
                1,
                idPerfil
        );


    }

             */

}
