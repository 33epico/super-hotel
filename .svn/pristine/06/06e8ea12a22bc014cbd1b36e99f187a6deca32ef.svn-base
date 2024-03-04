package com.vilu.springboot.web.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "promociones")
public class Promociones {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String codigoPromocion;
	
	@NotNull
	private String textoPromocion;
	
	@NotNull
	private String textoPromocionEN;
	
	@NotNull
	private String textoPromocionFR;
	
	@NotNull
	private String textoPromocionDE;
	
	private Long valorDescuento;
	
	private Long numeroDeUsos;
	
	@NotNull
	private String basesLegales;
	
	@NotNull
	private String basesLegalesEN;
	
	@NotNull
	private String basesLegalesFR;
	
	@NotNull
	private String basesLegalesDE;
	
	private Boolean activo = false;
	
	
	@NotNull
	@Column(name = "fecha_inicio")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	
	@NotNull
	@Column(name = "fecha_fin")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoPromocion() {
		return codigoPromocion;
	}

	public void setCodigoPromocion(String codigoPromocion) {
		this.codigoPromocion = codigoPromocion;
	}

	public String getTextoPromocion() {
		return textoPromocion;
	}

	public void setTextoPromocion(String textoPromocion) {
		this.textoPromocion = textoPromocion;
	}

	public Long getValorDescuento() {
		return valorDescuento;
	}

	public void setValorDescuento(Long valorDescuento) {
		this.valorDescuento = valorDescuento;
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

	public Long getNumeroDeUsos() {
		return numeroDeUsos;
	}

	public void setNumeroDeUsos(Long numeroDeUsos) {
		this.numeroDeUsos = numeroDeUsos;
	}
	
	public String getBasesLegales() {
		return basesLegales;
	}

	public void setBasesLegales(String basesLegales) {
		this.basesLegales = basesLegales;
	}
	
	
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	

	public String getTextoPromocionEN() {
		return textoPromocionEN;
	}

	public void setTextoPromocionEN(String textoPromocionEN) {
		this.textoPromocionEN = textoPromocionEN;
	}

	public String getTextoPromocionFR() {
		return textoPromocionFR;
	}

	public void setTextoPromocionFR(String textoPromocionFR) {
		this.textoPromocionFR = textoPromocionFR;
	}

	public String getTextoPromocionDE() {
		return textoPromocionDE;
	}

	public void setTextoPromocionDE(String textoPromocionDE) {
		this.textoPromocionDE = textoPromocionDE;
	}

	public String getBasesLegalesEN() {
		return basesLegalesEN;
	}

	public void setBasesLegalesEN(String basesLegalesEN) {
		this.basesLegalesEN = basesLegalesEN;
	}

	public String getBasesLegalesFR() {
		return basesLegalesFR;
	}

	public void setBasesLegalesFR(String basesLegalesFR) {
		this.basesLegalesFR = basesLegalesFR;
	}

	public String getBasesLegalesDE() {
		return basesLegalesDE;
	}

	public void setBasesLegalesDE(String basesLegalesDE) {
		this.basesLegalesDE = basesLegalesDE;
	}

	public String fechaActivaInactiva(Date fechaConsulta) {
		String estado ="ERROR";
		
		if (this.fechaFin.compareTo(fechaConsulta) < 0) {
			estado= "FUERA";
		}
		if (this.fechaInicio.compareTo(fechaConsulta) > 0) {
			estado= "PENDIENTE";
		}
		if (this.fechaFin.compareTo(fechaConsulta) > 0 && this.fechaInicio.compareTo(fechaConsulta) < 0) {
			estado= "DENTRO";
		}
		
		return estado;
	}
	
	
	private static final long serialVersionUID = 1L;
	
}
