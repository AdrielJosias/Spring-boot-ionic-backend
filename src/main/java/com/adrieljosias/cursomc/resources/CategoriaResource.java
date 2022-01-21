package com.adrieljosias.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adrieljosias.cursomc.domain.Categoria;
import com.adrieljosias.cursomc.dto.CategoriaDTO;
import com.adrieljosias.cursomc.services.CategoriaService;

@RestController 
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	//Rest esta acessando a classe CategoriaService
	@Autowired //Intancia automaticamente
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)//recebe tambem o id
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { //PathVariable associa o id da url com a variavel
		Categoria obj = service.find(id);					//RespoceEntity capsula varias informaçoes http para rest
		return ResponseEntity.ok().body(obj);
	}	
	
	@RequestMapping(method=RequestMethod.POST)//Incerir nova categoria no banco de dados
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) { //resposta http vazia
		obj = service.insert(obj); //chamar serviço que inclui a nova categoria no banco de dados
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();//pega a URI no novo recurso que foi incerido e inclui o ID da nova categoria incerida
		return ResponseEntity.created(uri).build();//gerar o codigo 201 automaticamente, atribuindo o uri 
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)//Atualizar algo 
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id ) { // é uma mistura do GET com o POST
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();//retorna vazio
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)//deleta
	public ResponseEntity<Void> delete(@PathVariable Integer id) { //PathVariable associa o id da url com a variavel
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//mostrar todas categorias
	@RequestMapping(method=RequestMethod.GET)//não busca por id
	public ResponseEntity<List<CategoriaDTO>> findAll() { //Retorna metodo DTO
		List<Categoria> list = service.findAll();//busca as listas				
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());//Converter a lista para uma lista DTO, criando um construtor na classe DTO
				return ResponseEntity.ok().body(listDto);
	}	
	
	//Paginação
	@RequestMapping(value= "/page", method=RequestMethod.GET)//pagina um id
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, //se nao informar o parameto da pagina automaticamente ele vai para pagina 0
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, //geralmente 24 linhas por ser multiplo de 1, 2, 3 e 4
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, //se nao informado, buscar por nome
			@RequestParam(value="direction", defaultValue="ASC")String direction) { //ASC ascendente ou DESC descendente 
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);//busca as paginas				
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));//Converter para DTO
				return ResponseEntity.ok().body(listDto);
	}
}
