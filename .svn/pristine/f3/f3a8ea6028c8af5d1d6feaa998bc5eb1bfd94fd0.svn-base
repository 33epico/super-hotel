package com.vilu.springboot.web.app.controller;

import java.util.Collection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Usuario;
import com.vilu.springboot.web.app.paginator.PageRender;
import com.vilu.springboot.web.app.service.IPisoService;

@Controller
@SessionAttributes("piso")
@RequestMapping("/privada")
public class PisoController {
	
	/*
	 *  Con usuarioSesion() obtenemos el usuario de la sesion
	 * 
	 */
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IPisoService pisoService;
	
	
	@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
	@RequestMapping(value = {"/listar"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page,
						Model model,
						Authentication authentication,
						HttpServletRequest request) {
		
		if(authentication != null) {
			logger.info("Usuario Logado: '"+authentication.getName()+"' ");
		}
	
		Pageable pageRequest = PageRequest.of(page, 6);
		Page<Piso> pisos = pisoService.findByUsuario(usuarioSesion(), pageRequest);
		PageRender<Piso> pageRender = new PageRender<Piso>("privada/listar", pisos);
		
		model.addAttribute("titulo", "Todos mis inmuebles");
		model.addAttribute("pisos", pisos);
		model.addAttribute("page", pageRender);
		return "privada/listar";
	}

	@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
	@RequestMapping(value = "/listar/{tipo}", method = RequestMethod.GET)
	public String listarTipo(
			@PathVariable String tipo, 
			@RequestParam(name = "page", defaultValue = "0") int page,
			Model model) {

		Pageable pageRequest = PageRequest.of(page, 6);
		Page<Piso> pisos = null;
		
		if (tipo.equals("activo")) {
			pisos = pisoService.findByUsuarioAndActivo (usuarioSesion(),false,pageRequest);
			model.addAttribute("titulo", "Cartera de pisos ACTIVOS");
		} else if (tipo.equals("inactivo")) {
			pisos = pisoService.findByUsuarioAndActivo (usuarioSesion(),true,pageRequest);
			model.addAttribute("titulo", "Cartera de pisos INACTIVOS");
		}

		PageRender<Piso> pageRender = new PageRender<Piso>("privada/listar", pisos);

		model.addAttribute("pisos", pisos);
		model.addAttribute("page", pageRender);
		return "/privada/listar";
	}

	/**
	 * Este metodo nos lleva al formulario de creacion de un piso
	 * Se encuentra en la parte privada de la aplicacion y solo sepuede acceder
	 * como gestor o como admin
	 * 
	 * 
	 */
	@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {
		
		Piso piso = new Piso();
		model.put("piso", piso);
		model.put("titulo", "Crear de piso");
		return "privada/form";
	}

	/**
	 * Este método recibe los parametros de la vista form para la creación de un
	 * nuevo piso.
	 * Si existen errores en el formulario se redirige a la pagina del formulario.
	 */
	@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Piso piso, 
			BindingResult result, 
			Model model, 
			RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario Crear Nuevo Piso");
			flash.addFlashAttribute("error", "Hay errores en el formulario");
			return "privada/form";
		}
		
		String mensajeFlashString = (piso.getId() != null) ? "Piso editado con exito" : "Nuevo piso guardado con exito";

		piso.setUsuario(usuarioSesion());
		pisoService.save(piso);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlashString);

		return "redirect:../privada/listar";
	}
	
	
	/**
	 * Editamos el piso con id recibido por parametros
	 * 
	 */
	@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
		@RequestMapping(value = "/form/{id}")
		public String editar(@PathVariable(value = "id") Long id, 
				RedirectAttributes flash,
				Map<String, Object> model) {
			
			// Creamos un objeto vacio
			Piso piso = null;
			
			if (id > 0) {
				
				// localizamos el objeto dentro de la base de datos a través de la interface
				piso = pisoService.findOne(id);
				
				if (piso == null) {
					flash.addFlashAttribute("error", "El id del piso no existe");
					return "redirect:privada/listar";
				}

			} else {
				// si no existe el objeto nos vamos a la pagina listar
				flash.addFlashAttribute("error", "El piso no se guardó");
				return "redirect:privada/listar";
			}
			
			if (piso.getUsuario().getUsername().equals(usuarioSesion().getUsername())) {

			// Como vamos a mandar los datos del cliente al formulario enviamos el objeto
			// cliente al parametro "cliente"
			model.put("piso", piso);
			model.put("titulo", "Guardar piso");
			
			}else {
				return "redirect:error_403";
			}

			return "/privada/form";
		}

	/**
	 * 
	 * ver/{id} Con esto vemos el objeto con el id que le hemos pasado Le pasamos el
	 * valor id, El objeto Model mapeado, la interface Map<String, Object> es
	 * basicamente un diccionario que almacena "nombre, Valor objeto" Los objetos
	 * para los mensajes flash
	 * 
	 */
	@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		// Localizamos el objeto pasado en la url
		Piso piso = pisoService.findOne(id);

		
		// Conbtrolamos si no existiese ese objeto
		if (piso == null) {
			flash.addFlashAttribute("error", "El piso que busca no esta registrado");
			return "redirect:privada/listar";
		}

		// Añadimos el titulo al modelo (web)
		model.put("titulo", "Detalle Piso:" + piso.getNombre());
		
		
		// Pasamos el cliente a la web donde podremos mostrar sus valores en detalle
		model.put("piso", piso);

		return "ver";
	}
	
		@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
		@RequestMapping(value = "/eliminar/{id}")
		public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

			if (id > 0) {
				Piso piso = pisoService.findOne(id);
				pisoService.delete(id);
				flash.addFlashAttribute("error", "Se ha eliminado el piso");
				return "redirect:../privada/listar";

			}
			flash.addFlashAttribute("error", "El id no puede ser negativo");
			return "redirect:../privada/listar";
		
		}
		

		
		@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
		@RequestMapping(value = {"/reservas"}, method = RequestMethod.GET)
		public String estadoReservas(
				Model model,
				Authentication authentication,
				HttpServletRequest request) {
			
			if(authentication != null) {
				logger.info("Usuario Logado: '"+authentication.getName()+"' ");
			}
		
			
			return "privada/estado_reservas";
		}
		
		//obtenemos el usuario de manera estatica
		private Usuario usuarioSesion () {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = pisoService.findUsuarioByUsername(auth.getName()); 
			return usuario;
		}
		
		private boolean hasRole(String role) {
			
			SecurityContext context = SecurityContextHolder.getContext();
			
			if (context==null) {
				return false;
			}
			
			Authentication auth = context.getAuthentication();
			
			if (auth==null) {
				return false;
			}
			
			Collection<? extends GrantedAuthority>  authorities = auth.getAuthorities();
			
			return authorities.contains(new SimpleGrantedAuthority(role));
	
		}
}
