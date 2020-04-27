<%-- 
    Document   : Retiros
    Created on : 28/03/2020, 03:08:35 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

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
            <div id="Retiro">
            <header>
                <h1>Transferencia en cajas</h1>
            </header>
                
            </div>

            <div id="contents">
                <%
                System.out.println("::::::::::   VISTA TRASFERENCIAS BANACARIAS    :::::::::");
                // Se obtienen los mensajes enviados por el Servlet en una variable
                // de tipo string la informacion enviada a la pagina de Deposito
                String msgTransaccion ="";
                msgTransaccion = (String) session.getAttribute("servletMsjTCaja3");
                System.out.println("---"+msgTransaccion);
                
                
                // Si el mensaje de 
                 if (msgTransaccion!=null && msgTransaccion.equals("FORM1") || 
                         msgTransaccion!=null && msgTransaccion.equals("ERROR")){

                    if (msgTransaccion!=null && msgTransaccion.equals("ERROR")){%>
                        <p class="mensajeErrorDep">${descTCaja.geteMensaje()}</p><%}%>
                        
<!--                        Se muestra el formulario para que el usuario indique la
                        cantidad de dinero e ingrese la descripcion para realizar
                        la transaccion-->
                    <form method="POST" action="transferencia-Caja" class="e-deposito2" 
                          onsubmit="return validarFormTransferencia()">

                        <!--Campo donde se muestra la identificacion del depositante-->
                    <p>
                        <label class="texto">Identificación Dep:&nbsp;&nbsp;&nbsp;</label>
                        <input type="text" name="idOrigen" id="idOrigen" 
                               autofocus="autofocus" class="campo2" 
                               value="${descTCaja.geteCedula()}"  readonly="true"/>
                    </p>
                        
                    <!--Campo donde se muestra el numero de cuenta origen -->
                    <p>
                        <label class="texto">N° Cuenta Origen:&nbsp;&nbsp;&nbsp;</label>
                        <input type="text" name="nCuentaOrigen" id="nCuentaOrigen" autofocus="autofocus"
                               class="campo2" readonly="true" value="${descTCaja.geteNumCuenta()}"/>
                    </p>
                    <!--Campo donde se muestra el numero de cuenta destino -->
                    <p>
                        <label class="texto">N° Cuenta Destino:&nbsp;&nbsp;</label>
                        <input type="text" name="nCuentaDestino" id="nCuentaDestino" autofocus="autofocus"
                               class="campo2" readonly="true" value="${descTCaja2.geteNumCuenta()}"/>
                    </p>
                    
                    <!--Se define la etique y el campo para que se ingrese el monto a transpasar-->
                    <p>
                        <label class="texto">Monto transferir:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text" id="montoDeposito" name="montoDeposito" 
                               autofocus="autofocus" placeholder=" Monto deposito " class="campo2" 
                               />
                    </p>
                    
                    <!--Se define la etiqueta y el campo para que el cajero ingrese el detalle del deposito-->
                    <p>
                        <label class="texto">Detalle de transf:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text" id="detalleDep" name="detalleDep" autofocus="autofocus"
                               placeholder="Detalle de deposito" class="campo2"/>
                    </p>
                    <p style="text-align: right;">
                        <button type="submit" class="boton">Realizar transferencia</button>
                    </p>
                </form> 
                        
                   <!--Se muestra un formulario para que el cajero pueda cancelar la accion y volver a la 
                    pantalla anterior-->
                <form method="POST" action="regresarCaja" onsubmit=""  class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="2" class="botonCuenta">Cancelar acción</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4">Volver a menú</button>&nbsp;
                    </form> 
                   
                   <%   }else if (msgTransaccion!=null && msgTransaccion.equals("READY")){ %>
                
                   <p class="mensajeCorrecto">${descTCaja.geteMensaje()} </p>
                        <p class="mensajeDepositoR">
                           <strong>DATOS DE TRANSFERENCIA:</strong><br>
                            <strong>N° Céd. cuenta dep    :&nbsp;&nbsp;&nbsp;</strong>${descTCaja.geteCedula()}<br>
                            <strong>N° Cuenta deposito    :&nbsp;&nbsp;&nbsp;</strong>${descTCaja.geteNumCuenta2()}<br>
                            <strong>N° Cuenta a debitar   :&nbsp;&nbsp;&nbsp;</strong>${descTCaja.geteNumCuenta()}<br>
                            <strong>Monto transferencia   :&nbsp;&nbsp;&nbsp;</strong>${descTCaja.geteMontoDeposito()}<br>
                            <strong>Detalle transferencia :&nbsp;&nbsp;&nbsp;</strong>${descTCaja.geteDetalleDeposito()}<br>
                        </p>
                        
                        
                           <!--Se muestra un formulario para que el cajero pueda cancelar la accion y volver a la 
                    pantalla anterior-->
                <form method="POST" action="regresarCaja" onsubmit=""  class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="2" class="botonCuenta">Realizar otra trnasferencia</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4">Volver a menú</button>&nbsp;
                    </form> 
                   
                   <%   } %>
            </div>
            <footer></footer>
        </div>
    </body>
</html>
