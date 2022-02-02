package com.adrieljosias.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adrieljosias.cursomc.domain.Categoria;
import com.adrieljosias.cursomc.domain.Produto;
import com.adrieljosias.cursomc.repositories.CategoriaRepository;
import com.adrieljosias.cursomc.repositories.ProdutoRepository;
import com.adrieljosias.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	//essa classe esta acessando a classe ProdutoResource que é Objeto acessa aos dados
	@Autowired   //Intancia automaticamente
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	//operacao que busca uma categoria por codigo
	public Produto find(Integer id) {
		 Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	//busca paginada, 
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);// obj que prepara as informações para fazer a consulta que retorna a pagina de dados){
		List<Categoria> categorias = categoriaRepository.findAllById(ids);//instaciar um repositorio de categorias na classe para gerar a lista de categoria
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
