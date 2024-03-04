package com.vilu.springboot.web.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.vilu.springboot.web.app.entity.UsrPerfil;
import com.vilu.springboot.web.app.entity.Usuario;

@Service
public class EnvioMailServiceNoVale {
	
	@Autowired
	private IPisoService pisoService;
	
	@Autowired
	private IConfiguracionService configuracionService;
	

	public void envioMail(String mail, String cabecera, String mensaje,String destinatario) {

		final String username = configuracionService.findValorByClave("emailReservas");
		final String password = configuracionService.findValorByClave("emailReservaspw");

		Properties prop = new Properties();
		prop.put("mail.smtp.host", configuracionService.findValorByClave("host"));
		prop.put("mail.smtp.port", configuracionService.findValorByClave("port"));
		prop.put("mail.smtp.auth", configuracionService.findValorByClave("auth"));
		prop.put("mail.smtp.starttls.enable", configuracionService.findValorByClave("starttls")); // TLS
		prop.put("spring.mail.properties.mail.smtp.starttls.enable", "true");
		prop.put("spring.mail.properties.mail.smtp.starttls.required", "true");
		prop.put("spring.mail.properties.mail.smtp.connectiontimeout", "5000");
		prop.put("spring.mail.properties.mail.smtp.timeout", "5000");
		prop.put("spring.mail.properties.mail.smtp.writetimeout", "5000");
		prop.put("spring.mail.password","eqhiyjbypyxjvltq");
		
		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
			message.setSubject(cabecera);
			message.setContent(mensaje, "text/html; charset=utf-8");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
