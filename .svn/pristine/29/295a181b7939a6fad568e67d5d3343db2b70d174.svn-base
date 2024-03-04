package com.vilu.springboot.web.app.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vilu.springboot.web.app.entity.Foto;
import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Reserva;
import com.vilu.springboot.web.app.pojo.ItemFoto;
import com.vilu.springboot.web.app.pojo.ItemPiso;
import com.vilu.springboot.web.app.pojo.ItemTarifa;
import com.vilu.springboot.web.app.service.IPisoService;
import com.vilu.springboot.web.app.service.ITarifaService;

@Service
public class PisoUtils {
	
	@Autowired
	private IPisoService pisoService;
	
	@Autowired
	private ITarifaService iTarifaService;
	
	/**
	 * Nos devuelve un objeto itempiso con la información completa de un piso
	 * @param piso
	 * @return
	 */
	public ItemPiso itemPisoDePiso (Piso piso) {
		
		List<ItemFoto> itemFotos = listaItemFotoDeFoto(pisoService.findFotoByPisoOrderByIdAsc(piso));
		List<ItemTarifa> itemTarifas = iTarifaService.listaTarifaListaItemTarifa(piso.getTarifas());
		List<String> ocupacion = fechasOcupado(piso);
		
		ItemPiso itemPiso = 
				new ItemPiso(
						piso.getId(),
						piso.getNombre(), 
						piso.getCalle(),
						piso.getPisoAltura(), 
						piso.getTarifaBase(), 
						piso.getHabitaciones(), 
						piso.getNumero(), 
						piso.getPoblacion(), 
						piso.getActivo(), 
						piso.getMotivo(), 
						piso.getDetalle(),
						piso.getPersonas(), 
						itemFotos, 
						itemTarifas,
						ocupacion,
						piso.getCoordenadaX(),
						piso.getCoordenadaY());
		return itemPiso;
		}
	
	/**
	 * Nos devuelve la lista de todos los pisos con toda la información de estos
	 * @param pisos
	 * @return
	 */
	public List<ItemPiso> listaItemPisoDePiso (List<Piso> pisos) {
		
		List<ItemPiso> itemPisos = new ArrayList<ItemPiso>();
		
		for (Piso piso: pisos) {
			itemPisos.add(itemPisoDePiso(piso));
		}
		
		return itemPisos;	
	}
	
	/**
	 * Nos devuelve un itemFoto de una foto con el detalle rute e id
	 * @param foto
	 * @return
	 */
	public ItemFoto itemFotoDeFoto (Foto foto) {
		
		ItemFoto itemFoto = new ItemFoto(foto.getId(),foto.getDetalle(), foto.getFotoImg());
		return itemFoto;
	}
	
	/**
	 * Nos devuelve una lista de itemfotos desde una lista de fotos 
	 * @param fotos
	 * @return
	 */
	public List<ItemFoto> listaItemFotoDeFoto (List<Foto> fotos) {
		
		List<ItemFoto> itemFotos = new ArrayList<ItemFoto>();
		
		for (Foto foto: fotos) {
			itemFotos.add(itemFotoDeFoto(foto));
		}
		
		return itemFotos;	
	}
	
	/**
	 * Si le pasamos un objeto piso nos devuelve una lista de todas las fechas ocupadas,
	 * solo tendrá en cuenta las reservas activas
	 * @param piso
	 * @return
	 */
	public List<String> fechasOcupado (Piso piso){
		Date fechafor = null;
		Calendar calendar = Calendar.getInstance();
		Date hoyDate = calendar.getTime();
		List<String> fechasOcupado = new ArrayList<String>();
		
		List<Reserva> dias=  pisoService.findReservaByPiso(piso);
		
		for(Reserva dia: dias) {
			if(dia.isActiva() && dia.getConsolidada()==1) {
					calendar.setTime(dia.getFechaInicio());
					if(hoyDate.before(dia.getFechaInicio())) {
						do {	
							//El hotel datepicker tiene un formato de fecha especifico, se lo adaptamos y ponemos comillas
							fechafor = calendar.getTime();
							String pattern = "yyyy-MM-dd";
							DateFormat df = new SimpleDateFormat(pattern);      
							String fechaString = df.format(fechafor);
							String fechaFin = df.format(dia.getFechaFin());
							if(!(fechaFin.equals(fechaString))) {
								fechasOcupado.add(fechaString);	
							}							
							calendar.add(Calendar.DAY_OF_YEAR, 1);
						}while (fechafor.before(dia.getFechaFin()));
					}
			}
		}
		return fechasOcupado;
	}
	
	/**
	 * Nos devuelve todos los pisos Activos y disponibles para una ocuapacion y unas fechas dadas
	 * Solo devuelve pisos con almenos una foto
	 * @param fechaIni
	 * @param fechaFin
	 * @param ocupacion
	 * @return
	 * @throws ParseException
	 */
	public List<Piso> pisosDisponibles(Date fechaIni, Date fechaFin, Long ocupacion) throws ParseException {
		List<Piso> pisosDisponibles = new ArrayList<Piso>();
		
		// localiza los pisos que estan activos (false activo - true inactivo)
		List<Piso> pisosActivos = pisoService.findByActivo(false);
	
		if(fechaIni==null && fechaFin==null) {
			for (Piso piso : pisosActivos) {
				if(piso.getPersonas()>=ocupacion|| ocupacion==0) {
					if (!(piso.getFotos().isEmpty())) {
						pisosDisponibles.add(piso);
					}
				}
			}	
		}else {
			for (Piso piso : pisosActivos) {
				if(disponible(fechaIni,fechaFin,piso.getId())) {
					if(piso.getPersonas()>=ocupacion|| ocupacion==0) {
						if (!(piso.getFotos().isEmpty())) {
							pisosDisponibles.add(piso);
						}
						
					}
				}
			}
		}
		return pisosDisponibles;
	}
	
	
	public boolean disponible(Date fechaInicial, Date fechaFinal, long id) throws ParseException {

		Piso piso = pisoService.findOne(id);
		List<Reserva> todasReservas = pisoService.findReservaByPiso(piso);

		for (Reserva actual : todasReservas) {
			if (fechaInicial.after(actual.getFechaInicio()) && fechaInicial.before(actual.getFechaFin())) {
				return false;
			}
			if (fechaFinal.after(actual.getFechaInicio()) && fechaFinal.before(actual.getFechaFin())) {
				return false;
			}
			if (fechaInicial.before(actual.getFechaInicio()) && fechaFinal.after(actual.getFechaFin())) {
				return false;
			}
		}
		return true;
	}
	
}
