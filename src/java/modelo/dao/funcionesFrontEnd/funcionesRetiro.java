/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao.funcionesFrontEnd;

import java.util.List;
import model.cuenta;
import model.movimiento;
import modelo.dao.servicioCuenta;

/**
 *
 * @Autores: Gabriel Barboza && Néstor Leiva
 */
public class funcionesRetiro {

    private final funcionesDeposito fd;
    private final servicioCuenta sc;

    /**
     *
     */
    public funcionesRetiro() {
        this.fd = new funcionesDeposito();
        this.sc = new servicioCuenta();
    }

    /**
     * Verificamos que el numero de cedula en el movimiento a realizar y el
     * numero de cedula solicitado sean el mismo que el del #cedula que aparece
     * en la cuenta ya que solamente el propietario de la cuenta puede realizar
     * los retiros, no hay personas autorizadas a otras cuentas.
     *
     * @param mov objeto movimiento
     * @param numCedula numero cedula
     * @return true si el #cedula del movimiento coincide con numero cedula del
     * cliente que se le solícito.
     */
    public boolean verificarClienteSiEsDueñoDeLaCuenta(movimiento mov, String numCedula) {
        return sc.obtenerCuenta(mov.getCuenta_num_cuenta()).get().getCliente_id_cliente().equals(numCedula);
    }

    /**
     * lista las cuentas del cliente que tenga asociadas a ese numero de cedula
     *
     * @param numCedula
     * @return lista cuentas , null , excepción.
     * @throws Exception
     */
    public List<cuenta> listarCuentasDelCliente(String numCedula) throws Exception {
        return fd.listarCuentasCliente(numCedula);
    }

    boolean verificarSiExistenFondosParaElRetiro(movimiento mov) {
        double saldoActual = sc.obtenerCuenta(mov.getCuenta_num_cuenta()).get().getSaldo_actual();
//        System.out.print("Saldo Ac"+saldoActual+" mov: "+mov.getMonto()+"\n");
        return saldoActual < mov.getMonto();
    }

    /**
     * Realiza la operacion principal del R4 , realiza todas las verificaciones
     * como que el cliente sea el dueño de la cuenta , que existan fondos para
     * poder realizar la transacción, despues realiza la operacion y actualiza
     * los valores correspondientes.
     *
     * @param mov movimiento(montos positivos)
     * @param cedula cedula del cliente
     * @return true si hay exito , false sino , o excepcion en caso de algun
     * imprevisto
     * @throws Exception tipo 9,10 u otro.
     */
    public boolean realizarRetiroDeCuenta(movimiento mov, String cedula) throws Exception {

        if (this.verificarClienteSiEsDueñoDeLaCuenta(mov, cedula)) {
            if (this.verificarSiExistenFondosParaElRetiro(mov) == false) {
//                if (this.verificarLimiteMonto(mov)) { // este no verifica si excede lo establecido.
                    mov.setMonto(-mov.getMonto());//pasamos el monto a negativo para que cuando se realice
                    //la actualización le reste a la cuenta en vez de sumarse.
                    mov.setDetalle("Retiro: " + mov.getDetalle());//se añade el retiro
                    return fd.realizarDepositoACuenta(mov);
//                }
            } else {
                throw new Exception("9");
            }
        } else {
            throw new Exception("10");
        }
    }
        public boolean realizarRetiroDeCuentaRemota(movimiento mov, String cedula) throws Exception {

        if (this.verificarClienteSiEsDueñoDeLaCuenta(mov, cedula)) {
            if (this.verificarSiExistenFondosParaElRetiro(mov) == false) {
                if (this.verificarLimiteMonto(mov)) { // este no verifica si excede lo establecido.
                    mov.setMonto(-mov.getMonto());//pasamos el monto a negativo para que cuando se realice
                    //la actualización le reste a la cuenta en vez de sumarse.
                    mov.setDetalle("Retiro" + mov.getDetalle());//se añade el retiro
                    return fd.realizarDepositoACuenta(mov);
                }
            } else {
                throw new Exception("9");
            }
        } else {
            throw new Exception("10");
        }
        return false;
    }
    /**
     * Metodo que verifica si el movimiento a realizar excede el monto
     * establecido previamente.
     *
     * @param mov movimiento(Objeto)
     * @return true si no hay problemas , false si el monto es mayor.
     * @throws Exception 5: el monto a transferir a la cuenta excede el límite.
     */
    public boolean verificarLimiteMonto(movimiento mov) throws Exception {
        servicioCuenta sc = new servicioCuenta();
        funcionesDeposito fd = new funcionesDeposito();
        if (sc.obtenerCuenta(mov.getCuenta_num_cuenta()).get().getLimite_transferencia_diaria()
                <= fd.obtenerSumaTotalTransferencias(mov.getCuenta_num_cuenta())+mov.getMonto()) {
            throw new Exception("5");// el monto a transferir a la cuenta excede el límite.
        } else {
            return true;
        }
    }
}
