/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao.funcionesFrontEnd;

import java.util.Optional;
import model.usuario;
import modelo.dao.servicioCliente;
import modelo.dao.servicioUsuario;

/**
 * Clase para las funciones necesarias para el desarrollo de la funcionalidad de
 * las paginas que utlizaran los servlets y demas clases para realizar lo
 * requerido con todo lo relacionado con el logueo (R1).coo
 *
 * @author gabri
 */
public class funcionesLogueo {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

    }

    public funcionesLogueo() {
    }

    // Retorna 1 si es cajero y 0 si es cliente.
    /**
     * Metodo para conocer el rol de un cliente, Se da por hecho que el numero
     * de cedula existe en la BD.
     *
     * @param ced cedula del cliente para obtener el rol.
     * @return 1 ó 0, 1 para Cajero y 2 para Cliente
     */
    public int obtenerRolCliente(String ced) {
        return new servicioUsuario().obtenerUsuario(
                new servicioCliente().obtenerCliente(ced).get().getUsuario_id_usuario()).get().getRol();
    }

    // Obtiene el id de usuario con la cedula del cliente.
    /**
     * Encuentra el id del Usuario que esta ligado al id del cliente.
     *
     * @param ced cedula del cliente
     * @return el id del usuario ó lanza una Exepción.
     * @throws Exception 2: si no Existe ese Cliente en le Sistema.
     */
    public int obtenerIdUsuario(String ced) throws Exception {
        if (verificarExistenciaCedulaCliente(ced)) {
            return new servicioCliente().obtenerCliente(ced).get().getUsuario_id_usuario();
        } else {
            throw new Exception("2");//"No Existe ese Cliente en el sistema!");        
        }
    }

    // Retora true si la cedula esta en la base de datos.
    /**
     * Verifica en la BD que el numero de cedula del cliente exista.
     *
     * @param ced cedula del cliente
     * @return true si existe, false si no.
     * @throws Exception 2:No existe el numero de cedula
     */
    public boolean verificarExistenciaCedulaCliente(String ced) throws Exception {
        servicioCliente scl = new servicioCliente();
        if (scl.obtenerCliente(ced).isPresent()) {
            return true;
        } else {
            throw new Exception("2");//No existe el numero de cedula
        }
    }
    
    // Metodo que arroja un true si el cliente existe y un false sino
    public boolean verificarPosibleCedulaCliente(String ced) throws Exception {
        servicioCliente scl = new servicioCliente();
        if (scl.obtenerCliente(ced).isPresent()) {
            return true;
        } else {
            return false;
        }
    }
    
    

   
    
    
    // Retorna true si la cedula coincide con su contraseña.
    /**
     * Metodo que valida si la contraseña asignada al usuario son correctas.
     *
     * @param id_usu id del usuario
     * @param contrs contraseña del usuario
     * @return true si esta correcto, false si no
     * @throws Exception 1: No existe el usuario digitado.
     */
    public boolean verificarContraseñaCliente(int id_usu, String contrs) throws Exception {
        servicioUsuario su = new servicioUsuario();
        Optional<usuario> u = su.obtenerUsuario(id_usu);
        if (u.isPresent())// verifica la contraseña digitada
        {
            return u.get().getClave_acceso().equals(contrs);
        } else// no existe ese usuario
        {
            throw new Exception("1");//No existe el Usuario Digitado");
        }
    }

    
    // Metodo que realiza la funcion de actualizar la contraseña utilizando 
    // la clase servicio login
    public boolean actualizarContraseña(usuario usuario) throws Exception {
        
        boolean verdadero=false;
        try {
            servicioUsuario su = new servicioUsuario();

            if (su.actualizarClave(usuario)) {
                verdadero=true;
                return verdadero;
            } else {
                throw new Exception("14");//No existe el Usuario Digitado");
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public usuario obtenerUsuarioPorCedula(String ced) throws Exception {
        servicioUsuario su = new servicioUsuario();
        usuario u = su.obtenerUsuario(this.obtenerIdUsuario(ced)).get();
        return u;
    }
    
    
   

    /**
     * Metodo principal para dar acceso al cliente en el login
     *
     * @param cedula # cédula
     * @param contrase contraseña/password
     * @return true si hay acceso Exitoso , false si no.
     * @throws Exception 3:contraseña incorrecta o otra ver txt de exepciones
     * del proyecto.
     */
    public boolean verificarLogueo(String cedula, String contrase) throws Exception {
        int id_us;

        // Si el la cedula del usaurio existe continuamos
        if (verificarExistenciaCedulaCliente(cedula)) {
            try {
                id_us = obtenerIdUsuario(cedula);
                if (verificarContraseñaCliente(id_us, contrase)) {
                    return true;
                } else {
                    throw new Exception("3");//Contraseña erronea
                }
            } catch (Exception ex) {
//                System.out.println(ex.getMessage());
                throw new Exception(ex.getMessage());

            }
        }
        return false;
    }

}
