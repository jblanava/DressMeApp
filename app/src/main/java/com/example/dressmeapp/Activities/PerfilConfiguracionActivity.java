package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Exportador;
import com.example.dressmeapp.R;


public class PerfilConfiguracionActivity extends AppCompatActivity {
    private Button cambioDatos;
    private Button cambioContrasenia;
    private Button exportarDatos;
    private Button borrarPerfil;
    private Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_configuracion);
        getSupportActionBar().hide();
        enlazarControles();
    }

    private void enlazarControles() {

        contexto = getApplicationContext();
        cambioDatos = findViewById(R.id.botonCambiaDatos);
        cambioContrasenia = findViewById(R.id.botonCambiaContrasenia);
        exportarDatos = findViewById(R.id.botonExportarDatos);
        borrarPerfil = findViewById(R.id.botonBorrarPerfil);
        cambioContrasenia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irCambiarContrasenia();
            }
        });

        cambioDatos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MostrarDatos();
            }
        });

        exportarDatos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ExportarDatos();
            }
        });

        borrarPerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irBorrarPerfil();
            }
        });
    }


    protected void irBorrarPerfil() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Desea eliminar el perfil?");
        alertDialog.setTitle("ATENCIÓN");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí, estoy seguro", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                borrado_de_perfil();
                //código Java si se ha pulsado sí
            }
        });
        alertDialog.setNegativeButton("No, me lo he pensado mejor", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //No hace nada, se queda en la misma Activity
                //código java si se ha pulsado no
            }
        });

        alertDialog.show(); // esto es importante :D

    }

    private void borrado_de_perfil() {

        int id= GestorBD.getIdPerfil();
        GestorBD.BorrarPerfil(contexto, id);

        this.finish();

        Intent borrado = new Intent(this, MainActivity.class);
        startActivity(borrado);



    }

    protected void MostrarDatos() {
        //No sabemos que hase, preguntar a María :D
    Intent salto = new Intent(this,MostrarDatosActivity.class);
    startActivity(salto);


    }

    protected void ExportarDatos() {
        new Exportador(this);
    }

    protected void irCambiarContrasenia() {
        Intent cambioContra = new Intent(this, ConfiguracionContraseniaActivity.class);
        startActivity(cambioContra);
    }
}
