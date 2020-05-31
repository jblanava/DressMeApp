package com.example.dressmeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.dressmeapp.BaseDatos.BaseDatos;
import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBD2;
import com.example.dressmeapp.BaseDatos.LibreriaBD;
import com.example.dressmeapp.Debug.Debug;
import com.example.dressmeapp.Objetos.Conjunto;
import com.example.dressmeapp.Objetos.Prenda;

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
public class ExampleInstrumentedTest {

    private static Context appContext;
    private static String nombreBaseDatos = "dressmeapp21.db";

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
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // XS


        GestorBD.crearPrenda(appContext,
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
        int maxId = GestorBD.obtenIDMaximoPrenda(appContext);

        // Añadir la prenda

        GestorBD.CrearPerfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.IdPerfilAsociado(appContext, "foo", "bar");
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // XS

        GestorBD.crearPrenda(appContext,
                "PrendaFoo",
                1,
                idTipo,
                idTalla,
                1,
                idPerfil
        );


        assertEquals(maxId + 1, GestorBD.obtenIDMaximoPrenda(appContext));

    }

    @Test
    public void borrarPrendaVisibleFuncionaBien() {

        String sentencia = "SELECT COUNT(*) FROM PRENDA WHERE VISIBLE=1";
        BaseDatos bd = new BaseDatos(appContext, nombreBaseDatos);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        GestorBD.CrearPerfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.IdPerfilAsociado(appContext, "foo", "bar");
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // M

        int idPrenda = GestorBD.obtenIDMaximoPrenda(appContext);
        GestorBD.crearPrenda(appContext,
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

        GestorBD.CambiarVisibilidadPrenda(appContext, idPrenda);

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
        BaseDatos bd = new BaseDatos(appContext, nombreBaseDatos);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        GestorBD.CrearPerfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.IdPerfilAsociado(appContext, "foo", "bar");
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // M

        int idPrenda = GestorBD.obtenIDMaximoPrenda(appContext);
        GestorBD.crearPrenda(appContext,
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

        GestorBD.borrarPrenda(appContext, idPrenda);

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
        BaseDatos bd = new BaseDatos(appContext, nombreBaseDatos);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        GestorBD.CrearPerfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.IdPerfilAsociado(appContext, "foo", "bar");
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // M

        int idPrenda = GestorBD.obtenIDMaximoPrenda(appContext);
        GestorBD.crearPrenda(appContext,
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

        GestorBD.CambiarVisibilidadPrenda(appContext, idPrenda);

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

        GestorBD.CrearPerfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.IdPerfilAsociado(appContext, "foo", "bar");
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // M

        int idPrenda = GestorBD.obtenIDMaximoPrenda(appContext);
        GestorBD.crearPrenda(appContext,
                "PrendaFoo",
                idColor,
                idTipo,
                idTalla,
                1,
                idPerfil
        );

        Prenda prenda = GestorBD.Obtener_Prenda(appContext, idPrenda);

        assertEquals(prenda.nombre, "PrendaFoo");
        assertEquals(prenda.color, "AZUL");
        assertEquals(prenda.tipo, "ABRIGO");
        assertEquals(prenda.talla, "M");

    }

    @Test
    public void modificarPrendaFuncionaBien() {

        GestorBD.CrearPerfil(appContext, "foo", "bar");
        int idPerfil = GestorBD.IdPerfilAsociado(appContext, "foo", "bar");
        int idColor = 1; // azul
        int idTipo = 1; // abrigo
        int idTalla = 1; // M

        int idPrenda = GestorBD.obtenIDMaximoPrenda(appContext);
        GestorBD.crearPrenda(appContext,
                "PrendaFoo",
                idColor,
                idTipo,
                idTalla,
                1,
                idPerfil
        );

        int idPrendaNuevo = GestorBD.obtenIDMaximoPrenda(appContext);

        Prenda prendaAntigua = GestorBD.Obtener_Prenda(appContext, idPrenda);
        Prenda prendaNueva = new Prenda(prendaAntigua.id, "PrendaBar",
                prendaAntigua.color, prendaAntigua.tipo, prendaAntigua.talla);

        GestorBD.Modificar_Prenda(appContext, prendaNueva);

        Prenda prendaNuevaReobtenida = GestorBD.Obtener_Prenda(appContext, idPrendaNuevo);

        assertEquals(prendaNueva.nombre, prendaNuevaReobtenida.nombre);

    }

    /********************************************************************************
     COLORES
     ********************************************************************************/

    @Test
    public void insertarColorFuncionaBien() {

        int numColoresAntes = GestorBD.ObtenerColores(appContext).size();
        GestorBD2.crearColor(appContext, "ColorPrueba", "#123456");
        int numColoresDespues = GestorBD.ObtenerColores(appContext).size();

        assertEquals(numColoresDespues, numColoresAntes + 1);

    }

    @Test
    public void obtenerIDMaximoColorFuncionaBien() {

        int maxIdAntes = GestorBD2.obtenIDMaximoColor(appContext);
        GestorBD2.crearColor(appContext, "ColorPrueba", "#123456");
        int maxIdDespues = GestorBD2.obtenIDMaximoColor(appContext);

        assertEquals(maxIdDespues, maxIdAntes + 1);

    }

    /********************************************************************************
     HISTORIAL & CONJUNTOS
     ********************************************************************************/

    @Test
    public void borrarConjuntoFuncionaBien() {

        // Crear conjunto

        int idPerfil = GestorBD.obtenIDMaximoPerfil(appContext);
        GestorBD.CrearPerfil(appContext, "foo", "bar");
        GestorBD.setIdPerfil(idPerfil);

        Conjunto cjto = new Conjunto();
        int idCjto = GestorBD.obtenIDMaximoConjunto(appContext);
        cjto.add(idCjto);

        int[] idsPrenda = new int[6];
        int[] tiposPrenda = {1, 13, 4, 11, 16, 8};
        for (int i = 0; i < 6; i++) {
            idsPrenda[i] = GestorBD.obtenIDMaximoPrenda(appContext);
            GestorBD.crearPrenda(appContext, "test" + (i + 1), 1, tiposPrenda[i], 1, 1, idPerfil);
            cjto.add(idsPrenda[i]);
        }

        GestorBD.addConjunto(appContext, cjto);

        int numConjuntosAntes = GestorBD.ConjuntosEnBD(appContext).size();

        // Borrarlo

        GestorBD gestor = new GestorBD(appContext);
        GestorBD.BorrarConjunto(idCjto);

        int numConjuntosDespues = GestorBD.ConjuntosEnBD(appContext).size();
        assertEquals(numConjuntosDespues, numConjuntosAntes - 1);

        // Comprobar que no está el conjunto
        String sql = "SELECT COUNT(*) AS CUENTA FROM CONJUNTO WHERE ABRIGO=" + idsPrenda[0]
                + " AND SUDADERA=" + idsPrenda[1]
                + " AND CAMISETA=" + idsPrenda[2]
                + " AND PANTALON=" + idsPrenda[3]
                + " AND ZAPATO=" + idsPrenda[4]
                + " AND COMPLEMENTO=" + idsPrenda[5]
                + " AND ID_PERFIL=" + idPerfil;
        BaseDatos bd = new BaseDatos(appContext, nombreBaseDatos);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        int ok = -1;
        Cursor cur = sqldb.rawQuery(sql, null);
        if (cur.moveToFirst()) {
            ok = cur.getInt(0);
        }
        cur.close();
        bd.close();
        sqldb.close();

        assertEquals(0, ok);

    }

    @Test
    public void obtenIDMaximoConjuntoFuncionaBien() {

        int idPerfil = GestorBD.obtenIDMaximoPerfil(appContext);
        GestorBD.CrearPerfil(appContext, "foo", "bar");

        Conjunto cjto = new Conjunto();
        int idCjto = GestorBD.obtenIDMaximoConjunto(appContext);
        cjto.add(idCjto);

        int[] idsPrenda = new int[6];
        int[] tiposPrenda = {1, 13, 4, 11, 16, 8};
        for (int i = 0; i < 6; i++) {
            idsPrenda[i] = GestorBD.obtenIDMaximoPrenda(appContext);
            GestorBD.crearPrenda(appContext, "test" + (i + 1), 1, tiposPrenda[i], 1, 1, idPerfil);
            cjto.add(idsPrenda[i]);
        }

        GestorBD.addConjunto(appContext, cjto);
        int idCjtoDespues = GestorBD.obtenIDMaximoConjunto(appContext);

        assertEquals(idCjtoDespues, idCjto + 1);

    }

    @Test
    public void insertarConjuntoFuncionaBien() {

        int cjtosExistentesAntes = GestorBD.ConjuntosEnBD(appContext).size();

        int idPerfil = GestorBD.obtenIDMaximoPerfil(appContext);
        GestorBD.CrearPerfil(appContext, "foo", "bar");
        GestorBD.setIdPerfil(idPerfil);

        Conjunto cjto = new Conjunto();
        int idCjto = GestorBD.obtenIDMaximoConjunto(appContext);
        cjto.add(idCjto);

        int[] idsPrenda = new int[6];
        int[] tiposPrenda = {1, 13, 4, 11, 16, 8};
        for (int i = 0; i < 6; i++) {
            idsPrenda[i] = GestorBD.obtenIDMaximoPrenda(appContext);
            GestorBD.crearPrenda(appContext, "test" + (i + 1), 1, tiposPrenda[i], 1, 1, idPerfil);
            cjto.add(idsPrenda[i]);
        }

        GestorBD.addConjunto(appContext, cjto);

        String sql = "SELECT COUNT(*) AS CUENTA FROM CONJUNTO WHERE ABRIGO=" + idsPrenda[0]
                + " AND SUDADERA=" + idsPrenda[1]
                + " AND CAMISETA=" + idsPrenda[2]
                + " AND PANTALON=" + idsPrenda[3]
                + " AND ZAPATO=" + idsPrenda[4]
                + " AND COMPLEMENTO=" + idsPrenda[5]
                + " AND ID_PERFIL=" + idPerfil;
        BaseDatos bd = new BaseDatos(appContext, nombreBaseDatos);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        int ok = -1;
        Cursor cur = sqldb.rawQuery(sql, null);
        if (cur.moveToFirst()) {
            ok = cur.getInt(0);
        }
        cur.close();
        bd.close();
        sqldb.close();


        int cjtosExistentesDespues = GestorBD.ConjuntosEnBD(appContext).size();

        assertEquals(cjtosExistentesDespues, cjtosExistentesAntes + 1);
        assertEquals(1, ok);

    }

}
