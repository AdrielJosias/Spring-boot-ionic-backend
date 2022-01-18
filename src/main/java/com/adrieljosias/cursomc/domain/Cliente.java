package com.adrieljosias.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.adrieljosias.cursomc.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String email;
	private String cpfOuCnpj;
	//Armazenar um numero inteiro ao inves do "TipoCliente"	internamente, porem para o mundo externo vai ser um dado TipoCliente(private TipoCliente tipo;)
	private Integer tipo;
	
		@OneToMany(mappedBy = "cliente") //um pra muitos
		private List<Endereco> enderecos = new ArrayList<>();
	
		@ElementCollection //Mapeamento
		@CollectionTable(name = "TELEFONE")
		private Set<String> telefones = new HashSet<>();//coleção de strings associada ao clinte, Set nao aceita repetição
	
		public Cliente() {
		super();
	}

		public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
			this.id = id;
			this.nome = nome;
			this.email = email;
			this.cpfOuCnpj = cpfOuCnpj;
			this.tipo = tipo.getCod();//Colocar getcod()
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

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getCpfOuCnpj() {
			return cpfOuCnpj;
		}

		public void setCpfOuCnpj(String cpfOuCnpj) {
			this.cpfOuCnpj = cpfOuCnpj;
		}

		public TipoCliente getTipo() {
			return TipoCliente.toEnum(tipo);//chamar metodo statico da classe TIPOCLIENTE, foi armazenado internamente um numero inteiro controlado por nós, mas a classe Cliente ela expoe para o sistema o dado TipoCliente
		}

		public void setTipo(TipoCliente tipo) {
			this.tipo = tipo.getCod();//Tambem alterar com o getcod()
		}

		public List<Endereco> getEnderecos() {
			return enderecos;
		}

		public void setEnderecos(List<Endereco> enderecos) {
			this.enderecos = enderecos;
		}

		public Set<String> getTelefones() {
			return telefones;
		}

		public void setTelefones(Set<String> telefones) {
			this.telefones = telefones;
		}

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
			Cliente other = (Cliente) obj;
			return Objects.equals(id, other.id);
		}
			
}
