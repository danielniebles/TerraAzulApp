package com.danielniebles.terraazulapp;

import java.util.Calendar;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class AgregarReservaActivity extends NavActivity {

    DatePicker datePicker;
    Calendar calendar;
    int año, mes, dia, id;
    StringBuilder fecha;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private String FIREBASE_URL="https://terraazul-cd8d8.firebaseio.com/";
    private Firebase firebasedata;
    Button bAceptar;
    String usuario;

    //Acá debe ir un calendario para seleccionar la cita a agendar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reserva);

        bAceptar = (Button)findViewById(R.id.bAceptar);

        Firebase.setAndroidContext(this);
        firebasedata = new Firebase(FIREBASE_URL);
        calendar = Calendar.getInstance();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        //Obtener de fecha
        año = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        id = prefs.getInt("id", 0);

        firebasedata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Usuarios").child("usuario"+id).exists()){
                    id = Integer.parseInt(dataSnapshot.child("Total").getValue().toString());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase firebd;
                Citas cita = new Citas(fecha.toString(), id);
                firebd = firebasedata.child("Citas").child("cita0");
                firebd.setValue(cita);
            }
        });

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view){
        showDialog(999);
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id == 999){
            return new DatePickerDialog(this,myDateListener,año,mes,dia);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3){
            showDate(arg1,arg2+1,arg3);
        }
    };

    private void showDate(int año, int mes, int dia){
        fecha = new StringBuilder().append(dia).append("/").append(mes).append("/")
                .append(año);
    }

}
