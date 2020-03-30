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
public class transferencia {
    private int id_transferencia;
    private String cuenta_origen;
    private String cuenta_destino;
    private String monto;
    private String fecha;

    @Override
    public String toString() {
        return "transferencia{" + "id_transferencia=" + id_transferencia + ", cuenta_origen=" + cuenta_origen + ", cuenta_destino=" + cuenta_destino + ", monto=" + monto + ", fecha=" + fecha + '}';
    }

    public int getId_transferencia() {
        return id_transferencia;
    }

    public void setId_transferencia(int id_transferencia) {
        this.id_transferencia = id_transferencia;
    }

    public String getCuenta_origen() {
        return cuenta_origen;
    }

    public void setCuenta_origen(String cuenta_origen) {
        this.cuenta_origen = cuenta_origen;
    }

    public String getCuenta_destino() {
        return cuenta_destino;
    }

    public void setCuenta_destino(String cuenta_destino) {
        this.cuenta_destino = cuenta_destino;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public transferencia() {
    }

    public transferencia(int id_transferencia, String cuenta_origen, String cuenta_destino, String monto, String fecha) {
        this.id_transferencia = id_transferencia;
        this.cuenta_origen = cuenta_origen;
        this.cuenta_destino = cuenta_destino;
        this.monto = monto;
        this.fecha = fecha;
    }
}
