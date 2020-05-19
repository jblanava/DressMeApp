package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.R;

public class MostrarDatosActivity extends AppCompatActivity {
    private TextView user;
    private TextView muestraUser;
    private TextView pass;
    private TextView muestraPassword;
    private GestorBD gestor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos); //Puede ser
        enlazarControles();
    }

    private void enlazarControles(){
       gestor= new GestorBD(this);
       user= findViewById(R.id.userPerfil);
       muestraUser=findViewById(R.id.MuestraUser);
       pass= findViewById(R.id.Contrasenia);
       muestraPassword=findViewById(R.id.PASSWORD);
       muestraDatos();

    }

    private void muestraDatos() {
    int id= gestor.getIdPerfil();
    String user= gestor.getUser(id);
    String pass= gestor.getPass(id);
    muestraUser.setText(user);
    muestraPassword.setText(pass);
    }
}
