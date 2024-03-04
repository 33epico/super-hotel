package com.vilu.springboot.web.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vilu.springboot.web.app.pojo.ItemPiso;
import com.vilu.springboot.web.app.service.IPisoService;
import com.vilu.springboot.web.app.service.impl.UtilsServiceImpl;

@RestController
@RequestMapping("/api") //esta sera la raiz de la url, es decir http://127.0.0.1:8080/api/
public class pisoRest {

	
	@Autowired
	private UtilsServiceImpl utilsServiceImpl;
	
	@Autowired
	private IPisoService pisoService;
	
	@GetMapping("/pisos")
	public List<ItemPiso> findAll(){
		return utilsServiceImpl.listaItemPisoDePiso(pisoService.findAll());
	}

}
