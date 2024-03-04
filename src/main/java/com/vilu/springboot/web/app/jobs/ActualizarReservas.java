package com.vilu.springboot.web.app.jobs;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vilu.springboot.web.app.entity.Reserva;
import com.vilu.springboot.web.app.service.IPisoService;
import com.vilu.springboot.web.app.utils.ReservaUtils;

@Service
public class ActualizarReservas {
	
	@Autowired
	private ReservaUtils reservaUtils;
	
	@Autowired
	private IPisoService pisoService;
	
	private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	
	    public void ActualizarReservas() {

	    	java.util.Date hoy = new Date();

	    	List<Reserva> reservasActivas = pisoService.findReservaByActivaAndConsolidada(true, 1);
	    	for (Reserva reserva: reservasActivas) {
	    		if (hoy.after(reserva.getFechaFin())) {
	    			reserva.setActiva(false);
	    			reserva.setConsolidada(2);
	    			pisoService.saveReserva(reserva);
	    		}
	    	}
	    }

}
