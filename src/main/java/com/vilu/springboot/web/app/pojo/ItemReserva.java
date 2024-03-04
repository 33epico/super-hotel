package com.vilu.springboot.web.app.pojo;

public class ItemReserva {
	
	String fechaFin;
	String fechaIni;
	Long idPiso;
	String descuento;
	double preciototal;
	
	public ItemReserva() {};
	
	public ItemReserva(String fechaFin, String fechaIni, Long idPiso, String descuento,double preciototal) {
		super();
		this.fechaFin = fechaFin;
		this.fechaIni = fechaIni;
		this.idPiso = idPiso;
		this.descuento = descuento;
		this.preciototal = preciototal;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}
	public Long getIdPiso() {
		return idPiso;
	}
	public void setIdPiso(Long idPiso) {
		this.idPiso = idPiso;
	}
	public String getDescuento() {
		return descuento;
	}
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}

	public double getPreciototal() {
		return preciototal;
	}
	public void setPreciototal(double preciototal) {
		this.preciototal = preciototal;
	}
	


}


