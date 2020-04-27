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
import model.cliente;

/**
 * servicioCliente.clase
 * @Autores: Gabriel Barboza && Néstor Leiva
 * Descripción: 
 */
public class servicioCliente extends servicioDeposito{

    /**
     * Realiza la conexion con la BD.
     * @return objeto de tipo conexion.
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

    private final String CMD_RECUPERAR
            = "select * from cliente where id_cliente=(?);";
    private final String CMD_INSERTAR
            = "insert into cliente (id_cliente,"
            + "usuario_id_usuario,apellidos,nombre,telefono) values"
            + " (?,?,?,?,?);";
    
    private final String CMD_LISTAR=
            "select * from cliente";
    
    /**
     * Recuperamos la info de un Cliente por medio de su numero de Cedula.
     * @param id # cedula
     * @return el cliente con todos sus datos.
     */
    public Optional<cliente> obtenerCliente(String id) {
        Optional<cliente> r = Optional.empty();
        try (Connection cnx = obtenerConexion();
            PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR);) {
            stm.clearParameters();
            stm.setString(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    r = Optional.of(new cliente(
                            rs.getString("id_cliente"),
                            rs.getString("apellidos"),
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            Integer.parseInt(rs.getString("usuario_id_usuario"))
                    ));
//                    System.out.print("Encontrado:"+rs.getString("id_cliente"));
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
     * Inserta un objeto de tipo cliente en la BD.
     * @param u es un Objeto de tipo cliente
     * @return true si se logro insertar , false de lo contrario
     */
    public boolean insertarCliente(cliente u) {
        boolean r = true;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_INSERTAR);) {
            stm.clearParameters();
            stm.setString(1, u.getId_cliente());
            stm.setString(2, String.valueOf(u.getUsuario_id_usuario()));
            stm.setString(3, u.getApellidos());
            stm.setString(4, u.getNombre());
            stm.setString(5, u.getTelefono());
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
     * Devuelve la lista de todos los clientes que esten en la base de datos,
     * en la tabla de cliente.
     * @return
     */
    public List<cliente> obtenerListaClientes() {
        List<cliente> r = new ArrayList<>();
        try (Connection cnx = obtenerConexion();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_LISTAR)) {
            while (rs.next()) {
                cliente e = new cliente(
                    
                            rs.getString("id_cliente"),
                            rs.getString("apellidos"),
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            Integer.parseInt(rs.getString("usuario_id_usuario"))
                )
                ;

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
        servicioCliente se = new servicioCliente();
        System.out.print(se.obtenerCliente("117230117").get().toString()+"\n");
        
        cliente c=new cliente();
        c.setId_cliente("007");
        c.setApellidos("bla bla");
        c.setNombre("bla bla");
        c.setUsuario_id_usuario(3);
        se.insertarCliente(c);
        
        System.out.print("-----------------------------------\n\n");
        
        List<cliente> clientes = se.obtenerListaClientes();
        System.out.println("Tamaño es : " + clientes.size());
        for (cliente cl : clientes) {
            System.out.printf(cl.toString() + "\n");
        }
        
       
        
    }
}
