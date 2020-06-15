package com.example.dressmeapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.GestorBDFotos;
import com.example.dressmeapp.Debug.Debug;
import com.example.dressmeapp.R;

import java.io.ByteArrayOutputStream;
import java.util.Timer;
import java.util.TimerTask;
public class PrimeraActivity extends AppCompatActivity {

    private Timer timer;
    private Context ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera);
        getSupportActionBar().hide();
        timer = new Timer();
        ct = this;

        comprobar_foto_por_defecto();
    }

    @Override
    protected void onResume(){
        super.onResume();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent it = new Intent(ct, MainActivity.class);
                startActivity(it);
                finish();
            }
        }, 1500);
    }

    private void comprobar_foto_por_defecto() {

        int idmax = GestorBD.obtener_id_maximo(ct, "FOTOS");
        Log.i("idmax", idmax + "");
        if (idmax == 1) {

            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.logologo);
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmap_data = stream.toByteArray();

            GestorBDFotos.guardar_foto(ct, bitmap_data);
        }
        Log.i("idmax", GestorBD.obtener_id_maximo(ct, "FOTOS") + "");

    }
}
