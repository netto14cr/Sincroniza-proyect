<%-- 
    Document   : MostrarTrasnferenciaRealizada
    Created on : 10/04/2020, 05:37:55 PM
    Author     : gabri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
        String monto = (String) request.getParameter("monto");
        String cuenta1 = (String) request.getSession().getAttribute("cuenta1");
        String cuenta2 = (String) request.getSession().getAttribute("cuenta2");
        String aceptar= (String) request.getParameter("aceptar");
        //String cancelar= (String) request.getParameter("cancelar");
        boolean realizado=Boolean.parseBoolean((String)request.getSession().getAttribute("realizado"));
        
    %>
    <body>
        <h1>El monto obtenido es :<%= monto%> </h1>
        
        <% if(aceptar==null){ %>
        <h1>Trasnsacción de Cuenta Origen = <%= cuenta1%> a Cuenta Destino : <%= cuenta2%></h1>
        <form action="ServletMostrarTransferencia" method="get">
            <input type="hidden" name ="monto" value="<%= monto %>">
            <button value="1" name="aceptar" type="submit">Aceptar</button>
            <button value="1" name="cancelar" type="submit">Cancelar</button>
        </form>
        <% } else if(realizado==true){

        %>
        <h2 style="color: #00cc99" >Procedemos a mostrar mensaje de confirmacion de la accion</h2>
        <h2 style="color: #00cc99">Exito</h2>
 <%
 }//else if(realizado==null){
 %>          
<%--<h2 style="color: #ff6600">Hubo algun error</h2>--%>
<%//} %> 
            <form action="ServletVerificacionCuentas">
            <input name="volverTransferencia" value="1" type="hidden">
            <button type="submit">Volver Transferencias</button>
        </form>
        <form action="ServletVerificacionCuentas">
            <input name="volverMenu" value="1" type="hidden">
            <button type="submit">Volver Menu </button>
        </form>
    </body>
</html>
