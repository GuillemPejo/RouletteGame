package me.guillem.roulettegame;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    Button jugar, ranking, ajuda, okey;
    EditText nom_persona;
    Animation desota, dedalt, girar;
    ImageView titol, ruleta;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        jugar= (Button) findViewById(R.id.jugar);
        ranking= (Button) findViewById(R.id.ranking);
        ajuda= (Button) findViewById(R.id.ajuda);
        ruleta= (ImageView) findViewById(R.id.ruleta);
        titol= (ImageView) findViewById(R.id.titol);

        dedalt = AnimationUtils.loadAnimation(this,R.anim.dedalt);
        desota = AnimationUtils.loadAnimation(this,R.anim.desota);
        girar = AnimationUtils.loadAnimation(this,R.anim.girar);

        AnimationSet ruleta_anim = new AnimationSet(false);//false means don't share interpolators


        titol.setAnimation(dedalt);
        ruleta_anim.addAnimation(girar);
        ruleta_anim.addAnimation(desota);
        ruleta.startAnimation(ruleta_anim);

        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, Jugar.class));
                openDialog();
            }
        });

        ajuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Ajuda.class));
            }
        });
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Ranking.class));
            }
        });


        }


    public void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialeg_nom);
        okey = (Button) dialog.findViewById(R.id.okey);
        nom_persona = (EditText) dialog.findViewById(R.id.nom_persona);

        dialog.setTitle("Nom");
        dialog.show();
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = nom_persona.getText().toString();
                Intent intent = new Intent(MainActivity.this, Jugar.class);
                intent.putExtra("nom", nom);
                startActivity(intent);
            }
        });

    }


    }


