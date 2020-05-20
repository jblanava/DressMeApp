package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.R;

public class RegistroActivity extends AppCompatActivity {

    private EditText textNombre;
    private EditText textContrasenia;
    private EditText textConfirmaContrasenia;
    private TextView textError;
    private Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
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


}
