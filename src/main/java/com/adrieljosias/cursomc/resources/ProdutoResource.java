package com.adrieljosias.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adrieljosias.cursomc.domain.Produto;
import com.adrieljosias.cursomc.dto.ProdutoDTO;
import com.adrieljosias.cursomc.resources.utils.URL;
import com.adrieljosias.cursomc.services.ProdutoService;

@RestController 
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	//Rest esta acessando a classe ProdutoService
	@Autowired //Intancia automaticamente
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)//recebe tambem o id
	public ResponseEntity<Produto> find(@PathVariable Integer id) { //PathVariable associa o id da url com a variavel
		Produto obj = service.find(id);					//RespoceEntity capsula varias informaçoes http para rest
		return ResponseEntity.ok().body(obj);
	}	
	
	//Paginação
		@RequestMapping(method=RequestMethod.GET)//pagina um id
		public ResponseEntity<Page<ProdutoDTO>> findPage(//criar classe DTO de produto
				@RequestParam(value="nome", defaultValue="") String nome,
				@RequestParam(value="categorias", defaultValue="") String categorias,
				@RequestParam(value="page", defaultValue="0") Integer page, //se nao informar o parameto da pagina automaticamente ele vai para pagina 0
				@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, //geralmente 24 linhas por ser multiplo de 1, 2, 3 e 4
				@RequestParam(value="orderBy", defaultValue="nome")String orderBy, //se nao informado, buscar por nome
				@RequestParam(value="direction", defaultValue="ASC")String direction) { //ASC ascendente ou DESC descendente 
			String nomeDecoded = URL.decodeParam(nome);
			List<Integer> ids = URL.decodeIntList(categorias);//lista de numeros inteiros
			Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);//busca as paginas				
			Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));//Converter para DTO
					return ResponseEntity.ok().body(listDto);
		}
}
