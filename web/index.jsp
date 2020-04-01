<%-- 
    Document   : index
    Created on : 19/03/2020, 03:28:42 PM
    Author     : netto
--%>

<%@page import="model.usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Proyecto I Banco Versión 1.0</title>
        <link href="css/estiloFormulario.css" rel="stylesheet" type="text/css"/>
        
        <!--Se declara la direccion y uso de javascript para validacion de la pagina-->
        <script type="text/javascript" src="jsValidaFormularios.js"></script>
    </head>
    <body>
            <!--Definir clase-->
            <div id="wrapper">
                <div id="Deposito1">
            <header>
                    <h1>Proyecto I Banco Versión 1.0</h1>
                    <h2>Iniciar Sesión</h2>
            </header>
                    
                    </div>
            <div id="contents">
                
                
<!--                <p class="information2">
                    APARTIR DE ESTA ACTUALIZACIÓN SE PODRAN OBSERVAR LOS NUEVOS CAMBIOS
                    DESDE LA PAGINA PRINCIPAL ;D!
                </p>
                
                <p class="information">
                    <strong>CAMBIOS V 1.3 <i>FIX R3</i></strong><br><br>
                    <i>-Se reestructuro en un 75% el servlet de deposito: </i><br>
                    Esto permitio poder acoplar los cambios para determinar si a la hora
                    de realizar un deposito la persona que de indentifica y el numero de 
                    cuenta o numero de identificacion ingresada determinan si la cuenta
                    esta asociada a el o no... De lo contrario se pedira que ingrese su 
                    nombre como bien indica el R3.
                </p>
                <br>-->
                
                <form method="GET" action="registro-usuario" class="e-deposito" onsubmit="return validarSecion()">
                    
                    <!--Campo para ingresar la identificación-->
                    <p>
                        <label class="texto">Identificación:&nbsp;</label>
                        <input class="campo2" type="text" id="id" name="id" autofocus="autofocus"
                               placeholder="(digite la identificación)" autocomplete="off"/>
                    </p>
                    <!--Campo para que el usuario pueda ingresar la contraseña-->
                    <p>
                        <label class="texto">Contraseña   :   &nbsp;</label>
                        <input class="campo2" type="password" id="password" name="password" autofocus="autofocus"
                               placeholder="(digite su contraseña)" autocomplete="off"/>
                        
                    </p>
                    <!--Boton que ejecuta y redireciona-->
                    <p>
                        <button type="submit" class="botonInit">Iniciar Sesión</button>
                    </p>
                </form>
                <br>
            
                    <!--Validacion de los parametros digitados y recibidos por el servidor-->
                    <% 
                    usuario user=(usuario) request.getAttribute("registro-usuario");
                    String id = request.getParameter("id");
                    String pass=request.getParameter("password");
                    
//                    Si el usuario ingreso parametros en la identificacion y contraseña
//                    verifica la respuesta del servlet y si ocurre un error con que sean
//                    equivocos.
                        if ((id != null) && !id.isEmpty() &&  pass!=null && !pass.isEmpty()) {
                            String msg = (String) session.getAttribute("servletMsjError");
//                            System.out.println("VER__:::"+msg);
                            if(msg.equals("1")){
                    %>

                    <p class="mensajeError">
                        EL USUARIO <strong><%= id%></strong> NO EXISTE NUESTRO SISTEMA
                        : <strong><%= id%></strong>.
                    </p>
                    <%
                    } else if (msg.equals("2")){
                    %>
                     <p class="mensajeError">
                        EL CLIENTE <strong><%= id%></strong> NO EXISTE EN EL SISTEMA
                        
                    </p>
                    <%
                    } else if (msg.equals("3")){
                    %>
                    <p class="mensajeError">
                        LA CONTRASEÑA DIGITADA ES INVALIDA
                    </p>
                    <% }
                        }
                        %>

                </div>
                
                
                
            <footer></footer>
        </div>
            
    </body>
</html>

            
                <!---------------------------------------------------------------->   
    