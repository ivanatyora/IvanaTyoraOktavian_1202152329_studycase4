package com.example.android.ivanatyora_1202152329_studycase4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listNama(View view) { //pindah ke class ListNama
        Intent ln = new Intent(MainActivity.this,ListNama.class);
        startActivity(ln); //memulai intent
    }

    public void cariGambar(View view) { //pindah ke class CariGambar
        Intent cg = new Intent(MainActivity.this,CariGambar.class);
        startActivity(cg); //memulai intent
    }
}
