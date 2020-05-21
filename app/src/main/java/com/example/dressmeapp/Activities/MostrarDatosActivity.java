package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.R;

public class MostrarDatosActivity extends AppCompatActivity {
    private TextView user;
    private TextView muestraUser;
    private TextView pass;
    private TextView muestraPassword;
    private Context contexto;
    private Button BotonPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos); //Puede ser
        enlazarControles();
        muestraDatos();
    }



    private void enlazarControles(){

        contexto = getApplicationContext();

        user = findViewById(R.id.userPerfil);
        muestraUser = findViewById(R.id.MuestraUser);
        pass = findViewById(R.id.Contrasenia);
        muestraPassword = findViewById(R.id.PASSWORD);

        BotonPass = findViewById(R.id.BotonPass);
        BotonPass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mostrarDatos_mas_pass();
            }
        });


    }

    private void muestraDatos() {

        int id= GestorBD.getIdPerfil();
        String user = GestorBD.getUser(contexto, id);
        String pass = "********";
        muestraUser.setText(user);
        muestraPassword.setText(pass);

    }
    private void mostrarDatos_mas_pass() {

        int id= GestorBD.getIdPerfil();
       String user = GestorBD.getUser(contexto, id);
       String pass_mostrada = GestorBD.getPass(this, id);

       muestraUser.setText(user);
       muestraPassword.setText(pass_mostrada);
    }

}