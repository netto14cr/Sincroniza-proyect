package servicios;
import beans.BeanTransCaja;
import beans.BeanTransCaja2;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.cuenta;
import modelo.dao.funcionesFrontEnd.funcionesDeposito;
import modelo.dao.funcionesFrontEnd.funcionesLogueo;

/*
 * ServletInicionSesion.java
 * Autores: Gabriel Barboza && Néstor Leiva
 * Descripcion: Clase java tipo servlet de Busqueda de cuentas en el requerimientos
 * busqueda de cajas
 */
@WebServlet(
        name = "ServletBusquedaRetiros",
        urlPatterns = {"/ServletBusquedaRetiros", "/fromBusqCuenOrig",
            "/fromBusqCuenDest","/regresarCaja"}
)

public class ServletBusquedaCajas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("\n:::::::  SERVLET BUSQUEDA CAJAS    ::::::::");
        
           // Se define de que direccion viene el usaurio
        String destino="";
        destino = "/WEB-INF/Banco/Vista/BusqCuentaTrans.jsp";
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());
        
        String tipoBusqCuenOrig = request.getParameter("tipoBusqOrigen");
        String tipoBusqCuenDest = request.getParameter("tipoBusqDestino");
        
        String idOrigen = request.getParameter("busqCuentaOrigen");
        String idDestino = request.getParameter("busqCuentaDestino");
        
        RequestDispatcher dispatcher = null;
        
        // Se define el bean para obtener datos, etiqueda para la clase jsp y
        // su respuesta de envio de mensajes
        BeanTransCaja bTCaja = (BeanTransCaja) sesionActual.getAttribute("descTCaja");
        request.getSession().getAttribute("bTCaja");
        BeanTransCaja2 bTCaja2 = (BeanTransCaja2) sesionActual.getAttribute("descTCaja2");
        request.getSession().getAttribute("bTCaja2");
        
        verificaOpcionesFormularioRegreso( dispatcher, request,response, destino, bTCaja, 
                bTCaja2,sesionActual);
      
        
        // Se atrapa en un try-catch donde se maneja los posibles errores de las funciones
        // dao from end por si en la realizacion de algun metodo ocurre una excepcion 
        // para contenerla y realizar el manejor de datos correspondiente para enviarlos 
        // al vista del usuario
            try {
                // Se guarda el valor en el bean
                bTCaja.seteTipoBusqueda(tipoBusqCuenOrig);
                bTCaja2.seteTipoBusqueda(tipoBusqCuenDest);
                
                //----------- BUSQUEDA CUENTA ORIGEN -----------------------------------
                // Si el tipo de busqueda seleccionado es por numero de cédula
                if (bTCaja.geteTipoBusqueda()!=null && bTCaja.geteTipoBusqueda().equals("nCedula")) {
                   
                    // Se verifica si la cedula ingresada existe en el sistema
                    // si se cuemple entra y manda la respuesta de que puede continuar
                    // con el deposito a esta cuenta
                    
                    if (servicioLogin.verificarExistenciaCedulaCliente(checkId(idOrigen))) {
                        // Si usuario existe se procede a buscarla cuentas asociadas
                        // Servlet respode que la operacion es uno y se toma como 
                        // correcta.
                        
                        // Se declara una lista de tipo de informacion de cuenta
                        List<cuenta> listaCuentas;
                        
                        // Se cargan todas las cuentas que posea el numero de cedula
                        // que se indico buscar
                        listaCuentas = servicioDeposito.listarCuentasCliente(idOrigen);
                        
                        bTCaja.seteExitenciaCuenta(true);
                        bTCaja.setLista(listaCuentas);
                        
                       // Se pasan los valores a la clase bean para el manejo de informacion
                       // entre el servidor y manejo de la clase Deposito.jsp
                        sesionActual.setAttribute("descTCaja", new BeanTransCaja(idOrigen, 
                                bTCaja.geteTipoBusqueda(),bTCaja.getLista(), 
                                bTCaja.getExitenciaCuenta()));
                        
                        // Se define que el servidor respondera con un 1 a depisto.jsp
                        // significando que la busqueda de la cedula ha sido exitoso
                        // y que se puede continuar con las acciones de deposito
                        request.getSession().setAttribute("servletMsjTCaja", "BusqDatosOrig1");
                    }
                    // Falso si ya el sistema verifico la cuenta por medio de numero de cedual
                    // entonces el usuario se encuentra en el segundo formulario de verificacion
                    // de identificacion de la cuenta si a un no se ha seleccionado una cuenta
                    }else if (bTCaja.getExitenciaCuenta() && !bTCaja.getCuentaSeleccionada()){
                    String selectCuentaOrig = request.getParameter("seletCuentaOrigen");
                    String idUOrigen = request.getParameter("idUOrigen");
                    
                            // Si el valor de cuenta seleccionada es diferente a nulo se 
                            // pasa la informacion al bean y se guardan
                            if (selectCuentaOrig!=null && idUOrigen!=null){
                                if(bTCaja.geteCedula().equals(idUOrigen)){
                                    bTCaja.seteNumCuenta(selectCuentaOrig);
                                    bTCaja.setCuentaSeleccionada(true);
                                    sesionActual.setAttribute("descTCaja", 
                                    new BeanTransCaja(bTCaja.geteCedula(), bTCaja.geteNumCuenta(), 
                                    bTCaja.geteTipoBusqueda(), bTCaja.getExitenciaCuenta(),
                                    bTCaja.getCuentaSeleccionada()));
                                    request.getSession().setAttribute("servletMsjTCaja", "SELECT_ORIGEN");
                                }else if(!bTCaja.equals(idUOrigen)){
                                    bTCaja.seteMensaje("Error el usuario "+idUOrigen+" no es dueño de la cuenta!");
                                    bTCaja.setCuentaSeleccionada(false);
                                    new BeanTransCaja(bTCaja.geteCedula(),null,bTCaja.geteTipoBusqueda(),
                                    bTCaja.getExitenciaCuenta(),bTCaja.getCuentaSeleccionada(),
                                            bTCaja.geteMensaje());
                                    request.getSession().setAttribute("servletMsjTCaja", "ERROR2");
                                }
                            }else {
                                // Falso si la cuenta seleccionada es nula entonces no realiza
                                // ninguna accion pero guarda los datos obtenidos previos
                                sesionActual.setAttribute("descTCaja", new BeanTransCaja(
                                bTCaja.geteCedula(),bTCaja.geteTipoBusqueda(),bTCaja.getLista(), 
                                bTCaja.getExitenciaCuenta()));
                                request.getSession().setAttribute("servletMsjTCaja", "BusqDatosOrig1");
                            }
                            // Verifica que si la cuenta fue seleccionada por numero de cuenta
                            // entonces se encuentra en el segundo formulario de cuenta origen
                            // donde debe verificar si es el dueño de la cuenta indicada
                            }else if (!bTCaja.getExitenciaCuenta() && bTCaja.getCuentaSeleccionada()){
                                String idUOrigen = request.getParameter("idUserOrigen");
                                boolean existeCuentaAsociada; 
                                existeCuentaAsociada=false;
                                
                                // Verifica que no existe un error y el numero de identiciacion no venga 
                                // vacio o nulo
                                if (idUOrigen!=null && !idUOrigen.isEmpty()){
                                if(servicioLogin.verificarExistenciaCedulaCliente(checkId(idUOrigen))){
                                    // Se declara una lista de tipo de informacion de cuenta
                                    List<cuenta> listaCuentas = null;
                                
                                // Se cargan todas las cuentas que posea el numero de cedula
                                // que se indico buscar
                                listaCuentas= servicioDeposito.listarCuentasCliente(idUOrigen);
                                
                                // Se recorre la informacion encontrada del numero de cedula
                                // indicado por el usuario y se verifica si tiene una cuenta
                                // asociada a la indicada en el primer formulario
                                for (int i=0; i<listaCuentas.size(); i++) {
                                    // Si la conside el numero de cuenta con el indicado
                                    // para depositar entonces siginifca que es el usuario
                                    // y existenciaCuentaAsociada pasa a verdadero
                                    if(listaCuentas.get(i).getNum_cuenta()
                                            ==Integer.parseInt(bTCaja.geteNumCuenta())){
                                        // Se cambia el estado del valor si encuentra un cuenta
                                        // similar a la indicada en la busqueda del primero 
//                                        forumlario a positivo
                                        existeCuentaAsociada=true;
                                    }
                                }
                            }
                            // Verifica que la cuenta con el numero de cedula pertenezca 
                            // a una cuenta del usuario
                            if(existeCuentaAsociada){
                                bTCaja.seteExitenciaCuenta(true);
                                bTCaja.seteCedula(idUOrigen);
                                sesionActual.setAttribute("descTCaja", 
                                new BeanTransCaja(bTCaja.geteCedula(), bTCaja.geteNumCuenta(), 
                                bTCaja.geteTipoBusqueda(), bTCaja.getExitenciaCuenta(),
                                bTCaja.getCuentaSeleccionada()));
                                request.getSession().setAttribute("servletMsjTCaja", "SELECT_ORIGEN");
                            
                            }
                            // Falso si no fue encontrada ninguna cedula aosciada la usuario entonces
                            // envia el mensaje correspondiente de error y guarda los datos obtenidos 
                            // anteriormente
                            else{
                                    bTCaja.seteMensaje("Error el usuario "+idUOrigen+" no es dueño de la cuenta "+bTCaja.geteNumCuenta());
                                    bTCaja.seteExitenciaCuenta(false);
                                    new BeanTransCaja(bTCaja.geteCedula(),bTCaja.geteNumCuenta(),
                                    bTCaja.geteTipoBusqueda(),bTCaja.getExitenciaCuenta(),
                                    bTCaja.getCuentaSeleccionada(),bTCaja.geteMensaje());
                                    request.getSession().setAttribute("servletMsjTCaja", "ERROR2");
                                }
                        }
                    //----------- BUSQUEDA POR TIPO NUEMERO DE CUENTA -------------------------
                    
//                Falso si el tipo de busqueda seleccionado es por numero de cuenta
                } else if (bTCaja.geteTipoBusqueda()!=null && bTCaja.geteTipoBusqueda().equals("nCuenta")) {
                    int numeroCuenta=0;
                    numeroCuenta = Integer.parseInt(idOrigen);
//                    Se verifica SI que el numero de cuenta a la que se quiere buscar
//                    para realizar el deposito exista en el sistema con un usuario
//                    registrado 
                    if (servicioDeposito.verificarExistenciaNumCuenta(numeroCuenta)) {
                              
                       // Se pasan los valores a la clase bean para el manejo de informacion
                       // entre el servidor y manejo de la clase Deposito.jsp
                        bTCaja.seteMensaje("");
                        bTCaja.seteNumCuenta(idOrigen); // Se pasa el numero de cuenta
                        bTCaja.seteTipoBusqueda(bTCaja.geteTipoBusqueda());
                        bTCaja.seteExitenciaCuenta(false);
                        bTCaja.setCuentaSeleccionada(true);
                        sesionActual.setAttribute("descTCaja", 
                        new BeanTransCaja(bTCaja.geteNumCuenta(), 
                                bTCaja.geteTipoBusqueda(),bTCaja.getExitenciaCuenta(),
                                bTCaja.getCuentaSeleccionada()));
                        
                        // Si el numero de cuenta existe Servlet respode que la 
                        // operacion es uno y se toma como correcta.
                        request.getSession().setAttribute("servletMsjTCaja", "BusqDatosOrig2");
                      
                    }
                }
//               ------------------ FIN BUSQUEDA CUENTA ORIGEN -------------------
                
                // Si el tipo de busqueda seleccionado es por numero de cédula
                if (bTCaja2.geteTipoBusqueda()!=null && 
                        bTCaja2.geteTipoBusqueda().equals("nCedula")) {
                    
                    // Se verifica si la cedula ingresada existe en el sistema
                    // si se cuemple entra y manda la respuesta de que puede continuar
                    // con el deposito a esta cuenta
                    if (servicioLogin.verificarExistenciaCedulaCliente(checkId(idDestino))) {
                        // Si usuario existe se procede a buscarla cuentas asociadas
                        // Servlet respode que la operacion es uno y se toma como 
                        // correcta.
                        
                        // Se declara una lista de tipo de informacion de cuenta
                        List<cuenta> listaCuentas2;
                        
                        // Se cargan todas las cuentas que posea el numero de cedula
                        // que se indico buscar
                        listaCuentas2 = servicioDeposito.listarCuentasCliente(idDestino);
                        
                       // Se pasan los valores a la clase bean para el manejo de informacion
                       // entre el servidor y manejo de la clase Deposito.jsp
                       bTCaja2.seteExitenciaCuenta(true);
                       bTCaja2.seteCedula(idDestino);
                       bTCaja2.setLista(listaCuentas2);
                        sesionActual.setAttribute("descTCaja2", new BeanTransCaja2(
                                bTCaja2.geteCedula(),bTCaja2.geteTipoBusqueda(),
                                bTCaja2.getLista(), bTCaja2.getExitenciaCuenta()));
                        
                        // Se define que el servidor respondera con un 1 a depisto.jsp
                        // significando que la busqueda de la cedula ha sido exitoso
                        // y que se puede continuar con las acciones de deposito
                        request.getSession().setAttribute("servletMsjTCaja2", "BusqDatosDest1");
                    }
                    
                }else if (bTCaja2.getExitenciaCuenta() && !bTCaja2.getCuentaSeleccionada()){
                    String selectCuentaDest = request.getParameter("seletCuentaDestino");
                    
                            // Si el valor de cuenta seleccionada es diferente a nulo se 
                            // pasa la informacion al bean y se guardan
                            if (selectCuentaDest!=null){
                                bTCaja2.seteNumCuenta(selectCuentaDest);
                                bTCaja2.setCuentaSeleccionada(true);
                                sesionActual.setAttribute("descTCaja2", 
                                new BeanTransCaja2(bTCaja2.geteCedula(), bTCaja2.geteNumCuenta(), 
                                bTCaja2.geteTipoBusqueda(), bTCaja2.getExitenciaCuenta(),
                                bTCaja2.getCuentaSeleccionada()));
                                request.getSession().setAttribute("servletMsjTCaja2", "BusqDatosDest2");
                            
                            }else {
                                // Falso si la cuenta seleccionada es nula entonces no realiza
                                // ninguna accion pero guarda los datos obtenidos previos
                                sesionActual.setAttribute("descTCaja2", new BeanTransCaja2(
                                bTCaja2.geteCedula(),bTCaja2.geteTipoBusqueda(),bTCaja2.getLista(), 
                                bTCaja2.getExitenciaCuenta()));
                                request.getSession().setAttribute("servletMsjTCaja2", "BusqDatosDest1");
                            }
//                Falso si el tipo de busqueda seleccionado es por numero de cuenta
                } else if (bTCaja2.geteTipoBusqueda()!=null && bTCaja2.geteTipoBusqueda().equals("nCuenta")
                        ||!bTCaja2.getCuentaSeleccionada() ) {
                    int numeroCuenta=0;
                    numeroCuenta = Integer.parseInt(idDestino);
//                    Se verifica SI que el numero de cuenta a la que se quiere buscar
//                    para realizar el deposito exista en el sistema con un usuario
//                    registrado 
                    if (servicioDeposito.verificarExistenciaNumCuenta(numeroCuenta)) {
                              
                       // Se pasan los valores a la clase bean para el manejo de informacion
                       // entre el servidor y manejo de la clase Deposito.jsp
                        bTCaja2.seteMensaje("");
                        bTCaja2.seteNumCuenta(idDestino); // Se pasa el numero de cuenta
                        bTCaja2.seteTipoBusqueda(bTCaja2.geteTipoBusqueda());
                        bTCaja2.seteExitenciaCuenta(true);
                        bTCaja2.setCuentaSeleccionada(true);
                        sesionActual.setAttribute("descTCaja2", 
                        new BeanTransCaja2(bTCaja2.geteNumCuenta(), 
                                bTCaja2.geteTipoBusqueda(),bTCaja2.getExitenciaCuenta(),
                                bTCaja2.getCuentaSeleccionada()));
                        
                        // Si el numero de cuenta existe Servlet respode que la 
                        // operacion es uno y se toma como correcta.
                        request.getSession().setAttribute("servletMsjTCaja2", "BusqDatosDest2");
                    }
                }
//                ----------------- FIN BUSQUEDA CUENTA DESTINO ----------------

//                     Verifica que las dos cuentas: Origen y Destino tengan los datos
                    // completos para proceder a realizar una transferencia con esto
                    // se enviara un nuevo mensaje a la pagina para que habilite un
                    // boton donde el usuario lo accione y continuar con el tramite
                    if(bTCaja.getCuentaSeleccionada() && bTCaja.getExitenciaCuenta() 
                            && bTCaja2.getCuentaSeleccionada() && bTCaja2.getExitenciaCuenta()){
                        
                        if(!bTCaja.geteNumCuenta().equals(bTCaja2.geteNumCuenta())){
                        request.getSession().setAttribute("servletMsjTCaja3", "LISTO");
                        
                        }else if(bTCaja.geteNumCuenta().equals(bTCaja2.geteNumCuenta())){
                        bTCaja2.setCuentaSeleccionada(false);
                        bTCaja2.seteExitenciaCuenta(false);
                        tipoBusqCuenDest="";
                        bTCaja2.seteTipoBusqueda(tipoBusqCuenDest);
                        bTCaja2.seteMensaje("ERROR: El numero de la cuenta destino es igual que el numero de cuenta origen!");
                        request.getSession().setAttribute("servletMsjTCaja2", "ERROR");
                        }
                        sesionActual.setAttribute("descTCaja",
                        new BeanTransCaja(bTCaja.geteCedula(), bTCaja.geteNumCuenta(), 
                                bTCaja.geteTipoBusqueda(), bTCaja.getExitenciaCuenta(),
                                bTCaja.getCuentaSeleccionada()));
                        
                        sesionActual.setAttribute("descTCaja2",
                        new BeanTransCaja2(bTCaja2.geteCedula(), bTCaja2.geteNumCuenta(), 
                                bTCaja2.geteTipoBusqueda(), bTCaja2.getExitenciaCuenta(),
                                bTCaja2.getCuentaSeleccionada(), bTCaja2.geteMensaje()));
                    }
                
            } catch (Exception ex) {
                
                switch (ex.getMessage()) {
                   
                    // Si la exceppcion es de tipo #2 -- Significa que a la hora de 
                    // realizar la busqueda de la cedula no se obtubo una respuesta
                    // positiva por parte de la busqueda en la base de datos y que
                    // está es equivocada y no existe.
                    case "2":
                        
                        // Si ocurre un error en el formulario 2 de la cuenta origen 
                        // en donde el usuario ingresa una cedula no existente, entonces
                        // se verifica que no exista la cuenta y cuenta origne ya haya sido 
                        // seleccionada previamente 
                        if(!bTCaja.getExitenciaCuenta() && bTCaja.getCuentaSeleccionada()){
                            bTCaja.seteMensaje("Error: la identificación es incoorrecta o no pertenece al usuario!"
                                + "aún usurio en nuestro sistema!");
                            bTCaja.seteExitenciaCuenta(false);
                            sesionActual.setAttribute("descTCaja",
                            new BeanTransCaja(bTCaja.geteNumCuenta(), 
                                bTCaja.geteTipoBusqueda(),bTCaja.getExitenciaCuenta(),
                                bTCaja.getCuentaSeleccionada(),bTCaja.geteMensaje()));
                            request.getSession().setAttribute("servletMsjTCaja", "ERROR3");
                        
                       }
                        // Si el error se presenta en el primer formulario de busqueda de cuenta
                        // origen por busqueda por numero de cedula , entonces envia el error 
                        // al forumlario 1 de busqueda de cuenta origen
                        if(bTCaja.geteTipoBusqueda()!=null){
                            System.out.println("\n<--- NO EXISTE CLIENTE EN EL SISTEMA --->\n");
                            bTCaja.seteMensaje("Error: la identificación "+idOrigen+" origen no pertenece"
                                + "aún usurio en nuestro sistema!");
                            sesionActual.setAttribute("descTCaja", 
                            new BeanTransCaja(bTCaja.geteMensaje()));
                            request.getSession().setAttribute("servletMsjTCaja", "ERROR");
                        }
                        // Si el error se presenta en el primer formulario de busqueda de cuenta
                        // destino por busqueda por numero de cedula , entonces envia el error 
                        // al forumlario 1 de busqueda de cuenta destino
                        if (bTCaja2.geteTipoBusqueda()!=null){
                            bTCaja2.seteMensaje("Error: la identificación "+idDestino+" destino no pertenece"
                                + "aún usurio en nuestro sistema!");
                            sesionActual.setAttribute("descTCaja2", 
                            new BeanTransCaja2(bTCaja2.geteMensaje()));
                            request.getSession().setAttribute("servletMsjTCaja2", "ERROR");
                        }
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                        
                    // Si la exceppcion es de tipo #4 -- Significa que a la hora de 
                    // realizar la busqueda de la cuenta no se obtubo una respuesta
                    // positiva por parte de la busqueda en la base de datos y que
                    // está es equivocada y no existe.
                    case "4":
                        
                        // Verifica que el error en el numero de cuenta no existente 
                        // sea de formulario de la cuenta origen
                        if(bTCaja.geteTipoBusqueda()!=null){
                            bTCaja.seteMensaje("Error: El numero de cuenta"+idOrigen+" origen no pertenece"
                                + "aun usurio en nuestro sistema!");
                            sesionActual.setAttribute("descTCaja", 
                            new BeanTransCaja(bTCaja.geteMensaje()));
                            request.getSession().setAttribute("servletMsjTCaja", "ERROR");
                        } 
                        // Verifica que el error en el numero de cuenta no existente 
                        // sea de formulario de la cuenta destino
                        if (bTCaja2.geteTipoBusqueda()!=null){
                            bTCaja2.seteMensaje("Error: El numero de cuenta"+idDestino+" de destino no pertenece"
                                + "aun usurio en nuestro sistema!");
                            sesionActual.setAttribute("descTCaja2", 
                            new BeanTransCaja2(bTCaja2.geteMensaje()));
                            request.getSession().setAttribute("servletMsjTCaja2", "ERROR");
                        }
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                }
            }
                        // Envio datos y respuestas de actualizacion fuera del servidor
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
    }

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

    private final funcionesDeposito servicioDeposito = new funcionesDeposito();
    private final funcionesLogueo servicioLogin = new funcionesLogueo();

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
     * Returns a short descTCajation of the servlet.
     *
     * @return a String containing servlet descTCajation
     */
    @Override
    public String getServletInfo() {
        return "Short descTCajation";
    }// </editor-fold>

    
    
    private void vaciaVariables(BeanTransCaja bTCaja,BeanTransCaja2 bTCaja2){
                        bTCaja.seteTipoBusqueda("");
                        bTCaja2.seteTipoBusqueda("");
                        bTCaja.seteExitenciaCuenta(false);
                        bTCaja2.seteExitenciaCuenta(false);
                        bTCaja.setCuentaSeleccionada(false);
                        bTCaja2.setCuentaSeleccionada(false);
    }
    
    
    
    // Metodo que verifica si el usuario selecciona algun boton de navegacion
    // mostrados para cancelar las acciones de deposito
    
    protected void verificaOpcionesFormularioRegreso(RequestDispatcher dispatcher,
            HttpServletRequest request, HttpServletResponse response, String destino,
            BeanTransCaja bTCaja,BeanTransCaja2 bTCaja2,HttpSession sesionActual) 
            throws ServletException, IOException{
        try {
            
//            System.out.println("\n\n:::::::::VERIFICA FORM REGRESO!::::\n\n");
            String botonFormulario = "";
            botonFormulario = request.getParameter("regresoOpcion");
            if (botonFormulario != null && !botonFormulario.isEmpty()) {

                // Swicht para tomar las opciones recibidas del formulario de botones
                // de nueva cuenta
                switch (botonFormulario) {
                    
                    // Opción 1 - El usuario cajero desea regresar al menu principal
                    case "1":
                        destino = "/WEB-INF/Banco/Vista/Cajero.jsp";
                        vaciaVariables(bTCaja,bTCaja2);
                        request.getSession().setAttribute("servletMsjTCaja", null);
                        request.getSession().setAttribute("servletMsjTCaja2", null);
                        request.getSession().setAttribute("servletMsjTCaja3", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                    // Opción 2 - Ocurrio un error en la creacion de la cuenta y el
                   //  usuario desaa intentar de nuevo registar una nueva
                    case "2":
                        destino = "/WEB-INF/Banco/Vista/BusqCuentaTrans.jsp";
                        vaciaVariables(bTCaja,bTCaja2);
                        request.getSession().setAttribute("servletMsjTCaja", null);
                        request.getSession().setAttribute("servletMsjTCaja2", null);
                        request.getSession().setAttribute("servletMsjTCaja3", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;
                      // Opción 3 - El usuario cajero desea registar una nueva cuenta
                    case "3":
                        destino = "/WEB-INF/Banco/Vista/BusqCuentaTrans.jsp";
                        vaciaVariables(bTCaja,bTCaja2);
                        request.getSession().setAttribute("servletMsjTCaja", null);
                        request.getSession().setAttribute("servletMsjTCaja2", null);
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        break;

                        // Esta opcion es para el boton del furmulario final de la 
//                        pagina busqueda cuentas transferencias y en este se guardan
                        // todos los datos obtenidos de la cuenta origen - destino,
                        // se redireccion y se envian a la pagina de transfernecias
                    case "4":
                        destino = "/WEB-INF/Banco/Vista/TransferenciaCaja.jsp";
                        request.getSession().setAttribute("servletMsjTCaja3", "FORM1");
                        dispatcher = request.getRequestDispatcher(destino);
                        dispatcher.forward(request, response);
                        
                        break;
                }
                
            }
        } catch (NumberFormatException ex) {
        }
        
    }
    
    
}
