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
import model.cliente;
import model.usuario;
import modelo.dao.funcionesFrontEnd.funcionesAperturaCuenta;
import modelo.dao.funcionesFrontEnd.funcionesLogueo;

@WebServlet(
        name = "ServletAperturaNuevo",
        urlPatterns = {"/ServletAperturaNuevo", "/crearCuentaPaso2"}
)
public class ServletAperturaNuevo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
           // Se define de que direccion viene el usaurio
        String destino="";
        destino = "/WEB-INF/Banco/Vista/NuevaCuenta.jsp";
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());
        
        
        
        // Se obtiene los valores guardados en el beanNueva Cuenta
        BeanNuevaCuenta bNC = (BeanNuevaCuenta) sesionActual.getAttribute("descripción");
       request.getSession().getAttribute("bNC");
        
        String nombreUs="";
        String apellidosUs="";
        String tipoMoneda="";
        String numeroCuenta="";
        String maxTrans="";
        String cedulaUs="";
        String pass="";
        String tipoUs="";
        int tipoUsuario=0;
        String mensaje="";
        
        int tipoCuenta;
        String telefonoUser="";
        tipoCuenta = 0;
        RequestDispatcher dispatcher = null;
        try{
//            Se obtiene el valor de tipo moneda seleccionada guardado anteriormente en el bean
        tipoMoneda=bNC.geteTipoMoneda();
//        Se obtiene el valor del numero de cuenta asignado por el sistema guardado 
//        anteriormente en el bean
        numeroCuenta=bNC.geteNumeroCuenta();
        
//        Se obtiene el monto maximo escogido para transferencias maximas guardado 
//        anteriormente en el bean
        maxTrans=bNC.geteMaxTransferencia();
        
//        Se obtiene el numero de cedula del usuario para verificar las cuentas registradas
       // guardado anteriormente en el bean
//            cedulaUs=request.getParameter("nCed2");
            cedulaUs=bNC.geteCedula();
//        Se obtine el tipo de cuenta ha crear para el cliente
            tipoUsuario=Integer.parseInt(request.getParameter("tipoUser"));
            // Se pasa el tipo usuario a la etiqueta en tipo string
            tipoUs=tipoUsuario+"";
        
//         Se obtiene el nombre del nuevo usuario
            nombreUs=request.getParameter("nombreUser");
            
//            Se obtiene los apellidos del nuevo usuario
            apellidosUs=request.getParameter("apellidosUser"); 
        
        //        Se obtiene el numero de telefono del usuario
        telefonoUser=request.getParameter("nTelefonoUser");
        
            
            
        } catch (NumberFormatException ex) {
            String tipoExcepcion="";
                tipoExcepcion = ex.getMessage();
                        System.out.println("\n <:::: ERROR AL RECUPERAR LA INFORMACION DEL USUARIO:::: "+tipoExcepcion);
//                        // Se envia una respuesta de errror desconocido a la hora de obtener la informacion 
                        // ingresada por el usuario
                        request.getSession().setAttribute("servletMsjNuevaCuenta", "9");
                        sesionActual.setAttribute("descripción", new BeanNuevaCuenta("Se ha "
                                + "presentado un error a la hora de obtener los datos del "
                                + "usuario para poder crear la cuenta...."));
                        
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
        }
        
     
        // SE setean los valores enviados por el usuario
        
        cliente cl=new cliente();
        cl.setNombre(nombreUs);
        cl.setApellidos(apellidosUs);
        cl.setId_cliente(cedulaUs);
        cl.setTelefono(telefonoUser);
        
//  ----------------------------------------------------------------------------
//        Prueba por consola que los datos obtenidos en el bean estna siendo guardados y 
//        obtenidos correctamente
                
        System.out.println("\n\nNOMBRE::"+nombreUs+
                "\nAPELLIDOS::"+apellidosUs+
                "\nCEDULA::"+cedulaUs+
                "\nTIPO USUARIO::"+tipoUsuario+
                "\nTIPO CUENTA::"+tipoCuenta+
                "\nNUMERO TELEFONICO::"+telefonoUser+
                "\nNUMERO CUENTA::"+numeroCuenta+
                "\nTIPO MONEDA::"+tipoMoneda+
                "\nMAX TRANSFERENCIA::"+maxTrans+"\n\n");
       //  ----------------------------------------------------------------------------
       
       
        try {
            // Se verifica si la creacion de la cuenta se ha realizado correctamente
            // de ser verdadero es true y si ocurre un error es false
            
            
            if(fAC.crearClienteNuevo(cl, tipoUsuario)){
                fAC.crearCuentaClienteExistente(cedulaUs, tipoMoneda, tipoUsuario);
                System.out.println("\n::::: LA NUEVA CUENTA"+cedulaUs+" HA SIDO CREADA "
                        + "CORRECTAMENTE!\n ");
                bNC.seteMensaje("NUEVA CUENTA CREADA EXITOSAMENTE!!");
                mensaje=bNC.geteMensaje();
                if(tipoUs.equals("0")){
                    tipoUs="Cliente";
                }else if (tipoUs.equals("1")){
                    tipoUs="Cajero";
                }
                maxTrans=bNC.maxTranferenciaEtiqueta(maxTrans);
                usuario user=fL.obtenerUsuarioPorCedula(cedulaUs);
                pass=user.getClave_acceso();
                bNC.setePassword(pass);
                System.out.println("PASS:::"+pass);
                
                // Se responde al servidor que la cuenta ha sido creada con exito!
                request.getSession().setAttribute("servletMsjNuevaCuenta", "6");
                
                // Se pasa la nueva información a clase bean nueva cuenta para 
                // actualizar los valores de la operacion realizada y datos 
                // ingresados al sistema del nuevo usuario para mostrar la informacion
                // mas facilmente
                sesionActual.setAttribute("descripción", new BeanNuevaCuenta(cedulaUs, 
                        maxTrans, tipoMoneda, numeroCuenta, nombreUs, apellidosUs, tipoUs, 
                        mensaje,telefonoUser, bNC.getePassword()));
                
                  dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        
            }else{
                       dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                
            }
       
            // Se define un catch para capturar la excepcion si no existiera la cedula o no se 
            // encontraran cuentas al usuario.
        } catch (Exception ex) {
                String tipoExcepcion="";
                tipoExcepcion = ex.getMessage();
                        System.out.println("\n <:::: HA OCURRIDO UN ERROR CON LA SOLICITUD :::: "+tipoExcepcion);
//                        // Se envia una respuesta de errror desconocido y que la operacion 
                           // no se pudo completar con exito
                        request.getSession().setAttribute("servletMsjNuevaCuenta", "8");
                        sesionActual.setAttribute("descripción", new BeanNuevaCuenta("Se ha presentado "
                                + "un error al realizar la operacion de creacion de la cuenta...."));
                        
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                
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
    
}
