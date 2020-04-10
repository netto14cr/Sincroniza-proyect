<%-- 
    Document   : SolicitarCuentaVinculacion
    Created on : 06/04/2020, 10:41:09 PM
    Author     : gabri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <h1>Digite el numero de cuenta</h1>
        
        <form action="ServletVerificacionCuenta" method="get">
            <input type="text" name="numeroCuenta"/>
            <button type="submit">Vincular</button>
        </form>
        
        <form action="ServletVerificacionCuenta" method="get">
            <input type="hidden" name="volverMenu" value="1"/>
            <button type="submit">Volver Menu</button>
        </form>
        
    </body>
</html>
