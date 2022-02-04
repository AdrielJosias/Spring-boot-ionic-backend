package com.adrieljosias.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.adrieljosias.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
		
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());//destinatario do email
		sm.setFrom(sender); //email incluido no aplication.properties 
		sm.setSubject("Pedido confimado! Código: " + obj.getId()); //assunto do email
		sm.setSentDate(new Date(System.currentTimeMillis())); //data o email com o horario do meu servidor
		sm.setText(obj.toString());//corpo do email
		return sm;
	}

}
