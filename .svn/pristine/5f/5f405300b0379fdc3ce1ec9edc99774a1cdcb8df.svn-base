package com.vilu.springboot.web.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Servicios;


public interface IServiciosDao  extends CrudRepository<Servicios, Long> {
	
	public List<Servicios> findServiciosByPisoOrderByIdAsc (Piso piso);
	
}
