package servicios;
import beans.BeanLogin;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.usuario;
import modelo.dao.funcionesFrontEnd.funcionesDeposito;
import modelo.dao.funcionesFrontEnd.funcionesLogueo;

/*
 * ServletInicionSesion.java
 * Autores: Gabriel Barboza && Néstor Leiva
 * Descripcion: Clase java tipo servlet de cambio de contraseña 
 */
@WebServlet(
        name = "ServletCambioPassword",
        urlPatterns = {"/ServletCambioPassword", "/cambio-Contra"}
)

public class ServletCambioPassword extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("\n:::::::  SERVLET CAMBIO DE CONTRASEÑA     ::::::::");
        
        // Se define de que direccion viene el usaurio
        String destinoCajero; destinoCajero = "";
        String destinoCliente;destinoCliente = "";
        String destino;destino="";
        String mensajeAux="";
        destinoCliente = "/WEB-INF/Banco/Vista/Cliente.jsp";
        destinoCajero = "/WEB-INF/Banco/Vista/Cajero.jsp";
        
        // Se declara las respuestas de las sesiones
        HttpSession sesionActual = request.getSession();
        // Se establece el envio de etiqueta y mensaje al bean
        sesionActual.setAttribute("ruta", request.getRequestURI());

        String newPass = request.getParameter("passwNew");
        String repPassw = request.getParameter("passwNew");
        RequestDispatcher dispatcher = null;
        
        // Se declara el uso de informacion guarda en el bean de login y
        // se establece como se respondera la infromacion manejada en el servlet
        BeanLogin bLogin = (BeanLogin) sesionActual.getAttribute("eLogin");
        request.getSession().getAttribute("bLogin");

        
        // Se verifica que la informacion enviada por el ususario no venga vacia
        // o sean nula para poder continuar y actualizar la informacion sin generar
        // algun error en el proceso
        if (newPass != null && repPassw != null) {

            try {
                // SE obtiene la informacion del usuario usarla para comparar y 
                // actualizar informacion del mismi
                usuario user = servicio.obtenerUsuarioPorCedula(bLogin.geteIdentificacion());
                
                // Se verifica el tipo de usuario a actualizar la informacion de
                // la nueva contraseña , si es == 0 - Es cliente
                if (user.getRol()==0){
                    destino=destinoCliente;
                    
                 // si el tipo de usario es cajero
                }else if (user.getRol() == 1) {
                    destino=destinoCajero;
                }
                
                // Se verifica que la nueva contraseña sea diferente a la anterior
                // registrada en el sistema
                if(!newPass.equals(user.getClave_acceso())){
                    //----------------------------------------------------------
                    
                    // AQUI VA EL METODO QUE FALTA DE FUNCIONES DE LOGUEO QUE
                    // ACTUALIZARIA LA NUEWVA CONTRASEÑA Y EL VENCIMIENTO DE LA 
                    // MISMA EN LA BASE DE DATOS

                    user.setClave_acceso(newPass);
                    servicio.actualizarContraseña(user);
                    //----------------------------------------------------------
                    
                    // Se guarda en el bean la nueva clave del usuario
                    sesionActual.setAttribute("eLogin",
                    new BeanLogin(user.getId_usuario()+"", newPass));
                            
                    // Server envia mensaje a la pagina principal del usuario 
                    // enviando 1 siginific que usuario puede continuar
                    // realizando las funciones del sistema y la contraseña
                    // nueva ya quedo actualizada en el sistema
                    request.getSession().setAttribute("servletLogin", "1");
                    // Se envia la informacion de respuesta a la direccion
                    // del clase de usuario correspondiente tipo.jsp
                    dispatcher = request.getRequestDispatcher(destino);
                    dispatcher.forward(request, response);
                        
                    
                 // Falso Si la nueva contraseña es igual a la anterior se notificara
                 // el error al usuario y el proceso no podra realizarse
                }else if (newPass.equals(user.getClave_acceso())){
                    
                    // Server envia mensaje a la pagina principal del usuario  
                    // enviando 5 la pagina,s significando que las contraseña anterior y
                    // la nueva son iguales y debe elegir otra diferente
                    request.getSession().setAttribute("servletLogin", "5");
                    
                    // Se envia en el bean el mensaje de errror
                    mensajeAux="Ha ocurrido en error a la hora de actualizar la"
                                 + " contraseña debido a que es igual a la anterior!";
                    sesionActual.setAttribute("eLogin", new BeanLogin(mensajeAux));
                    // Se envia la informacion de respuesta a la direccion
                    // del clase de usuario correspondiente tipo.jsp
                    dispatcher = request.getRequestDispatcher(destino);
                    dispatcher.forward(request, response);
                }
            } catch (Exception ex) {
                String tipoExcepcion;
                tipoExcepcion = ex.getMessage();
                // Manejo de exceopciones en la operacion de cambio de contraseña
                switch (tipoExcepcion) {
                    case "14":
                        
                        // Servelet responde con un mensaje a la pagina para procesar el error
                        request.getSession().setAttribute("servletLogin", "5");
                        // Se envia en el bean el mensaje de errror
                        mensajeAux="Ha ocurrido en error al actualizar la contraseña!!";
                        sesionActual.setAttribute("eLogin", new BeanLogin(mensajeAux));
                    // Se envia la informacion de respuesta a la direccion
                    // del clase de usuario correspondiente tipo.jsp
                    dispatcher = request.getRequestDispatcher(destino);
                    dispatcher.forward(request, response);
                        break;
                    
                }// Cierra la validacion del switch de excepciones
            }// Cierra el cacth de excepciones
        }// Falso si los datos registrados en la clase son nulos se envia un mensaje de
        // error a la pagina del usuario
        else{
            // Servelet responde con un mensaje a la pagina para procesar el error
            request.getSession().setAttribute("servletLogin", "5");
            // Se envia en el bean el mensaje de errror
                    mensajeAux="Ha ocurrido en error datos recibidos son nulos o vacios!!";
                    sesionActual.setAttribute("eLogin", new BeanLogin(mensajeAux));
                    // Se envia la informacion de respuesta a la direccion
                    // del clase de usuario correspondiente tipo.jsp
                    dispatcher = request.getRequestDispatcher(destino);
                    dispatcher.forward(request, response);
        }

    }// Cierra el metodo general

    // Declaracion de clases de funciones de modelo.dao utilizados en la clase
    private final funcionesLogueo servicio = new funcionesLogueo();
    private final funcionesDeposito fD = new funcionesDeposito();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
