package com.example.dressmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textError;
    private EditText textNombre;
    private EditText textContrasenia;
    private Button btnLogin;
    private Button btnRegistro;
    private GestorBD gestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enlazarControles();

    }

    private void enlazarControles() {

        textError = findViewById(R.id.textError);
        textError.setText("");
        textNombre = findViewById(R.id.textNombre);
        textContrasenia = findViewById(R.id.textContrasenia);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //irAMenuPrincipal();
                //quitarTeclado(v);
                // aqui pongo lo que hace el boton
                login(textNombre.getText().toString(), textContrasenia.getText().toString());
            }
        });

        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irARegistro();
            }
        });


    }

    private void irAMenuPrincipal() {

        Intent nuevaActividad = new Intent(this, MenuPrincipalActivity.class);
        startActivity(nuevaActividad);

    }
    private void irARegistro(){
        Intent nuevaActividad = new Intent(this,RegistroActivity.class);
        startActivity(nuevaActividad);
    }


    protected void login(String usuario, String pass) {

        String UsuarioError = "Usuario no encontrado en la base de datos";
        String PassError = "Contraseña incorrecta para el usuario introducido";

        if (gestor.UsuarioEstaEnBD(usuario) && gestor.PassCorrecta(usuario,pass)){
            GestorBD.idPerfil = gestor.GetIdPerfil(usuario,pass);
            irAMenuPrincipal();
        }else{
            if(gestor.UsuarioEstaEnBD(usuario)){ // Errores diferentes si el usuario esta o no en la base de datos
                textError.setText(PassError);
            }else{
                textError.setText(UsuarioError);
            }

        }

    }



}
