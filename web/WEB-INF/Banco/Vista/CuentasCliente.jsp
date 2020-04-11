<%-- 
    Document   : CuentasCliente
    Created on : 04/04/2020, 12:48:07 AM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="model.cuenta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="css/estiloCuentaCliente.css" rel="stylesheet" type="text/css"/>
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
    %>

    <body>
        <div id="wrapper">
            <p>Mostramos las cuentasa del cliente con la cedula</p>
            <p>Cedula :<%= bl.geteIdentificacion()%> </p>
            <p>Contraseña :<%= bl.getePasword()%> </p>

            <%
                String numCu = request.getParameter("numeroCuenta");
            %>
        </script> 
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
        <p>Numero de cuenta : <%= numCu%></p>
    </div>
</body>
</html>
