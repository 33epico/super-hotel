package com.vilu.springboot.web.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vilu.springboot.web.app.entity.Role;
import com.vilu.springboot.web.app.entity.UsrPerfil;
import com.vilu.springboot.web.app.entity.Usuario;

public interface IUsuarioService {
	
	//Servicios para el rol
	public void saveRole(Role role);
	
	public void deleteRole (Long id);
	
	public Optional<Role> findRoleById (Long id);
	
	//Servicios para el usuario
	public void saveUsuario(Usuario usuario);
	
	public Usuario findUsuarioByUsername(String username);
	
	public Usuario findUsuarioById (Long id);
	
	public List<Usuario> findByUsers ();

	public void saveUsrPerfil(UsrPerfil usrPerfil);
	
	public UsrPerfil findByUsuario (Usuario usuario);
	
	public Page<Usuario> findAllUsuario(Pageable pageable);
	
	public List<Usuario> findAllUsuario();

	public String obtenerKey();

}
