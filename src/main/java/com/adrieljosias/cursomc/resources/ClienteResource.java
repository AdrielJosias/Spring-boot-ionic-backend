package com.adrieljosias.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adrieljosias.cursomc.domain.Cliente;
import com.adrieljosias.cursomc.dto.ClienteDTO;
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
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)//Atualizar algo 
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id ) { // é uma mistura do GET com o POST
		Cliente obj = service.fromDTO(objDto); //converter objDTO para um obj Entity
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();//retorna vazio
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)//deleta
	public ResponseEntity<Void> delete(@PathVariable Integer id) { //PathVariable associa o id da url com a variavel
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//mostrar todos Clientes
	@RequestMapping(method=RequestMethod.GET)//não busca por id
	public ResponseEntity<List<ClienteDTO>> findAll() { //Retorna metodo DTO
		List<Cliente> list = service.findAll();//busca as listas				
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());//Converter a lista para uma lista DTO, criando um construtor na classe DTO
				return ResponseEntity.ok().body(listDto);
	}	
	
	//Paginação
	@RequestMapping(value= "/page", method=RequestMethod.GET)//pagina um id
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, //se nao informar o parameto da pagina automaticamente ele vai para pagina 0
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, //geralmente 24 linhas por ser multiplo de 1, 2, 3 e 4
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, //se nao informado, buscar por nome
			@RequestParam(value="direction", defaultValue="ASC")String direction) { //ASC ascendente ou DESC descendente 
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);//busca as paginas				
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));//Converter para DTO
				return ResponseEntity.ok().body(listDto);
	}
}
