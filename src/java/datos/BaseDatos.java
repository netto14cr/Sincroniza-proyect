package datos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class BaseDatos {

    private BaseDatos() throws
            ClassNotFoundException,
            IOException,
            IllegalAccessException,
            InstantiationException {
        configuracion = new Properties();
        try {
            configuracion.load(getClass().getResourceAsStream(ARCHIVO_CONFIGURACION));
            try {
                String manejador = configuracion.getProperty("database_driver");
                System.out.printf("Cargando el manejador de la base de datos: %s%n", manejador);
                Class.forName(manejador).newInstance();
            } catch (ClassNotFoundException
                    | IllegalAccessException
                    | InstantiationException ex) {
                System.err.println("No se pudo cargar el manejador de la base de datos..");
                System.err.printf("Excepción: '%s'%n", ex.getMessage());
                throw ex;
            }
        } catch (IOException ex) {
            System.err.println("No se pudo leer el archivo de configuración..");
            throw ex;
        }
    }

    public static BaseDatos obtenerInstancia() throws
            ClassNotFoundException,
            IOException,
            IllegalAccessException,
            InstantiationException {
        if (instancia == null) {
            instancia = new BaseDatos();
        }
        return instancia;
    }

    public Connection obtenerConexion(String baseDatos, String usuario, String clave)
            throws SQLException {
        if (cnx != null) {
            cnx.close();
        }
        String URL_conexion = String.format("%s//%s/%s",
                configuracion.getProperty("protocol"),
                configuracion.getProperty("server_url"),
                baseDatos);
        System.out.printf("Conexión: '%s'%n", URL_conexion);

        cnx = DriverManager.getConnection(URL_conexion, usuario, clave);
        return cnx;
    }

    public Properties obtenerConfiguracion() {
        return configuracion;
    }

    public static void main(String[] args) {
        try {
            BaseDatos bd = BaseDatos.obtenerInstancia();
            Properties cfg = bd.obtenerConfiguracion();
            try (Connection cnx = bd.obtenerConexion(
                    cfg.getProperty("database"),
                    cfg.getProperty("user"),
                    cfg.getProperty("password")
            )) {
                System.out.println("La conexión fue exitosa..");
            }
        } catch (IOException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | SQLException ex) {
            System.err.println("No se pudo establecer la conexión a la base de datos.");
        }
    }

    private static final String ARCHIVO_CONFIGURACION = "bd.properties";
    private static BaseDatos instancia = null;
    private Properties configuracion;
    private Connection cnx;
}
