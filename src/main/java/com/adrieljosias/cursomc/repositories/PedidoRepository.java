//classe que acessa o banco de dados, faz consultas para acessar os dados da tabela categoria
package com.adrieljosias.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrieljosias.cursomc.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
			//acessa os dados no tipo Pedido, e seu tipo é integer
}
