package com.adrieljosias.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adrieljosias.cursomc.domain.Categoria;
import com.adrieljosias.cursomc.domain.Cidade;
import com.adrieljosias.cursomc.domain.Cliente;
import com.adrieljosias.cursomc.domain.Endereco;
import com.adrieljosias.cursomc.domain.Estado;
import com.adrieljosias.cursomc.domain.Produto;
import com.adrieljosias.cursomc.domain.enums.TipoCliente;
import com.adrieljosias.cursomc.repositories.CategoriaRepository;
import com.adrieljosias.cursomc.repositories.CidadeRepository;
import com.adrieljosias.cursomc.repositories.ClienteRepository;
import com.adrieljosias.cursomc.repositories.EnderecoRepository;
import com.adrieljosias.cursomc.repositories.EstadoRepository;
import com.adrieljosias.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CusomcApplication implements CommandLineRunner{
    
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
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
		
		//Instanciar os estados
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
			
		//Instanciar as cidades
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
			
		//Associar as cidades aos estados
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		//instanciar os Clientes e endereços
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);				
		
		cli1.getTelefones().addAll(Arrays.asList("27963323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834",cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "98777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	}

}
