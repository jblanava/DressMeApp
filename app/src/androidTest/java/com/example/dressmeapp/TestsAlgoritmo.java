package com.example.dressmeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.dressmeapp.BaseDatos.*;
import com.example.dressmeapp.Objetos.Conjunto;

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
public class TestsAlgoritmo {

    private static Context appContext;

    // ¡¡Hacer tests borra la base de datos!!

    @Before
    public void prepararTest() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appContext.deleteDatabase(BaseDatos.nombreBD);
    }

    @Test
    public void borrarConjuntoFuncionaBien() {

        // Crear perfil y loguearnos en él
        int idPerfil = GestorBDPerfil.crear_perfil(appContext, "foo", "bar");
        GestorBD.idPerfil = idPerfil;

        // Crear y añadir el conjunto
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

        // Extraer número de conjuntos antes
        int numConjuntosAntes = GestorBD.get_ids_tabla(appContext, "CONJUNTO").size();

        // Borrarlo
        GestorBDPrendas.borrar_conjunto(appContext, idCjto);

        // Extraer número de conjuntos después y asegurarnos de que es uno menos
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

        int idPerfil = GestorBDPerfil.crear_perfil(appContext, "foo", "bar");

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

        int idCjtoDespues = GestorBDAlgoritmo.add_conjunto(appContext, cjto, 0);

        assertEquals(idCjtoDespues, idCjto);

    }

    @Test
    public void insertarConjuntoFuncionaBien() {

        int cjtosExistentesAntes = GestorBD.get_ids_tabla(appContext, "CONJUNTO").size();

        int idPerfil = GestorBDPerfil.crear_perfil(appContext, "foo", "bar");
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
