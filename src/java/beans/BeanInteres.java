package beans;

import java.io.Serializable;
import java.util.List;

/**
 * BeanRetiro.java
 * @Autores: Gabriel Barboza && Néstor Leiva
 * Descripción: Este bean sirve para manejar de manera inteligente la informacion
 * en el servlet de retiro
 */

public class BeanInteres implements Serializable {

    // Declaracion de constructor por defecto
    public BeanInteres() {
    }

    public BeanInteres(String eMensaje) {
        this.eMensaje = eMensaje;
    }

    public BeanInteres(String eMensaje, boolean eOperacionRealizada) {
        this.eMensaje = eMensaje;
        this.eOperacionRealizada = eOperacionRealizada;
    }
    

    public BeanInteres(List<model.movimiento> lista, String eMensaje, boolean eOperacionRealizada) {
        this.lista = lista;
        this.eMensaje = eMensaje;
        this.eOperacionRealizada = eOperacionRealizada;
    }

    public List<model.movimiento> getLista() {
        return lista;
    }

    public void setLista(List<model.movimiento> lista) {
        this.lista = lista;
    }

    public String geteMensaje() {
        return eMensaje;
    }

    public void seteMensaje(String eMensaje) {
        this.eMensaje = eMensaje;
    }

    public boolean getOperacionRealizada() {
        return eOperacionRealizada;
    }

    public void seteOperacionRealizada(boolean eOperacionRealizada) {
        this.eOperacionRealizada = eOperacionRealizada;
    }

    @Override
    public String toString() {
        return "BeanInteres{" + "lista=" + lista + ", eMensaje=" + eMensaje
                + ", eOperacionRealizada=" + eOperacionRealizada + '}';
    }
    
    private List<model.movimiento> lista;
    private String eMensaje;
    private boolean eOperacionRealizada;
    
}
