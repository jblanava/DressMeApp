package com.example.dressmeapp.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDPerfil;
import com.example.dressmeapp.Objetos.Exportador;
import com.example.dressmeapp.R;




public class PerfilConfiguracionActivity extends AppCompatActivity {
    private Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_configuracion);
        getSupportActionBar().hide();
        enlazar_controles();
    }

    private void enlazar_controles() {

        contexto = getApplicationContext();
        Button bCambioDatos = findViewById(R.id.botonCambiaDatos);
        Button bCambioContrasenia = findViewById(R.id.botonCambiaContrasenia);
        Button bExportarDatos = findViewById(R.id.botonExportarDatos);
        Button bBorrarPerfil = findViewById(R.id.botonBorrarPerfil);
        bCambioContrasenia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ir_a_cambiar_contrasenia();
            }
        });

        bCambioDatos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mostrar_datos();
            }
        });

        bExportarDatos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                exportar_datos();
            }
        });

        bBorrarPerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                confirmar_borrar_perfil();
            }
        });
    }

    protected void confirmar_borrar_perfil() {

        AlertDialog.Builder alert_dialog = new AlertDialog.Builder(this);
        alert_dialog.setMessage("¿Desea eliminar el perfil?");
        alert_dialog.setTitle("ATENCIÓN");
        alert_dialog.setIcon(android.R.drawable.ic_dialog_alert);
        alert_dialog.setCancelable(false);
        alert_dialog.setPositiveButton("Sí, estoy seguro", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                borrado_de_perfil();
                //código Java si se ha pulsado sí
            }
        });
        alert_dialog.setNegativeButton("No, me lo he pensado mejor", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //No hace nada, se queda en la misma Activity
                //código java si se ha pulsado no
            }
        });

        alert_dialog.show(); // esto es importante :D

    }

    private void borrado_de_perfil() {

        int id= GestorBD.getIdPerfil();
        GestorBDPerfil.BorrarPerfil(contexto, id);

        this.finish();

        Intent borrado = new Intent(this, MainActivity.class);
        startActivity(borrado);
    }

    protected void mostrar_datos() {
        //No sabemos que hase, preguntar a María :D
        Intent salto = new Intent(this,MostrarDatosActivity.class);
        startActivity(salto);
    }

    protected void exportar_datos() {
        pedir_permisos();
    }

    protected void ir_a_cambiar_contrasenia() {
        Intent intent = new Intent(this, ConfiguracionContraseniaActivity.class);
        startActivity(intent);
    }


    private void pedir_permisos() {
        int estado = ContextCompat.checkSelfPermission(PerfilConfiguracionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (estado == PackageManager.PERMISSION_GRANTED) {
            new Exportador(this);
            Toast.makeText(PerfilConfiguracionActivity.this, "Se han exportado tus prendas en la carpeta de descargas", Toast.LENGTH_SHORT).show();

        } else {
            ActivityCompat.requestPermissions(PerfilConfiguracionActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int request_code, @NonNull String[] permissions, @NonNull int[] grant_results) {
        if (request_code == 0) {
            if (grant_results.length > 0 && grant_results[0] == PackageManager.PERMISSION_GRANTED) {
                new Exportador(this);
                Toast.makeText(PerfilConfiguracionActivity.this, "Se han exportado tus prendas en la carpeta de descargas", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(PerfilConfiguracionActivity.this, "El permiso para el almacenamiento está denegado", Toast.LENGTH_SHORT).show();

            }
        }
    }

}
