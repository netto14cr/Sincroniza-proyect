<%-- 
    Document   : Retiros
    Created on : 28/03/2020, 03:08:35 PM
    Author     : netto
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
        <jsp:useBean class="beans.BeanRetiro" id="descripRetiro" scope="session">
        </jsp:useBean>
        
        <title>Pagina de Retiros</title>
    </head>
    <body>
        <div id="wrapper">
            <div id="Retiro">
            <header>
                <h1>Retiros</h1>
            </header>
                
            </div>

            <div id="contents">
                <%
                System.out.println("°°°°°°°°°°°°°°°°°°CARGA DEPOSITO PAGINA°°°°°°°°°°°°°°");
                // Se obtienen los mensajes enviados por el Servlet en una variable
                // de tipo string la informacion enviada a la pagina de Deposito
                String msgRetiro ="";
                String msgRetiro2="";
                msgRetiro = (String) session.getAttribute("servletMsjRetiro");
                msgRetiro2 = (String) session.getAttribute("servletMsjRetiro2");
                
                if (msgRetiro!=null){
                    System.out.println("----D1--"+msgRetiro);
                }
                if (msgRetiro2!=null){
                    System.out.println("----D2--"+msgRetiro2);
                }
                
                
                // Declacion del uso de la clase bean para manejor de datos
                BeanRetiro bRet = (BeanRetiro) session.getAttribute("descripRetiro");
                request.getSession().getAttribute("bRet");
                // Se valida que mientras los mensajes del servlet sean nulos o 
                // vacios se muestre el primer formulario de busqueda de cuenta 
                // a depositar
                
                
                
                cuenta c = (cuenta) request.getAttribute("busquedaCuenta-cajero");
                String tipoBusqueda = request.getParameter("tipoBusquedaCuenta");
                String numeroCuenta = request.getParameter("nCuentaDeposito");
                String montoAux = request.getParameter("montoDeposito");
                String montoAux2 = request.getParameter("detalleMontoDep");
                
                
                if(msgRetiro==null || msgRetiro.isEmpty()){ 
                System.out.println("\n\n::::::FORMULARIO DE BUSQUEDA # CUENTA :::::\n\n");
                
                %>
                
                <form method="GET" action="busquedaCuentaRetiro-cajero" class="e-deposito" 
                      onsubmit="return validarForumlarioDeposito1()">

                    <!--Campo para ingresar la identificación-->
                    <p>
                        <label class="texto">Buscar cuenta por:  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <select id="tipoBusquedaCuenta" name="tipoBusquedaCuenta" class="select">
                            <option value="nCedula" >Número de cédula</option>
                            <option value="nCuenta">Número de cuenta</option>
                        </select>

                    </p>
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero o cedula de la cuenta-->
                    <p>
                        <label class="texto">Cuenta a depositar:&nbsp;</label>
                        <input type="text" name="nCuentaDeposito" id="nCuentaDeposito" autofocus="autofocus"
                               placeholder="digite la cuenta " class="campo" autocomplete="off" />
                    </p>
                    <p style="text-align: right;">
                        <button type="submit" id="button"name="BontonBuscarCuenta" class="boton"
                                onclick="" >Buscar cuentas</button>
                    </p>
                </form>
                    
                
                <!--Se muestra un formulario para que el cajero pueda volver al menu principal-->
                <form method="GET" action="regresarRetiro" onsubmit=""  class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4">Volver a menú</button>&nbsp;
                    </form>
                <!--TABLA DOS PARA DEPOSITO-->

                <%
//                    Se recibe la respuesta si se ejecuta alguna accion con el servlet 
//                    de buscar cuenta en el sistema
                    //String msgBusqueda = (String) session.getAttribute("servletMsjBusquedaCuenta");
                    
                    // Condision si el usuario ha ingresado un valor cuando le 
                    // dio buscar cuentas
                  
                    }else if (msgRetiro!=null && msgRetiro.equals("1") 
                        && msgRetiro2==null) {
                       
                        System.out.println("\n\n::::::::::  FORM 2 VERIFICA DUEÑO CUENTA  ::::::::::\n\n");
                        %>

                        <form method="GET" action="retiro-cajero" class="e-deposito2" 
                              onsubmit="return validarForumlarioRetiro2()">

                    <!--Campo para ingresar la identificación-->
                        <p>
                            <label class="texto">Seleccion cuenta:</label>
                       
                        <select id="seletCuenta" name="seletCuenta" class="select">
                            <%for (cuenta lis : bRet.getLista()) {%>
                            <option value="<%=lis.getNum_cuenta()%>"><%=lis.getNum_cuenta()%></option>
                            <% } %>
                        </select>
                    </p>
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero o cedula de la cuenta-->
                    <p>
                        <label class="texto">Número identificación:</label>
                        <input type="text" name="detalleNumId" id="detalleNumId" autofocus="autofocus"
                               placeholder="Identificación depositante " class="campo2" autocomplete="off" />
                    </p>

                    <p style="text-align: right;">
                        <button type="submit" class="boton">Verificar cuenta</button>
                    </p>
                </form> 
                        
                        <!--Se muestra un formulario para que el cajero pueda cancelar la accion y volver a la 
                        pantalla anterior-->
                <form method="GET" action="regresarRetiro" onsubmit=""  class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="2" class="botonCuenta">Cancelar acción</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4">Volver a menú</button>&nbsp;
                    </form>
                        
<!--                    Se muestra el 3 formulario con una pequeña variante en 
                        campo de detalle de nombre de depositante si no es el dueño de la cuenta-->
                        <%}else if (msgRetiro2!=null && msgRetiro2.equals("INGRESA_MONTO_RETIRO")){
                        System.out.println("::::::FORMULARIO 3 - RETIRO - ELEGIE MONTO::");
                        %>   
                        
                    <form method="GET" action="retiro-cajero2" class="e-deposito2" 
                          onsubmit="return validarForumlarioRetiro3()">

                    <!--Campo para ingresar la identificación-->
                    <p>
                        <label class="texto">Cuenta seleccionada:&nbsp;</label>
                        <input type="text" name="seletCuenta" id="seletCuenta" autofocus="autofocus"
                               class="campo2" readonly="true" value="${descripRetiro.geteNumCuenta()}"/>
                    </p>
                    
                    
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero o cedula de la cuenta-->
                    <p>
                        <label class="texto">Número identificación:</label>
                        <input type="text" name="detalleNumId" id="detalleNumId" 
                               autofocus="autofocus" class="campo2" 
                               value="${descripRetiro.geteCedula()}"  readonly="true"/>
                    </p>
                    

                    <!--Se define la etiqueta y el campo para que el cajero ingrese el monto a depositar-->
                    <p>
                        <label class="texto">Monto a deposito:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text" id="montoDeposito" name="montoDeposito" 
                               autofocus="autofocus"placeholder=" Monto deposito " class="campo2" 
                               value="${descripRetiro.geteMontoDeposito()}"/>
                    </p>
                    
                    

                    <!--Se define la etiqueta y el campo para que el cajero ingrese el detalle del deposito-->
                    <p>
                        <label class="texto">Detalle de deposito:&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text" id="detalleDep" name="detalleDep" autofocus="autofocus"
                               placeholder="Detalle de deposito" class="campo2" 
                               value="${descripRetiro.geteDetalleDeposito()}"/>
                    </p>
                    

                    <p style="text-align: right;">
                        <button type="submit" class="boton">Autorizar retiro</button>
                    </p>
                </form> 
                        
                   <!--Se muestra un formulario para que el cajero pueda cancelar la accion y volver a la 
                    pantalla anterior-->
                <form method="GET" action="regresarRetiro" onsubmit=""  class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="2" class="botonCuenta">Cancelar acción</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4">Volver a menú</button>&nbsp;
                    </form> 
                   
                   
                   <%   }           // Falso si se realizo el deposito correctamente

                        else if (msgRetiro.equals("1") && msgRetiro2.equals("RETIRO")){ 
                            System.out.println("\n\n MUESTRA MENSAJE DATOS RETIRO REALIZADO");
                            %>
                          
                        <p class="mensajeCorrecto"> Se ha completado el deposito correctamente
                        </p>
                        <p class="mensajeDepositoR">
                           
                            <strong>N° Céd. cuenta dep   :&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteCedula()}<br>
                            <strong>N° Cuenta deposito   :&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteNumCuenta()}<br>
                            <strong>Monto transferencia  :&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteMontoDeposito()}<br>
                            <strong>Detalle Deposito     :&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteDetalleDeposito()}<br>
                        </p>
                        
                        <!--Formulario de opciones para volver a realizar un deposito o regresar al menu principal-->
                        <form method="GET" action="regresarRetiro" onsubmit=""  class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                        <button type="submit" name="regresoOpcion" value="3" 
                                class="botonRegreso">Realizar otro deposito</button>&nbsp;
                    </form>
                
                 <%
                // Falso si la busqueda seleccionada es por número de cuenta
                }else if (msgRetiro.equals("2") && msgRetiro2==null) {
                    System.out.println("\n\n++++FORM 3+++++CUENTA++++ \n\n");
                %>
                <form method="GET" action="retiro-cajero" class="e-deposito2" onsubmit="return validarDepNumCuenta()">

                    <!--Campo para mostrar el numero de cuenta ingresado previamente
                    a la cual se realizara el deposito-->
                    <p>
                        <label class="texto">Número de cuenta :&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text"  class="campo2" readonly="true" value="${descripRetiro.geteNumCuenta()}"/>
                    </p>

                    
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero o cedula de la cuenta-->
                    <p>
                        <label class="texto">Número identificación:</label>
                        <input type="text" name="detalleNumId" id="detalleNumId" autofocus="autofocus"
                               placeholder="Identificación depositante " class="campo2" autocomplete="off" />
                    </p>
                    
                    
                    <!--Se define la etiqueta y el campo para que el cajero ingrese el monto a depositar-->
                    <p>
                        <label class="texto">Monto a depositar :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text" id="detalleMontoDep" name="detalleMontoDep" autofocus="autofocus"
                               placeholder="Monto deposito" class="campo2"/>
                    </p>

                    <!--Se define la etiqueta y el campo para que el cajero ingrese el detalle del deposito-->
                    <p>
                        <label class="texto">Detalle de deposito :&nbsp;</label>
                        <textarea type="text" name="detalleDepTxt" id="detalleDepTxt" autofocus="autofocus"
                                  placeholder=" Ingresar aqui el detalle de deposito" class="textarea"></textarea>
                    </p>
                    <br>
                    <p>
                        <button type="submit" class="botonRegreso">Realizar deposito</button>
                    </p>
                </form> 
                    
                    <!--Fromulario para cancelar la operacion de realizar el deposito-->
                    <form method="GET" action="regresarRetiro" onsubmit=""  class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="2" class="botonCuenta">Cancelar acción</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4"  >Volver a menú</button>&nbsp;
                    </form>ERROR_NO_CUENTA
                 
                    
                     <%
                // Falso si la busqueda seleccionada es por número de cuenta
                }else if (msgRetiro.equals("1") && msgRetiro2.equals("ERROR_NO_CUENTA")) {
                    System.out.println("\n\n<<<<<ERROR NO EXISTE CUENTA CEDULA! \n\n");
                %>
                
                <p class="mensajeErrorDep">
                        ${descripRetiro.geteMensaje().toString()} </p> 
                    <!--Fromulario para cancelar la operacion de intenetar o salir de retiros-->
                    <form method="GET" action="regresarRetiro" onsubmit=""  
                          class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" name="regresoOpcion" value="2" 
                                class="botonRegreso">Intentar de nuevo</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso">Volver a menú</button>&nbsp;
                    </form>
                    
                    
                    
                    
                     <%
                // Falso si la busqueda seleccionada es por número de cuenta
                }else if (msgRetiro.equals("2") && msgRetiro2.equals("3")) {
                    System.out.println("\n\n++++FORM 4+++++CUENTA NO DUEÑO++++ \n\n");
                %>
                
                
                <p class="mensajeErrorDep">
                        ${descripRetiro.geteMensaje().toString()} </p> 
                <form method="GET" action="retiro-cajero" class="e-deposito2" onsubmit="return validarDepNumCuenta2()">

                    <!--Campo para mostrar el numero de cuenta ingresado previamente
                    a la cual se realizara el deposito-->
                    <p>
                        <label class="texto">Número de cuenta :&nbsp;&nbsp;&nbsp;</label>
                        <input type="text"  class="campo2" readonly="true" value="${descripRetiro.geteNumCuenta()}"/>
                    </p>

                    <!--Campo para ingresar la identificación-->
                    <p>
                        <label class="texto">Nombre depositante:&nbsp;</label>
                        <input type="text" name="detalleNombreDep" autofocus="autofocus"
                               placeholder="Nombre depositante" class="campo2"
                               id="detalleNombreDep"/>
                    </p>
                    
                    <!--Campo para que el usuario pueda ingresar la cuenta el numero o cedula de la cuenta-->
                    <p>
                        <label class="texto">Número identificación:</label>
                        <input type="text" name="detalleNumId" id="detalleNumId" autofocus="autofocus"
                               placeholder="Identificación depositante " class="campo2" value="${descripRetiro.geteCedulaDet()}" />
                    </p>
                    
                    
                    <!--Se define la etiqueta y el campo para que el cajero ingrese el monto a depositar-->
                    <p>
                        <label class="texto">Monto a depositar:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="text" id="detalleMontoDep" name="detalleMontoDep" autofocus="autofocus"
                               placeholder="Monto deposito" class="campo2" value="${descripRetiro.geteMontoDeposito()}"/>
                    </p>

                    <!--Se define la etiqueta y el campo para que el cajero ingrese el detalle del deposito-->
                    <p>
                        <label class="texto">Detalle de deposito:&nbsp;</label>
                        <textarea type="text" name="detalleDepTxt" id="detalleDepTxt" autofocus="autofocus"
                                  class="textarea">${descripRetiro.geteDetalleDeposito()}</textarea>
                    </p>
                    <br>
                    <p>
                        <button type="submit" class="botonRegreso">Realizar deposito</button>
                    </p>
                </form> 
                    
                    <!--Fromulario para cancelar la operacion de realizar el deposito-->
                    <form method="GET" action="regresarRetiro" onsubmit=""  class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="2" class="botonCuenta">Cancelar acción</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4"  >Volver a menú</button>&nbsp;
                    </form>
                    
                <%   }           // Falso si se realizo el deposito correctamente

                        else if (msgRetiro.equals("2") && msgRetiro2.equals("DEPREADY3")){ 
                            System.out.println("\n\n MUESTRA MENSAJE DEPOSITO CEDULA");
                            %>
                          
                        <p class="mensajeCorrecto"> Se ha completado el deposito correctamente
                        </p>
                        <p class="mensajeDepositoR">
                           <strong>N° Cédula dueño cuenta:&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteCedula()}<br>
                            <strong>N° Cuenta deposito   :&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteNumCuenta()}<br>
                            <strong>Monto transferencia  :&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteMontoDeposito()}<br>
                            <strong>Detalle Deposito     :&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteDetalleDeposito()}<br>
                        </p>
                        
                        <!--Formulario de opciones para volver a realizar un deposito o regresar al menu principal-->
                        <form method="GET" action="regresarRetiro" onsubmit=""  class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                        <button type="submit" name="regresoOpcion" value="3" 
                                class="botonRegreso">Realizar otro deposito</button>&nbsp;
                    </form>    
                    
                    
                    
                    
                <%}else if (msgRetiro.equals("2") && msgRetiro2.equals("DEPREADY4")){
                        System.out.println("DEEEEEP2");%>
                        <p class="mensajeCorrecto"> ${descripRetiro.geteMensaje()}</p>
                        
                             <p class="mensajeDepositoR">
                            <strong>N° Identificacion depositante   :&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteCedulaDet()}<br>
                            <strong>N° Cuenta deposito   :&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteNumCuenta()}<br>
                            <strong>Monto transferencia  :&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteMontoDeposito()}<br>
                            <strong>Detalle Deposito     :&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteDetalleDeposito()}<br>
                            <strong>Nombre detalle dep.  :&nbsp;&nbsp;&nbsp;</strong>${descripRetiro.geteNombreUs()}<br>
                        </p>
                        
                        
                         <!--Formulario de opciones para volver a realizar un deposito o regresar al menu principal-->
                        <form method="GET" action="regresarRetiro" onsubmit=""  class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                        <button type="submit" name="regresoOpcion" value="3" 
                                class="botonRegreso">Realizar otro deposito</button>&nbsp;
                    </form>  
                    
                <%
                    // Falso si la busqueda es por cedula y la respuesta del servidor
                    // es un dos indica que el usuario no existe en el sistema y 
                    // que cedula ingresada es incorrecta.
                } else if (msgRetiro!=null && msgRetiro.equals("3")) {
                %>
                <p class="mensajeErrorDep">
                    EL NÚMERO DE CÉDULA <strong> <%= numeroCuenta%> </strong> DIGITADO NO EXISTE EN EL SISTEMA
                </p>
                <p class="mensajeErrorDep2">
                    POR FAVOR INTENTELO NUEVAMENTE, GRACIAS!
                </p>
                
                
                <form method="GET" action="regresarRetiro" onsubmit=""  class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" name="regresoOpcion" value="2" class="botonRegreso">Intentar de nuevo</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                    </form>
                
                
                <%} 
                //  Falso si el numero de cuenta no existe
                else if (tipoBusqueda.equals("nCuenta") && msgRetiro.equals("4")) {%>
                <p class="mensajeErrorDep">
                    EL NÚMERO DE CUENTA <strong> <%= numeroCuenta%> </strong> NO ESTA ASOCIADO A NINGUN CLIENTE
                </p>

                <p class="mensajeErrorDep2">
                    POR FAVOR INTENTELO NUEVAMENTE, GRACIAS!
                </p>
                
                
                 <form method="GET" action="regresarRetiro" onsubmit=""  class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" name="regresoOpcion" value="2" class="botonRegreso">Intentar de nuevo</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                    </form>
                

                <%} // Falso si se presenta un error a la hora de depositar!
                            else if (msgRetiro2!=null && msgRetiro2.equals("10")){%>
                            <p class="mensajeDepositoR">${descripRetiro.geteMensaje()}</p>
                            <form method="GET" action="regresarRetiro" onsubmit=""  class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" name="regresoOpcion" value="2" class="botonRegreso">Intentar de nuevo</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                    </form>
                            

                        <%} 
                //  Falso si el numero de cuenta no existe
                else if (msgRetiro.equals("2") && msgRetiro.equals("10")) {%>
                <p class="mensajeErrorDep">
                    ${descripRetiro.geteMensaje()}
                </p>

                <p class="mensajeErrorDep2">
                    POR FAVOR INTENTELO NUEVAMENTE, GRACIAS!
                </p>
                
                
                 <form method="GET" action="regresarRetiro" onsubmit=""  class="formRegresoMenu">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" name="regresoOpcion" value="2" class="botonRegreso">Intentar de nuevo</button>&nbsp;
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" value="1" class="botonRegreso"
                                     >Volver a menú</button>&nbsp;
                    </form>
                


                      <%}  %>
                
                
                
            </div>
                
                
            <footer></footer>
        </div>

    </body>
</html>
