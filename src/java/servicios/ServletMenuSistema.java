package servicios;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.cuenta;
import modelo.dao.funcionesFrontEnd.funcionesConsultaCuentasMovimientos;
import modelo.dao.funcionesFrontEnd.funcionesLogueo;

/*
 * ServletMenuSistema.java
 * Autores: Gabriel Barboza && Néstor Leiva
 * Descripcion: Clase java tipo servlet de redireccion y acciones para opciones
 * menu en la navegacion del sistema tanto del cliente como Cajero
 */
@WebServlet(
        name = "ServletMenuSistema",
        urlPatterns = {"/ServletMenuSistema", "/menu-Navegacion"}
)

public class ServletMenuSistema extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("\n:::::::  SERVLET MENU DE REDIRECCION SISTEMA     ::::::::");
        
        // Se define de que direccion viene el usaurio
        String destino = "";
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());
        RequestDispatcher dispatcher = null;
        
        String botonFormulario = "";
        try{
            botonFormulario = request.getParameter("opcionMenu");
            if (botonFormulario != null && !botonFormulario.isEmpty()) {
                
                destino = "/WEB-INF/Banco/Vista/Cajero.jsp";
                // Swicht para tomar las opciones recibidas del formulario de botones
                // de nueva cuenta
                switch (botonFormulario) {
                    
                    // Opción 1 - El usuario cajero desea ir a la pagina de 
                    // aperturaCuenta (NuevaCuenta)
                    case "1":
                        destino = "/WEB-INF/Banco/Vista/NuevaCuenta.jsp";
                        dispatcher = request.getRequestDispatcher(destino);
                        request.getSession().setAttribute("servletMsjNuevaCuenta", null);
                        dispatcher.forward(request, response);
                        break;
                    // Opción 2 - El usuario cajero desea ir a la pagina deposito
                    case "2":
                        destino = "/WEB-INF/Banco/Vista/Deposito.jsp";
                        request.getSession().setAttribute("servletMsjDeposito", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                      // Opción 3 - El usuario cajero desea ir a la pagina retiros
                    case "3":
                        destino = "/WEB-INF/Banco/Vista/Retiros.jsp";
                        request.getSession().setAttribute("servletMsjRetiros", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                      // Opción 4 - El usuario cajero desea ir a la pagina acreditacion interes
                    case "4":
                        destino = "/WEB-INF/Banco/Vista/Cajero.jsp";
                        request.getSession().setAttribute("servletMsjMenu", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                      // Opción 5 - El usuario desea cerrar cesión y se devuelve al index
                    case "5":
                        destino = "/index.jsp";
                        request.getSession().setAttribute("servletMsjMenu", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                        
                    // Opción 11 - El usuario cajero reliza un transferencia en cajas
                    case "11":
                        destino = "/WEB-INF/Banco/Vista/BusqCuentaTrans.jsp";
                        request.getSession().setAttribute("servletMsjTCaja", null);
                        request.getSession().setAttribute("servletMsjTCaja2", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;    
                        
                    // Opción 6 - El usuario cliente desea consultar sus cuentas
                    case "6":
                        destino = "/WEB-INF/Banco/Vista/CuentasCliente.jsp";
                        request.getSession().setAttribute("servletMsjMenu", null);
                        funcionesConsultaCuentasMovimientos fc=new funcionesConsultaCuentasMovimientos();
                        String cedu=(String) request.getSession().getAttribute("id");
                             request.getSession().setAttribute("listaCuentas",
                                     (List<cuenta>)fc.listarCuentasCliente(cedu));
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                        
                    // Opción 7 - El usuario cliente desea consultar el saldo de la cuents
                    case "7":
//                        destino = "/WEB-INF/Banco/Vista/Saldo.jsp";
                        destino = "/WEB-INF/Banco/Vista/Cliente.jsp";
                        request.getSession().setAttribute("servletMsjMenu", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                        
                    // Opción 8 - El usuario cliente desea realizar transferencias
                    case "8":
//                        destino = "/WEB-INF/Banco/Vista/Transferencias.jsp";
                        destino = "/WEB-INF/Banco/Vista/Cliente.jsp";
                        request.getSession().setAttribute("servletMsjMenu", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                        
                    // Opción 9 - El usuario cliente desea consultar sus movimientos bancarios
                    case "9":
//                        destino = "/WEB-INF/Banco/Vista/MovBancario.jsp";
                        destino = "/WEB-INF/Banco/Vista/Cliente.jsp";
                        request.getSession().setAttribute("servletMsjMenu", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                        
                    // Opción 10 - El usuario cliente desea afiliar una cuenta a favoritos
                    case "10":
                        destino = "/WEB-INF/Banco/Vista/SolicitarCuentaVinculacion.jsp";
                        //request.getSession().setAttribute("servletMsjMenu", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
            }
            }
        } catch (NumberFormatException ex) {
        } catch (Exception ex) {
            Logger.getLogger(ServletMenuSistema.class.getName()).log(Level.SEVERE, null, ex);
        }

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
