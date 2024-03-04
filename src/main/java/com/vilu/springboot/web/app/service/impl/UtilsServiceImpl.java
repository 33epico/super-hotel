package com.vilu.springboot.web.app.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Promociones;
import com.vilu.springboot.web.app.entity.Reserva;
import com.vilu.springboot.web.app.entity.Usuario;
import com.vilu.springboot.web.app.pojo.ItemPiso;
import com.vilu.springboot.web.app.service.IUtilsService;
import com.vilu.springboot.web.app.utils.PisoUtils;
import com.vilu.springboot.web.app.utils.ReservaUtils;
import com.vilu.springboot.web.app.utils.Utils;

@Service
public class UtilsServiceImpl implements IUtilsService{
	
	@Autowired
	private PisoUtils pisoUtils;
	
	@Autowired
	private ReservaUtils reservaUtils;
	
	@Autowired
	private Utils utils;

	/**
	 * Obtener item
	 * -itemPiso
	 * -Lista de Itempiso
	 */
	public ItemPiso itemPisoDePiso(Piso piso) {
		return pisoUtils.itemPisoDePiso(piso);
	}

	public List<ItemPiso> listaItemPisoDePiso(List<Piso> pisos) {
		return pisoUtils.listaItemPisoDePiso(pisos);
	}
	

	/**
	 * Utils para reservas
	 * -listaReservas
	 * -fechas ocupacion
	 * -detalleReserva
	 */
	public TreeMap<String, Object> listaReservas(List<Reserva> reservas) {
		return reservaUtils.listaReservas(reservas);
	}
	
	public TreeMap<String, Long> detalleReserva(Date fechaInicial, Date fechaFinal, long pisoId) {
		return reservaUtils.detalleReserva(fechaInicial, fechaFinal, pisoId);
	}
	
	public List<String> fechasOcupado (Piso piso){
		return pisoUtils.fechasOcupado(piso);
	}

	public long calculoPrecio(Date fechaInicial, Date fechaFinal, long pisoId) {
		return reservaUtils.calculoPrecio(fechaInicial, fechaFinal, pisoId);
	}

	/**
	 * Genera un id unico de reserva con una fecha y un nombre de usuario
	 */
	public String creacionIdReserva() {
		return  (((UUID.randomUUID().toString()).toUpperCase())).substring(0, 30);
		}

	/**
	 * Pasamos un string del hotel date picker y nos devuelve dos un array con dos fechas, inicio y fin
	 */
	public List<Date> fechasDeDatepicker(String fechas) {
		return reservaUtils.fechasDeDatepicker(fechas);
	}

	public List<Piso> pisosDisponibles(Date fechaIni, Date fechaFin, Long ocupacion) throws ParseException {
		return pisoUtils.pisosDisponibles(fechaIni, fechaFin, ocupacion);
	}

	public boolean disponible(Date fechaInicial, Date fechaFinal, long id) throws ParseException {
		return pisoUtils.disponible(fechaInicial,fechaFinal,id);
	}

	/*Utilidades generales*/
	public Date strignToDate(String fechaString) {
		return Utils.strignToDate(fechaString);
	}
	
	public Date strignToDateHoras(String fechaString) {
		return Utils.strignToDateHoras(fechaString);
	}
	

	public String dateToString(Date fechaDate) {
		return Utils.dateToString(fechaDate);
	}

	public String fechaActivaInactiva(Date fechaIni, Date fechaFin, Date fechaConsulta) {
		return utils.fechaActivaInactiva(fechaIni, fechaFin, fechaConsulta);
	}
	
	public String fechasValidas(Date fechaInicio,Date fechaFin) {
		return utils.fechasValidas(fechaInicio, fechaFin);
	}
	
	public boolean validarPromo(Promociones promocion, Usuario usuario,List<Reserva> reservasUsuario) {
		return utils.validarPromo(promocion, usuario, reservasUsuario);
	}
	
	public Reserva reservaBasica(String fechaIni, String fechaFin, Usuario usuario,Piso piso, String codigoPromo) {
		return reservaUtils.reservaBasica(fechaIni, fechaFin, usuario, piso,codigoPromo);
	}

	public List<String> inicializacionFechas() {
		return utils.inicializacionFechas();
	}
	
	public boolean validarReserva(Reserva reserva) {
		return reservaUtils.validarReserva(reserva);
	}
	
	public Date sumarHoras(Date fecha, int horas) { 
		return utils.sumarHoras(fecha, horas);
	}
	
	public void cancelarReserva(String  idReserva,String locale) {
		reservaUtils.cancelarReserva(idReserva, locale);
	}
	

}
