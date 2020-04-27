
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
 * ServletTransferenciaClienteCuentaFavo.java
 * @Autores: Gabriel Barboza && Néstor Leiva
 * Descripción: Este bean se utiliza para manejar la informacion en el servidor
 * de transferencia de cuenta a de un cliente a una que tenga como favorita
 */
public class ServletTransferenciaClienteCuentaFavo extends HttpServlet {

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

            String cuen1 = request.getParameter("cuenta1");
            String cuen2 = request.getParameter("cuenta2");
            String destino = "";

            if (cuen1 != null && cuen2 != null && cuen1.equals(cuen2) == false) {// nos vamos a confirmar las dos cuentas
                int numCuen = Integer.parseInt((String) request.getParameter("cuenta1"));
                servicioCuenta sc = new servicioCuenta();
                double saldoDisponible = sc.obtenerCuenta(numCuen).get().getSaldo_actual();
                String moneda=sc.obtenerCuenta(numCuen).get().getMoneda_nombre();
                destino = "/WEB-INF/Banco/Vista/VerificacionCuenta1Cuenta2.jsp";
                request.getSession().setAttribute("saldo", String.valueOf(saldoDisponible));
                request.getSession().setAttribute("moneda",moneda);
                request.getSession().setAttribute("cuenta1", cuen1);
                request.getSession().setAttribute("cuenta2", cuen2);
                RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
            } else {

                destino = "/WEB-INF/Banco/Vista/TransferenciaClienteCuentasFavoritas.jsp";
                RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
            }
        }catch(Exception ex)
        {
            System.out.println("Error : "+ex.getMessage());
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

