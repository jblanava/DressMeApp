package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDPerfil;
import com.example.dressmeapp.R;

public class ConfiguracionContraseniaActivity extends AppCompatActivity {

    private EditText contraseniaAntigua;
    private EditText nuevaContrasenia;
    private EditText nuevaContrasenia2;
    private TextView textError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_contrasenia);
        getSupportActionBar().hide();
        enlaza_controles();
    }


    private void enlaza_controles(){

        contraseniaAntigua = findViewById(R.id.contraseniaAntigua);
        nuevaContrasenia = findViewById(R.id.nuevaContrasenia);
        nuevaContrasenia2 = findViewById(R.id.nuevaContrasenia2);
        textError=findViewById(R.id.textError);
        textError.setText("");

        Button botonActualizar = findViewById(R.id.botonActualizar);
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                update_pass();
            }
        });

    }
    private void update_pass(){

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
        } else if (!antiguaPass.equals(GestorBDPerfil.get_contrasenia(this, GestorBD.idPerfil))) {
            // Hay que introducir la contraseña antigua para cambiarla
            ok = false;
            errores += "Escriba la contraseña antigua para cambiarla";
        }

        if (ok) {
            GestorBDPerfil.actualizar_perfil(getApplicationContext(), GestorBD.idPerfil, nuevaContrasenia.getText().toString());

            Intent salto = new Intent(this, MenuPrincipalActivity.class);
            startActivity(salto);
            this.finish();
        } else {
            textError.setText(errores);
        }

    }



}
