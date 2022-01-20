package com.adrieljosias.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adrieljosias.cursomc.domain.Cliente;
import com.adrieljosias.cursomc.services.ClienteService;

@RestController 
@RequestMapping(value = "/clientes")
public class ClienteResource {

	//Rest esta acessando a classe ClienteService
	@Autowired //Intancia automaticamente
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)//recebe tambem o id
	public ResponseEntity<Cliente> find(@PathVariable Integer id) { //PathVariable associa o id da url com a variavel
		Cliente obj = service.find(id);					//RespoceEntity capsula varias informaçoes http para rest
		return ResponseEntity.ok().body(obj);
	}	
	
}
