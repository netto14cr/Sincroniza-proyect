<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>


        <link href="css/estiloFormulario.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Se declara la direccion y uso de javascript para validacion de la pagina-->
        <script type="text/javascript" src="jsValidaFormularios.js"></script>
        <!--Se define el uso de la etiqueta bean con datos de apertura nueva cuenta-->
        <jsp:useBean class="beans.BeanLogin" id="eLogin" scope="session">
        </jsp:useBean>

        <title>Pagina Cajero</title>
    </head>
    <body>

        <div id="wrapper">


            <div id="MenuCajero">
                <header>
                    <h1>Pagina Cajero</h1></header>
            </div>


            <div id="contents">
                <%
                    System.out.println("::::::::::   VISTA CAJERO   :::::::::");
                    String msgLogin = "";
                    msgLogin = (String) session.getAttribute("servletLogin");
                    if (msgLogin != null && msgLogin.equals("1")) {
                %>

                <form method="GET" action="menu-Navegacion" onsubmit=""  class="formMenu">
                    <button type="submit" id="opcionMenu" name="opcionMenu" 
                            value="1" class="botonMenuOpciones"
                            >Apertura cuentas</button>&nbsp;
                    <button type="submit" id="opcionMenu" name="opcionMenu" 
                            value="2" class="botonMenuOpciones"
                            >Depositos</button>&nbsp;
                    <button type="submit" id="opcionMenu" name="opcionMenu" 
                            value="3" class="botonMenuOpciones"
                            >Retiros</button>&nbsp;
                    <button type="submit" id="opcionMenu" name="opcionMenu" 
                            value="11" class="botonMenuOpciones"
                            >Transf. en caja </button>&nbsp;
                    <button type="submit" id="opcionMenu" name="opcionMenu" 
                            value="4" class="botonMenuOpciones"
                            >Acreditación interes</button>&nbsp;
                    <button type="submit" id="opcionMenu" name="opcionMenu" 
                            value="5" class="botonMenuOpciones"
                            >Cerrar Sesión</button>&nbsp;
                </form>

                <%} else if (msgLogin != null && msgLogin.equals("2")) {
                %>

<!--                Si el mensaje enviado por el servidor es numero 2 indica que se
                Se le mostrara al usuario una pantalla con un pequeño formulario
                para que cambie de sesion-->

                 
                 <p class="mensajeErrorDep">
                        ${eLogin.geteMensaje()}
                    </p>
                    <form method="GET" action="cambio-Contra" onsubmit="return validarCambioPassw()"  class="e-deposito">
                    <!--Campo para ingresar la nueva contraseña-->
                    
                    
                    <p>
                        <label class="texto">Contraseña nueva:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input class="campo2" id="passwNew" name="passwNew" 
                               autofocus="autofocus" placeholder="Nueva contraseña"
                                autocomplete="off" />
                    </p>

                    <!--Campo para ingresar la nueva contraseña-->
                    <p>
                        <label class="texto">Repita la contraseña :</label>
                        <input class="campo2" id="passwRep" name="passwRep" 
                               autofocus="autofocus" placeholder="Repita su contraseña"
                                autocomplete="off" />
                    </p>
                    
                    <p>
                        <button type="submit" id="cambiaPassw" name="cambiaPassw"
                                value="1" class="botonRegreso" onsubmit=""
                                     >Cambiar</button>&nbsp;
                    </p>
                </form>

                    <form method="GET" action="menu-Navegacion" onsubmit=""  class="formRegresoMenu">
                        <button type="submit" id="opcionMenu" name="opcionMenu" value="5" class="botonCuenta">cancelar</button>&nbsp;
                    </form>
                <%}else if (msgLogin != null && msgLogin.equals("5")) {
                %>
                <p class="mensajeErrorDep2">
                        ${eLogin.geteMensaje()}
                    </p>
                
                <%}
                %>
                
                <!---------    FIN PAGINA CAJERO USUARIO POR PRIMERA VEZ------->
                
            </div>
        </div>

    </body>
</html>
