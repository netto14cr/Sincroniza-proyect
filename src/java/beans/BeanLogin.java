package beans;
import java.io.Serializable;

/**
 * BeanLogin.java
 * @Autores: Gabriel Barboza && Néstor Leiva
 * Descripción: Este bean sirve para manejar de manera inteligente la informacion
 * en el servlet de inicio de sesión
 */

public class BeanLogin implements Serializable {

    // Declaracion de constructor por defecto
    public BeanLogin() {
    }

    public BeanLogin(String eIdentificacion, String ePasword) {
        this.eIdentificacion = eIdentificacion;
        this.ePasword = ePasword;
    }

    public BeanLogin(String eIdentificacion, String ePasword, String eMensaje) {
        this.eIdentificacion = eIdentificacion;
        this.ePasword = ePasword;
        this.eMensaje = eMensaje;
    }
    public BeanLogin(String eMensaje) {
        this.eMensaje = eMensaje;
    }

    public String geteIdentificacion() {
        return eIdentificacion;
    }

    public void seteIdentificacion(String eIdentificacion) {
        this.eIdentificacion = eIdentificacion;
    }

    public String getePasword() {
        return ePasword;
    }

    public void setePasword(String ePasword) {
        this.ePasword = ePasword;
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

    public String geteMensaje() {
        return eMensaje;
    }

    public void seteMensaje(String eMensaje) {
        this.eMensaje = eMensaje;
    }

    @Override
    public String toString() {
        return "BeanLogin{" + "eIdentificacion=" + eIdentificacion + ", ePasword=" 
                + ePasword + ", eNombreUs=" + eNombreUs + ", eApellidosUs=" 
                + eApellidosUs + ", eMensaje=" + eMensaje + '}';
    }

    private String eIdentificacion;
    private String ePasword;
    private String eNombreUs;
    private String eApellidosUs;
    private String eMensaje;
    
}
