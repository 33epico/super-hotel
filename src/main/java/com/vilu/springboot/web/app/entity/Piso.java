package com.vilu.springboot.web.app.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pisos")
public class Piso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombre;

	@NotEmpty
	private String calle;

	@NotEmpty
	private String pisoAltura;
	
	@NotNull
	private long tarifaBase;
	
	@NotEmpty
	private String habitaciones;

	@NotEmpty
	@Size(min = 2, max = 4)
	private String numero;
	
	@NotEmpty
	private String coordenadaX;
	
	@NotEmpty
	private String coordenadaY;

	private String poblacion;

	private Boolean activo;
	
	private String motivo;
	
	private String detalle;
	
	private String detalleEN;
	
	private String detalleFR;
	
	private String detalleDE;
	
	@NotNull
	private int personas;
	
	private int servicioLimpieza;

	@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date createAt;
	
	//Creamos un objeto de colección list ya que un piso puede tener muchas fotos
	@OneToMany (mappedBy="piso" ,cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Foto> fotos;
	
	//Creamos un objeto de colección list ya que un piso puede tener muchas fotos
	@OneToMany (mappedBy="piso" ,fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Tarifa> tarifas;

	//Con esto indicamos que muchos pisos solo tienen un usuario y con el fetch lazy evitamos traer todo de golpe
	@ManyToOne
	private Usuario usuario;
	
	@OneToMany (mappedBy="piso" ,fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Delegado> delegado;

	//Creamos un objeto de colección list ya que un piso puede tener muchas fotos
	@OneToMany (mappedBy="piso" ,fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Servicios> servicios;
	
	
	public String getMotivo() {
		return motivo;
	}

	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public int getPersonas() {
		return personas;
	}

	public void setPersonas(int personas) {
		this.personas = personas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Piso() {
		super();
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getPisoAltura() {
		return pisoAltura;
	}

	public void setPisoAltura(String pisoAltura) {
		this.pisoAltura = pisoAltura;
	}
	
	
	public long getTarifaBase() {
		return tarifaBase;
	}

	public void setTarifaBase(long tarifaBase) {
		this.tarifaBase = tarifaBase;
	}



	public String getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(String habitaciones) {
		this.habitaciones = habitaciones;
	}
	
	public List<Tarifa> getTarifas() {
		return tarifas;
	}

	

	public String getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(String coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public String getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(String coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public void setTarifas(List<Tarifa> tarifas) {
		this.tarifas = tarifas;
	}

	public String getDetalleEN() {
		return detalleEN;
	}

	public void setDetalleEN(String detalleEN) {
		this.detalleEN = detalleEN;
	}

	public String getDetalleFR() {
		return detalleFR;
	}

	public void setDetalleFR(String detalleFR) {
		this.detalleFR = detalleFR;
	}

	public String getDetalleDE() {
		return detalleDE;
	}

	public void setDetalleDE(String detalleDE) {
		this.detalleDE = detalleDE;
	}
	
	public List<Servicios> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicios> servicios) {
		this.servicios = servicios;
	}

	public int getServicioLimpieza() {
		return servicioLimpieza;
	}

	public void setServicioLimpieza(int servicioLimpieza) {
		this.servicioLimpieza = servicioLimpieza;
	}



	private static final long serialVersionUID = 1L;

}
