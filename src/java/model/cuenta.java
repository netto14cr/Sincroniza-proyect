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
public class cuenta {
    private int num_cuenta;
    private int tipo_cuenta_id_tipo_cuenta;
    private String cliente_id_cliente;
    private String moneda_nombre;
    private String fecha_creación;
    private double limite_transferencia_diaria;
    private int activa;
    private double saldo_actual;
    private String fecha_ultima_aplicación;

    @Override
    public String toString() {
        return "cuenta{" + "num_cuenta=" + num_cuenta + ", tipo_cuenta_id_tipo_cuenta=" + tipo_cuenta_id_tipo_cuenta + ", cliente_id_cliente=" + cliente_id_cliente + ", moneda_nombre=" + moneda_nombre + ", fecha_creaci\u00f3n=" + fecha_creación + ", limite_transferencia_diaria=" + limite_transferencia_diaria + ", activa=" + activa + ", saldo_actual=" + saldo_actual + ", fecha_ultima_aplicaci\u00f3n=" + fecha_ultima_aplicación + '}';
    }

    /**
     *
     * @return
     */
    public int getNum_cuenta() {
        return num_cuenta;
    }

    /**
     *
     * @param num_cuenta
     */
    public void setNum_cuenta(int num_cuenta) {
        this.num_cuenta = num_cuenta;
    }

    /**
     *
     * @return
     */
    public int getTipo_cuenta_id_tipo_cuenta() {
        return tipo_cuenta_id_tipo_cuenta;
    }

    /**
     *
     * @param tipo_cuenta_id_tipo_cuenta
     */
    public void setTipo_cuenta_id_tipo_cuenta(int tipo_cuenta_id_tipo_cuenta) {
        this.tipo_cuenta_id_tipo_cuenta = tipo_cuenta_id_tipo_cuenta;
    }

    /**
     *
     * @return
     */
    public String getCliente_id_cliente() {
        return cliente_id_cliente;
    }

    /**
     *
     * @param cliente_id_cliente
     */
    public void setCliente_id_cliente(String cliente_id_cliente) {
        this.cliente_id_cliente = cliente_id_cliente;
    }

    /**
     *
     * @return
     */
    public String getMoneda_nombre() {
        return moneda_nombre;
    }

    /**
     *
     * @param moneda_nombre
     */
    public void setMoneda_nombre(String moneda_nombre) {
        this.moneda_nombre = moneda_nombre;
    }

    /**
     *
     * @return
     */
    public String getFecha_creación() {
        return fecha_creación;
    }

    /**
     *
     * @param fecha_creación
     */
    public void setFecha_creación(String fecha_creación) {
        this.fecha_creación = fecha_creación;
    }

    /**
     *
     * @return
     */
    public double getLimite_transferencia_diaria() {
        return limite_transferencia_diaria;
    }

    /**
     *
     * @param limite_transferencia_diaria
     */
    public void setLimite_transferencia_diaria(double limite_transferencia_diaria) {
        this.limite_transferencia_diaria = limite_transferencia_diaria;
    }

    /**
     *
     * @return
     */
    public int getActiva() {
        return activa;
    }

    /**
     *
     * @param activa
     */
    public void setActiva(int activa) {
        this.activa = activa;
    }

    /**
     *
     * @return
     */
    public double getSaldo_actual() {
        return saldo_actual;
    }

    /**
     *
     * @param saldo_actual
     */
    public void setSaldo_actual(double saldo_actual) {
        this.saldo_actual = saldo_actual;
    }

    /**
     *
     * @return
     */
    public String getFecha_ultima_aplicación() {
        return fecha_ultima_aplicación;
    }

    /**
     *
     * @param fecha_ultima_aplicación
     */
    public void setFecha_ultima_aplicación(String fecha_ultima_aplicación) {
        this.fecha_ultima_aplicación = fecha_ultima_aplicación;
    }

    /**
     *
     */
    public cuenta() {
    }

    /**
     *
     * @param num_cuenta
     * @param tipo_cuenta_id_tipo_cuenta
     * @param cliente_id_cliente
     * @param moneda_nombre
     * @param fecha_creación
     * @param limite_transferencia_diaria
     * @param activa
     * @param saldo_actual
     * @param fecha_ultima_aplicación
     */
    public cuenta(int num_cuenta, int tipo_cuenta_id_tipo_cuenta, String cliente_id_cliente, String moneda_nombre, String fecha_creación, double limite_transferencia_diaria, int activa, double saldo_actual, String fecha_ultima_aplicación) {
        this.num_cuenta = num_cuenta;
        this.tipo_cuenta_id_tipo_cuenta = tipo_cuenta_id_tipo_cuenta;
        this.cliente_id_cliente = cliente_id_cliente;
        this.moneda_nombre = moneda_nombre;
        this.fecha_creación = fecha_creación;
        this.limite_transferencia_diaria = limite_transferencia_diaria;
        this.activa = activa;
        this.saldo_actual = saldo_actual;
        this.fecha_ultima_aplicación = fecha_ultima_aplicación;
    }
}
