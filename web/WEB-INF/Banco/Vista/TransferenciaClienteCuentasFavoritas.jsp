<%-- 
    Document   : TransferenciaClienteCuentasFavoritas
    Created on : 09/04/2020, 09:02:31 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="model.cuenta"%>
<%@page import="model.favorita"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="css/estiloCliente/estiloTransferencia1.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
        List<favorita> lis = (List<favorita>) request.getSession().getAttribute("listaFavoritas");
        List<cuenta> list = (List<cuenta>) request.getSession().getAttribute("listaCuentas");
        String cu1 = (String) request.getParameter("cuenta1"), cu2 = (String) request.getParameter("cuenta2");
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");
        Date date = new Date();
    %>
    <body style="background-image: url('css/estiloCliente/cliente/fondo-cliente_2.jpg') "> 
          <div id="wrapper">
            <div id="encabezado" style=" background-image: url('css/estiloCliente/cliente/encabezado_1.jpg')">
<jsp:useBean class="beans.BeanLogin" id="eLogin" scope="session">
        </jsp:useBean>

                <span id="fecha" style="font-size: 25px"><%= dateFormat.format(date)%></span>
                <div id="InfoCliente">
                    <h2 style ="text-decoration: underline; ">Sección Cliente</h2>
                    <h2>Id Usuario: <jsp:getProperty name="eLogin" property="eIdentificacion"/></h2>
                </div>
            </div>
                <div id="opciones" style="background-image: url('css/estiloCliente/cuentas-clientes/menu-cuentas_1.jpg') "">
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
                    <option class="boton" value="<%= elem.getCuenta_num_cuenta()%>" ><%= elem.getCuenta_num_cuenta()%></option>
                    <%
                        }
                    %>  

                </select>
                <button  class="boton1" type="submit">send</button>
            </form>


            <form action="ServletVerificacionCuentas">
                <input name="volverMenu" value="1" type="hidden">
                <button class="boton"  type="submit">Volver Menu </button>
            </form>


        </form>
        </div>
    </div>
</body>
</html>
