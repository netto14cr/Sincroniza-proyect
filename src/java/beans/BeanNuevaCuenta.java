package beans;

import java.io.Serializable;

public class BeanNuevaCuenta implements Serializable {

    public BeanNuevaCuenta() {
    }
    
    private boolean cuentaCreada;

    // Se define el constructor por defecto para pasar los parametros 
    // del usuario que no existe en el sistema y se procedera a craer cuenta
    public BeanNuevaCuenta(String eCedula, String eMaxTransferencia, String eTipoMoneda,
            String eNumeroCuenta, String eMensaje, boolean cuentaCreada) {
        this.eCedula = eCedula;
        this.eMaxTransferencia = eMaxTransferencia;
        this.eTipoMoneda = eTipoMoneda;
        this.eNumeroCuenta = eNumeroCuenta;
        this.eMensaje = eMensaje;
        this.cuentaCreada=cuentaCreada;
    }

    // Se define el constructor por defecto para pasar los parametros 
    // del usuario nuevo en la completacion del segundo formulario
    public BeanNuevaCuenta(String eNombreUs, String eApellidosUs, String eTipoUs) {
        this.eNombreUs = eNombreUs;
        this.eApellidosUs = eApellidosUs;
        this.eTipoUs = eTipoUs;
    }

    public BeanNuevaCuenta(String eCedula, String eMaxTransferencia, String eTipoMoneda, 
            String eNumeroCuenta, String eNombreUs, String eApellidosUs, String eTipoUs, 
            String eMensaje, String eTelefono, String ePassword) {
        this.eCedula = eCedula;
        this.eMaxTransferencia = eMaxTransferencia;
        this.eTipoMoneda = eTipoMoneda;
        this.eNumeroCuenta = eNumeroCuenta;
        this.eNombreUs = eNombreUs;
        this.eApellidosUs = eApellidosUs;
        this.eTipoUs = eTipoUs;
        this.eMensaje = eMensaje;
        this.eTelefono=eTelefono;
        this.ePassword=ePassword;
    }

    public boolean getCuentaCreada() {
        return cuentaCreada;
    }

    public void setCuentaCreada(boolean cuentaCreada) {
        this.cuentaCreada = cuentaCreada;
    }

    
    public String getePassword() {
        return ePassword;
    }

    public void setePassword(String ePassword) {
        this.ePassword = ePassword;
    }
    
    public BeanNuevaCuenta(String eMensaje) {
        this.eMensaje = eMensaje;
    }

    
    public String geteMensaje() {
        return eMensaje;
    }

    public void seteMensaje(String eMensaje) {
        this.eMensaje = eMensaje;
    }

    public String geteTelefono() {
        return eTelefono;
    }

    public void seteTelefono(String eTelefono) {
        this.eTelefono = eTelefono;
    }

    

   
    public String geteCedula() {
        return eCedula;
    }

    public void seteCedula(String eCedula) {
        this.eCedula = eCedula;
    }

    public String geteMaxTransferencia() {
        return eMaxTransferencia;
    }

    public void seteMaxTransferencia(String eMaxTransferencia) {
        this.eMaxTransferencia = eMaxTransferencia;
    }

    public String geteTipoMoneda() {
        return eTipoMoneda;
    }

    public void seteTipoMoneda(String eTipoMoneda) {
        this.eTipoMoneda = eTipoMoneda;
    }

    public String geteNumeroCuenta() {
        return eNumeroCuenta;
    }

    public void seteNumeroCuenta(String eNumeroCuenta) {
        this.eNumeroCuenta = eNumeroCuenta;
    }

    public String geteNombreUs() {
        return eNombreUs;
    }

    public void seteNombreUs(String eNombreUs) {
        this.eNombreUs = eNombreUs;
    }

    public String geteApellidosUs() {
        return eApellidosUs;
    }

    public void seteApellidosUs(String eApellidosUs) {
        this.eApellidosUs = eApellidosUs;
    }

    public String geteTipoUs() {
        return eTipoUs;
    }

    public void seteTipoUs(String eTipoUs) {
        this.eTipoUs = eTipoUs;
    }

    @Override
    public String toString() {
        return "BeanNuevaCuenta{" + "cuentaCreada=" + cuentaCreada + ", eCedula=" 
                + eCedula + ", eMaxTransferencia=" + eMaxTransferencia + ", eTipoMoneda=" 
                + eTipoMoneda + ", eNumeroCuenta=" + eNumeroCuenta + ", eNombreUs=" 
                + eNombreUs + ", eApellidosUs=" + eApellidosUs + ", eTipoUs=" 
                + eTipoUs + ", eMensaje=" + eMensaje + ", eTelefono=" + eTelefono 
                + ", ePassword=" + ePassword + '}';
    }

    

    public String getEtiqueta() {
        return eCedula;
    }

    public void setEtiqueta(String eCedula) {
        this.eCedula = eCedula;
    }

    // MEtodo que devuelte el nombre de la transferencia de acuerdo a la opcion
    // seleccionada por el usuario
    public String maxTranferenciaEtiqueta(String maxT) {
        String nombreT = "";
        switch (maxT) {
           
            case "1":
                nombreT="100.000 "+eTipoMoneda;
                break;
            case "2":
                nombreT="200.000";
                break;
            case "3":
                nombreT="300.000";
                break;
            case "4":
                nombreT="400.000";
                break;
            case "5":
                nombreT="500.000";
                break;
            case "6":
                nombreT="600.000";
                break;

            case "7":
                nombreT="700.000";
                break;
            case "8":
                nombreT="800.000";
                
                break;
            case "9":
                nombreT="900.000";
                break;
            case "10":
                nombreT="1.000.000";
                break;
        }   
        return nombreT;
    }

 
    private String eCedula;
    private String eMaxTransferencia;
    private String eTipoMoneda;
    private String eNumeroCuenta;
    private String eNombreUs;
    private String eApellidosUs;
    private String eTipoUs;
    private String eMensaje;
    private String eTelefono;
    private String ePassword;
}
