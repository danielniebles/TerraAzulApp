package com.danielniebles.terraazulapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Loggin2Activity extends AppCompatActivity {

    Button bEnter1, bEnter2;
    TextView tRegister;
    EditText eUserL, ePassL;
    private String FIREBASE_URL="https://terraazul-cd8d8.firebaseio.com/";
    private Firebase firebasedata;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    int total, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin2);

        bEnter2 = (Button)findViewById(R.id.bEnter2);
        bEnter1 = (Button)findViewById(R.id.bEnter1);
        tRegister = (TextView)findViewById(R.id.tRegister);
        eUserL = (EditText)findViewById(R.id.eUserL);
        ePassL = (EditText)findViewById(R.id.ePassL);

        Firebase.setAndroidContext(this);
        firebasedata = new Firebase(FIREBASE_URL);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        firebasedata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Total").exists()){
                    total = Integer.parseInt(dataSnapshot.child("Total").getValue().toString());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        bEnter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("Invitado", 1);
                /*firebasedata.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(int k = 0; k < total; k++){
                            if(dataSnapshot.child("Usuarios").child("usuario"+k).exists()){
                                id = (dataSnapshot.child("Usuarios").child("usuario"+k).getValue(Usuario.class)).getId();
                            }
                        }

                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });*/

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        bEnter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("Invitado", 0);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        tRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(intent);
            }
        });


    }
}
