<%-- 
    Document   : Deposito
    Created on : 21/03/2020, 03:07:01 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="beans.BeanDeposito"%>
<%@page import="java.awt.Button"%>
<%@page import="com.sun.glass.events.MouseEvent"%>
<%@page import="modelo.dao.servicioCuenta"%>
<%@page import="modelo.dao.funcionesFrontEnd.funcionesDeposito"%>
<%@page import="java.util.List"%>
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
        <jsp:useBean class="beans.BeanDeposito" id="descrip" scope="session">
        </jsp:useBean>
        
        <title>Deposito-Cajero</title>
    </head>
    <body>
        <div id="wrapper">
            <div id="Deposito1">
                 <header>
                    <h1>Deposito</h1>
                 </header>
            </div>


            <div id="contents">
                <%
                System.out.println("::::::::::   VISTA DEPOSITO BANCARIO    :::::::::");
                // Se obtienen los mensajes enviados por el Servlet en una variable
                // de tipo string la informacion enviada a la pagina de Deposito
                String msgDeposito ="";
                String msgDeposito2="";
                msgDeposito = (String) session.getAttribute("servletMsjDeposito");
                msgDeposito2 = (String) session.getAttribute("servletMsjDeposito2");
                
                // Declacion del uso de la clase bean para manejor de datos
                BeanDeposito bDep = (BeanDeposito) session.getAttribute("descrip");
                request.getSession().getAttribute("bDep");
                // Se valida que mientras los mensajes del servlet sean nulos o 
                // vacios se muestre el primer formulario de busqueda de cuenta 
                // a depositar
                
                String tipoBusqueda = request.getParameter("tipoBusquedaCuenta");
                String numeroCuenta = request.getParameter("nCuentaDeposito");
                
                if(msgDeposito==null || msgDeposito.isEmpty()){ %>
                <form method="POST" action="busquedaCuenta-cajero" class="e-deposito" 
                      onsubmit="return validarForumlarioDeposito1()">
                    <!--Campo para ingresar la identificación-->
                    <p>
                        <label class="texto">Buscar cuenta por:  &nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;</label>
                        <select id="tipoBusquedaCuenta" name="tipoBusquedaCuenta" 
                                class="select">
                            <option value="nCedula" >Número de cédula</option>
                            <option value="nCuenta">Número de cuenta</option>
                        </select>

                    </p>
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero o 
                    cedula de la cuenta-->
                    <p>
                        <label class="texto">Cuenta a depositar:&nbsp;</label>
                        <input type="text" name="nCuentaDeposito" id="nCuentaDeposito" 
                               autofocus="autofocus"
                               placeholder="digite la cuenta " class="campo" autocomplete="off" />
                    </p>
                    <p style="text-align: right;">
                        <button type="submit" id="button"name="BontonBuscarCuenta" class="boton"
                                onclick="" >Buscar cuentas</button>
                    </p>
                </form>
                    
                <!--Se muestra un formulario para que el cajero pueda volver al menu principal-->
                <form method="POST" action="regresarDeposito" onsubmit=""  class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4">Volver a menú</button>&nbsp;
                    </form>
                <!--TABLA DOS PARA DEPOSITO-->
                <%
                    // Condision si el usuario ha ingresado un valor cuando le 
                    // dio buscar cuentas
                  
                    }else if (msgDeposito!=null && msgDeposito.equals("1") 
                        && msgDeposito2==null) {%>

                        <form method="POST" action="deposito-cajero" class="e-deposito2" 
                              onsubmit="return validarForumlarioDeposito2()">

                    <!--Campo para ingresar la identificación-->
                        <p>
                            <label class="texto">Seleccion cuenta:</label>
                       
                        <select id="seletCuenta" name="seletCuenta" class="select">
                            <%for (cuenta lis : bDep.getLista()) {%>
                            <option value="<%=lis.getNum_cuenta()%>">
                                <%=lis.getNum_cuenta()%></option>
                            <% } %>
                        </select>
                    </p>

                    <!--Se define la etiqueta y el campo para que el cajero ingrese 
                    el monto a depositar-->
                    <p>
                        <label class="texto">Monto a deposito:&nbsp;&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;</label>
                        <input type="text" id="montoDeposito" name="montoDeposito" 
                               autofocus="autofocus"
                               placeholder=" Monto deposito " class="campo2"/>
                    </p>
                    
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero o 
                    cedula de la cuenta-->
                    <p>
                        <label class="texto">Número identificación:</label>
                        <input type="text" name="detalleNumId" id="detalleNumId" 
                               autofocus="autofocus"
                               placeholder="Identificación depositante " class="campo2" 
                               autocomplete="off" />
                    </p>

                    <!--Se define la etiqueta y el campo para que el cajero ingrese el 
                    detalle del deposito-->
                    <p>
                        <label class="texto">Detalle de deposito:&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text" id="detalleDep" name="detalleDep" autofocus="autofocus"
                               placeholder="Detalle de deposito" class="campo2"/>
                    </p>
                    <p style="text-align: right;">
                        <button type="submit" class="boton">Realizar deposito</button>
                    </p>
                </form> 
                        <!--Se muestra un formulario para que el cajero pueda cancelar 
                        la accion y volver a la 
                        pantalla anterior-->
                <form method="POST" action="regresarDeposito" onsubmit=""  class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="2" class="botonCuenta">Cancelar acción</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4">Volver a menú</button>&nbsp;
                    </form>
                        
<!--                    Se muestra el 3 formulario con una pequeña variante en 
                        campo de detalle de nombre de depositante si no es el dueño de la cuenta-->
                        <%}else if (msgDeposito2!=null && msgDeposito2.equals("2")){ %>   
                         <p class="mensajeErrorDep">
                        ${descrip.geteMensaje().toString()} </p> 
                         
                          
                    <form method="POST" action="deposito-cajero2" class="e-deposito2" 
                          onsubmit="return validarForumlarioDeposito3()">

                    <!--Campo para ingresar la identificación-->
                    <p>
                        <label class="texto">Cuenta seleccionada:&nbsp;</label>
                        <input type="text" name="seletCuenta" id="seletCuenta" 
                               autofocus="autofocus"
                               class="campo2" readonly="true" value="${descrip.geteNumCuenta()}"/>
                    </p>

                    <!--Se define la etiqueta y el campo para que el cajero ingrese 
                    el monto a depositar-->
                    <p>
                        <label class="texto">Monto a deposito:&nbsp;&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;</label>
                        <input type="text" id="montoDeposito" name="montoDeposito" 
                               autofocus="autofocus"placeholder=" Monto deposito " 
                               class="campo2" 
                               value="${descrip.geteMontoDeposito()}"/>
                    </p>
                    
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero o 
                    cedula de la cuenta-->
                    <p>
                        <label class="texto">Número identificación:</label>
                        <input type="text" name="detalleNumId" id="detalleNumId" 
                               autofocus="autofocus"
                               placeholder="Identificación depositante " class="campo2" 
                               autocomplete="off" value="${descrip.geteCedulaDet()}" />
                    </p>

                    <!--Se define la etiqueta y el campo para que el cajero ingrese el detalle 
                    del deposito-->
                    <p>
                        <label class="texto">Detalle de deposito:&nbsp;&nbsp;
                            &nbsp;&nbsp;</label>
                        <input type="text" id="detalleDep" name="detalleDep" 
                               autofocus="autofocus"
                               placeholder="Detalle de deposito" class="campo2" 
                               value="${descrip.geteDetalleDeposito()}"/>
                    </p>
                         <!--Campo para ingresar la identificación-->
                    <p>
                        <label class="texto">Nombre depositante:&nbsp;&nbsp;</label>
                        <input type="text" name="detalleNombreDep" autofocus="autofocus"
                               placeholder="Nombre depositante" class="campo2"
                               id="detalleNombreDep"/>
                    </p>
                    <p style="text-align: right;">
                        <button type="submit" class="boton">Realizar deposito</button>
                    </p>
                </form> 
                        
                   <!--Se muestra un formulario para que el cajero pueda cancelar la 
                   accion y volver a la pantalla anterior-->
                <form method="POST" action="regresarDeposito" onsubmit=""  class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="2" class="botonCuenta">Cancelar acción</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4">Volver a menú</button>&nbsp;
                    </form> 
                   
                   
                   <%   }           // Falso si se realizo el deposito correctamente
                        else if (msgDeposito.equals("1") && msgDeposito2.equals("DEPREADY")){%>
                          
                        <p class="mensajeCorrecto"> Se ha completado el deposito correctamente
                        </p>
                        <p class="mensajeDepositoR">
                            <strong>N° Céd. cuenta dep   :&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteCedula()}<br>
                            <strong>N° Cuenta deposito   :&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteNumCuenta()}<br>
                            <strong>Monto transferencia  :&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteMontoDeposito()}<br>
                            <strong>Detalle Deposito     :&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteDetalleDeposito()}<br>
                        </p>
                        
                        <!--Formulario de opciones para volver a realizar un deposito 
                        o regresar al menu principal-->
                        <form method="POST" action="regresarDeposito" onsubmit=""  
                              class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                        <button type="submit" name="regresoOpcion" value="3" 
                                class="botonRegreso">Realizar otro deposito</button>&nbsp;
                    </form>
                
                        
                        
                        <%}else if (msgDeposito.equals("1") && 
                                msgDeposito2.equals("DEPREADY2")){%>
                        <p class="mensajeCorrecto"> ${descrip.geteMensaje()}</p>
                        
                             <p class="mensajeDepositoR">
                            <strong>N° Cédula cuenta deposito:&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteCedula()}<br>
                            <strong>N° Identificacion depositante   :&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteCedulaDet()}<br>
                            <strong>N° Cuenta deposito   :&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteNumCuenta()}<br>
                            <strong>Monto transferencia  :&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteMontoDeposito()}<br>
                            <strong>Detalle Deposito     :&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteDetalleDeposito()}<br>
                            <strong>Nombre detalle dep.  :&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteNombreUs()}<br>
                        </p>
                        
                         <!--Formulario de opciones para volver a realizar un deposito 
                         o regresar al menu principal-->
                        <form method="POST" action="regresarDeposito" onsubmit=""  
                              class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                        <button type="submit" name="regresoOpcion" value="3" 
                                class="botonRegreso">Realizar otro deposito</button>&nbsp;
                    </form>

                 <%
                // Falso si la busqueda seleccionada es por número de cuenta
                }else if (msgDeposito.equals("2") && msgDeposito2==null) {
                    System.out.println("\n\n++++FORM 3+++++CUENTA++++ \n\n");
                %>
                <form method="POST" action="deposito-cajero" class="e-deposito2" 
                      onsubmit="return validarDepNumCuenta()">

                    <!--Campo para mostrar el numero de cuenta ingresado previamente
                    a la cual se realizara el deposito-->
                    <p>
                        <label class="texto">Número de cuenta :&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text"  class="campo2" readonly="true" 
                               value="${descrip.geteNumCuenta()}"/>
                    </p>

                    
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero 
                    o cedula de la cuenta-->
                    <p>
                        <label class="texto">Número identificación:</label>
                        <input type="text" name="detalleNumId" id="detalleNumId" 
                               autofocus="autofocus"
                               placeholder="Identificación depositante " class="campo2" 
                               autocomplete="off" />
                    </p>
                    
                    
                    <!--Se define la etiqueta y el campo para que el cajero ingrese 
                    el monto a depositar-->
                    <p>
                        <label class="texto">Monto a depositar :&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;</label>
                        <input type="text" id="detalleMontoDep" name="detalleMontoDep" 
                               autofocus="autofocus"
                               placeholder="Monto deposito" class="campo2"/>
                    </p>

                    <!--Se define la etiqueta y el campo para que el cajero ingrese 
                    el detalle del deposito-->
                    <p>
                        <label class="texto">Detalle de deposito :&nbsp;</label>
                        <textarea type="text" name="detalleDepTxt" id="detalleDepTxt" 
                                  autofocus="autofocus"
                                  placeholder=" Ingresar aqui el detalle de deposito" 
                                  class="textarea"></textarea>
                    </p>
                    <br>
                    <p>
                        <button type="submit" class="botonRegreso">Realizar deposito</button>
                    </p>
                </form> 
                    <!--Fromulario para cancelar la operacion de realizar el deposito-->
                    <form method="POST" action="regresarDeposito" onsubmit="" 
                          class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="2" class="botonCuenta">Cancelar acción</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4"  >Volver a menú</button>&nbsp;
                    </form>
                 
                     <%
                // Falso si la busqueda seleccionada es por número de cuenta
                }else if (msgDeposito.equals("2") && msgDeposito2.equals("3")) {%>
                
                <p class="mensajeErrorDep">
                        ${descrip.geteMensaje().toString()} </p> 
                <form method="POST" action="deposito-cajero" class="e-deposito2" 
                      onsubmit="return validarDepNumCuenta2()">

                    <!--Campo para mostrar el numero de cuenta ingresado previamente
                    a la cual se realizara el deposito-->
                    <p>
                        <label class="texto">Número de cuenta :&nbsp;&nbsp;&nbsp;</label>
                        <input type="text"  class="campo2" readonly="true" 
                               value="${descrip.geteNumCuenta()}"/>
                    </p>

                    <!--Campo para ingresar la identificación-->
                    <p>
                        <label class="texto">Nombre depositante:&nbsp;</label>
                        <input type="text" name="detalleNombreDep" autofocus="autofocus"
                               placeholder="Nombre depositante" class="campo2"
                               id="detalleNombreDep"/>
                    </p>
                    
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero 
                    o cedula de la cuenta-->
                    <p>
                        <label class="texto">Número identificación:</label>
                        <input type="text" name="detalleNumId" id="detalleNumId" 
                               autofocus="autofocus"
                               placeholder="Identificación depositante " class="campo2" 
                               value="${descrip.geteCedulaDet()}" />
                    </p>
                    
                    
                    <!--Se define la etiqueta y el campo para que el cajero ingrese el
                    monto a depositar-->
                    <p>
                        <label class="texto">Monto a depositar:&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;</label>
                        <input type="text" id="detalleMontoDep" name="detalleMontoDep" 
                               autofocus="autofocus"
                               placeholder="Monto deposito" class="campo2" 
                               value="${descrip.geteMontoDeposito()}"/>
                    </p>

                    <!--Se define la etiqueta y el campo para que el cajero ingrese 
                    el detalle del deposito-->
                    <p>
                        <label class="texto">Detalle de deposito:&nbsp;</label>
                        <textarea type="text" name="detalleDepTxt" id="detalleDepTxt" 
                                  autofocus="autofocus"
                                  class="textarea">${descrip.geteDetalleDeposito()}</textarea>
                    </p>
                    <br>
                    <p>
                        <button type="submit" class="botonRegreso">Realizar deposito</button>
                    </p>
                </form> 
                    
                    <!--Fromulario para cancelar la operacion de realizar el deposito-->
                    <form method="POST" action="regresarDeposito" onsubmit="" 
                          class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="2" class="botonCuenta">Cancelar acción</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4"  >Volver a menú</button>&nbsp;
                    </form>
                    
                <%   }           // Falso si se realizo el deposito correctamente

                        else if (msgDeposito.equals("2") && msgDeposito2.equals("DEPREADY3")){%>
                          
                        <p class="mensajeCorrecto"> Se ha completado el deposito correctamente
                        </p>
                        <p class="mensajeDepositoR">
                           <strong>N° Cédula dueño cuenta:&nbsp;&nbsp;&nbsp;</strong>
                           ${descrip.geteCedula()}<br>
                            <strong>N° Cuenta deposito   :&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteNumCuenta()}<br>
                            <strong>Monto transferencia  :&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteMontoDeposito()}<br>
                            <strong>Detalle Deposito     :&nbsp;&nbsp;&nbsp;</strong>
                            ${descrip.geteDetalleDeposito()}<br>
                        </p>
                        
                        <!--Formulario de opciones para volver a realizar un deposito 
                        o regresar al menu principal-->
                        <form method="POST" action="regresarDeposito" onsubmit=""  
                              class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                        <button type="submit" name="regresoOpcion" value="3" 
                                class="botonRegreso">Realizar otro deposito</button>&nbsp;
                    </form>    
                    
                <%}else if (msgDeposito.equals("2") && msgDeposito2.equals("DEPREADY4")){
                        System.out.println("DEEEEEP2");%>
                        <p class="mensajeCorrecto"> ${descrip.geteMensaje()}</p>
                        
                             <p class="mensajeDepositoR">
                            <strong>N° Identificacion depositante   :&nbsp;&nbsp;
                                &nbsp;</strong>${descrip.geteCedulaDet()}<br>
                            <strong>N° Cuenta deposito   :&nbsp;&nbsp;&nbsp;
                            </strong>${descrip.geteNumCuenta()}<br>
                            <strong>Monto transferencia  :&nbsp;&nbsp;&nbsp;
                            </strong>${descrip.geteMontoDeposito()}<br>
                            <strong>Detalle Deposito     :&nbsp;&nbsp;&nbsp;
                            </strong>${descrip.geteDetalleDeposito()}<br>
                            <strong>Nombre detalle dep.  :&nbsp;&nbsp;&nbsp;
                            </strong>${descrip.geteNombreUs()}<br>
                        </p>
                        
                        
                         <!--Formulario de opciones para volver a realizar un deposito 
                         o regresar al menu principal-->
                        <form method="POST" action="regresarDeposito" onsubmit=""  
                              class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                        <button type="submit" name="regresoOpcion" value="3" 
                                class="botonRegreso">Realizar otro deposito</button>&nbsp;
                    </form>  
                    
                <%
                    // Falso si la busqueda es por cedula y la respuesta del servidor
                    // es un dos indica que el usuario no existe en el sistema y 
                    // que cedula ingresada es incorrecta.
                } else if (msgDeposito!=null && msgDeposito.equals("3")) {
                %>
                <p class="mensajeErrorDep">
                    EL NÚMERO DE CÉDULA <strong> <%= numeroCuenta%> </strong> 
                    DIGITADO NO EXISTE EN EL SISTEMA
                </p>
                <p class="mensajeErrorDep2">
                    POR FAVOR INTENTELO NUEVAMENTE, GRACIAS!
                </p>
                
                
                <form method="POST" action="regresarDeposito" onsubmit=""  
                      class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" name="regresoOpcion" value="2" 
                                class="botonRegreso">Intentar de nuevo</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                    </form>
                
                
                <%} 
                //  Falso si el numero de cuenta no existe
                else if (tipoBusqueda.equals("nCuenta") && msgDeposito.equals("4")){%>
                <p class="mensajeErrorDep">
                    EL NÚMERO DE CUENTA <strong> <%= numeroCuenta%> </strong> 
                    NO ESTA ASOCIADO A NINGUN CLIENTE
                </p>
                <p class="mensajeErrorDep2">
                    POR FAVOR INTENTELO NUEVAMENTE, GRACIAS!
                </p>
                
                
                 <form method="POST" action="regresarDeposito" onsubmit=""  
                       class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" name="regresoOpcion" value="2" 
                                class="botonRegreso">Intentar de nuevo</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                    </form>
                
                <%} // Falso si se presenta un error a la hora de depositar!
                            else if (msgDeposito2!=null && msgDeposito2.equals("10")){%>
                            <p class="mensajeDepositoR">${descrip.geteMensaje()}</p>
                            <form method="POST" action="regresarDeposito" onsubmit="" 
                                  class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" name="regresoOpcion" value="2" 
                                class="botonRegreso">Intentar de nuevo</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                    </form>
                        <%} 
                //  Falso si el numero de cuenta no existe
                else if (msgDeposito.equals("2") && msgDeposito.equals("10")) {%>
                <p class="mensajeErrorDep">
                    ${descrip.geteMensaje()}
                </p>
                <p class="mensajeErrorDep2">
                    POR FAVOR INTENTELO NUEVAMENTE, GRACIAS!
                </p>
                
                 <form method="POST" action="regresarDeposito" onsubmit=""  
                       class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" name="regresoOpcion" value="2" 
                                class="botonRegreso">Intentar de nuevo</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion"
                                value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                    </form>
                      <%}  %>
            </div>
            <footer></footer>
        </div>
    </body>
</html>
