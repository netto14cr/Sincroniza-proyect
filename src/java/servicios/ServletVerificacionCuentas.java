/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.dao.servicioCuenta;

/**
 *
 * @author gabri
 */
public class ServletVerificacionCuentas extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String opcion = request.getParameter("opcion");
            String destino = "";
            RequestDispatcher dispatcher;
            String errorMonto = "";
            String monto = request.getParameter("monto");
            request.setAttribute("monto",monto);
            int numCuen ;
            servicioCuenta sc = new servicioCuenta();
            double saldoDisponible ;
            String volverTransferencia=request.getParameter("volverTransferencia");
            String volverMenu=request.getParameter("volverMenu");
            if(volverTransferencia!=null && volverTransferencia.equals("1"))
            {
                destino = "/WEB-INF/Banco/Vista/TransferenciaClienteCuentasFavoritas.jsp";
                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
            }else if (volverMenu!=null && volverMenu.equals("1"))
            {
                destino = "/WEB-INF/Banco/Vista/Cliente.jsp";
                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
            }else{
            if (opcion != null && opcion.equals("1")) {
                // solicitamos el monto para la trasaccion pero en la misma pagina
                destino = "/WEB-INF/Banco/Vista/VerificacionCuenta1Cuenta2.jsp";
                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
                request.setAttribute("opcion", opcion);
            } else if (opcion != null && opcion.equals("0")) {//regresamos a jsp Transferencias
                destino = "/WEB-INF/Banco/Vista/TransferenciaClienteCuentasFavoritas.jsp";
                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
                request.setAttribute("opcion", opcion);
            } else if (monto != null && isNumeric(monto)) {
                
                double mont = Double.parseDouble(monto);
                numCuen = Integer.parseInt((String) request.getSession().getAttribute("cuenta1"));

                saldoDisponible = sc.obtenerCuenta(numCuen).get().getSaldo_actual();
                if (mont > saldoDisponible || mont<=0) {
                    errorMonto = "1";
                    request.setAttribute("errorMonto", errorMonto);
                    destino = "/WEB-INF/Banco/Vista/VerificacionCuenta1Cuenta2.jsp";
                    dispatcher = request.getRequestDispatcher(destino);
                    dispatcher.forward(request, response);
                    request.setAttribute("opcion", opcion);
                }  else {

                    request.setAttribute("errorMonto", errorMonto);
                    destino = "/WEB-INF/Banco/Vista/MostrarTrasnferenciaRealizada.jsp";
                    dispatcher = request.getRequestDispatcher(destino);
                    dispatcher.forward(request, response);
                    request.setAttribute("opcion", opcion);
                }
            }else
            {
                 errorMonto = "1";
                    request.setAttribute("errorMonto", errorMonto);
                 // solicitamos el monto para la trasaccion pero en la misma pagina
                destino = "/WEB-INF/Banco/Vista/VerificacionCuenta1Cuenta2.jsp";
                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
                request.setAttribute("opcion", opcion);
            }
            }
        }
    }
    private static boolean isNumeric(String cadena){
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
}

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
