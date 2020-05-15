package com.example.dressmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText textNombre;
    private EditText textContrasenia;
    private Button btnLogin;
    private Button btnRegistro;
    private int prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enlazarControles();
    }

    private void enlazarControles() {

        textNombre = findViewById(R.id.textNombre);
        textContrasenia = findViewById(R.id.textContrasenia);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aqui pongo donde vaya el registro
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irAMenuPrincipal();
                //quitarTeclado(v);
                // aqui pongo lo que hace el boton
                //login(e_usuario.getText().toString(), e_password.getText().toString());
            }
        });

    }

    private void irAMenuPrincipal() {

        Intent nuevaActividad = new Intent(this, MenuPrincipalActivity.class);
        startActivity(nuevaActividad);
        finish();
    }
    private void irARegistro(){
        Intent nuevaActividad = new Intent(this,RegistroActivity.class);
        startActivity(nuevaActividad);
    }

    private void comprobarNombre(String n){

        //definir esta opearcion de bases de datos
        if(EstaBD(n)){

            TextoError.setText("Nombre de usuario ya existente");

        }

    }

    protected void login(String usuario, String pass ){

        if(usuario.equalsIgnoreCase("1") && pass.equalsIgnoreCase("2")){
            irAMenuPrincipal();
        }else{
            TextoError.setText("Error en login");
        }

    }



}
