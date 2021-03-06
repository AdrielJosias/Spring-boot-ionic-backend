//classe que acessa o banco de dados, faz consultas para acessar os dados da tabela categoria
package com.adrieljosias.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adrieljosias.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {//acessa os dados no tipo Categoria, e seu tipo é integer
	
	@Transactional(readOnly=true)//não necessita ser inserida no banco de daos, ocasionando mais velocidade
	Cliente findByEmail(String email);
}
