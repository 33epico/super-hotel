package com.vilu.springboot.web.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vilu.springboot.web.app.auth.handler.LoginSuccesHandler;
import com.vilu.springboot.web.app.service.JpaUserDetailsService;



//este se usa para que funcionen las anotaciones en las otras clases
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private LoginSuccesHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	@Autowired
	private JpaUserDetailsService UserDetailsService;
	
	
	protected void configure(HttpSecurity http) throws Exception {
			
		//Añadimos las url que tiene acceso todo el mundo
		http
		.authorizeRequests()
		.antMatchers(
				"/publico/**"
				,"/bootstrap-4.5.2-dist/**"
				,"/"
				,"/pay"
				,"/success"
				,"/mensajes"
				,"/cancel"
				,"/locale"
				,"/reservas/**"
				,"/index"
				,"/registro/**"
				,"/uploads/**"
				,"/css/**"
				,"/js/**"
				,"/images/**"
				,"/json/**"
				,"/hotel-datepicker/**"
				,"/vendor/**"
				,"/fecha-master/**"
				,"/recuperarpw/**"
				,"/reestablecerpw/**"
				).permitAll()
		.anyRequest().authenticated()
		.and()
			.formLogin()
				.successHandler(successHandler)
					.loginPage("/login")
			.permitAll()
		.and()
		.logout().permitAll()
		.and().exceptionHandling().accessDeniedPage("/error_403");
		
		// .antMatchers("/form/**").hasAnyRole("ADMIN")
		//.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		//.antMatchers("/factura/**").hasAnyRole("ADMIN")
	}

	
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception{
		
		build.userDetailsService(UserDetailsService)
		.passwordEncoder(passwordEncoder);
		
	}
	
}
