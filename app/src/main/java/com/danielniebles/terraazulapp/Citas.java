package com.danielniebles.terraazulapp;

/**
 * Created by Daniel on 31/10/2016.
 */

public class Citas {
    private String fecha;
    private int usuario;

    public String getFecha() {
        return fecha;
    }

    public Citas(String fecha, int usuario) {
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
}
