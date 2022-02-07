package com.adrieljosias.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.adrieljosias.cursomc.domain.PagamentoComBoleto;
import com.adrieljosias.cursomc.domain.PagamentoComCartao;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {
// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
	
	@Bean
	public JavaMailSender jMS (){
		return new JavaMailSenderImpl();
	}
	
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoComCartao.class);//subclasses necessarias
				objectMapper.registerSubtypes(PagamentoComBoleto.class);//subclasses necessarias
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}
//codigo padrão