<%-- 
    Document   : MovimientosCuenta
    Created on : 05/04/2020, 07:12:00 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="model.movimiento"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="css/estiloCliente/estiloPaginaDetalleCuenta.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body style="background-image: url('css/estiloCliente/cliente/fondo-cliente_2.jpg') " 

          >
        <div id="wrapper">
            <%
                beans.BeanLogin bl = (beans.BeanLogin) session.getAttribute("beanLogin");
                List<movimiento> lis = (List<movimiento>) request.getSession().getAttribute("listaMovimientos");

                String saldo = request.getSession().getAttribute("saldo").toString();
                String moneda = request.getSession().getAttribute("moneda").toString();
                DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");
                Date date = new Date();
            %>

            <div id="encabezado" style=" background-image: url('css/estiloCliente/cliente/encabezado_1.jpg')">


                <span id="fecha" style="font-size: 25px">   <%= dateFormat.format(date)%></span>
                <div id="InfoCliente">
                    <%-- <h2 style ="text-decoration: underline; ">Sección Cliente</h2> --%>
                    <h2>    Id Usuario:><%= bl.geteIdentificacion()%> </h2>

                </div>
            </div>
            <div id="contenido">
                <div id="infoCuenta">
                    <h1>Información de la Cuenta </h1>
                    <h2>Cuenta : <%= request.getParameter("numeroCuenta")%></h2>
                    <h2>Saldo = <%= saldo%> ,  Moneda :  <%= moneda%></h2>
                    <h2>Cedula :<%= bl.geteIdentificacion()%> </h2>
                    <form method="get" action="ServletMovimientosCuentas" >
                    <input type ="hidden" name="volver" value="1">
                    <button type="submit" >Volver</button>
                </form>
                </div>

                <div id="seleccionFecha">
                    <%-- <p>Contraseña :<%= bl.getePasword()%> </p> --%>
                    <form method="get" action="ServletMovimientosCuentas" >

                        <input type="date" name="fech1" value="">

                        <input type="date" name="fech2" value="">
                        <input type="hidden" name="numeroCuenta" value="<%=request.getParameter("numeroCuenta")%>">
                        <%

                            String fech1 = request.getParameter("fech1"), fech2 = request.getParameter("fech2");
                        %>
                        <button type="submit">Submit</button>
                    </form>

                    <p>Desde : <%= fech1%> --  Hasta <%= fech2%> . </p> 

                </div>
                    <div id="muestraDatos">
                <table class="tabla">
                    <thead>
                    <caption><strong><h1 style="color: #00cc99">Cuentas Cliente</h1></strong></caption>
                    <tr>
                        <th>id-movimiento</th>
                        <th>num-cuenta</th>
                        <th>monto</th>
                        <th>Fecha de movimiento</th>
                        <th>Detalle</th>
                        <th>Depositante</th>
                        <th>Aplicado</th>
                    </tr>
                    </thead>

                    <tbody>
                        <%if (lis.size() == 0) {
                        %>
                    <h1 style="background-color: #006666" style="color: #ff6600">*********************No Existen movimientos en estos plazos...</h1>


                    <%
                        }
                        for (movimiento elem : lis) {
                    %>


                    <tr>
                        <td class="col-cuentas"><%= elem.getId_movimiento()%></td>
                        <td class="col-cuentas"><%= elem.getCuenta_num_cuenta()%></td>
                        <td class="col-cuentas"><%= elem.getMonto()%></td>
                        <td class="col-cuentas"><%= elem.getFecha()%></td>
                        <td class="col-cuentas"><%= elem.getDetalle()%></td>
                        <td class="col-cuentas"><%= elem.getDepositante()%></td>
                        <td class="col-cuentas"><%= elem.getAplicado()%></td>
                    </tr>

                    <%   }%>
                    </tbody> 
                </table>
                    </div>
                <%-- <nav>
                          <p>
                              Haga clic
                              <a href="/WEB-INF/Banco/Vista/MovimientosCuenta.jsp">aquí</a>
                              para ir a la página del formulario.
                          </p>
                      </nav>
                --%>
                
               
                

            </div>
        </div>
    </body>
</html>
