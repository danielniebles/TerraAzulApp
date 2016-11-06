package com.danielniebles.terraazulapp;

import java.util.ArrayList;

/**
 * Created by Daniel on 31/10/2016.
 */

public class Usuario {
    private String usuario;
    private String contraseña;
    private String mail;
    private String sexo;
    private ArrayList<Carro> carros;
    private int id;

    public Usuario(String usuario, String contraseña, String mail, String sexo, ArrayList<Carro> carros, int id) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.mail = mail;
        this.sexo = sexo;
        this.carros = carros;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Carro> getCarros() {
        return carros;
    }

    public void setCarros(ArrayList<Carro> carros) {
        this.carros = carros;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


}
