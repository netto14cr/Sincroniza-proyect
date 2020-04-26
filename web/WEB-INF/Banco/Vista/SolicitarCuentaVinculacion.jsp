<%-- 
    Document   : SolicitarCuentaVinculacion
    Created on : 06/04/2020, 10:41:09 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="css/estiloCliente/estiloAfiliarCuenta.css" rel="stylesheet" type="text/css"/>
        <%--<link href="css/estiloCuentaCliente.css" rel="stylesheet" type="text/css"/>--%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
     DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");
            Date date = new Date();
    %>
         <body style="background-image: url('css/estiloCliente/cliente/fondo-cliente_2.jpg') " 
        <div id="wrapper">
            <jsp:useBean class="beans.BeanLogin" id="eLogin" scope="session">
        </jsp:useBean>
            <div id="encabezado" style=" background-image: url('css/estiloCliente/cliente/encabezado_1.jpg')">


                    <span id="fecha" style="font-size: 25px"><%= dateFormat.format(date)%></span>
                    <div id="InfoCliente">
                        
                        <h2>Id Usuario: <jsp:getProperty name="eLogin" property="eIdentificacion"/></h2>
                    </div>
                </div>
            <div id="solicitud">
        <h1>Digite el numero de cuenta</h1>
        
        <form action="ServletVerificacionCuenta" method="get">
            <input type="text" name="numeroCuenta" placeholder="Digite el numero de cuenta"/>
            <button class="boton1" type="submit" >Vincular</button>
        </form> 
       
        </div>
            <div id="volver">
                 <form action="ServletVerificacionCuenta" method="get">
            <input type="hidden" name="volverMenu" value="1"/>
            <button class="boton" type="submit">Volver Menu</button>
        </form>
            </div>
        </div>
    </body>
</html>
