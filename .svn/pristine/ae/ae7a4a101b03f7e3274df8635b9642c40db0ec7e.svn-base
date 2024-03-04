package com.vilu.springboot.web.app.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, length = 45)
	@NotEmpty
	private String username;

	@Column(length = 60)
	private String password;
	
	private String keynopwd;

	private int enabled;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public List<Role> roles;

	@OneToOne(mappedBy = "usuario")
	private UsrPerfil usrPerfil;
	
	public List<Piso> getPisos() {
		return pisos;
	}

	public void setPisos(List<Piso> pisos) {
		this.pisos = pisos;
	}

	//@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JoinColumn(name = "user_id")
	//private List<Piso> pisos ;

	@OneToMany (mappedBy="usuario" ,fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Piso> pisos;
	
	@OneToMany (mappedBy="usuario" ,fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Reserva> reservas;
	
	
	public UsrPerfil getUsrPerfil() {
		return usrPerfil;
	}

	public void setUsrPerfil(UsrPerfil usrPerfil) {
		this.usrPerfil = usrPerfil;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	public String getKeynopwd() {
		return keynopwd;
	}

	public void setKeynopwd(String keynopwd) {
		this.keynopwd = keynopwd;
	}

	private static final long serialVersionUID = 1L;

}
