package com.adrieljosias.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adrieljosias.cursomc.domain.Pedido;
import com.adrieljosias.cursomc.services.PedidoService;

@RestController 
@RequestMapping(value="/pedidos")
public class PedidoResource {

	//Rest esta acessando a classe PedidoService
	@Autowired //Intancia automaticamente
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)//recebe tambem o id
	public ResponseEntity<Pedido> find(@PathVariable Integer id) { //PathVariable associa o id da url com a variavel
		Pedido obj = service.find(id);					//RespoceEntity capsula varias informaçoes http para rest
		return ResponseEntity.ok().body(obj);
	}	
	
	@RequestMapping(method=RequestMethod.POST)//Incerir nova categoria no banco de dados
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) { //resposta http vazia
		obj = service.insert(obj); //converter objDTO para um obj Entity
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();//pega a URI no novo recurso que foi incerido e inclui o ID da nova categoria incerida
		return ResponseEntity.created(uri).build();//gerar o codigo 201 automaticamente, atribuindo o uri 
	}
	
}
