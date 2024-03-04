package com.vilu.springboot.web.app.service;

import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.vilu.springboot.web.app.entity.Recibos;
import com.vilu.springboot.web.app.entity.UsrPerfil;
import com.vilu.springboot.web.app.entity.Usuario;

@Service
public class EmailService {


	@Autowired
	private EnvioMailServiceNoVale envioMailService;
	
	@Autowired
	private IConfiguracionService configuracionService;

	@Autowired
	private IPisoService pisoService;
	
    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    public EmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    
    public Session envioMailTemplate(Usuario usuario) throws MessagingException {
    	
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
        return session;
	}
    
    //contruir el mensaje html
    public void plantillaRecibo(Session session, String para, String template, Recibos recibo,Locale locale ) {
    	
    	
		Context context = new Context();
		context.setVariable("recibo", recibo);
		context.setLocale(locale);
		String process = templateEngine.process(template, context);
        
		try {
        	Message mimeMessage = new MimeMessage(session);
        	MimeMessageHelper helper = new MimeMessageHelper((MimeMessage) mimeMessage);
	        helper.setFrom(new InternetAddress(para));
	        helper.setSubject("Recibo apartamento "+recibo.getCodigo() );
	        helper.setText(process, true);
	        helper.setTo(para);
	        Transport.send(mimeMessage);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //contruir el mensaje html
    public void plantillaUsrAlta(Session session, String para, String template, Locale locale, String keynopw, String mensaje,String titulo ) {
    	
    	
		Context context = new Context();
		context.setLocale(locale);
		context.setVariable("titulo", titulo);
		context.setVariable("keynopw","localhost:8080/reestablecerpw/"+ keynopw);
		context.setVariable("mensajeMail", keynopw);
		String process = templateEngine.process(template, context);
        
		try {
        	Message mimeMessage = new MimeMessage(session);
        	MimeMessageHelper helper = new MimeMessageHelper((MimeMessage) mimeMessage);
	        helper.setFrom(new InternetAddress(para));
	        helper.setSubject("Alta" );
	        helper.setText(process, true);
	        helper.setTo(para);
	        Transport.send(mimeMessage);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}