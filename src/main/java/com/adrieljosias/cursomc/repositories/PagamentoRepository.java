//classe que acessa o banco de dados, faz consultas para acessar os dados da tabela categoria
package com.adrieljosias.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrieljosias.cursomc.domain.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
			//acessa os dados no tipo Pagamento, e seu tipo é integer
}
