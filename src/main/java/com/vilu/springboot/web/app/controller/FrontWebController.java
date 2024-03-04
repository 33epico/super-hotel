package com.vilu.springboot.web.app.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vilu.springboot.web.app.entity.Foto;
import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.pojo.ItemTarifa;
import com.vilu.springboot.web.app.service.IConfiguracionService;
import com.vilu.springboot.web.app.service.IPisoService;
import com.vilu.springboot.web.app.service.ITarifaService;
import com.vilu.springboot.web.app.service.IUtilsService;
import com.vilu.springboot.web.app.service.impl.LocaleService;
import com.vilu.springboot.web.app.utils.ReservaUtils;

@Controller
public class FrontWebController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	protected final String colorFondo = null;
	protected final String colorTexto = null;
	
	@Autowired
	private IUtilsService pisoUtils;
	
	@Autowired
	private IPisoService pisoService;
	
	@Autowired
	private ReservaUtils reservaUtils;
	
	@Autowired
	private ITarifaService iTarifaService;
	
	@Autowired
	private IConfiguracionService configuracionService;
	
	@Autowired
	private LocaleService localeService;


	/**
	 * Landing page, es la pantalla a la que llegamos 
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String landing(Model model) {
		//inicializamos parametros de estilos
		model = configuracionService.configuracion(model);
		model = configuracionService.norma(model);
		return "index";
	}
	

	@RequestMapping(value = ("/publico/listar"), method = RequestMethod.GET)
	public String listar(
			@RequestParam(name = "ocupacion", defaultValue = "0") Long ocupacion,
			@RequestParam(name = "fechas", required = false) String fechas, 
			Model model,
			Authentication authentication,
			HttpServletRequest request) throws ParseException {
		
		List<Foto> miniFotos = new ArrayList<Foto>();
		List<Piso> itemPisos = new ArrayList<Piso>();
		List<Date> fechasIniFin = null;
		List<Piso> pisosDisponibles=null;
		List<ItemTarifa> itemTarifas = null;
		String fechaOcupacion;
		String locale = LocaleContextHolder.getLocale().toString().substring(3);
		
		
		//Aniadimos la configuracion de aspecto
		model = configuracionService.configuracion(model);
		model = configuracionService.norma(model);
		
		
		if(fechas!=null) {
			//fechasIniFin.get(0) es fecha inicial y fechasIniFin.get(1) es la fecha final
			fechasIniFin = reservaUtils.fechasDeDatepicker(fechas);
			pisosDisponibles =  pisoUtils.pisosDisponibles(fechasIniFin.get(0), fechasIniFin.get(1), ocupacion);
			fechaOcupacion = pisoUtils.dateToString(fechasIniFin.get(0))+" - "+pisoUtils.dateToString(fechasIniFin.get(1));
		}else {
			pisosDisponibles = pisoUtils.pisosDisponibles(null, null, ocupacion);
			fechaOcupacion = "";
		}
		
		if(pisosDisponibles.size()==0||pisosDisponibles == null) {
			fechaOcupacion = localeService.textoLocale("nopisos", locale);
		}
		
		//localizamos todos los pisos activos y disponibles para esas fechas y esa ocupacion
		for (Piso piso : pisosDisponibles) {
			miniFotos.add(piso.getFotos().get(0));
			piso.setTarifaBase(piso.getTarifaBase());
			itemPisos.add(piso);		
		}
	
		model.addAttribute("fecha",fechaOcupacion);
		model.addAttribute("miniFotos", miniFotos);
		model.addAttribute("itemPisos",pisoUtils.listaItemPisoDePiso(itemPisos));
		model.addAttribute("ocupacion",ocupacion);
		model.addAttribute("idioma",locale);

		return "publico/listadoMapa";
	}

	@RequestMapping(value = ("publico/galeria_publica/{id}"), method = RequestMethod.GET)
	public String galeriaPublica(@PathVariable(value = "id") Long id, 
							     Model model, 
							     Authentication authentication,
							     HttpServletRequest request,
							     RedirectAttributes flash) {
		
		//Aniadimos la configuracion de aspecto
		model = configuracionService.configuracion(model);
		String locale = LocaleContextHolder.getLocale().toString().substring(3);
		
		Piso piso = pisoService.findOne(id);
		if(piso == null) {
			flash.addFlashAttribute("error", localeService.textoLocale("error", locale));
			return "redirect:/index";
		}
		List<Foto> miniFotos = piso.getFotos();
		List<String> ocupadosList= pisoUtils.fechasOcupado(pisoService.findOne(id));//c
		
		model.addAttribute("ocupados", ocupadosList);
		model.addAttribute("miniFotos", miniFotos);
		model.addAttribute("itemPiso", pisoUtils.itemPisoDePiso(piso));
		model.addAttribute("idioma",locale);
		model.addAttribute("piso", piso);

		return "publico/galeria_publica";
	}

	
}
