package com.vilu.springboot.web.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.vilu.springboot.web.app.entity.Foto;
import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.service.IPisoService;
import com.vilu.springboot.web.app.service.IUploadFileService;


@Controller
@RequestMapping("/galeria")
@SessionAttributes ("foto") 
public class FotoController {
	
	@Autowired
	private IPisoService pisoService;
	
	
	@Autowired
	private IUploadFileService uploadFileservice;
	
	private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	
	@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Map<String, Object> model) {

		// Creamos un objeto vacio
		Piso piso = null;
		Foto foto = new Foto();

		if (id > 0) {
			piso = pisoService.findOne(id);
			if (piso == null) {
				flash.addFlashAttribute("error", "El id del piso no existe");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "Error el id del piso no existe");
			return "redirect:/listar";
		}
		
		foto.setPiso(piso);
		model.put("piso", piso);
		model.put("foto", foto);
		model.put("titulo", "Guardar Imagen");

		return "galeria/form";
	}
	
	@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Foto foto, BindingResult result, Model model,
			@RequestParam("file") MultipartFile fotoImg, RedirectAttributes flash, SessionStatus status) {

		if (fotoImg.isEmpty()) {
			flash.addFlashAttribute("error", "No hay foto para subir");
			return"redirect:/listar";
		}	
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de galeria");
			flash.addFlashAttribute("error", "Hay errores en el formulario");
			return "galeria/form";
		}
		
		
		if (!fotoImg.isEmpty()) {

			if (foto.getId() != null && foto.getId() > 0 && foto.getFotoImg() != null
					&& foto.getFotoImg().length() > 0) {

				try {
					uploadFileservice.delete(foto.getFotoImg());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileservice.copy(fotoImg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Con esto ponemos un mensaje de info en el modelo
			flash.addFlashAttribute("info", "Ha subido Correctamente la foto '" + uniqueFilename + "'");

			foto.setFotoImg(uniqueFilename);
		}
		else {
			flash.addFlashAttribute("warning", "No hay foto para subir");
			return"redirect:/listar";
		}

	String mensajeFlashString = (foto.getId() != null) ? "Galeria editada con exito" : "Galeria guardada con exito";
	

	pisoService.saveFoto(foto);
	status.setComplete();
	flash.addFlashAttribute("success",mensajeFlashString);
	
	return"redirect:/galeria/form/"+foto.getPiso().getId();
	}
	
	@Secured({"ROLE_GESTOR","ROLE_ADMIN"})
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Foto foto = pisoService.findOneFoto(id);
			pisoService.deleteFoto(id);
			flash.addFlashAttribute("error", "Se ha eliminado la foto");

			try {
				if (uploadFileservice.delete(foto.getFotoImg())) {
					flash.addFlashAttribute("info", "Foto " + foto.getFotoImg() + " eliminada con exito ");
				}
			} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
				
		return "redirect:/listar";

		}
	flash.addFlashAttribute("error", "El id no puede ser negativo");
	return "redirect:/listar";
	}
	
}
