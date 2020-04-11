package servicios;
import beans.BeanTransCaja;
import beans.BeanTransCaja2;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.movimiento;
import modelo.dao.funcionesFrontEnd.funcionesTrasfereciasCajas;

/*
 * ServletTransferenciaCaja.java
 * Autores: Gabriel Barboza && Néstor Leiva
 * Descripcion: Clase java tipo servlet de Trnasferencia en cajas del usuario cajero 
 */
@WebServlet(
        name = "ServletTransferenciaCaja ",
        urlPatterns = {"/ServletTransferenciaCaja ", "/transferencia-Caja"}
)

public class ServletTransferenciaCaja extends HttpServlet {

    protected void processRequest(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException
             {

                 
        System.out.println("\n:::::::  SERVLET TRANSFERENCIA - EFECTIVO    ::::::::");
                 
        // Se define de que direccion viene el usaurio
        String destino = "";
        destino = "/WEB-INF/Banco/Vista/TransferenciaCaja.jsp";
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());

        RequestDispatcher dispatcher = null;

        String montoDep,detalleDep;
        montoDep="";detalleDep="";
                
        montoDep = request.getParameter("montoDeposito");
        detalleDep = request.getParameter("detalleDep");
        
        // Se define el bean para obtener datos, etiqueda para la clase jsp y
        // su respuesta de envio de mensajes
        BeanTransCaja bTCaja = (BeanTransCaja) sesionActual.getAttribute("descTCaja");
        request.getSession().getAttribute("bTCaja");
        BeanTransCaja2 bTCaja2 = (BeanTransCaja2) sesionActual.getAttribute("descTCaja2");
        request.getSession().getAttribute("bTCaja2");
        
        try {
            // Verifica que la operacion no se haya realizado antes, esto es encaso de refrescar
            // la pagina con F5
            
                    System.out.println("\n::::::::::::: "+bTCaja.getCuentaSeleccionada());
            if (bTCaja.getCuentaSeleccionada()){
            
                // verifica que el monto de deposito no sea nulo o vacio
                if (montoDep!=null && !montoDep.isEmpty()){
                    System.out.println("MONTO !NULL");
                    
                    
                    System.out.println("\nMONTO____: "+montoDep);
                    System.out.println("\nDETAL____: "+detalleDep);
                    System.out.println("\nCue 1____: "+bTCaja.geteNumCuenta());
                    System.out.println("\nCue 2____: "+bTCaja2.geteNumCuenta());
                    // Se setean los valores de movimiento
                    mov.setMonto(Double.parseDouble(montoDep));
                    mov.setDetalle(detalleDep);
                    mov.setCuenta_num_cuenta(Integer.parseInt(bTCaja.geteNumCuenta()));
           
                    
                    
                    // Se realiza la transferencia en el sistema sino hay ningun incombeniente
                    // por que la funcion es capaz de verificar si los datos proporcionados
                    // son correctos y si la cuenta de donde se traslada la plata origen
                    // cuenta con recursos suficientes para realizar la transferencia
                    fTC.realizarTrasnferencia(Integer.parseInt(bTCaja.geteNumCuenta()),
                    Integer.parseInt(bTCaja2.geteNumCuenta()), mov, bTCaja.geteCedula());
                    System.out.println("TRASFERENCIA COMPLETA!");
                    // Se envia un mensaje de respuesta a la pagina de trasferecnias jsp
                    request.getSession().setAttribute("servletMsjTCaja3", "READY");
                    // Se guardan y se envian los datos de la trasferencia realizada
                    bTCaja.seteDetalleDeposito(detalleDep);
                    bTCaja.seteMontoDeposito(montoDep);
                    bTCaja.setCuentaSeleccionada(false);
                    bTCaja.seteMensaje("La trasferencia se ha realizado exitosamente!");
                    sesionActual.setAttribute("descTCaja", 
                        new BeanTransCaja(bTCaja.geteNumCuenta(),bTCaja.geteCedula(),
                       bTCaja.geteMontoDeposito(), bTCaja.geteDetalleDeposito(),
                       bTCaja2.geteNumCuenta(), bTCaja.geteMensaje(), bTCaja.getCuentaSeleccionada()));
                }
            }
        } catch (Exception ex) {
            System.out.println("ALGUN ERROR EN EL PROCESO"+ex.getMessage());
                switch (ex.getMessage()) {
                    
                    case "9":
                         bTCaja.seteMensaje("ERROR MONTO: La cuenta de origen no cuenta "
                                 + "con sufientes fondos para realizar la transfernecia de: "+montoDep);
                         sesionActual.setAttribute("descTCaja", 
                            new BeanTransCaja(bTCaja.geteCedula(), bTCaja.geteNumCuenta(), 
                                    bTCaja.geteMensaje()));
                         request.getSession().setAttribute("servletMsjTCaja3", "ERROR");
                         destino = "/WEB-INF/Banco/Vista/TransferenciaCaja.jsp";
                         observer(request, dispatcher, response, destino);
                        break;
                }
            }
    // Si se llama el servidor se actualiza 
    observer(request, dispatcher, response, destino);
}
       
        
    // Metodo que se encarga de actualizar las acciones de servidor - envio y respuesta
    private void observer(HttpServletRequest request, RequestDispatcher dispatcher,
            HttpServletResponse response, String destino) throws ServletException, IOException{
        dispatcher = request.getRequestDispatcher(destino);
              dispatcher.forward(request, response);
    }
    

    private final movimiento mov = new movimiento();
    private final funcionesTrasfereciasCajas fTC = new funcionesTrasfereciasCajas();

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

 
  
