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
@Table(name = "fotos")
public class Foto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String detalle;
	
	private String fotoImg;
	
	@ManyToOne (fetch=FetchType.LAZY)
	private Piso piso;
	
	
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


	public String getFotoImg() {
		return fotoImg;
	}

	public void setFotoImg(String fotoImg) {
		this.fotoImg = fotoImg;
	}

	public Piso getPiso() {
		return piso;
	}
	
	public void setPiso(Piso piso) {
		this.piso = piso;
	}

	
	
	private static final long serialVersionUID = 1L;
}
