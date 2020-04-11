<%-- 
    Document   : TransferenciaClienteCuentasFavoritas
    Created on : 09/04/2020, 09:02:31 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="model.cuenta"%>
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
        List<favorita> lis = (List<favorita>) request.getSession().getAttribute("listaFavoritas");
        List<cuenta> list=(List<cuenta>) request.getSession().getAttribute("listaCuentas");
        String cu1 = (String) request.getParameter("cuenta1"), cu2 = (String) request.getParameter("cuenta2");
    %>
    <body>
        <h1>Transferencias</h1>
        <h2>Cuentas Favoritas</h2>
        <form method="get" action="ServletTransferenciaClienteCuentaFavo">
            Cuenta Origen :  <select name="cuenta1" >
                <%
                    for (cuenta elem : list) {
                %>
                <option value="<%= elem.getNum_cuenta()%>" ><%= elem.getNum_cuenta()%></option>
                <%
                    }
                %>  

            </select> 

            Cuenta Destino :  <select name="cuenta2" >
                <%
                    for (favorita elem : lis) {
                %>
                <option value="<%= elem.getCuenta_num_cuenta()%>" ><%= elem.getCuenta_num_cuenta()%></option>
                <%
                    }
                %>  

            </select>
                <button type="submit">send</button>
        </form>


<form action="ServletVerificacionCuentas">
    <input name="volverMenu" value="1" type="hidden">
    <button type="submit">Volver Menu </button>
</form>


    </form>
</body>
</html>
