package com.vilu.springboot.web.app;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.vilu.springboot.web.app.service.IUploadFileService;
import com.vilu.springboot.web.app.utils.ReservaUtils;
import com.vilu.springboot.web.app.jobs.*;

@SpringBootApplication
@EnableScheduling
public class ViluApplication implements CommandLineRunner{

	private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IUploadFileService uploadFileService;
	
	@Autowired
	private ActualizarReservas actualizarReservas;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	
	public static void main(String[] args) {
		SpringApplication.run(ViluApplication.class, args);
	}

	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
	}
	
	@Scheduled(cron = "0 0 12 ? * 1,2,3,4,5,6,7 ")
	    public void ActualizarReservaLanzador() {
		 actualizarReservas.ActualizarReservas();
		 log.error("[INFO] Se ha ejecutado el job");	
	 }
	

}
