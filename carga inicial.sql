
USE `eif209_2001_p01b` ;
INSERT INTO `eif209_2001_p01b`.`moneda`
	(`nombre`, `descripcion`, `simbolo`, `tipo_cambio_compra`, `tipo_cambio_venta`)
	VALUES
		('CRC', 'Colón', '₡', '1.0', '1.0'),
		('USD', 'Dólar EEUU', '$', '565.64', '573.56'),
		('EUR', 'Euro', '€', '610.65', '640.67')
	;
    
-- SELECT * FROM  `eif209_2001_p01b`.`moneda`;

insert into eif209_2001_p01b.tipo_cuenta
(id_tipo_cuenta,descripción,tasa_interés)
values
(0,'Cuenta de Ahorros',5.76),
(1,'Cuenta de Corrientes',10.76)
;

-- Insertamos un Cliente y un Cajero
insert into eif209_2001_p01b.usuario
(id_usuario,clave_acceso,clave_vencida,rol)
values
(1,'11111Av$',0,1),
(2,'11111Av$',0,0)
;


insert into eif209_2001_p01b.cliente
(id_cliente,usuario_id_usuario,apellidos,nombre,telefono)
values
('111111111',1,'Root Root','Cajero',   '19191919'),
('111111110',2,'Manson Balboa','Rocky-Cliente','666666666')
;

insert into eif209_2001_p01b.cuenta
(num_cuenta,tipo_cuenta_id_tipo_cuenta,cliente_id_cliente,moneda_nombre)
values
(1,0,'111111110','CRC')
;

insert into eif209_2001_p01b.favorita 
(cliente_id_cliente,cuenta_num_cuenta)
select eif209_2001_p01b.cuenta.cliente_id_cliente,eif209_2001_p01b.cuenta.num_cuenta
 from eif209_2001_p01b.cuenta
;

update eif209_2001_p01b.cuenta set saldo_actual=10000 where cliente_id_cliente='111111110';

insert into eif209_2001_p01b.movimiento
(cuenta_num_cuenta,monto,aplicado)
values
(1,10000,1)
;
