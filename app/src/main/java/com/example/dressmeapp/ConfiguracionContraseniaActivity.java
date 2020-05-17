package com.example.dressmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConfiguracionContraseniaActivity extends AppCompatActivity {
    private GestorBD gestor;
    private EditText contraseniaAntigua;
    private EditText nuevaContrasenia;
    private EditText nuevaContrasenia2;
    private TextView textError;
    private Button  botonActualizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_contrasenia);
        enlazaControles();
    }


    private void enlazaControles(){

        contraseniaAntigua = findViewById(R.id.contraseniaAntigua);
        nuevaContrasenia = findViewById(R.id.nuevaContrasenia);
        nuevaContrasenia2 = findViewById(R.id.nuevaContrasenia2);
        textError=findViewById(R.id.textError);
        textError.setText("");

        botonActualizar = findViewById(R.id.botonActualizar);
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UpdatePass();
            }
        });

    }
    private void UpdatePass(){

        if(nuevaContrasenia.getText().toString().equals(nuevaContrasenia2.getText().toString())) {
            int idPerfil = gestor.getIdPerfil();
            gestor.ActualizarPerfil(idPerfil, nuevaContrasenia.getText().toString());
        }else{
            textError.setText("ERROR, LAS CONTRASEÑAS NO COINCIDEN");
        }

    }



}
