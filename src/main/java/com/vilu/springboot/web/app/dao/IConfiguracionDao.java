package com.vilu.springboot.web.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.vilu.springboot.web.app.entity.Configuracion;

public interface IConfiguracionDao extends CrudRepository<Configuracion, Long> {
	
	public Configuracion findByClave (String clave);
	
	public String findValorByClave (String clave);
	
	public List<Configuracion> findByGrupo (String grupo);
}

