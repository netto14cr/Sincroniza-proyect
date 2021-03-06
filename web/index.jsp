<%-- 
    Document   : index
    Created on : 19/03/2020, 03:28:42 PM
    Author     : Gabriel Barboza && Néstor Leiva
--%>

<%@page import="model.usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Proyecto I Banco Versión 1.8.0</title>
        <link href="css/estiloFormulario.css" rel="stylesheet" type="text/css"/>
        
        <!--Se declara la direccion y uso de javascript para validacion de la pagina-->
        <script type="text/javascript" src="jsValidaFormularios.js"></script>
    </head>
    <body>
            <!--Definir clase-->
            <div id="wrapper">
                <div id="Deposito1">
            <header>
                    <h1>Proyecto I Banco Versión 1.8.0</h1>
                    <h2>Inicio de Sesión!</h2>
            </header>
                    </div>
            <div id="contents">
          
                <form method="POST" action="registro-usuario" class="e-deposito" 
                      onsubmit="return validarSecion()">
                    
                    <!--Campo para ingresar la identificación-->
                    <p>
                        <label class="texto">Identificación:&nbsp;</label>
                        <input class="campo2" type="text" id="id" name="id" autofocus="autofocus"
                              placeholder="(digite la identificación)" value="" autocomplete="off"/>
                    </p>
                    <!--Campo para que el usuario pueda ingresar la contraseña-->
                    <p>
                        <label class="texto">Contraseña   :   &nbsp;</label>
                        <input class="campo2" type="password" id="password" 
                               name="password" autofocus="autofocus"
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
                            if(msg.equals("ERROR")){
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
                    <%}else if (msg.equals("3")){ %>
                    <p class="mensajeError">LA CONTRASEÑA DIGITADA ES INVALIDA
                    </p>
                    <%} else if (msg.equals("4")){
                    %>
                     <p class="mensajeError">
                        EXISTEN NO PUEDEN EXISTIR CAMPOS VACIOS
                    </p>
                    <% }}%>

                </div>
            <footer></footer>
        </div>
            
    </body>
</html>
