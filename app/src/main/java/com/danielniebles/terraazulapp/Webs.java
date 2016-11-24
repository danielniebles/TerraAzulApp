package com.danielniebles.terraazulapp;

/**
 * Created by Daniel on 18/11/2016.
 */

public class Webs {
    private String name;
    //private int thumbnail;
    private String thumbnail, descripcion;

    public Webs() {
    }

    public Webs(String name, String thumbnail, String descripcion) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
