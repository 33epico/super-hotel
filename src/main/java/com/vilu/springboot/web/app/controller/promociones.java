package com.vilu.springboot.web.app.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.vilu.springboot.web.app.entity.Promociones;
import com.vilu.springboot.web.app.service.IPisoService;
import com.vilu.springboot.web.app.service.IUtilsService;
import com.vilu.springboot.web.app.utils.Utils;


@Controller
@RequestMapping("/promociones")
@SessionAttributes ("promociones") 
public class promociones {
	
	@Autowired
	private IPisoService pisoUtils;
	
	@Autowired
	private IUtilsService utilsService;
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/guardarPromo", method = RequestMethod.GET)
	public String guardarPromo(@Valid Promociones promociones, 
							   BindingResult result, 
							   Model model,
							   RedirectAttributes flash, 
							   SessionStatus status) {
		String validarFecha = "KO";
		
		if (promociones== null || result.hasErrors()){
			flash.addFlashAttribute("error", "Ha habido un error al crear la nueva promoción - revise los campos");
			return "redirect:/administracion/administrador";
		}else {
			validarFecha = utilsService.fechasValidas(promociones.getFechaInicio(), promociones.getFechaFin());
			if(validarFecha.equals("OK")){
				pisoUtils.savePromociones(promociones);	
				status.setComplete();
			}else {
				flash.addFlashAttribute("error", "Ha habido un error al crear la nueva promoción - "+validarFecha);
				return "redirect:/administracion/administrador";
			}
		}
		return "redirect:/administracion/administrador";
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/activarDesactivar/{id}", method = RequestMethod.GET)
	public String activarDesactivar(@PathVariable(value = "id") Long id,
							   		RedirectAttributes flash, 
							   		SessionStatus status) {
		Promociones promociones = null;
		if(id>0) {
			promociones= pisoUtils.findPromocionesById(id);
				if(promociones.equals(null)) {
					flash.addFlashAttribute("error", "Ha habido un error al editar la promoción - no existe la promoción");
					return "redirect:/administracion/administrador";
				}else {
				if(promociones.getActivo()) {
					promociones.setActivo(false);
				}else {
					promociones.setActivo(true);
				}
			pisoUtils.savePromociones(promociones);
			status.setComplete();
			}
		}else {
			flash.addFlashAttribute("error", "Ha habido un error al editar la promoción - el id no existe");
			return "redirect:/administracion/administrador";
		}
		return "redirect:/administracion/administrador";
	}
	

}
