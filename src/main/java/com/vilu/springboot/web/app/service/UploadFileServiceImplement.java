package com.vilu.springboot.web.app.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImplement implements IUploadFileService {

	
	private final Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

	private final static String UPLOAD_FOLDER = "uploads";
	
	
	/*
	 
	Recurso Load con nombre de archivo
	 
	 */
	public Resource load(String filename) throws MalformedURLException {

		//Obtenemos el nombre de la ruta del archivo
		Path pathFoto = getPath(filename);

		log.info("PathFoto: " + pathFoto);
		
		//Inicializamos el recurso
		Resource recurso = null;

		//obtenemos el recurso pasando el path y convertido a uri
		recurso = new UrlResource(pathFoto.toUri());
		//Si el recurso (foto) tiene errores no hace nada
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la foto " + pathFoto.toString());
		}

		//Devolvemos el recurso
		return recurso;
	}
	
	/*
	 
	Recurso copy pasando el archivo como parametro
	 
	 */
	public String copy(MultipartFile file) throws IOException {

		
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		// Esta es la ruta relativa
		Path rootPath = getPath(uniqueFilename);

		log.info("rootPath: " + rootPath);

		// Con esto copiamos el archivo a la ruta mapeada en rootabsolutePath
		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;

	}

	/*
	 
	Recurso delete pasando el nombre del archivo como parametro
	 
	 */
	public boolean delete(String Filename) {
		
		Path rootPath = getPath(Filename);
		
		File archivo = rootPath.toFile();

		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
			return true;
			}
		}

		return false;
	}

	
	public Path getPath(String filename) {

		return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();

	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOAD_FOLDER).toFile());
	}

	public void init() throws IOException {
	 Files.createDirectory(Paths.get(UPLOAD_FOLDER));
		
	}
}


