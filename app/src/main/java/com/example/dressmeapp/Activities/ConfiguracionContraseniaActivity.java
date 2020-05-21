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

        String antiguaPass = contraseniaAntigua.getText().toString();
        String nuevaPass = nuevaContrasenia.getText().toString();
        String nuevaPassConfirmada = nuevaContrasenia2.getText().toString();

        String errores = "";
        boolean ok = true;

        if (!nuevaPass.equals(nuevaPassConfirmada)) {
            // Las contraseñas nuevas deben coincidir
            ok = false;
            errores += "Las contraseñas nuevas no coinciden";
        } else if (nuevaPass.isEmpty()) {
            // La nueva contraseña no debe ser vacía
            ok = false;
            errores += "Introduzca una nueva contraseña";
        }/* else if (GestorBD.PassCorrecta(getApplicationContext(), , antiguaPass)) {
            // Hay que introducir la contraseña antigua para cambiarla
            ok = false;
            errores += "Introduzca una nueva contraseña";
        }*/

        if (ok) {
            GestorBD.ActualizarPerfil(getApplicationContext(), GestorBD.getIdPerfil(), nuevaContrasenia.getText().toString());

            Intent salto = new Intent(this, MenuPrincipalActivity.class);
            startActivity(salto);
            this.finish();
        } else {
            textError.setText(errores);
        }

    }



}
