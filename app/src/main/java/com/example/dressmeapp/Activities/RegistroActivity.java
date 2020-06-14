package com.example.dressmeapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDPerfil;
import com.example.dressmeapp.Objetos.Importador;
import com.example.dressmeapp.R;

public class RegistroActivity extends AppCompatActivity {

    private EditText eNombre;
    private EditText eContrasenia;
    private EditText eConfirma_contrasenia;
    private TextView tError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();
        enlazar_controles();
    }

    private void enlazar_controles() {

        eNombre = findViewById(R.id.textNombre);
        eContrasenia = findViewById(R.id.textContrasenia);
        eConfirma_contrasenia = findViewById(R.id.textConfirmaContrasenia);
        tError = findViewById(R.id.textError);
        Button bConfirmar = findViewById(R.id.buttonRegistro);

        bConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar_crear_cuenta(eNombre.getText().toString(),
                        eContrasenia.getText().toString(),
                        eConfirma_contrasenia.getText().toString());
            }
        });

        Button bImportar = findViewById(R.id.buttonImportar);

        bImportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importar_datos();
            }
        });

    }

    private void validar_crear_cuenta(String nombre, String contrasenia, String contrasenia_confirmada) {

        String errores = "";
        boolean ok = true;

        // Validar nombre
        if (nombre.isEmpty()) {
            ok = false;
            errores += getString(R.string.registro_error_sinnombre) + "\n";
        } else if (GestorBDPerfil.UsuarioEstaEnBD(getApplicationContext(), nombre)) {
            ok = false;
            errores += getString(R.string.registro_error_nombreusado) + "\n";
        }

        // Validar contraseña
        if (!contrasenia.equals(contrasenia_confirmada)) {
            ok = false;
            errores += getString(R.string.registro_error_passnocoincide) + "\n";
        } else if (contrasenia.isEmpty()) {
            ok = false;
            errores += getString(R.string.registro_error_sinpass) + "\n";
        }

        // Mostrar errores o crear cuenta
        if (ok) {
            GestorBDPerfil.CrearPerfil(getApplicationContext(), nombre, contrasenia);
            GestorBD.idPerfil = GestorBD.get_id_tabla(getApplicationContext(), "PERFIL", nombre);
            ir_a_menu_principal();
        } else {
            tError.setText(errores);
        }

    }


    private void ir_a_menu_principal() {

        Intent nueva_actividad = new Intent(this, MenuPrincipalActivity.class);
        startActivity(nueva_actividad);
        finish();
    }

    private void importar_datos() {
        pedir_permisos();
    }


    private void pedir_permisos() {
        int estado_de_permiso = ContextCompat.checkSelfPermission(RegistroActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (estado_de_permiso == PackageManager.PERMISSION_GRANTED) {
            importar();
        } else {
            ActivityCompat.requestPermissions(RegistroActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                importar();
            } else {
                Toast.makeText(RegistroActivity.this, "El permiso para el almacenamiento está denegado", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void importar()
    {
        new Importador(this);
        Toast.makeText(RegistroActivity.this, "Se han importado tus prendas desde la carpeta de descargas", Toast.LENGTH_SHORT).show();
        finish();
    }

}
