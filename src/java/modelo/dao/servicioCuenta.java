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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import model.cuenta;
import model.cuentaConTasaYDescripcion;
import model.favorita;

/**
 *
 * @author gabri
 */
public class servicioCuenta {

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
    
    private static final String CMD_RECUPERAR
            = "select *"
            + " from cuenta where num_cuenta=?;";
    private static final String CMD_LISTAR
            = "select * from cuenta;";
    private static final String CMD_INSERTAR
            = "insert into cuenta (num_cuenta,tipo_cuenta_id_tipo_cuenta,cliente_id_cliente,moneda_nombre)"
            + " values "
            + "(?,?,?,?);";
    private static final String CMD_ULTIMO_ID
            //            = "select id_usuario from usuario where id_usuario=( select last_insert_id() );";
            = "select max(num_cuenta) from cuenta;";
    
    private static final String CMD_LIGAR_CUENTA_A_FAVORITA
            ="insert into favorita (cliente_id_cliente,cuenta_num_cuenta)"
            + " values "
            + "(?,?);";
    private static final String CMD_LISTAR_CUENTAS_FAVORITAS
            ="select * from favorita where cliente_id_cliente=(?);";
    private static final String CMD_INSERTAR_CUENTA_FAVORITA
            ="insert into favorita (cliente_id_cliente,cuenta_num_cuenta) " +
"values " +
"(?,?);";
    //  este metodo retorna cuentasConTasaYDescripcion VER MODELO
    private static final String CMD_RECUPERAR_CUENTAS_CON_TASA_Y_DESCRIPCION
            ="select * from eif209_2001_p01b.cuenta inner join eif209_2001_p01b.tipo_cuenta on eif209_2001_p01b.cuenta.tipo_cuenta_id_tipo_cuenta=tipo_cuenta.id_tipo_cuenta;";

    /**
     * Inserta un objeto favorita en la tabla de la BD
     * tiene que ir con los atributos de cedula y la cuenta a la que se 
     * desea asociar.
     * @param fav objeto favorita
     * @return true si inserto correcto , false de lo contrario.
     */
    public boolean insertarCuentaAFavoritas(favorita fav) {
        boolean r = true;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_INSERTAR_CUENTA_FAVORITA);) {
            stm.clearParameters();
            stm.setString(1, fav.getCliente_id_cliente());
            stm.setString(2, String.valueOf(fav.getCuenta_num_cuenta()));
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
    
    /**
     * Lista todas las cuentas asociadas a este cliente , que se encuentran
     * en la tabla de favoritas.
     * @param cedula String numero de cedula del cliente
     * @return lista de objetos favorita que contienen id del cliente y sus # de cuentas 
     * asociados.
     */
    public List<favorita> listarNumCuentasfavoritas(String cedula) {
//        Optional<movimiento> r = Optional.empty();
        List<favorita> lis = new ArrayList<>();
        favorita r;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_LISTAR_CUENTAS_FAVORITAS);) {
            stm.clearParameters();
            stm.setString(1, cedula);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                r = new favorita(rs.getString("cliente_id_cliente")
                ,rs.getInt("cuenta_num_cuenta")
                )
                ;
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
     *Metodo que lista todas las cuentas en el sistema y las recupera
     * con la descripción de la cuenta(0,1) y el interes con el fin de utilizarla
     * en otro metodo para hacer la acreditación de todas las cuentas,hay una clase nueva
     * para tal fin hereda de cuenta y tiene 2 atributos mas solamente es secilla.
     * @return lista de objetos cuentaConTasaYDescripción.
     */
    public List<cuentaConTasaYDescripcion> listarCuentasConTasayDescripcion()
    {
        List<cuentaConTasaYDescripcion> r = new ArrayList<>();
        try (Connection cnx = obtenerConexion();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_RECUPERAR_CUENTAS_CON_TASA_Y_DESCRIPCION)) {
            while (rs.next()) {
                cuentaConTasaYDescripcion e = new cuentaConTasaYDescripcion(
                        rs.getDouble("tasa_interés"),
                        rs.getString("descripción"),
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

                r.add(e);
            }
        } catch (IOException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return r; 
    }
    
    /**
     *Realiza el enlace de las tablas favorita y cuentas , obtiene la cuenta
     * ingresada y la liga a favoritas para que asi pueda realizar operaciones
     * con ella.
     * @param ultimo_id numero de cuenta de la cuenta recien creada
     * @return  true si logro ligarlas , false si no.
     */
    public boolean ligarCuentaPropiaAFavoritas(int ultimo_id)
    {
//        int ultimo_id=this.obtenerUltimoNumCuenta();
        cuenta caux=this.obtenerCuenta(ultimo_id).get();
        String id_cliente=caux.getCliente_id_cliente();
        
           boolean r=true;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_LIGAR_CUENTA_A_FAVORITA);) {
            stm.clearParameters();
            stm.setString(1, id_cliente);
            stm.setString(2, String.valueOf(ultimo_id));
            
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
    
    /**
     * Obtenemos el ultimo numero de cuenta digitado en la tabla de cuenta
     * @return int numero de cuenta.
     */
    public int obtenerUltimoNumCuenta() {
        int r = 0;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_ULTIMO_ID);) {
            stm.clearParameters();

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    r = Integer.parseInt(rs.getString("max(num_cuenta)"));
                } else {
//                    System.out.println("No Entro!");

                }
            }
        } catch (IOException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return r;
    }

    /**
     *Recuperamos la cuenta con todos sus detalles utilizando el numero de cuenta.
     * @param numCuenta numero de cuenta (tabla cuenta)
     * @return el objeto cuenta con todos sus atributos.
     */
    public Optional<cuenta> obtenerCuenta(int numCuenta) {

        Optional<cuenta> r = Optional.empty();
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR);) {
            stm.clearParameters();
            stm.setString(1, String.valueOf(numCuenta));
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    r = Optional.of(new cuenta(
                            Integer.parseInt(rs.getString("num_cuenta")),
                            Integer.parseInt(rs.getString("tipo_cuenta_id_tipo_cuenta")),
                            rs.getString("cliente_id_cliente"),
                            rs.getString("moneda_nombre"),
                            rs.getString("fecha_creacion"),
                            Double.parseDouble(rs.getString("limite_transferencia_diaria")),
                            Integer.parseInt(rs.getString("activa")),
                            Double.parseDouble(rs.getString("saldo_actual")),
                            rs.getString("fecha_ultima_aplicacion")
                    )
                    );
//                    System.out.print("Encontrado:"+rs.getString("id_usuario"));
                }
            }
        } catch (IOException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return r;
    }

    /**
     *
     * @param cuen
     * @return
     */
    public boolean insertarCuenta(cuenta cuen) {
        boolean r = true;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_INSERTAR);) {
            stm.clearParameters();
            stm.setString(1, String.valueOf(cuen.getNum_cuenta()));
            stm.setString(2, String.valueOf(cuen.getTipo_cuenta_id_tipo_cuenta()));
            stm.setString(3, String.valueOf(cuen.getCliente_id_cliente()));
            stm.setString(4, String.valueOf(cuen.getMoneda_nombre()));
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
    
    /**
     *Recuperamos la lista de todos los usuarios que se encuentra en la 
     * BD.
     * @return  lista de cuentas.
     */
    public List<cuenta> obtenerListaUsuarios() {
        List<cuenta> r = new ArrayList<>();
        try (Connection cnx = obtenerConexion();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_LISTAR)) {
            while (rs.next()) {
                cuenta e = new cuenta(
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

                r.add(e);
            }
        } catch (IOException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return r;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        servicioCuenta sc=new servicioCuenta();
//        
//        System.out.println(sc.obtenerUltimoNumCuenta());
//        
//        List<cuenta> lis=sc.obtenerListaUsuarios();
//        
//        for (cuenta li : lis) {
//            System.out.println(li.toString());
//        }
//        
//        System.out.println(sc.obtenerCuenta(1).get().toString());
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
//   LocalDateTime now = LocalDateTime.now();  
//   System.out.println(dtf.format(now)); 
//   System.out.println("-------------------------------------------"); 
//        
//   cuenta c=new cuenta();
//   c.setCliente_id_cliente("666");
//   c.setTipo_cuenta_id_tipo_cuenta(0);
//   c.setNum_cuenta(sc.obtenerUltimoNumCuenta()+1);
//   c.setMoneda_nombre("CRC");
//   sc.insertarCuenta(c);
//        
//    System.out.println(sc.ligarCuentaPropiaAFavoritas(sc.obtenerUltimoNumCuenta()+1));
//    System.out.println(new funcionesAperturaCuenta().crearCuentaClienteExistente("117230117","CRC", 1));

// listado de cuenta con la tasa de interés y la descripción.
    List<cuentaConTasaYDescripcion> lis=sc.listarCuentasConTasayDescripcion();
        
        for (cuentaConTasaYDescripcion li : lis) {
            System.out.println(li.toString());
        }
        
    }
    
    
}
