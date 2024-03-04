package com.vilu.springboot.web.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vilu.springboot.web.app.entity.Mensajes;

public interface IMensajesService {

	public Page<Mensajes> findByNombreUsuarioAndLeido (String nombreUsuario,Boolean term, Pageable pageable);
	
	public List<Mensajes> findByNombreUsuarioAndLeido (String nombreUsuario,Boolean term);
	
	public Mensajes findOne(Long id);
	
	public void save(Mensajes mensaje);
}
