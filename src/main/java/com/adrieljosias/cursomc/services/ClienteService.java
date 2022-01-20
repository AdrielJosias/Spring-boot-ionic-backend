package com.adrieljosias.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrieljosias.cursomc.domain.Cliente;
import com.adrieljosias.cursomc.repositories.ClienteRepository;
import com.adrieljosias.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	//essa classe esta acessando a classe CategoriaResource que é Objeto acessa aos dados
	@Autowired   //Intancia automaticamente
	private ClienteRepository repo;
	
	//operacao que busca uma categoria por codigo
	public Cliente find(Integer id) {
		 Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
}
