package com.vilu.springboot.web.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:./messages_es.properties")
public class MessageProperties {

	@Value("${error.reserva.nologing}")
	public  String errnologing;
	
	@Value("${error.reserva.nopisos}")
	public  String errnopisos;
	
	@Value("${error.reserva.generico}")
	public  String errgenerico;
	
	@Value("${error.reserva.promo}")
	public  String errpromo;

	@Value("${error.reserva.novalida}")
	public  String errresernovalida;
	
	@Value("${error.reserva.fechas}")
	public  String errfechas;
	
	@Value("${peticion.reserva.cancelacion}")
	public  String peticionCancelacion;
	
	@Value("${peticion.reserva.mensaje.cancelacion}")
	public  String mensajeCancelacion;
	
	@Value("${text.listado.nopisos}")
	public  String nopisos;
	
	@Value("${text.mensajes.notificacion}")
	public  String notificacion;
	
	@Value("${text.piso.error}")
	public  String error;
	
	@Value("${error.usuario.repetido}")
	public String errUsuRepetido;

	public String getErrnologing() {
		return errnologing;
	}

	public String getErrnopisos() {
		return errnopisos;
	}

	public String getErrgenerico() {
		return errgenerico;
	}

	public String getErrpromo() {
		return errpromo;
	}

	public String getErrresernovalida() {
		return errresernovalida;
	}

	public String getErrfechas() {
		return errfechas;
	}

	public String getPeticionCancelacion() {
		return peticionCancelacion;
	}

	public String getMensajeCancelacion() {
		return mensajeCancelacion;
	}

	public String getNotificacion() {
		return notificacion;
	}

	public String getNopisos() {
		return nopisos;
	}

	public String getError() {
		return error;
	}

	public String getErrUsuRepetido() {
		return errUsuRepetido;
	}
	
	
	
	
}



