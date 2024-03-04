package com.vilu.springboot.web.app.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tarifas")
public class Tarifa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String detalle;
	
	@NotNull
	@Column(name = "fecha_inicio")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	
	@NotNull
	@Column(name = "fecha_fin")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;
	
	@NotNull
	private long tarifaBase;

	@NotNull
	private long tarifaNueva;
	
	@NotNull
	private boolean activa;
	
	public long getTarifaNueva() {
		return tarifaNueva;
	}

	public void setTarifaNueva(long tarifaNueva) {
		this.tarifaNueva = tarifaNueva;
	}


	@NotNull
	@ManyToOne (fetch=FetchType.LAZY)
	private Piso piso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public long getTarifaBase() {
		return tarifaBase;
	}


	public void setTarifaBase(long tarifaBase) {
		this.tarifaBase = tarifaBase;
	}




	public Piso getPiso() {
		return piso;
	}

	public void setPiso(Piso piso) {
		this.piso = piso;
	}
	
	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	

	private static final long serialVersionUID = 1L;


}
