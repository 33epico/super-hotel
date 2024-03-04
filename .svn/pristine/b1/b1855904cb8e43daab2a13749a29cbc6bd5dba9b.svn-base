package com.vilu.springboot.web.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vilu.springboot.web.app.dao.IUsuarioDao;
import com.vilu.springboot.web.app.entity.Role;
import com.vilu.springboot.web.app.entity.Usuario;


@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private IUsuarioDao usuarioDao;
	
	private Logger logger=LoggerFactory.getLogger(JpaUserDetailsService.class);
	

	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioDao.findUsuarioByUsername(username);
				
		
		if(usuario == null) {
			logger.error("Error Loging: el usuario '"+username+"' no existe");
			throw new UsernameNotFoundException("Usuario no existe");
			
		}
		
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(Role role:usuario.getRoles()){
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		if(authorities.isEmpty()) {
			logger.error("Error Loging: el usuario '"+username+"' no tiene roles asociados");
			throw new UsernameNotFoundException("Usuario no tiene roles");
			
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), authorities);
	}

}
