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
public class cliente {
    private String id_cliente;
    private String apellidos;
    private String nombre;
    private String telefono;
    private int usuario_id_usuario;

    @Override
    public String toString() {
        return "cliente{" + "id_cliente=" + id_cliente + ", apellidos=" + apellidos + ", nombre=" + nombre + ", telefono=" + telefono + ", usuario_id_usuario=" + usuario_id_usuario + '}';
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getUsuario_id_usuario() {
        return usuario_id_usuario;
    }

    public void setUsuario_id_usuario(int usuario_id_usuario) {
        this.usuario_id_usuario = usuario_id_usuario;
    }

    public cliente() {
    }

    public cliente(String id_cliente, String apellidos, String nombre, String telefono, int usuario_id_usuario) {
        this.id_cliente = id_cliente;
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.telefono = telefono;
        this.usuario_id_usuario = usuario_id_usuario;
    }
    
}
