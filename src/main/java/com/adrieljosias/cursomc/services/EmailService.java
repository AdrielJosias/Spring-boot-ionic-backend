package com.adrieljosias.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.adrieljosias.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	
}
