package com.vilu.springboot.web.app.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Reserva;
import com.vilu.springboot.web.app.entity.Usuario;

public interface IReservaDao  extends PagingAndSortingRepository<Reserva, Long> {
	
	public List<Reserva> findReservaByPiso (Piso piso);
	
	public List<Reserva> findReservaByActivaAndConsolidada(boolean activa,long consolidada); 
	
	public List<Reserva> findReservaByPisoAndActivaAndConsolidada(Piso piso, boolean activa,long consolidada); 
	
	public List<Reserva> findReservaByUsuario (Usuario usuario);
	
	public Reserva findReservaBycodigoReserva (String codigReserva);
	
}
