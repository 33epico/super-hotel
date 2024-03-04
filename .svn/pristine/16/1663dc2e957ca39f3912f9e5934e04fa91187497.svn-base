package com.vilu.springboot.web.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vilu.springboot.web.app.entity.Role;
import com.vilu.springboot.web.app.entity.Usuario;
import com.vilu.springboot.web.app.pojo.ItemRoles;
import com.vilu.springboot.web.app.service.IPisoService;
import com.vilu.springboot.web.app.service.IUtilUsrService;

@Service
public class UsrUtilsImpl implements IUtilUsrService{
	
	@Autowired
	private IPisoService usuarioService;
	
	/**
	 * Actualizar roles, se envia el nombre de usuario y una lista de los posibles Roles y un on off para ver si esta activo o no
	 */
	public void  actualizarRoles(List<String> rolesArr, String username) {
		Usuario usuario = usuarioService.findUsuarioByUsername(username);
		List<Role> roles =  usuario.getRoles();
		for(Role rol: roles) {
			usuarioService.deleteRole((long) rol.getId());
		}
		
		for(String authorityString: rolesArr) {
			Role role = new Role();
			role.setAuthority(authorityString);
			role.setUser_id(usuarioService.findUsuarioByUsername(username).getId());
			usuarioService.saveRole(role);
		}
	}
	
	/**
	 * Devuelve una lista de itemRole con los roles de los usuarios
	 */
	public List<ItemRoles>  listaRolesUsr() {
		List<Usuario> usuarios =  usuarioService.findAllUsuario();
		List<ItemRoles> itemRolesArr = new ArrayList<ItemRoles>();
		
		for(Usuario usuario: usuarios) {
			ItemRoles itemRoles = new ItemRoles();
			itemRoles.setUsuario(usuario.getUsername());
			
			List<Role> roles =  usuario.getRoles();
			List<String> authorityList = new ArrayList<String>(); 
			
			for (Role rol: roles) {
				authorityList.add(rol.getAuthority());
			}
					
			if(authorityList.contains("ROLE_GESTOR")) {
				itemRoles.setGestor("true");
			}else {
				itemRoles.setGestor("false");
			}
			if(authorityList.contains("ROLE_ADMIN")) {
				itemRoles.setAdmin("true");
			}else {
				itemRoles.setAdmin("false");
			}
			if(authorityList.contains("ROLE_CLIENTE")) {
				itemRoles.setCliente("true");
			}else {
				itemRoles.setCliente("false");
			}
			if(authorityList.contains("ROLE_SERVICIO")) {
				itemRoles.setServicio("true");
			}else {
				itemRoles.setServicio("false");
			}
			itemRolesArr.add(itemRoles);
			
		}
		
		return itemRolesArr;
	}
	
	}


