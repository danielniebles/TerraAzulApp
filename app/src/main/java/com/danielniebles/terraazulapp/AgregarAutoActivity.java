package com.danielniebles.terraazulapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AgregarAutoActivity extends AppCompatActivity {


    Spinner sMarca;
    Button bFinalizar;
    String usuario, contraseña, mail, sexo, nombre, marca, modelo, placa;
    EditText ePlaca1, ePlaca2, eModelo, eNombreauto;
    RadioGroup rdgGroup2;
    int id;
    boolean mascotas;
    private String FIREBASE_URL="https://terraazul-cd8d8.firebaseio.com/";
    private Firebase firebasedata;
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
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

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

        sMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                marca = sMarca.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            usuario = extras.getString("user");
            contraseña = extras.getString("pass");
            mail = extras.getString("email");
            sexo = extras.getString("sexo");

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
            flag = true;
        }else{

        }

        bFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Firebase firebd;
                int a = rdgGroup2.getCheckedRadioButtonId();
                switch (a){
                    case R.id.rSi:
                        mascotas = true;
                        break;
                    case R.id.rNo:
                        mascotas = false;
                        break;
                }

                nombre = eNombreauto.getText().toString();
                modelo = eModelo.getText().toString();
                placa = ePlaca1.getText().toString()+"-"+ePlaca2.getText().toString();

                if(flag == true){
                    Carro carro = new Carro(nombre, marca, modelo, placa, mascotas);
                    autos.add(carro);

                    Usuario user = new Usuario(usuario, contraseña, mail, sexo, autos, id);
                    firebd = firebasedata.child("Usuarios").child("usuario"+id);
                    firebd.setValue(user);

                    editor.putInt("id",id);
                    editor.commit();
                    id++;
                    firebd = firebasedata.child("Total");
                    firebd.setValue(id);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{

                }



            }
        });





    }
}
