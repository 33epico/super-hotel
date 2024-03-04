package com.vilu.springboot.web.app.controller;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Servicios;
import com.vilu.springboot.web.app.entity.Usuario;
import com.vilu.springboot.web.app.service.IPisoService;

@Controller
@RequestMapping("/galeria")
@SessionAttributes ("servicios") 
public class ServiciosController {
	
	@Autowired
	private IPisoService pisoService;
	
	@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
	@RequestMapping(value = "/servicios/{id}")
	public String editarServicios(@PathVariable(value = "id") Long id,RedirectAttributes flash, Map<String, Object> model) {
		Piso piso = null;
		Servicios servicios = new Servicios();
		
		if(id >0 ) {
			piso = pisoService.findOne(id);
			servicios.setPiso(piso);
			
			List<Servicios>  serviciosActivos= pisoService.findServiciosByPisoOrderByIdAsc(piso);
			
			model.put("titulo", "Servicios");
			model.put("piso", piso);
			model.put("servicios",servicios);
			model.put("serviciosActivos", serviciosActivos);

			return "galeria/formServicios";
			
		}else {
			flash.addFlashAttribute("error", "Error el id del piso no existe");
			return "redirect:/listar";
		}
		
	}
	
	@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
	@RequestMapping(value = "/guardarServicio", method = RequestMethod.POST)
	public String guardar(@Valid Servicios servicios,
							BindingResult result, Model model,
							RedirectAttributes flash, 
							SessionStatus status) {
		Piso piso = servicios.getPiso();
		
		if (piso.getUsuario().getUsername().equals(usuarioSesion().getUsername())) {
			if (result.hasErrors()) {
				flash.addFlashAttribute("error", "Ha habido un error al crear el servicio - todos los campos son necesarios");
				return"redirect:/galeria/servicios/"+piso.getId();
			}
			if (!(servicios == null)) {
				pisoService.saveServicios(servicios);
				return"redirect:/galeria/servicios/"+piso.getId();
			}
		}else {
			return "redirect:error_403";
		}
		
		return"redirect:/galeria/servicios/"+piso.getId();
	}
	
	@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
	@RequestMapping(value = "/eliminarServicio/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		
		if (id > 0) {
			Servicios servicios = pisoService.findOneServicios(id);
			Piso piso = servicios.getPiso();
			if (!(servicios==null) && !(piso==null)){
				pisoService.deleteServicios(id);
				return"redirect:/galeria/servicios/"+piso.getId();
			}else {
				flash.addFlashAttribute("error", "Ha habido un error con el piso o con el servicio a eleminiar");
				return "redirect:/listar";
			}
			
		}else{
			flash.addFlashAttribute("error", "El id a eliminar no existe");
			return "redirect:/listar";
		}
	}
	
	//obtenemos el usuario de manera estatica
	private Usuario usuarioSesion () {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = pisoService.findUsuarioByUsername(auth.getName()); 
		return usuario;
	}

}
