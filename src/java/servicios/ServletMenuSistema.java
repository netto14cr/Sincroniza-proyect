package servicios;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.cuenta;
import model.favorita;
import modelo.dao.funcionesFrontEnd.funcionesConsultaCuentasMovimientos;
import modelo.dao.funcionesFrontEnd.funcionesLogueo;
import modelo.dao.funcionesFrontEnd.funcionesTrasfereciasCajas;

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
        String destino; destino = "";
        String dir; dir="";
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());
        RequestDispatcher dispatcher = null;

        String botonFormulario = "";
        try {
            botonFormulario = request.getParameter("opcionMenu");
            if (botonFormulario != null && !botonFormulario.isEmpty()) {

                // Swicht para tomar las opciones recibidas del formulario de botones
                // de nueva cuenta
                switch (botonFormulario) {
                    
                    
                    //------------------ Redireccionamiento del Cajero ----------------------- 

                    // Opción 1 - El usuario cajero desea ir a la pagina de 
                    // aperturaCuenta (NuevaCuenta)
                    case "1":
                        destino = "/WEB-INF/Banco/Vista/NuevaCuenta.jsp";
                        actulizarMensajeServidor(request, "servletMsjNuevaCuenta");
                        break;
                    // Opción 2 - El usuario cajero desea ir a la pagina deposito
                    case "2":
                        destino = "/WEB-INF/Banco/Vista/Deposito.jsp";
                        actulizarMensajeServidor(request, "servletMsjDeposito");
                        break;
                    // Opción 3 - El usuario cajero desea ir a la pagina retiros
                    case "3":
                        destino = "/WEB-INF/Banco/Vista/Retiros.jsp";
                        actulizarMensajeServidor(request, "servletMsjRetiros");
                        break;
                    // Opción 4 - El usuario cajero desea ir a la pagina acreditacion interes
                    case "4":
                        destino = "/WEB-INF/Banco/Vista/Cajero.jsp";
                        actulizarMensajeServidor(request, "servletMsjMenu");
                        break;
                    // Opción 5 - El usuario desea cerrar cesión y se devuelve al index
                    case "5":
                        destino = "/index.jsp";
                        actulizarMensajeServidor(request, "servletMsjMenu");
                        break;

                    // Opción 11 - El usuario cajero reliza un transferencia en cajas
                    case "11":
                        destino = "/WEB-INF/Banco/Vista/BusqCuentaTrans.jsp";

                        actulizarMensajeServidor(request, "servletMsjTCaja");
                        actulizarMensajeServidor(request, "servletMsjTCaja2");
                        break; 
                        
                    // Opción 11 - El usuario cajero reliza un transferencia en cajas
                    case "12":
                        destino = "/WEB-INF/Banco/Vista/AcreditarInteres.jsp";
                        actulizarMensajeServidor(request, "servletMsjInteres");
                        break; 
                        
                        
//        ---------------------------- Redireccionamiento del cliente -----------------------                       
                        
                    // Opción 6 - El usuario cliente desea consultar sus cuentas
                    case "6":

                        destino = "/WEB-INF/Banco/Vista/CuentasCliente.jsp";
                        actulizarMensajeServidor(request, "servletMsjMenu");
                        funcionesConsultaCuentasMovimientos fc = new funcionesConsultaCuentasMovimientos();
                        String cedu = (String) request.getSession().getAttribute("id");
                        request.getSession().setAttribute("listaCuentas", 
                                (List<cuenta>) fc.listarCuentasCliente(cedu));
                        break;

                    // Opción 7 - El usuario cliente desea consultar el saldo de la cuents
                    case "7":
//                        
                        destino = "/WEB-INF/Banco/Vista/Cliente.jsp";
                        actulizarMensajeServidor(request, "servletMsjMenu");
                        break;

                    // Opción 8 - El usuario cliente desea realizar transferencias
                    case "8":
                        destino = "/WEB-INF/Banco/Vista/TransferenciaClienteCuentasFavoritas.jsp";
                        funcionesConsultaCuentasMovimientos fcs = new funcionesConsultaCuentasMovimientos();
                        String cedus = (String) request.getSession().getAttribute("id");
                        System.out.println("CEDULA : " + cedus);
                        request.getSession().setAttribute("listaCuentas", (List<cuenta>)
                                fcs.listarCuentasCliente(cedus));
                        
                        funcionesTrasfereciasCajas ftc = new funcionesTrasfereciasCajas();
                        String ceduas = (String) request.getSession().getAttribute("id");
                        List<favorita> lis = ftc.listarCuentasFavoritasDeCliente(ceduas);
                        request.getSession().setAttribute("listaFavoritas", lis);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);

                        break;

                    // Opción 9 - El usuario cliente desea consultar sus movimientos bancarios
                    case "9":
//                      
                        destino = "/WEB-INF/Banco/Vista/Cliente.jsp";
                        //request.getSession().setAttribute("servletMsjMenu", null);
                        actulizarMensajeServidor(request, "servletMsjMenu");
                        break;

                    // Opción 10 - El usuario cliente desea afiliar una cuenta a favoritos
                    case "10":
                        destino = "/WEB-INF/Banco/Vista/SolicitarCuentaVinculacion.jsp";
                        actulizarMensajeServidor(request, "servletMsjMenu");
                        break;
                }
            }

            // Si se llama el servidor se actualiza 
            observer(request, dispatcher, response, destino);
            }
        }
         catch (Exception ex) {

            System.out.println("Error : " + ex.getMessage());
        }

    }



    // Declaracion de funciones implementada por el servlet

    private final funcionesLogueo servicio = new funcionesLogueo();
    
    // Metodo que se encarga de actualizar las acciones de servidor - envio y respuesta
    private void observer(HttpServletRequest request, RequestDispatcher dispatcher,
            HttpServletResponse response, String destino) 
            throws ServletException, IOException{
        
        dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);
    }
    
    // Actualiza el mensaje recibido por el servidor a nulo (lo resetea)
    private void actulizarMensajeServidor(HttpServletRequest request, String dir){
        request.getSession().setAttribute(dir, null);
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


}

