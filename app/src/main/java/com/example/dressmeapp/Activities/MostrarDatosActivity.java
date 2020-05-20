package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

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
    }

    private void enlazarControles(){

        contexto = getApplicationContext();

        user = findViewById(R.id.userPerfil);
        muestraUser = findViewById(R.id.MuestraUser);
        pass = findViewById(R.id.Contrasenia);
        muestraPassword = findViewById(R.id.PASSWORD);
        muestraDatos();

    }

    private void muestraDatos() {

        int id= GestorBD.getIdPerfil();
        String user = GestorBD.getUser(contexto, id);
        String pass = GestorBD.getPass(contexto, id);
        muestraUser.setText(user);
        muestraPassword.setText(pass);

    }

/*
    public void ocultarContrasenia{


        BotonPass = findViewById(R.id.BotonPass);

        BotonPass.setOnTouchListener(new OnTouchListener()){
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        }
    }
}
*/
}