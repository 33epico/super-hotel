package com.vilu.springboot.web.app.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.vilu.springboot.web.app.entity.Usuario;


public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long>{
	
	public Usuario findUsuarioByUsername(String username);
		
	public Usuario findByKeynopwd(String Keynopwd);

}
