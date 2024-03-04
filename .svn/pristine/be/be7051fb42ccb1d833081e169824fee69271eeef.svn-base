package com.vilu.springboot.web.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vilu.springboot.web.app.dao.IPerfilUsrDao;
import com.vilu.springboot.web.app.dao.IRoleDao;
import com.vilu.springboot.web.app.dao.IUsuarioDao;
import com.vilu.springboot.web.app.entity.Role;
import com.vilu.springboot.web.app.entity.UsrPerfil;
import com.vilu.springboot.web.app.entity.Usuario;
import com.vilu.springboot.web.app.service.IUsuarioService;

@Service
public class IUsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao usuarioDao;

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IPerfilUsrDao perfilUsrDao;

	//
	public Usuario findUsuarioById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Transactional
	public void deleteRole(Long id) {
		roleDao.deleteById(id);
	}

	// Usuarios y roles

	public List<Usuario> findByUsers() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Transactional(readOnly = true)
	public Page<Usuario> findAllUsuario(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}

	public Usuario findByKeynopwd(String Keynopwd) {
		return usuarioDao.findByKeynopwd(Keynopwd);
	}

	@Transactional
	public void saveUsuario(Usuario usuario) {
		usuarioDao.save(usuario);
	}

	@Transactional
	public void saveRole(Role role) {
		roleDao.save(role);
	}

	@Transactional
	public void saveUsrPerfil(UsrPerfil usrPerfil) {
		perfilUsrDao.save(usrPerfil);
	}

	public Usuario findUsuarioByUsername(String username) {
		return usuarioDao.findUsuarioByUsername(username);
	}

	public UsrPerfil findByUsuario(Usuario usuario) {
		return perfilUsrDao.findByUsuario(usuario);
	}

	public List<Usuario> findAllUsuario() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	public Optional<Role> findRoleById(Long id) {
		return roleDao.findById(id);
	}
	
	public String obtenerKey() {
		return (((UUID.randomUUID().toString()).toUpperCase()).substring(0, 20));
	}

}
