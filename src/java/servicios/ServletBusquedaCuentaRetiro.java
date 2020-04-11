package servicios;
import beans.BeanRetiro;
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
import model.cuenta;
import modelo.dao.funcionesFrontEnd.funcionesDeposito;
import modelo.dao.funcionesFrontEnd.funcionesLogueo;

/*
 * ServletBusquedaCuentaRetiro.java
 * Autores: Gabriel Barboza && Néstor Leiva
 * Descripcion: Clase java tipo servlet de ibusqueda de cuentas para realizar retiros - cajero
 */
@WebServlet(
        name = "ServletBusquedaCuentaRetiro",
        urlPatterns = {"/ServletBusquedaCuentaRetiro", "/busCuentaRetiro-cajero",
            "/regresar-Retiro"}
)

public class ServletBusquedaCuentaRetiro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\n:::::::  SERVLET BUSQUEDA CUENTAS RETIRO    ::::::::");
        // Se define de que direccion viene el usaurio
        String destino="";
        destino = "/WEB-INF/Banco/Vista/Retiros.jsp";
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());
        
        String tipoBusquedaCuenta = request.getParameter("tipoBusquedaCuenta");
        
        String id = request.getParameter("nCuentaDeposito");
        RequestDispatcher dispatcher = null;
        
        // Se obtiene los valores guardados en el beanNueva Cuenta
        BeanRetiro bRet = (BeanRetiro) sesionActual.getAttribute("descripRetiro");
        request.getSession().getAttribute("bRet");
        
        verificaOpcionesFormularioRegreso( dispatcher, request,response, destino);
        // Verifica si la identificacion no es nula o vacia y de obtener
        // para proceder seguidamente a verificar la cedula o numero de 
        // cuenta y su esta exsite en el sistema
        
        if (id != null && !id.isEmpty()) {
            
            try {
                // Verificamos primero que la cédula del usuario exista en el 
                //sistema

                bRet.seteTipoBusqueda(tipoBusquedaCuenta);
                if (bRet.geteTipoBusqueda()!=null)
                System.out.println("TPI BUSQUEDA"+bRet.geteTipoBusqueda());
                //----------- BUSQUEDA POR TIPO NUEMERO DE CÉDULA ------------------------
                
                // Si el tipo de busqueda seleccionado es por numero de cédula
                if (tipoBusquedaCuenta.equals("nCedula")) {
                    // Se verifica si la cedula ingresada existe en el sistema
                    // si se cuemple entra y manda la respuesta de que puede continuar
                    // con el deposito a esta cuenta
                    
                    
                    if (servicioLogin.verificarExistenciaCedulaCliente(checkId(id))) {
                        // Si usuario existe se procede a buscarla cuentas asociadas
                        // Servlet respode que la operacion es uno y se toma como 
                        // correcta.
                        
                        // Se declara una lista de tipo de informacion de cuenta
                        List<cuenta> listaCuentas;
                        
                        // Se cargan todas las cuentas que posea el numero de cedula
                        // que se indico buscar
                        listaCuentas = servicioDeposito.listarCuentasCliente(id);
                        
                       // Se pasan los valores a la clase bean para el manejo de informacion
                       // entre el servidor y manejo de la clase Deposito.jsp
                        sesionActual.setAttribute("descripRetiro", new BeanRetiro(id, 
                                tipoBusquedaCuenta,listaCuentas));
                        
                        // Se define que el servidor respondera con un 1 a depisto.jsp
                        // significando que la busqueda de la cedula ha sido exitoso
                        // y que se puede continuar con las acciones de deposito
                        request.getSession().setAttribute("servletMsjRetiro", "1");
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                    }
                    
                    //----------- BUSQUEDA POR TIPO NUEMERO DE CUENTA -------------------------
                    
//                Falso si el tipo de busqueda seleccionado es por numero de cuenta
                } else if (tipoBusquedaCuenta.equals("nCuenta")) {
                    int numeroCuenta; numeroCuenta=0;
                    numeroCuenta = Integer.parseInt(id);
                    
//                    Se verifica SI que el numero de cuenta a la que se quiere buscar
//                    para realizar el deposito exista en el sistema con un usuario
//                    registrado 
                    if (servicioDeposito.verificarExistenciaNumCuenta(numeroCuenta)) {
                              
                       // Se pasan los valores a la clase bean para el manejo de informacion
                       // entre el servidor y manejo de la clase Deposito.jsp
                        bRet.seteMensaje("");
                        bRet.seteNumCuenta(id); // Se pasa el numero de cuenta
                        bRet.seteTipoBusqueda(tipoBusquedaCuenta);
                        sesionActual.setAttribute("descripRetiro", 
                        new BeanRetiro(bRet.geteMensaje(),bRet.geteNumCuenta(), 
                                bRet.geteTipoBusqueda()));
                        
                        // Si el numero de cuenta existe Servlet respode que la 
                        // operacion es uno y se toma como correcta.
                        request.getSession().setAttribute("servletMsjRetiro", "2");
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                    }
                }
            } catch (Exception ex) {
                
                switch (ex.getMessage()) {
                   
                    // Si la exceppcion es de tipo #2 -- Significa que a la hora de 
                    // realizar la busqueda de la cedula no se obtubo una respuesta
                    // positiva por parte de la busqueda en la base de datos y que
                    // está es equivocada y no existe.
                    case "2":
                        bRet.seteMensaje("Error: la identificación "+id+" no pertenece"
                                + "aún usurio en nuestro sistema!");
                        sesionActual.setAttribute("descripRetiro", 
                        new BeanRetiro(bRet.geteMensaje()));
                        request.getSession().setAttribute("servletMsjRetiro", "ERROR_NO_CUENTA");
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                        
                    // Si la exceppcion es de tipo #4 -- Significa que a la hora de 
                    // realizar la busqueda de la cuenta no se obtubo una respuesta
                    // positiva por parte de la busqueda en la base de datos y que
                    // está es equivocada y no existe.
                    case "4":
                        bRet.seteMensaje("Error: El numero de cuenta "+id+" no pertenece"
                                + "aun usurio en nuestro sistema!");
                        sesionActual.setAttribute("descripRetiro", 
                        new BeanRetiro(bRet.geteMensaje()));
                        
                        request.getSession().setAttribute("servletMsjRetiro", "ERROR_NO_CUENTA");
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                }
            }
        }else{
            request.getSession().setAttribute("servletMsjRetiro", null);
                        dispatcher = request.getRequestDispatcher(destino);
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

    private final funcionesDeposito servicioDeposito = new funcionesDeposito();
    private final funcionesLogueo servicioLogin = new funcionesLogueo();

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
     * Returns a short descripRetirotion of the servlet.
     *
     * @return a String containing servlet descripRetirotion
     */
    @Override
    public String getServletInfo() {
        return "Short descripRetirotion";
    }// </editor-fold>

    
    
    
    // Metodo que verifica si el usuario selecciona algun boton de navegacion
    // mostrados para cancelar las acciones de deposito
    
    protected void verificaOpcionesFormularioRegreso(RequestDispatcher dispatcher,
            HttpServletRequest request, HttpServletResponse response, String destino) 
            throws ServletException, IOException{
        try {
            
            String botonFormulario = "";
            botonFormulario = request.getParameter("regresoOpcion");
            if (botonFormulario != null && !botonFormulario.isEmpty()) {

                // Swicht para tomar las opciones recibidas del formulario de botones
                // de nueva cuenta
                switch (botonFormulario) {
                    
                    // Opción 1 - El usuario cajero desea regresar al menu principal
                    case "1":
                        destino = "/WEB-INF/Banco/Vista/Cajero.jsp";
                        request.getSession().setAttribute("servletMsjRetiro", null);
                        request.getSession().setAttribute("servletMsjRetiro2", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                    // Opción 2 - Ocurrio un error en la creacion de la cuenta y el
                   //  usuario desaa intentar de nuevo registar una nueva
                    case "2":
                        destino = "/WEB-INF/Banco/Vista/Retiros.jsp";
                        request.getSession().setAttribute("servletMsjRetiro", null);
                        request.getSession().setAttribute("servletMsjRetiro2", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                      // Opción 3 - El usuario cajero desea registar una nueva cuenta
                    case "3":
                        destino = "/WEB-INF/Banco/Vista/Retiros.jsp";
                        request.getSession().setAttribute("servletMsjRetiro", null);
                        request.getSession().setAttribute("servletMsjRetiro2", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;

                }
            }
        } catch (NumberFormatException ex) {
        }
        
    }
    
    
}
