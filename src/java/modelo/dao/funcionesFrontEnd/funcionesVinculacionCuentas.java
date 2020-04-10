/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao.funcionesFrontEnd;

import java.util.Scanner;
import model.cliente;
import model.cuenta;
import model.favorita;
import modelo.dao.servicioCliente;
import modelo.dao.servicioCuenta;

/**
 *
 * @author gabri
 */
public class funcionesVinculacionCuentas {

    private final servicioCuenta sc;
    private final servicioCliente scli;

    /**
     * Retorna el cliente al que esta asociada el numero de cuenta que recibe
     * como parametro.
     *
     * @param num_cuenta int numero de la cuenta
     * @return objeto cliente
     */
    public cliente vincularCuentaACliente(int num_cuenta) throws Exception {
        //1° ocupamos obtener la cuenta que esta solicitando el usuario.
        //2° ocupamos el cliente que esta asociado a esa supuesta cuenta.
        //3° retornamos el cliente en caso de existir.
        new funcionesDeposito().verificarExistenciaNumCuenta(num_cuenta);
        cuenta c = sc.obtenerCuenta(num_cuenta).get();
        cliente cli = scli.obtenerCliente(c.getCliente_id_cliente()).get();
        return cli;
    }

    /**
     * Realiza la insercion del objeto favorita en la BD con sus atributos
     * OBVIAMENTE , tiene que llevar la cedula del cliente y la cedula a
     * vincular.
     *
     * @param fav objeto favorita
     * @return true si lo logro , false de lo contrario.
     */
    public boolean realizarVinculacion(favorita fav) {
        return sc.insertarCuentaAFavoritas(fav);
    }

    /**
     * Constructor inicializa los dos servicios para la realización de los
     * metodos necesarios para completar este requerimiento.
     */
    public funcionesVinculacionCuentas() {
        sc = new servicioCuenta();
        scli = new servicioCliente();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        // hacemos una simulación de como seria la funcionalidad de este requerimiento.
        String cedulaUsuario = "117230117";
        Scanner input = new Scanner(System.in);
        System.out.print("Ingrese el numero de cuenta a asociar: ");
        int number = input.nextInt();
        System.out.println("Ingresó " + number);
        // ahora mostramos los datos de esa cuenta.
        funcionesVinculacionCuentas fvc = new funcionesVinculacionCuentas();

        cliente c;
        try {
            c = fvc.vincularCuentaACliente(number);
            System.out.println(c.toString());
            System.out.print("1 vincular , 0 cancelar");
            int decision = input.nextInt();
            System.out.println("Ingresó " + decision);
            if (decision == 1 && c != null)//vinculamos
            {
                fvc.realizarVinculacion(new favorita(cedulaUsuario, number));
            } else {
                System.out.println("Operación Cancelada!");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

}
