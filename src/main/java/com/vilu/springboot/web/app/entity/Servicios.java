package com.vilu.springboot.web.app.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "servicios")
public class Servicios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nombreServicio;
	
	@NotEmpty
	private String nombreServicioEN;
	
	@NotEmpty
	private String nombreServicioFR;
	
	@NotEmpty
	private String nombreServicioDE;
	
	@NotEmpty
	private String icono;
	
	@NotEmpty
	private String descripcion;
	
	@NotEmpty
	private String descripcionEN;
	
	@NotEmpty
	private String descripcionFR;
	
	@NotEmpty
	private String descripcionDE;
	
	@ManyToOne (fetch=FetchType.LAZY)
	private Piso piso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Piso getPiso() {
		return piso;
	}

	public void setPiso(Piso piso) {
		this.piso = piso;
	}
	
	
	
	public String getNombreServicioEN() {
		return nombreServicioEN;
	}

	public void setNombreServicioEN(String nombreServicioEN) {
		this.nombreServicioEN = nombreServicioEN;
	}

	public String getNombreServicioFR() {
		return nombreServicioFR;
	}

	public void setNombreServicioFR(String nombreServicioFR) {
		this.nombreServicioFR = nombreServicioFR;
	}

	public String getNombreServicioDE() {
		return nombreServicioDE;
	}

	public void setNombreServicioDE(String nombreServicioDE) {
		this.nombreServicioDE = nombreServicioDE;
	}

	public String getDescripcionEN() {
		return descripcionEN;
	}

	public void setDescripcionEN(String descripcionEN) {
		this.descripcionEN = descripcionEN;
	}

	public String getDescripcionFR() {
		return descripcionFR;
	}

	public void setDescripcionFR(String descripcionFR) {
		this.descripcionFR = descripcionFR;
	}

	public String getDescripcionDE() {
		return descripcionDE;
	}

	public void setDescripcionDE(String descripcionDE) {
		this.descripcionDE = descripcionDE;
	}



	private static final long serialVersionUID = 1L;
	

	
}
