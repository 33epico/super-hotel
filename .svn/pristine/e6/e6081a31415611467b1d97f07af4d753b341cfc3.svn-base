package com.vilu.springboot.web.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.vilu.springboot.web.app.entity.Foto;
import com.vilu.springboot.web.app.entity.Peticiones;
import com.vilu.springboot.web.app.entity.Piso;
import com.vilu.springboot.web.app.entity.Promociones;
import com.vilu.springboot.web.app.entity.Recibos;
import com.vilu.springboot.web.app.entity.Reserva;
import com.vilu.springboot.web.app.entity.Role;
import com.vilu.springboot.web.app.entity.Servicios;
import com.vilu.springboot.web.app.entity.Tarifa;
import com.vilu.springboot.web.app.entity.UsrPerfil;
import com.vilu.springboot.web.app.entity.Usuario;


public interface IPisoService {
	
	public List<Piso> findAll();
	
	public void save(Piso piso);

	public Piso findOne(Long id);
	
	public void delete(Long id);
	
	public Page<Piso> findAll(Pageable pageable);

	public Page<Piso> findByActivo(Boolean term,Pageable pageable);
	
	public List<Piso> findByActivo(Boolean term);
	
	public Page<Piso> findByUsuario (Usuario usuario, Pageable pageable);
	
	public Page<Piso> findByUsuarioAndActivo (Usuario usuario,Boolean term, Pageable pageable);
	
	//Servicios para los servicios del piso
	
	public List<Servicios> findServiciosByPisoOrderByIdAsc(Piso piso);
	
	public Servicios findOneServicios(Long id);
	
	public void saveServicios(Servicios servicios);
	
	public void deleteServicios(Long id);
	
	//Servicios para la galeria
	public void saveFoto(Foto foto);
	
	public void deleteFoto(Long id);
	
	public Foto findOneFoto(Long id);
	
	public List<Foto> findAllFoto();
	
	public List<Foto> findFotoByPisoOrderByIdAsc(Piso piso);
	
	//Servicios para la tarifa
	public void saveTarifa(Tarifa tarifa);
		
	public Tarifa findOneTarifa(Long id);
		
	public List<Tarifa> findAllTarifa();
	
	public List <Tarifa> findByPisoOrderByFechaInicioDesc(Piso piso);
	
	
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
	
	//recupera el usuario por la key de recuperacion
	public Usuario findByKeynopwd(String Keynopwd);
	
	//Servicios para reservas
	public void saveReserva(Reserva reserva);
	
	public Reserva findReservaBycodigoReserva(String codigoReserva);

	public List<Reserva> findReservaByPiso (Piso piso);
	
	public List<Reserva> findReservaByActivaAndConsolidada(boolean activa,long consolidada); 
	
	public List<Reserva> findReservaByPisoAndActivaAndConsolidada (Piso piso,boolean activa, long consolidada );
	
	public List<Reserva> findReservaByUsuario (Usuario usuario);
	
	//Promociones
	
	public void savePromociones(Promociones promociones);
	
	public Promociones findPromocionesById(Long id);
	
	public void deletePromociones(Promociones promociones);
	
	public Page<Promociones> findAllPromociones(Pageable pageable);
	
	public Promociones findPromocionesBycodigoPromocion(String codigoPromocion);
	
	//recibos
	public Recibos findRecibosBycodigo (String codigo);
	
	public void saveRecibos(Recibos recibos);

	//Peticiones
	public void savePeticiones (Peticiones peticion);
	
	
}
