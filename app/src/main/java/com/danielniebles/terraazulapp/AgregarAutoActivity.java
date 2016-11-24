package com.danielniebles.terraazulapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AgregarAutoActivity extends AppCompatActivity {


    Spinner sMarca;
    Button bFinalizar;
    String usuario, contraseña, mail, sexo, nombre, marca, modelo, placa, mascotas;
    EditText ePlaca1, ePlaca2, eModelo, eNombreauto;
    RadioGroup rdgGroup2;
    int carros;
    private String FIREBASE_URL="https://terraazul-cd8d8.firebaseio.com/";
    private Firebase firebasedata;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String UID;
    ArrayList<Carro> autos = new ArrayList<>();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_auto);

        Firebase.setAndroidContext(this);
        firebasedata = new Firebase(FIREBASE_URL);
        UID = user.getUid();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agregar auto");

        ePlaca1 = (EditText)findViewById(R.id.ePlaca1);
        ePlaca2 = (EditText)findViewById(R.id.ePlaca2);
        eModelo = (EditText)findViewById(R.id.eModelo);
        eNombreauto = (EditText)findViewById(R.id.eNombreauto);
        rdgGroup2 = (RadioGroup) findViewById(R.id.rdgGroup2);

        sMarca = (Spinner)findViewById(R.id.sMarca);
        bFinalizar = (Button)findViewById(R.id.bFinalizar);

        List list = new ArrayList();
        list.add("Seleccione:");
        list.add("Renault");
        list.add("Mazda");
        list.add("Audi");
        list.add("Toyota");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,list);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sMarca.setAdapter(arrayAdapter);

        firebasedata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    //String carros1 = dataSnapshot.child("Usuarios").child(UID).child("numCarros").getValue().toString();
                if(dataSnapshot.child("Usuarios").child(UID).child("Carros").exists()){
                    carros = (int)dataSnapshot.child("Usuarios").child(UID).child("Carros").getChildrenCount();
                }else{
                    carros = 0;
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        sMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                marca = sMarca.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = rdgGroup2.getCheckedRadioButtonId();
                switch (a){
                    case R.id.rSi:
                        mascotas = "Sí";
                        break;
                    case R.id.rNo:
                        mascotas = "No";
                        break;
                }
                nombre = eNombreauto.getText().toString();
                modelo = eModelo.getText().toString();
                placa = ePlaca1.getText().toString()+"-"+ePlaca2.getText().toString();

                Carro carro = new Carro(nombre, marca, modelo, placa, mascotas);
                firebasedata.child("Usuarios").child(UID).child("Carros").child(Integer.toString(carros)).setValue(carro);
                /*carros = carros + 1;
                firebasedata.child("Usuarios").child(UID).child("numCarros").setValue(carros);*/
                finish();
            }
        });

    }
}
