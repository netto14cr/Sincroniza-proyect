<%-- 
    Document   : Retiros
    Created on : 21/03/2020, 03:08:35 PM
    Author     : netto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Se declara la direccion y uso de estilos para dieseño de la pagina--> 
        <link href="css/estiloFormulario.css" rel="stylesheet" type="text/css"/>
        <!--Se declara la direccion y uso de javascript para validacion de la pagina-->
        <script type="text/javascript" src="jsValidaFormularios.js"></script>
        <title>Pagina de Retiros</title>
    </head>
    <body>
        <div id="wrapper">
            <div id="Retiro">
            <header>
                <h1>Retiros</h1>
            </header>
                
                <div id="contents">
                <%
                
                // Se obtienen los mensajes enviados por el Servlet en una variable
                // de tipo string la informacion enviada a la pagina de Deposito
                String msgDeposito ="";
                String msgDeposito2="";
                msgDeposito = (String) session.getAttribute("servletMsjDeposito");
                msgDeposito2 = (String) session.getAttribute("servletMsjDeposito2");
                // Declacion del uso de la clase bean para manejor de datos
                //BeanDeposito bDep = (BeanDeposito) session.getAttribute("descrip");
                //request.getSession().getAttribute("bDep");
                // Se valida que mientras los mensajes del servlet sean nulos o 
                // vacios se muestre el primer formulario de busqueda de cuenta 
                // a depositar
                
                
                
                if(msgDeposito==null || msgDeposito.isEmpty()){ 
                System.out.println("\n\n+++++++++++FORM 1++++++++++++++++++\n\n");
                
                %>
                
                
                <form method="GET" action="busquedaCuenta-cajero" class="e-deposito" 
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
<!--                        <button type="submit" id="button"name="BontonBuscarCuenta" class="boton"
                                onclick="" >Buscar cuentas</button>-->
                    </p>
                </form>
                
                
                <!--Se muestra un formulario para que el cajero pueda volver al menu principal-->
                <form method="GET" action="regresarDeposito" onsubmit=""  class="forBotonRegreso">
                        <input type="hidden" id="regreso" name="regreso" />
                        <button type="submit" id="regresoOpcion" name="regresoOpcion" 
                                value="1" class="botonRegreso4">Volver a menú</button>&nbsp;
                    </form>
                
                
                <p class="mensajeErrorDep">
                    ESTA REQUERIMIENTO NO ESTA DESARROLLADO.... SE RETRASO POR 
                    CAMBIOS DE IMPLEMENTACION DE R3 (REDISEÑADO) Y APARTIR DE ESTE
                    SE DESARROLLARA R4 YA QUE ES SIMILAR EN UN 85%!
                </p>
                
                
                
                           <% }%>
                
                
                
                
                
                
                
                
                
                
            </div>    
            </div>
        </div>
    </body>
</html>
