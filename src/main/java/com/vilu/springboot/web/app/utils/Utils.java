package com.vilu.springboot.web.app.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vilu.springboot.web.app.entity.Promociones;
import com.vilu.springboot.web.app.entity.Reserva;
import com.vilu.springboot.web.app.entity.Usuario;


@Service
public class Utils {
	
	
	/**
	 * Este metodo devuelve una fecha de "hoy" con fechaIni y una fecha de "mañana" con fecha fin
	 * 
	 */
	public static List<String> inicializacionFechas() {
		Date fecha = new Date();
		ArrayList<String> fechasArrString = new ArrayList<String>();
		String pattern = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(pattern);  
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);			
		String fechaIni = df.format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		String fechaFin = df.format(calendar.getTime());
		fechasArrString.add(fechaIni);
		fechasArrString.add(fechaFin);
		return fechasArrString;
	}
	
	/**
	 * Pasamos una fecha en string y lo convertimos a date
	 * @param fechaString
	 * @return
	 */
	public static Date strignToDate(String fechaString) {
		Date fechaDate = null;
		try {
			fechaDate = new SimpleDateFormat(Constants.FORMATO_FECHA_HOTELDATEPICKER).parse(fechaString+" 12:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fechaDate;
	}
	
	/**
	 * 
	 */
	public static Date strignToDateHoras(String fechaString) {
		Date fechaDate = null;
		try {
			fechaDate = new SimpleDateFormat(Constants.FORMATO_FECHA_HORAS).parse(fechaString+" 12:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fechaDate;
	}
	
	/**
	 * Pasamos una fecha en Date y lo convertimos a string
	 * @param fechaDate
	 * @return
	 */
	public static String dateToString(Date fechaDate) {
		String pattern = Constants.FORMATO_FECHA_HOTELDATEPICKER;
		DateFormat df = new SimpleDateFormat(pattern);      
		String fechaString = df.format(fechaDate);
		return fechaString;
	}
	
	/**
	 * Este método se utiliza para saber si una fecha se encuntra entre dos fecha o fuera de ese rango
	 * Si la fecha de consulta esta antes de fecha inicio esta PENDIENTE (no ha llegado al rango)
	 * Si la fecha de consulta esta entre la de inicio y la de fin esta DENTRO (del rango)
	 * Si la fecha de consulta es posteior a la de fin esta FUERA (del rango)
	 * @param fechaIni
	 * @param fechaFin
	 * @param fechaConsulta
	 * @return
	 * @throws ParseException
	 */
	public String fechaActivaInactiva(Date fechaIni, Date fechaFin, Date fechaConsulta) {
		String activa ="ERROR";
		
		if (fechaFin.compareTo(fechaConsulta) < 0) {
			activa= "FUERA";
		}
		if (fechaIni.compareTo(fechaConsulta) > 0) {
			activa= "PENDIENTE";
		}
		if (fechaIni.compareTo(fechaConsulta) > 0 && fechaIni.compareTo(fechaConsulta) < 0) {
			activa= "DENTRO";
		}
		
		return activa;
	}
	
	public String fechasValidas(Date fechaInicio,Date fechaFin) {
		java.util.Date fecha = new Date();
		String mensaje = "OK";
		
		//Comparamos las fechas y evitamos que se añadan tarifas anteriores al dia actual
		if (fechaFin.compareTo(fecha) < 0 ) {
			mensaje="No se puede configurar una fecha fin anterior a la fecha actual";
		}
		
		if (fechaInicio.compareTo(fecha) < 0 ) {
			mensaje = "No se puede configurar una fecha inicio anterior a la fecha actual";
		}
		
		if(fechaInicio.compareTo(fechaInicio) < 0) {
			mensaje = "La fecha de inicio no puede ser superor a la fecha fin";
		}
		
		return mensaje;
	}
	
	/**
	 * El metodo nos devuelve true si la promoción para un usuario y una reserva es valida
	 * @param promocion
	 * @param usuario
	 * @param reservasUsuario
	 * @return
	 */
	public boolean validarPromo(Promociones promocion, Usuario usuario,List<Reserva> reservasUsuario) {
		java.util.Date fecha = new Date();
		int numeroUsosPromo = 0;
		if (promocion.getActivo()) {
			if (promocion.getFechaFin().after(fecha) && promocion.getFechaInicio().before(fecha)) {
				if(!reservasUsuario.isEmpty()) {
					for(Reserva reserva: reservasUsuario) {
						if(!(reserva.getCodigoPromo()==null) && reserva.getCodigoPromo().equals(promocion.getCodigoPromocion())){
							numeroUsosPromo++;
							if(promocion.getNumeroDeUsos()>=numeroUsosPromo) {
								return false;//ya se ha usado mas veces de las permitidas
							}
						}
					}
				}
			return true;//se permite la promo
			}
			return false; //la promo esta fuera de fecha
		}
		return false;//no esta activa la promo
	}
	
	/**
	 * Funcion que nos permite sumar horas a una fecha
	 * @param fecha
	 * @param horas
	 * @return
	 */
	public Date sumarHoras(Date fecha, int horas) { 
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); // Configuramos la fecha que se recibe
	      calendar.set(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0
	      return calendar.getTime();
	}
	

	
	class Constants {
	      public static final String FORMATO_FECHA_HOTELDATEPICKER = "yyyy-MM-dd";
	      public static final String FORMATO_FECHA_HORAS = "yyyy-MM-dd HH:mm:ss";
	}

}
