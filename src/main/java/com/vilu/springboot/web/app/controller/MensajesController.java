package com.vilu.springboot.web.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.vilu.springboot.web.app.entity.Mensajes;
import com.vilu.springboot.web.app.entity.Usuario;
import com.vilu.springboot.web.app.paginator.PageRender;
import com.vilu.springboot.web.app.service.IMensajesService;
import com.vilu.springboot.web.app.service.IUsuarioService;
import com.vilu.springboot.web.app.service.impl.LocaleService;


@Controller
@RequestMapping("/mensajes")
public class MensajesController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IMensajesService mensajeService;
	
	@Autowired
	private LocaleService localeService;

	@RequestMapping(value = {"/noLeidos","/leidos",}, method = RequestMethod.GET)
	public String noLeidos(@RequestParam(name = "page", defaultValue = "0") int page,
						   @RequestParam(name = "tipoMSG", defaultValue = "0") Long tipoMSG,
						   @RequestParam(name = "idMsgNoLeido", required= false) Long idMsgNoLeido,
						   @RequestParam(name = "idMsgLeido", required= false) Long idMsgLeido,
						  Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 6);
		Usuario usuario = usuarioSesion ();
		Page<Mensajes> mensajes = null;
		PageRender<Mensajes> pageRender = null;
		String plantilla = null;
		
		//si queremos actualizar leido/no leido
		if (idMsgNoLeido != null) {
			Mensajes mensajeTextoMSG = mensajeService.findOne(idMsgNoLeido);
			mensajeTextoMSG.setLeido(true);
			mensajeService.save(mensajeTextoMSG);
		}else if (idMsgLeido != null) {
			Mensajes mensajeTextoMSG = mensajeService.findOne(idMsgLeido);
			mensajeTextoMSG.setLeido(false);
			mensajeService.save(mensajeTextoMSG);
		}
		//= no leidos 1 leidos
		if(tipoMSG==Mensajes.NO_LEIDO) {
			mensajes = mensajeService.findByNombreUsuarioAndLeido(usuario.getUsername(), false, pageRequest);	
			pageRender = new PageRender<Mensajes>("/mensajes/noLeidos", mensajes);
			plantilla = "mensajes/noLeidos";
		}else if (tipoMSG==Mensajes.LEIDO){
			mensajes = mensajeService.findByNombreUsuarioAndLeido(usuario.getUsername(), true, pageRequest);	
			pageRender = new PageRender<Mensajes>("/mensajes/leidos", mensajes);
			plantilla = "mensajes/leidos";
		}
		
		model.addAttribute("mensajes", mensajes);
		model.addAttribute("page", pageRender);
		model.addAttribute("mensajeTextoMSG", new Mensajes());
		
		
		return plantilla;
	}

	
	/*MODULO AJAX MENSAJES   localhost:8080/mensajes/ajax/detalleMensaje?id=1*/
	@RequestMapping("ajax/detalleMensaje")
	public String detalleMensaje(
			@RequestParam(name = "id", required = true) Long id,
			Model model) {
		
		Mensajes mensajeTextoMSG = mensajeService.findOne(id);
		mensajeTextoMSG.setLeido(true);
		mensajeService.save(mensajeTextoMSG);
		model.addAttribute("mensajeTextoMSG", mensajeTextoMSG);
		return "mensajes/noLeidos :: #mensajeTexto";
	}
	
	/*MODULO AJAX MENSAJES   localhost:8080/mensajes/ajax/detalleMensaje?id=1*/
	@RequestMapping("ajax/notificacionMensaje")
	public String actualizaNotificaciones(
			Model model) {
		
		String locale = LocaleContextHolder.getLocale().toString().substring(3);
		Usuario usuario = usuarioSesion ();
		String notificacionMSG = "1";
		List <Mensajes> mensaje= mensajeService.findByNombreUsuarioAndLeido(usuario.getUsername(), false);	
		
		if (mensaje.isEmpty()) {
			notificacionMSG = localeService.textoLocale("notificacion", locale);
		}else {
			notificacionMSG = localeService.textoLocale("notificacion", locale)+"("+mensaje.size()+")";
		}
		model.addAttribute("notificacionMSG", notificacionMSG);
		return "layout/public_layout :: #notificacionMSG";
	}
	
	//obtenemos el usuario de manera estatica
	private Usuario usuarioSesion () {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.findUsuarioByUsername(auth.getName()); 
		return usuario;
	}
}
