package servicios;

import beans.BeanNuevaCuenta;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.dao.funcionesFrontEnd.funcionesAperturaCuenta;
import modelo.dao.funcionesFrontEnd.funcionesLogueo;

@WebServlet(
        name = "ServletAperturaCuenta",
        urlPatterns = {"/ServletAperturaCuenta", "/crearCuentaPaso1", "/regresarCuentaFin-Cajero"}
)

public class ServletAperturaCuenta extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Se define de que direccion viene el usaurio
        String destino = "";
        destino = "/WEB-INF/Banco/Vista/NuevaCuenta.jsp";
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());

        
        RequestDispatcher dispatcher = null;
        // Se obtiene los valores guardados en el beanNueva Cuenta
        BeanNuevaCuenta bNC = (BeanNuevaCuenta) sesionActual.getAttribute("descripción");
        request.getSession().getAttribute("bNC");

        // Declaracion de vairables empleadas para asignar el manejo de la información
        // obtenidos de los datos recibidos de NuevaCeunta.jsp
        String tipoMoneda = "";
        String numeroCuenta = "";
        String maxTrans = "";
        String cedula = "";
        String mensaje = "";
        int tipoCuenta;
        
        tipoCuenta = 0;
        verificaOpcionesFormularioRegreso( dispatcher, request,response, destino);
        

        try {
//            Se obtiene el valor de tipo moneda seleccionada
            tipoMoneda = request.getParameter("tipoMoneda");
//        Se obtiene el valor del numero de cuenta asignado por el sistema
            numeroCuenta = fAC.generarNumeroCuentaNuevo() + "";

//        Se obtiene el monto maximo escogido para transferencias maximas
            maxTrans = request.getParameter("maximoTransferencia");

//        Se obtiene el numero de cedula del usuario para verificar las cuentas registradas
            cedula = request.getParameter("numeroCedula");

//        Se obtine el tipo de cuenta ha crear para el cliente
        } catch (NumberFormatException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }

        try {
            // Se verifica si la cedula brindada por el usuario existe y se procede
            // ha mandar el mensaje y creacion de cuenta en el sistema
            if (fL.verificarExistenciaCedulaCliente(checkId(cedula))) {
                System.out.println("\n::::: NUEVA CUENTA HA SIDO ASOCIADA A: " + cedula);
                fAC.crearCuentaClienteExistente(checkId(cedula), tipoMoneda, tipoCuenta);
                // Se responde al servidor que la cuenta ha sido creada con exito!
                request.getSession().setAttribute("servletMsjNuevaCuenta", "1");

                // Se realiza la conversion de etiqueta para mostrar al usuario
                maxTrans = bNC.maxTranferenciaEtiqueta(maxTrans);

                // Se define un menaje de que la nueva cuenta fue creada con exito
                bNC.seteMensaje("NUEVA CUENTA CREADA EXITOSAMENTE!!");
                mensaje = bNC.geteMensaje();

                // Se pasa los valores de la nueva cuenta a la clae bean para
                // poder manejar los datos mas facilmente en nuevaCuenta.jsp
                sesionActual.setAttribute("descripción", new BeanNuevaCuenta(cedula,
                        maxTrans, tipoMoneda, numeroCuenta, mensaje));

                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);

            }

            // Se define un catch para capturar la excepcion si no existiera la cedula o no se 
            // encontraran cuentas al usuario.
        } catch (Exception ex) {
            String tipoExcepcion = "";
            tipoExcepcion = ex.getMessage();
//                
            switch (tipoExcepcion) {

                case "2":
                    System.out.println("\n <:::: NO EXISTE El CLIENTE EN EL SISTEMA :::: ");
//                        Se reponde a la pagina que no existe ese usuario para poder continuar
//                        con el registro de este al sistema
                    request.getSession().setAttribute("servletMsjNuevaCuenta", "2");

                    bNC.seteMensaje("ERROR NO EXISTE EL CLIENTE EN EL SISTEMA");
                    mensaje = bNC.geteMensaje();
                    sesionActual.setAttribute("descripción", new BeanNuevaCuenta(cedula,
                            maxTrans, tipoMoneda, numeroCuenta, mensaje));
                    dispatcher = request.getRequestDispatcher(destino);
                    dispatcher.forward(request, response);
                    break;
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    // </editor-fold>    // </editor-fold>

    //    Validacion para el ingreso de tipo de identificación
    private String checkId(String txt) {
        String r = txt;

        Pattern pat = Pattern.compile("([1-9,A])-?([0-9]{4})-?([0-9]{4})");
        Matcher m = pat.matcher(txt);
        if (m.find()) {
            r = String.format("%s%s%s", m.group(1), m.group(2), m.group(3));
        }

        return r;
    }

    // SE declara el uso de funciones de la clase DAO para el uso de los servicios
    private final funcionesAperturaCuenta fAC = new funcionesAperturaCuenta();
    private final funcionesLogueo fL = new funcionesLogueo();

    
    protected void verificaOpcionesFormularioRegreso(RequestDispatcher dispatcher,
            HttpServletRequest request, HttpServletResponse response, String destino) 
            throws ServletException, IOException{
        try {
            String botonFormulario = "";
            botonFormulario = request.getParameter("regresoOpcion");
            if (botonFormulario != null && !botonFormulario.isEmpty()) {

                // Swicht para tomar las opciones recibidas del formulario de botones
                // de nueva cuenta
                switch (botonFormulario) {
                    
                    // Opción 1 - El usuario cajero desea regresar al menu principal
                    case "1":
                        destino = "/WEB-INF/Banco/Vista/Cajero.jsp";
                        dispatcher = request.getRequestDispatcher(destino);
                        request.getSession().setAttribute("servletMsjNuevaCuenta", null);
                        dispatcher.forward(request, response);
                        break;
                    // Opción 2 - Ocurrio un error en la creacion de la cuenta y el
                   //  usuario desaa intentar de nuevo registar una nueva
                    case "2":
                        destino = "/WEB-INF/Banco/Vista/NuevaCuenta.jsp";
                        request.getSession().setAttribute("servletMsjNuevaCuenta", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                      // Opción 3 - El usuario cajero desea registar una nueva cuenta
                    case "3":
                        destino = "/WEB-INF/Banco/Vista/NuevaCuenta.jsp";
                        request.getSession().setAttribute("servletMsjNuevaCuenta", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;

                }
            }
        } catch (NumberFormatException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        
    }
    
}
