package com.vilu.springboot.web.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vilu.springboot.web.app.MessageProperties;
import com.vilu.springboot.web.app.MessagePropertiesEN;

@Service
public class LocaleService {
	
	@Autowired
	private MessageProperties messageProperties;
	
	@Autowired
	private MessagePropertiesEN messagePropertiesEN;
	
	public String textoLocale(String texto, String locale) {
		
		String textoReturn="";
		if (locale.equals("ES")) {
			
			if(texto.equals("errnologing")) {textoReturn=messageProperties.getErrnologing();}
			if(texto.equals("errnopisos")) {textoReturn=messageProperties.getErrnopisos();}
			if(texto.equals("errgenerico")) {textoReturn=messageProperties.getErrgenerico();}
			if(texto.equals("errpromo")) {textoReturn=messageProperties.getErrpromo();}
			if(texto.equals("errresernovalida")) {textoReturn=messageProperties.getErrresernovalida();}
			if(texto.equals("notificacion")) {textoReturn=messageProperties.getNotificacion();}
			if(texto.equals("mensajeCancelacion")) {textoReturn=messageProperties.getMensajeCancelacion();}
			if(texto.equals("peticionCancelacion")) {textoReturn=messageProperties.getPeticionCancelacion();}
			if(texto.equals("nopisos")) {textoReturn=messageProperties.getNopisos();}
			if(texto.equals("errUsuRepetido")) {textoReturn=messageProperties.getErrUsuRepetido();}
			
		}

		if (locale.equals("EN")) {
			
			if(texto.equals("errnologing")) {textoReturn=messagePropertiesEN.getErrnologing();}
			if(texto.equals("errnopisos")) {textoReturn=messagePropertiesEN.getErrnopisos();}
			if(texto.equals("errgenerico")) {textoReturn=messagePropertiesEN.getErrgenerico();}
			if(texto.equals("errpromo")) {textoReturn=messagePropertiesEN.getErrpromo();}
			if(texto.equals("errresernovalida")) {textoReturn=messagePropertiesEN.getErrresernovalida();}
			if(texto.equals("notificacion")) {textoReturn=messagePropertiesEN.getNotificacion();}
			if(texto.equals("mensajeCancelacion")) {textoReturn=messagePropertiesEN.getPeticionCancelacion();}
			if(texto.equals("peticionCancelacion")) {textoReturn=messagePropertiesEN.getPeticionCancelacion();}
			if(texto.equals("nopisos")) {textoReturn=messagePropertiesEN.getNopisos();}
			if(texto.equals("errUsuRepetido")) {textoReturn=messagePropertiesEN.getErrUsuRepetido();}
		}
		return textoReturn;
	}

}
