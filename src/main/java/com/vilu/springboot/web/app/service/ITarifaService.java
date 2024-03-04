package com.vilu.springboot.web.app.service;

import java.util.Date;
import java.util.List;

import com.vilu.springboot.web.app.entity.Tarifa;
import com.vilu.springboot.web.app.pojo.ItemTarifa;


public interface ITarifaService {

	public long calculoPrecio(Date fechaInicial, Date fechaFinal, long pisoId);
	
	public int nochesReserva (Date fechaIniDate, Date fechaFinDate) ;
	
	public ItemTarifa tarifaItemTarifa(Tarifa tarifa) ;
	
	public List<ItemTarifa> listaTarifaListaItemTarifa(List<Tarifa> tarifas);
}
