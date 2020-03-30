/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import datos.BaseDatos;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import model.cuenta;
import model.movimiento;

/**
 *
 * @author gabri
 */
public class servicioDeposito {

    /**
     *
     */
    public servicioDeposito() {
    }

    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IOException
     * @throws SQLException
     */
    public Connection obtenerConexion() throws
            ClassNotFoundException,
            IllegalAccessException,
            InstantiationException,
            IOException,
            SQLException {
        BaseDatos bd = BaseDatos.obtenerInstancia();
        Properties cfg = bd.obtenerConfiguracion();
        Connection cnx = bd.obtenerConexion(
                cfg.getProperty("database"),
                cfg.getProperty("user"),
                cfg.getProperty("password")
        );
        return cnx;
    }

    // Consulta los movimientos de una cuenta en particular
    // para poder verificar los montos a transferir y para
    // ver los movimientos de la misma
    private final String CMD_RECUPERAR
            = "select * from movimiento where cuenta_num_cuenta=(?);";

    private final String CMD_RECUPERAR_Ordenado
            ="select * from movimiento where cuenta_num_cuenta=(?) order by fecha desc;";
    // para consultar las cuentas asociadas a una cedula
    private final String CMD_LISTAR_CEDULA
            = "select * from cuenta where cliente_id_cliente=?;";
    private final String CMD_RECUPERAR_OrdenadoPorFecha
            ="SELECT * FROM movimiento " +
        "WHERE cuenta_num_cuenta=(?) and (fecha BETWEEN (?) AND (?)) ;";
            
    // creo que este funciona nada mas pasarle null en
    // depositante en caso de que sea el dueño de la cuenta.
    private final String CMD_INSERTAR_MOVIMIENTO
            = "insert into movimiento ("
            + "cuenta_num_cuenta,monto,detalle,depositante) values"
            + " (?,?,?,?);";
//    private final String CMD_INSERTAR_CUENTA_PROPIA
//            = "insert into movimiento ("
//            + "cuenta_num_cuenta,monto,detalle) values"
//            + " (?,?,?);";

    // El metodo solamente usa el numero de cuenta pero dos veces...
    // aqui se realiza la actualización del saldo de la cuenta
    private final String CMD_ACTUALIZAR_MONTO
            = "update cuenta set saldo_actual=(saldo_actual+(select sum(monto) from movimiento where cuenta_num_cuenta=? and aplicado=0)) where num_cuenta=?;";
    // Se marca como aplicado el movimiento realizado.
    private final String CMD_APLICAR_TRASPASOS
            = "update movimiento set aplicado=1 where cuenta_num_cuenta=?;";

    // Los movimientos que se insertaron y actualizaron el monto en la tabla de 
    // cuentas , en la tabla de movimientos se deben marcar como aplicados.

    /**
     * Despues de Actualizarse el monto en la cuenta indicada se marca el movimiento
     * como aplicado(1) para asi cuando se vayan a actualizar los montos en las cuentas 
     * respectivas no se actualizen montos que ya han sido aplicados, es decir que solamente
     * los montos como aplicados=0 se actualicen.
     * @param mov objeto movimiento
     * @return true si se hizo , false si no.
     */
    public boolean establecerComoAplicadosLosMovimientosRealizados(movimiento mov) {
        boolean r = true;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_APLICAR_TRASPASOS);) {
            stm.clearParameters();
            stm.setString(1, String.valueOf(mov.getCuenta_num_cuenta()));
            stm.execute();

        } catch (IOException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            r = false;
        }
        return r;

    }

    // Con el mismo movimiento que se inserta en la BD si es correcto
    // se pasa el mismo objeto para que con el se obtenga cual es
    // el numero de cuenta seleccionado para traspasar el monto
    // de la tabla de movimiento al de la cuenta

    /**
     * Despues de insertar un movimiento , este actualiza el monto insertado
     * en el movimiento y se lo agrega a la cuenta indicada.
     * @param mov objeto movimiento
     * @return true si se actualizo bien , false si no.
     */
    public boolean actualizarMontoDeMovimientoEnLaCuenta(movimiento mov) {
        boolean r = true;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_ACTUALIZAR_MONTO);) {
            stm.clearParameters();
            stm.setString(1, String.valueOf(mov.getCuenta_num_cuenta()));
            stm.setString(2, String.valueOf(mov.getCuenta_num_cuenta()));
            stm.execute();

        } catch (IOException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            r = false;
        }
        return r;
    }

    // inserta un movimiento en la BD

    /**
     * Inserta un movimiento en la BD.
     * @param mov objeto de tipo movimineto
     * @return true si se hizo , false sino.
     */
    public boolean insertarMovimiento(movimiento mov) {
        boolean r = true;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_INSERTAR_MOVIMIENTO);) {
            stm.clearParameters();
            stm.setString(1, String.valueOf(mov.getCuenta_num_cuenta()));
            stm.setString(2, String.valueOf(mov.getMonto()));
            stm.setString(3, mov.getDetalle());
            stm.setString(4, mov.getDepositante());
            stm.execute();

        } catch (IOException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            r = false;
        }
        return r;
    }

    // retonar las cuentas relacionadas a ese numero de cedula

    /**
     * Recuperamos la info de todas las cuentas que existen asociadas a un cliente.
     * @param cedula numero de cedula
     * @return la lista de sus cuentas o null o vacia si no tenia ninguna.
     */
    public List<cuenta> obtenerCuentasCliente(String cedula) {
        List<cuenta> lis = new ArrayList<>();
        cuenta r;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_LISTAR_CEDULA);) {
            stm.clearParameters();
            stm.setString(1, String.valueOf(cedula));
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                r = new cuenta(
                        Integer.parseInt(rs.getString("num_cuenta")),
                        Integer.parseInt(rs.getString("tipo_cuenta_id_tipo_cuenta")),
                        rs.getString("cliente_id_cliente"),
                        rs.getString("moneda_nombre"),
                        rs.getString("fecha_creacion"),
                        Double.parseDouble(rs.getString("limite_transferencia_diaria")),
                        Integer.parseInt(rs.getString("activa")),
                        Double.parseDouble(rs.getString("saldo_actual")),
                        rs.getString("fecha_ultima_aplicacion")
                );
                lis.add(r);
            }

        } catch (IOException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return lis;
    }

    // Obtiene la lista de los movimientos relacionadas con
    // ese numero de cuenta , retorna null en caso de no haber nada.

    /**
     * Obtenemos todos los movimientos asociados a una cuenta.
     * @param num_cuenta numero de cuenta
     * @return null o lista de movimientos.
     */
    public List<movimiento> obtenerMovimientos(int num_cuenta) {
//        Optional<movimiento> r = Optional.empty();
        List<movimiento> lis = new ArrayList<>();
        movimiento r;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR);) {
            stm.clearParameters();
            stm.setString(1, String.valueOf(num_cuenta));
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                r = new movimiento(
                        Integer.parseInt(rs.getString("id_movimiento")),
                        Integer.parseInt(rs.getString("cuenta_num_cuenta")),
                        Double.parseDouble(rs.getString("monto")),
                        rs.getString("fecha"),
                        rs.getString("detalle"),
                        rs.getString("depositante"),
                        Integer.parseInt(rs.getString("aplicado"))
                );
                lis.add(r);
            }

        } catch (IOException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return lis;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        servicioDeposito sd = new servicioDeposito();
//       // P R U E B A - O B T E N E R - M O V I M I E N T O S

//        List<movimiento> lis = sd.obtenerMovimientos(1);
//        System.out.println("Tam: "+lis.size());
//        for (movimiento li : lis) {         
//            System.out.println(li.toString());   
//        }
//       // F I N - P R U E B A 
//          //  P R U E B A - O B T E N E R - C U E N T A S - CLIENTE
//          new funcionesAperturaCuenta().crearCuentaClienteExistente("117230117", "CRC", 0);
//          List<cuenta> lis = sd.obtenerCuentasCliente("117230117");
//          lis.forEach((li) -> {
//              System.out.println(li.toString());
//        });
        // F I N - P R U E B A 
        //-------------------------------------------------------
        // P R U E B A - I N S E R T A R - D E P O S I T O 
//        movimiento m = new movimiento();
//        m.setCuenta_num_cuenta(5);
//        m.setMonto(250);
//        m.setDetalle("Regalo");
        // actualizamos el monto en la cuenta 
//       System.out.println(sd.actualizarMontoDeMovimientoEnLaCuenta(m));
        // despues actualizamos el movimiento a realizado.
//       System.out.println(sd.establecerComoAplicadosLosMovimientosRealizados(m));
//        System.out.println(sd.insertarMovimiento(m));
//         F I N - P R U E B A 
    }

    /**
     * Filtra los movimientos por orden de inserción ya sea de 
     * mas reciente a mas antiguo.
     * @param num_cuenta int numero de la cuenta del cliente.
     * @return la list de movimientos o algun tipo de excepción relacionado
     * con la BD , puede ser el parametro o a la hora de la conexión.
     */
    public List<movimiento> obtenerMovimientosOrdenados(int num_cuenta) {
        
//        Optional<movimiento> r = Optional.empty();
        List<movimiento> lis = new ArrayList<>();
        movimiento r;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_Ordenado);) {
            stm.clearParameters();
            stm.setString(1, String.valueOf(num_cuenta));
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                r = new movimiento(
                        Integer.parseInt(rs.getString("id_movimiento")),
                        Integer.parseInt(rs.getString("cuenta_num_cuenta")),
                        Double.parseDouble(rs.getString("monto")),
                        rs.getString("fecha"),
                        rs.getString("detalle"),
                        rs.getString("depositante"),
                        Integer.parseInt(rs.getString("aplicado"))
                );
                lis.add(r);
            }

        } catch (IOException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return lis;
    
    }

    /**
     * Filtra los movimiento de una cuenta en especifico por rango de fechas
     * ejemplo movimientos desde el 2020-12-31 hasta el 2021-12-31 por decir algo
     * es importante que los parametros de fecha sean en string "yyyy-mm-dd" de lo 
     * contrario no funcionan los rangos.
     * @param num_cuenta int del numero de la fecha.
     * @param fech1 String fecha 1
     * @param fech2 String fecha 2
     * @return retorna la lista de los movimientos o una excepción por error al conectar
     * con la BD o con los parametros pasados.
     */
    public List<movimiento> obtenerMovimientosOrdenadosPorFecha(int num_cuenta,String fech1,
            String fech2) {
        
//        Optional<movimiento> r = Optional.empty();
        List<movimiento> lis = new ArrayList<>();
        movimiento r;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_OrdenadoPorFecha);) {
            stm.clearParameters();
            stm.setString(1, String.valueOf(num_cuenta));
            stm.setString(2, String.valueOf(fech1));
            stm.setString(3, String.valueOf(fech2));
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                r = new movimiento(
                        Integer.parseInt(rs.getString("id_movimiento")),
                        Integer.parseInt(rs.getString("cuenta_num_cuenta")),
                        Double.parseDouble(rs.getString("monto")),
                        rs.getString("fecha"),
                        rs.getString("detalle"),
                        rs.getString("depositante"),
                        Integer.parseInt(rs.getString("aplicado"))
                );
                lis.add(r);
            }

        } catch (IOException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return lis;
    }
    
}
