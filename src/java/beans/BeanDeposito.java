package beans;

import java.io.Serializable;
import java.util.List;
import model.cuenta;

public class BeanDeposito implements Serializable {

    // Declaracion de constructor por defecto
    public BeanDeposito() {
    }
    
    // Declaracion del constructor si el usuario a depositar es la cuenta propia
    // entonces se guardan los valores de cedula, nombre, apellidos y el tipoBusqueda
    // realizada
     public BeanDeposito(String eCedula, String eNombreUs, String eTipoBusqueda) {
        this.eCedula = eCedula;
        this.eNombreUs = eNombreUs;
        this.eTipoBusqueda = eTipoBusqueda;
    }

    // Declaracion del constructor por si la cuenta se realiza por numero de cuenta
    // y no le pertenece al usuario
    public BeanDeposito(String eNumCuenta, String eTipoBusqueda) {
        this.eNumCuenta = eNumCuenta;
        this.eTipoBusqueda = eTipoBusqueda;
    }

    // Declaracion del constructor por si la cuenta buscada por el numero de cedula
    // no pertenece al usuario cajero

    public BeanDeposito(String eCedula, String eTipoBusqueda, List<cuenta> lista) {
        this.eCedula = eCedula;
        this.eTipoBusqueda = eTipoBusqueda;
        this.lista = lista;
    }

    public BeanDeposito(String eNumCuenta, String eCedula,
            String eMontoDeposito, String eDetalleDeposito, String eMensaje) {
        this.eNumCuenta = eNumCuenta;
        this.eCedula = eCedula;
        this.eMontoDeposito = eMontoDeposito;
        this.eDetalleDeposito = eDetalleDeposito;
        this.eMensaje = eMensaje;
    }

    public BeanDeposito(String eCedula, String eCedulaDet, String eNumCuenta, 
            String eNombreUs, String eMontoDeposito, String eDetalleDeposito, 
            String eMensaje,String eTipoBusqueda) {
        this.eCedula = eCedula;
        this.eCedulaDet = eCedulaDet;
        this.eNumCuenta = eNumCuenta;
        this.eNombreUs = eNombreUs;
        this.eMontoDeposito = eMontoDeposito;
        this.eDetalleDeposito = eDetalleDeposito;
        this.eMensaje = eMensaje;
        this.eTipoBusqueda=eTipoBusqueda;
    }
    

    public BeanDeposito(String eMensaje) {
        this.eMensaje = eMensaje;
    }
    
    
     public BeanDeposito(String eMensaje, String eNumCuenta,String eTipoBusqueda,  
             String eNombreUs ) {
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

    @Override
    public String toString() {
        return "BeanDeposito{" + "eCedula=" + eCedula + ", eCedulaDet=" + eCedulaDet 
                + ", eNumCuenta=" + eNumCuenta + ", eNombreUs=" + eNombreUs 
                + ", eMontoDeposito=" + eMontoDeposito 
                + ", eMonedaDeposito=" + eMonedaDeposito + ", eDetalleDeposito=" 
                + eDetalleDeposito + ", eTipoBusqueda=" + eTipoBusqueda + ", lista=" 
                + lista + ", eMensaje=" + eMensaje + '}';
    }
    
    
    
    
    private String eCedula;
    private String eCedulaDet;
    private String eNumCuenta;
    private String eNombreUs;
    private String eApellidosUs;
    private String eMontoDeposito;
    private String eMonedaDeposito;
    private String eDetalleDeposito;
    private String eTipoBusqueda;
    private List<cuenta> lista;
    private String eMensaje;
    
}
