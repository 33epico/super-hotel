/*"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqld" --defaults-file="C:\ProgramData\MySQL\MySQL Server 8.0\my.ini"*/
/*Pisos*/
INSERT INTO `db_pisos`.`pisos` (`id`, `calle`, `create_at`, `detalle`, `habitaciones`, `nombre`, `numero`, `piso_altura`, `poblacion`,`activo`,`usuario_id`,`tarifa_base`,`personas`,`coordenadaX`,`coordenadaY`,`detalleEN`,`servicio_limpieza`) VALUES ('1', 'Ricardo Ortiz', '2019-08-29', 'Bonito piso a dos minutos del centro de la ciudad con maravillosas vistas de la catedral', '4', 'Casa Augusto', '98', '4', 'Madrid',0,2,100,'2','40.0354508','-6.0901300','detalle en ingles','20');
INSERT INTO `db_pisos`.`pisos` (`id`, `calle`, `create_at`, `detalle`, `habitaciones`, `nombre`, `numero`, `piso_altura`, `poblacion`,`activo`,`usuario_id`,`tarifa_base`,`personas`,`coordenadaX`,`coordenadaY`,`detalleEN`,`servicio_limpieza`) VALUES ('2', 'Alcalá 34', '2019-08-29', 'Espacioso hoar piso a dos minutos del centro de la ciudad con maravillosas vistas de la catedral', '5', 'Casa Julio', '100', '4', 'Madrid',1,1,150,'4','40.0354505','-6.0902350','detalle en ingles','20');
INSERT INTO `db_pisos`.`pisos` (`id`, `calle`, `create_at`, `detalle`, `habitaciones`, `nombre`, `numero`, `piso_altura`, `poblacion`,`activo`,`usuario_id`,`tarifa_base`,`personas`,`coordenadaX`,`coordenadaY`,`detalleEN`,`servicio_limpieza`) VALUES ('3', 'Ricardo Ortiz', '2019-08-29', 'Bonito piso a dos minutos del centro de la ciudad con maravillosas vistas de la catedral', '4', 'Casa Adriana', '98', '4', 'Madrid',1,1,120,'8','40.0354500','-6.0903400','detalle en ingles','20');
INSERT INTO `db_pisos`.`pisos` (`id`, `calle`, `create_at`, `detalle`, `habitaciones`, `nombre`, `numero`, `piso_altura`, `poblacion`,`activo`,`usuario_id`,`tarifa_base`,`personas`,`coordenadaX`,`coordenadaY`,`detalleEN`,`servicio_limpieza`) VALUES ('4', 'Odonell', '2019-08-29', 'Espacioso hoar piso a dos minutos del centro de la ciudad con maravillosas vistas de la catedral', '5', 'Casa Aurelia', '100', '4', 'Madrid',0,1,85,'6','40.0354509','-6.0904450','detalle en ingles','20');



/*usuarios y roles pass:sasasa*/
INSERT INTO users ( username,password,enabled) VALUES ( 'test@gmail.com', '$2a$10$htA8.yJBxx8yM2NL4DWa8O6Oz8iBnak9M3P0GdGkTnfkFxsuCVP.K', 1);
INSERT INTO users ( username,password,enabled) VALUES ( 'admin', '$2a$10$htA8.yJBxx8yM2NL4DWa8O6Oz8iBnak9M3P0GdGkTnfkFxsuCVP.K', 1);
INSERT INTO users ( username,password,enabled) VALUES ( 'admin2', '$2a$10$htA8.yJBxx8yM2NL4DWa8O6Oz8iBnak9M3P0GdGkTnfkFxsuCVP.K', 1);

INSERT INTO authority ( user_id, authority) VALUES ( 1,'ROLE_GESTOR');
INSERT INTO authority ( user_id, authority) VALUES ( 2,'ROLE_ADMIN');
INSERT INTO authority ( user_id, authority) VALUES ( 3,'ROLE_USUARIO');


INSERT INTO `db_pisos`.`perfil_usuario` (`id`,`apellidos`,`create_at`,`direccion`,`fecha_nacimiento`,`nombre`,`telefono`,`usuario_id`) VALUES ('1','adminApe','2019-08-29','direccion','2019-01-01','nombreAdmin','83756345','2');
INSERT INTO `db_pisos`.`perfil_usuario` (`id`,`apellidos`,`create_at`,`direccion`,`fecha_nacimiento`,`nombre`,`telefono`,`usuario_id`) VALUES ('2','eduApe','2019-08-29','direccion','2019-01-01','edunombre','3244234','1');

/*Configuracion email*/
INSERT INTO configuracion (clave, valor,grupo) VALUES ('host', 'smtp.gmail.com','email'); 
INSERT INTO configuracion (clave, valor,grupo) VALUES ('port', '587','email'); 
INSERT INTO configuracion (clave, valor,grupo) VALUES ('auth', 'true','email'); 
INSERT INTO configuracion (clave, valor,grupo) VALUES ('starttls', 'true','email');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('emailReservas', 'test@gmail.com','email');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('emailReservaspw', 'password','email');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('emailmailing', 'test@gmail.com','email');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('emailmailingpw', 'pass','email');

/*personalizacion*/
INSERT INTO configuracion (clave, valor,grupo) VALUES ('nombreNegocio', 'Villa Lusitania','personaliza'); 
INSERT INTO configuracion (clave, valor,grupo) VALUES ('mensajeBienvenidaES', 'Este mail se ha enviado a test@gmail.com por un sistema automático, por favor no respondas a este email directamente. Para contestar a este mail hagalo a traves de la pagnia de contacto ','personaliza'); 
INSERT INTO configuracion (clave, valor,grupo) VALUES ('fotoFondo', 'foro.jpg','personaliza');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('colorFondo', 'bg-dark','personaliza');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('colorTexto', 'text-white','personaliza'); 
INSERT INTO configuracion (clave, valor,grupo) VALUES ('logotipo', 'foro.jpg','personaliza');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('botonPrimario', 'bg-secondary','personaliza');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('botonSecundario', 'bg-primary','personaliza');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('horain', '13:00:00','personaliza'); 
INSERT INTO configuracion (clave, valor,grupo) VALUES ('horaout', '12:00:00','personaliza'); 
INSERT INTO configuracion (clave, valor,grupo) VALUES ('cancelacion', 'texto legar de cancelación ','normas');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('cancelacionEN', 'texto legar de cancelación EN','normas');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('cancelacionFR', 'texto legar de cancelación FR','normas');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('cancelacionDE', 'texto legar de cancelación DE','normas');

INSERT INTO configuracion (clave, valor,grupo) VALUES ('condicionesServicio', 'texto de condiciones de servicio','normas');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('condicionesServicioEN', 'texto de condiciones de servicio EN','normas');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('condicionesServicioFR', 'texto de condiciones de servicio FR','normas');
INSERT INTO configuracion (clave, valor,grupo) VALUES ('condicionesServicioDE', 'texto de condiciones de servicio DE','normas');

/* impuestos*/
INSERT INTO configuracion (clave, valor,grupo) VALUES ('iva', '21','impuestos');

/*reserva activa ahora*/
--INSERT INTO `db_pisos`.`reservas` (`id`,`activa`,`codigo_reserva`,`consolidada`,`descuento`,`descuento_calculado`,`fecha_fin`,`fecha_inicio`,`precio_calculado`,`piso_id`,`usuario_id`) VALUES('11', 1, '20200734 EDUC45569AA', '1', '10','5', '2024-12-20 12:00:00', '2020-12-20 12:00:00', '395', '4', '1');
--INSERT INTO `db_pisos`.`reservas` (`id`,`activa`,`codigo_reserva`,`consolidada`,`descuento`,`descuento_calculado`,`fecha_fin`,`fecha_inicio`,`precio_calculado`,`piso_id`,`usuario_id`) VALUES('50', 1, '20200734 EDUC45569HH', '1', '10','5', '2024-12-26 12:00:00', '2020-12-21 12:00:00', '395', '4', '1');


/*reserva cancelada */
--INSERT INTO `db_pisos`.`reservas` (`id`,`activa`,`codigo_reserva`,`consolidada`,`descuento`,`fecha_fin`,`fecha_inicio`,`precio_calculado`,`piso_id`,`usuario_id`) VALUES('12', 0, '20200734 EDUC45569BB', '3', '10', '2020-12-28 12:00:00', '2020-12-26 12:00:00', '275', '4', '1');

/*proxima activa reserva */
--INSERT INTO `db_pisos`.`reservas` (`id`,`activa`,`codigo_reserva`,`consolidada`,`descuento`,`fecha_fin`,`fecha_inicio`,`precio_calculado`,`piso_id`,`usuario_id`) VALUES('10', 1, '20200734 EDUC45569CC', '1', '10', '2020-11-28 12:00:00', '2020-11-26 12:00:00', '185', '4', '1');

/*Reserva pasada historico*/
--INSERT INTO `db_pisos`.`reservas` (`id`,`activa`,`codigo_reserva`,`consolidada`,`descuento`,`fecha_fin`,`fecha_inicio`,`precio_calculado`,`piso_id`,`usuario_id`) VALUES('13', 0, '20200734 EDUC45569DD', '2', '10', '2020-06-29 12:00:00', '2020-06-27 12:00:00', '180', '4', '1');

/*No pagada y pasada*/
--INSERT INTO `db_pisos`.`reservas` (`id`,`activa`,`codigo_reserva`,`consolidada`,`descuento`,`fecha_fin`,`fecha_inicio`,`precio_calculado`,`piso_id`,`usuario_id`) VALUES('14', 0, '20200734 EDUC45569ZZ', '0', '10', '2020-06-29 12:00:00', '2020-06-27 12:00:00', '100', '4', '1');

/*No pagada y futura*/
--INSERT INTO `db_pisos`.`reservas` (`id`,`activa`,`codigo_reserva`,`consolidada`,`descuento`,`fecha_fin`,`fecha_inicio`,`precio_calculado`,`piso_id`,`usuario_id`) VALUES('15', 1, '20200734 EDUC45569ZZ', '0', '10', '2021-12-29 12:00:00', '2021-12-27 12:00:00', '100', '4', '1');



/*Promos*/
INSERT INTO `db_pisos`.`promociones` (`id`,`activo`,`bases_legales`,`bases_legalesde`,`bases_legalesen`,`bases_legalesfr`,`codigo_promocion`,`fecha_fin`,`fecha_inicio`,`numero_de_usos`,`texto_promocion`,`texto_promocionde`,`texto_promocionen`,`texto_promocionfr`,`valor_descuento`) VALUES ('1', true, 'base legal', 'base legal DE', 'base legal EN', 'base legal FR', 'HOLA10', '2021-11-01 00:00:00', '2020-10-20 00:00:00', '2', 'Texto promocion', 'Texto promocion DE', 'Texto promocion EN', 'Texto promocion FR', '20');



