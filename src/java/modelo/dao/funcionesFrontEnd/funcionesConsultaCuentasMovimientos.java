
package modelo.dao.funcionesFrontEnd;

import java.util.List;
import java.util.Scanner;
import model.cuenta;
import model.movimiento;
import modelo.dao.servicioDeposito;

/**
 *
 * @Autores: Gabriel Barboza && Néstor Leiva
 */
public class funcionesConsultaCuentasMovimientos {

    private servicioDeposito sd;//para listar los movimientos de una cuenta en específico
    private funcionesDeposito fd;//para listar las cuentas de un cliente en específico.

    /**
     *
     */
    public funcionesConsultaCuentasMovimientos() {
        sd = new servicioDeposito();
        fd = new funcionesDeposito();
    }

    /**
     * Lista todas las cuentas relacionadas a un numero de cedula de un cliente
     * @param cedula String numero de cedula
     * @return la lista de las cuentas
     * @throws Exception algun tipo de excepción relacionada con el txt de excepciones.
     */
    public List<cuenta> listarCuentasCliente(String cedula) throws Exception {
        return fd.listarCuentasCliente(cedula);
    }

    /**
     * Lista los movimientos de la cuenta a como esten insertados en la BD
     * sin ningun tipo de filtro.
     * @param num_cuenta int numero de  cuenta del cliente.
     * @return lista de movimientos.
     */
    public List<movimiento> listarMovimientosCuenta(int num_cuenta) {
        return sd.obtenerMovimientos(num_cuenta);
    }
    
    /**
     * Lista los movimientos de la cuenta a como esten insertados en la BD
     * sin ningun tipo de filtro.
     * @param tipoDatoBuscado
     * @return lista de movimientos.
     */
    public List<movimiento> listarMovimientosPorTipo(String tipoDatoBuscado) {
        return sd.obtenerMovimientosPorTipoBase(tipoDatoBuscado);
    }
    
    
        
    /**
     * Lista los movimientos de la cuenta por medio de rango de fechas
     * de la "yyyy-mm-dd" hasta la otra "yyyy-mm-dd" y 
     * las filtra en de mas reciente a mas antigua
     * @param num_cuenta int numero de cuenta del cliente
     * @param fech1 String fecha 1"yyyy-mm-dd"
     * @param fech2 String fecha 2"yyyy-mm-dd"
     * @return lista de movimientos.
     */
    public List<movimiento> listarMovimientosCuentaPorFecha(int num_cuenta,String fech1,String fech2)
    {
        return sd.obtenerMovimientosOrdenadosPorFecha(num_cuenta,fech1,fech2);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Ingrese el numero de cedula Cliente: ");
        String numCedula = input.next();
        System.out.println("Ingresó " + numCedula);
        List<cuenta> lis;
        try {
            lis = new funcionesConsultaCuentasMovimientos().listarCuentasCliente(numCedula);
            for (cuenta li : lis) {
                System.out.println(li.toString());
            }
            System.out.print("Numero Cuenta (Propia): ");
            int number2 = input.nextInt();
            System.out.println("Ingresó " + number2);

            List<movimiento> list;
            
            System.out.print("Fecha 1: ");
            String fech1 = input.next();
            System.out.println("Ingresó " + fech1);
            System.out.print("Fecha 2: ");
            String fech2 = input.next();
            System.out.println("Ingresó " + fech2);
            
            list=new funcionesConsultaCuentasMovimientos().listarMovimientosCuentaPorFecha(number2, fech1, fech2);
        //  Ordenar de mayor a menor
//            System.out.print("1 Ordenar Mas nuevas a viejas, 0 viejas a nuevas ");
//            int number3 = input.nextInt();
//            if(number3==1)//ordenamos
//                list=new funcionesConsultaCuentasMovimientos().listarMovimientosCuentaOrdenados(number2);
//            else
//                list = new funcionesConsultaCuentasMovimientos().listarMovimientosCuenta(number2);
               
            System.out.println("Sus movimientos son los siguientes !");
            System.out.println("TAM:"+list.size());
            for (movimiento object : list) {
                System.out.println(object.toString());
            }

        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }

    }

}
