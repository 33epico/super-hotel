package com.vilu.springboot.web.app.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.vilu.springboot.web.app.entity.Promociones;


public interface IPromocionesDao extends PagingAndSortingRepository<Promociones, Long> {

	public Promociones findPromocionesBycodigoPromocion(String codigoPromocion);
	

}
