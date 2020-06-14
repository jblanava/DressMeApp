package com.example.dressmeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.dressmeapp.BaseDatos.BaseDatos;
import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDAlgoritmo;
import com.example.dressmeapp.BaseDatos.GestorBDPerfil;
import com.example.dressmeapp.BaseDatos.GestorBDPrendas;
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

    // ¡¡Hacer tests borra la base de datos!!

    @Before
    public void prepararTest() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appContext.deleteDatabase(BaseDatos.nombreBD);
    }

    /********************************************************************************
     PERFILES
     ********************************************************************************/

    @Test
    public void insertarPerfilFuncionaBien() {

        GestorBDPerfil.crear_perfil(appContext, "UsuarioPrueba", "ContraseñaPrueba");
        assertTrue(GestorBDPerfil.usuario_existe(appContext, "UsuarioPrueba"));
        assertTrue(GestorBDPerfil.pass_correcta(appContext, "UsuarioPrueba", "ContraseñaPrueba"));

    }

    @Test
    public void obtenerIDMaximoPerfilFuncionaBien() {

        int maxCalculado = GestorBD.obtener_id_maximo(appContext, "PERFIL");
        GestorBDPerfil.crear_perfil(appContext, "UsuarioPrueba2", "ContraseñaPrueba2");
        int maxNuevoPerfil = GestorBD.get_id_tabla(appContext, "PERFIL", "UsuarioPrueba2");

        assertEquals(maxCalculado, maxNuevoPerfil);

    }

    @Test
    public void borrarPerfilFuncionaBien() {

        int perfilesAntes = 0;
        int perfilesDespues = 0;

        String sentencia = "SELECT COUNT(*) FROM PERFIL";
        BaseDatos bd = new BaseDatos(appContext, BaseDatos.nombreBD);
        SQLiteDatabase sqLiteDatabase = bd.getReadableDatabase();

        GestorBDPerfil.crear_perfil(appContext, "UsuarioPrueba3", "ContraseñaPrueba3");
        int maxNuevoPerfil = GestorBD.get_id_tabla(appContext, "PERFIL", "UsuarioPrueba3");

        Cursor cur = sqLiteDatabase.rawQuery(sentencia, null);
        if (cur.moveToFirst()) {
            perfilesAntes = cur.getInt(0);
        }

        GestorBDPerfil.borrar_perfil(appContext, maxNuevoPerfil);

        cur = sqLiteDatabase.rawQuery(sentencia, null);
        if (cur.moveToFirst()) {
            perfilesDespues = cur.getInt(0);
        }

        assertEquals(perfilesDespues, perfilesAntes - 1);

    }

    @Test
    public void actualizarPerfilFuncionaBien() {

        GestorBDPerfil.crear_perfil(appContext, "UsuarioPrueba4", "ContraseñaPrueba4");
        int id = GestorBD.get_id_tabla(appContext, "PERFIL", "UsuarioPrueba4");

        GestorBDPerfil.actualizar_perfil(appContext, id, "NuevaContraseña");

        assertTrue(GestorBDPerfil.pass_correcta(appContext, "UsuarioPrueba4", "NuevaContraseña"));

    }

    @Test
    public void getUserFuncionaBien() {

        GestorBDPerfil.crear_perfil(appContext, "UsuarioPrueba5", "ContraseñaPrueba5");
        int id = GestorBD.get_id_tabla(appContext, "PERFIL", "UsuarioPrueba5");

        assertEquals("UsuarioPrueba5", GestorBDPerfil.get_usuario(appContext, id));

    }

    @Test
    public void getPassFuncionaBien() {

        GestorBDPerfil.crear_perfil(appContext, "UsuarioPrueba6", "ContraseñaPrueba6");
        int id = GestorBD.get_id_tabla(appContext, "PERFIL", "UsuarioPrueba6");

        assertEquals("ContraseñaPrueba6", GestorBDPerfil.get_contrasenia(appContext, id));

    }

    /********************************************************************************
     PRENDAS Y CONJUNTOS
     ********************************************************************************/

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

    /********************************************************************************
     COLORES
     ********************************************************************************/

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

    /********************************************************************************
     HISTORIAL & CONJUNTOS
     ********************************************************************************/


    @Test
    public void borrarConjuntoFuncionaBien() {

        // Crear conjunto

        int idPerfil = GestorBD.obtener_id_maximo(appContext, "PERFIL");
        GestorBDPerfil.crear_perfil(appContext, "foo", "bar");
        GestorBD.idPerfil = idPerfil;

        Conjunto cjto = new Conjunto("test");
        int idCjto = GestorBD.obtener_id_maximo(appContext, "CONJUNTO");
        cjto.add(idCjto);

        int[] idsPrenda = new int[6];
        int[] tiposPrenda = {1, 13, 4, 11, 16, 8};
        for (int i = 0; i < 6; i++) {
            idsPrenda[i] = GestorBD.obtener_id_maximo(appContext, "PRENDA");
            GestorBDPrendas.crear_prenda(appContext, "test" + (i + 1), 1, tiposPrenda[i], 1, 1, idPerfil);
            cjto.add(idsPrenda[i]);
        }

        GestorBDAlgoritmo.add_conjunto(appContext, cjto, 0);

        int numConjuntosAntes = GestorBD.get_ids_tabla(appContext, "CONJUNTO").size();

        // Borrarlo

        GestorBDPrendas.borrar_conjunto(appContext, idCjto);

        int numConjuntosDespues = GestorBD.get_ids_tabla(appContext, "CONJUNTO").size();
        assertEquals(numConjuntosDespues, numConjuntosAntes - 1);

        // Comprobar que no está el conjunto
        String sql = "SELECT COUNT(*) AS CUENTA FROM CONJUNTO WHERE ABRIGO=" + idsPrenda[0]
                + " AND SUDADERA=" + idsPrenda[1]
                + " AND CAMISETA=" + idsPrenda[2]
                + " AND PANTALON=" + idsPrenda[3]
                + " AND ZAPATO=" + idsPrenda[4]
                + " AND COMPLEMENTO=" + idsPrenda[5]
                + " AND ID_PERFIL=" + idPerfil;
        BaseDatos bd = new BaseDatos(appContext, BaseDatos.nombreBD);
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

        int idPerfil = GestorBD.obtener_id_maximo(appContext, "PERFIL");
        GestorBDPerfil.crear_perfil(appContext, "foo", "bar");

        Conjunto cjto = new Conjunto("test");
        int idCjto = GestorBD.obtener_id_maximo(appContext, "CONJUNTO");
        cjto.add(idCjto);

        int[] idsPrenda = new int[6];
        int[] tiposPrenda = {1, 13, 4, 11, 16, 8};
        for (int i = 0; i < 6; i++) {
            idsPrenda[i] = GestorBD.obtener_id_maximo(appContext, "PRENDA");
            GestorBDPrendas.crear_prenda(appContext, "test" + (i + 1), 1, tiposPrenda[i], 1, 1, idPerfil);
            cjto.add(idsPrenda[i]);
        }

        GestorBDAlgoritmo.add_conjunto(appContext, cjto, 0);
        int idCjtoDespues = GestorBD.obtener_id_maximo(appContext, "CONJUNTO");

        assertEquals(idCjtoDespues, idCjto + 1);

    }

    @Test
    public void insertarConjuntoFuncionaBien() {

        int cjtosExistentesAntes = GestorBD.get_ids_tabla(appContext, "CONJUNTO").size();

        int idPerfil = GestorBD.obtener_id_maximo(appContext, "PERFIL");
        GestorBDPerfil.crear_perfil(appContext, "foo", "bar");
        GestorBD.idPerfil = idPerfil;

        Conjunto cjto = new Conjunto("test!");
        int idCjto = GestorBD.obtener_id_maximo(appContext, "CONJUNTO");
        cjto.add(idCjto);

        int[] idsPrenda = new int[6];
        int[] tiposPrenda = {1, 13, 4, 11, 16, 8};
        for (int i = 0; i < 6; i++) {
            idsPrenda[i] = GestorBD.obtener_id_maximo(appContext, "PRENDA");
            GestorBDPrendas.crear_prenda(appContext, "test" + (i + 1), 1, tiposPrenda[i], 1, 1, idPerfil);
            cjto.add(idsPrenda[i]);
        }

        GestorBDAlgoritmo.add_conjunto(appContext, cjto, 0);

        String sql = "SELECT COUNT(*) AS CUENTA FROM CONJUNTO WHERE ABRIGO=" + idsPrenda[0]
                + " AND SUDADERA=" + idsPrenda[1]
                + " AND CAMISETA=" + idsPrenda[2]
                + " AND PANTALON=" + idsPrenda[3]
                + " AND ZAPATO=" + idsPrenda[4]
                + " AND COMPLEMENTO=" + idsPrenda[5]
                + " AND ID_PERFIL=" + idPerfil;
        BaseDatos bd = new BaseDatos(appContext, BaseDatos.nombreBD);
        SQLiteDatabase sqldb = bd.getReadableDatabase();

        int ok = -1;
        Cursor cur = sqldb.rawQuery(sql, null);
        if (cur.moveToFirst()) {
            ok = cur.getInt(0);
        }
        cur.close();
        bd.close();
        sqldb.close();


        int cjtosExistentesDespues = GestorBD.get_ids_tabla(appContext, "CONJUNTO").size();

        assertEquals(cjtosExistentesDespues, cjtosExistentesAntes + 1);
        assertEquals(1, ok);

    }
}
