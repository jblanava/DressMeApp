package com.example.dressmeapp.Activities;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDPerfil;
import com.example.dressmeapp.R;

public class MostrarDatosActivity extends AppCompatActivity {
    private TextView muestraUser;
    private TextView muestraPassword;
    private Context contexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos); //Puede ser
        getSupportActionBar().hide();
        enlazarControles();
        muestraDatos();
    }



    private void enlazarControles(){

        contexto = getApplicationContext();

        muestraUser = findViewById(R.id.MuestraUser);
        muestraPassword = findViewById(R.id.PASSWORD);

        Button botonPass = findViewById(R.id.BotonPass);
        botonPass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mostrarDatos_mas_pass();
            }
        });


    }

    private void muestraDatos() {

        int id= GestorBD.getIdPerfil();
        String user = GestorBDPerfil.getUser(contexto, id);
        String pass = "********";
        muestraUser.setText(user);
        muestraPassword.setText(pass);

    }
    private void mostrarDatos_mas_pass() {

        int id= GestorBD.getIdPerfil();
       String user = GestorBDPerfil.getUser(contexto, id);
       String pass_mostrada = GestorBDPerfil.getPass(this, id);

       muestraUser.setText(user);
       muestraPassword.setText(pass_mostrada);
    }

}