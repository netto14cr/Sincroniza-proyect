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
public class favorita {
   private String cliente_id_cliente;
   private int cuenta_num_cuenta;

    @Override
    public String toString() {
        return "favorita{" + "cliente_id_cliente=" + cliente_id_cliente + ", cuenta_num_cuenta=" + cuenta_num_cuenta + '}';
    }

    public favorita() {
    }

    public favorita(String cliente_id_cliente, int cuenta_num_cuenta) {
        this.cliente_id_cliente = cliente_id_cliente;
        this.cuenta_num_cuenta = cuenta_num_cuenta;
    }

    public String getCliente_id_cliente() {
        return cliente_id_cliente;
    }

    public void setCliente_id_cliente(String cliente_id_cliente) {
        this.cliente_id_cliente = cliente_id_cliente;
    }

    public int getCuenta_num_cuenta() {
        return cuenta_num_cuenta;
    }

    public void setCuenta_num_cuenta(int cuenta_num_cuenta) {
        this.cuenta_num_cuenta = cuenta_num_cuenta;
    }
   
}
