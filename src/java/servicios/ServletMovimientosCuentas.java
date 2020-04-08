/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.movimiento;
import modelo.dao.funcionesFrontEnd.funcionesConsultaCuentasMovimientos;

/**
 *
 * @author gabri
 */
public class ServletMovimientosCuentas extends HttpServlet {

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
            String destino ="";
            String volver=request.getParameter("volver");
            String volverOpciones=request.getParameter("volverOpciones");
            if(volver != null && volver.equals("1"))
            {
                destino= "/WEB-INF/Banco/Vista/CuentasCliente.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
            dispatcher.forward(request, response);
            }
            if(volverOpciones!=null && volverOpciones.equals("1"))
            {
                destino= "/WEB-INF/Banco/Vista/Cliente.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
            dispatcher.forward(request, response);
            }
            else
            {
            destino= "/WEB-INF/Banco/Vista/MovimientosCuenta.jsp";
            String fech1="",fech2="",numCuenta="";
            numCuenta=request.getParameter("numeroCuenta");
            fech1=request.getParameter("fech1");
            fech2=request.getParameter("fech2");
            
            if(fech1.equals("")==false || fech2.equals("")==false)
            {//entonces ordenamos  la lista diferente
                funcionesConsultaCuentasMovimientos fc=new funcionesConsultaCuentasMovimientos();
                
                List<movimiento> lis=(List<movimiento>)fc.listarMovimientosCuentaPorFecha(Integer.parseInt(numCuenta), fech1, fech2);
                
                request.getSession().setAttribute("listaMovimientos", lis);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
            dispatcher.forward(request, response);
            }
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
