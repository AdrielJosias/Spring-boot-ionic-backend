package com.adrieljosias.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adrieljosias.cursomc.domain.Categoria;
import com.adrieljosias.cursomc.domain.Produto;
import com.adrieljosias.cursomc.repositories.CategoriaRepository;
import com.adrieljosias.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CusomcApplication implements CommandLineRunner{
    
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CusomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		//Instânciar os produtos
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		//Associar as categorias aos produtos, addALL adiciona todos elementos na lista
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3)); //Arrays.asList coleção de elementos
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//Associar os produtos nas suas categorias
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		/*Para acessar o banco de dados e salvar um obj (categoria) 
		 * temos que chamar a classe repository e instancia-la
		 * arrays.aslist necessario para mais de um obj (categoria)
		*/
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		
	}

}
