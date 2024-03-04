package com.vilu.springboot.web.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.vilu.springboot.web.app.dao.IConfiguracionDao;
import com.vilu.springboot.web.app.entity.Configuracion;
import com.vilu.springboot.web.app.service.IConfiguracionService;

@Service
public class ConfiguracionServiceImpl implements IConfiguracionService {

	
	@Autowired
	private IConfiguracionDao configuracionDao;
	
	public Configuracion findByClave(String clave) {
		return configuracionDao.findByClave(clave);
	}
	
	public List<Configuracion> findByGrupo (String grupo){
		return (List<Configuracion>)  configuracionDao.findByGrupo(grupo);	
	}

	public void save(Configuracion configuracion) {
		configuracionDao.save(configuracion);	
	}

	public String findValorByClave(String clave) {
		return (configuracionDao.findByClave(clave)).getValor();
	}

	public Model configuracion(Model model) {

		List<Configuracion> aspecto = configuracionDao.findByGrupo("personaliza");
		for(Configuracion configuracion: aspecto) {
			model.addAttribute(configuracion.getClave(), configuracion.getValor());
		}
		return model;
	}

	public Model norma(Model model) {

		List<Configuracion> normas = configuracionDao.findByGrupo("normas");
		for(Configuracion configuracion: normas) {
			model.addAttribute(configuracion.getClave(), configuracion.getValor());
		}
		return model;
	}
	
	
}
