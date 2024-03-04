package com.vilu.springboot.web.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.vilu.springboot.web.app.pojo.ItemReserva;

@Component
public class LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler{

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		 FlashMap flashMap = new FlashMap();
		
		 flashMap.put("success", "Hola "+authentication.getName() +" has iniciado sesión con exito!");
		
		 flashMapManager.saveOutputFlashMap(flashMap, request, response);
		 
		 
		 //verificamos si el usuario intento hacer una reserva durante la sesion y redirigimos a la pagina
		 HttpSession session = request.getSession();
		 if(session.getAttribute("reservaPendiente")!=null) {
			 ItemReserva itemReserva = (ItemReserva) session.getAttribute("reservaPendiente");
			 response.sendRedirect("/reservas/listar/"+itemReserva.getIdPiso());
		 }else {
			 response.sendRedirect("/index");
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
	
}
