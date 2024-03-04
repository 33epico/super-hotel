package com.vilu.springboot.web.app.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ItemTarifa {
	

	private Long id;
	
	private String detalle;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;
	
	private long tarifaBase;

	private long tarifaNueva;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public long getTarifaBase() {
		return tarifaBase;
	}

	public void setTarifaBase(long tarifaBase) {
		this.tarifaBase = tarifaBase;
	}

	public long getTarifaNueva() {
		return tarifaNueva;
	}

	public void setTarifaNueva(long tarifaNueva) {
		this.tarifaNueva = tarifaNueva;
	}

	public ItemTarifa(Long id, String detalle, Date fechaInicio, Date fechaFin, long tarifaBase, long tarifaNueva) {
		super();
		this.id = id;
		this.detalle = detalle;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tarifaBase = tarifaBase;
		this.tarifaNueva = tarifaNueva;
	}

}
