package servicios;

import beans.BeanLogin;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.cliente;
import model.cuenta;
import model.usuario;
import modelo.dao.funcionesFrontEnd.funcionesDeposito;
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

        // Se define de que direccion viene el usaurio
        String destinoCajero = "";
        String destinoCliente = "";
        String mensajeAux="";
        destinoCliente = "/WEB-INF/Banco/Vista/Cliente.jsp";
        destinoCajero = "/WEB-INF/Banco/Vista/Cajero.jsp";
        
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
                if (servicio.verificarLogueo(checkId(id), passw)) {
                    
                    System.out.println("\n\nPASA EL LOGIN:::::::::\n\n");
                    
                    usuario user = servicio.obtenerUsuarioPorCedula(id);
//                    Condisional que verifica el tipo de usuario que inicia sesión
//                      Si es usuario es 0 es cliente
                    if (servicio.obtenerRolCliente(id) == 0) {
                        System.out.println("CLIENTEEEEE -----");

                        
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
                        System.out.println("\n\n-----CAJEROOOOO -----\n\n");


                            // SI EL USUARIO CAJERO TIENE LA CLAVE VENCIA
                        if (user.getClave_vencida() == 1) {
                            System.out.println("\n\n<<<<<<<< CLAVE VENCIDA >>>>>>\n\n");
                            
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
                            System.out.println("\n\n<<<<<<<< ENTRA DIRECTO >>>>>>\n\n");
                            
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

                    System.out.println("CONTRASEÑA INVALIDA -----");
                    dispatcher = request.getRequestDispatcher(
                            "/WEB-INF/Banco/Vista/index.jsp");
                    dispatcher.forward(request, response);
                }

            } catch (Exception ex) {
                String tipoExcepcion;
                tipoExcepcion = ex.getMessage();
                System.out.println("\n\n++++ERROR+++++"+tipoExcepcion);
                switch (tipoExcepcion) {
                    case "1":
                        System.out.println("\n <-- NO EXISTE EL USUARIO DIGITADO --->\n");
                        request.getSession().setAttribute("servletMsjError", "1");
                        dispatcher = request.getRequestDispatcher(
                                "/index.jsp");
                        dispatcher.forward(request, response);
                        break;
                    case "2":
                        System.out.println("\n<--- NO EXISTE CLIENTE EN EL SISTEMA --->\n");
                        request.getSession().setAttribute("servletMsjError", "2");
                        dispatcher = request.getRequestDispatcher(
                                "/index.jsp");
                        dispatcher.forward(request, response);
                        break;
                    case "3":
                        System.out.println("\n <--- LA CONTRASEÑA DIGITADA ES INVALIDA ---> ");
                        request.getSession().setAttribute("servletMsjError", "3");
                        dispatcher = request.getRequestDispatcher(
                                "/index.jsp");
                        dispatcher.forward(request, response);
                        break;

                }
            }
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
