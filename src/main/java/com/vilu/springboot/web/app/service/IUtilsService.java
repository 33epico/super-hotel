package com.vilu.springboot.web.app.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.vilu.springboot.web.app.entity.Foto;
import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Promociones;
import com.vilu.springboot.web.app.entity.Reserva;
import com.vilu.springboot.web.app.entity.Usuario;
import com.vilu.springboot.web.app.pojo.ItemPiso;

public interface IUtilsService {
	
	//utilidades generales
	
	public  List<String> inicializacionFechas();
	
	public Date strignToDate(String fechaString);
	
	public Date strignToDateHoras(String fechaString);
	
	public String dateToString(Date fechaDate);
	
	public List<Date> fechasDeDatepicker(String fechas);
	
	public String fechaActivaInactiva(Date fechaIni, Date fechaFin, Date fechaConsulta);
	
	//utilidades de conversión
	public ItemPiso itemPisoDePiso (Piso piso);
	
	public List<ItemPiso> listaItemPisoDePiso (List<Piso> pisos);
	
	//utilidades de reservas
	
	/**
	 * para una lista de reservas nos devuelve el estado de cada una de ellas
	 * */
	public TreeMap <String, Object> listaReservas (List<Reserva> reservas);
	
	/**
	 * Devuleve una lista con una fecha y el precio para ese dia entre la fecha de inicio y fin  de la reserva de entrada
	 * */
	public TreeMap<String, Long> detalleReserva (Date fechaInicial, Date fechaFinal, long pisoId);
	
	/**
	 * Devuelve el valor de un precio via tarifa dado dos fechas y un id para el piso
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param pisoId
	 * @return
	 */
	public long calculoPrecio(Date fechaInicial, Date fechaFinal, long pisoId);
	
	/**
	 * Nos devuelve ok si la fecha de inicio o la de fin estan correctas si no, nos da el mensaje de error
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public String fechasValidas(Date fechaInicio,Date fechaFin);
	
	/**
	 * Nos devuelve las fechas en las que se encuentra ocupado el piso
	 * @param piso
	 * @return
	 */
	public List<String> fechasOcupado (Piso piso);
	
	/**
	 * Creacion de un id de reserva
	 * @param usuario
	 * @param fecha
	 * @return
	 */
	public String creacionIdReserva();
	
	/**
	 * Dado una fecha y una ocupacion nos devuelve una lista de pisos disponibles
	 * @param fechaIni
	 * @param fechaFin
	 * @param ocupacion
	 * @return
	 * @throws ParseException 
	 */
	public List<Piso> pisosDisponibles(Date fechaIni,Date fechaFin,Long ocupacion) throws ParseException;
	
	/**
	 * 
	 * @param pisos
	 * @return
	 */
	//TODO corregir el itemFoto para que no haga bucle
//	public List<Foto> fotosPisosDisponibles(List<Piso> pisos);
	
	/**
	 * dado el id de un piso, fecha inicio y fecha fin nos devuelve 1 si esta disponible 0 si no
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param id
	 * @return
	 * @throws ParseException 
	 */
	public boolean disponible(Date fechaInicial, Date fechaFinal, long id) throws ParseException;
	
	/**
	 * Para saber si una promocion es valida para un usuario
	 * @param promocion
	 * @param usuario
	 * @param reservasUsuario
	 * @return
	 */
	public boolean validarPromo(Promociones promocion, Usuario usuario,List<Reserva> reservasUsuario);
	
	/**
	 * Creamos una reserva basica para cargar los combos
	 * @param fechaIni
	 * @param fechaFin
	 * @param usuario
	 * @param piso
	 * @return
	 */
	
	public Reserva reservaBasica(String fechaIni, String fechaFin, Usuario usuario,Piso piso, String codigoPromo);

	/**
	 * No indica si las fechas de una reserva estan correctamente o no, de esta manera podemos
	 * saber si la reserva es valida para el piso donde queremos hacerla
	 * @param reserva
	 * @return
	 */
	public boolean validarReserva(Reserva reserva);
	
	/**
	 * Emite la petición de cancelación de una reserva
	 * @param idReserva
	 * @param locale
	 */
	public void cancelarReserva(String  idReserva,String locale);
}
