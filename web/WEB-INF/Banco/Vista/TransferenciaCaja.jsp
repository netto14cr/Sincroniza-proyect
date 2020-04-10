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
                String msgCajas ="";
                String msgCajas2="";
                msgCajas = (String) session.getAttribute("servletMsjTCaja");
                msgCajas2 = (String) session.getAttribute("servletMsjTCaja2");
                
                %>
                        <p class="mensajeDepositoR">
                           
                            <strong>N° Céd. cuenta dep:&nbsp;&nbsp;&nbsp;</strong>
                            ${descTCaja.geteCedula()}<br>
                            <strong>N° Cuenta deposito:&nbsp;&nbsp;&nbsp;</strong>
                            ${descTCaja.geteNumCuenta()}<br>
                        </p>
            </div>
            <footer></footer>
        </div>
    </body>
</html>
