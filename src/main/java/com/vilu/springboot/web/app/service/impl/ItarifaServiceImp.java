package com.vilu.springboot.web.app.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Tarifa;
import com.vilu.springboot.web.app.pojo.ItemTarifa;
import com.vilu.springboot.web.app.service.IPisoService;
import com.vilu.springboot.web.app.service.ITarifaService;

@Service
public class ItarifaServiceImp implements ITarifaService {
	
	@Autowired
	private IPisoService pisoService;

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
	 * Conversion de una tarifa a un itemTarifa
	 * @author Epico
	 *
	 */
	public ItemTarifa tarifaItemTarifa(Tarifa tarifa) {
		 ItemTarifa itemTarifa = new ItemTarifa(tarifa.getId(), 
												tarifa.getDetalle(), 
												tarifa.getFechaInicio(), 
												tarifa.getFechaFin(), 
												tarifa.getTarifaBase(), 
												tarifa.getTarifaNueva());
	return itemTarifa;
		}

	/**
	 * Conversion de una ista de tarifas a una lista de itemTarifas
	 * @author Epico
	 *
	 */
	public List<ItemTarifa> listaTarifaListaItemTarifa(List<Tarifa> tarifas) {
		List<ItemTarifa> itemTarifas = new ArrayList<ItemTarifa>();
		
		for(Tarifa tarifa: tarifas) {
			itemTarifas.add(tarifaItemTarifa(tarifa));
		}
		
		return itemTarifas;
	}
	
	
		
	public int nochesReserva (Date fechaIniDate, Date fechaFinDate) {
		
		int dias = (int) ((fechaFinDate.getTime() - fechaIniDate.getTime()) / 86400000);
		return dias;
	}

}
