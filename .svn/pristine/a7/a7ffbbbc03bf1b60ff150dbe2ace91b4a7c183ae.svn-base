package com.vilu.springboot.web.app.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.vilu.springboot.web.app.entity.Configuracion;
import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Promociones;
import com.vilu.springboot.web.app.paginator.PageRender;
import com.vilu.springboot.web.app.service.IConfiguracionService;
import com.vilu.springboot.web.app.service.IPisoService;
import com.vilu.springboot.web.app.service.IUtilUsrService;


@Controller
@RequestMapping("/administracion")
public class AdministracionController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	
	@Autowired
	private IUtilUsrService utilUsrService;
	
	@Autowired
	private IConfiguracionService configuracionService;
	
	@Autowired
	private IPisoService pisoService;
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = {"/configuracion"})
	public String configuracion(
			Map<String, Object> model,
			Authentication authentication,
			HttpServletRequest request) {
		 
		String host = "";
		String port = "";
		String auth = "";
		String starttls = "";
		String emailReservas = "";
		String emailReservaspw = "";
		String emailmailing = "";
		String emailmailingpw = "";
		
		
		List<Configuracion> configuraciones =  configuracionService.findByGrupo("email");
		
		if(configuraciones!=null && !configuraciones.isEmpty()) {
			for(Configuracion configuracion:configuraciones) {
				
				host = (configuracion.getClave().equals("host"))?configuracion.getValor():host;
				port = (configuracion.getClave().equals("port"))?configuracion.getValor():port;
				auth = (configuracion.getClave().equals("auth"))?configuracion.getValor():auth;
				starttls = (configuracion.getClave().equals("starttls"))?configuracion.getValor():starttls;
				emailReservas = (configuracion.getClave().equals("emailReservas"))?configuracion.getValor():emailReservas;
				emailReservaspw = (configuracion.getClave().equals("emailReservaspw"))?configuracion.getValor():emailReservaspw;
				emailmailing = (configuracion.getClave().equals("emailmailing"))?configuracion.getValor():emailmailing;
				emailmailingpw = (configuracion.getClave().equals("emailmailingpw"))?configuracion.getValor():emailmailingpw;
			}
		}
		model.put("host", host);
		model.put("port", port);
		model.put("auth", auth);
		model.put("starttls", starttls);
		model.put("emailReservas", emailReservas);
		model.put("emailReservaspw", emailReservaspw);
		model.put("emailmailing", emailmailing);
		model.put("emailmailingpw", emailmailingpw);
		
	
		return "administracion/configuracion";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/guardarconfiguracionmail", method = RequestMethod.GET)
	public String guardar(
			@RequestParam(name = "host") String host,
			@RequestParam(name = "port") String port,
			@RequestParam(name = "auth") String auth,
			@RequestParam(name = "starttls") String starttls,
			@RequestParam(name = "emailReservas") String emailReservas,
			@RequestParam(name = "emailReservaspw") String emailReservaspw,
			@RequestParam(name = "emailmailing") String emailmailing,
			@RequestParam(name = "emailmailingpw") String emailmailingpw,
			Map<String, Object> model,
			RedirectAttributes flash,
			SessionStatus status) {

		List<Configuracion> configuraciones =  configuracionService.findByGrupo("email");
		
		
		if(configuraciones!=null && !configuraciones.isEmpty()) {
			for(Configuracion configuracion:configuraciones) {
				
				String clave = configuracion.getClave();
				
				if (clave.equals("host")){ configuracion.setValor(host);}
				if (clave.equals("port")){ configuracion.setValor(port);}
				if (clave.equals("auth")){ configuracion.setValor(auth);}
				if (clave.equals("starttls")){ configuracion.setValor(starttls);}
				if (clave.equals("emailReservas")){ configuracion.setValor(emailReservas);}
				if (clave.equals("emailReservaspw")){ configuracion.setValor(emailReservaspw);}
				if (clave.equals("emailmailing")){ configuracion.setValor(emailmailing);}
				if (clave.equals("emailmailingpw")){ configuracion.setValor(emailmailingpw);}
				
				configuracionService.save(configuracion);
			}
		}

		return "redirect:../administracion/configuracion";
	}
	
	/**
	 * Metodo para que un administrador asigne permisos a otros usuarios
	 * @param usuario
	 * @param permiso
	 * @return
	 */
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = {"/usuarios"}, method = RequestMethod.GET)
	public String permisosUsuarios(
			Model model,
			@RequestParam(name = "username") String username,
			@RequestParam(name = "cliente", defaultValue = "off") String cliente,
			@RequestParam(name = "gestor", defaultValue = "off") String gestor,
			@RequestParam(name = "servicio", defaultValue = "off") String servicio,
			@RequestParam(name = "administrador", defaultValue = "off") String administrador,
			RedirectAttributes flash
			){
		
		List<String> rolesArr = new ArrayList<String>();
		if(cliente.equals("on")) {rolesArr.add("ROLE_CLIENTE");}
		if(gestor.equals("on")) {rolesArr.add("ROLE_GESTOR");}
		if(servicio.equals("on")) {rolesArr.add("ROLE_SERVICIO");}
		if(administrador.equals("on")) {rolesArr.add("ROLE_ADMIN");}
		
		utilUsrService.actualizarRoles(rolesArr, username);
		
		flash.addFlashAttribute("mensaje", "Se ha actualizado el usuario "+username);
			
		return "redirect:../administrador";
	}
	

	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = {"/administrador"}, method = RequestMethod.GET)
	public String inicio(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "pagePromo", defaultValue = "0") int pagePromo,
			Model model,
			Authentication authentication,
			HttpServletRequest request) {
		
		List<Configuracion> aspecto = configuracionService.findByGrupo("personaliza");
		List<Configuracion> normas = configuracionService.findByGrupo("normas");
		
		
		//Configuración de aspecto
		for(Configuracion configuracion: aspecto) {
			model.addAttribute(configuracion.getClave(), configuracion.getValor());
		}
		
		//Configuracion de normas
		for(Configuracion configuracion: normas) {
			model.addAttribute(configuracion.getClave(), configuracion.getValor());
		}
		//Gestión de promociones
		Pageable pageRequest = PageRequest.of(pagePromo, 6, Sort.by(Sort.Direction.DESC, "fechaInicio"));
		Page<Promociones> promocionesLista =  pisoService.findAllPromociones(pageRequest);
		PageRender<Promociones> pageRender = new PageRender<Promociones>("/administrador", promocionesLista);
		model.addAttribute("promocionesLista", promocionesLista);
		model.addAttribute("page", pageRender);
				
		Promociones promociones = new Promociones();
		model.addAttribute("promociones", promociones);

		model.addAttribute("roles", utilUsrService.listaRolesUsr());
		return "administracion/administracion";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = {"/personalizacion"}, method = RequestMethod.GET)
	public String personalizacion(
			@RequestParam(name = "nombreNegocio",defaultValue = "0") String nombreNegocio,
			@RequestParam(name = "colorFondo",defaultValue = "0") String colorFondo,
			@RequestParam(name = "colorTexto",defaultValue = "0") String colorTexto,
			@RequestParam(name = "imagenPrincipal",defaultValue = "0") String imagenPrincipal,
			@RequestParam(name = "logotipo",defaultValue = "0") String logotipo,
			@RequestParam(name = "botonPrimario",defaultValue = "0") String botonPrimario,
			@RequestParam(name = "botonPrimarioBorde",defaultValue = "0") String botonPrimarioBorde,
			@RequestParam(name = "botonSecundario",defaultValue = "0") String botonSecundario,
			@RequestParam(name = "botonSecundarioBorde",defaultValue = "0") String botonSecundarioBorde,
			Model model,
			Authentication authentication,
			HttpServletRequest request,
			RedirectAttributes flash) {
	
		
		List<Configuracion> personaliza = configuracionService.findByGrupo("personaliza");
		for(Configuracion configuracion: personaliza ) {
			if(configuracion.getClave().equals("nombreNegocio") && !(nombreNegocio.equals("0"))) {
				configuracion.setClave("nombreNegocio");
				configuracion.setValor(nombreNegocio);
			}
			if(configuracion.getClave().equals("colorFondo")  && !(colorFondo.equals("0"))) {
				configuracion.setClave("colorFondo");
				configuracion.setValor(colorFondo);
			}
			if(configuracion.getClave().equals("colorTexto")  && !(colorTexto.equals("0"))) {
				configuracion.setClave("colorTexto");
				configuracion.setValor(colorTexto);
			}
			if(configuracion.getClave().equals("imagenPrincipal")  && !(imagenPrincipal.equals("0"))) {
				configuracion.setClave("imagenPrincipal");
				configuracion.setValor(imagenPrincipal);
			}
			if(configuracion.getClave().equals("imagenPrincipal")  && !(imagenPrincipal.equals("0"))) {
				configuracion.setClave("imagenPrincipal");
				configuracion.setValor(imagenPrincipal);
			}
			if(configuracion.getClave().equals("logotipo")  && !(logotipo.equals("0"))) {
				configuracion.setClave("logotipo");
				configuracion.setValor(logotipo);
			}
			if(configuracion.getClave().equals("botonPrimario")  && !(botonPrimario.equals("0"))) {
				configuracion.setClave("botonPrimario");
				configuracion.setValor(botonPrimario);
			}
			if(configuracion.getClave().equals("botonSecundario")  && !(botonSecundario.equals("0"))) {
				configuracion.setClave("botonSecundario");
				configuracion.setValor(botonSecundario);
			}
			configuracionService.save(configuracion);
		}
		
		flash.addFlashAttribute("mensaje", "Se ha actualizado la configuracion ");
		
		model.addAttribute("roles", utilUsrService.listaRolesUsr());
		return "redirect:../administrador";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = {"/normas"}, method = RequestMethod.GET)
	public String normas(
			@RequestParam(name = "cancelacion",defaultValue = "0") String cancelacion,
			@RequestParam(name = "cancelacionEN",defaultValue = "0") String cancelacionEN,
			@RequestParam(name = "cancelacionFR",defaultValue = "0") String cancelacionFR,
			@RequestParam(name = "cancelacionDE",defaultValue = "0") String cancelacionDE,
			@RequestParam(name = "condicionesServicio",defaultValue = "0") String condicionesServicio,
			@RequestParam(name = "condicionesServicioEN",defaultValue = "0") String condicionesServicioEN,
			@RequestParam(name = "condicionesServicioFR",defaultValue = "0") String condicionesServicioFR,
			@RequestParam(name = "condicionesServicioDE",defaultValue = "0") String condicionesServicioDE,
			Model model,
			Authentication authentication,
			HttpServletRequest request,
			RedirectAttributes flash
			) {
			
		List<Configuracion> normas = configuracionService.findByGrupo("normas");
		
		for(Configuracion configuracion: normas ) {
			if(configuracion.getClave().equals("cancelacion") && !(cancelacion.equals("0"))) {
				configuracion.setClave("cancelacion");
				configuracion.setValor(cancelacion);
			}
			if(configuracion.getClave().equals("cancelacionEN") && !(cancelacion.equals("0"))) {
				configuracion.setClave("cancelacionEN");
				configuracion.setValor(cancelacionEN);
			}
			if(configuracion.getClave().equals("cancelacionFR") && !(cancelacion.equals("0"))) {
				configuracion.setClave("cancelacionFR");
				configuracion.setValor(cancelacionFR);
			}
			if(configuracion.getClave().equals("cancelacionDE") && !(cancelacion.equals("0"))) {
				configuracion.setClave("cancelacionDE");
				configuracion.setValor(cancelacionDE);
			}
			if(configuracion.getClave().equals("condicionesServicio") && !(cancelacion.equals("0"))) {
				configuracion.setClave("condicionesServicio");
				configuracion.setValor(condicionesServicio);
			}
			if(configuracion.getClave().equals("condicionesServicioEN") && !(cancelacion.equals("0"))) {
				configuracion.setClave("condicionesServicioEN");
				configuracion.setValor(condicionesServicioEN);
			}
			if(configuracion.getClave().equals("condicionesServicioFR") && !(cancelacion.equals("0"))) {
				configuracion.setClave("condicionesServicioFR");
				configuracion.setValor(condicionesServicioFR);
			}
			if(configuracion.getClave().equals("condicionesServicioDE") && !(cancelacion.equals("0"))) {
				configuracion.setClave("condicionesServicioDE");
				configuracion.setValor(condicionesServicioDE);
			}
		
		configuracionService.save(configuracion);
		}
		
		flash.addFlashAttribute("mensaje", "Se ha actualizado la configuracion ");
		
		return "redirect:../administrador";
}

}
