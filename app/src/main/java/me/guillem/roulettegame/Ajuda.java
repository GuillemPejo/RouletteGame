package me.guillem.roulettegame;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Ajuda extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}