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
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reservas")
public class Reserva {
	
	public static String HORA_IN = " 13:00:00";
	public static String HORA_OUT = " 12:00:00";
	public static String PETICION_PENDIENTE="PENDIENTE";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String codigoReserva;
	
	@NotNull
	@Column(name = "fecha_inicio")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	
	@NotNull
	@Column(name = "fecha_fin")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;
	
	//Es el precio total base calculado a base de tarifas y fechas
	@NotNull
	private float precioCalculado;
	
	private float ivaCalculado;
	
	private float descuentoCalculado;
	
	//El precio con impuestos y descuentos aplicados
	private float precioPagar;
	
	//0 Creada y no pagada
	//1 Creada y pagada
	//2 Finalizada
	//3 cancelada
	@NotNull
	private long consolidada;
	
	@NotNull
	private float descuento;
	
	private String codigoPromo;
	
	@NotNull
	private boolean activa;
	
	@NotNull
	@ManyToOne (fetch=FetchType.LAZY)
	private Piso piso;

	@NotNull
	@ManyToOne (fetch=FetchType.LAZY)
	private Usuario usuario;
	
	private String mensaje;
	
	private int servicioLimpieza; 
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getCodigoReserva() {
		return codigoReserva;
	}



	public void setCodigoReserva(String codigoReserva) {
		this.codigoReserva = codigoReserva;
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



	public long getConsolidada() {
		return consolidada;
	}



	public void setConsolidada(long consolidada) {
		this.consolidada = consolidada;
	}



	public float getDescuento() {
		return descuento;
	}



	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}



	public String getCodigoPromo() {
		return codigoPromo;
	}



	public void setCodigoPromo(String codigoPromo) {
		this.codigoPromo = codigoPromo;
	}



	public boolean isActiva() {
		return activa;
	}



	public void setActiva(boolean activa) {
		this.activa = activa;
	}



	public Piso getPiso() {
		return piso;
	}



	public void setPiso(Piso piso) {
		this.piso = piso;
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	

	public int getServicioLimpieza() {
		return servicioLimpieza;
	}



	public void setServicioLimpieza(int servicioLimpieza) {
		this.servicioLimpieza = servicioLimpieza;
	}



	public String getMensaje() {
		return mensaje;
	}



	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}



	private static final long serialVersionUID = 1L;


}
