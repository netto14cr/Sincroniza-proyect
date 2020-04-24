<%-- 
    Document   : Deposito
    Created on : 03/03/2020, 03:07:01 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>


        <%-- <link href="css/estiloFormulario.css" rel="stylesheet" type="text/css"/> --%>
        <link href="css/estiloCliente/estiloPaginaCliente.css" rel="stylesheet" type="text/css"/> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Se declara la direccion y uso de javascript para validacion de la pagina-->
        <script type="text/javascript" src="jsValidaFormularios.js"></script>
        <!--Se define el uso de la etiqueta bean con datos de apertura nueva cuenta-->
        <jsp:useBean class="beans.BeanLogin" id="eLogin" scope="session">
        </jsp:useBean>
        <%
            String msgLogin = "";
            msgLogin = (String) session.getAttribute("servletLogin");
            beans.BeanLogin bl = (beans.BeanLogin) session.getAttribute("eLogin");
            session.setAttribute("beanLogin", bl);
            session.setAttribute("id", bl.geteIdentificacion());
            DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");
            Date date = new Date();

            if (msgLogin != null && msgLogin.equals("1")) {
        %>
        <title>Pagina Cliente</title>
    </head>
    <body style="background-image: url('css/estiloCliente/cliente/fondo-cliente_2.jpg') " 
         
          >
        <div id="clienteFondo">
            <div id="wrapper">
                <div id="encabezado" style=" background-image: url('css/estiloCliente/cliente/encabezado_1.jpg')">


                    <span id="fecha" style="font-size: 25px"><%= dateFormat.format(date)%></span>
                    <div id="InfoCliente">
                        <h2 style ="text-decoration: underline; ">Sección Cliente</h2>
                        <h2>Id Usuario: <jsp:getProperty name="eLogin" property="eIdentificacion"/></h2>
                    </div>
                </div>

                <div id="titulo">
                    <h1 style="font-family: sans-serif"> Bienvenido !</h1>
                    <h2 style="font-family: monospace"> Selecione la opcion que desea realizar </h2>
                </div>
                <div id="contents">
                    <%--
                     <p>Bean : id = <%= bl.geteIdentificacion()%></p>
                     
                     <p>Contraseña: <jsp:getProperty name="eLogin" property="ePasword"/></p>
                    --%>
                    <div id="menu">

                        <form method="POST" action="menu-Navegacion" onsubmit=""  class="formMenu" >
                            <button type="submit" id="opcionMenu" name="opcionMenu" value="6" class="botonMenuOpciones"
                                    >Mis Cuentas</button>&nbsp;
                            <%-- 
                                     <button type="submit" id="opcionMenu" name="opcionMenu" value="7" class="botonMenuOpciones"
                                     >Consultar saldo</button>&nbsp;
                                    
                                     <button type="submit" id="opcionMenu" name="opcionMenu" value="9" class="botonMenuOpciones"
                                     >Movimientos</button>&nbsp;
                            --%>
                            <button type="submit" id="opcionMenu" name="opcionMenu" value="8" class="botonMenuOpciones"
                                    >Transferencias</button>&nbsp;
                            <button type="submit" id="opcionMenu" name="opcionMenu" value="10" class="botonMenuOpciones"
                                    >Afiliar un cuenta</button>&nbsp;

                            <button type="submit" id="opcionMenu" name="opcionMenu" value="5" class="botonMenuOpciones"
                                    >Cerrar Sesión</button>&nbsp;
                        </form>

                    </div>
                    <%} else if (msgLogin != null && msgLogin.equals("2")) {
                    %>

                    <!--                Si el mensaje enviado por el servidor es numero 2 indica que se
                                    Se le mostrara al usuario una pantalla con un pequeño formulario
                                    para que cambie de sesion-->


                    <p class="mensajeErrorDep">
                        ${eLogin.geteMensaje()}
                    </p>
                    <form method="POST" action="cambio-Contra" onsubmit="return validarCambioPassw()"  class="e-deposito">
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

                    <form method="POST" action="menu-Navegacion" onsubmit=""  class="formRegresoMenu">
                        <button type="submit" id="opcionMenu" name="opcionMenu" value="5" class="botonCuenta">cancelar</button>&nbsp;
                    </form>
                    <%} else if (msgLogin != null && msgLogin.equals("5")) {
                    %>
                    <p class="mensajeErrorDep2">
                        ${eLogin.geteMensaje()}
                    </p>

                    <%}
                    %>

                    <!---------    FIN PAGINA CAJERO USUARIO POR PRIMERA VEZ------->

                </div>
            </div>
        </div>
    </body>
</html>
