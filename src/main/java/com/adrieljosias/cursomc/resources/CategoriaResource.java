package com.adrieljosias.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adrieljosias.cursomc.domain.Categoria;
import com.adrieljosias.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	//Rest esta acessando a classe CategoriaService
	@Autowired //Intancia automaticamente
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)//recebe tambem o id
	public ResponseEntity<?> find(@PathVariable Integer id) { //PathVariable associa o id da url com a variavel
		Categoria obj = service.buscar(id);					//RespoceEntity capsula varias informaçoes http para rest
		return ResponseEntity.ok().body(obj);
	}	
	
}
