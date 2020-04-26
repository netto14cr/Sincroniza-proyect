<%-- 
    Document   : VerificacionCuenta1Cuenta2
    Created on : 10/04/2020, 05:15:34 PM
    Author     : gabri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="css/estiloCliente/estiloTransferencia1.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
    String cuenta1=(String)request.getSession().getAttribute("cuenta1");
    String cuenta2=(String)request.getSession().getAttribute("cuenta2");
    String opc=(String)request.getAttribute("opcion");
    String errorMonto=(String)request.getAttribute("errorMonto");
    String moneda=(String) request.getSession().getAttribute("moneda");
    String saldo= (String) request.getSession().getAttribute("saldo");
    
    %>
    <body style="background-image: url('css/estiloCliente/cliente/fondo-cliente_2.jpg') "> 
        <h1>Cuenta 1 : <%= cuenta1 %> </h1> <h1>Cuenta 2 : <%= cuenta2 %> </h1>
        <h1>Saldo Cuenta <%= saldo %></h1>
        <h1>Moneda <%= moneda %></h1>
        <%if(errorMonto!=null && errorMonto.equals("1")){%>
        Monto Invalido Digite un Monto Valido y Menor al Saldo
        <form action="ServletVerificacionCuentas" method="get">
            <input type="text" placeholder="Digite el monto" name="monto">
            <button class="boton1"  type="submit">Realizar Transacción</button>
        </form>
        <% }else if(saldo.equals("0.0")==false){// si no hay problemas con nada entonces solicita  %>
        <form action="ServletVerificacionCuentas" method="get">
            <input type="text" placeholder="Digite el monto" name="monto">
            <button class="boton1"  type="submit">Realizar Transacción</button>
        </form>
        <% }else
{%>
<h1 style="color: #ff6600" style="">La cuenta escogida no tiene fondos!</h1>
<%
    
}

%>

<form action="ServletVerificacionCuentas">
    <input name="volverTransferencia" value="1" type="hidden">
    <button  class="boton" type="submit">Volver Transferencias</button>
</form>
<form action="ServletVerificacionCuentas">
    <input name="volverMenu" value="1" type="hidden">
    <button  class="boton" type="submit">Volver Menu </button>
</form>
    </body>
</html>
