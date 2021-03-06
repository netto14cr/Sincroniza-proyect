package servicios;

import beans.BeanLogin;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.usuario;
import modelo.dao.funcionesFrontEnd.funcionesLogueo;

/*
 * ServletInicionSesion.java
 * Autores: Gabriel Barboza && Néstor Leiva
 * Descripcion: Clase java tipo servlet de inicio sesión de usuarios
 */
@WebServlet(
        name = "ServletInicioSesion",
        urlPatterns = {"/ServletInicioSesion", "/registro-usuario"}
)

public class ServletInicioSesion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("\n:::::::  SERVLET MENU INICIO     ::::::::");
        
        // Se define de que direccion viene el usaurio
        String destinoCajero,destinoCliente,mensajeAux, destinoOrigen;
        destinoCajero = "";destinoCliente = "";mensajeAux="";
        destinoCliente = "/WEB-INF/Banco/Vista/Cliente.jsp";
        destinoCajero = "/WEB-INF/Banco/Vista/Cajero.jsp";
        destinoOrigen="/index.jsp";
        
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());

        String id = request.getParameter("id");
        String passw = request.getParameter("password");
        RequestDispatcher dispatcher = null;
        // Se obtiene los valores guardados en el beanNueva Cuenta
        BeanLogin bLogin = (BeanLogin) sesionActual.getAttribute("eLogin");
        request.getSession().getAttribute("bLogin");

        if (id != null && passw != null) {
            try {
                System.out.println("USER: "+id+" ----- PASS: "+passw+"\n");
                if (servicio.verificarLogueo(checkId(id), passw)) {
                    System.out.println("ENTRA LOGUEO");
                    usuario user = servicio.obtenerUsuarioPorCedula(id);
//                    Condisional que verifica el tipo de usuario que inicia sesión
//                      Si es usuario es 0 es cliente

                     System.out.println("::: "+user.toString());
                    if (servicio.obtenerRolCliente(id) == 0) {

                        // SI EL USUARIO CLIENTE TIENE LA CLAVE VENCIDA
                        if (user.getClave_vencida() == 1) {
                            mensajeAux="Bienvenido " + id + " a Proyect Bank\nPor "
                                    + "ser la primera vez que inicia sesión su clave vencera hoy..\n"
                                    + "Por favor cambiarla, gracias!";
                            // Se guardan en el beans de iniciao de sesion datos importantes
                            // de identificacion, contraseña y mensaje del cliente
                            sesionActual.setAttribute("eLogin",new BeanLogin(id, passw, mensajeAux));
                            
                            // Server envia mensaje a la pagina principal del cliente 
                            // enviando 2 la pagina sabra que debe modificar la contraseña
                            request.getSession().setAttribute("servletLogin", "2");
                            dispatcher = request.getRequestDispatcher(destinoCliente);
                            dispatcher.forward(request, response);
                            
                         // FALSO SI EL USUARIO CLIENTE NO TIENE LA CLAVE VENCIDA
                        } else if (user.getClave_vencida() == 0) {
                            // Se guardan en el beans de iniciao de sesion datos importantes
                            // de identificacion, contraseña del cliente
                            
                            sesionActual.setAttribute("eLogin", new BeanLogin(id, passw));
                            // Server envia mensaje a la pagina principal del cliente 
                            // enviando 1 la pagina sabra que cliente no debe cambiar contraseña
                            request.getSession().setAttribute("servletLogin", "1");
                            dispatcher = request.getRequestDispatcher(destinoCliente);
                            dispatcher.forward(request, response);
                        }

//--------------- FALSO SI LA IDENTIFICACION INICIO SESION ES DE USUARIO CAJERO ------------
                    } else if (servicio.verificarLogueo(checkId(id), passw)) {

                            // SI EL USUARIO CAJERO TIENE LA CLAVE VENCIA
                        if (user.getClave_vencida() == 1) {
                            mensajeAux="Bienvenido " + id + " a Proyect Bank\nPor "
                                    + "ser la primera vez que inicia sesión su clave vencera hoy..\n"
                                    + "Por favor cambiarla, gracias!";
                            // Se guardan en el beans de iniciao de sesion datos importantes
                            // de identificacion, contraseña y mensaje del cajero
                            sesionActual.setAttribute("eLogin", new BeanLogin(id, passw, mensajeAux));
                            
                            // Server envia mensaje a la pagina principal del cliente 
                            // enviando 2 la pagina sabra que debe modificar la contraseña
                            request.getSession().setAttribute("servletLogin", "2");
                            dispatcher = request.getRequestDispatcher(destinoCajero);
                            dispatcher.forward(request, response);
                            
                       //   FALSO SI EL USUARIO CAJERO TIENE LA CLAVE VENCIA     
                        } else if (user.getClave_vencida() == 0) {
                            
                            // Se guardan en el beans de iniciao de sesion datos importantes
                            // de identificacion, contraseña del cajero
                            sesionActual.setAttribute("eLogin", new BeanLogin(id, passw));
                            // Server envia mensaje a la pagina principal del cajero 
                            // enviando 1 la pagina sabra que cajero no debe cambiar contraseña
                            request.getSession().setAttribute("servletLogin", "1");
                            dispatcher = request.getRequestDispatcher(destinoCajero);
                            dispatcher.forward(request, response);
                        }
                    }

//                    Falso si la cédula ingresada existe pero la contraseña no existe o 
//                    es incorrecta
                } else if (servicio.verificarExistenciaCedulaCliente(id)
                        && !servicio.verificarExistenciaCedulaCliente(id)) {

                    dispatcher = request.getRequestDispatcher(
                            "/WEB-INF/Banco/Vista/index.jsp");
                    dispatcher.forward(request, response);
                }

            } catch (Exception ex) {
                String aux;
                aux="";
                System.out.println("EXISTE UN ERROR!");
                switch (ex.getMessage()) {
                    
                    case "1":
                        aux="1";
                        break;
                    case "2":
                        aux="2";
                        break;
                    case "3":
                        aux="3";
                        break;
                }
                request.getSession().setAttribute("servletMsjError", aux);
                dispatcher = request.getRequestDispatcher(destinoOrigen);
                dispatcher.forward(request, response);
            }
        }// Se valida que se muestre un mensaje de error si la la identificacion o
        // la contraseña vienen vacios o nulos
        else if (id == null || id.isEmpty() || passw == null || passw.isEmpty()){
                request.getSession().setAttribute("servletMsjError", "4");
                dispatcher = request.getRequestDispatcher(destinoOrigen);
                dispatcher.forward(request, response);
        }
    }

//    Validacion para el ingreso de tipo de identificación
    private String checkId(String txt) {
        String r = txt;

        Pattern pat = Pattern.compile("([1-9,A])-?([0-9]{4})-?([0-9]{4})");
        Matcher m = pat.matcher(txt);
        if (m.find()) {
            r = String.format("%s%s%s", m.group(1), m.group(2), m.group(3));
        }

        return r;
    }
    private final funcionesLogueo servicio = new funcionesLogueo();

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
