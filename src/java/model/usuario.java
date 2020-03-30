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
public class usuario {
    
    private int id_usuario;
    private String clave_acceso;
    private int clave_vencida;
    private int rol;

    @Override
    public String toString() {
        return "usuario{" + "id_usuario=" + id_usuario + ", clave_acceso=" + clave_acceso + ", clave_vencida=" + clave_vencida + ", rol=" + rol + '}';
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getClave_acceso() {
        return clave_acceso;
    }

    public void setClave_acceso(String clave_acceso) {
        this.clave_acceso = clave_acceso;
    }

    public int getClave_vencida() {
        return clave_vencida;
    }

    public void setClave_vencida(int clave_vencida) {
        this.clave_vencida = clave_vencida;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public usuario(int id_usuario, String clave_acceso, int clave_vencida, int rol) {
        this.id_usuario = id_usuario;
        this.clave_acceso = clave_acceso;
        this.clave_vencida = clave_vencida;
        this.rol = rol;
    }

    public usuario() {
    }

}
