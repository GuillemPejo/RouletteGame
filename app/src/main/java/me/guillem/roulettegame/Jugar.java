package me.guillem.roulettegame;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Jugar extends AppCompatActivity {


    Button girar;

    Button menys;
    Button mes;

    TextView tokens;
    TextView aposta;

    TextView resultat;


    RadioButton negre;
    RadioButton vermell;

    RadioButton par;
    RadioButton impar;

    RadioButton primer;
    RadioButton segon;
    RadioButton tercer;

    EditText numero_ruleta;


    TextView text;
    ImageView roda;

    Animation zoom;


    Random r;

    int graus = 0, graus_antic = 0;

    int itokens = 10;
    int aposta_ini = 0;

    private static final float SECTOR = 360f / 37f / 2f;



    private static final String[] sectors = {"32 vermell", "15 negre",
            "19 vermell", "4 negre", "21 vermell", "2 negre", "25 vermell", "17 negre", "34 vermell",
            "6 negre", "27 vermell", "13 negre", "36 vermell", "11 negre", "30 vermell", "8 negre",
            "23 vermell", "10 negre", "5 vermell", "24 negre", "16 vermell", "33 negre",
            "1 vermell", "20 negre", "14 vermell", "31 negre", "9 vermell", "22 negre",
            "18 vermell", "29 negre", "7 vermell", "28 negre", "12 vermell", "35 negre",
            "3 vermell", "26 negre", "0"};


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        //iniciFirebase();

        girar = (Button) findViewById(R.id.spin);
        text = (TextView) findViewById(R.id.text);
        roda = (ImageView) findViewById(R.id.roda);
        tokens = (TextView) findViewById(R.id.tokens);
        numero_ruleta = (EditText) findViewById(R.id.numero_ruleta);
        mes = (Button) findViewById(R.id.mes);
        menys = (Button) findViewById(R.id.menys);
        tokens = (TextView) findViewById(R.id.tokens);

        aposta = (TextView) findViewById(R.id.aposta);

        negre = (RadioButton) findViewById(R.id.negre);
        vermell = (RadioButton) findViewById(R.id.vermell);
        par = (RadioButton) findViewById(R.id.par);
        impar = (RadioButton) findViewById(R.id.impar);
        primer = (RadioButton) findViewById(R.id.primer);
        segon = (RadioButton) findViewById(R.id.segon);
        tercer = (RadioButton) findViewById(R.id.tercer);

        resultat = (TextView) findViewById(R.id.resultat);

        zoom = AnimationUtils.loadAnimation(this,R.anim.zoom_in);


        Intent svc = new Intent(this, Musicadefons.class);
        startService(svc);




        tokens.setText("" + itokens);

        final String n_ruleta = numero_ruleta.getText().toString();

        //if(n_ruleta>36){Toast.makeText(getApplicationContext(), "No pots apostar un numero superior a 36", Toast.LENGTH_SHORT).show();}

        //girar.setEnabled(false);

        r = new Random();


        final MediaPlayer so_ruleta = MediaPlayer.create(this, R.raw.roulette_wheel);

        girar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
/*                if(itokens<0){
                    Toast.makeText(getApplicationContext(), "No tens suficient tokens per apostar", Toast.LENGTH_SHORT).show();

                }*/
                so_ruleta.seekTo(0);
                so_ruleta.start();
                graus_antic = graus % 360;
                graus = r.nextInt(3600) + 720;
                RotateAnimation rotate = new RotateAnimation(graus_antic, graus, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(9600);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        girar.setEnabled(false);
                        mes.setEnabled(false);
                        menys.setEnabled(false);
                        negre.setEnabled(false);
                        vermell.setEnabled(false);
                        par.setEnabled(false);
                        impar.setEnabled(false);
                        primer.setEnabled(false);
                        segon.setEnabled(false);
                        tercer.setEnabled(false);
                        numero_ruleta.setEnabled(false);

                        text.setBackground(null);
                        text.setText("");

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //Animation a = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
                        so_ruleta.pause();
                        text.setBackgroundResource(R.drawable.centre_text);
                        text.setText(numeroActual(360 - (graus % 360)));
                        text.setAnimation(zoom);
                        mes.setEnabled(true);
                        menys.setEnabled(true);
                        negre.setEnabled(true);
                        vermell.setEnabled(true);
                        par.setEnabled(true);
                        impar.setEnabled(true);
                        primer.setEnabled(true);
                        segon.setEnabled(true);
                        tercer.setEnabled(true);
                        numero_ruleta.setEnabled(true);
                        numero_ruleta.setText("");
                        //aposta.setText("0");
                        girar.setEnabled(true);
                        int numeroNum = Integer.parseInt(text.getText().toString().replaceAll("[^0-9]", ""));
                        if (negre.isChecked()){
                            if(text.getText().toString().contains("negre")){
                                Guanyar();
                                itokens = itokens + Integer.parseInt(aposta.getText().toString());
                            }else{
                                Perdre();
                                itokens = itokens - Integer.parseInt(aposta.getText().toString());
                            }
                        }else if (vermell.isChecked()){
                            if(text.getText().toString().contains("vermell")){
                                Guanyar();
                                itokens = itokens + Integer.parseInt(aposta.getText().toString());
                            }else{
                                Perdre();
                                itokens = itokens - Integer.parseInt(aposta.getText().toString());
                            }
                        }else if (par.isChecked()){
                            if(0==(numeroNum%2)){
                                Guanyar();
                                itokens = itokens + Integer.parseInt(aposta.getText().toString());
                            }else{
                                Perdre();
                                itokens = itokens - Integer.parseInt(aposta.getText().toString());
                            }
                        }else if (impar.isChecked()){
                            if(0!=(numeroNum%2)){
                                Guanyar();
                                itokens = itokens + Integer.parseInt(aposta.getText().toString());
                            }else{
                                Perdre();
                                itokens = itokens - Integer.parseInt(aposta.getText().toString());
                            }
                        }else if (primer.isChecked()){
                            if(numeroNum>=1 && numeroNum<=12){
                                Guanyar();
                                itokens = itokens + (Integer.parseInt(aposta.getText().toString()) * 3);
                            }else{
                                Perdre();
                                itokens = itokens - Integer.parseInt(aposta.getText().toString());
                            }
                        }else if (segon.isChecked()){
                            if(numeroNum>=13 && numeroNum<=24){
                                Guanyar();
                                itokens = itokens + (Integer.parseInt(aposta.getText().toString()) * 3);
                            }else{
                                Perdre();
                                itokens = itokens - Integer.parseInt(aposta.getText().toString());
                            }
                        }else if (tercer.isChecked()){
                            if(numeroNum>=25 && numeroNum<=36){
                                Guanyar();
                                itokens = itokens + (Integer.parseInt(aposta.getText().toString()) * 3);
                            }else{
                                Perdre();
                                itokens = itokens - Integer.parseInt(aposta.getText().toString());
                            }
                        }else if (!(isEmpty(numero_ruleta))){
                            if(numeroNum==Integer.parseInt(numero_ruleta.getText().toString())){
                                Guanyar();
                                itokens = itokens + (Integer.parseInt(aposta.getText().toString()) * 36);
                            }else{
                                Perdre();
                                itokens = itokens - Integer.parseInt(aposta.getText().toString());
                            }
                        }

                        tokens.setText("" + itokens);
                        Persona p = new Persona();
                        p.setNom(getIntent().getStringExtra("nom"));
                        p.setMax_aposta(aposta_ini);
                        p.setPuntuacio(itokens);
                        //databaseReference.child("Persones").child(p.getNom()).setValue(p);



                        //text.startAnimation(a);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                roda.startAnimation(rotate);


            }
        });

    }

    private void iniciFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private String numeroActual(int graus) {
        int i = 0;
        String text = null;

        do {
            // start and end of each sector on the wheel
            float start = SECTOR * (i * 2 + 1);
            float end = SECTOR * (i * 2 + 3);

            if (graus >= start && graus < end) {
                // degrees is in [start;end[
                // so text is equals to sectors[i];
                text = sectors[i];
            }

            i++;
            // now we can test our Android Roulette Game :)
            // That's all !
            // In the second part, you will learn how to add some bets on the table to play to the Roulette Game :)
            // Subscribe and stay tuned !

        } while (text == null && i < sectors.length);

        return text;
    }

    public void augmentar(View view) {
        if(aposta_ini ==itokens){
            Toast.makeText(getApplicationContext(), "No tens suficient tokens per apostar", Toast.LENGTH_SHORT).show();

        }else {
            aposta_ini = aposta_ini + 1;
            mostrar(aposta_ini);
        }
    }

    public void disminuir(View view) {
        if(aposta_ini ==0){
            mostrar(0);
        }else {
            aposta_ini = aposta_ini - 1;
            mostrar(aposta_ini);
        }
    }

    private void mostrar(int number) {
        aposta.setText("" + number);
    }

    public void rbClick(View v) {
        int clickat;
        negre.setChecked(false);
        vermell.setChecked(false);
        par.setChecked(false);
        impar.setChecked(false);
        primer.setChecked(false);
        segon.setChecked(false);
        tercer.setChecked(false);
        numero_ruleta.clearFocus();

        if(v.getId()==numero_ruleta.getId()){
            clickat = numero_ruleta.getId();

        }else {
            clickat = v.getId();
            RadioButton butto = (RadioButton) findViewById(clickat);
            butto.setChecked(true);
        }

        switch(clickat){

            case R.id.negre:
                resultat.setText(""+"NEGRE x2");

                break;

            case R.id.vermell:
                resultat.setText(""+"VERMELL x2");
                break;

            case R.id.par:
                resultat.setText(""+"PARELL x2");
                break;

            case R.id.impar:
                resultat.setText(""+"IMPARELL x2");
                break;

            case R.id.primer:
                resultat.setText(""+"1r x3");
                break;

            case R.id.segon:
                resultat.setText(""+"2n x3");
                break;

            case R.id.tercer:
                resultat.setText(""+"3 x3");
                break;

            case R.id.numero_ruleta:

                break;
        }

        numero_ruleta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    negre.setChecked(false);
                    vermell.setChecked(false);
                    par.setChecked(false);
                    impar.setChecked(false);
                    primer.setChecked(false);
                    segon.setChecked(false);
                    tercer.setChecked(false);
                    resultat.setText(numero_ruleta.getText().toString()+" x36");
                } else {
                    numero_ruleta.getText().clear();
                }
            }
        });
    }

    public void Guanyar(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final MediaPlayer so_guanyar = MediaPlayer.create(this, R.raw.so_guanyar);
        v.vibrate(500);
        so_guanyar.seekTo(0);
        so_guanyar.start();
        resultat.setText(""+"HAS GUANYAT");



    }
    public void Perdre(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
        final MediaPlayer so_perdre = MediaPlayer.create(this, R.raw.so_perdre);
        so_perdre.seekTo(0);
        so_perdre.start();
        resultat.setText(""+"HAS PERDUT");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_superior, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        startActivity(new Intent(Jugar.this, Ajuda.class));

        return super.onOptionsItemSelected(item);
        }
    }





/*
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        resultat.setText("Your choice: " + radioButton.getText());

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.negre:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.vermell:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.par:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.impar:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.primer:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.segon:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.tercer:
                if (checked)
                    // Pirates are the best
                    break;
        }
    }
*/

    /*
    public void funcio(View v)
    {
        negre.setBackgroundColor(getResources().getColor(android.R.color.black);
        vermell.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark);
        par.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark);
        impar.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light);


        boolean negre_s = false;
        boolean vermell_s = false;
        boolean par_s = false;
        boolean impar_s = false;


        switch (v.getId()){
            case (R.id.negre):
                negre_s = true;
                negre.setBackgroundResource(R.drawable.boto_aposta_selec);
                habilitar(negre);
                break;
            case (R.id.vermell):
                vermell_s = true;
                vermell.setBackgroundResource(R.drawable.boto_aposta_selec);
                break;
            case (R.id.par):
                par_s = true;
                par.setBackgroundResource(R.drawable.boto_aposta_selec);
                break;
            case (R.id.impar):
                impar_s = true;
                impar.setBackgroundResource(R.drawable.boto_aposta_selec);
                break;
            default:
                break;
        }
    }
    /*



    /*private Button void habilitar( hab){
        if (hab){
        girar.setEnabled(true);

    }
*/


