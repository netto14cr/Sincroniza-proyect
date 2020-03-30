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
import model.usuario;

/**
 *
 * @author gabri
 */
public class servicioUsuario {

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
            + " from usuario where id_usuario=?;";
    private static final String CMD_LISTAR
            = "select * from usuario;";
    private static final String CMD_INSERTAR
            = "insert into usuario ("
            + "id_usuario,clave_acceso,clave_vencida,rol) values "
            + "(?,?,?,?);";
    private static final String CMD_ACTUALIZAR_CLAVE
            = "update usuario set clave_acceso=(?),clave_vencida=0 where"
            + " id_usuario=(?);";
    private static final String CMD_ULTIMO_ID
//            = "select id_usuario from usuario where id_usuario=( select last_insert_id() );";
              ="select max(id_usuario) from usuario;";

    /**
     * Recupera de la BD el ultimo id de la tabla de usuario.
     * @return int numero de id de la tabla usuario.
     */
    public int obtenerUltimoIdUsuario() {
        int r= 0;
        try (Connection cnx = obtenerConexion();
            PreparedStatement stm = cnx.prepareStatement(CMD_ULTIMO_ID);) {
            stm.clearParameters();
            
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    r=Integer.parseInt(rs.getString("max(id_usuario)"));                    
                }else
                {
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
     *Actualiza la contraseña de un usuario en la tabla de usuario
     * deacuerdo con su id.
     * @param u objeto de tipo Usuario
     * @return true si se actualizo , false si no.
     */
    public boolean actualizarClave(usuario u) {
        boolean r = true;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_ACTUALIZAR_CLAVE);) {
            stm.clearParameters();
            stm.setString(1, u.getClave_acceso());
            stm.setString(2, String.valueOf(u.getId_usuario()));
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
     *Recuperamos un usuario de la BD con su ID.
     * @param id PK id_usuario
     * @return objeto optional(usuario) - empty o el objeto usuario.
     */
    public Optional<usuario> obtenerUsuario(int id) {
        Optional<usuario> r = Optional.empty();
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR);) {
            stm.clearParameters();
            stm.setString(1, String.valueOf(id));
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    r = Optional.of(new usuario(
                            Integer.parseInt(rs.getString("id_usuario")),
                            rs.getString("clave_acceso"),
                            Integer.parseInt(rs.getString("clave_vencida")),
                            Integer.parseInt(rs.getString("rol")))
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
    
    // el usuario tiene que venir con la clave lista! OJAS

    /**
     * Inserta en la BD un usuario
     * @param u objeto usuario
     * @return true si se realizo ,false si no.
     */
    public boolean insertarUsuario(usuario u) {
        boolean r = true;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_INSERTAR);) {
            stm.clearParameters();
            stm.setString(1, String.valueOf(u.getId_usuario()));
            stm.setString(2, u.getClave_acceso());
            stm.setString(3, String.valueOf(u.getClave_vencida()));
            stm.setString(4, String.valueOf(u.getRol()));
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
     *Recuperamos la info de todos los usuarios que existen
     * @return null o list de usuarios.
     */
    public List<usuario> obtenerListaUsuarios() {
        List<usuario> r = new ArrayList<>();
        try (Connection cnx = obtenerConexion();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_LISTAR)) {
            while (rs.next()) {
                usuario e = new usuario(
                        Integer.parseInt(rs.getString("id_usuario")),
                        rs.getString("clave_acceso"),
                        Integer.parseInt(rs.getString("clave_vencida")),
                        Integer.parseInt(rs.getString("rol"))
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
        servicioUsuario se = new servicioUsuario();

//        List<usuario> usuarios = se.obtenerListaUsuarios();
//        System.out.print("Tamaño es : " + usuarios.size());
//        for (usuario us : usuarios) {
//            System.out.printf(us.toString() + "\n");
//        }
//         System.out.print("------------------------------------------\n\n"
//                 + "Encontrado : "+l.get().toString()+"\n");
        System.out.println(se.obtenerUltimoIdUsuario());
        se.actualizarClave(new usuario(1,"121",1,0));
    }
}
