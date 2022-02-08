package com.adrieljosias.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private Environment env;
	
	//caminhos que por padrão vão estar liberados
	private static final String [] PUBLIC_MATCHERS = {
			"/h2-console/**"
	};
	
	//caminhos de leitura qe o usuarios podem recuperar os dados
		private static final String [] PUBLIC_MATCHERS_GET = {
				"/produtos/**",
				"/categorias/**",
				"/clientes/**"
		};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { //por padrão esse metodo pode mandar uma exceção

		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable(); //liberando acesso no banco de dados do h2
		}
		
		http.cors().and().csrf().disable();
		http.authorizeRequests().antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated(); //TODOS OS CAMINHOS QUE ESTIVER NESSE VETOR SERA PERMITIDO, E PARA TODO RESTO EXIGE AUTENICAÇÃO 
	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //assegurar que o backnd nao criar sessao de usuario
	}
	
	@Bean //permite acesso aos endpoints por multiplas fontes com configurizaçoes basicas
	CorsConfigurationSource corConfigurationSouce() {
		final UrlBasedCorsConfigurationSource souce = new UrlBasedCorsConfigurationSource();
		souce.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return souce;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() { //codifica senha do cliente
		return new BCryptPasswordEncoder();
	}
	
	
}
