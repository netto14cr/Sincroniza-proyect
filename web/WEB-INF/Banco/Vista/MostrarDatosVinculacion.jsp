<%-- 
    Document   : MostrarDatosVinculacion
    Created on : 06/04/2020, 10:57:30 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="model.cliente"%>
<%@page import="modelo.dao.funcionesFrontEnd.funcionesVinculacionCuentas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body><%
        int numCuenta;
        String s=request.getParameter("numeroCuenta");
        try{
        numCuenta=Integer.parseInt(s);
        }catch(NumberFormatException ex)
        {
            numCuenta=-1;
        }
        catch(Exception ex)
        {
           System.out.println("Error : "+ex.getMessage());
        numCuenta=-1;
        }
        
    %>
        <h1>Numero de Cuenta Recibido = <%= numCuenta  %></h1>
        <% funcionesVinculacionCuentas fv=new funcionesVinculacionCuentas();
        String nombre="No Existe";
        String apellidos="No Existe";
        try{
         cliente cl=fv.vincularCuentaACliente(numCuenta);         
         nombre=cl.getNombre();        
         apellidos=cl.getApellidos();
        }catch(Exception e)
        {
            System.out.println("Error : "+e.getMessage());
        }
         
        %>
        <h1 syle="color:black">Datos Dueño</h1>
        <h2>Nombre  : <%= nombre %></h1>
        <h2>Apellidos: <%= apellidos %></h1>
        
        <form action="ServletVerificacionCuenta" method="get">
            <input type="hidden" name="volverMenu" value="1"/>
            <button type="submit">Volver Menu</button>
        </form> 
        
        <form action="ServletVerificacionCuenta" method="get">
            <input type="hidden" name="volverEscogerCuenta" value="1"/>
            <button type="submit">Volver Escoger Cuenta</button>
        </form>
        <% if( (nombre!="No Existe") &&
                (apellidos!="NO Existe")){ %>
        <form action="ServletVerificacionCuenta" method="get">
        <input type="hidden" name="RealizarVinculación" value="1"/>
            <input type="hidden" name="numeroCuenta" value="<%= numCuenta %>"/>
            <button type="submit">Realizar</button>
        </form>
        <form action="ServletVerificacionCuenta" method="get">
            <input type="hidden" name="RealizarVinculación" value="2"/>
            <button type="submit">Cancelar</button>
        </form>
        <% 
        }
        boolean hecho=Boolean.parseBoolean(request.getParameter("Hecho"));
        
%>
<p>Hecho : <%= hecho %></p>
    </body>
</html>
