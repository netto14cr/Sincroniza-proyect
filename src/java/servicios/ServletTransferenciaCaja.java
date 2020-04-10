package servicios;
import beans.BeanRetiro;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.movimiento;
import modelo.dao.funcionesFrontEnd.funcionesDeposito;
import modelo.dao.funcionesFrontEnd.funcionesLogueo;
import modelo.dao.funcionesFrontEnd.funcionesRetiro;

/*
 * ServletInicionSesion.java
 * Autores: Gabriel Barboza && Néstor Leiva
 * Descripcion: Clase java tipo servlet de Trnasferencia en cajas del usuario cajero 
 */
@WebServlet(
        name = "ServletTransferenciaCaja",
        urlPatterns = {"/ServletTransferenciaCaja", "algo", "algo"}
)

public class ServletTransferenciaCaja extends HttpServlet {

    protected void processRequest(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException
             {

                 
        System.out.println("\n:::::::  SERVLET TRANSFERENCIA BANCARIAS CAJAS    ::::::::");
                 
        // Se define de que direccion viene el usaurio
        String destino = "";
        destino = "/WEB-INF/Banco/Vista/TranferenciaCaja.jsp";
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());

        RequestDispatcher dispatcher = null;
        // Se obtiene los valores guardados en el beanNueva Cuenta
        BeanRetiro bTrans = (BeanRetiro) sesionActual.getAttribute("descripTransCaja");
        request.getSession().getAttribute("bTrans");

                    dispatcher = request.getRequestDispatcher(destino);
                    dispatcher.forward(request, response);
            }
       
        

    private final funcionesDeposito fDeposito = new funcionesDeposito();
    private final funcionesLogueo fLogin = new funcionesLogueo();
    private final movimiento mov = new movimiento();
    private final funcionesRetiro fRetiro = new funcionesRetiro();

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
    }

 
  
