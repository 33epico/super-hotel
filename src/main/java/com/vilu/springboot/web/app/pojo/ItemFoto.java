package com.vilu.springboot.web.app.pojo;



public class ItemFoto {

	private Long id;
	
	private String detalle;
	
	private String fotoImg;

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

	public ItemFoto() {
		super();
	}

	public ItemFoto(Long id, String detalle, String fotoImg) {
		super();
		this.id = id;
		this.detalle = detalle;
		this.fotoImg = fotoImg;
	}
	

}
