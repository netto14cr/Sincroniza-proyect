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
import model.movimiento;
import modelo.dao.funcionesFrontEnd.funcionesTransferenciasRemotas;

/**
 *
 * @author gabri
 */
public class ServletMostrarTransferencia extends HttpServlet {

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

            String aceptar = request.getParameter("aceptar");
            String cancelar = request.getParameter("cancelar");
            RequestDispatcher dispatcher;
            String destino = "";
            String volverTransferencia = request.getParameter("volverTransferencia");
            String volverMenu = request.getParameter("volverMenu");
            String monto = request.getParameter("monto");
            request.setAttribute("monto", monto);
            request.setAttribute("aceptar",aceptar);
            request.setAttribute("cancelar",cancelar);
            if (volverTransferencia != null && volverTransferencia.equals("1")) {
                destino = "/WEB-INF/Banco/Vista/TransferenciaClienteCuentasFavoritas.jsp";
                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
            } else if (volverMenu != null && volverMenu.equals("1")) {
                destino = "/WEB-INF/Banco/Vista/Cliente.jsp";
                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
            } else if (aceptar != null && aceptar.equals("1")) {
                destino = "/WEB-INF/Banco/Vista/MostrarTrasnferenciaRealizada.jsp";
                
                funcionesTransferenciasRemotas fr=new funcionesTransferenciasRemotas();
                int num1=Integer.parseInt( String.valueOf(request.getSession().getAttribute("cuenta1")));
                int num2=Integer.parseInt( String.valueOf(request.getSession().getAttribute("cuenta2")));
                movimiento mov=new movimiento();
                double mon=Double.parseDouble(monto);
                mov.setMonto(mon);
                mov.setCuenta_num_cuenta(num1);
                mov.setDetalle("DETALLE DEFECTO");
                String ced=(String) request.getSession().getAttribute("id");
                boolean realizado=fr.realizarTrasnferencia(num1,num2, mov,ced );
                //realizamos la trasaccion
                request.getSession().setAttribute("realizado",String.valueOf(realizado));
                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
            } else if (cancelar != null && cancelar.equals("1")) {
                destino = "/WEB-INF/Banco/Vista/MostrarTrasnferenciaRealizada.jsp";
                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
            }

        }catch(Exception ex)
            
        {
            System.out.print("error : "+ex.getMessage());
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
