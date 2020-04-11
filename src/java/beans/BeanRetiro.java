package beans;

import java.io.Serializable;
import java.util.List;
import model.cuenta;

/**
 * BeanRetiro.java
 * @Autores: Gabriel Barboza && Néstor Leiva
 * Descripción: Este bean sirve para manejar de manera inteligente la informacion
 * en el servlet de retiro
 */

public class BeanRetiro implements Serializable {

    // Declaracion de constructor por defecto
    public BeanRetiro() {
    }
    
    // Declaracion del constructor si el usuario a depositar es la cuenta propia
    // entonces se guardan los valores de cedula, nombre, apellidos y el tipoBusqueda
    // realizada
     public BeanRetiro(String eCedula, String eNumCuenta, String eTipoBusqueda, 
             boolean eExitenciaCuenta) {
        this.eCedula = eCedula;
        this.eNumCuenta = eNumCuenta;
        this.eTipoBusqueda = eTipoBusqueda;
        this.eExitenciaCuenta=eExitenciaCuenta;
    }

    // Declaracion del constructor por si la cuenta se realiza por numero de cuenta
    // y no le pertenece al usuario
    public BeanRetiro(String eNumCuenta, String eTipoBusqueda) {
        this.eNumCuenta = eNumCuenta;
        this.eTipoBusqueda = eTipoBusqueda;
    }

    // Declaracion del constructor por si la cuenta buscada por el numero de cedula
    // no pertenece al usuario cajero

    public BeanRetiro(String eCedula, String eTipoBusqueda, List<cuenta> lista) {
        this.eCedula = eCedula;
        this.eTipoBusqueda = eTipoBusqueda;
        this.lista = lista;
    }

    public BeanRetiro(String eNumCuenta, String eCedula,
            String eMontoDeposito, String eDetalleDeposito, String eMensaje) {
        this.eNumCuenta = eNumCuenta;
        this.eCedula = eCedula;
        this.eMontoDeposito = eMontoDeposito;
        this.eDetalleDeposito = eDetalleDeposito;
        this.eMensaje = eMensaje;
    }

    public BeanRetiro(String eCedula,String eNumCuenta,String eTipoBusqueda,
            boolean eExitenciaCuenta, String eMensaje, String eDetalleDeposito) {
        this.eCedula = eCedula;
        this.eNumCuenta = eNumCuenta;
        this.eTipoBusqueda=eTipoBusqueda;
        this.eExitenciaCuenta=eExitenciaCuenta;
        this.eMensaje = eMensaje;
        this.eDetalleDeposito = eDetalleDeposito;
    }
    

    public BeanRetiro(String eMensaje) {
        this.eMensaje = eMensaje;
    }
    
     public BeanRetiro(String eMensaje, String eNumCuenta,String eTipoBusqueda) {
        this.eMensaje = eMensaje;
        this.eNumCuenta = eNumCuenta;
        this.eTipoBusqueda=eTipoBusqueda;
    }

    public String geteCedulaDet() {
        return eCedulaDet;
    }

    public void seteCedulaDet(String eCedulaDet) {
        this.eCedulaDet = eCedulaDet;
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

    @Override
    public String toString() {
        return "BeanRetiro{" + "eCedula=" + eCedula + ", eCedulaDet=" + eCedulaDet 
                + ", eNumCuenta=" + eNumCuenta + ", eNombreUs=" + eNombreUs 
                + ", eMontoDeposito=" + eMontoDeposito + ", eMonedaDeposito=" 
                + eMonedaDeposito + ", eDetalleDeposito=" + eDetalleDeposito 
                + ", eTipoBusqueda=" + eTipoBusqueda + ", lista=" + lista 
                + ", eMensaje=" + eMensaje + ", eExitenciaCuenta=" + eExitenciaCuenta + '}';
    }
    
    
    private String eCedula;
    private String eCedulaDet;
    private String eNumCuenta;
    private String eNombreUs;
    private String eMontoDeposito;
    private String eMonedaDeposito;
    private String eDetalleDeposito;
    private String eTipoBusqueda;
    private List<cuenta> lista;
    private String eMensaje;
    private boolean eExitenciaCuenta;
    
}
