/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao.funcionesFrontEnd;

import java.util.List;
import model.cuentaConTasaYDescripcion;
import model.movimiento;
import modelo.dao.servicioCuenta;

/**
 *
 * @author gabri
 */
public class funcionesAcreditacionIntereses {

    servicioCuenta sc;
    funcionesDeposito fd;

    /**
     * Constructor que inicializa las variables de servicio de cuenta y
     * funciones de deposito
     */
    public funcionesAcreditacionIntereses() {
        sc = new servicioCuenta();// para listar las cuentas
        fd = new funcionesDeposito();// para agregar los intereses a las cuentas.
    }

    /**
     * (***SOLUCIONAR***)--> ver cual criterio se puede usar para ver cuales
     * cuentas se ponen como inactivas para asi realizar este procedimiento de
     * una manera mas precisa, Realiza la función de acreditar todas las cuentas
     * del sistema con sus intereses correspondientes y se le agrega el detalle
     * de acreditación en el movimiento y se registran los movimientos , se
     * actualizan los saldos y se actualiza los saldos de las cuentas con el
     * salario mas los intereses aplicados,y y tambien se ponen como aplicados
     * los movimientos en los que se actualizaron los saldos de las cuentas.
     *
     * @return true si se generaron todos sin problema , false de lo contrario
     * cabe destacar que no es raro que no se apliquen intereses dado que aún no
     * se validan si las cuentas con salario 0 pasan a ser inactivas con esa
     * actulización este metodo solamente retornaria true si se hacen a los que
     * pueden correctamente y false si hay alguna en la que se pudo realizar
     * pero hubo algun error .
     */
    public boolean generarAcreditacionDeIntereses() {
        movimiento mov;
        boolean error = true;
        List<cuentaConTasaYDescripcion> lis = sc.listarCuentasConTasayDescripcion();
        for (cuentaConTasaYDescripcion li : lis) {
            mov = new movimiento();
            mov.setDepositante("Banco-Cajero");
            mov.setDetalle("Acreditación de Intereses");
            mov.setCuenta_num_cuenta(li.getNum_cuenta());
            mov.setMonto((li.getSaldo_actual() * li.getTasa_interes()) / 100);
            try {
                // insertamos el movimiento y actualizamos el saldo de la cuenta
                fd.realizarDepositoACuentaRefact(mov);
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
                error = false;
            }

        }
        return error;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        funcionesAcreditacionIntereses fa = new funcionesAcreditacionIntereses();

        System.out.println(fa.generarAcreditacionDeIntereses());
//         la cuenta del cajero no tiene dinero por eso esta esta parte de abajo
//          para que no genere una excepción.
//        movimiento mov=new movimiento();
//            mov.setDepositante("Banco-Cajero");
//            mov.setDetalle("prueba para los intereses");
//            mov.setCuenta_num_cuenta(4);
//            mov.setMonto(5000);
//        try {
//            new funcionesDeposito().realizarDepositoACuenta(mov);
//        } catch (Exception ex) {
//            System.out.println("Error : "+ex.getMessage());
//        }
//    }
    }

}
