package com.vilu.springboot.web.app.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vilu.springboot.web.app.entity.Peticiones;
import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Promociones;
import com.vilu.springboot.web.app.entity.Reserva;
import com.vilu.springboot.web.app.entity.Tarifa;
import com.vilu.springboot.web.app.entity.Usuario;
import com.vilu.springboot.web.app.service.IConfiguracionService;
import com.vilu.springboot.web.app.service.IPisoService;
import com.vilu.springboot.web.app.service.IUtilsService;
import com.vilu.springboot.web.app.service.impl.LocaleService;

@Service
public class ReservaUtils {

	
	@Autowired
	private IPisoService pisoService;
	
	@Autowired
	private  IUtilsService utilsService;
	
	@Autowired
	private IConfiguracionService configuracionService;
	
	@Autowired
	private PisoUtils pisoUtils;
	
	@Autowired
	private LocaleService localeService;
	
	
	public static final String FORMATO_FECHA_HOTELDATEPICKER = "yyyy-MM-dd";
    public static final String FORMATO_FECHA = "MM/dd/yyyy";
	
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Como entrada tiene una lista de reservas:
	 * Nos devuelve un treemap tipo <string, Objeto> con las reservas categorizadas como se muestra a continuación
	 *  reservasActivas", reservasActivas
	 *  reservasProximas", reservasProximas
	 *  reservasHistorico", reservasHistorico
	 *  reservasCanceladas", reservasCanceladas
	 *  reservasSinPagar", reservasSinPagar
	 *  
	 *  Utiliza los siguentes para metros para su categoria a demas de la fecha de las reservas frente a la fecha actual
	 *  0 Creada y no pagada
	 *	1 Creada y pagada
	 *	2 Finalizada (historificada)
	 *	3 cancelada	
	 * 
	 */
	public TreeMap <String, Object> listaReservas (List<Reserva> reservas){
		List<Reserva> reservasActivas = new ArrayList<Reserva>();
		List<Reserva> reservasProximas = new ArrayList<Reserva>();
		List<Reserva> reservasHistorico  = new ArrayList<Reserva>();
		List<Reserva> reservasCanceladas  = new ArrayList<Reserva>();
		List<Reserva> reservasSinPagar  = new ArrayList<Reserva>();
		TreeMap<String, Object> reservasCategorizadas = new TreeMap <String,Object>();
		
		Date hoy = new Date(System.currentTimeMillis());
		for (Reserva reserva: reservas) {
			if (reserva.isActiva()) {
				if(reserva.getConsolidada()==0 && hoy.before(reserva.getFechaInicio())) {
					reservasSinPagar.add(reserva);
				}
				if(reserva.getConsolidada()==1 && hoy.before(reserva.getFechaInicio())) {
					reservasProximas.add(reserva);
				}
				if(hoy.after(reserva.getFechaInicio()) && hoy.before(reserva.getFechaFin())) {
					reservasActivas.add(reserva);
				}
			}else {
				if(reserva.getConsolidada()==3) {
					reservasCanceladas.add(reserva);
				}
				if(reserva.getConsolidada()==2) {
					reservasHistorico.add(reserva);
				}
				if(reserva.getConsolidada()==0) {
					reservasHistorico.add(reserva);
				}
			}
		}
		
		reservasCategorizadas.put("reservasActivas", reservasActivas);
		reservasCategorizadas.put("reservasProximas", reservasProximas);
		reservasCategorizadas.put("reservasHistorico", reservasHistorico);
		reservasCategorizadas.put("reservasCanceladas", reservasCanceladas);
		reservasCategorizadas.put("reservasSinPagar", reservasSinPagar);
		
		return reservasCategorizadas;
	}
	
	/**
	 * Devuelve una lista <String, long> con la fecha y el precio calculado de cada uno de los dias 
	 * de dicha fecha teniendo en cuenta las tarifas y las temporadas.
	 * Como parametro de entrada se mete una fecha de inicio una fecha de fin y el piso que se quiere
	 * evaluar.
	 * 
	 */
	
	public TreeMap<String, Long> detalleReserva (Date fechaInicial, Date fechaFinal, long pisoId){

		Date fechafor = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaFinal);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		fechaFinal = calendar.getTime();
		calendar.setTime(fechaInicial);
		Boolean actTarifa = false;
		
		TreeMap<String, Long> detalleReserva = new TreeMap <String, Long>();

		Piso piso = pisoService.findOne(pisoId);
		List <Tarifa> listaTarifas = pisoService.findByPisoOrderByFechaInicioDesc(piso);

		do {
			fechafor = calendar.getTime();

			//recorremos los dias, si no hay tarifa configurada añadimos la base
			if (listaTarifas.isEmpty()) {
				detalleReserva.put(Utils.dateToString(fechafor), piso.getTarifaBase());
				actTarifa = true;
			} else {

				for (Tarifa tarifa : listaTarifas) {

					Date fechaFin = tarifa.getFechaFin();
					Date fechaIni = tarifa.getFechaInicio();

					if (fechafor.after(fechaIni) && (fechafor.before(fechaFin))) {
						detalleReserva.put(Utils.dateToString(fechafor), tarifa.getTarifaNueva());
						actTarifa = true;
					}
				}
			}
			
		if (actTarifa.equals(false) ) {
			detalleReserva.put(Utils.dateToString(fechafor), piso.getTarifaBase());
			actTarifa = false;
		}
		
		actTarifa = false;
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		} while (fechafor.before(fechaFinal));

		return detalleReserva;
	}
	
	/**
	 * Dada una fecha de inicio, una de fin y un piso hará el calculo del valor
	 * de la reserva en base a la tarifa base y las tarifas configuradas entre
	 * esas fechas
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param pisoId
	 * @return
	 */
	public long calculoPrecio(Date fechaInicial, Date fechaFinal, long pisoId) {

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
	
	/**
	 * Pasamos una doble fecha obtenida con el datapicker y nos devuele dos fechas
	 * en un array, la feche de inicio y la fecha de fin
	 * @param fechas
	 * @return
	 */
	public List<Date> fechasDeDatepicker(String fechas) {
				String[] fechasARR = fechas.split(" - ");
				ArrayList<Date> fechasArrString = new ArrayList<Date>();

				SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA_HOTELDATEPICKER);

				try {
					fechasArrString.add(dateFormat.parse(fechasARR[0]));
					fechasArrString.add(dateFormat.parse(fechasARR[1]));

				} catch (ParseException e) {
					e.printStackTrace();
				}
		return fechasArrString;
	}
	
	/**
	 * Creamos una reserva con los datos minimos y correctos
	 * @author Epico
	 *
	 */
	public Reserva reservaBasica(String fechaIni, String fechaFin, Usuario usuario,Piso piso, String codigoPromo) {
		
		Reserva reservaBasica = new Reserva();
		float descuentoCalculado=0;		
		String codigoReserva="";//id de la reserva
		/*Date fechaIniDate = new Date();
		
		if (fechaFin == null || fechaIni == null) {
			fechaIni = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fechaIniDate);
			fechaFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fechaIniDate);
		}*/
		
		if(!(usuario==null)){
			reservaBasica.setUsuario(usuario);
			codigoReserva = utilsService.creacionIdReserva();
		}
		
		float precioCalculado = (float) Math.floor(calculoPrecio( utilsService.strignToDate(fechaIni),utilsService.strignToDate(fechaFin),piso.getId()));
		float ivaCalculado = (float) Math.floor(calculoIva(piso.getServicioLimpieza() + precioCalculado));
		float precioPagar = (float) Math.floor(precioCalculado+piso.getServicioLimpieza() + ivaCalculado);
		
		if(!(codigoPromo==null || codigoPromo.isEmpty())) {
			try {
				Promociones promocionUsuario = pisoService.findPromocionesBycodigoPromocion(codigoPromo.replace(" ", ""));
				if (!promocionUsuario.equals("")||promocionUsuario != null) {
					float descuento = Float.parseFloat(promocionUsuario.getValorDescuento().toString())/100;
					descuentoCalculado = (float) Math.floor(precioPagar*descuento);
					precioPagar =  (float) Math.floor(precioPagar- descuentoCalculado);
					reservaBasica.setCodigoPromo(codigoPromo);
				}
			}catch(Exception e) {
				e.printStackTrace();
				log.error("[error] no se ha encontrado la reserva "+codigoPromo);
				
			}
		}
			
		reservaBasica.setServicioLimpieza(piso.getServicioLimpieza()+0);
		reservaBasica.setActiva(true);
		reservaBasica.setConsolidada(0);
		reservaBasica.setPiso(piso);
		reservaBasica.setUsuario(usuario);
		reservaBasica.setFechaFin(utilsService.strignToDateHoras(fechaFin));
		reservaBasica.setFechaInicio(utilsService.strignToDateHoras(fechaIni));
		reservaBasica.setPrecioCalculado((float) Math.floor(precioCalculado));
		reservaBasica.setDescuentoCalculado((float) Math.floor(descuentoCalculado));
		reservaBasica.setIvaCalculado((float) Math.floor(ivaCalculado));
		reservaBasica.setPrecioPagar((float) Math.floor((precioPagar)));
		reservaBasica.setCodigoReserva(codigoReserva);		
		return reservaBasica;
	}
	
	/**
	 * Valida una reserva respecto del resto de reservas activas y pagadas del sistema
	 * @param reserva
	 * @return
	 */
	public boolean validarReserva(Reserva reserva) {
		
		List<Reserva> listaReservas = pisoService.findReservaByPisoAndActivaAndConsolidada(reserva.getPiso(), true, 1);
		Date fechaFin = reserva.getFechaFin();
		Date fechaIni = reserva.getFechaInicio();
		
		for(Reserva reservaFor: listaReservas ) {
			
			if(!(reservaFor.getCodigoReserva().equals(reserva.getCodigoReserva())) &&
						reservaFor.getConsolidada() == 1) {
				if(fechaIni.equals(reservaFor.getFechaInicio())) {
					return false;
 				}else if(fechaIni.after(reservaFor.getFechaInicio()) && (fechaIni.before(fechaIni))) {
					return false;
				}
				
				if(fechaFin.equals(reservaFor.getFechaFin())) {
					return false;
				}else if(fechaFin.after(reservaFor.getFechaFin()) && (fechaFin.before(fechaFin))) {
					return false;
				}
				if(reservaFor.getFechaInicio().after(fechaIni)&& reservaFor.getFechaFin().before(fechaFin)) {
					return false;
				}
					
			}
			return true;
		}
		return true;
	}
	
	/**
	 * se emite la peticion de cancelación de una reserva
	 * @param idReserva
	 * @return
	 */
	public void cancelarReserva(String  idReserva,String locale) {
		Peticiones peticion = new Peticiones();
		peticion.setCodigo(idReserva);
		peticion.setMensaje(localeService.textoLocale("peticionCancelacion", locale)+" "+idReserva);
		peticion.setTipo(Peticiones.CANCELACION);
		peticion.setCreateAt(new Date());
		pisoService.savePeticiones(peticion);
		Reserva reservaCancelada = pisoService.findReservaBycodigoReserva(idReserva);
		reservaCancelada.setConsolidada(3);
		reservaCancelada.setActiva(false);
		reservaCancelada.setMensaje(Reserva.PETICION_PENDIENTE);
		pisoService.saveReserva(reservaCancelada);
	}
	
	private float calculoIva(float precioCalculado) {
		float iva = Float.parseFloat(configuracionService.findByClave("iva").getValor())/100;
		return precioCalculado*iva;
	}
	

}
