package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dressmeapp.Activities.ConfiguracionContraseniaActivity;
import com.example.dressmeapp.Activities.MainActivity;
import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.R;

public class PerfilConfiguracionActivity extends AppCompatActivity {
    private Button cambioDatos;
    private Button cambioContrasenia;
    private Button borrarPerfil;
    private GestorBD gestor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_configuracion);
        enlazarControles();
    }

    private void enlazarControles() {
        //Aquí le paso el contexto al gestorBD
        gestor= new GestorBD(this);
        cambioDatos = findViewById(R.id.botonCambiaDatos);
        cambioContrasenia = findViewById(R.id.botonCambiaContrasenia);
        borrarPerfil = findViewById(R.id.botonBorrarPerfil);
        cambioContrasenia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irCambiarContrasenia();
            }
        });

        cambioDatos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irCambiarDatos();
            }
        });

        borrarPerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irBorrarPerfil();
            }
        });
    }

    protected void irBorrarPerfil() {
        final Intent borrado = new Intent(this, MainActivity.class);


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Desea eliminar el perfil?");
        alertDialog.setTitle("ATENCIÓN");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí, estoy seguro", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {

                int id= gestor.getIdPerfil();
                gestor.BorrarPerfil(getApplicationContext(),id);
                startActivity(borrado);
                //código Java si se ha pulsado sí
            }
        });
        alertDialog.setNegativeButton("No, me lo he pensado mejor", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //No hace nada, se queda en la imsma Activity
                //código java si se ha pulsado no
            }
        });

    }

    protected void irCambiarDatos() {
        //No sabemos que hase, preguntar a María :D
    }

    protected void irCambiarContrasenia() {
        Intent cambioContra = new Intent(this, ConfiguracionContraseniaActivity.class);
        startActivity(cambioContra);
    }
}
