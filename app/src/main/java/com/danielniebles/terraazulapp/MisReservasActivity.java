package com.danielniebles.terraazulapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class MisReservasActivity extends NavActivity{

    FloatingActionButton fab;
    ArrayList<Citas> citas = new ArrayList<Citas>();
    Adapter adapter = null;
    ListView list;
    Button bDetalles, bCancelar;
    String fecha, hora;
    private String FIREBASE_URL="https://terraazul-cd8d8.firebaseio.com/";
    private Firebase firebasedata;
    int numCitas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_mis_reservas);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedorFrame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_mis_reservas, contentFrameLayout);

        Firebase.setAndroidContext(this);
        firebasedata = new Firebase(FIREBASE_URL);

        fab = (FloatingActionButton)findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AgregarReservaActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mis reservas");

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        //Citas cita = new Citas("Hoy", "El negro", "MiMazda");
        //citas.add(cita);

        list = (ListView)findViewById(R.id.list4);


        firebasedata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Citas").exists()){
                    numCitas = (int)dataSnapshot.child("Citas").getChildrenCount();
                }
                citas.clear();
                for(int i=0;i<=numCitas;i++){
                    if(dataSnapshot.child("Citas").child(Integer.toString(i)).exists()){
                        String fecha = dataSnapshot.child("Citas").child(Integer.toString(i)).child("fecha").getValue().toString();
                        String hora = dataSnapshot.child("Citas").child(Integer.toString(i)).child("hora").getValue().toString();
                        String direccion = dataSnapshot.child("Citas").child(Integer.toString(i)).child("direccion").getValue().toString();
                        double latitud = Double.valueOf(dataSnapshot.child("Citas").child(Integer.toString(i)).child("latitud").getValue().toString());
                        double longitud = Double.valueOf(dataSnapshot.child("Citas").child(Integer.toString(i)).child("longitud").getValue().toString());
                        String operario = dataSnapshot.child("Citas").child(Integer.toString(i)).child("uid").getValue().toString();
                        Citas cita = new Citas(fecha, hora, latitud, longitud, operario, direccion);
                        citas.add(cita);
                    }
                }

                adapter = new Adapter(getApplicationContext(), citas);
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    class Adapter extends ArrayAdapter<Citas> {
        public Adapter(Context context, ArrayList objects) {
            super(context, R.layout.item_reserva, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.item_reserva, null);

            TextView operario = (TextView)item.findViewById(R.id.tOperario);
            TextView auto = (TextView)item.findViewById(R.id.tAuto);
            TextView fecha = (TextView)item.findViewById(R.id.tFecha);
            TextView direccion = (TextView)item.findViewById(R.id.tDireccion1);
            ImageView imagen = (ImageView)item.findViewById(R.id.imagenreserva);

            operario.setText("Daniel");
            auto.setText("Mi Auto");
            fecha.setText((citas.get(position)).getFecha()+" a las "+citas.get(position).getHora());
            direccion.setText(citas.get(position).getDireccion());
            imagen.setImageResource(R.drawable.hoja);

            bCancelar = (Button)item.findViewById(R.id.bCancelar);
            bDetalles = (Button)item.findViewById(R.id.bDetalles);

            bDetalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(getApplicationContext(), MapsActivity.class);
                    intent2.putExtra("indice", String.valueOf(position));
                    startActivity(intent2);
                }
            });

            bCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    citas.remove(position);
                    list.invalidateViews();
                    firebasedata.child("Citas").removeValue();
                    firebasedata.child("Citas").setValue(citas);
                }
            });

            return item;
        }
    }
}
