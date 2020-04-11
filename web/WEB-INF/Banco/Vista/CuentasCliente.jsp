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
       <p>Mostramos las cuentasa del cliente con la cedula</p>
        <p>Cedula :<%= bl.geteIdentificacion()%> </p>
        <p>Contraseña :<%= bl.getePasword()%> </p>
 <%-- 
       
<p>f1 <%= fech1%></p>
<p>f2 <%= fech2%></p>  <form method="POST" action="" >

            <input type="date" name="fech1" value="">

            <input type="date" name="fech2" value="">
            <%
                String fech1 = request.getParameter("fech1"), fech2 = request.getParameter("fech2");
            %>
            <button type="submit">Submit</button>
        </form>

      <table class="tablaCuentas">
            <thead>
            <caption><strong><h1>Cuentas Cliente</h1></strong></caption>
            <tr>
                <th>Número Cuenta</th>
                <th>Tipo Cuenta</th>
                <th>Moneda</th>
                <th>Fecha Creación</th>
                <th>Fecha Ultima Aplicación</th>
                <th>Saldo Actual</th>
            </tr>
        </thead>

        <tbody>
            <%
                for (cuenta elem : lis) {
            %>

            <tr>
                <td class="col-cuentas"><%= elem.getNum_cuenta()%></td>
                <td class="col-cuentas"><%= elem.getTipo_cuenta_id_tipo_cuenta()%></td>
                <td class="col-cuentas"><%= elem.getMoneda_nombre()%></td>
                <td class="col-cuentas"><%= elem.getFecha_creación()%></td>
                <td class="col-cuentas"><%= elem.getFecha_ultima_aplicación()%></td>
                <td class="col-cuentas"><%= elem.getSaldo_actual()%></td>
            </tr>

            <%
                }
                String numCu = request.getParameter("numeroCuenta");
            %>
        </tbody> --%>
    </table>

 
 <script>
function validateFormRANGO() {
    var x = document.getElementById("numeroCuenta").value;
    var y = document.getElementById("Maximo").value;
    alert(x);
    alert(y);
    if ((isNaN(x) || isNaN(y))
            || ((x > y) || x < 0)) {
        alert("El numero de cuenta es invalido");
        return false;
    }
}
<%
 String numCu = request.getParameter("numeroCuenta");
%>
</script> 
    <p><strong>Seleccione una cuenta para ver sus detalles</strong></p>
<form name="myForm"  action="datosMovimientoCuenta"  <%--onsubmit="return validateFormRANGO() "--%> method="get">
   <select name="numeroCuenta">
        <% for (cuenta elem : lis) { %>
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

</body>
</html>
