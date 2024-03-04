package com.vilu.springboot.web.app.dao;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.vilu.springboot.web.app.entity.Foto;
import com.vilu.springboot.web.app.entity.Piso;


public interface IFotoDao  extends PagingAndSortingRepository<Foto, Long>{


	public List<Foto> findFotoByPisoOrderByIdAsc (Piso piso);
	
}
