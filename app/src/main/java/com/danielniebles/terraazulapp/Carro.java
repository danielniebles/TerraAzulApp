package com.danielniebles.terraazulapp;

/**
 * Created by Daniel on 31/10/2016.
 */

public class Carro {

    private String nombre;
    private String marca;
    private String modelo;
    private String placa;
    private boolean mascotas;

    public Carro(String nombre, String marca, String modelo, String placa, boolean mascotas) {
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.mascotas = mascotas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public boolean isMascotas() {
        return mascotas;
    }

    public void setMascotas(boolean mascotas) {
        this.mascotas = mascotas;
    }
}
