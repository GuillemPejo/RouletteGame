package me.guillem.roulettegame;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Ranking extends AppCompatActivity {

    private List<Persona> personaList = new ArrayList <Persona>();
    ArrayAdapter<Persona> arrayAdapterPersona;

    ListView llista;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        iniciFirebase();
        llista = findViewById(R.id.llista);
        databaseReference.child("Persones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            personaList.clear();
            for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                Persona p = objSnapshot.getValue(Persona.class);

                personaList.add(p);
                arrayAdapterPersona = new ArrayAdapter <Persona>(Ranking.this, android.R.layout.simple_list_item_1, personaList
                );
                llista.setAdapter(arrayAdapterPersona);

            }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



}

    private void iniciFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
