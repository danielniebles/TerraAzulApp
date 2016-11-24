package com.danielniebles.terraazulapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity {

    Button bAddCar, bSave, bCancel;
    EditText eUser, ePass, eRepeat, eMail;
    RadioGroup rdgGroup1;
    String usuario, contraseña, sexo = "Femenino", mail;
    private String FIREBASE_URL="https://terraazul-cd8d8.firebaseio.com/";
    private Firebase firebasedata;
    ArrayList<Carro> autos;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        bAddCar = (Button)findViewById(R.id.bAddCar);
        eUser = (EditText)findViewById(R.id.eUser);
        ePass = (EditText)findViewById(R.id.ePass);
        eRepeat = (EditText)findViewById(R.id.eRepeat);
        eMail = (EditText)findViewById(R.id.eMail);
        rdgGroup1 = (RadioGroup)findViewById(R.id.rdgGroup1);
        bSave = (Button)findViewById(R.id.bSave);

        Firebase.setAndroidContext(this);
        firebasedata = new Firebase(FIREBASE_URL);

        //Consulta inicial de usuarios registrados
        firebasedata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Total").exists()){
                    id = Integer.parseInt(dataSnapshot.child("Total").getValue().toString());
                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        bAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ePass.getText().toString().equals(eRepeat.getText().toString())){
                    Intent intent = new Intent(getApplicationContext(), AgregarAutoActivity.class);
                    intent.putExtra("user", eUser.getText().toString());
                    intent.putExtra("pass", ePass.getText().toString());
                    intent.putExtra("email", eMail.getText().toString());
                    intent.putExtra("sexo", sexo);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Firebase firebd;

                int a = rdgGroup1.getCheckedRadioButtonId();
                switch (a){
                    case R.id.rFem:
                        sexo = "Femenino";
                        break;
                    case R.id.rMas:
                        sexo = "Masculino";
                        break;
                }

                usuario = eUser.getText().toString();
                contraseña = ePass.getText().toString();
                mail = eMail.getText().toString();

                Usuario user = new Usuario(usuario, contraseña, mail, sexo, autos);
                firebd = firebasedata.child("Usuarios").child("usuario"+id);
                firebd.setValue(user);
                id++;
                firebd = firebasedata.child("Total");
                firebd.setValue(id);

            }
        });
    }
}
