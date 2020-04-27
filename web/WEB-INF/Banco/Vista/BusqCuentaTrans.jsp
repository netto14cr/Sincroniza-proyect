<%-- 
    Document   : Retiros
    Created on : 28/03/2020, 03:08:35 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="beans.BeanTransCaja2"%>
<%@page import="beans.BeanTransCaja"%>
<%@page import="beans.BeanRetiro"%>
<%@page import="beans.BeanRetiro"%>
<%@page import="model.cuenta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Se declara la direccion y uso de estilos para dieseño de la pagina--> 
        <link href="css/estiloFormulario.css" rel="stylesheet" type="text/css"/>
        <!--Se declara la direccion y uso de javascript para validacion de la pagina-->
        <script type="text/javascript" src="jsValidaFormularios.js"></script>
        <!--Se define el uso de la etiqueta bean con datos de deposito bancario-->
        <jsp:useBean class="beans.BeanTransCaja" id="descTCaja" scope="session">
        </jsp:useBean>
        
        <jsp:useBean class="beans.BeanTransCaja2" id="descTCaja2" scope="session">
        </jsp:useBean>
        
        <title>Transferencia en cajas</title>
    </head>
    <body>
        <div id="wrapper">
            <div id="Transferencias">
            <header>
                <h1>Transferencia en cajas</h1>
            </header>
                
            </div>

            <div id="contents">
                <%
                System.out.println("::::::::::   VISTA BUSQUEDA CUENTAS T CAJAS   :::::::::");
                // Se obtienen los mensajes enviados por el Servlet en una variable
                // de tipo string la informacion enviada a la pagina de Deposito
                String msgCajas ="";
                String msgCajas2="";
                String msgCajas3="";
                msgCajas = (String) session.getAttribute("servletMsjTCaja");
                msgCajas2 = (String) session.getAttribute("servletMsjTCaja2");
                msgCajas3 = (String) session.getAttribute("servletMsjTCaja3");
                
                
                // Declacion del uso de la clase bean para manejor de datos
                BeanTransCaja bTCaja = (BeanTransCaja) session.getAttribute("descTCaja");
                request.getSession().getAttribute("bTCaja");
                BeanTransCaja2 bTCaja2= (BeanTransCaja2) session.getAttribute("descTCaja2");
                request.getSession().getAttribute("bTCaja2");
                
                if(msgCajas==null || msgCajas.isEmpty() || msgCajas!=null && msgCajas.equals("ERROR")){ 
                 if(msgCajas!=null  && msgCajas.equals("ERROR")){
                     %><p class="ErrorBusqCajaOrig">${descTCaja.geteMensaje()}</p><%}%>
                <!--Forumlario para la busqueda de cuentas de origen-->
                
                <form method="POST" action="fromBusqCuenOrig" class="e-BusCajaOrig" 
                      onsubmit="return valiFormBusqCajCuenOrigen()">

                    <h2> Busqueda cuenta Origen </h2>
                    <!--Campo para ingresar la identificación-->
                    <p>
                        <label class="texto">Buscar cuenta por:  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <select id="tipoBusqOrigen" name="tipoBusqOrigen" class="select">
                            <option value="nCedula" >Número de cédula</option>
                            <option value="nCuenta">Número de cuenta</option>
                        </select>

                    </p>
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero o cedula de la cuenta-->
                    <p>
                        <label class="texto">Cuenta a depositar:&nbsp;</label>
                        <input type="text" name="busqCuentaOrigen" id="busqCuentaOrigen" autofocus="autofocus"
                               placeholder="digite la cuenta " class="campo3" autocomplete="off" />
                    </p>
                    <p style="text-align: right;">
                        <button type="submit" id="button"name="BontonBuscarCuenta" class="boton"
                                onclick="" value="bOrigen">Buscar cuentas</button>
                    </p>
                </form>
                    
                <%} 
                        // condision que valida que miestras el mensaje del servidor 
                        // sea nulo se muestre el boton de volver a menu
                        if (msgCajas==null && msgCajas2==null){%>
                                        <!--Se muestra un formulario para que el cajero pueda cancelar la accion y volver a la 
                        pantalla anterior-->
                <form method="POST" action="regresarCaja" onsubmit=""  class="forBotonRegreso">
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonTransacion">Volver a menú</button>&nbsp;
                    </form>
                                        
                
                <%} 
                    // Muestra el formulario de busqueda de cuenta destino
                    // miestras el mensaje del servidor de busqueda 2 sea nulo o
                    // si este cambio su actualizacion a error en el formulario
                     if (msgCajas2==null || msgCajas2!=null && msgCajas2.equals("ERROR")){
                     
                     // Si el msj2 del servidor de busqueda es error muestra una ventana
                     // arriba del formulario con el error
                    if(msgCajas2!=null && msgCajas2.equals("ERROR")){%>
                     <p class="ErrorBusqCajaDest">${descTCaja2.geteMensaje()}</p><%}%>
                    
                  <!----------------Formulario de busqueda de cuenta de destino------------------>
                  
                <form method="POST" action="fromBusqCuenDest" class="e-BusqCajaDest" 
                      onsubmit="return valiFormBusqCajCuenDestino()">

                    <h3> Busqueda cuenta Destino </h3>
                    <!--Campo para ingresar la identificación-->
                    <p>
                        <label class="texto">Buscar cuenta por:  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <select id="tipoBusqDestino" name="tipoBusqDestino" class="select">
                            <option value="nCedula" >Número de cédula</option>
                            <option value="nCuenta">Número de cuenta</option>
                        </select>
                    </p>
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero o cedula de la cuenta-->
                    <p>
                        <label class="texto">Cuenta a depositar:&nbsp;</label>
                        <input type="text" name="busqCuentaDestino" id="busqCuentaDestino" 
                               autofocus="autofocus" placeholder="digite la cuenta " 
                               class="campo3" autocomplete="off" />
                    </p>
                    <p style="text-align: right;">
                        <button type="submit" id="button"name="BontonBuscarCuenta" class="boton"
                                onclick="" value="bDestino" >Buscar cuentas</button>
                    </p>
                </form>
                

                <%
//                    Muestra formulario de deposito cuenta por nuemro de cedula y
//                    en este froumlario se verifica que la persona sea dueña de la
//                    cuenta
                  
                    } if (msgCajas!=null && !msgCajas.isEmpty() 
                        && msgCajas.equals("BusqDatosOrig1") || msgCajas!=null && 
                        msgCajas.equals("ERROR2")) {
                       
                          // Si el msj1 del servidor de busqueda es error muestra una ventana
                        // arriba del formulario con el error
                        if(msgCajas!=null  && msgCajas.equals("ERROR2")){
                     %><p class="ErrorBusqCajaOrig">${descTCaja.geteMensaje()}</p><%}%>

                        <form method="POST" action="fromBusqCuenOrig" class="e-BusCajaOrig" 
                              onsubmit="return valiFormBusqCajCuenOrigen2()">
                            <h2> Cuenta origen</h2>     
                            
                    <!--Campo para ingresar la identificación-->
                        <p>
                            <label class="texto">Seleccion cuenta:</label>
                       
                        <select id="seletCuentaOrigen" name="seletCuentaOrigen" class="select2">
                            <%for (cuenta lis : bTCaja.getLista()) {%>
                            <option value="<%=lis.getNum_cuenta()%>"><%=lis.getNum_cuenta()%></option>
                            <% } %>
                        </select>
                    </p>
                    <h2> Verificacion de cuenta pertenece al usuario</h2> 
                    
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero o cedula de la cuenta-->
                    <p>
                        <label class="texto">Número identificación:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text" name="idUOrigen" id="idUOrigen" autofocus="autofocus"
                               placeholder="Identificación depositante " class="campo2" autocomplete="off" />
                    </p>

                    <p style="text-align: right;">
                        <button type="submit" class="boton">Verificar cuenta</button>
                    </p>
                </form> 
                      
                        <%
//                    Muestra formulario 2 ingreso de verificacion de numero de cedula
                   // de la persona dueña de la cuenta      

                  
                    }else if (msgCajas!=null && !msgCajas.isEmpty() 
                        && msgCajas.equals("BusqDatosOrig2") || msgCajas!=null 
                        && msgCajas.equals("ERROR3")) {
                         if (msgCajas!=null && msgCajas.equals("ERROR3")){%>
                            <p class="ErrorBusqCajaOrig">${descTCaja.geteMensaje()}</p><%}%>
                        <form method="POST" action="fromBusqCuenOrig" class="e-BusCajaOrig" 
                              onsubmit="return valiFormBusqCajCuenOrigen3()">
                            <h2> Cuenta origen</h2>     
                            
                        <!--Campo para mostrar el numero de cuenta ingresado previamente
                                a la cual se realizara el deposito-->
                    <p>
                        <label class="texto">Número de cuenta :&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text"  class="campo2" readonly="true" value="${descTCaja.geteNumCuenta()}"/>
                    </p>

                    <h2> Verificacion de cuenta pertenece al usaurio</h2> 
                    
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero o cedula de la cuenta-->
                    <p>
                        <label class="texto">Número identificación:</label>
                        <input type="text" name="idUserOrigen" id="idUserOrigen" autofocus="autofocus"
                               placeholder="Identificación depositante " class="campo2" autocomplete="off" />
                    </p>

                    <p style="text-align: right;">
                        <button type="submit" class="boton">Verificar cuenta</button>
                    </p>
                </form> 
                    
                  
                    
                    <%// Se muestra la acyualizacion del formulario  al usuario 
                        // con la cuenta de origen que el servidor acepto y 
                        // guardo su valor 
                    } else if (msgCajas!=null && !msgCajas.isEmpty() && 
                        msgCajas.equals("SELECT_ORIGEN")) {
                        %>
                        <form method="POST" action="" class="e-BusCajaOrig" >
                            <h2> Datos cuenta origen completa</h2>
                         <!--Campo para mostrar el numero de cuenta ingresado previamente
                                a la cual se realizara el deposito-->
                    <p>
                        <label class="texto">Número de cuenta :&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text"  class="campo2" readonly="true" value="${descTCaja.geteNumCuenta()}"/>
                    </p>
                    
                    <p>
                        <label class="texto">Número identificación:</label>
                        <input type="text" name="" id="" autofocus="autofocus"
                               class="campo2" readonly="true" value="${descTCaja.geteCedula()}" />
                    </p>
                </form> 
                        
                    
                    
                         <%
                       // Muestra forumulario 2 de destino en caso que el usuario 
                       // ingreso la cedula en la busqueda de la cuenta y ahora
                       // se muestra las opciones para que seleccione la cuenta
                    } if (msgCajas2!=null && !msgCajas2.isEmpty() && 
                        msgCajas2.equals("BusqDatosDest1")) {
                        %>
                        <form method="POST" action="fromBusqCuenDest" class="e-BusqCajaDest" 
                              onsubmit="return validarForumlarioRetiro2()">
                            <h3>Cuenta destino</h3>
                        <p>
                            <label class="texto">Seleccion cuenta:</label>
                       
                        <select id="seletCuentaDestino" name="seletCuentaDestino" class="select">
                            <%for (cuenta lis : bTCaja2.getLista()) {%>
                            <option value="<%=lis.getNum_cuenta()%>"><%=lis.getNum_cuenta()%></option>
                            <% } %>
                        </select>
                    </p>
                    <p style="text-align: right;">
                        <button type="submit" class="boton">Confirmar cuenta destino</button>
                    </p>
                </form> 
                        
                        
                 <%// Se muestra la informacion de la cuenta seleccionada por el usuario
                     // de destino para realizar la transaccion
                    } else if (msgCajas2!=null && !msgCajas2.isEmpty() && 
                        msgCajas2.equals("BusqDatosDest2")) {
                        %>
                        <form method="POST" action="fromBusqCuenDest" class="e-BusqCajaDest" 
                              onsubmit="return validarForumlarioRetiro2()">
                            <h3>Cuenta destino</h3>
                         <!--Campo para mostrar el numero de cuenta ingresado previamente
                                a la cual se realizara el deposito-->
                    <p>
                        <label class="texto">Número de cuenta :&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text"  class="campo2" readonly="true" value="${descTCaja2.geteNumCuenta()}"/>
                    </p>
                </form> 
                    
                    <%}
                        // Si mensaje de servelet es listo significa que las cuentas 
//                            de origen y destino han seleccionado sus datos y mostrara
//                            un formulario con un boton de continuar
                        if (msgCajas3!=null && !msgCajas3.isEmpty() && msgCajas3.equals("LISTO")){%>
                    
<!--                         Formulario que permite continuar con el proceso para realizar
                         la transaccion mediante el boton en pantalla-->
                                        
                <form method="POST" action="regresarCaja" onsubmit=""  class="forBotonRegreso">
                       <br><br><br><br><br>
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="2" class="botonRegresoInteres2">Cancelar acción</button>&nbsp;
                                
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegresoInteres">Volver a menú</button>&nbsp;
                                
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="4" class="botonTransacion">Continuar transaccion</button>&nbsp;
                    </form>
                    
                    </div>
                    <%// Acutlaizacion de botones mientras los mensajes del servidor sean
                      // diferentes al estado inicial (nulo) y que mensaje 3 siga siendo nulo
                      // entonces se muestras las opciones del formulario con los botones de
                      // cancelar acciones realizadas y el de volver
                    } if (msgCajas!=null && !msgCajas.isEmpty()
                        && msgCajas3==null ||
                        msgCajas2!=null && !msgCajas2.isEmpty()
                        && msgCajas3==null) {
                        %>
                 
                                  <!--Se muestra un formulario para que el cajero pueda cancelar la accion y volver a la 
                        pantalla anterior-->
                <form method="POST" action="regresarCaja" onsubmit=""  class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="2" class="botonRegresoInteres2">Cancelar acción</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegresoInteres">Volver a menú</button>&nbsp;
                    </form>
                    
                   <% } %>
                      
                   
            
                
            <footer></footer>
        </div>

    </body>
</html>
