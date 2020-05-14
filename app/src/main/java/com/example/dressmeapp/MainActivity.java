package com.example.dressmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText text_nombre;
    private EditText text_contrasenia;
    private Button btn_login;
    private Button btn_registro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enlazarControles();
    }

    private void enlazarControles() {

        text_nombre = findViewById(R.id.text_nombre);
        text_contrasenia = findViewById(R.id.text_contrasenia);
        btn_login = findViewById(R.id.btn_login);
        btn_registro = findViewById(R.id.btn_registro);

        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irAMenuPrincipal();
                //quitarTeclado(v);
                // aqui pongo lo que hace el boton
                //login(e_usuario.getText().toString(), e_password.getText().toString());
            }
        });

    }

    private void irAMenuPrincipal() {
        Intent nuevaActividad = new Intent(this, MenuPrincipal.class);
        startActivity(nuevaActividad);
        finish();
    }

}
