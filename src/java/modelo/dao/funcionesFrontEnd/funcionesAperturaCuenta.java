/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao.funcionesFrontEnd;

import model.PasswordGenerator;
import model.cliente;
import model.cuenta;
import model.usuario;
import modelo.dao.servicioCliente;
import modelo.dao.servicioCuenta;
import modelo.dao.servicioUsuario;

/**
 * Clase para las funciones necesarias para el desarrollo de la funcionalidad de las paginas
 * que utlizaran los servlets y demas clases para realizar lo requerido con
 * todo lo relacionado con la apertura de cuentas (R2).
 * @author gabri
 */
public class funcionesAperturaCuenta {

    /**
     *Genera un numero de cuenta nuevo fijandose en la BD,
     * cual fue el ultimo num_cuenta insertado para asi aumentarlo
     * en una unidad.
     * @return int numero de cuenta
     */
    public int generarNumeroCuentaNuevo() {
        int num = 0;
        servicioCuenta sc = new servicioCuenta();
        num = sc.obtenerUltimoNumCuenta() + 1;
        return num;
    }
    
    /**
     *Metodo que genera una contraseña de manera al azar
     * utilizando letras mayusculas,minusculas y numeros.
     * @return un String de 8 caracteres.
     */
    public String generarContraseña()
            {
                return PasswordGenerator.getPassword(
		PasswordGenerator.MINUSCULAS+
		PasswordGenerator.MAYUSCULAS+
		PasswordGenerator.ESPECIALES,8);
            }

    /**
     *
     */
    public funcionesAperturaCuenta() {
    }
    
    // Se parte del hecho que antes de llamar a este metodo
    // hay que verificar previamente que el numero de cedula
    // exista en la base de datos o no para así poder identificar
    // si se ocupan solicitar los datos o ya tenemos los necesarios.

    /**
     * Metodo que crea una una cuenta a un cliente existente,
     * se da por hecho que el cliente existe en la BD.
     * @param ced cedula del cliente.
     * @param moneda tipo de moneda (CRC,USD,EUR).
     * @param tipo 0,1 0 corriente de ahorros,1 cuenta corriente.
     * @return true si se creo , false si no.
     */
    public boolean crearCuentaClienteExistente(String ced,String moneda,int tipo)
    {
        servicioCuenta sc=new servicioCuenta();
        funcionesAperturaCuenta funciones=new funcionesAperturaCuenta();
        int num_cuenta=funciones.generarNumeroCuentaNuevo();
        
        cuenta c=new cuenta();
        c.setNum_cuenta(num_cuenta);
        c.setCliente_id_cliente(ced);
        c.setTipo_cuenta_id_tipo_cuenta(tipo);
        c.setMoneda_nombre(moneda);
        
        // como se logro insertar la cuenta procedemos a ligar
        // la misma cuenta a la cuenta de favoritas.
        if(sc.insertarCuenta(c))
            return sc.ligarCuentaPropiaAFavoritas(num_cuenta);
        else
            return false;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        
        funcionesAperturaCuenta f=new funcionesAperturaCuenta();
        
//        System.out.println(f.crearClienteNuevo(new cliente("999",
//                "packeado","mani","911",0),0));
//        System.out.println(f.crearCuentaClienteExistente("999", "CRC",0));
    }

    //Se necesitan el rol y todos los datos relacionados 
    //con el cliente para registrarlo en el sistema.

    /**
     * Crea un cliente nuevo y un usuario enlaza a ambos en la BD,
     * el id del usuario es el FK del cliente, es necesario el
     * id,nombre,apellido obligatorio,Solamente sin el id_usuario.
     * @param c objetod de tipo cliente.
     * @param rol int rol (Cajero,Cliente).
     * @return true si se creo , false si no.
     */
    public boolean crearClienteNuevo(cliente c,int rol)
    {
        funcionesLogueo flg=new funcionesLogueo();
        servicioUsuario se=new servicioUsuario();
        servicioCliente sc=new servicioCliente();
        usuario u=new usuario();
        u.setId_usuario(se.obtenerUltimoIdUsuario()+1);
        u.setRol(rol);
        u.setClave_acceso(this.generarContraseña());
        u.setClave_vencida(1);
        // ya creamos el usuario , lo insertamos 
        // asociarlo cliente para eso setiamos el fk del cliente.
        c.setUsuario_id_usuario(u.getId_usuario());
        if(se.insertarUsuario(u))// si se logro insertar bien el usuario
            return sc.insertarCliente(c);
        else return false;
        
    }
}
