/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @Autores: Gabriel Barboza && Néstor Leiva
 */
public class tipo_cuenta {
    private int id_tipo_cuenta;
    private String descripción;
    private double tasa_interés;

    @Override
    public String toString() {
        return "tipo_cuenta{" + "id_tipo_cuenta=" + id_tipo_cuenta + ", descripci\u00f3n=" + descripción + ", tasa_inter\u00e9s=" + tasa_interés + '}';
    }

    public tipo_cuenta() {
    }

    public tipo_cuenta(int id_tipo_cuenta, String descripción, double tasa_interés) {
        this.id_tipo_cuenta = id_tipo_cuenta;
        this.descripción = descripción;
        this.tasa_interés = tasa_interés;
    }

    public int getId_tipo_cuenta() {
        return id_tipo_cuenta;
    }

    public void setId_tipo_cuenta(int id_tipo_cuenta) {
        this.id_tipo_cuenta = id_tipo_cuenta;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public double getTasa_interés() {
        return tasa_interés;
    }

    public void setTasa_interés(double tasa_interés) {
        this.tasa_interés = tasa_interés;
    }
    
}
