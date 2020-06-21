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
}

