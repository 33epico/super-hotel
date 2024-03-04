package com.vilu.springboot.web.app.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.vilu.springboot.web.app.entity.Mensajes;
import com.vilu.springboot.web.app.entity.Piso;

public interface IMensajesDao extends PagingAndSortingRepository<Mensajes, Long> {

	public Page<Mensajes> findByNombreUsuarioAndLeido (String nombreUsuario,Boolean term, Pageable pageable);
	
	public List<Mensajes> findByNombreUsuarioAndLeido(String nombreUsuario, Boolean term);


}
