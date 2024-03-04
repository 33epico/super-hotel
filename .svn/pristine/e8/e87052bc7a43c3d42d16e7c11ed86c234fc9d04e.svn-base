package com.vilu.springboot.web.app.dao;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Tarifa;

public interface ITarifaDao extends PagingAndSortingRepository<Tarifa, Long> {
	
	List <Tarifa> findByPisoOrderByFechaInicioDesc(Piso piso);
	
}
