package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dressmeapp.BaseDatos.GestorBD2;
import com.example.dressmeapp.Objetos.ColorPrenda;
import com.example.dressmeapp.R;

public class NuevoColorActivity extends AppCompatActivity {

    private TextView txtError;
    private ImageView imgColor;
    private TextView txtNombreColor;

    private ColorPrenda miColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_color);
        getSupportActionBar().hide();
        enlazarControles();
    }

    private void enlazarControles() {

        txtError = findViewById(R.id.txtError);

        miColor = new ColorPrenda(0, "", "#000000");
        // ID = 0 (por definir cuando se añada el color a la BD)
        // Nombre por defecto = cadena vacía
        // Color por defecto = #000000

        imgColor = findViewById(R.id.imgColor);
        txtNombreColor = findViewById(R.id.txtNombreColor);
        txtNombreColor.setText(miColor.getNombre());

        SeekBar barRojo = findViewById(R.id.barRojo);
        barRojo.setProgress(miColor.getRojo());

        SeekBar barVerde = findViewById(R.id.barVerde);
        barVerde.setProgress(miColor.getVerde());

        SeekBar barAzul = findViewById(R.id.barAzul);
        barAzul.setProgress(miColor.getAzul());

        barRojo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                miColor.setRojo(progress);
                imgColor.setBackgroundColor(miColor.getAndroidColor());
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        barVerde.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                miColor.setVerde(progress);
                imgColor.setBackgroundColor(miColor.getAndroidColor());
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        barAzul.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                miColor.setAzul(progress);
                imgColor.setBackgroundColor(miColor.getAndroidColor());
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        Button btnAniadirColor = findViewById(R.id.btnAniadirColor);
        btnAniadirColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarNuevoColor(txtNombreColor.getText().toString(), miColor.getHex());
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void confirmarNuevoColor(String nombre, String hex) {
        if (nombre.length() != 0) {
            GestorBD2.crearColor(this, nombre, hex);
            finish();
        } else {
            txtError.setText("Ponga un nombre al color");
        }
    }


}
