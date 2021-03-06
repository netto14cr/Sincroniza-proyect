/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao.funcionesFrontEnd;

import java.util.List;
import java.util.Scanner;
import model.cuenta;
import model.favorita;
import model.movimiento;
import modelo.dao.servicioCuenta;
import modelo.dao.servicioMoneda;

/**
 *
 * @Autores: Gabriel Barboza && Néstor Leiva
 */
public class funcionesTrasfereciasCajas extends funcionesDeposito {

    /**
     *
     */
    public funcionesTrasfereciasCajas() {
        this.fd = new funcionesDeposito();
    }

    /**
     * Verifica que el cliente tenga este numero de cuenta asociado 
     * para poder realizar transacciones , de lo contrario un cliente
     * no puede realizar transacciones a cuentas en las que no tiene como
     * favoritas.
     * @param cedula string numero de cuenta del cliente.
     * @param numCuentaDestino int numero de cuenta a asociar
     * @return true si es una favorita de el ,false de lo contrario.
     */
    public boolean verificarCuentaExistenteEnFavoritas(String cedula,int numCuentaDestino)
    {
        List<favorita> lis=new servicioCuenta().listarNumCuentasfavoritas(cedula);
        boolean ver=false;
        for (favorita li : lis) {
            if(li.getCuenta_num_cuenta()==numCuentaDestino)
            {
                ver=true;
                break;
            }
        }
        return ver;
    }
   private final funcionesDeposito fd;
    /**
     * Este metodo realiza la transferencia es importante recalcar que para el
     * parametro de movimiento solamente se necesitan que lleve los atributos de
     * : monto,detalle,# de la cuenta ORIGEN!, de lo contrario hay resultados
     * imprevistos.
     *
     * @param numCuentaOrigen Numero de la cuenta origen
     * @param numCuentaDestino Numero de la cuenta destino
     * @param mov objeto movimiento con el monto,detalle,#Cuenta-ORIGEN!!!!
     * @param cedula cedula del dueño de la cuenta ORIGEN!!!
     * @return true si se realizo todo bien o false de lo contrario
     * @throws Exception de tipo 4,9 u otra que estan en el rando del
     * Txt.Info-Excepciones.
     */
    
    public boolean realizarTrasnferencia(int numCuentaOrigen, int numCuentaDestino,
            movimiento mov, String cedula) throws Exception {
        if (this.verificarExistenciaNumCuenta(numCuentaOrigen)
                && this.verificarExistenciaNumCuenta(numCuentaDestino)
                ) {// como ambas existen verificamos si tienen el mismo tipo , pero antes
            // recuperamos ambas cuentas.
            
            servicioCuenta sc = new servicioCuenta();
            String tipo_moneda_cO = sc.obtenerCuenta(numCuentaOrigen).get().getMoneda_nombre();
            String tipo_moneda_cD = sc.obtenerCuenta(numCuentaDestino).get().getMoneda_nombre();

            servicioMoneda sm = new servicioMoneda();
            double moneOrige = sm.obtenerMoneda(tipo_moneda_cO).get().getTipo_cambio_venta();
            // por que uno se los va a dar a una cuenta (se los vende)
            double moneDesti = sm.obtenerMoneda(tipo_moneda_cD).get().getTipo_cambio_compra();
            // por que la cuenta destino es la que recibe el dinero.
            // EUR - Colones
            // USD - Colones
            // Colones - USD/EUR
            // el que interesa es el tipo destino.
            double montoADepositar = mov.getMonto();

            // la base va a hacer el de la cuenta origen
            // x de monto para xtipo de cuentaDestino.
            montoADepositar = montoADepositar * moneOrige;
            montoADepositar /= moneDesti;
            System.out.print("Monto seria : " + String.valueOf(montoADepositar));

            if (new funcionesRetiro().realizarRetiroDeCuenta(mov, cedula)) {
                // si se realizo con éxito el retiro de la cuenta entonces procedemos a 
                //Realizar el deposito en la cuenta destino
                mov.setCuenta_num_cuenta(numCuentaDestino);
                // se setea el numero al que se realiza el deposito
                mov.setMonto(montoADepositar);
                // se setea el monto a transferir.
                return new funcionesDeposito().realizarDepositoACuentaRefact(mov);
            } else {
                throw new Exception("9");
            }

        } else {
            throw new Exception("4");
        }
    }
    
    /**
     * Lista todas las cuentas favoritas de ese cliente.
     * @param cedula String cedula del cliente.
     * @return lista de objetos favorita
     */
    public List<favorita> listarCuentasFavoritasDeCliente(String cedula)
    {
        return new servicioCuenta().listarNumCuentasfavoritas(cedula);
    }
    
    /**
     * Verifica que el numero de cuenta pertenezca al cliente
     * con el numero de cedula brindado.
     * @param numCuenta int numero de cuenta
     * @param numCedula string numero de cedula del cliente.
     * @return true si es el dueño , false de lo contrario.
     */
    public boolean verificarClienteSiEsDueñoDeLaCuenta(int numCuenta, String numCedula) {
        return new servicioCuenta().obtenerCuenta(numCuenta).get().getCliente_id_cliente().equals(numCedula);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Ingrese el numero de cedula Cliente: ");
            String number = input.next();
            System.out.println("Ingresó " + number);
            List<cuenta> lis= 
      new funcionesTrasfereciasCajas().listarCuentasCliente(number);
            for (cuenta li : lis) {
                System.out.println(li.toString());
            }
            
            System.out.print("Numero Cuenta Origen(Propia): ");
            int number2 = input.nextInt();
            System.out.println("Ingresó " + number2);
             System.out.print("Ingrese la Cantidad de Dinero: ");
            double number4 = input.nextDouble();
            System.out.println("Ingresó " + number4);
            movimiento mov = new movimiento();
            mov.setMonto(number4);
            mov.setDetalle("Ayuda Escolar");
            mov.setCuenta_num_cuenta(number2);
            // Mostramos las cuentas que tiene como favoritas *****
            List<favorita> listaf=new funcionesTrasfereciasCajas().listarCuentasFavoritasDeCliente(number);
            for (favorita object : listaf) {
                System.out.println(object.toString());
            }
            // Fin de impresión.
            
            System.out.print("Numero Cuenta Destino: ");
            int number3 = input.nextInt();
            System.out.println("Ingresó " + number3);
            // el numero de cuenta y detalle son de la persona que realiza el movimiento
            new funcionesTrasfereciasCajas().realizarTrasnferencia(number2, number3, mov, String.valueOf(number));
        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
        }

//        try {
//            List<movimiento> lis= new funcionesTrasfereciasCajas().listarMovimientosCuenta(3);
//            for (movimiento li : lis) {
//                System.out.println(li.toString());
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(funcionesTrasfereciasCajas.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            
    }
}
