package com.vilu.springboot.web.app.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vilu.springboot.web.app.dao.IFotoDao;
import com.vilu.springboot.web.app.dao.IPerfilUsrDao;
import com.vilu.springboot.web.app.dao.IPeticionesDao;
import com.vilu.springboot.web.app.dao.IPisoDao;
import com.vilu.springboot.web.app.dao.IPromocionesDao;
import com.vilu.springboot.web.app.dao.IRecibosDao;
import com.vilu.springboot.web.app.dao.IReservaDao;
import com.vilu.springboot.web.app.dao.IRoleDao;
import com.vilu.springboot.web.app.dao.IServiciosDao;
import com.vilu.springboot.web.app.dao.ITarifaDao;
import com.vilu.springboot.web.app.dao.IUsuarioDao;
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
import com.vilu.springboot.web.app.service.IPisoService;

@Service
public class PisoServiceImpl implements IPisoService {

	
	@Autowired
	private IPisoDao pisoDao;
	
	@Autowired
	private IFotoDao fotoDao;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IPerfilUsrDao perfilUsrDao;
	
	@Autowired
	private ITarifaDao tarifaDao;
	
	@Autowired
	private IReservaDao reservaDao;
	
	@Autowired
	private IServiciosDao serviciosDao;
	
	@Autowired
	private IPromocionesDao promocionesDao;
	
	@Autowired
	private IRecibosDao recibosDao;
	
	@Autowired
	private IPeticionesDao peticionesDao;
	
	//Metodos para el piso
	@Transactional(readOnly = true)
	public List<Piso> findAll() {
		return (List<Piso>) pisoDao.findAll();
	}

	@Transactional
	public void save(Piso piso) {
		pisoDao.save(piso);
	}

	public Piso findOne(Long id) {
		return pisoDao.findById(id).orElse(null);
	}

	@Transactional
	public void delete(Long id) {
		pisoDao.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Page<Piso> findAll(Pageable pageable) {
		return pisoDao.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Page<Piso> findByActivo(Boolean term,Pageable pageable) {
		return pisoDao.findByActivo(term, pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<Piso> findByUsuario(Usuario usuario, Pageable pageable) {
		return pisoDao.findByUsuario(usuario, pageable);
	}
	
	
	@Transactional(readOnly = true)
	public List<Piso> findByActivo(Boolean term) {
		return pisoDao.findByActivo(term);
	}
	
	public Page<Piso> findByUsuarioAndActivo( Usuario usuario,Boolean term, Pageable pageable) {
		return pisoDao.findByUsuarioAndActivo(usuario,term, pageable);
	}
	//Metodos para los servicios del piso
	public List<Servicios> findServiciosByPisoOrderByIdAsc(Piso piso) {
		return serviciosDao.findServiciosByPisoOrderByIdAsc(piso);
	}
	
	@Transactional
	public void saveServicios(Servicios servicios) {
		 serviciosDao.save(servicios);
	}

	@Transactional
	public void deleteServicios(Long id) {
		serviciosDao.deleteById(id);	
	}

	//Metodos para foto
	
	@Transactional 
	public void saveFoto(Foto foto) {
		fotoDao.save(foto);
		
	}

	public Foto findOneFoto(Long id) {
		return fotoDao.findById(id).orElse(null);
	}
	
	@Transactional
	public void deleteFoto(Long id) {
		fotoDao.deleteById(id);
	}

	
	public Piso fetchByIdWithFotos (Long id) {
		return pisoDao.fetchByIdWithFotos(id);
	}

	public List<Foto> findAllFoto() {
		return (List<Foto>) fotoDao.findAll();
	}
	
	public List<Foto> findFotoByPisoOrderByIdAsc(Piso piso) {
		return fotoDao.findFotoByPisoOrderByIdAsc(piso);
	}

	//Usuarios y roles
	
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

	//Metodos para las tarifas
	public Tarifa findOneTarifa(Long id) {
		return tarifaDao.findById(id).orElse(null);
	}

	public List<Tarifa> findAllTarifa() {
		return (List<Tarifa>) tarifaDao.findAll();
	}	

	public void saveTarifa(Tarifa tarifa) {
		tarifaDao.save(tarifa);
	}

	public List<Tarifa> findByPisoOrderByFechaInicioDesc(Piso piso) {
        return tarifaDao.findByPisoOrderByFechaInicioDesc(piso);
	}

	//Metodos para las reservas
	@Transactional
	public void saveReserva(Reserva reserva) {
		reservaDao.save(reserva);
	}

	public Page<Reserva> findAllReserva(Pageable pageable) {
		return reservaDao.findAll(pageable);
	}
	
	public List<Reserva> findReservaByActivaAndConsolidada(boolean activa,long consolidada){
		return reservaDao.findReservaByActivaAndConsolidada(activa,consolidada);
	}

	public List<Reserva> findReservaByPiso (Piso piso) {
		return  reservaDao.findReservaByPiso(piso);
	}

	public List<Reserva> findReservaByUsuario(Usuario usuario) {
		return reservaDao.findReservaByUsuario(usuario);
	}

	public Reserva findReservaBycodigoReserva(String codigoReserva) {
		return reservaDao.findReservaBycodigoReserva(codigoReserva);
	}
	
	public List<Reserva> findReservaByPisoAndActivaAndConsolidada(Piso piso, boolean activa,long consolidada) {
		return reservaDao.findReservaByPisoAndActivaAndConsolidada(piso, activa, consolidada);
	}
	
	//
	public Usuario findUsuarioById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}
	
	@Transactional
	public void deleteRole(Long id) {
		roleDao.deleteById(id);
	}

	public Servicios findOneServicios(Long id) {
		return serviciosDao.findById(id).orElse(null);
	}

	public void savePromociones(Promociones promociones) {
		promocionesDao.save(promociones);	
	}

	public void deletePromociones(Promociones promociones) {
		promocionesDao.delete(promociones);
	}

	public Page<Promociones> findAllPromociones(Pageable pageable) {
		return promocionesDao.findAll(pageable);
	}

	public Promociones findPromocionesById(Long id) {
		return promocionesDao.findById(id).orElse(null);
	}
	
	public Promociones findPromocionesBycodigoPromocion(String codigoPromocion) {
		return promocionesDao.findPromocionesBycodigoPromocion(codigoPromocion);
	}

	//Metodos recibos
	public Recibos findRecibosBycodigo(String codigo) {
		return recibosDao.findRecibosBycodigo(codigo);
	}

	public void saveRecibos(Recibos recibos) {
		recibosDao.save(recibos);
	}

	//metodos peticiones
	public void savePeticiones(Peticiones peticion) {
		peticionesDao.save(peticion);
	}

	
}
