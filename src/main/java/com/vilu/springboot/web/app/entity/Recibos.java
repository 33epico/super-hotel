package com.vilu.springboot.web.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "recibos")
public class Recibos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String codigo;
	
	@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date emision;
	
	@NotNull
	private float precioCalculado;
	
	@NotNull
	private float ivaCalculado;
	
	private float descuentoCalculado;
	
	//El precio con impuestos y descuentos aplicados
	@NotNull
	private float precioPagar;
	
	@NotNull
	private String detalle;
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getCodigo() {
		return codigo;
	}



	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}



	public Date getEmision() {
		return emision;
	}



	public void setEmision(Date emision) {
		this.emision = emision;
	}



	public float getPrecioCalculado() {
		return precioCalculado;
	}



	public void setPrecioCalculado(float precioCalculado) {
		this.precioCalculado = precioCalculado;
	}



	public float getIvaCalculado() {
		return ivaCalculado;
	}



	public void setIvaCalculado(float ivaCalculado) {
		this.ivaCalculado = ivaCalculado;
	}



	public float getDescuentoCalculado() {
		return descuentoCalculado;
	}



	public void setDescuentoCalculado(float descuentoCalculado) {
		this.descuentoCalculado = descuentoCalculado;
	}



	public float getPrecioPagar() {
		return precioPagar;
	}



	public void setPrecioPagar(float precioPagar) {
		this.precioPagar = precioPagar;
	}




	public Recibos(@NotNull String codigo, @NotNull float precioCalculado,
			@NotNull float ivaCalculado, float descuentoCalculado, @NotNull float precioPagar,
			@NotNull String detalle) {
		super();
		this.codigo = codigo;
		this.precioCalculado = precioCalculado;
		this.ivaCalculado = ivaCalculado;
		this.descuentoCalculado = descuentoCalculado;
		this.precioPagar = precioPagar;
		this.detalle = detalle;
	}



	public String getDetalle() {
		return detalle;
	}



	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}



	private static final long serialVersionUID = 1L;
}
