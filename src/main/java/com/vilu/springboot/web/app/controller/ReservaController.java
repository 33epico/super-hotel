package com.vilu.springboot.web.app.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.vilu.springboot.web.app.entity.Foto;
import com.vilu.springboot.web.app.entity.Peticiones;
import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Promociones;
import com.vilu.springboot.web.app.entity.Recibos;
import com.vilu.springboot.web.app.entity.Reserva;
import com.vilu.springboot.web.app.entity.Usuario;
import com.vilu.springboot.web.app.pojo.ItemReserva;
import com.vilu.springboot.web.app.service.EmailService;
import com.vilu.springboot.web.app.service.EnvioMailServiceNoVale;
import com.vilu.springboot.web.app.service.IConfiguracionService;
import com.vilu.springboot.web.app.service.IPisoService;
import com.vilu.springboot.web.app.service.ITarifaService;
import com.vilu.springboot.web.app.service.IUtilsService;
import com.vilu.springboot.web.app.service.impl.LocaleService;
import com.vilu.springboot.web.app.service.impl.UtilsServiceImpl;
import com.vilu.springboot.web.app.utils.ReservaUtils;

import ch.qos.logback.classic.pattern.Util;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

	@Autowired
	private IPisoService pisoService;
	
	@Autowired
	private ReservaUtils reservaUtils;
	
	@Autowired
	private UtilsServiceImpl utilVarios;
	
	@Autowired
	private IUtilsService pisoUtils;
	
	@Autowired
	private IConfiguracionService configuracionService;
	
	@Autowired
	private LocaleService localeService;
	
	@Autowired
	private EmailService envioMailService;


	public static String CODIGO_RESERVA;
	public static float PRECIO_RESERVA;

	private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	
	@RequestMapping(value = ("/reserva/{id}"), method = RequestMethod.GET)
	public String reserva(@PathVariable(value = "id") Long id,
			@RequestParam(name = "fechaFin", required = false) String fechaFin,
			@RequestParam(name = "fechaIni", required = false) String fechaIni,
			@RequestParam(name = "fechas", required = false) String fechas,
			Model model,
			RedirectAttributes flash,
			HttpServletRequest request) {
		
		//inicializamos parametros de estilos
		model = configuracionService.configuracion(model);
		model = configuracionService.norma(model);
		
		ItemReserva itemReserva = null;
		Reserva reservaBase = null; 
		Foto foto = null;
		String locale = LocaleContextHolder.getLocale().toString().substring(3);
		List<Foto> fotos = pisoService.findFotoByPisoOrderByIdAsc(pisoService.findOne(id));
		HttpSession session = request.getSession();
		List<String> ocupadosList= pisoUtils.fechasOcupado(pisoService.findOne(id));//cargamos la lista de ocupados
		
		//solo avanzamos si hay foto y el usr está registrado
		if (!fotos.isEmpty()) {
			foto = fotos.get(0);
		}else if(fotos.isEmpty()) {
			flash.addFlashAttribute("error",  localeService.textoLocale("errnopisos", locale));
			return "redirect:/index";
		}
		
		//cargamos reservas en la sesión o inicializamos fechas
		if(session.getAttribute("reservaPendiente")!=null){
			itemReserva = (ItemReserva) session.getAttribute("reservaPendiente");
			SimpleDateFormat dateFormat = new SimpleDateFormat(reservaUtils.FORMATO_FECHA_HOTELDATEPICKER);
			try {
				fechaIni = pisoUtils.dateToString(dateFormat.parse(itemReserva.getFechaIni()));
				fechaFin = pisoUtils.dateToString(dateFormat.parse(itemReserva.getFechaFin()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(fechaFin == null||fechaIni == null) {
			ArrayList<String> fechasArrString = (ArrayList<String>) pisoUtils.inicializacionFechas();
			fechaIni = fechasArrString.get(0);
			fechaFin = fechasArrString.get(1);
		}
		if(fechas!=null) {
			List<Date> fechasArr = pisoUtils.fechasDeDatepicker(fechas);
			fechaIni = pisoUtils.dateToString(fechasArr.get(0));
			fechaFin = pisoUtils.dateToString(fechasArr.get(1));
		}
		
		model.addAttribute("iva",configuracionService.findByClave("iva").getValor());
		model.addAttribute("fechaIni", fechaIni);
		model.addAttribute("fechaFin", fechaFin);
		model.addAttribute("piso",pisoService.findOne(id));
		model.addAttribute("foto",foto);
		model.addAttribute("ocupados",ocupadosList.toArray(new String[0]));
		model.addAttribute("locale",locale);
			
	    return "reservas/paginaReserva";
	}
	
	@RequestMapping(value = ("/crearReserva"), method = RequestMethod.POST)
	public String creacionReserva(
			@RequestParam(name = "fechas", required = true) String fechas,
			@RequestParam(name = "id", required = true) Long id,
			@RequestParam(name = "descuentoSend", required = false) String descuento,			
			@RequestParam(name = "preciototal", required = false) String preciototal, 
			Authentication authentication,
			RedirectAttributes flash,
			Model model, 
			HttpServletRequest request) {
		
		//Aniadimos la configuracion de aspecto
		model = configuracionService.configuracion(model);
		model = configuracionService.norma(model);

		List<Date> fechasIniFin = null;	
		String locale = LocaleContextHolder.getLocale().toString().substring(3);
		Reserva reserva = new Reserva();
		HttpSession session = request.getSession();
		String fechaIni="";
		String fechaFin ="";
		
		if(usuarioSesion()==null) {
			flash.addFlashAttribute("error", localeService.textoLocale("errnologing", locale));
			return "redirect:/login";
		}
				
		if(fechas!=null) {
			//fechasIniFin.get(0) es fecha inicial y fechasIniFin.get(1) es la fecha final
			fechasIniFin = reservaUtils.fechasDeDatepicker(fechas);
			fechaFin = pisoUtils.dateToString(fechasIniFin.get(1))+Reserva.HORA_OUT;
			fechaIni = pisoUtils.dateToString(fechasIniFin.get(0))+Reserva.HORA_IN;
		}else {
			flash.addFlashAttribute("error",localeService.textoLocale("errgenerico", locale));
			return "redirect:/login";
		}
		
		//Creamos la reserva para dejarla en la sesión
		reserva = reservaUtils.reservaBasica(fechaIni,fechaFin,usuarioSesion(),pisoService.findOne(id),descuento);
		session.setAttribute("reservaPendiente", new ItemReserva(fechaFin,fechaIni, id, descuento, reserva.getPrecioCalculado()));
		pisoService.saveReserva(reserva);
		CODIGO_RESERVA = reserva.getCodigoReserva();
		PRECIO_RESERVA=reserva.getPrecioPagar();
		
		//control de seguridad compara el valor calculado en el frontal con el calculado en el back solo avanza si hay una diferencia de 1 o menos
		if(Double.parseDouble(preciototal)-reserva.getPrecioPagar() >= -2 &&
				Double.parseDouble(preciototal)-reserva.getPrecioPagar() <= 2) {
			log.error("[ERROR] Ha habido una diferencia de mas de 2 euros entre lo calculado en el frontal y en el backend");			
		}
		
		if(!(descuento==null)){
			Promociones promocion = pisoService.findPromocionesBycodigoPromocion(descuento);
			if (!(promocion==null)){
				model.addAttribute("promocion",promocion);
			}
		}
		
		if(!pisoUtils.validarReserva(reserva)) {
			flash.addFlashAttribute("error",localeService.textoLocale("errfechas", locale));
			return "redirect:/reservas/reserva/"+id;
		}
		
		model.addAttribute("piso",reserva.getPiso());
		model.addAttribute("foto",pisoService.findFotoByPisoOrderByIdAsc(pisoService.findOne(id)).get(0));
		model.addAttribute("reserva",reserva);
		model.addAttribute("idioma",locale);
		model.addAttribute("noches",(int) ((reserva.getFechaFin().getTime()-reserva.getFechaInicio().getTime())/86400000));
		
		return "reservas/procesoPago"; 
	}
	
	@RequestMapping(value = ("/pagarCreada"), method = RequestMethod.GET)
	public String pagarReservaYaCreada(
			@RequestParam(name = "idReserva", required = true) String idreserva,
			Authentication authentication,
			RedirectAttributes flash,
			Model model, 
			HttpServletRequest request) {
		
		//Aniadimos la configuracion de aspecto
		model = configuracionService.configuracion(model);
		model = configuracionService.norma(model);

		List<Date> fechasIniFin = null;	
		String locale = LocaleContextHolder.getLocale().toString().substring(3);
		Reserva reserva = pisoService.findReservaBycodigoReserva(idreserva);
		HttpSession session = request.getSession();
		
		if(usuarioSesion()==null) {
			flash.addFlashAttribute("error", localeService.textoLocale("errnologing", locale));
			return "redirect:/login";
		}
		
		//Creamos la reserva para dejarla en la sesión
		/*reserva = reservaUtils.reservaBasica(fechaIni,fechaFin,usuarioSesion(),pisoService.findOne(id),descuento);
		session.setAttribute("reservaPendiente", new ItemReserva(fechaFin,fechaIni, id, descuento, reserva.getPrecioCalculado()));*/
		pisoService.saveReserva(reserva);
		CODIGO_RESERVA = reserva.getCodigoReserva();
		PRECIO_RESERVA=reserva.getPrecioPagar();
		
		if(!pisoUtils.validarReserva(reserva)) {
			flash.addFlashAttribute("error",localeService.textoLocale("errfechas", locale));
			return "redirect:/reservas/reserva/"+reserva.getPiso().getId();
		}
		
		model.addAttribute("piso",reserva.getPiso());
		model.addAttribute("foto",pisoService.findFotoByPisoOrderByIdAsc(pisoService.findOne(reserva.getPiso().getId())).get(0));
		model.addAttribute("reserva",reserva);
		model.addAttribute("idioma",locale);
		model.addAttribute("noches",(int) ((reserva.getFechaFin().getTime()-reserva.getFechaInicio().getTime())/86400000));
		
		return "reservas/procesoPago"; 
	}


	@RequestMapping(value = ("/pago"), method = RequestMethod.GET)
	public String reservaConsolidada(@RequestParam(name = "codigoReserva", required = true) String codigoReserva,
									 @RequestParam(name = "precioTotal", required = true) String precioTotal,
									 HttpServletRequest request,
									 RedirectAttributes flash,
									 Model model) {
		
		//Aniadimos la configuracion de aspecto
		model = configuracionService.configuracion(model);
		model = configuracionService.norma(model);
		
		String locale = LocaleContextHolder.getLocale().toString().substring(3);
		
		if (!CODIGO_RESERVA.equals(codigoReserva) || PRECIO_RESERVA != Float.parseFloat(precioTotal)) {
			log.error("[ERROR] No coinciden los datos del backend con los enviado desde el frontal");			
			CODIGO_RESERVA = "";
			PRECIO_RESERVA=0;
		}

		HttpSession session = request.getSession();
		Session sessionMail = null;
		Reserva reserva = pisoService.findReservaBycodigoReserva(codigoReserva);
		List<Foto> fotos = pisoService.findFotoByPisoOrderByIdAsc(reserva.getPiso());
		Foto foto = fotos.get(0);
		
		session.setAttribute("reservaPendiente", null);
		session.setAttribute("CodigoReserva", null);
		
		String detalleRecibo = "Reseva del dia "+reserva.getFechaInicio()+" al dia "+reserva.getFechaFin()+" a nombre de "+reserva.getUsuario().getUsername();
		Recibos recibo = new Recibos(codigoReserva, reserva.getPrecioCalculado(), reserva.getIvaCalculado(), reserva.getDescuentoCalculado(), reserva.getPrecioPagar(), detalleRecibo);
		               
		
		try {
			sessionMail = envioMailService.envioMailTemplate(reserva.getUsuario());
			envioMailService.plantillaRecibo(sessionMail, reserva.getUsuario().getUsrPerfil().getUsuario().getUsername(),"/emails/recibo", recibo, LocaleContextHolder.getLocale()); 		
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		if(pisoUtils.validarReserva(reserva)) {
			reserva.setConsolidada(1);
			pisoService.saveReserva(reserva);	
		}else {
			flash.addFlashAttribute("error",localeService.textoLocale("errgenerico", locale));
			return "redirect:/login";
		}
		
		model.addAttribute("foto", foto);
		model.addAttribute("piso",reserva.getPiso());
		model.addAttribute("reserva",reserva);
		model.addAttribute("noches",(int) ((reserva.getFechaFin().getTime()-reserva.getFechaInicio().getTime())/86400000));
		
		return "reservas/recibo_reserva"; 
	}
	
	/*
	 * 
	 * MODULO DE PETICIONES AJAX PARA LA CREACIÓN DE RESERVAS
	 * 
	 * */
	@RequestMapping(value = ("/ajax/fechasPrecio"), method = RequestMethod.GET)
	public String fechasPrecio(
			@RequestParam(name = "fechaIni", required = false) String fechaIni,
			@RequestParam(name = "fechaFin", required = false) String fechaFin,
			@RequestParam(name = "id", required = false) Long id,
			Model model) {
		
		Date fechaIniDate =  pisoUtils.strignToDate(fechaIni);
		Date fechaFinDate =  pisoUtils.strignToDate(fechaFin);
		
		Long totalPrecio =  reservaUtils.calculoPrecio(fechaIniDate, fechaFinDate, id);
		
		model.addAttribute("totalPrecioParam", totalPrecio);
		
		return "reservas/paginaReserva :: #totalPrecio";
	}
	
	@RequestMapping("/ajax/desglosePrecios")
	public String desglosePrecio(
			@RequestParam(name = "fechaIni", required = false) String fechaIni,
			@RequestParam(name = "fechaFin", required = false) String fechaFin,
			@RequestParam(name = "id", required = false) Long id,
			Model model) {
		
		Date fechaIniDate = pisoUtils.strignToDate(fechaIni);
		Date fechaFinDate = pisoUtils.strignToDate(fechaFin);
		
		TreeMap<String, Long> desglosePreciosList =  reservaUtils.detalleReserva(fechaIniDate, fechaFinDate, id);

		model.addAttribute("desglosePreciosList", desglosePreciosList);


		return "reservas/paginaReserva :: #desglosePrecios";
	}
	
	@RequestMapping("/ajax/promociones")
	public String valorDescuento(@RequestParam(name = "promocion", required = false) String promocion,
								 Model model) {
		
		promocion=promocion.replace(" ", "");
		Usuario usuario = usuarioSesion();
		List<Reserva> reservasUsuario = pisoService.findReservaByUsuario(usuario);
		Promociones promocionUsuario = pisoService.findPromocionesBycodigoPromocion(promocion);
		String locale = LocaleContextHolder.getLocale().toString().substring(3);
		
		
		if(usuario == null) {
			model.addAttribute("errorPromo",localeService.textoLocale("errnologing", locale));
			return "reservas/paginaReserva :: #errorPromoId";
		}else {
	
				if(promocionUsuario== null) {
					model.addAttribute("errorPromo",localeService.textoLocale("errnologing", locale));
					return "reservas/paginaReserva :: #errorPromoId";
				}
					
				//identificamos si el usuario puede disfrutar la promocion
				if (pisoUtils.validarPromo(promocionUsuario, usuario, reservasUsuario)) {
					model.addAttribute("descuentoPromo",promocionUsuario.getValorDescuento());
					return "reservas/paginaReserva :: #descuentoId";
				}else {		
					model.addAttribute("descuentoPromo",0);
					return "reservas/paginaReserva :: #descuentoId";
				}
		}
	}
	
	//obtenemos el usuario de manera estatica
	private Usuario usuarioSesion () {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = pisoService.findUsuarioByUsername(auth.getName()); 
		return usuario;
	}

}
