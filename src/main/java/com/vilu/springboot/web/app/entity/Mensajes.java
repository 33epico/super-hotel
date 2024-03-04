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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "mensajes")
public class Mensajes {
	
	public static long NO_LEIDO = 0;
	
	public static long LEIDO = 1;
	
	public static String PISO = "PISO";
	
	public static String USUARIO = "USUARIO";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String nombreUsuario;
	
	@NotNull
	@ManyToOne (fetch=FetchType.LAZY)
	private Piso piso;

	private String asunto; 
	
	@NotNull
	private String mensaje;

	@NotNull
	private boolean leido;
	
	@NotNull
	private String usuarioPiso;

	@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
	private Date createAt;
	
	@NotNull
	private String enviadoPor;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	

	public Piso getPiso() {
		return piso;
	}

	public void setPiso(Piso piso) {
		this.piso = piso;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}


	public String getNombreUsuario() {
		return this.piso.getUsuario().getUsername()+'-'+this.piso.getNombre();
	}


	public String getUsuarioPiso() {
		return usuarioPiso;
	}

	public void setUsuarioPiso(String usuarioPiso) {
		this.usuarioPiso = usuarioPiso;
	}






	private static final long serialVersionUID = 1L;

}
