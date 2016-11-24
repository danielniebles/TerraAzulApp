package com.danielniebles.terraazulapp;

import com.firebase.client.Firebase;

/**
 * Created by Daniel on 31/10/2016.
 */

public class Citas {
    private String fecha;
    private String hora;
    private String UID;
    private double latitud;
    private double longitud;
    private String FIREBASE_URL="https://terraazul-cd8d8.firebaseio.com/";
    private Firebase firebasedata;

    public Citas(String fecha, String hora, double latitud, double longitud, String UID) {
        this.fecha = fecha;
        this.hora = hora;
        this.latitud = latitud;
        this.longitud = longitud;
        this.UID = UID;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
    public double getLatitud() {
        return latitud;
    }
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
    public double getLongitud() {
        return longitud;
    }
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    public String getUID() {
        return UID;
    }
    public void setUID(String usuario) {
        this.UID = usuario;
    }
}
