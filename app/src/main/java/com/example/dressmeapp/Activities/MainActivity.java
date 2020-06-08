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

public class MainActivity extends AppCompatActivity {

    private TextView textError;
    private EditText textNombre;
    private EditText textContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        enlazarControles();
    }

    private void enlazarControles() {

        textError = findViewById(R.id.textError);
        textError.setText("");
        textNombre = findViewById(R.id.textNombre);
        textContrasenia = findViewById(R.id.textContrasenia);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //irAMenuPrincipal();
                //quitarTeclado(v);
                // aqui pongo lo que hace el boton
                login(textNombre.getText().toString(), textContrasenia.getText().toString());
            }
        });

        Button btnRegistro = findViewById(R.id.btnRegistro);
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
        finish();
    }
    private void irARegistro(){
        Intent nuevaActividad = new Intent(this, RegistroActivity.class);
        startActivity(nuevaActividad);
    }

    private void login(String usuario, String pass) {

        String error = "";
        boolean ok = true;

        if (!GestorBD.UsuarioEstaEnBD(getApplicationContext(), usuario)) {
            ok = false;
            error = getString(R.string.login_incorrecto_usuario);
        } else if (!GestorBD.PassCorrecta(getApplicationContext(), usuario, pass)) {
            ok = false;
            error = getString(R.string.login_incorrecto_pass);
        }

        if (ok) {
            GestorBD.setIdPerfil(GestorBD.IdPerfilAsociado(getApplicationContext(), usuario, pass));
            irAMenuPrincipal();
        } else {
            textError.setText(error);
        }


    }



}
