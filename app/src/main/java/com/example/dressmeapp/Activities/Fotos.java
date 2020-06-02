package com.example.dressmeapp.Activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.dressmeapp.BaseDatos.BaseDatos;
import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.BaseDatos.LibreriaBD;
import com.example.dressmeapp.R;

import java.io.ByteArrayOutputStream;

public class Fotos extends AppCompatActivity {


    String id_activo;
    Boolean ha_hecho_foto;
    ImageView imagen;
    Button b_guardar;
    static final int REQUEST_IMAGE_CAPTURE = 1;
  //  static final int REQUEST_TAKE_PHOTO = 1;
  //  String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        ha_hecho_foto = false;
        enlazaControles();
        leer_parametro();
      //  verificarYPedirPermisosDeAlmacenamiento();
        verificarYPedirPermisosDeCamara();

    }

    protected void leer_parametro(){
        id_activo = getIntent().getExtras().getString("id_modificar");

    }


    protected void enlazaControles() {

         imagen = (ImageView) findViewById(R.id.imageView2);
        b_guardar = (Button) findViewById(R.id.foto_guardar);

        // Ahora pongo los eventos
        b_guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // aqui pongo lo que hace el boton
                guardar_foto();
            }
        });

    }

    private void guardar_foto() {
        if(ha_hecho_foto){
            GestorBD.eliminar_foto_antigua(this,id_activo);
            Bitmap bitmap = ((BitmapDrawable)imagen.getDrawable()).getBitmap();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap .compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] img = bos.toByteArray();

            GestorBD.guardarFoto(this,img, id_activo);

            this.finish();
        }else{
            Toast.makeText(Fotos.this, "No hay foto para subir", Toast.LENGTH_SHORT).show();
        }


    }


    protected void toma_foto_2() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imageBitmap);
            ha_hecho_foto = true;
        }
    }








    // Banderas que indicarán si tenemos permisos
    private boolean tienePermisoCamara = false,
            tienePermisoAlmacenamiento = false;

    // Código de permiso, defínelo tú mismo
    private static final int CODIGO_PERMISOS_CAMARA = 1,
            CODIGO_PERMISOS_ALMACENAMIENTO = 2;

    private void verificarYPedirPermisosDeCamara() {
        int estadoDePermiso = ContextCompat.checkSelfPermission(Fotos.this, Manifest.permission.CAMERA);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // En caso de que haya dado permisos ponemos la bandera en true
            // y llamar al método
            //permisoDeCamaraConcedido();
            toma_foto_2();
        } else {
            // Si no, entonces pedimos permisos. Ahora mira onRequestPermissionsResult
            ActivityCompat.requestPermissions(Fotos.this,
                    new String[]{Manifest.permission.CAMERA},
                    CODIGO_PERMISOS_CAMARA);
        }
    }

    private void verificarYPedirPermisosDeAlmacenamiento() {
        int estadoDePermiso = ContextCompat.checkSelfPermission(Fotos.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // En caso de que haya dado permisos ponemos la bandera en true
            // y llamar al método
            //permisoDeAlmacenamientoConcedido();
          // toma_foto_2();
        } else {
            // Si no, entonces pedimos permisos. Ahora mira onRequestPermissionsResult
            ActivityCompat.requestPermissions(Fotos.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CODIGO_PERMISOS_ALMACENAMIENTO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODIGO_PERMISOS_CAMARA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisoDeCamaraConcedido();
                } else {
                    permisoDeCamaraDenegado();
                }
                break;

            case CODIGO_PERMISOS_ALMACENAMIENTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisoDeAlmacenamientoConcedido();
                } else {
                    permisoDeAlmacenamientoDenegado();
                }
                break;

            // Aquí más casos dependiendo de los permisos
            // case OTRO_CODIGO_DE_PERMISOS...

        }
    }

    private void permisoDeAlmacenamientoConcedido() {
        // Aquí establece las banderas o haz lo que
        // ibas a hacer cuando el permiso se concediera. Por
        // ejemplo puedes poner la bandera en true y más
        // tarde en otra función comprobar esa bandera
       // Toast.makeText(Fotos.this, "El permiso para el almacenamiento está concedido", Toast.LENGTH_SHORT).show();
        tienePermisoAlmacenamiento = true;
    }

    private void permisoDeAlmacenamientoDenegado() {
        // Esto se llama cuando el usuario hace click en "Denegar" o
        // cuando lo denegó anteriormente
        Toast.makeText(Fotos.this, "El permiso para el almacenamiento está denegado", Toast.LENGTH_SHORT).show();
    }

    private void permisoDeCamaraConcedido() {
        // Aquí establece las banderas o haz lo que
        // ibas a hacer cuando el acceso a la cámara se condeciera
        // Por ejemplo puedes poner la bandera en true y más
        // tarde en otra función comprobar esa bandera
      //  Toast.makeText(Fotos.this, "El permiso para la cámara está concedido", Toast.LENGTH_SHORT).show();
        tienePermisoCamara = true;
    }

    private void permisoDeCamaraDenegado() {
        // Esto se llama cuando el usuario hace click en "Denegar" o
        // cuando lo denegó anteriormente
        Toast.makeText(Fotos.this, "El permiso para la cámara está denegado", Toast.LENGTH_SHORT).show();
    }









}
