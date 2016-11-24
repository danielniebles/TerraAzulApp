package com.danielniebles.terraazulapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AutosFragment extends Fragment {


    ListView list2;
    ArrayList<Carro> carros = new ArrayList<Carro>();
    private static final String FIREBASE_URL="https://terraazul-cd8d8.firebaseio.com/";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int numero, clicked;
    private Firebase firebasedatos;
    FloatingActionButton floatingActionButton;
    String UID;
    Adapter adaptador = null;


    public AutosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_autos, container, false);
        Firebase.setAndroidContext(getActivity());
        firebasedatos = new Firebase(FIREBASE_URL);
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab3);

        UID = user.getUid();

        list2 = (ListView) view.findViewById(R.id.list3);

        firebasedatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Usuarios").child(UID).child("Carros").exists()){
                    numero = (int)dataSnapshot.child("Usuarios").child(UID).child("Carros").getChildrenCount();
                }
                carros.clear();
                for(int i=0;i<=numero;i++){
                    if(dataSnapshot.child("Usuarios").child(UID).child("Carros").child(Integer.toString(i)).exists()){
                        String nombre = dataSnapshot.child("Usuarios").child(UID).child("Carros").child(Integer.toString(i)).child("nombre").getValue().toString();
                        String marca = dataSnapshot.child("Usuarios").child(UID).child("Carros").child(Integer.toString(i)).child("marca").getValue().toString();
                        String mascotas = dataSnapshot.child("Usuarios").child(UID).child("Carros").child(Integer.toString(i)).child("mascotas").getValue().toString();
                        String modelo = dataSnapshot.child("Usuarios").child(UID).child("Carros").child(Integer.toString(i)).child("modelo").getValue().toString();
                        String placa = dataSnapshot.child("Usuarios").child(UID).child("Carros").child(Integer.toString(i)).child("placa").getValue().toString();
                        Carro carrolist = new Carro(nombre, marca, modelo, placa, mascotas);
                        carros.add(carrolist);
                    }
                }
                adaptador = new Adapter(getActivity(), carros);
                list2.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AgregarAutoActivity.class);
                startActivity(intent);
            }
        });

        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clicked = position;
                Toast.makeText(getActivity(),Integer.toString(clicked), Toast.LENGTH_SHORT);
            }
        });

        return view;
    }

    class Adapter extends ArrayAdapter<Carro> {
        public Adapter(Context context, ArrayList objects) {
            super(context, R.layout.carro_item, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.carro_item, null);

            TextView nombre = (TextView)item.findViewById(R.id.tNombrecarro);
            nombre.setText((carros.get(position)).getNombre());

            TextView marca = (TextView)item.findViewById(R.id.tMarca);
            marca.setText((carros.get(position)).getMarca());

            TextView modelo = (TextView)item.findViewById(R.id.tModelo);
            modelo.setText((carros.get(position)).getModelo());

            TextView placa = (TextView)item.findViewById(R.id.tPlaca);
            placa.setText((carros.get(position)).getPlaca());

            TextView mascotas = (TextView)item.findViewById(R.id.tMascotas);
            mascotas.setText((carros.get(position)).getMascotas());

            CircularImageView imagen = (CircularImageView)item.findViewById(R.id.imagenauto);
            imagen.setImageResource(R.drawable.circlecar);

            Button prueba = (Button)item.findViewById(R.id.prueba);
            prueba.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carros.remove(position);
                    list2.invalidateViews();
                    firebasedatos.child("Usuarios").child(UID).child("Carros").removeValue();
                    firebasedatos.child("Usuarios").child(UID).child("Carros").setValue(carros);
                }



            });

            return item;
        }
    }

}
