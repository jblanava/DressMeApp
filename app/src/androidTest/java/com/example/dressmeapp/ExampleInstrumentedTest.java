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
    private static String nombreBaseDatos = "BaseDeDatosPruebas.db";

    @Before
    public void prepararTest() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appContext.deleteDatabase(nombreBaseDatos);

        GestorBD.seleccionarBD(nombreBaseDatos);
    }

    /********************************************************************************
     PERFILES
     ********************************************************************************/

    @Test
    public void insertarPerfilFuncionaBien() {

        GestorBD.CrearPerfil(appContext, "UsuarioPrueba", "ContraseñaPrueba");
        assertTrue(GestorBD.UsuarioEstaEnBD(appContext, "UsuarioPrueba"));
        assertTrue(GestorBD.PassCorrecta(appContext, "UsuarioPrueba", "ContraseñaPrueba"));

    }

    @Test
    public void obtenerIDMaximoPerfilFuncionaBien() {

        int maxCalculado = GestorBD.obtenIDMaximoPerfil(appContext);
        GestorBD.CrearPerfil(appContext, "UsuarioPrueba2", "ContraseñaPrueba2");
        int maxNuevoPerfil = GestorBD.IdPerfilAsociado(appContext, "UsuarioPrueba2", "ContraseñaPrueba2");

        assertEquals(maxCalculado, maxNuevoPerfil);

    }

    @Test
    public void borrarPerfilFuncionaBien() {

        int perfilesAntes = 0;
        int perfilesDespues = 0;

        String sentencia = "SELECT COUNT(*) FROM PERFIL";
        BaseDatos bd = new BaseDatos(appContext, nombreBaseDatos);
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

    @Test
    public void actualizarPerfilFuncionaBien() {

        GestorBD.CrearPerfil(appContext, "UsuarioPrueba4", "ContraseñaPrueba4");
        int id = GestorBD.IdPerfilAsociado(appContext, "UsuarioPrueba4", "ContraseñaPrueba4");

        GestorBD.ActualizarPerfil(appContext, id, "NuevaContraseña");

        assertTrue(GestorBD.PassCorrecta(appContext, "UsuarioPrueba4", "NuevaContraseña"));

    }

    @Test
    public void getUserFuncionaBien() {

        GestorBD.CrearPerfil(appContext, "UsuarioPrueba5", "ContraseñaPrueba5");
        int id = GestorBD.IdPerfilAsociado(appContext, "UsuarioPrueba5", "ContraseñaPrueba5");

        assertEquals("UsuarioPrueba5", GestorBD.getUser(appContext, id));

    }

    @Test
    public void getPassFuncionaBien() {

        GestorBD.CrearPerfil(appContext, "UsuarioPrueba6", "ContraseñaPrueba6");
        int id = GestorBD.IdPerfilAsociado(appContext, "UsuarioPrueba6", "ContraseñaPrueba6");

        assertEquals("ContraseñaPrueba6", GestorBD.getPass(appContext, id));

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


        GestorBD.CrearPerfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.IdPerfilAsociado(appContext, "foo", "bar");
        int idTipo = 1; // abrigo
        int idTalla = 1; // XS

        GestorBD.crearPrenda(appContext,
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

    // TODO: Solucionar esto
    /*
    @Test
    public void obtenerIDMaximoPrendaFuncionaBien() {

        // Extraer ID máximo, que debería tener la prenda
        int maxId = GestorBD.obtenIDMaximoPrenda(appContext);

        // Añadir la prenda

        GestorBD.CrearPerfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.IdPerfilAsociado(appContext, "foo", "bar");
        int idTipo = 1; // abrigo
        int idTalla = 1; // XS

        GestorBD.crearPrenda(appContext,
                "PrendaFoo",
                "rojo",
                idTipo,
                idTalla,
                1,
                idPerfil
        );


        assertEquals(maxId, idNuevaPrenda);

    }
*/
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
