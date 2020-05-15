package com.example.dressmeapp;


        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.Calendar;

        import android.R.integer;
        import android.database.Cursor;
        import android.util.Log;

public class LibreriaBD {

    public static   String Rs_String (ResultSet rs , String pcampo)
    {
        try {

            if (rs.getString(pcampo)!= null)
                return rs.getString(pcampo);
            else
                return ""; // Por si es null

        } catch (SQLException e) {

            e.printStackTrace();
            return "error en campo "+pcampo; //
        }
    }

    public static int Rs_int (ResultSet rs , String pcampo)
    {
        try {
            return rs.getInt(pcampo);

        } catch (SQLException e) {

            e.printStackTrace();
            return 0 ;// Error Null , o el campo no existe jejejeje
        }
    }

    public static boolean Rs_boolean (ResultSet rs , String pcampo)
    {
        try {
            return rs.getBoolean(pcampo);

        } catch (SQLException e) {

            e.printStackTrace();
            return false; // Error Null , o el campo no existe jejejeje
        }
    }

    public static double Rs_double (ResultSet rs , String pcampo)
    {
        try {
            return rs.getDouble(pcampo);

        } catch (SQLException e) {

            e.printStackTrace();
            return 0 ;// Error Null , o el campo no existe jejejeje
        }
    }


    public static   int CampoInt (Cursor pcursor , String pcampo)
    {
        return ( pcursor.getInt(pcursor.getColumnIndex(pcampo)));
    }

    public static   double  CampoDouble (Cursor pcursor , String pcampo)
    {
        return ( pcursor.getDouble(pcursor.getColumnIndex(pcampo)));
    }

    public static double redondea ( double pnumero)  // Redondea a 2 decimales
    {
        double retorna;
        retorna = (double) Math.round(pnumero*100)/100;
        return retorna;
    }

    public static double redondea2( double pnumero)  // Redondea a 2 decimales
    {
        return redondea(pnumero);  // Por ponerla con sentio
    }

    public static double redondea3( double pnumero)  // Redondea a 3 decimales
    {
        double retorna;
        retorna = (double) Math.round(pnumero*1000)/1000;
        return retorna;
    }


    public static	String Campo (Cursor pcursor , String pcampo)
    {
        return ( pcursor.getString(pcursor.getColumnIndex(pcampo)).trim());
    }

    public static String QuitaComas(String pcadena)
    {

        return pcadena.replace("'"," ").replace("%", " ");


    }


    public static String double2String(double d)
    {
        if (d==(long)d)
            return String.format("%d", (long)d);
        else
            return String.format("%s", d);
    }

    public static double String2double(String numero)
    {
        try{
            double dnumero = Double.parseDouble(numero);
            return dnumero;
        }
        catch(final Exception e)
        {
            return 0;
        }

    }

    public static double String2int(String numero)
    {
        try{
            int dnumero = Integer.parseInt(numero);
            return dnumero;
        }
        catch(final Exception e)
        {
            return 0;
        }

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

