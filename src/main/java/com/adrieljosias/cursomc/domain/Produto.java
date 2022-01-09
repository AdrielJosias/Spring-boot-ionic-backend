package com.adrieljosias.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity //Entidade do JPA para mapeamento obj relacional
public class Produto implements Serializable { //Implementação do Serializable
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Definindo a geração automatica dos id
	private Integer id;
	private String nome;
	private Double preco;
	
	@ManyToMany //Criação de tabela para ligação entre produto e categoria
	@JoinTable(name = "PRODUTO_CATEGORIA", //nome da tabela de ligação
		joinColumns = @JoinColumn(name = "Produto_id"), //chave estrangeira, nome do campo produto
		inverseJoinColumns = @JoinColumn(name = "Categoria_id") //chave estrangeira, nome do campo categoria
	)
	private List<Categoria> categorias = new ArrayList<>();
	
	public Produto () {
	}

	//criar construtor de todos atributos menos das coleções (categorias)
	public Produto(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	//Criação do HashCode e equals somente do ID, para comparar dois obj pelo conteudo
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}

	
	
}
