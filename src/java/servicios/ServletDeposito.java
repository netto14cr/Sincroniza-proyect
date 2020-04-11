package servicios;

import beans.BeanDeposito;
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

/*
 * ServletInicionSesion.java
 * Autores: Gabriel Barboza && Néstor Leiva
 * Descripcion: Clase java tipo servlet de Depositvos del usuario cajero 
 */
@WebServlet(
        name = "ServletDeposito",
        urlPatterns = {"/ServletDeposito", "/deposito-cajero", "/deposito-cajero2"}
)

public class ServletDeposito extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        // Se define de que direccion viene el usaurio
        String destino = "";
        destino = "/WEB-INF/Banco/Vista/Deposito.jsp";
        HttpSession sesionActual = request.getSession();
        sesionActual.setAttribute("ruta", request.getRequestURI());

        RequestDispatcher dispatcher = null;
        // Se obtiene los valores guardados en el beanNueva Cuenta
        BeanDeposito bDep = (BeanDeposito) sesionActual.getAttribute("descrip");
        request.getSession().getAttribute("bDep");

     

            System.out.println("\n:::::::  SERVLET DEPOSTIO   ::::::::");
            String detalleDep;
            String tipoBus;
            tipoBus = "";
            detalleDep = "";
            int numeroCuentaSelect;
            numeroCuentaSelect = 0;
            String montoDep;montoDep = "";
            String detNombre; detNombre="";
            String detNumIden;detNumIden="";
            String errorGetDatos; errorGetDatos="";
            boolean existeCuentaAsociada;
            existeCuentaAsociada=false;
            
            tipoBus = bDep.geteTipoBusqueda();
            System.out.println("TIPO B:::"+tipoBus);
            if (tipoBus != null) {
                
                // Si la forma de deposito es por numero de cedula
                if (tipoBus.equals("nCedula")) {
                    montoDep = request.getParameter("montoDeposito");
                    detalleDep = request.getParameter("detalleDep");
                    detNumIden=request.getParameter("detalleNumId");
                    detNombre=request.getParameter("detalleNombreDep");
                        
                    // Verifica si el campo de monto a depositar tiene datos o ingresa vacio
                    if (montoDep != null && !montoDep.isEmpty() && detNumIden!=null) {
                        numeroCuentaSelect = Integer.parseInt(request.getParameter("seletCuenta"));
                        bDep.seteNumCuenta(numeroCuentaSelect + "");
                        bDep.seteTipoBusqueda(tipoBus);
                        bDep.seteDetalleDeposito(detalleDep);
                        
                        // Verifica si el numero de cuenta ingresado por cedula y el numero de
                        // identificación son iguales entonces el es el dueño de la cuenta
                        if (detNumIden.equals(bDep.geteCedula())){
                            try {
                                // Se pasa el mensaje de que la operacion se relizo con exito
                                // para mostrar entre la información al usuario
                                bDep.seteMensaje("El deposito se realizo con exito!");
                                // Se actualizan los datos del bean Deposito con la información
                                // del deposito realizado para mostrarla al usuario cajero
                                sesionActual.setAttribute("descrip",
                                        new BeanDeposito(bDep.geteCedula(),detNumIden,bDep.geteNumCuenta(),
                                                "",montoDep,bDep.geteDetalleDeposito(), bDep.geteMensaje(),
                                                bDep.geteTipoBusqueda()));
                                
                                // Se pasan los datos obtenidos  del deposito a movimientos
                                mov.setMonto(Double.parseDouble(montoDep));
                                mov.setCuenta_num_cuenta(numeroCuentaSelect);
                                mov.setDetalle(detalleDep);
                                // Se realiza el deposito en la base de datos
                                fDeposito.realizarDepositoACuentaRefact(mov);
                                
                                // Se responde a la pagina deposito.jsp con 1 en mensaje de
                                // deposito 2 indicando el deposito ha sido efectuadp correctamente
                                // y que tipo de pantalla debe mostrar al usuario cajero
                                request.getSession().setAttribute("servletMsjDeposito2", "DEPREADY");
                            } catch (Exception ex) {
                               
                            }
                            
                        }
                        // Falso si la cuenta buscada y el numero de identificación son diferente 
                        // entonces no es el dueño de la cuenta
                        else if (!detNumIden.equals(bDep.geteCedula()) && detNombre==null){
                          // Se eniva un mensaje por medio del bean para mostrarlo al suario
                          // en pantalla
                          bDep.seteMensaje("El sistema detecto que la cuenta no pertenece"
                                  + "a la persona identificada. Se requiere ingresar el nombre"
                                  + "de detalle de depositante!");
                          sesionActual.setAttribute("descrip", 
                            new BeanDeposito(bDep.geteCedula(),detNumIden,bDep.geteNumCuenta(),
                                 "",montoDep,bDep.geteDetalleDeposito(),bDep.geteMensaje(),
                                    bDep.geteTipoBusqueda()));

                          // El servlet avisa a la pagina Depostio.jsp que el usuario no
                          // no es dueño de la cuenta para que se le muestre un nuevo campo 
                          // de detalle de cuenta que debe llenar
                          request.getSession().setAttribute("servletMsjDeposito2", "2");
                          
                        }
                        // Si detalle de nombre es diferenta a vacio o nulo 
                        else if (!detNumIden.equals(bDep.geteCedula()) && detNombre!=null){
                            
                            try {
                                // Se pasa el mensaje de que la operacion se relizo con exito
                                // para mostrar entre la información al usuario
                                bDep.seteMensaje("El deposito ha sido realizado con exito!");
                                bDep.seteNombreUs(detNombre);
                                // Se actualizan los datos del bean Deposito con la información
                                // del deposito realizado para mostrarla al usuario cajero
                                sesionActual.setAttribute("descrip",
                                        new BeanDeposito(bDep.geteCedula(),detNumIden,bDep.geteNumCuenta(),
                                                bDep.geteNombreUs(),montoDep,detalleDep, bDep.geteMensaje(),
                                                ""));
                                
                                //--- Aqui se setea el detalle de nombre depostiante ----
                                
                                // Se pasan los datos obtenidos  del deposito a movimientos
                                mov.setMonto(Double.parseDouble(montoDep));
                                mov.setCuenta_num_cuenta(numeroCuentaSelect);
                                mov.setDetalle(detalleDep);
                                
                                // Se realiza el deposito en la base de datos
                                fDeposito.realizarDepositoACuentaRefact(mov);
                                
                                // Se responde a la pagina deposito.jsp con 1 en mensaje de
                                // deposito 2 indicando el deposito ha sido efectuadp correctamente
                                // y que tipo de pantalla debe mostrar al usuario cajero
                                request.getSession().setAttribute("servletMsjDeposito2", "DEPREADY2");
                            } catch (Exception ex) {
                                
                            }
                        }
                    }
                }
                // Falso si la forma de deposito es por numero de cuenta
                 else if (tipoBus.equals("nCuenta")) {
                     
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
                            
                            
                            if(fLogin.verificarPosibleCedulaCliente(detNumIden)){
                                
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
                                            ==Integer.parseInt(bDep.geteNumCuenta())){
                                        existeCuentaAsociada=true;
                                    }
                                }
                            }
                            if(existeCuentaAsociada){
                                
                                // Se pasa la información al bean
                                bDep.seteDetalleDeposito(detalleDep);
                                bDep.seteMontoDeposito(montoDep);
                                bDep.seteNumCuenta(bDep.geteNumCuenta());
                                bDep.seteCedula(detNumIden);
                                bDep.seteMensaje("El deposito ha sido realizado con exito!");
                                
                                // Se actualizan los datos del bean Deposito con la información
                                // del deposito realizado para mostrarla al usuario cajero
                                sesionActual.setAttribute("descrip",
                                        new BeanDeposito(bDep.geteNumCuenta(),bDep.geteCedula(),
                                                bDep.geteMontoDeposito(),bDep.geteDetalleDeposito(),
                                                bDep.geteMensaje()));
                                
                                // Se pasan los datos a obtenidos a movimientos
                                mov.setCuenta_num_cuenta(Integer.parseInt(bDep.geteNumCuenta()));
                                mov.setMonto(Integer.parseInt(montoDep));
                                // Se reliza el deposito en la base de datos
                                fDeposito.realizarDepositoACuentaRefact(mov);
                                // Se actualiza el mensaje al servidor como deposito listo
                                request.getSession().setAttribute("servletMsjDeposito2", "DEPREADY3");
                                
                            }// Falso si no existe una cuenta asociada y el detalle de nombre
                            // es vacio o nulo, se le tiene que pedir a usuario que ingrese la
                            // informacion de este campo
                            else if (!existeCuentaAsociada && detNombre==null){
                                
                                // Se eniva un mensaje por medio del bean para mostrarlo al suario
                                // en pantalla
                                bDep.seteMensaje("El sistema detecto que la cuenta no pertenece"
                                        + "a la persona identificada. Se requiere ingresar el nombre"
                                        + "de detalle de depositante!");
                                
                                // Se actualizan los datos del bean Deposito con la información
                                // del deposito realizado para mostrarla al usuario cajero
                                sesionActual.setAttribute("descrip",
                                        new BeanDeposito(bDep.geteCedula(),detNumIden,bDep.geteNumCuenta(),
                                                "",montoDep,detalleDep, bDep.geteMensaje(),
                                                bDep.geteTipoBusqueda()));
                                
                                // Se actualiza el mensaje al servidor que el usario no es el dueño de
                                // la cuenta  pasando un numero 3
                                request.getSession().setAttribute("servletMsjDeposito2", "3");
                                 
                            }// Falso si no es dueño de la cuenta y ya ingreso su nombre en 
                            // valor de detalle nombre entonces se procede a actualizar los 
                            // datos y a realizar el deposito en la cuenta
                            else if (!existeCuentaAsociada && detNombre!=null){
                                
                                bDep.seteNombreUs(detNombre);
                                bDep.seteMontoDeposito(montoDep);
                                bDep.seteDetalleDeposito(detalleDep);
                                bDep.seteMensaje("El deposito ha sido realizado con exito!");
                                // Se actualizan los datos en el bean deposito para mostrarlos
                                // en la pagina de deposito.jsp
                                sesionActual.setAttribute("descrip",
                                        new BeanDeposito(bDep.geteCedula(),detNumIden,bDep.geteNumCuenta(),
                                                bDep.geteNombreUs(),bDep.geteMontoDeposito(),
                                                bDep.geteDetalleDeposito(), bDep.geteMensaje(),
                                                ""));
                                
                                // Se pasan los datos obtenidos a los movimientos
                                mov.setCuenta_num_cuenta(Integer.parseInt(bDep.geteNumCuenta()));
                                mov.setMonto(Double.parseDouble(montoDep));
                                mov.setDetalle(detalleDep);
                                // Se reliza el deposito en la base de datos
                                fDeposito.realizarDepositoACuentaRefact(mov);
                                
                                // Se responde a la pagina deposito.jsp con 6 indicando que
                                // la transaccion de deposito ha sido efectuada correctamente
                                request.getSession().setAttribute("servletMsjDeposito2", "DEPREADY4");
                                
                            }
                        } catch (Exception ex) {
                            switch (ex.getMessage()) {
                case "6":
                    bDep.seteMensaje("NO SE PUDO REALIZAR EL DEPOSITVO\nERROR:" + ex.getMessage() + "\n");
                    sesionActual.setAttribute("descrip", new BeanDeposito(bDep.geteMensaje()));

                    request.getSession().setAttribute("servletMsjDeposito2", "10");
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
        }
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
