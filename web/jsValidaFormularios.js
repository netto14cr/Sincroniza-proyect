/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




// Funcion que valida los campos del formulario de inicio de Sesión
function validarSecion() {
var datosRecibidos;
        datosRecibidos = true;
        var error = "";
        var numeroIden = document.getElementById('id').value,
        cedulaValida = /^[1-9]-?\d{4}-?\d{4}$/;
        // Si el campo de número de cédula de usuario esta en blanco se detecta como error
        if (document.getElementById('id').value == "") {
error = "\n El campo de la cédula está en blanco";
        datosRecibidos = false;
        }
// Falso si el formato ingresado de número de cédula es incorrecto
else if (!cedulaValida.test(numeroIden)) {
error = "\n El numero de cédula: " + numeroIden + " es incorrecto!\nPor favor intente el formato\n\
            1-0000-0000";
        datosRecibidos = false;
        } else if (document.getElementById('password').value == "") {
error = "\n El campo de la contraseña está en blanco";
        datosRecibidos = false;
        }

// Si existen datos incorrecto entonces se realiza un alert con el error ocurrido al usuario
if (!datosRecibidos) {
alert('ERROR: ' + error);
        }
return datosRecibidos;
}

//--------------------FIN VALIDACION DE INICIO DE SESIÓN------------------------

//----------------- VALIDACION DE CAMBIO DE CONTRASEÑA VENCIDA -----------------

function validarCambioPassw() {
var datosCorrectos7;
        datosCorrectos7 = true;
        var error7 = "";
        var passExam = 'Avion14$';
        var espacios = false;
        var cont = 0;
        var passN = document.getElementById('passwNew').value;
        var passR = document.getElementById('passwRep').value;
        if (passN.length >= 8){
            var mayuscula = false;
            var minuscula = false;
            var numero = false;
            var caracter_raro = false;
        for (var i = 0; i < passN.length; i++){
            if (passN.charCodeAt(i) >= 65 && passN.charCodeAt(i) <= 90)
                {mayuscula = true; }
            else if (passN.charCodeAt(i) >= 97 && passN.charCodeAt(i) <= 122)
                {minuscula = true; }
            else if (passN.charCodeAt(i) >= 48 && passN.charCodeAt(i) <= 57)
                {numero = true; }
            else
                {caracter_raro = true; }
}


// Valida que la contraseña nueva digitada sea igual a la contraseña del ejemplo
if (passN.toString() == passExam.toString()) {
error7 = "\n La contraseña nueva no puede ser igual a la del ejemplo!\n\n M A M O N  ;D";
        datosCorrectos7 = false;
        }


else if (mayuscula == false || minuscula == false || caracter_raro == false || numero == false)
        {
        error7 = "\n La contraseña no es segura\n\n Por favor asegurese de ingresar otra \n\
con las siguentes condiciones:\n\n-Mayuscula\n-Caracter especial\n-Numero\n-Minuscula\n\n\n\
Ejemplo: Avion14$";
                datosCorrectos7 = false;
                }
}

// Valida que el campo de nueva contraseña contraseña no este en blanco
else if (document.getElementById('passwNew').value.toString() == '') {
error7 = "\n El campo de la nueva contraseña está en blanco";
        datosCorrectos7 = false;
        }

// Valida que el campo de repeticion de la contraseña no este en blanco
else if (document.getElementById('passwRep').value.toString() == '') {
error7 = "\n El campo de repetición de la contraseña está en blanco";
        datosCorrectos7 = false;
        }
else if (passN.length == 0 || passR.length == 0) {
error7 = "\n Los campos de la contraseña no pueden quedar vacios";
        datosCorrectos7 = false;
        }

// Valida que la contraseña nueva digitada sea igual a la contraseña digitada
// en el campo de repetir la misma
else if (passN.toString() != passR.toString()) {
error7 = "\n Las contraseña y la repeticion no pueden ser diferentes!";
        datosCorrectos7 = false;
        }

// Valida que la contraseña no sea menor a 8 caracteres
else if (passN.length <= 8)
        {
        error7 = "\nLa contraseña no puede ser menor a 8 caracteres!";
                datosCorrectos7 = false;
                }



// Si existen datos incorrecto entonces se realiza un alert con el error ocurrido al usuario
if (!datosCorrectos7) {
alert('ERROR: ' + error7);
        }
return datosCorrectos7;
}


//---------------   FIN VALIDACION DE CAMBIO DE CONTRASEÑA VENCIDA--------------


//----------------- VALIDACION DE APERTURA DE CUENTA ---------------------------

// :::::::::::::   Funcion que valida los campos del formulario numero 1  ::::::
function validarForumlario1() {
var datosCorrectos;
        datosCorrectos = true;
        var error = "";
        var numCedula = document.getElementById('numeroCedula').value,
        cedulaValida = /^[1-9]-?\d{4}-?\d{4}$/;
        // Si el campo de número de cédula de usuario esta en blanco se detecta como error
        if (document.getElementById('numeroCedula').value == "") {
error = "\n El campo de la cédula está en blanco";
        datosCorrectos = false;
        }
// Falso si el formato ingresado de número de cédula es incorrecto
else if (!cedulaValida.test(numCedula)) {
error = "\n El numero de cédula: " + numCedula + " es incorrecto!\nPor favor intente el formato\n\
            1-0000-0000";
        datosCorrectos = false;
        }

// Si existen datos incorrecto entonces se realiza un alert con el error ocurrido al usuario
if (!datosCorrectos) {
alert('ERROR: ' + error);
        }
return datosCorrectos;
}


// :::::::::: Funcion que valida los campos del formulario numero 2 ::::::::::::

function validarForumlario2() {
var datosCorrectos2;
        datosCorrectos2 = true;
        var error2 = "";
        var numTel=document.getElementById('nTelefonoUser').value;
        var nombreUs=document.getElementById('nombreUser').value;
        var apellidosUs=document.getElementById('apellidosUser').value;
        // Si el campo nombre de usuario esta en blanco se detecta como error
        if (document.getElementById('nombreUser').value == "") {
            error2 = "\n El campo de nombre está en blanco";
            datosCorrectos2 = false;
        }
        // Valida que el nombre sea mayor a dos letras
        else if (nombreUs.length<=2){
            error2 = "\n El nombre no puede ser menor o igual a 2 letras";
            datosCorrectos2 = false;
        }
        
        
        // Falso Si el campo de aplellidos del usuario esta en blanco se detecta como error
        else if (document.getElementById('apellidosUser').value == "") {
            error2 = "\n El campo de apellidos está en blanco ";
            datosCorrectos2 = false;
        }
        
        // Valida que el apellidos sea mayor a cinco letras
        else if (apellidosUs.length<5){
            error2 = "\n El nombre no puede ser menor a 5 letras";
            datosCorrectos2 = false;
        }

        // Falso si el campo de numero de telefono del usuario esta en blanco se detecta como error
        else if (document.getElementById('nTelefonoUser').value == "") {
            error2 = "\n El campo de número de telefono está en blanco ";
            datosCorrectos2 = false;
        }
        
        // Valida que el valor ingresado en el campo numero de telefono sea numerico
        else if (isNaN(numTel) ) {
            error2 = "\n El valor ingresado en el campo de telefono no es una experesion numerica! ";
            datosCorrectos2 = false;
        }
        
        // Valida que el tamaño de numero telefonico ingresado
        else if( !(/^\d{8}$/.test(numTel)) ) {
            error2 = "\n El numero telefonico debe contener 8 digitos! ";
            datosCorrectos2 = false;
        }
        
            
        // Si existen datos incorrecto entonces se realiza un alert con el error ocurrido al usuario
        if (!datosCorrectos2) {
            alert('ERROR: ' + error2);
        }
return datosCorrectos2;
}

//  Funcion que verifica los campo de la cuenta y cambia de color si no se
// les ha ingresado datos
function verificarCuenta(campos) {

if (campos.value == '') {
campos.ClassName = 'error';
        } else {
campos.ClassName = 'campo';
        }
}

function revisarEmail(elemento) {
if (elemento.value !== '') {
var datoE = elemento.value;
        var exp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        // Si es verdadera es que el correo es valido
        if (!exp.test(datoE)) {
elemento.ClassName('error');
        }// Falso es incorrecto 
else {
elemento.ClassName('campo');
        }
}
}


//-----------------FIN VALIDACION DE APERTURA DE CUENTA ---------------------------


//----------------- VALIDACION DEPOSITOS BANCARIOS ---------------------------------

// Funcion que valida los campos del formulario numero 1 - busqueda de cuenta
// por deposito
function validarForumlarioDeposito1() {
var datosCorrectos4;
        datosCorrectos4 = true;
        var error4 = "";
        var cd = document.getElementById('nCuentaDeposito').value,
        cedulaFormato = /^[1-9]-?\d{4}-?\d{4}$/, expNumerica = /^[1-1000000]$/;
        var tipoBusqueda = document.getElementById('tipoBusquedaCuenta');
        //var op1='nCedula';

        // Si el campo de número de cédula de usuario esta en blanco se detecta como error


        //if (tipoBusqueda == "nCedula") {

        console.log("::::METODO DE DEPOSITO:::");
        if (tipoBusqueda.value.toString() == 'nCedula') {
            console.log("<<<<< ENTRO EN CEDULA >>>>");
            if (document.getElementById('nCuentaDeposito').value == "") {
                error4 = "\n El campo de la cédula está en blanco";
                datosCorrectos4 = false;
            } else if (!cedulaFormato.test(cd)) {
                error4 = "\n El numero de cédula: " + cd + " es incorrecto!\nPor favor intente el formato\n\
                1-0000-0000";
                datosCorrectos4 = false;
            }
        
        } else if (tipoBusqueda.value.toString() == 'nCuenta') {
            console.log("<<<<< ENTRO EN NUMERO DE CUENTA >>>>");
            if (document.getElementById('nCuentaDeposito').value == "") {
                error4 = "\n El campo del numero de cuenta está en blanco";
                datosCorrectos4 = false;
            }
            else if (isNaN(cd) ) {
                error4 = "\n El valor en el numero de cuenta no es un valor numerico! ";
                datosCorrectos4 = false;
            }
        }

    // Si existen datos incorrecto entonces se realiza un alert con el error ocurrido al usuario
    if (!datosCorrectos4) {
        alert('ERROR: ' + error4);
        }
return datosCorrectos4;
}


// Funcion que valida los campos del formulario numero 2 - deposito de efectivo
function validarForumlarioDeposito2() {
var datosCorrectos5;
        datosCorrectos5 = true;
        var error5 = "";
        var cd = document.getElementById('detalleNumId').value,
        cedulaFormato = /^[1-9]-?\d{4}-?\d{4}$/;
        var detMonto=document.getElementById('montoDeposito').value;
        
        // Si el campo de monto del deposito esta vacio , se valida que debe ingresar 
        // informacion en este espacio
        if (document.getElementById('montoDeposito').value == "") {
error5 = "\n El campo monto de deposito no puede estár en blanco";
        datosCorrectos5 = false;
        }


// Falso si el monto digitado es menor o igual a 0 se  valida su error
else if (document.getElementById('montoDeposito').value <= 0){
error5 = "\n El campo monto de deposito no puede ser menor o igual a 0";
        datosCorrectos5 = false;
        }
        
         // Valida que el campo ingresado en monto sea una expresion numerica
        else if (isNaN(detMonto) ) {
            error5 = "\n El valor ingresado en el campo monto de deposito no es una experesion numerica! ";
            datosCorrectos = false;
        }
        
         // Valida que el monto ingresado para deposito sea una expresion numerica
        else if (detMonto.length >= 7 ){
            error5 = "\n La cifra excede el limite permitido para transferencia del sistema!\n\
             Por favor intente un valor menor a un millon!   ";
            datosCorrectos = false;
        }
        
        
         // Valida si el campo de identificacion va vacio
        else if (document.getElementById('detalleNumId').value == "") {
            error5 = "\n El campo de numero de identificacion de depositante está en blanco";
            datosCorrectos5 = false;
            
        }
        // Valida si el formato ingresado en la cedula es correcto
        else if (!cedulaFormato.test(cd)) {
            error5 = "\n El numero de cédula: " + cd + " es incorrecto!\nPor favor intente el formato\n\
            1-0000-0000";
            datosCorrectos5 = false;
        }

// Si existen datos incorrecto entonces se realiza un alert con el error ocurrido al usuario
if (!datosCorrectos5) {
alert('ERROR: ' + error5);
        }
return datosCorrectos5;
}



// Funcion que valida los campos del formulario numero 2 - deposito de efectivo
function validarForumlarioDeposito3() {
var datosCorrectos5;
        datosCorrectos5 = true;
        var error5 = "";
        var cd = document.getElementById('detalleNumId').value,
        cedulaFormato = /^[1-9]-?\d{4}-?\d{4}$/;
        var detMonto=document.getElementById('montoDeposito').value;
        var detNombre=document.getElementById('detalleNombreDep').value;
        
        // Si el campo de monto del deposito esta vacio , se valida que debe ingresar 
        // informacion en este espacio
        if (document.getElementById('montoDeposito').value == "") {
error5 = "\n El campo monto de deposito no puede estár en blanco";
        datosCorrectos5 = false;
        }

// Falso si el detalle de deposito va en blanco se valida su error
else if (document.getElementById('detalleNombreDep').value=="") {
error5 = "\n El campo de nombre depositante está en blanco";
        datosCorrectos5 = false;
        }

// Falso si el monto digitado es menor o igual a 0 se  valida su error
else if (document.getElementById('montoDeposito').value <= 0){
error5 = "\n El campo monto de deposito no puede ser menor o igual a 0";
        datosCorrectos5 = false;
        }
        
         // Valida que el campo ingresado en monto sea una expresion numerica
        else if (isNaN(detMonto) ) {
            error5 = "\n El valor ingresado en el campo monto de deposito no es una experesion numerica! ";
            datosCorrectos = false;
        }
        
         // Valida que el monto ingresado para deposito sea una expresion numerica
        else if (detMonto.length >= 7 ){
            error5 = "\n La cifra excede el limite permitido para transferencia del sistema!\n\
             Por favor intente un valor menor a un millon!   ";
            datosCorrectos = false;
        }
        
        
        
        // Valida que el detalle del nombre tenga al menos 5 caracteres ingresados
        else if (detNombre.length < 5 ){
            error = "\n El campo del nombre no puede ser menor a 5 letras! ";
            datosCorrectos = false;
        }
        
        
        
         // Valida si el campo de identificacion va vacio
        else if (document.getElementById('detalleNumId').value == "") {
            error5 = "\n El campo de numero de identificacion de depositante está en blanco";
            datosCorrectos5 = false;
            
        }
        // Valida si el formato ingresado en la cedula es correcto
        else if (!cedulaFormato.test(cd)) {
            error5 = "\n El numero de cédula: " + cd + " es incorrecto!\nPor favor intente el formato\n\
            1-0000-0000";
            datosCorrectos5 = false;
        }

// Si existen datos incorrecto entonces se realiza un alert con el error ocurrido al usuario
if (!datosCorrectos5) {
alert('ERROR: ' + error5);
        }
return datosCorrectos5;
}



// Funcion que valida le formulario de deposito por numero de cuenta

function validarDepNumCuenta() {
        var datosCorrectos;
        datosCorrectos = true;
        var error = "";
        var cd = document.getElementById('detalleNumId').value,
        cedulaFormato = /^[1-9]-?\d{4}-?\d{4}$/, expNumerica = /^[0-1000000]$/;
        var detMonto=document.getElementById('detalleMontoDep').value;
        
         console.log("ENTRA EN METODO DE VALIDACION");
        
        // Valida si el campo de identificacion va vacio
         if (document.getElementById('detalleNumId').value == "") {
            error = "\n El campo de numero de identificacion de depositante está en blanco";
            datosCorrectos = false;
            console.log("VALIDA ID");
            
        }
        // Valida si el formato ingresado en la cedula es correcto
        else if (!cedulaFormato.test(cd)) {
            error = "\n El numero de cédula: " + cd + " es incorrecto!\nPor favor intente el formato\n\
            1-0000-0000";
            datosCorrectos = false;
            console.log("VALIDA ID FORMATO");
        }
        
       
        // Valida que el campo ingresado en monto sea una expresion numerica
        else if (isNaN(detMonto) ) {
            error = "\n El valor ingresado en el campo monto de deposito no es una experesion numerica! ";
            datosCorrectos = false;
            console.log("VALIDA TIPO MONTO");
        }
        
        
        // Falso si el monto digitado es menor o igual a 0 se  valida su error
        else if (detMonto <= 0){
            error = "\n El campo monto de deposito no puede ser menor o igual a 0";
            datosCorrectos = false;
            console.log("VALIDA VALOR MONTO");
        }
        
         // Valida que el monto ingresado para deposito sea una expresion numerica
        else if (detMonto.length >= 7 ){
            error = "\n La cifra excede el limite permitido para transferencia del sistema!\n\
             Por favor intente un valor menor a un millon!   ";
            datosCorrectos = false;
            console.log("VALIDA MAX MONTO");
        }

// Si existen datos incorrecto entonces se realiza un alert con el error ocurrido al usuario
if (!datosCorrectos) {
alert('ERROR: ' + error);
        }
return datosCorrectos;
}


// Funcion que valida le formulario de deposito por numero de cuenta

function validarDepNumCuenta2() {
        var datosCorrectos;
        datosCorrectos = true;
        var error = "";
        var cd = document.getElementById('detalleNumId').value,
        cedulaFormato = /^[1-9]-?\d{4}-?\d{4}$/, expNumerica = /^[0-1000000]$/;
        var tipoBusqueda = document.getElementById('tipoBusquedaCuenta');
        var detMonto=document.getElementById('detalleMontoDep').value;
        var detNombre=document.getElementById('detalleNombreDep').value;
        
        

        // Valida que el campo de nombre de detalle depositante vaya con un nombre ingresado
        // y no sea vacio
        if (document.getElementById('detalleNombreDep').value == "") {
            error = "\n El campo detalle nombre depositante está en blanco";
            datosCorrectos = false;
        }
        
        // Valida si el campo de identificacion va vacio
        else if (document.getElementById('detalleNumId').value == "") {
            error = "\n El campo de numero de identificacion de depositante está en blanco";
            datosCorrectos = false;
            
        }
        // Valida si el formato ingresado en la cedula es correcto
        else if (!cedulaFormato.test(cd)) {
            error = "\n El numero de cédula: " + cd + " es incorrecto!\nPor favor intente el formato\n\
            1-0000-0000";
            datosCorrectos = false;
        }
        
        // Valida que el detalle del nombre tenga al menos 5 caracteres ingresados
        else if (detNombre.length < 5 ){
            error = "\n El campo del nombre no puede ser menor a 5 letras! ";
            datosCorrectos = false;
        }
       
        // Valida que el campo ingresado en monto sea una expresion numerica
        else if (isNaN(detMonto) ) {
            error = "\n El valor ingresado en el campo monto de deposito no es una experesion numerica! ";
            datosCorrectos = false;
        }
        
        
        // Falso si el monto digitado es menor o igual a 0 se  valida su error
        else if (detMonto <= 0){
            error = "\n El campo monto de deposito no puede ser menor o igual a 0";
            datosCorrectos = false;
        }
        
         // Valida que el monto ingresado para deposito sea una expresion numerica
        else if (detMonto.length >= 7 ){
            error = "\n La cifra excede el limite permitido para transferencia del sistema!\n\
             Por favor intente un valor menor a un millon!   ";
            datosCorrectos = false;
        }

// Si existen datos incorrecto entonces se realiza un alert con el error ocurrido al usuario
if (!datosCorrectos) {
alert('ERROR: ' + error);
        }
return datosCorrectos;
}









//----------------- FIN VALIDACION DEPOSITOS BANCARIOS -----------------------------


