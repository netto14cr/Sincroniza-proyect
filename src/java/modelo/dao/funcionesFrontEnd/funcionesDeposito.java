/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao.funcionesFrontEnd;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import model.cuenta;
import model.movimiento;
import modelo.dao.servicioCuenta;
import modelo.dao.servicioDeposito;

/**
 * Clase para las funciones necesarias para el desarrollo de la funcionalidad de las paginas
 * que utlizaran los servlets y demas clases para realizar lo requerido con
 * todo lo relacionado con los depositos(R3).
 * @Autores: Gabriel Barboza && Néstor Leiva
 */
public class funcionesDeposito {
    
    
    // Realiza el deposito a una cuenta con la ayuda del objeto
    // movimiento ya que este contiene los datos necesarios
    // para realizarlo, solamente se ocupa el
    // * numero de cuenta
    // * monto , los demas atributos si no se especifican en el
    // objeto movimiento entonces se llenan por defecto.

    /**
     * Funcion que realiza el deposito de la cuenta y ademas 
     * valida que el monto sea mayor a 0.
     * @param mov objeto movimiento
     * @return true si salio bien , false si no , o excepcion
     * @throws Exception de tipo 6,8 u otra.
     */
    public boolean realizarDepositoACuentaRefact(movimiento mov) throws Exception
    {
        mov.setDetalle(""+mov.getDetalle());//añadimos la palabra deposito...
        if(mov.getMonto()>0)
            return this.realizarDepositoACuenta(mov);
        else
            throw new Exception("8");
    }
    
    
    
    /**
     * Metodo para realizar el deposito a una cuenta (R3)
     * este metodo llama a muchos otros para que ocurra esto.
     * @param mov movimiento se ocupa el numCuenta,monto,detalle,
     * @return true si lo realizo , false o exepcion.
     * @throws Exception 6: No se pudo realizar el movimiento.
     */
    
    protected boolean realizarDepositoACuenta(movimiento mov) throws Exception {
        servicioDeposito sd = new servicioDeposito();
        // si ambos son true entonces procedemos a insertar el movimiento
        // 1.sabemos que el monto esta bien
        // 2.la cuenta esta bien
        if (this.verificarExistenciaNumCuenta(mov.getCuenta_num_cuenta())
              /* solo en el retiro se verifica el monto maimo
                para las transacciones ! OJAS && this.verificarLimiteMonto(mov)*/) {
            if (sd.insertarMovimiento(mov)) {// como se inserto bien actualizamos el monto en la cuenta
                if (sd.actualizarMontoDeMovimientoEnLaCuenta(mov)) {
                    // finalizamos el proceso estableciendo como aplicados el movimiento realizado
                    // ya con el saldo de la cuenta actualizado.
                    return sd.establecerComoAplicadosLosMovimientosRealizados(mov);
                }
            } else {
                throw new Exception("6");// no se pudo realizar el movimiento.
            }
        }
        
        return false;
    }

    // Este metodo verifica que el numero de cuenta exista
    // en la BD , es para que no haya error cuando el cliente
    // le indica cual de las cuentas es la que quiere realizar
    // el deposito correspondiente , de lo contrario lanza una excepción.

    /**
     * Revisa que le numero de cuenta exista en la BD tabla->cuenta.
     * @param numCuenta # cuenta
     * @return true si existe , false si no.
     * @throws Exception 4: no existe el numero de cuenta digitado.
     */
    public boolean verificarExistenciaNumCuenta(int numCuenta) throws Exception {
        servicioCuenta sc = new servicioCuenta();

        if (sc.obtenerCuenta(numCuenta).isPresent()) {
            return true;
        } else {
            throw new Exception("4");//no existe el numero de cuenta digitado
        }
    }

    
    
    public double obtenerSumaTotalTransferencias(int num_cuenta)
    {
        servicioCuenta sc=new servicioCuenta();
        servicioDeposito sd=new servicioDeposito();
        Optional<cuenta> c=sc.obtenerCuenta(num_cuenta);
        double totalTransferido=0;
        // Cuenta obtenida , vemos los movimientos realizados hasta el momento por FECHA
        // Fecha Actual
        //Instanciamos el objeto Calendar
        //en fecha obtenemos la fecha y hora del sistema
        Calendar fecha = new GregorianCalendar();
        //Obtenemos el valor del año, mes, día,
        //hora, minuto y segundo del sistema
        //usando el método get y el parámetro correspondiente
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
//        int hora = fecha.get(Calendar.HOUR_OF_DAY);
//        int minuto = fecha.get(Calendar.MINUTE);
//        int segundo = fecha.get(Calendar.SECOND);
        System.out.println("Fecha Actual: "
                           + dia + "/" + (mes+1) + "/" + año);
//        System.out.printf("Hora Actual: %02d:%02d:%02d %n",
//                                              hora, minuto, segundo);
        // ahora obtenemos el listado de los movimientos en ese rango
        String fecha1=año + "-" + (mes+1) + "-" + (dia);
        String fecha2=año + "-" + (mes+1) + "-" + (dia+1);// no incluye este dia.
        List<movimiento> lis=sd.obtenerMovimientosOrdenadosPorFecha(num_cuenta,
                fecha1, fecha2);
        int cont=0;
        for (movimiento li : lis) {
            System.out.println(li.toString());
            if(li.getMonto()<0)//entonces fue un retiro, procedemos a sumar los retiros.
            {
                System.out.println("monto : "+li.getMonto());
                totalTransferido+=Math.abs(li.getMonto());
                cont+=1;
            }
        }
        System.out.println("Fecha2: "+fecha2+" cont : "+cont);
        System.out.println("El monto total transferido en la fecha :"+fecha1+" es = "+totalTransferido);
        
        return totalTransferido;
    }
    
    /**
     * Lista todas las cuentas que tiene el cliente
     * @param numCedula # cedula 
     * @return Lista-cuentas. o excepcion o null.
     * @throws Exception 6: no se pudo obtener el listado.
     */
    public List<cuenta> listarCuentasCliente(String numCedula) throws Exception
    {
        servicioDeposito sd=new servicioDeposito();
        List<cuenta> lis=sd.obtenerCuentasCliente(numCedula);
       
        if(lis.isEmpty()|| lis==null)
        {
            throw new Exception("6");//No se pudo Obtener el Listado , Error desconocido
        }else
            return lis;
    }
    
    
    
    
    /**
     * Muestra todos los movimientos de una cuenta en especifico.
     * @param numCuenta # de cuenta
     * @return Lista-movimientos. o excepcion 
     * o null-vacia en caso de que no tenga ninguna cuenta asociada.
     * @throws Exception 7: no se pudieron listar los movimientos.
     */
    protected List<movimiento> listarMovimientosCuenta(int numCuenta) throws Exception
    {
        servicioDeposito sd=new servicioDeposito();
        List<movimiento> lis=sd.obtenerMovimientos(numCuenta);
        if(lis.isEmpty() || lis==null)
        {
            throw new Exception("7");// No se puede listar los Movimientos , Error desconocido
        }else
            return lis;
    }
    
    /**
     *
     */
    public funcionesDeposito() {
    }
    
}
