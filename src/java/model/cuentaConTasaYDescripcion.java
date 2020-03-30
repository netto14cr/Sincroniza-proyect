/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Clase cuenta que contiene los atributos de su tabla asociada de tipo_cuenta
 * que contiene los atributos como la descripción de ella que puede ser de ahorros o corriente
 * y los intereses esta hereda de cuenta.
 * @author gabri
 */
public class cuentaConTasaYDescripcion extends cuenta {

    private double tasa_interes;
    private String descripcion;

    @Override
    public String toString() {
        return super.toString()+"cuentaConTasaYDescripcion{" + "tasa_interes=" + tasa_interes + ", descripcion=" + descripcion + '}';
    }

    public cuentaConTasaYDescripcion(double tasa_interes, String descripcion) {
        this.tasa_interes = tasa_interes;
        this.descripcion = descripcion;
    }

    public cuentaConTasaYDescripcion(double tasa_interes, String descripcion, int num_cuenta, int tipo_cuenta_id_tipo_cuenta, String cliente_id_cliente, String moneda_nombre, String fecha_creación, double limite_transferencia_diaria, int activa, double saldo_actual, String fecha_ultima_aplicación) {
        super(num_cuenta, tipo_cuenta_id_tipo_cuenta, cliente_id_cliente, moneda_nombre, fecha_creación, limite_transferencia_diaria, activa, saldo_actual, fecha_ultima_aplicación);
        this.tasa_interes = tasa_interes;
        this.descripcion = descripcion;
    }

    public double getTasa_interes() {
        return tasa_interes;
    }

    public void setTasa_interes(double tasa_interes) {
        this.tasa_interes = tasa_interes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
