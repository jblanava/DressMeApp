package com.example.dressmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistroActivity extends AppCompatActivity {

    private EditText textNombre;
    private EditText textContrasenia;
    private EditText textConfirmaContrasenia;
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
        btnConfirmar = findViewById(R.id.buttonRegistro);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textContrasenia.toString().equals(textConfirmaContrasenia.toString())){
                    crearPerfil();
                }
                }
        });

    }

        private void crearPerfil() {

    }

}
