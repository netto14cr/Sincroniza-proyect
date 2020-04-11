package beans;
import java.io.Serializable;
import java.util.List;
import model.cuenta;

/**
 * BeanTransCaja.java
 * @Autores: Gabriel Barboza && Néstor Leiva
 * Descripción: Este bean se utiliza para manejar la informacion en el servidor
 * de busqueda de cuentas de transferencias en cajas
 */



public class BeanTransCaja implements Serializable {

    // Declaracion de constructor por defecto
    public BeanTransCaja() {
    }
    
    // Declaracion del constructor si el usuario a depositar es la cuenta propia
    // entonces se guardan los valores de cedula, nombre, apellidos y el tipoBusqueda
    // realizada
     public BeanTransCaja(String eCedula, String eNumCuenta, String eTipoBusqueda, 
             boolean eExitenciaCuenta) {
        this.eCedula = eCedula;
        this.eNumCuenta = eNumCuenta;
        this.eTipoBusqueda = eTipoBusqueda;
        this.eExitenciaCuenta=eExitenciaCuenta;
    }
     
     
     public BeanTransCaja(String eCedula, String eNumCuenta, String eMensaje) {
        this.eCedula = eCedula;
        this.eNumCuenta = eNumCuenta;
        this.eMensaje = eMensaje;
    }

    // Declaracion del constructor por si la cuenta se realiza por numero de cuenta
    // y no le pertenece al usuario
    public BeanTransCaja(String eNumCuenta, String eTipoBusqueda) {
        this.eNumCuenta = eNumCuenta;
        this.eTipoBusqueda = eTipoBusqueda;
    }

    // Declaracion del constructor por si la cuenta buscada por el numero de cedula
    // no pertenece al usuario cajero

    public BeanTransCaja(String eCedula, String eTipoBusqueda, List<cuenta> lista, 
            boolean eExitenciaCuenta) {
        this.eCedula = eCedula;
        this.eTipoBusqueda = eTipoBusqueda;
        this.lista = lista;
        this.eExitenciaCuenta=eExitenciaCuenta;
    }
    
    public BeanTransCaja(String eCedula,String eNumCuenta,String eTipoBusqueda, 
            boolean eExitenciaCuenta, boolean cuentaSeleccionada) {
        this.eCedula = eCedula;
        this.eNumCuenta = eNumCuenta;
        this.eTipoBusqueda=eTipoBusqueda;
        this.eExitenciaCuenta=eExitenciaCuenta;
        this.cuentaSeleccionada=cuentaSeleccionada;
    }
    public BeanTransCaja(String eCedula,String eNumCuenta,String eTipoBusqueda, 
            boolean eExitenciaCuenta, 
             boolean cuentaSeleccionada, String eMensaje) {
        this.eCedula = eCedula;
        this.eNumCuenta = eNumCuenta;
        this.eTipoBusqueda=eTipoBusqueda;
        this.eExitenciaCuenta=eExitenciaCuenta;
        this.cuentaSeleccionada=cuentaSeleccionada;
        this.eMensaje = eMensaje;
    }
    

    public BeanTransCaja(String eNumCuenta, String eCedula,
            String eMontoDeposito, String eDetalleDeposito, String eNumCuenta2, 
            String eMensaje, boolean cuentaSeleccionada) {
        this.eNumCuenta = eNumCuenta;
        this.eCedula = eCedula;
        this.eMontoDeposito = eMontoDeposito;
        this.eDetalleDeposito = eDetalleDeposito;
        this.eNumCuenta2 = eNumCuenta2;
        this.eMensaje = eMensaje;
        this.cuentaSeleccionada=cuentaSeleccionada;
    }

    public BeanTransCaja(String eCedula,String eNumCuenta,String eTipoBusqueda,
            boolean eExitenciaCuenta, String eMensaje, String eDetalleDeposito) {
        this.eCedula = eCedula;
        this.eNumCuenta = eNumCuenta;
        this.eTipoBusqueda=eTipoBusqueda;
        this.eExitenciaCuenta=eExitenciaCuenta;
        this.eMensaje = eMensaje;
        this.eDetalleDeposito = eDetalleDeposito;
    }
    

    public BeanTransCaja(String eMensaje) {
        this.eMensaje = eMensaje;
    }
    
     public BeanTransCaja(String eNumCuenta,String eTipoBusqueda, boolean eExitenciaCuenta, 
             boolean cuentaSeleccionada) {
        this.eNumCuenta = eNumCuenta;
        this.eTipoBusqueda=eTipoBusqueda;
        this.eExitenciaCuenta=eExitenciaCuenta;
        this.cuentaSeleccionada=cuentaSeleccionada;
    }
     public BeanTransCaja(String eNumCuenta,String eTipoBusqueda, boolean eExitenciaCuenta, 
             boolean cuentaSeleccionada, String eMensaje) {
        this.eNumCuenta = eNumCuenta;
        this.eTipoBusqueda=eTipoBusqueda;
        this.eExitenciaCuenta=eExitenciaCuenta;
        this.cuentaSeleccionada=cuentaSeleccionada;
        this.eMensaje = eMensaje;
        
    }

    
    public String geteCedula() {
        return eCedula;
    }

    public void seteCedula(String eCedula) {
        this.eCedula = eCedula;
    }

    public String geteNumCuenta() {
        return eNumCuenta;
    }

    public void seteNumCuenta(String eNumCuenta) {
        this.eNumCuenta = eNumCuenta;
    }

    public String geteMontoDeposito() {
        return eMontoDeposito;
    }

    public void seteMontoDeposito(String eMontoDeposito) {
        this.eMontoDeposito = eMontoDeposito;
    }

    public String geteMonedaDeposito() {
        return eMonedaDeposito;
    }

    public void seteMonedaDeposito(String eMonedaDeposito) {
        this.eMonedaDeposito = eMonedaDeposito;
    }

    public String geteTipoBusqueda() {
        return eTipoBusqueda;
    }

    public void seteTipoBusqueda(String eTipoBusqueda) {
        this.eTipoBusqueda = eTipoBusqueda;
    }

    public String geteDetalleDeposito() {
        return eDetalleDeposito;
    }

    public void seteDetalleDeposito(String eDetalleDeposito) {
        this.eDetalleDeposito = eDetalleDeposito;
    }
    
    public String geteNombreUs() {
        return eNombreUs;
    }

    public void seteNombreUs(String eNombreUs) {
        this.eNombreUs = eNombreUs;
    }


    public void setEtiqueta(String eCedula) {
        this.eCedula = eCedula;
    }

    public String geteMensaje() {
        return eMensaje;
    }

    public void seteMensaje(String eMensaje) {
        this.eMensaje = eMensaje;
    }
    
    public List<cuenta> getLista() {
        return lista;
    }

    public void setLista(List<cuenta> lista) {
        this.lista = lista;
    }

    public boolean getExitenciaCuenta() {
        return eExitenciaCuenta;
    }

    public void seteExitenciaCuenta(boolean eExitenciaCuenta) {
        this.eExitenciaCuenta = eExitenciaCuenta;
    }

    public boolean getCuentaSeleccionada() {
        return cuentaSeleccionada;
    }

    public void setCuentaSeleccionada(boolean cuentaSeleccionada) {
        this.cuentaSeleccionada = cuentaSeleccionada;
    }

    public String geteNumCuenta2() {
        return eNumCuenta2;
    }

    public void seteNumCuenta2(String eNumCuenta2) {
        this.eNumCuenta2 = eNumCuenta2;
    }

    @Override
    public String toString() {
        return "BeanTransCaja{" + "eCedula=" + eCedula + ", eNumCuenta=" 
                + eNumCuenta + ", eNumCuenta2=" + eNumCuenta2 + ", eNombreUs=" 
                + eNombreUs + ", eMontoDeposito=" + eMontoDeposito + ", eMonedaDeposito=" 
                + eMonedaDeposito + ", eDetalleDeposito=" + eDetalleDeposito 
                + ", eTipoBusqueda=" + eTipoBusqueda + ", lista=" + lista 
                + ", eMensaje=" + eMensaje + ", eExitenciaCuenta=" + eExitenciaCuenta 
                + ", cuentaSeleccionada=" + cuentaSeleccionada + '}';
    }
    
    private String eCedula;
    private String eNumCuenta;
    private String eNumCuenta2;
    private String eNombreUs;
    private String eMontoDeposito;
    private String eMonedaDeposito;
    private String eDetalleDeposito;
    private String eTipoBusqueda;
    private List<cuenta> lista;
    private String eMensaje;
    private boolean eExitenciaCuenta;
    private boolean cuentaSeleccionada;
    
}
