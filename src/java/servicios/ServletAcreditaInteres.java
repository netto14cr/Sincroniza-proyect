  /*
 * ServletAcreditaInteres.java
 * Autores: Gabriel Barboza && Néstor Leiva
 * Descripción: Clase java tipo servlet de acreditacion de interes del usuario cajero 
 */
package servicios;

import beans.BeanInteres;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.movimiento;
import modelo.dao.funcionesFrontEnd.funcionesAcreditacionIntereses;
import modelo.dao.funcionesFrontEnd.funcionesConsultaCuentasMovimientos;

 
@WebServlet(
        name = "ServletAcreditaInteres",
        urlPatterns = {"/ServletAcreditaInteres", "/acredita-interes", "/regreso-interes"}
)

public class ServletAcreditaInteres extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        RequestDispatcher dispatcher = null;
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());

        System.out.println("\n:::::::  SERVLET INTERES   ::::::::");
        try {
            // Se obtiene los valores guardados en el beanNueva Cuenta
            BeanInteres bInteres = (BeanInteres) sesionActual.getAttribute("descInteres");
            request.getSession().getAttribute("bInteres");
            String destino; destino = "/WEB-INF/Banco/Vista/AcreditarInteres.jsp";
            String opcionFormulario; opcionFormulario = "";
            opcionFormulario = request.getParameter("formAcredita");
            
            
            if (opcionFormulario != null && !opcionFormulario.isEmpty()) {

                // Swicht para tomar las opciones recibidas del formulario de botones
                // de nueva cuenta
                switch (opcionFormulario) {
                    
//                    Si el valor del formulario es uno indica el usuario cajero 
//                    desea autoriza la acreditacion de interes
                    case "1":
                        if(!bInteres.getOperacionRealizada()){
                           // Se llama al metodo que genera los interese en las cuentas
                            fAcredInt.generarAcreditacionDeIntereses();
                        
                            List<movimiento> list = null;
                            // Se listan la informacion de los movimientos registrados 
                            // de tipo Acreditacion de intereses
                            list=fMovCuenta.listarMovimientosTipoUltimos(
                                    "Deposito: Acreditación de Intereses");
                        
                            // Se pasa la informacion al bean de intereses
                            bInteres.setLista(list);
                        
                            // Se establece un mensaje a mostrar la usuario de operecion realizada
                            bInteres.seteMensaje("La operación de acreditar intereses "
                                + "ha sido realizada exitosamente !");
                            bInteres.seteOperacionRealizada(true);
                        
                            // Se pasa los datos al bean para que la pagina conozca los datos
                            sesionActual.setAttribute("descInteres",
                            new BeanInteres(bInteres.getLista(),
                            bInteres.geteMensaje(), bInteres.getOperacionRealizada()));
                        
                            // Se define un mensaje a enviar del servidor a la pagina para
                            // que asi pueda actualizar el estado visualmente
                            request.getSession().setAttribute("servletMsjInteres", "READY");
                        }
                        break;
                    // Si es opcion dos significa que el usuario desea volver 
//                        a menu de inicio cajero
                    case "2":
                        // SE pasa la direccion de menu inicial
                        destino = "/WEB-INF/Banco/Vista/Cajero.jsp";
                        // El mensaje del servidor se resetea al inicial
                        request.getSession().setAttribute("servletMsjInteres", null);
                        bInteres.seteOperacionRealizada(false);
                        bInteres.seteMensaje("");
                        sesionActual.setAttribute("descInteres",
                        new BeanInteres(bInteres.geteMensaje(), bInteres.getOperacionRealizada()));
                        
                        break;
                    case "3":
                        destino = "/WEB-INF/Banco/Vista/AcreditarInteres.jsp";
                        bInteres.seteOperacionRealizada(false);
                        bInteres.seteMensaje("");
                        sesionActual.setAttribute("descInteres",
                        new BeanInteres(bInteres.geteMensaje(), bInteres.getOperacionRealizada()));
                        request.getSession().setAttribute("servletMsjInteres", null);
                        break;

                        
                }
                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
            }
       
        } catch (Exception ex) {
            
            switch (ex.getMessage()) {
                case "6":
                    break;
            }
        }
        
        
    }
    
    
    private final funcionesAcreditacionIntereses fAcredInt = new funcionesAcreditacionIntereses();
    private final   funcionesConsultaCuentasMovimientos fMovCuenta=new funcionesConsultaCuentasMovimientos();
    
    

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
