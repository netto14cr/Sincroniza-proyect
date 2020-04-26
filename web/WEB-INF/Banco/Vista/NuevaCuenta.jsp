<%-- 
    Document   : NuevaCuenta
    Created on : 21/03/2020, 03:06:38 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="model.usuario"%>
<%@page import="modelo.dao.funcionesFrontEnd.funcionesAperturaCuenta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Se declara la direccion y uso de estilos para dieseño de la pagina--> 
        <link href="css/estiloFormulario.css" rel="stylesheet" type="text/css"/>
        <!--Se declara la direccion y uso de javascript para validacion de la pagina-->
        <script type="text/javascript" src="jsValidaFormularios.js"></script>
        <!--Se define el uso de la etiqueta bean con datos de apertura nueva cuenta-->
        <jsp:useBean class="beans.BeanNuevaCuenta" id="descripción" scope="session">
        </jsp:useBean>
      
        <title>Apertura de cuenta</title>
    </head>
    <body>
        <div id="wrapper">
            <div id="AperturaCuenta">
            <header>
                <h1>Apertura de nueva cuenta</h1>
            </header>
            </div>
            
            <div id="contents">
                <%
                    System.out.println("::::::::::   VISTA APERTURA DE CUENTA    :::::::::");
                    
                funcionesAperturaCuenta fAP=new funcionesAperturaCuenta();
                String msgNuevaCuenta =""; 
                msgNuevaCuenta= (String) session.getAttribute("servletMsjNuevaCuenta");
                if(msgNuevaCuenta==null || msgNuevaCuenta.isEmpty() && msgNuevaCuenta!="2"){%>
                
<!--                Se muestra el formulario numero 1 - para que el usuario cajaero pueda
                ingresar los datos de la cuenta que desea crear nueva-->

                <form method="POST" action="crearCuentaPaso1" class="e-deposito" onsubmit="return validarForumlario1()">

                    <!--Campo donde se muestra el numero de cuenta generado por el sistema-->
                    <p>
                        <label class="texto">Numero de cuenta:&nbsp;</label>
                        <input type="text" class="campo" id="numeroCuenta" readonly="true" 
                        name="numeroCuenta" value="<%=fAP.generarNumeroCuentaNuevo()%>" 
                        autofocus="autofocus"/>
                    </p>
                    
                    <!--Campo para ingresar la identificación-->
                    <p>
                        <label class="texto">Número de cédula:&nbsp;</label>
                        <input class="campo" id="numeroCedula" name="numeroCedula" 
                               autofocus="autofocus" placeholder="Ejemplo: 1-0000-0000"
                               onblur="verificarCuenta(this)" autocomplete="off" />
                    </p>
                    
                    
                      <!--Campo para seleccionar el tipo de moneda paral cuenta-->
                    <p>
                        <label class="texto">Seleccionar moneda:  &nbsp;</label>
                        <select id="tipoMoneda" name="tipoMoneda" class="select">
                            <option value="CRC">₡ -- colones </option>
                            <option value="USD">$ -- Dolares </option>
                            <option value="EUR">€ -- Euros </option>
                        </select>
                    </p>
                    
                    <!--Campo donde se muestra el saldo inicial de la nueva cuenta-->
                     <p>
                        <label class="texto">Saldo inicial:&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input  type="text" class="campo" id="saldoInit" readonly="true" 
                                name="saldoInit" autofocus="autofocus" value="0"/>
                     </p>
                    
                      <!--Campo para seleccionar el maximo de transferencias diarias de la cuenta-->
                    <p>
                        <label class="texto">Seleccionar moneda:  &nbsp;</label>
                        <select  id="maximoTransferencia" name="maximoTransferencia" class="select">
                            
                            <option value="1">1 &nbsp;a &nbsp;   100.000 </option>
                            <option value="2">1 &nbsp;a &nbsp;   200.000 </option>
                            <option value="3">1 &nbsp;a &nbsp;   300.000 </option>
                            <option value="4">1 &nbsp;a &nbsp;   400.000 </option>
                            <option value="5">1 &nbsp;a &nbsp;   500.000 </option>
                            <option value="6">1 &nbsp;a &nbsp;  600.000 </option>
                            <option value="7">1 &nbsp;a &nbsp;  700.000 </option>
                            <option value="8">1 &nbsp;a &nbsp;  800.000 </option>
                            <option value="9">1 &nbsp;a &nbsp;  900.000 </option>
                            <option value="10">1 &nbsp;a &nbsp;1.000.000 </option>
                        </select>
                    </p>
                    
                    <!--Campo para seleccionar el tipo de cuenta 0-> Ahorros / 1->corriente --> 
                    <p>
                        <label class="texto">Seleccion tipo cuenta:</label>
                        <select id="tipoMoneda" name="tipoCuenta" class="select">
                            <option value="0">Cuenta de ahorrros </option>
                            <option value="1">Cuenta corriente </option>
                        </select>
                    </p>
                    
                   <!--Campo del formulario donde se define el boton del formulario-->
                   <p style="text-align: right;">
                        <button type="submit" id="BotonComprobarCuenta"name="BotonComprobarCuenta" class="boton"
                                onclick="" >Comprobar nueva cuenta</button>
                   </p>
                </form>
                    
<!--                    Se muestra un segundo formulario al mismo tiempo en pantalla
                    para que el usuario pueda elegir la opcion de cancelar la 
                    apertura de la nueva cuenta, este lo dirigira al menu principal-->
                    <form method="POST" action="regresarCuentaFin-Cajero" onsubmit=""  class="forBotonRegreso">
                             <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                     value="1" class="botonRegreso4">Volver a menú</button>&nbsp;
                         </form>
                  <%  } %>
                  
                  
<!--                    Aqui va el formulario si el usuario a quien crea la cuenta no
                    se encuntra registrado en el sistema-->
                    <% 
                        
                        String nCedula=""; String nCuenta="";
                        nCedula = request.getParameter("numeroCedula");
                        nCuenta= request.getParameter("numeroCuenta");
                        
                        
                        // Si el servidor enviar respuesta ==1 siginifica que el 
                        // numero de cedula indica por el usuario en el sistema
                        // ya contaba con cuenta y se le procede a asignar una 
                        // nueva cuenta con esto se actualiza la pantalla con la
                        // informacion y mensaje de la nueva cuenta
                      if (msgNuevaCuenta!=null && msgNuevaCuenta.equals("1")){%>
                          <p class="mensajeCorrecto">
                            
                              ${descripción.geteMensaje()}</p><br>
                          <p class="mensajeCorrecto">
                         <strong>Identificación   : &nbsp;&nbsp;&nbsp;</strong> ${descripción.geteCedula()}<br>
                         <strong>Número de cuenta:</strong> ${descripción.geteNumeroCuenta()}<br>
                         <strong>Max. transferencia: </strong> ${descripción.geteMaxTransferencia()}<br>
                         <strong>Tipo de Moneda    : </strong> ${descripción.geteTipoMoneda()}<br>
                         <form method="POST" action="regresarCuentaFin-Cajero" onsubmit=""  class="forBotonRegreso">
                             <button type="submit" id="regresoOpcion" name="regresoOpcion" value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                                     <button type="submit" id="regresoOpcion" name="regresoOpcion" value="3" class="botonRegreso"
                                     >Registrar otra cuenta</button>&nbsp;
                         </form>
                              
                              
                      <%
                          // Falso si el mensaje del servidor es 2 significa que el numero de 
                          // cedula indicado para la cuenta no existe en el sistema y se procede
                          // a mostrar un segundo formulario para registrar al usuario y agregarlo
                          // seguidamente en el sistema.
                          }else if (msgNuevaCuenta!=null && msgNuevaCuenta.equals("2")){%>
                          <form method="POST" action="crearCuentaPaso2" class="e-deposito2" 
                                onsubmit="return validarForumlario2()">

                        <!--Campo donde se muestra el numero de cuenta generado por el sistema-->
                    <p>
                        <label class="texto">Número de cuenta:&nbsp;&nbsp;&nbsp;</label>
                        <input type="text" class="campo2" id="numeroCuenta2" readonly="true"
                               name="numeroCuenta2" value="${descripción.geteNumeroCuenta()}" 
                               autofocus="autofocus"/>
                    </p>
                    <!--Campo para mostar la identificacion de la nueva cuenta-->
                    <p>
                        <label class="texto">Número de cédula:&nbsp;&nbsp;&nbsp;</label>
                        <input class="campo2" id="nCed2" name="nCed2" 
                               autofocus="autofocus" readonly="true" 
                               value="${descripción.geteCedula()}"  />
                    </p>      
                    
                     <!--Campo para mostrar el tipo de moneda-->
                    <p>
                        <label class="texto">Tipo de moneda: &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;</label>
                        <input  type="text" class="campo2" id="tipoMoneda2" readonly="true" 
                                name="tipoMoneda2" autofocus="autofocus" 
                                value="${descripción.geteTipoMoneda()}" />
                    </p>
                    <p>
                        <label class="texto">Nombre:   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;</label>
                        <input type="text" id="nombreUser" name="nombreUser" 
                               autofocus="autofocus" placeholder=" Digite el nombre " 
                               class="campo2" 
                               autocomplete="off" onblur="" />
                    </p>
                    
                    <!--Campo para ingresar el apellido 1 del nuevo cliente-->
                    <p>
                        <label class="texto">Apellidos: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            &nbsp;</label>
                        <input type="text" id="apellidosUser" name="apellidosUser" 
                               autofocus="autofocus" id="apellidosUser"
                               placeholder=" Digite sus dos apellidos " class="campo2" 
                               autocomplete="off" onblur=""/>
                    </p>

                    <!--Campo para ingresar el numero de telefono del nuevo cliente-->
                    <p>
                        <label class="texto">Número de telefono:&nbsp;</label>
                        <input type="text" id="nTelefonoUser" name="nTelefonoUser" 
                               autofocus="autofocus"placeholder=" Digite número de telefono " 
                               class="campo2" autocomplete="off" />
                    </p>
                    
                     <!--Campo para seleccionar el tipo de usuario en el sistema 
                     cuenta cajero o cuenta cliente-->
                    <p>
                        <label class="texto">Tipo usuario:&nbsp;&nbsp;&nbsp; </label>
                        <select id="tipoUser" name="tipoUser" class="select2">
                            <option value="0"> Cliente </option>
                            <option value="1"> Cajero </option>
                        </select>
                    </p>

                    <p style="text-align: right;">
                        <button type="submit" id="BotonRegistrar"name="BotonRegistrar"
                                class="boton" onclick="">Registrar nuevo cliente</button>
                    </p>
                </form> 
                    <!--Se muestra el formulario para cancelar las acciones de registro-->
                       <form method="POST" action="regresarCuentaFin-Cajero" onsubmit=""  
                             class="forBotonRegreso">
                             <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                     value="1" class="botonCuenta"
                                     >Cancelar registro</button>&nbsp;
                         </form>
                    
                    
                          <%}else if (msgNuevaCuenta!=null && msgNuevaCuenta.equals("6")){
                                // Se busca la clave generada la nuevo usuario
                                //usuario us= (usuario) session.getAttribute("us");
                                
                                %>
                          
                     <p class="mensajeCorrecto">
                         ${descripción.geteMensaje()}<br><strong>Nombre:
                         </strong> ${descripción.geteNombreUs()} ${descripción.geteApellidosUs()}<br>
                         <strong>Número de cuenta  : </strong> ${descripción.geteNumeroCuenta()}<br>
                         <strong>Número de Cédula  : </strong> ${descripción.geteCedula()}<br>
                         <strong>Max.transferencia : </strong> ${descripción.geteMaxTransferencia()}<br>
                         <strong>Tipo de Moneda    : </strong> ${descripción.geteTipoMoneda()}<br>
                         <strong>Número telefonico :  </strong> ${descripción.geteTelefono()}<br>
                         <strong>Contraseña        :  </strong> ${descripción.getePassword()}<br>
                         <strong>Tipo Usuario      :  </strong> ${descripción.geteTipoUs()}<br>
                         
                          <form method="POST" action="regresarCuentaFin-Cajero" onsubmit=""  class="forBotonRegreso">
                             <button type="submit" id="regresoOpcion" name="regresoOpcion" value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                                     <button type="submit" id="regresoOpcion" name="regresoOpcion" value="3" class="botonRegreso"
                                     >Registrar otra cuenta</button>&nbsp;
                         </form>
                         
                            
                    <%}else if (msgNuevaCuenta!=null && msgNuevaCuenta.equals("7")){%>
                    <p class="mensajeCorrecto">${descripción.geteMensaje()}</p>
                    
                     <form method="POST" action="regresarCuentaFin-Cajero" onsubmit=""  class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" name="regresoOpcion" value="2" class="botonRegreso">Intentar de nuevo</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                    </form>
                       
                    <%}
                      // Falso si ocurre un error a la hora de crear la nueva cuenta
                     else if (msgNuevaCuenta!=null && msgNuevaCuenta.equals("8")){%>
                    <p class="mensajeCorrecto">${descripción.geteMensaje()}</p>
                    
                     <form method="POST" action="regresarCuentaFin-Cajero" onsubmit=""  class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" name="regresoOpcion" value="2" class="botonRegreso">Intentar de nuevo</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                    </form>
                    
                    
                    <%}
                     // Falso si ocurre un error a la hora de obtener los datos del nuevo cliente
                     else if (msgNuevaCuenta!=null && msgNuevaCuenta.equals("9")){%>
                    <p class="mensajeCorrecto">${descripción.geteMensaje()}</p>
                    
                     <form method="POST" action="regresarCuentaFin-Cajero" onsubmit=""  class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" name="regresoOpcion" value="2" class="botonRegreso">Intentar de nuevo</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                    </form>
                    
                    <%}
                    %>
            </div>
        </div>
    </body>
</html>
