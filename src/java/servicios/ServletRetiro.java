package servicios;
import beans.BeanDeposito;
import beans.BeanRetiro;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.cuenta;
import model.movimiento;
import modelo.dao.funcionesFrontEnd.funcionesDeposito;
import modelo.dao.funcionesFrontEnd.funcionesLogueo;
import modelo.dao.funcionesFrontEnd.funcionesRetiro;

/*
 * ServletInicionSesion.java
 * Autores: Gabriel Barboza && Néstor Leiva
 * Descripcion: Clase java tipo servlet de Depositvos del usuario cajero 
 */
@WebServlet(
        name = "ServletRetiro",
        urlPatterns = {"/ServletRetiro", "/retiro-cajero", "/retiro-cajero2"}
)

public class ServletRetiro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        // Se define de que direccion viene el usaurio
        String destino = "";
        destino = "/WEB-INF/Banco/Vista/Retiros.jsp";
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());

        RequestDispatcher dispatcher = null;
        // Se obtiene los valores guardados en el beanNueva Cuenta
        BeanRetiro bRet = (BeanRetiro) sesionActual.getAttribute("descripRetiro");
        request.getSession().getAttribute("bRet");

     

            System.out.println("\n\n<<<<<<<<<<<<<<< SERVELET RETIRO CAJERO >>>>>\n\n");
            String opcionDep;
            String detalleDep;
            String tipoBus;
            tipoBus = "";
            detalleDep = "";
            opcionDep = "";
            int numeroCuentaSelect;
            numeroCuentaSelect = 0;
            String montoDep;montoDep = "";
            String detNombre; detNombre="";
            String detNumIden;detNumIden="";
            String errorGetDatos; errorGetDatos="";
            boolean existeCuentaAsociada;
            existeCuentaAsociada=false;
            
            
            tipoBus = bRet.geteTipoBusqueda();
            //existeCuentaAsociada=bRet.isExitenciaCuenta();
            System.out.println("TIPO B:::"+tipoBus);
            if (tipoBus != null) {
                
                System.out.println("<<<<SALTO TIPO BUSQUEDA>>>>>");
                
                // Si la forma de deposito es por numero de cedula
                if (tipoBus.equals("nCedula")) {
                    System.out.println("\n::::::: RETIRO X CEDULA  ::::::::::::");
                    detNumIden=request.getParameter("detalleNumId");
                    
                    System.out.println("\n NUM CUENTA:::"+bRet.geteNumCuenta());
                        
                    // Verifica que el campo de identificacion de usuario no venga vacio 
                    if(detNumIden!=null){
                        // Verifica si el numero de cuenta ingresado por cedula y el numero de
                        // identificación son iguales y que no haya escogido ninguna cuenta para
                        // realizar el deposito, entonces se serciora que se la primera vez
                        // que entra al metodo para guardar datos de la seleccion de cuenta
                        // cedula del usuario y el tipo de busqueda anteriormente selecionado 
                        // entonces significa que se encuentra en el segundo formulario de retiro
                        // verificacion de usaurio dueño de la cuenta
                        if (detNumIden.equals(bRet.geteCedula()) && bRet.geteNumCuenta()==null){
                            
                            
                                // Se cambia el estado existencia de cuenta asociada a verdadero
                                existeCuentaAsociada=true;
                                bRet.seteExitenciaCuenta(existeCuentaAsociada);
                            
                                numeroCuentaSelect = Integer.parseInt(request.getParameter("seletCuenta"));
                                bRet.seteNumCuenta(numeroCuentaSelect + "");
                                bRet.seteTipoBusqueda(tipoBus);
                                bRet.seteCedula(detNumIden);
                                bRet.seteMensaje("");
                                
                                // Se actualizan los datos del bean Deposito con la información
                                // del deposito realizado para mostrarla al usuario cajero
                                sesionActual.setAttribute("descripRetiro",
                                        new BeanRetiro(bRet.geteCedula(), bRet.geteNumCuenta(), 
                                                bRet.geteTipoBusqueda(), bRet.getExitenciaCuenta()));
                                // Servlet responde a la pagina que el usuario es dueño de la 
                                // cuenta y que se encuenta listo para pasar al fromulario 3
                                // de deposito.
                                request.getSession().setAttribute("servletMsjRetiro2", "INGRESA_MONTO_RETIRO");
                                
                                System.out.println("\n||||| RETIRO CEDULA && DUEÑO SELECT CUENTA!!    |||||||||");
                                
                        }// Falso si existencia de cuenta es verdadera entonces usario puede proceder a
                        // verificar si puede realizar el retiro
                        
                        else if (bRet.getExitenciaCuenta() && bRet.geteNumCuenta()!=null){
                            System.out.println("REALIZAR RETIRO VERIFICA 3 FORM");
                            montoDep = request.getParameter("montoDeposito");
                            detalleDep = request.getParameter("detalleDep");
                            
                            // Se verifica que el monto de retiro no sea vacio y que sea mayor a 0
                            if(montoDep!=null && Integer.parseInt(montoDep)>0){
                                
                                mov.setCuenta_num_cuenta(Integer.parseInt
                                (bRet.geteNumCuenta()));
                                mov.setDetalle(detalleDep);
                                mov.setMonto(Double.parseDouble(montoDep));
                                fRetiro.realizarRetiroDeCuenta(mov,bRet.geteCedula());
                                
                                bRet.seteMensaje("El retiro se ha realizado exitosamente!");
                                bRet.seteDetalleDeposito(detalleDep);
                                bRet.seteMontoDeposito(montoDep);
                                sesionActual.setAttribute("descripRetiro",
                                        new BeanRetiro(bRet.geteNumCuenta(),bRet.geteCedula()
                                         ,bRet.geteMontoDeposito(),bRet.geteDetalleDeposito(),bRet.geteMensaje()));
                                request.getSession().setAttribute("servletMsjRetiro2", "RETIRO");
                            }// Falso no se ingreso un valor para el retiro correcto
                            else {
                                
                                bRet.seteNumCuenta(bRet.geteNumCuenta());
                                bRet.seteCedula(bRet.geteCedula());
                                bRet.seteTipoBusqueda(bRet.geteTipoBusqueda());
                                bRet.seteMensaje("Error monto es incorrecto!");
                                // Se envia el mensaje de error al servidor
                                sesionActual.setAttribute("descripRetiro",
                                        new BeanRetiro(bRet.geteMensaje()));
                                
                                // El servidor envia el mensaje de error el monto ingresado por el 
                                // usuario es nulo o menor a 0 por lo que se reponde error monto
                                request.getSession().setAttribute("servletMsjRetiro2", "ERROR_MONTO");
                            }
                            
                        }// Falso si no existe la cuenta
                        else if (!bRet.getExitenciaCuenta()){
                            bRet.seteMensaje("No puede efectuar el retiro de efectivo por que no es el dueño de la cuenta!");
                            // Se envia el mensaje de error al servidor
                                sesionActual.setAttribute("descripRetiro",
                                        new BeanRetiro(bRet.geteMensaje()));
                                // El servidor envia el mansaje de error el usuario y la cuenta no le pertenece
                                // y por lo tanto no podra realizar el retiro de efectivo
                                request.getSession().setAttribute("servletMsjRetiro2", "ERROR_NO_CUENTA");
                            }
                        }
                    }// Falso si la forma de deposito es por numero de cuenta
                    else if (tipoBus.equals("nCuenta")) {
                     
                     System.out.println("\n\n:::::::DEPOSITO POR NUMERO CUENTA::::::::::::");
                     
                    detNombre=request.getParameter("detalleNombreDep");
                    montoDep=request.getParameter("detalleMontoDep");
                    detalleDep = request.getParameter("detalleDepTxt");
                    detNumIden=request.getParameter("detalleNumId");
                    // Valida que el numero de identificacion
                    // no sea vacio o sean nulo
                    if (detNumIden!=null){
                        try {
                            // Verifica si la cedula indicada del depositante y el numero
                            // de cuenta indicado le pertenecen
                            
                            System.out.println(">>> IF NUM IDEN ");
                            
                            if(fLogin.verificarPosibleCedulaCliente(detNumIden)){
                                
                                System.out.println("VERIFICA EXIS CLIENT");
                            // Se declara una lista de tipo de informacion de cuenta
                            List<cuenta> listaCuentas;
                        
                            // Se cargan todas las cuentas que posea el numero de cedula
                            // que se indico buscar
                            listaCuentas = fDeposito.listarCuentasCliente(detNumIden);
                                
                            // Se recorre la informacion encontrada del numero de cedula
                            // indicado por el usuario y se verifica si tiene una cuenta
                            // asociada a la indicada en el primer formulario
                                for (int i=0; i<listaCuentas.size(); i++) {
                                    // Si la conside el numero de cuenta con el indicado
                                    // para depositar entonces siginifca que es el usuario
                                    // y existenciaCuentaAsociada pasa a verdadero
                                    if(listaCuentas.get(i).getNum_cuenta()
                                            ==Integer.parseInt(bRet.geteNumCuenta())){
                                        System.out.println("PISITIVO++++++");
                                        existeCuentaAsociada=true;
                                    }
                                }
                            }
                            if(existeCuentaAsociada){
                                
                                // Se pasa la información al bean
                                bRet.seteDetalleDeposito(detalleDep);
                                bRet.seteMontoDeposito(montoDep);
                                bRet.seteNumCuenta(bRet.geteNumCuenta());
                                bRet.seteCedula(detNumIden);
                                bRet.seteMensaje("El deposito ha sido realizado con exito!");
                                
                                // Se actualizan los datos del bean Deposito con la información
                                // del deposito realizado para mostrarla al usuario cajero
                                sesionActual.setAttribute("descripRetiro",
                                        new BeanDeposito(bRet.geteNumCuenta(),bRet.geteCedula(),
                                                bRet.geteMontoDeposito(),bRet.geteDetalleDeposito(),
                                                bRet.geteMensaje()));
                                
                                // Se pasan los datos a obtenidos a movimientos
                                mov.setCuenta_num_cuenta(Integer.parseInt(bRet.geteNumCuenta()));
                                mov.setMonto(Integer.parseInt(montoDep));
                                // Se reliza el deposito en la base de datos
                                fDeposito.realizarDepositoACuentaRefact(mov);
                                // Se actualiza el mensaje al servidor como deposito listo
                                request.getSession().setAttribute("servletMsjRetiro2", "DEPREADY3");
                                System.out.println("\n\n-----DEPOSITO CORRECTO DUEÑO CUENTA--------\n\n");
                                
                            }// Falso si no existe una cuenta asociada y el detalle de nombre
                            // es vacio o nulo, se le tiene que pedir a usuario que ingrese la
                            // informacion de este campo
                            else if (!existeCuentaAsociada && detNombre==null){
                                
                                // Se eniva un mensaje por medio del bean para mostrarlo al suario
                                // en pantalla
                                bRet.seteMensaje("El sistema detecto que la cuenta no pertenece"
                                        + "a la persona identificada. Se requiere ingresar el nombre"
                                        + "de detalle de depositante!");
                                
                                // Se actualizan los datos del bean Deposito con la información
                                // del deposito realizado para mostrarla al usuario cajero
                                sesionActual.setAttribute("descripRetiro",
                                        new BeanDeposito(bRet.geteCedula(),detNumIden,bRet.geteNumCuenta(),
                                                "",montoDep,detalleDep, bRet.geteMensaje(),
                                                bRet.geteTipoBusqueda()));
                                
                                // Se actualiza el mensaje al servidor que el usario no es el dueño de
                                // la cuenta  pasando un numero 3
                                request.getSession().setAttribute("servletMsjRetiro2", "3");
                                
                                System.out.println("\n\n|||||  DIFERENTE DUEÑO NUM CUENTA 33   |||||||||\n\n");
                                
                            }// Falso si no es dueño de la cuenta y ya ingreso su nombre en 
                            // valor de detalle nombre entonces se procede a actualizar los 
                            // datos y a realizar el deposito en la cuenta
                            else if (!existeCuentaAsociada && detNombre!=null){
                                
                                bRet.seteNombreUs(detNombre);
                                bRet.seteMontoDeposito(montoDep);
                                bRet.seteDetalleDeposito(detalleDep);
                                bRet.seteMensaje("El deposito ha sido realizado con exito!");
                                // Se actualizan los datos en el bean deposito para mostrarlos
                                // en la pagina de deposito.jsp
                                sesionActual.setAttribute("descripRetiro",
                                        new BeanDeposito(bRet.geteCedula(),detNumIden,bRet.geteNumCuenta(),
                                                bRet.geteNombreUs(),bRet.geteMontoDeposito(),
                                                bRet.geteDetalleDeposito(), bRet.geteMensaje(),
                                                ""));
                                
                                // Se pasan los datos obtenidos a los movimientos
                                mov.setCuenta_num_cuenta(Integer.parseInt(bRet.geteNumCuenta()));
                                mov.setMonto(Double.parseDouble(montoDep));
                                mov.setDetalle(detalleDep);
                                // Se reliza el deposito en la base de datos
                                fDeposito.realizarDepositoACuentaRefact(mov);
                                
                                // Se responde a la pagina deposito.jsp con 6 indicando que
                                // la transaccion de deposito ha sido efectuada correctamente
                                request.getSession().setAttribute("servletMsjRetiro2", "DEPREADY4");
                                
                                System.out.println("\n\n||||| DEPOSITO REALIZADO  NUM CUENTA && "
                                        + "PERSONA DIFERENTE    |||||||||\n\n");
                                
                            }
                        } catch (Exception ex) {
                            switch (ex.getMessage()) {
                case "6":
                    System.out.println("\n<--- NO SE PUDO REALIZAR EL DE DEPOSITO --->\n");
                    bRet.seteMensaje("NO SE PUDO REALIZAR EL DEPOSITVO\nERROR:" + ex.getMessage() + "\n");
                    sesionActual.setAttribute("descripRetiro", new BeanDeposito(bRet.geteMensaje()));

                    request.getSession().setAttribute("servletMsjRetiro2", "10");
                    dispatcher = request.getRequestDispatcher(destino);
                    dispatcher.forward(request, response);
                    break;
                    }
                        }
                    }
                }
                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
            }// Falso si se recarga la pagina y identificacion ya no contiene valores
            // se segira respondiento y direccionando a la misma pagina que estaba
            else{
                
                dispatcher = request.getRequestDispatcher(destino);
                dispatcher.forward(request, response);
            }
    }
    
    private final funcionesDeposito fDeposito = new funcionesDeposito();
    private final funcionesLogueo fLogin = new funcionesLogueo();
    private final movimiento mov = new movimiento();
    private final funcionesRetiro fRetiro=new funcionesRetiro();
             


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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletRetiro.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletRetiro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short descripRetirotion of the servlet.
     *
     * @return a String containing servlet descripRetirotion
     */
    @Override
    public String getServletInfo() {
        return "Short descripRetirotion";
    }// </editor-fold>

}
