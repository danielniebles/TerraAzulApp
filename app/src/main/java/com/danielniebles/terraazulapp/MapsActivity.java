package com.danielniebles.terraazulapp;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String FIREBASE_URL="https://terraazul-cd8d8.firebaseio.com/";
    private Firebase firebasedata;
    double Latitud, Longitud;
    String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        MapsInitializer.initialize(this);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        Firebase.setAndroidContext(getApplicationContext());
        firebasedata = new Firebase(FIREBASE_URL);

        firebasedata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMap.clear();
                Latitud = Double.valueOf(dataSnapshot.child("Operarios").child("0").child("Latitud").getValue().toString());
                Longitud = Double.valueOf(dataSnapshot.child("Operarios").child("0").child("Longitud").getValue().toString());
                nombre = dataSnapshot.child("Operarios").child("0").child("Nombre").getValue().toString();

                LatLng operario2 = new LatLng(Latitud, Longitud);
                mMap.addMarker(new MarkerOptions().position(operario2).anchor(0.5f, 0.5f).title("Operario").snippet(nombre).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_bike_black_48dp)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(operario2));

                LatLng ubicacionCita = new LatLng(6.269277, -75.565516);
                mMap.addMarker(new MarkerOptions().position(ubicacionCita).title("Cita").snippet("Mi carro - 12:30").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_car_black_48dp)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionCita,15));

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
