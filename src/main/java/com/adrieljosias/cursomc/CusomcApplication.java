package com.adrieljosias.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adrieljosias.cursomc.domain.Categoria;
import com.adrieljosias.cursomc.repositories.CategoriaRepository;

@SpringBootApplication
public class CusomcApplication implements CommandLineRunner{
    
	@Autowired
	private CategoriaRepository categoriarepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CusomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		/*Para acessar o banco de dados e salvar um obj (categoria) 
		 * temos que chamar a classe repository e instancia-la
		 * arrays.aslist necessario para mais de um obj (categoria)
		*/
		categoriarepository.saveAll(Arrays.asList(cat1, cat2));
		
		
	}

}
