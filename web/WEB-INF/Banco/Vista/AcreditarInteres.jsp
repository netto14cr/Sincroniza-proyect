<%-- 
    Document   : Retiros
    Created on : 28/03/2020, 03:08:35 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="beans.BeanRetiro"%>
<%@page import="beans.BeanRetiro"%>
<%@page import="model.cuenta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Se declara la direccion y uso de estilos para dieseño de la pagina--> 
        <link href="css/estiloFormulario.css" rel="stylesheet" type="text/css"/>
        <!--Se declara la direccion y uso de javascript para validacion de la pagina-->
        <script type="text/javascript" src="jsValidaFormularios.js"></script>
        <!--Se define el uso de la etiqueta bean con datos de deposito bancario-->
        <jsp:useBean class="beans.BeanInteres" id="descInteres" scope="session">
        </jsp:useBean>
        
        <title>Acreditación de Interes</title>
    </head>
    <body>
        <div id="wrapper">
            <div id="Interes">
            <header>
                <h1>Acreditación de Interes</h1>
            </header>
                
            </div>

            <div id="contents">
                <%
                System.out.println("::::::::::   VISTA ACEDITACION INTERES    :::::::::");
                // Se obtienen los mensajes enviados por el Servlet en una variable
                // de tipo string la informacion enviada a la pagina de Deposito
                String msgInteres ="";
                msgInteres = (String) session.getAttribute("servletMsjInteres");
                
                beans.BeanInteres bInteres = (beans.BeanInteres) session.getAttribute("descInteres");
                request.getSession().getAttribute("bInteres");
            
                
                 if (msgInteres==null || msgInteres!=null && msgInteres.equals("ERROR")){
                
                    if (msgInteres!=null && msgInteres.equals("ERROR")){%>
                        <p class="mensajeErrorDep">${descInteres.geteMensaje()}</p><%}%>
                  
                       <!--Se muestra formulario para que el usuario cajero pueda acreditar--> 
                       <!--los intereses a las cuentas de banco en el sistema-->
                    <form method="POST" action="acredita-interes" class="e-deposito2">

                    <!--Campo donde se muestra el numero de cuenta origen -->
                    <p> <h2> Acreditar interes a cuentas del banco </h2></p>
                    <!--Campo donde se muestra el numero de cuenta destino -->
                    <p>
                 
                    <p style="text-align: right;">
                        <button type="submit" class="boton"
                                id="formAcredita" name="formAcredita" value="1"
                                >Autorizar interes</button>
                    </p>
                </form> 
                        
                   <!--Se muestra un formulario para que el cajero pueda cancelar la accion y volver a la 
                    pantalla anterior-->
                <form method="POST" action="regreso-interes" onsubmit=""  class="forBotonRegreso">
                        <button type="submit" id="formAcredita" name="formAcredita" 
                                value="2" class="botonRegreso4">Volver a menú</button>&nbsp;
                    </form> 
                   
                   <% }  
                       else if (msgInteres!=null && msgInteres.equals("READY")){ %>
                
                   <p class="mensajeCorrecto">${descInteres.geteMensaje()} </p>
                            
                   <div class="container">
                   
                   <table border="1">
                            <thead>
                                <tr>
                                    <th>Id Movimiento</th>
                                    <th>Numero cuenta</th>
                                    <th>Nuevo Saldo</th>
                                    <th>Detalle mov</th>
                                    <th>Fecha mov</th>
                                    <th>Nombre depositante</th>
                                </tr>
                            </thead>
                            
                                    <%for(int i=0; i<bInteres.getLista().size(); i++){%>
                            <tbody>
                                    <tr>
                                    <td><%=bInteres.getLista().get(i).getId_movimiento()%></td>
                                    <td><%=bInteres.getLista().get(i).getCuenta_num_cuenta() %></td>
                                    <td><%=bInteres.getLista().get(i).getMonto()%></td>
                                    <td><%=bInteres.getLista().get(i).getDetalle()%></td>
                                    <td><%=bInteres.getLista().get(i).getFecha() %></td>
                                    <td><%=bInteres.getLista().get(i).getDepositante() %></td>
                                </tr>
                            </tbody>
                            <%}%>
                        </table>
                 
                   </div>
                            
                              <!--Se muestra un formulario para que el cajero pueda cancelar la accion y volver a la 
                    pantalla anterior-->
                <form method="POST" action="regreso-interes" onsubmit=""  class="forBotonRegreso">
                        <button type="submit" id="formAcredita" name="formAcredita" 
                                value="3" class="botonRegresoInteres2">Realizar de nuevo</button>&nbsp;
                        <button type="submit" id="formAcredita" name="formAcredita" 
                                value="2" class="botonRegresoInteres">Volver a menú</button>&nbsp;
                    </form> 
                             
                            
                   
                   <%   } %>
            </div>
            <footer></footer>
        </div>
    </body>
</html>
