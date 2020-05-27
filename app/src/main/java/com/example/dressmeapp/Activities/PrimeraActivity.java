package com.example.dressmeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dressmeapp.R;

import java.util.Timer;
import java.util.TimerTask;
public class PrimeraActivity extends AppCompatActivity {

    private Timer timer;
    private Context ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera);
        timer = new Timer();
        ct = this;
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
        }, 2000);
    }
}
