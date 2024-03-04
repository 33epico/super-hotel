package com.vilu.springboot.web.app.controller;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.vilu.springboot.web.app.entity.Tarifa;
import com.vilu.springboot.web.app.service.IPisoService;


@Controller
@RequestMapping("/tarifa")
@SessionAttributes("tarifa")
public class TarifaController {

	@Autowired
	private IPisoService pisoService;
	
	@RequestMapping(value = "/editor", method = RequestMethod.POST)
	public String guardar(@Valid Tarifa tarifa, 
			BindingResult result,
			Model model,
			Map<String, Object> modelo,
			RedirectAttributes flash,
			SessionStatus status) {
	
	Long id = tarifa.getPiso().getId();
	
	
	Piso piso = pisoService.findOne(id);
	List <Tarifa> listaTarifas = pisoService.findByPisoOrderByFechaInicioDesc(piso);
	
	java.util.Date fecha = new Date();
	Date fechaFin = tarifa.getFechaFin();
	Date fechaIni = tarifa.getFechaInicio();
	
	//Comparamos las fechas y evitamos que se añadan tarifas anteriores al dia actual
	if (fechaFin.compareTo(fecha) < 0 ) {
		 flash.addFlashAttribute("error", "No se puede configurar una fecha fin anterior a la fecha actual");
			return "redirect:editor/"+id;
	}
	
	if (fechaIni.compareTo(fecha) < 0 ) {
		 flash.addFlashAttribute("error", "No se puede configurar una fecha inicio anterior a la fecha actual");
			return "redirect:editor/"+id;
	}

	
	//Comparamos las fechas y evitamos que se añadan tarifas anteriores al dia actual
	for (Tarifa test : listaTarifas) { 		      
		
     
		if(fechaIni.compareTo(test.getFechaInicio())> 0) {
			if(fechaIni.compareTo(test.getFechaFin()) < 0 ) {
			flash.addFlashAttribute("error", "La fecha de inicio se solapa con la tarifa id="+test.getId()+ " ninguna fecha debe solaparse");
			return "redirect:editor/"+id;
			}
		}
		
		
		if(fechaFin.compareTo(test.getFechaInicio())> 0) {
			if(fechaFin.compareTo(test.getFechaFin()) < 0 ) {
			flash.addFlashAttribute("error", "La fecha de fin se solapa con la tarifa id="+test.getId()+ " ninguna fecha debe solaparse");
			return "redirect:editor/"+id;
			}
		}
	}
		
	//Fecha de fin anterior a la de inicio
	if (tarifa.getFechaFin().before(tarifa.getFechaInicio())) {
		flash.addFlashAttribute("error", "La fecha de fin no puede ser anterior a la de inicio");
		return "redirect:editor/"+id;
	}
	
	if (result.hasErrors()) {
		System.out.println(result.toString());
		model.addAttribute("titulo", "Formulario Crear Nueva Tarifa");
		flash.addFlashAttribute("error", "Hay errores en el formulario");
		return "redirect:editor/"+id;
	}else {
		pisoService.saveTarifa(tarifa);
		status.setComplete();
		flash.addFlashAttribute("success","Se ha guardado con exito");
	}

	return "redirect:editor/"+id;
	}
	
	@RequestMapping(value = "/editor/{id}")
	public String crear(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		
		Tarifa tarifa = new Tarifa();
		tarifa.setPiso(pisoService.findOne(id));
		tarifa.setTarifaBase(pisoService.findOne(id).getTarifaBase());
		
		model.put("titulo", "Crear tarifa");
		model.put("tarifa", tarifa);
		
		Piso piso = pisoService.findOne(id);
		List <Tarifa> listaTarifas = pisoService.findByPisoOrderByFechaInicioDesc(piso);
		model.put("listaTarifas", listaTarifas);
		
		return "tarifas/form_tarifa";
	}
	

	
	public long calculoPrecio(Date fechaInicial, Date fechaFinal, long pisoId) throws ParseException {

		Date fechafor = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicial);
		long precioTotal = 0;
		Boolean actTarifa = false;

		Piso piso = pisoService.findOne(pisoId);
		List <Tarifa> listaTarifas = pisoService.findByPisoOrderByFechaInicioDesc(piso);

		do {
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			fechafor = calendar.getTime();

			//recorremos los dias, si no hay tarifa configurada añadimos la base
			if (listaTarifas.isEmpty()) {
				precioTotal = precioTotal + piso.getTarifaBase();
				actTarifa = true;

			} else {

				for (Tarifa tarifa : listaTarifas) {

					Date fechaFin = tarifa.getFechaFin();
					Date fechaIni = tarifa.getFechaInicio();

					if (fechafor.after(fechaIni) && (fechafor.before(fechaFin))) {
						precioTotal = precioTotal + tarifa.getTarifaNueva();
						actTarifa = true;
					}
				}

			}
			
		if (actTarifa.equals(false) ) {
			precioTotal = precioTotal + piso.getTarifaBase();
			actTarifa = false;
		}
		
		actTarifa = false;
		
		} while (fechafor.before(fechaFinal));

		return precioTotal;

	}
	

	
}
