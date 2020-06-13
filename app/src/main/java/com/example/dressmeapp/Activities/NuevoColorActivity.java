package com.example.dressmeapp.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dressmeapp.BaseDatos.GestorBDPrendas;
import com.example.dressmeapp.Objetos.ColorPrenda;
import com.example.dressmeapp.R;

public class NuevoColorActivity extends AppCompatActivity {

    private TextView tError;
    private ImageView iColor;
    private TextView tNombreColor;

    private ColorPrenda color_prenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_color);
        getSupportActionBar().hide();
        enlazarControles();
    }

    private void enlazarControles() {

        tError = findViewById(R.id.txtError);

        color_prenda = new ColorPrenda(0, "", "#000000");
        // ID = 0 (por definir cuando se añada el color a la BD)
        // Nombre por defecto = cadena vacía
        // Color por defecto = #000000

        iColor = findViewById(R.id.imgColor);
        tNombreColor = findViewById(R.id.txtNombreColor);
        tNombreColor.setText(color_prenda.getNombre());

        SeekBar barRojo = findViewById(R.id.barRojo);
        barRojo.setProgress(color_prenda.getRojo());

        SeekBar barVerde = findViewById(R.id.barVerde);
        barVerde.setProgress(color_prenda.getVerde());

        SeekBar barAzul = findViewById(R.id.barAzul);
        barAzul.setProgress(color_prenda.getAzul());

        barRojo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                color_prenda.setRojo(progress);
                iColor.setBackgroundColor(color_prenda.getAndroidColor());
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        barVerde.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                color_prenda.setVerde(progress);
                iColor.setBackgroundColor(color_prenda.getAndroidColor());
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        barAzul.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                color_prenda.setAzul(progress);
                iColor.setBackgroundColor(color_prenda.getAndroidColor());
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        Button btnAniadirColor = findViewById(R.id.btnAniadirColor);
        btnAniadirColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarNuevoColor(tNombreColor.getText().toString(), color_prenda.getHex());
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void confirmarNuevoColor(String nombre, String hex) {
        if (nombre.length() != 0) {
            GestorBDPrendas.crear_color(this, nombre, hex);
            finish();
        } else {
            tError.setText("Ponga un nombre al color");
        }
    }


}
