package com.example.dressmeapp.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBD2;
import com.example.dressmeapp.R;

import java.util.List;

public class AniadirCombinacion extends AppCompatActivity {

    Spinner Scolor1;
    Spinner Scolor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_combinacion);

        enlazar_controles();
    }


    void enlazar_controles() {// Enlaza los controles y rellena los Spinner con la lista de opciones

        Scolor1 = findViewById(R.id.spinner_color1);
        Scolor2 = findViewById(R.id.spinner_color2);
        Button Bguardar = findViewById(R.id.boton_guardar);


        List<String> colores = GestorBD.get_nombres_tabla(this, "color");
        ArrayAdapter<String> adapterColores = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colores);
        adapterColores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Scolor1.setAdapter(adapterColores);
        Scolor2.setAdapter(adapterColores);

        Bguardar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
    }

    void guardar()
    {
        int c1 = Scolor1.getSelectedItemPosition() + 1;
        int c2 = Scolor2.getSelectedItemPosition() + 1;

        if (GestorBD2.crearComboColor(this, c1, c2)) {
            Toast.makeText(AniadirCombinacion.this, "Se ha guardado la combinacion indicada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AniadirCombinacion.this, "Combinación de colores ya registrada", Toast.LENGTH_SHORT).show();
        }


    }
}
