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
import model.favorita;
import modelo.dao.funcionesFrontEnd.funcionesVinculacionCuentas;

/**
 *
 * @author gabri
 */
public class ServletVerificacionCuenta extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */

            String destino="";
            String RealizarVinculacion=request.getParameter("RealizarVinculación");
            String volverMenu=request.getParameter("volverMenu");
            String volverEscogerCuenta=request.getParameter("volverEscogerCuenta");
            if(volverMenu!=null && volverMenu.equals("1"))
            {
                destino= "/WEB-INF/Banco/Vista/Cliente.jsp";
                RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
            dispatcher.forward(request, response);
            }else if(volverEscogerCuenta!=null && volverEscogerCuenta.equals("1"))
            {
                destino= "/WEB-INF/Banco/Vista/SolicitarCuentaVinculacion.jsp";
            //request.getSession().setAttribute("servletMsjMenu", null);
            RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
            dispatcher.forward(request, response);
            }else if(RealizarVinculacion!=null &&  RealizarVinculacion.equals("1"))
            {// se realiza la vinculacion
                beans.BeanLogin bl = (beans.BeanLogin) request.getSession().getAttribute("eLogin");
                funcionesVinculacionCuentas fv=new funcionesVinculacionCuentas();
                String numCuenta=request.getParameter("numeroCuenta");
                int num=Integer.parseInt(numCuenta);
                if(fv.realizarVinculacion(new favorita(bl.geteIdentificacion(),num)))
                {
                    request.getSession().setAttribute("Hecho","true");
                }else
                {
                    request.getSession().setAttribute("Hecho","false");
                }
                destino= "/WEB-INF/Banco/Vista/MostrarDatosVinculacion.jsp";
            //request.getSession().setAttribute("servletMsjMenu", null);
           request.setAttribute("numeroCuenta",request.getParameter("numeroCuenta"));
            RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
            dispatcher.forward(request, response);
            }else if(RealizarVinculacion!=null &&  RealizarVinculacion.equals("2"))
            {
                destino= "/WEB-INF/Banco/Vista/SolicitarCuentaVinculacion.jsp";
            //request.getSession().setAttribute("servletMsjMenu", null);
            RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
            dispatcher.forward(request, response);
            }
            else
            {
                destino= "/WEB-INF/Banco/Vista/MostrarDatosVinculacion.jsp";
            //request.getSession().setAttribute("servletMsjMenu", null);
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
