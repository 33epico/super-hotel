package com.vilu.springboot.web.app.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name ="authority", uniqueConstraints= {@UniqueConstraint(columnNames= {"user_id","authority"})})
public class Role implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	public String authority;
	
	private Long user_id;
	

	public Long getUser_id() {
		return user_id;
	}


	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getAuthority() {
		return authority;
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}




}
