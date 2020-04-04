package servicios;

import beans.BeanRetiro;
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

        // Se define de que direccion viene el usaurio
        String destino = "";
        destino = "/WEB-INF/Banco/Vista/TranferenciaCaja.jsp";
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());

        RequestDispatcher dispatcher = null;
        // Se obtiene los valores guardados en el beanNueva Cuenta
        BeanRetiro bTrans = (BeanRetiro) sesionActual.getAttribute("descripTransCaja");
        request.getSession().getAttribute("bTrans");

        System.out.println("\n\n<<<<<<<<<<<<<<< SERVELET RETIRO CAJERO >>>>>\n\n");
        String opcionDep;
        String detalleDep;
        String tipoBus;
        tipoBus = "";
        detalleDep = "";
        opcionDep = "";
        int numeroCuentaSelect;
        numeroCuentaSelect = 0;
        String montoDep;
        montoDep = "";
        String detNombre;
        detNombre = "";
        String detNumIden;
        detNumIden = "";
        String errorGetDatos;
        errorGetDatos = "";
        boolean existeCuentaAsociada;
        existeCuentaAsociada = false;

        

        try {
            tipoBus = bTrans.geteTipoBusqueda();
            System.out.println("TIPO B:::" + tipoBus);
            if (tipoBus != null) {

                System.out.println("<<<<SALTO TIPO BUSQUEDA>>>>>");

                // Si la forma de deposito es por numero de cedula
                if (tipoBus.equals("nCedula")) {
                    System.out.println("\n::::::: RETIRO --- X --- CEDULA  ::::::::::::");
                    detNumIden = request.getParameter("detalleNumId");
                    System.out.println("\n NUM CUENTA:::" + bTrans.geteNumCuenta());
                    // Verifica que el campo de identificacion de usuario no venga vacio 
                    if (detNumIden != null) {
                        
                        System.out.println("\nRETIRO-- ID ! = NULL");
                        // Verifica si el numero de cuenta ingresado por cedula y el numero de
                        // identificación son iguales y que no haya escogido ninguna cuenta para
                        // realizar el deposito, entonces se serciora que se la primera vez
                        // que entra al metodo para guardar datos de la seleccion de cuenta
                        // cedula del usuario y el tipo de busqueda anteriormente selecionado 
                        // entonces significa que se encuentra en el segundo formulario de retiro
                        // verificacion de usaurio dueño de la cuenta
                        if (detNumIden.equals(bTrans.geteCedula()) && bTrans.geteNumCuenta() == null) {

                            // Se cambia el estado existencia de cuenta asociada a verdadero
                            existeCuentaAsociada = true;
                            bTrans.seteExitenciaCuenta(existeCuentaAsociada);

                            numeroCuentaSelect = Integer.parseInt(request.getParameter("seletCuenta"));
                            bTrans.seteNumCuenta(numeroCuentaSelect + "");
                            bTrans.seteTipoBusqueda(tipoBus);
                            bTrans.seteCedula(detNumIden);
                            bTrans.seteMensaje("");

                            // Se actualizan los datos del bean Deposito con la información
                            // del deposito realizado para mostrarla al usuario cajero
                            sesionActual.setAttribute("descripRetiro",
                                    new BeanRetiro(bTrans.geteCedula(), bTrans.geteNumCuenta(),
                                            bTrans.geteTipoBusqueda(), bTrans.getExitenciaCuenta()));
                            // Servlet responde a la pagina que el usuario es dueño de la 
                            // cuenta y que se encuenta listo para pasar al fromulario 3
                            // de deposito.
                            request.getSession().setAttribute("servletMsjRetiro2", "INGRESA_MONTO_RETIRO");
                            System.out.println("\n||||| RETIRO CEDULA && DUEÑO SELECT CUENTA!!    |||||||||");

                        }// Falso si existencia de cuenta es verdadera entonces usario puede proceder a
                        // verificar si puede realizar el retiro
                        else if (bTrans.getExitenciaCuenta() && bTrans.geteNumCuenta() != null) {
                            System.out.println("REALIZAR RETIRO VERIFICA 3 FORM");
                            montoDep = request.getParameter("montoDeposito");
                            detalleDep = request.getParameter("detalleDep");

                            // Se verifica que el monto de retiro no sea vacio y que sea mayor a 0
                            if (montoDep != null && Integer.parseInt(montoDep) > 0) {

                                mov.setCuenta_num_cuenta(Integer.parseInt(bTrans.geteNumCuenta()));
                                mov.setDetalle(detalleDep);
                                mov.setMonto(Double.parseDouble(montoDep));
                                fRetiro.realizarRetiroDeCuenta(mov, bTrans.geteCedula());

                                bTrans.seteMensaje("El retiro se ha realizado exitosamente!");
                                bTrans.seteDetalleDeposito(detalleDep);
                                bTrans.seteMontoDeposito(montoDep);
                                sesionActual.setAttribute("descripRetiro",
                                        new BeanRetiro(bTrans.geteNumCuenta(), bTrans.geteCedula(),
                                                 bTrans.geteMontoDeposito(), bTrans.geteDetalleDeposito(), bTrans.geteMensaje()));
                                request.getSession().setAttribute("servletMsjRetiro2", "RETIRO");
                                 
                                System.out.println("QUE:::"+montoDep);
                            }// Falso si el usuario ingreso un valor pero es menor o igual a 0
                                else if(montoDep != null && Integer.parseInt(montoDep) < 0){
                                    bTrans.seteMensaje("El monto no puede ser negativo , ni menor o igual a 0!");
                                    sesionActual.setAttribute("descripRetiro",
                                        new BeanRetiro(bTrans.geteCedula(), bTrans.geteNumCuenta(),
                                                bTrans.geteTipoBusqueda(), bTrans.getExitenciaCuenta(), 
                                                bTrans.geteMensaje(),bTrans.geteDetalleDeposito()));
                                    
                                    // El servidor envia el mensaje de error el monto ingresado por el 
                                    // usuario es nulo o menor a 0 por lo que se reponde error monto
                                    request.getSession().setAttribute("servletMsjRetiro2", "ERROR_MONTO");
                                }

                                  // Falso si se recarga la pagina y no se ha completado la accion guarda la informacion
                                 else {
                                    bTrans.seteNumCuenta(bTrans.geteNumCuenta());
                                    bTrans.seteCedula(bTrans.geteCedula());
                                    bTrans.seteTipoBusqueda(bTrans.geteTipoBusqueda());
                                    bTrans.seteMensaje("Error monto es incorrecto!");
                                    // Se envia el mensaje de error al servidor
                                    sesionActual.setAttribute("descripRetiro",
                                        new BeanRetiro(bTrans.geteCedula(),bTrans.geteNumCuenta(), 
                                                bTrans.geteTipoBusqueda(),bTrans.getExitenciaCuenta()));

                                    // El servidor envia el mensaje de que continua relizando la operacion de retiro
                                    // a la pagina retiro.jsp
                                    request.getSession().setAttribute("servletMsjRetiro2", "INGRESA_MONTO_RETIRO");
                                    }

                        }// Falso si no existe la cuenta
                        else if (!bTrans.getExitenciaCuenta()) {
                            bTrans.seteMensaje("No puede efectuar el retiro de efectivo por que no es el dueño de la cuenta!");
                            // Se envia el mensaje de error al servidor
                            sesionActual.setAttribute("descripRetiro",
                                    new BeanRetiro(bTrans.geteMensaje()));
                            // El servidor envia el mansaje de error el usuario y la cuenta no le pertenece
                            // y por lo tanto no podra realizar el retiro de efectivo
                            request.getSession().setAttribute("servletMsjRetiro2", "ERROR_NO_CUENTA");
                        }
                    }
                }
                
                // ---------------  TIPO RETIRO POR NUMERO DE CUENTA     ---------------------
                else if (tipoBus.equals("nCuenta")) {
                    System.out.println("\n\n:::::::RETIRO POR NUMERO CUENTA::::::::::::");

                    detNumIden = request.getParameter("detalleNumId");
                    // Valida que el numero de identificacion
                    // no sea vacio o sean nulo
                    
                    
                    System.out.println("VALOR ::: NUM"+detNumIden);
                    if (detNumIden != null) {
                        
                        System.out.println("\nNUMERO DE CUENTA ! = N U L L ");
                            // Verifica si la cedula indicada del depositante y el numero
                            // de cuenta indicado le pertenecen y que ya no haya entrado y
                            // que el bean sepa si ya es usuario
                            
                            System.out.println(">>>>>>>>>>>>"+bTrans.getExitenciaCuenta());
                            if (fLogin.verificarPosibleCedulaCliente(detNumIden) && !bTrans.getExitenciaCuenta()) {

                                System.out.println("VERIFICA CUENTA ASOCIADA LE PERTENEZCA");
                                // Se declara una lista de tipo de informacion de cuenta
                                List<cuenta> listaCuentas;

                                // Se cargan todas las cuentas que posea el numero de cedula
                                // que se indico buscar
                                listaCuentas = fDeposito.listarCuentasCliente(detNumIden);

                                // Se recorre la informacion encontrada del numero de cedula
                                // indicado por el usuario y se verifica si tiene una cuenta
                                // asociada a la indicada en el primer formulario
                                for (int i = 0; i < listaCuentas.size(); i++) {
                                    // Si la conside el numero de cuenta con el indicado
                                    // para depositar entonces siginifca que es el usuario
                                    // y existenciaCuentaAsociada pasa a verdadero
                                    
                                    System.out.println("---->"+listaCuentas.get(i).getNum_cuenta());
                                    if (listaCuentas.get(i).getNum_cuenta()
                                            == Integer.parseInt(bTrans.geteNumCuenta())) {
                                        System.out.println("PISITIVO++++++");
                                        existeCuentaAsociada = true;
                                        
                                    }
                                }
                                
                                if(existeCuentaAsociada){
                                // Se pasa el valor de eixstencia cuenta al bean para recordar la 
                                // informacion
                                bTrans.seteExitenciaCuenta(existeCuentaAsociada);
                                bTrans.seteCedula(detNumIden);
                                bTrans.seteTipoBusqueda(tipoBus);
                                // Se actualizan los datos del bean Deposito con la información
                                // del deposito realizado para mostrarla al usuario cajero
                                sesionActual.setAttribute("descripRetiro",
                                new BeanRetiro(bTrans.geteCedula(), bTrans.geteNumCuenta(),
                                    bTrans.geteTipoBusqueda(), bTrans.getExitenciaCuenta()));
                                // Servlet responde a la pagina que el usuario es dueño de la 
                                // cuenta y que se encuenta listo para pasar al fromulario 3
                                // de deposito.
                                request.getSession().setAttribute("servletMsjRetiro2", "INGRESA_MONTO_RETIRO");
                            System.out.println("\n||||| RETIRO NUMERO CUENTA && DUEÑO SE GUARDAN DATOS!!    |||||||||");
                                }else if(!existeCuentaAsociada){
                                    System.out.println("Error el usuario no es dueño de la cuenta");
                                bTrans.seteMensaje("ERROR: La persona identificada no es dueña de la cuenta! "
                                        + "No se puede procesar la solicitud de deposito.");
                                sesionActual.setAttribute("descripRetiro", new BeanRetiro(bTrans.geteMensaje()));
                                request.getSession().setAttribute("servletMsjRetiro2", "ERROR_NO_CUENTA");
                                }
                            }
                            // Verifica que la cuenta sea verdaderamente del depositante
                            else if (bTrans.getExitenciaCuenta()) {
                                  
                                System.out.println("\nVERIFICA RETIRO FORM 3 MONTO");
                                montoDep = request.getParameter("montoDeposito");
                                detalleDep = request.getParameter("detalleDep");
                                
                                
                                // Se verifica que el monto de retiro no sea vacio y que sea mayor a 0
                                if (montoDep != null && Integer.parseInt(montoDep) > 0) {

                                    mov.setCuenta_num_cuenta(Integer.parseInt(bTrans.geteNumCuenta()));
                                    mov.setDetalle(detalleDep);
                                    mov.setMonto(Double.parseDouble(montoDep));
                                    fRetiro.realizarRetiroDeCuenta(mov, bTrans.geteCedula());

                                    bTrans.seteMensaje("El retiro se ha realizado exitosamente!");
                                    bTrans.seteDetalleDeposito(detalleDep);
                                    bTrans.seteMontoDeposito(montoDep);
                                    sesionActual.setAttribute("descripRetiro",
                                        new BeanRetiro(bTrans.geteNumCuenta(), bTrans.geteCedula(),
                                                 bTrans.geteMontoDeposito(), bTrans.geteDetalleDeposito(), bTrans.geteMensaje()));
                                    request.getSession().setAttribute("servletMsjRetiro2", "RETIRO");
                                    System.out.println("\n\n-----RETIRO CORRECTO POR NUMERO DE CUENTA--------\n\n");
                                 
                                }// Falso si el usuario ingreso un valor pero es menor o igual a 0
                                else if(montoDep != null && Integer.parseInt(montoDep) < 0){
                                    bTrans.seteMensaje("El monto no puede ser negativo , ni menor o igual a 0!");
                                    sesionActual.setAttribute("descripRetiro",
                                        new BeanRetiro(bTrans.geteCedula(), bTrans.geteNumCuenta(),
                                                bTrans.geteTipoBusqueda(), bTrans.getExitenciaCuenta(), 
                                                bTrans.geteMensaje(),bTrans.geteDetalleDeposito()));
                                    
                                    // El servidor envia el mensaje de error el monto ingresado por el 
                                    // usuario es nulo o menor a 0 por lo que se reponde error monto
                                    request.getSession().setAttribute("servletMsjRetiro2", "ERROR_MONTO");
                                }

                                  // Falso si se recarga la pagina y no se ha completado la accion guarda la informacion
                                 else {
                                    bTrans.seteNumCuenta(bTrans.geteNumCuenta());
                                    bTrans.seteCedula(bTrans.geteCedula());
                                    bTrans.seteTipoBusqueda(bTrans.geteTipoBusqueda());
                                    bTrans.seteMensaje("Error monto es incorrecto!");
                                    // Se envia el mensaje de error al servidor
                                    sesionActual.setAttribute("descripRetiro",
                                        new BeanRetiro(bTrans.geteCedula(),bTrans.geteNumCuenta(), 
                                                bTrans.geteTipoBusqueda(),bTrans.getExitenciaCuenta()));

                                    // El servidor envia el mensaje de que continua relizando la operacion de retiro
                                    // a la pagina retiro.jsp
                                    request.getSession().setAttribute("servletMsjRetiro2", "INGRESA_MONTO_RETIRO");
                                    }
                            } // Falso si el nuemro de cuenta no le pertenece a la persona con la 
                            // identificación ingresada
                            
                            else if (!bTrans.getExitenciaCuenta()){
                                System.out.println("Error el usuario no es dueño de la cuenta");
                                // Se envia el mensaje de error que no se puede efecutar el retiro
                                bTrans.seteMensaje("ERROR: La persona identificada no es dueña de la cuenta! "
                                        + "No se puede procesar la solicitud de deposito.");
                                sesionActual.setAttribute("descripRetiro", new BeanRetiro(bTrans.geteMensaje()));
                                request.getSession().setAttribute("servletMsjRetiro2", "ERROR_NO_CUENTA");
                            }
                        }  //Falso numero de identificacion falso
                            else if (detNumIden==null){
                                System.out.println("Error numero de identificacion es nulo");
                                bTrans.seteMensaje("ERROR: numero de identificacion es nulo!");
                                sesionActual.setAttribute("descripRetiro", new BeanRetiro(bTrans.geteMensaje()));
                                request.getSession().setAttribute("servletMsjRetiro2", "ERROR_NO_CUENTA");
                            }
                             }
                                }
                          // Falso si se recarga la pagina y identificacion ya no contiene valores
                          // se segira respondiento y direccionando a la misma pagina que estaba
//                            }else{
//                                System.out.println("TIPO DE BUSQUEDA ES NULO");
//                                bTrans.seteMensaje("ERROR: Tipo de busqueda es vacio o nulo!");
//                                sesionActual.setAttribute("descripRetiro", new BeanRetiro(bTrans.geteMensaje()));
//                                request.getSession().setAttribute("servletMsjRetiro2", "ERROR_NO_CUENTA");
//                                }
            
                        } catch (Exception ex) {
                            switch (ex.getMessage()) {
                                case "9":
                                    
                                    System.out.println("\n<--- ERRROR NO TIENE SUFICIENTES FONDOS --->\n");
                                    
                                    bTrans.seteNumCuenta(bTrans.geteNumCuenta());
                                    bTrans.seteCedula(bTrans.geteCedula());
                                    bTrans.seteTipoBusqueda(bTrans.geteTipoBusqueda());
                                    bTrans.seteDetalleDeposito(detalleDep);
                                    bTrans.seteMensaje("ERROR: El usuario no dispone de fondos suficientes "
                                            + "para realizar el retiro indicado, por favor intente otro monto a retirar");
                                    // Se envia el mensaje de error al servidor
                                    sesionActual.setAttribute("descripRetiro",
                                        new BeanRetiro(bTrans.geteCedula(), bTrans.geteNumCuenta(),
                                                bTrans.geteTipoBusqueda(), bTrans.getExitenciaCuenta(),
                                                bTrans.geteMensaje(), bTrans.geteDetalleDeposito()));
                                    // Servlet envia información a la pagina con el detalle de error
                                    // ocurrido 
                                    
                                    request.getSession().setAttribute("servletMsjRetiro2", "ERROR_MONTO");
                                    dispatcher = request.getRequestDispatcher(destino);
                                    dispatcher.forward(request, response);
                                    //dispatcher.forward(request, response);
                                    break;

                                case "10":
                                    System.out.println("\n<--- ERRROR NO TIENE SUFICIENTES FONDOS --->\n");
                                    bTrans.seteMensaje("ERROR: El usuario a realizar el retiro no es dueño de la cuenta");
                                    sesionActual.setAttribute("descripRetiro", new BeanRetiro(bTrans.geteMensaje()));
                                    // Servlet envia información a la pagina con el detalle de error
                                    // ocurrido 
                                    request.getSession().setAttribute("servletMsjRetiro2", "ERROR_NO_CUENTA");
                                    dispatcher = request.getRequestDispatcher(destino);
                                    dispatcher.forward(request, response);
                                    break;

                                case "11":
                                    System.out.println("\n<--- ERRROR EL USUARIO NO TIENE ESTA CUENTA ASOCIADA A FAVORITAS --->\n");
                                    bTrans.seteMensaje("ERROR: El usuario no tiene la cuenta asociada a favoritas");
                                    sesionActual.setAttribute("descripRetiro", new BeanRetiro(bTrans.geteMensaje()));
                                    // Servlet envia información a la pagina con el detalle de error
                                    // ocurrido 
                                    request.getSession().setAttribute("servletMsjRetiro2", "ERROR_NO_CUENTA");
                                    dispatcher = request.getRequestDispatcher(destino);
                                    dispatcher.forward(request, response);
                                    break;
                            }// Cierra switch
                        }// Cierra excepcion catch
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

 
  
