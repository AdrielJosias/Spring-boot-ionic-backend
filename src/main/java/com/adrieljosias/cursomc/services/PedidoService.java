package com.adrieljosias.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrieljosias.cursomc.domain.Pedido;
import com.adrieljosias.cursomc.repositories.PedidoRepository;
import com.adrieljosias.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	//essa classe esta acessando a classe PedidoResource que é Objeto acessa aos dados
	@Autowired   //Intancia automaticamente
	private PedidoRepository repo;
	
	//operacao que busca uma categoria por codigo
	public Pedido buscar(Integer id) {
		 Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
}
