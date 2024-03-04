package com.vilu.springboot.web.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vilu.springboot.web.app.dao.IMensajesDao;
import com.vilu.springboot.web.app.entity.Foto;
import com.vilu.springboot.web.app.entity.Mensajes;
import com.vilu.springboot.web.app.service.IMensajesService;

@Service
public class MensajesServiceImpl implements IMensajesService {

	@Autowired
	private IMensajesDao mensajesDao;

	@Override
	public Page<Mensajes> findByNombreUsuarioAndLeido(String nombreUsuario, Boolean term, Pageable pageable) {
		return mensajesDao.findByNombreUsuarioAndLeido(nombreUsuario, term, pageable);
	}

	@Override
	public Mensajes findOne(Long id) {
		return mensajesDao.findById(id).orElse(null);
	}

	@Override
	public void save(Mensajes mensaje) {
		mensajesDao.save(mensaje);
	}

	@Override
	public List<Mensajes> findByNombreUsuarioAndLeido(String nombreUsuario, Boolean term) {
		return  (List<Mensajes>)  mensajesDao.findByNombreUsuarioAndLeido(nombreUsuario, term);
	}
	
}
