<%-- 
    Document   : MostrarDatosVinculacion
    Created on : 06/04/2020, 10:57:30 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="model.cliente"%>
<%@page import="modelo.dao.funcionesFrontEnd.funcionesVinculacionCuentas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="css/estiloCliente/estiloPaginaDatosAfiliacion.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
     <%
     DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");
            Date date = new Date();
    %>
    <body style="background-image: url('css/estiloCliente/cliente/fondo-cliente_2.jpg')">
          <jsp:useBean class="beans.BeanLogin" id="eLogin" scope="session">
        </jsp:useBean>
        <%
            int numCuenta;
            String s = request.getParameter("numeroCuenta");
            try {
                numCuenta = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                numCuenta = -1;
            } catch (Exception ex) {
                System.out.println("Error : " + ex.getMessage());
                numCuenta = -1;
            }
            funcionesVinculacionCuentas fv = new funcionesVinculacionCuentas();
            String nombre = "No Existe";
            String apellidos = "No Existe";
            try {
                cliente cl = fv.vincularCuentaACliente(numCuenta);
                nombre = cl.getNombre();
                apellidos = cl.getApellidos();
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }

        %>
        <div id="wrapper">
            <div id="contents">
                <div id="encabezado" style=" background-image: url('css/estiloCliente/cliente/encabezado_1.jpg')">
                    <span id="fecha" style="font-size: 25px"><%= dateFormat.format(date)%></span>
                    <div id="InfoCliente">                        
                        <h2>Id Usuario: <jsp:getProperty name="eLogin" property="eIdentificacion"/></h2>
                    </div>
                </div>
                    <div id="informacion">
            <h1>Numero de Cuenta Escogido = <%= numCuenta%></h1>
            <h1 syle="color:black">Datos Dueño</h1>
            <h2>Nombre  : <%= nombre%></h1>
                <h2>Apellidos: <%= apellidos%></h1>
                <div id="opciones">
                    <form action="ServletVerificacionCuenta" method="get">
                        <input type="hidden" name="volverMenu" value="1"/>
                        <button class="boton" type="submit">Volver Menu</button>
                    </form> 

                    <form action="ServletVerificacionCuenta" method="get">
                        <input type="hidden" name="volverEscogerCuenta" value="1"/>
                        <button class="boton"  type="submit">Volver Escoger Cuenta</button>
                    </form>
                    <% if ((nombre != "No Existe")
                                && (apellidos != "NO Existe")) {%>
                    <form action="ServletVerificacionCuenta" method="get">
                        <input type="hidden" name="RealizarVinculación" value="1"/>
                        <input type="hidden" name="numeroCuenta" value="<%= numCuenta%>"/>
                        <button class="boton1"  type="submit">Realizar</button>
                    </form>
                    <form action="ServletVerificacionCuenta" method="get">
                        <input type="hidden" name="RealizarVinculación" value="2"/>
                        <button class="boton"  type="submit">Cancelar</button>
                    </form
                    </div>
                    <%
                        }

                        String hecho = (String) request.getSession().getAttribute("Hecho");
                        if(hecho==null)
{
hecho="No se ha Vinculado";
}
                    %>
                    <p style="color:cyan ">Hecho : <%= hecho%></p>
                    </div>
                    </div>
                    </div>
                    </body>
                    </html>
