<%-- 
    Document   : CuentasCliente
    Created on : 04/04/2020, 12:48:07 AM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="model.cuenta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="css/estiloCliente/estiloCuentaCliente.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <%
        beans.BeanLogin bl = (beans.BeanLogin) session.getAttribute("beanLogin");
        List<cuenta> lis = (List<cuenta>) request.getSession().getAttribute("listaCuentas");
        System.out.println(lis.size());
        for (cuenta elem : lis) {
            System.out.println(elem.toString());
        }
         DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");
            Date date = new Date();
    %>

    <body style="background-image: url('css/estiloCliente/cliente/fondo-cliente_2.jpg') ">
        <div id="wrapper">
            
<div id="encabezado" style=" background-image: url('css/estiloCliente/cliente/encabezado_1.jpg')">


                <span id="fecha" style="font-size: 25px"><%= dateFormat.format(date)%></span>
                <div id="InfoCliente">
                    <h2 style ="text-decoration: underline; ">Sección Cliente</h2>
                    <h2>Id Usuario:><%= bl.geteIdentificacion()%> </h2>
                </div>
</div>
            <div id="titulo">
                <h1 style="font-family: sans-serif"> Bienvenido !</h1>
                <h2 style="font-family: monospace"> Selecione la cuenta para ver sus detalles</h2>
            </div>
            
            
            <%-- <p>Contraseña :<%= bl.getePasword()%> </p>
            <p>Mostramos las cuentasa del cliente con la cedula</p>
            --%>

            <%
                String numCu = request.getParameter("numeroCuenta");
            %>
            <div id="opcionMenu" style="background-image: url('css/estiloCliente/cuentas-clientes/menu-cuentas_1.jpg')" >
        <p><strong>Seleccione una cuenta para ver sus detalles</strong></p>
        <form name="myForm"  action="datosMovimientoCuenta"  <%--onsubmit="return validateFormRANGO() "--%> method="get">
            <select name="numeroCuenta">
                <% for (cuenta elem : lis) {%>
                <option  value="<%= elem.getNum_cuenta()%>"><%=elem.getNum_cuenta()%></option>
                <%}%>        
            </select>
            <input type="hidden" id="Maximo" name="Maximo" value="<%= lis.size()%>"/>
            <button type="submit">Enviar</button>
        </form>
        <form  action="ServletMovimientosCuentas"> 
            <input type="hidden" name="volverOpciones" value="1"/>
            <button type="submit">Volver Menu</button>
        </form>
        <h2>Datos para los requerimientos</h2>
        <% if (numCu==null)
        {
         numCu="No ha Seleccionado";   
        }
        %>
        <p>Numero de cuenta : <%= numCu%></p>
        </div>    
    </div>
</body>
</html>
