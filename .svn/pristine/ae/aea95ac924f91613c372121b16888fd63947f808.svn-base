package com.vilu.springboot.web.app.controller;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vilu.springboot.web.app.service.IUploadFileService;

@Controller
public class UploadsController {

	@Autowired
	private IUploadFileService uploadFileservice;
	
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			
			//Pasamos el nombre del archivo
			recurso = uploadFileservice.load(filename);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Metemos el recurso en la cabecera http
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachmen; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
}
