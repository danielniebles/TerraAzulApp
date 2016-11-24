package com.danielniebles.terraazulapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AgregarReservaActivity extends AppCompatActivity {

    DatePicker datePicker;
    Calendar calendar;
    int hora, minutos;
    int año, mes, dia, id;
    StringBuilder fecha;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private String FIREBASE_URL="https://terraazul-cd8d8.firebaseio.com/";
    private Firebase firebasedata;
    Button bAceptar;
    boolean flag = false;
    TimePicker timeHour;
    String usuario, horaCita;
    double latitud, longitud;
    EditText eDireccion, eCiudad;
    ArrayList<Citas> arrayCitas = new ArrayList<Citas>();
    int numCitas;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String UID;

    //Acá debe ir un calendario para seleccionar la cita a agendar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reserva);

        UID = user.getUid();

        timeHour = (TimePicker) findViewById(R.id.timeHour);
        eDireccion = (EditText) findViewById(R.id.eDireccion);
        eCiudad = (EditText) findViewById(R.id.eCiudad);
        bAceptar = (Button)findViewById(R.id.bAceptar);

        //Ubicacion de la universidad
        /*eLatitud.setText(String.valueOf(6.267768));
        eLongitud.setText(String.valueOf(-75.568720));*/

        Firebase.setAndroidContext(this);
        firebasedata = new Firebase(FIREBASE_URL);
        calendar = Calendar.getInstance();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        //Obtener de fecha
        año = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        firebasedata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Citas").exists()){
                    numCitas = (int)dataSnapshot.child("Citas").getChildrenCount();
                }else{
                    numCitas = 0;
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        bAceptar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                hora = timeHour.getCurrentHour();
                minutos = timeHour.getCurrentMinute();

                if (minutos<10){
                    horaCita = String.valueOf(hora)+":0"+String.valueOf(minutos);
                }else{
                    horaCita = String.valueOf(hora)+":"+String.valueOf(minutos);
                }

                LatLng prueba = getLocationFromAddress(getApplicationContext(), eDireccion.getText().toString()+", "+
                        eCiudad.getText().toString()+", Colombia");

                //if(prueba == null){
                    //flag = true;
                    //Toast.makeText(getApplicationContext(), "Error de conexión. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                //}else{
                    latitud = prueba.latitude;
                    longitud = prueba.longitude;
                //}

                /*latitud = Double.parseDouble(eLatitud.getText().toString());
                longitud = Double.parseDouble(eLongitud.getText().toString());*/

                if(TextUtils.isEmpty(fecha)){
                    Toast.makeText(getApplicationContext(), "Ingrese la fecha", Toast.LENGTH_SHORT).show();
                    //flag = false;
                }else{
                    //if(!flag==true){
                        Citas cita = new Citas(fecha.toString(), horaCita, latitud, longitud, UID, eDireccion.getText().toString());
                        firebasedata.child("Citas").child(Integer.toString(numCitas)).setValue(cita);
                        finish();
                    //}
                }
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

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

}