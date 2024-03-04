package com.vilu.springboot.web.app.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vilu.springboot.web.app.entity.Peticiones;
import com.vilu.springboot.web.app.entity.Reserva;
import com.vilu.springboot.web.app.entity.Role;
import com.vilu.springboot.web.app.entity.UsrPerfil;
import com.vilu.springboot.web.app.entity.Usuario;
import com.vilu.springboot.web.app.service.EmailService;
import com.vilu.springboot.web.app.service.EnvioMailServiceNoVale;
import com.vilu.springboot.web.app.service.IConfiguracionService;
import com.vilu.springboot.web.app.service.IPisoService;
import com.vilu.springboot.web.app.service.IUsuarioService;
import com.vilu.springboot.web.app.service.impl.LocaleService;
import com.vilu.springboot.web.app.service.impl.PisoServiceImpl;
import com.vilu.springboot.web.app.utils.ReservaUtils;

@SuppressWarnings("unused")
@Controller
public class LoginController {

	@Autowired
	private IPisoService pisoService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService envioMailService;
	
	@Autowired
	private ReservaUtils reservaUtis;

	@Autowired
	private LocaleService localeService;
	
	@Autowired
	private IConfiguracionService configuracionService;
	
	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping("/borrar")
	public String borrar(Model model) {
		//inicializamos parametros de estilos
				model = configuracionService.configuracion(model);
				model = configuracionService.norma(model);

				model.addAttribute("keynopw", "localhost:8080/index");
				model.addAttribute("mensajeMail", "Este mail se ha enviado por un sistema automático, por favor no respondas a este email directamente. Para contestar a este mail hagalo a traves de la pagnia de contacto ");
		return "emails/altaCuenta";
	}
	
	
	@GetMapping("/login")
	public String login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, 
			Model model, 
			Principal principal,
			HttpServletRequest request,
			RedirectAttributes flash) {

		if (principal != null) {
			flash.addFlashAttribute("info", "Ya estabas logado");
			return "redirect:/index";	
		}
		if (error != null) {
			model.addAttribute("error", "Error en Login: nombre de usuario o contraseña incorrectos");
		}
		if (logout != null) {
			model.addAttribute("success", "Se ha cerrado la sesión");
			return "redirect:/index";
		}
		
		return "usuarios/login";
	}
	
	/**
	 * Metodo para la creación de usuarios y perfiles con los datos proporcionados por el metodo guardar
	 */
	@RequestMapping(value = "/registro")
	public String crear(Map<String, Object> model) {

		Usuario usuario = new Usuario();
		UsrPerfil usrPerfil = new UsrPerfil();
		
		model.put("usuario", usuario);
		model.put("usrPerfil", usrPerfil);
		model.put("titulo", "Creación de usuarios");
		
		return "usuarios/usr_form";
	}

	/**
	 *
	 */
	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	public String guardar  (@Valid Usuario usuario,
					        @Valid	UsrPerfil usrPerfil, 
							BindingResult result, 
							Model model, 
							RedirectAttributes flash,
							HttpServletRequest request,
							SessionStatus status) {
		
		String locale = LocaleContextHolder.getLocale().toString().substring(3);
		Session sessionMail = null;
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "El formulario de registro tiene errores");
			flash.addFlashAttribute("error", "Hay errores en el formulario");
			return "usuarios/usr_form";
		}
		
		if (pisoService.findUsuarioByUsername(usuario.getUsername())==null) {
			//Password para usuario
			String password = usuario.getPassword();
			usuario.setPassword(passwordEncoder.encode(password));
			pisoService.saveUsuario(usuario);
			usrPerfil.setUsuario(usuario);
			pisoService.saveUsrPerfil(usrPerfil);
			
			try {
				sessionMail = envioMailService.envioMailTemplate(usuario);
				envioMailService.plantillaUsrAlta(sessionMail, usuario.getUsername(),"/emails/altaCuenta", LocaleContextHolder.getLocale(), usuarioService.obtenerKey(), configuracionService.findByClave("mensajeBienvenidaES").getValor(), configuracionService.findByClave("nombreNegocio").getValor());		
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}else {
			flash.addFlashAttribute("info", localeService.textoLocale("errUsuRepetido", locale));
		}
		
		

		return "redirect:login";

	}
	
	@RequestMapping(value = "/perfil")
	public String ver(@RequestParam(name = "idReserva", required = false) String idReserva,
					Map<String, Object> model, RedirectAttributes flash) {
		
		UsrPerfil usrPerfil = pisoService.findByUsuario(usuarioSesion());
		Usuario usuario = usrPerfil.getUsuario();
		List<Role> role =  usuario.getRoles();
		List<Reserva> reservas = pisoService.findReservaByUsuario(usuarioSesion());
	

		
		//si se ha cancelado una reserva gestionamos la peticion 
		if(idReserva != null) {
			String locale = LocaleContextHolder.getLocale().toString().substring(3);
			reservaUtis.cancelarReserva(idReserva, locale);
			model.put("warning",localeService.textoLocale("errgenerico", locale));
		}
		
		//0 Creada y no pagada
		//1 Proxima (Creada y pagada)
		//2 Finalizada (historificada)
		//3 cancelada	
		TreeMap<String, Object> reservasCategorizadas = reservaUtis.listaReservas(reservas);
		
		model.put("usrPerfil", usrPerfil);
		model.put("usuario", usuario);
		model.put("roles", role);
		model.put("reservasActivas", reservasCategorizadas.get("reservasActivas"));
		model.put("reservasProximas", reservasCategorizadas.get("reservasProximas"));
		model.put("reservasHistorico",  reservasCategorizadas.get("reservasHistorico"));
		model.put("reservasCanceladas",  reservasCategorizadas.get("reservasCanceladas"));
		model.put("reservasSinPagar",  reservasCategorizadas.get("reservasSinPagar"));
		model.put("titulo", "Perfil de Usuario");

		return "usuarios/usr_profile";
	}
	
	@RequestMapping(value = ("/recuperarpw"), method = RequestMethod.GET)
	public String listar(
			@RequestParam(name = "username") String username,
			@RequestParam(name = "email") String email, 
			Model model, Authentication authentication,
			HttpServletRequest request) throws ParseException {
		
		UsrPerfil usrPerfil = null;
		Usuario usuario = pisoService.findUsuarioByUsername(username);
		String cabecera = null;
		String mensaje = null;
			
		if(usuario != null){
			 usrPerfil = usuario.getUsrPerfil();
		}
		
		/*
		if(usuario != null){
		     if((usrPerfil.getEmail()).equals(email)) {
		    	 model.addAttribute("test", "Existe el usuario y coincide el mail");
		    	 
		    	 String contrasena = (((UUID.randomUUID().toString()).toUpperCase()).substring(0, 20));
		    	 cabecera = "Recuperación contraseña vilu";
		    	 mensaje = ""
		    	 		+ "<!DOCTYPE html>\n" + 
		    	 		"<html>\n" + 
		    	 		"<body>\n" + 
		    	 		"<h5>A continuación le proporcionamos un link para recuperar su cuenta:</h5> \n" + 
		    	 		"<a href=\"http://localhost:8080/reestablecerpw/"+contrasena+"\" >Link</a>\n" + 
		    	 		"\n" + 
		    	 		"</body>\n" + 
		    	 		"</html>"
		    	 		+ "";
		    	 
		    	 usuario.setKeynopwd(contrasena);	 
		    	 usuario.setPassword(passwordEncoder.encode(contrasena));
		    	 pisoService.saveUsuario(usuario);
		
		    	 envioMailService.envioMail(usrPerfil.getEmail(), cabecera, mensaje,usrPerfil.getEmail());
		    	 
		     }else {
		    	 model.addAttribute("test", "Existe el usuario pero coincide el mail"); 
		     }
			
		}else{
			 model.addAttribute("test", "no existe el usuario");
		}*/
	
			return "publico/peticion_recuperapw";
		}
	

	
	@RequestMapping(value = ("/reestablecerpw/{key}"), method = RequestMethod.GET)
	public String reestablecerpw(@PathVariable(value = "key") String key, 
			Map<String, Object> model,
			HttpServletRequest request) throws ParseException {	
		
			if(!key.equals(null)&& !key.isEmpty()) {
				model.put("key", key);
			}
		
			return "publico/recuperarpw";
		}

	@RequestMapping(value = ("/reestablecerpw/actualizado"), method = RequestMethod.GET)
	public String guardarnuevopw(
			@RequestParam(value = "key") String key,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "password2") String password2,
			Model model,
			HttpServletRequest request) throws ParseException {	
		if(password==password2) {
	
			if(!key.equals(null)&& !key.isEmpty()) {
				Usuario usuario = pisoService.findByKeynopwd(key);
				
				if(usuario != null){
					usuario.setPassword(passwordEncoder.encode(password));
					usuario.setKeynopwd("null");
					pisoService.saveUsuario(usuario);
				}else {
					model.addAttribute("error", "Error algo ha fallado");
				}
			}else {
				model.addAttribute("error", "Error algo ha fallado");
			}
		}else {
			model.addAttribute("error", "Error algo ha fallado");
		}
		return "usuarios/login";
		}
	
	
	//obtenemos el usuario de manera estatica
	private Usuario usuarioSesion () {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = pisoService.findUsuarioByUsername(auth.getName()); 
		return usuario;
	}
	
	

}
