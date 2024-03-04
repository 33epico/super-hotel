package com.vilu.springboot.web.app.dao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.vilu.springboot.web.app.entity.Foto;
import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Usuario;


public interface IPisoDao extends PagingAndSortingRepository<Piso, Long>{
	
	public Page<Piso> findByActivo(Boolean term, Pageable pageable);
	
	public List<Piso> findByActivo(Boolean term);


	@Query("select c from Piso c left join fetch c.fotos f where c.id = ?1")
	public Piso fetchByIdWithFotos(Long id);
	

	public Page<Piso> findByUsuario (Usuario usuario, Pageable pageable);
	
	public Page<Piso> findByUsuarioAndActivo (Usuario usuario,Boolean term, Pageable pageable);
	
	
}
