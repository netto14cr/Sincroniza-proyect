/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author gabri
 */
public class moneda {
    private String nombre;
    private String descripción;
    private String simbolo;
    private double tipo_cambio_compra;
    private double tipo_cambio_venta;

    @Override
    public String toString() {
        return "moneda{" + "nombre=" + nombre + ", descripci\u00f3n=" + descripción + ", simbolo=" + simbolo + ", tipo_cambio_compra=" + tipo_cambio_compra + ", tipo_cambio_venta=" + tipo_cambio_venta + '}';
    }

    public moneda(String nombre, String descripción, String simbolo, double tipo_cambio_compra, double tipo_cambio_venta) {
        this.nombre = nombre;
        this.descripción = descripción;
        this.simbolo = simbolo;
        this.tipo_cambio_compra = tipo_cambio_compra;
        this.tipo_cambio_venta = tipo_cambio_venta;
    }

    public moneda() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public double getTipo_cambio_compra() {
        return tipo_cambio_compra;
    }

    public void setTipo_cambio_compra(double tipo_cambio_compra) {
        this.tipo_cambio_compra = tipo_cambio_compra;
    }

    public double getTipo_cambio_venta() {
        return tipo_cambio_venta;
    }

    public void setTipo_cambio_venta(double tipo_cambio_venta) {
        this.tipo_cambio_venta = tipo_cambio_venta;
    }
}
