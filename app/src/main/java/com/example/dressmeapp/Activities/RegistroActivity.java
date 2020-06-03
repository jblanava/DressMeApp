package com.example.dressmeapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.Objetos.Exportador;
import com.example.dressmeapp.Objetos.Importador;
import com.example.dressmeapp.R;

public class RegistroActivity extends AppCompatActivity {

    private EditText textNombre;
    private EditText textContrasenia;
    private EditText textConfirmaContrasenia;
    private TextView textError;
    private Button btnConfirmar;
    private Button btnImportar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();
        enlazarControles();
    }

    private void enlazarControles() {

        textNombre = findViewById(R.id.textNombre);
        textContrasenia = findViewById(R.id.textContrasenia);
        textConfirmaContrasenia = findViewById(R.id.textConfirmaContrasenia);
        textError = findViewById(R.id.textError);
        btnConfirmar = findViewById(R.id.buttonRegistro);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCrearCuenta(textNombre.getText().toString(),
                        textContrasenia.getText().toString(),
                        textConfirmaContrasenia.getText().toString());
            }
        });

        btnImportar = findViewById(R.id.buttonImportar);

        btnImportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importarDatos();
            }
        });

    }

    private void validarCrearCuenta(String nombre, String contrasenia, String contraseniaConfirmada) {

        String errores = "";
        boolean ok = true;

        // Validar nombre
        if (nombre.isEmpty()) {
            ok = false;
            errores += getString(R.string.registro_error_sinnombre) + "\n";
        } else if (GestorBD.UsuarioEstaEnBD(getApplicationContext(), nombre)) {
            ok = false;
            errores += getString(R.string.registro_error_nombreusado) + "\n";
        }

        // Validar contraseña
        if (!contrasenia.equals(contraseniaConfirmada)) {
            ok = false;
            errores += getString(R.string.registro_error_passnocoincide) + "\n";
        } else if (contrasenia.isEmpty()) {
            ok = false;
            errores += getString(R.string.registro_error_sinpass) + "\n";
        }

        // Mostrar errores o crear cuenta
        if (ok) {
            GestorBD.CrearPerfil(getApplicationContext(), nombre, contrasenia);
            GestorBD.setIdPerfil(GestorBD.IdPerfilAsociado(getApplicationContext(), nombre, contrasenia));
            irAMenuPrincipal();
        } else {
            textError.setText(errores);
        }

    }


    private void irAMenuPrincipal() {

        Intent nuevaActividad = new Intent(this, MenuPrincipalActivity.class);
        startActivity(nuevaActividad);
        finish();
    }

    private void importarDatos() {
        verificarYPedirPermisosDeAlmacenamiento();
    }


    private void verificarYPedirPermisosDeAlmacenamiento() {
        int estadoDePermiso = ContextCompat.checkSelfPermission(RegistroActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            new Importador(this);
            Toast.makeText(RegistroActivity.this, "Se han importado tus prendas desde la carpeta de descargas", Toast.LENGTH_SHORT).show();

        } else {
            ActivityCompat.requestPermissions(RegistroActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Importador(this);
                    Toast.makeText(RegistroActivity.this, "Se han importado tus prendas desde la carpeta de descargas", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegistroActivity.this, "El permiso para el almacenamiento está denegado", Toast.LENGTH_SHORT).show();

                }
                break;

        }
    }

}
