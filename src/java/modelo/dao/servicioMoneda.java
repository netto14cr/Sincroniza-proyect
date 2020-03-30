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
import model.moneda;

/**
 *
 * @author gabri
 */
public class servicioMoneda {
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
    
    private final String CMD_RECUPERAR=
            "select * from moneda where nombre=(?);";
    
 public Optional<moneda> obtenerMoneda(String nombreMoneda) {
        Optional<moneda> r = Optional.empty();
        try (Connection cnx = obtenerConexion();
            PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR);) {
            stm.clearParameters();
            stm.setString(1, nombreMoneda);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    r = Optional.of(new moneda(
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getString("simbolo"),
                            Double.parseDouble(rs.getString("tipo_cambio_compra")),
                            Double.parseDouble(rs.getString("tipo_cambio_venta"))
                    ));
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

    public servicioMoneda() {
    }
 private final String CMD_LISTAR=
            "select * from moneda ;";
  public List<moneda> obtenerListaMonedas() {
        List<moneda> r = new ArrayList<>();
        try (Connection cnx = obtenerConexion();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_LISTAR)) {
            while (rs.next()) {
                moneda e = new moneda(
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getString("simbolo"),
                            Double.parseDouble(rs.getString("tipo_cambio_compra")),
                            Double.parseDouble(rs.getString("tipo_cambio_venta"))
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
  
  
      public static void main(String[] args) {
          
          
          servicioMoneda sm=new servicioMoneda();
          List<moneda> lis=sm.obtenerListaMonedas();
          
          for (moneda li : lis) {
              System.out.println(li.toString());
          }
          
          moneda m=sm.obtenerMoneda("USD").get();
          
          System.out.println(m.toString());
          
      }
  
  
}
