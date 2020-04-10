<%-- 
    Document   : TransferenciaClienteCuentasFavoritas
    Created on : 09/04/2020, 09:02:31 PM
    Author     : gabri
--%>

<%@page import="model.favorita"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
    List<favorita> lis=( List<favorita> ) request.getSession().getAttribute("listaFavorita");
    
    %>
    <body>
        <h1>Transferencias</h1>
        <h2>Cuentas Favoritas</h2>
        <form method="get" action="ServletTransferenciaClienteCuentaFavo">
            <input name="cuenta1" type="text" />
            <input name="cuenta2" type="text" />
            <button type="submit"> enviar </button>
        </form>
    </body>
</html>
