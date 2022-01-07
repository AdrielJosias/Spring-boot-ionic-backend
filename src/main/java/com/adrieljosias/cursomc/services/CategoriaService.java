package com.adrieljosias.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrieljosias.cursomc.domain.Categoria;
import com.adrieljosias.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	//essa classe esta acessando a classe CategoriaResource que é Objeto acessa aos dados
	@Autowired   //Intancia automaticamente
	private CategoriaRepository repo;
	
	//operacao que busca uma categoria por codigo
	public Categoria buscar(Integer id) {
		 Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null); 
	}
	
}
