package com.example.dressmeapp.BaseDatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import android.R.integer;
import android.database.Cursor;
import android.util.Log;

public class LibreriaBD {

    public static int campo_int(Cursor pcursor, String pcampo) {
        return ( pcursor.getInt(pcursor.getColumnIndex(pcampo)));
    }


    public static String campo_string(Cursor pcursor, String pcampo) {
        return ( pcursor.getString(pcursor.getColumnIndex(pcampo)).trim());
    }


    public static String hoy ()
    {
        // Retorna un String con la fecha de hoy
        String dia,mes,anio;
        String fecha_sistema;

        Calendar c1 = Calendar.getInstance();
        dia = Integer.toString(c1.get(Calendar.DATE));
        mes = String.valueOf((c1.get(Calendar.MONTH)+1)); // los meses los cuenta desde cero
        anio = Integer.toString(c1.get(Calendar.YEAR));
        fecha_sistema = dia+"/"+mes+"/"+anio;

        return (fecha_sistema.toString());
    }

    public static String primero_de_mes()
    {
        // Retorna un String con la fecha de hoy
        String dia,mes,anio;
        String fecha_sistema;

        Calendar c1 = Calendar.getInstance();
        //  dia = Integer.toString(c1.get(Calendar.DATE));
        mes = String.valueOf((c1.get(Calendar.MONTH)+1)); // los meses los cuenta desde cero
        anio = Integer.toString(c1.get(Calendar.YEAR));

        dia = "01";
        fecha_sistema = dia+"/"+mes+"/"+anio;

        return (fecha_sistema.toString());
    }


    public static String primero_de_anio()
    {
        // Retorna un String con la fecha de hoy
        String dia,mes,anio;
        String fecha_sistema;

        Calendar c1 = Calendar.getInstance();
        //  dia = Integer.toString(c1.get(Calendar.DATE));
        //   mes = String.valueOf((c1.get(Calendar.MONTH)+1)); // los meses los cuenta desde cero
        anio = Integer.toString(c1.get(Calendar.YEAR));

        dia = "01";
        mes = "01";
        fecha_sistema = dia+"/"+mes+"/"+anio;

        return (fecha_sistema.toString());
    }
}

