package com.danielniebles.terraazulapp;

import java.util.ArrayList;

/**
 * Created by Daniel on 31/10/2016.
 */

public class Usuario {
    private String uid, nombre, correo, urlphoto;
    //private int numCarros;
    private ArrayList<Carro> carros;

    public Usuario(String uid, String nombre, String correo, String urlphoto, ArrayList<Carro> carros /*int numCarros*/) {
        this.uid = uid;
        this.nombre = nombre;
        this.correo = correo;
        this.urlphoto = urlphoto;
        this.carros = carros;
        //this.numCarros = numCarros;
    }

    /*public int getNumCarros() {
        return numCarros;
    }

    public void setNumCarros(int numCarros) {
        this.numCarros = numCarros;
    }*/

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUrlphoto() {
        return urlphoto;
    }

    public void setUrlphoto(String urlphoto) {
        this.urlphoto = urlphoto;
    }

    public ArrayList<Carro> getCarros() {
        return carros;
    }

    public void setCarros(ArrayList<Carro> carros) {
        this.carros = carros;
    }
}
