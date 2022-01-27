package com.adrieljosias.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adrieljosias.cursomc.domain.Cidade;
import com.adrieljosias.cursomc.domain.Cliente;
import com.adrieljosias.cursomc.domain.Endereco;
import com.adrieljosias.cursomc.domain.enums.TipoCliente;
import com.adrieljosias.cursomc.dto.ClienteDTO;
import com.adrieljosias.cursomc.dto.ClienteNewDTO;
import com.adrieljosias.cursomc.repositories.ClienteRepository;
import com.adrieljosias.cursomc.repositories.EnderecoRepository;
import com.adrieljosias.cursomc.services.exceptions.DataIntegrityException;
import com.adrieljosias.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	//essa classe esta acessando a classe ClienteResource que é Objeto acessa aos dados
	@Autowired   //Intancia automaticamente
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	
	//operacao que busca uma Cliente por codigo
	public Cliente find(Integer id) {
		 Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
		@Transactional//metodo de incerir repositorio de nova Cliente
		public Cliente insert(Cliente obj) { 
			obj.setId(null); //reforça que será incerido um obj novo
			obj = repo.save(obj);
			enderecoRepository.saveAll(obj.getEnderecos());
			return obj;
			}
			
			//metodo de atualizar o repositorio
			public Cliente update(Cliente obj) {
				Cliente newObj = find(obj.getId());//Instancia um Cliente apartir do banco de dados, Verifica se o ID é existente, ou se deverá mandar uma eceção
				updateData(newObj, obj);//metodo auxiliar para atualizar o newobj com base no obj que veio com argumento
				return repo.save(newObj);//salva o newobj atuaizado
			}
		
			//Deleta algo
			public void delete(Integer id) {
				find(id);//verifica se o id existe
				try { //tenta deletar um id, se o id tem atribuos interligados com outras classe ele lança o catch
				repo.deleteById(id);
				}
				catch (DataIntegrityViolationException e) { //mensagem de erro ao tentar excluir uma Cliente que possui pedidos
					throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas ");//receber na camada de resouce
				}
		}
			//buscar todas Cliente
			public List<Cliente> findAll() {
				return repo.findAll();
			}
			
			//retorna 1 pagina por vez
			public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
				PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
						orderBy);// obj que prepara as informações para fazer a consulta que retorna a pagina de dados
				return repo.findAll(pageRequest);//findall considera o pagerequest como argumento e retorna a pagina
			}
			
			//instancia uma Cliente apartir de um DTO
			public Cliente fromDTO(ClienteDTO objDto) {
				return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
			}
			
			//instancia uma Cliente apartir de um DTO
			public Cliente fromDTO(ClienteNewDTO objDto) {
				Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
				Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
				Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
				cli.getEnderecos().add(end);
				cli.getTelefones().add(objDto.getTelefone1());
				if (objDto.getTelefone2()!= null) {
					cli.getTelefones().add(objDto.getTelefone2());
				}
				if (objDto.getTelefone3()!= null) {
					cli.getTelefones().add(objDto.getTelefone3());
				}
				return cli;
			}
			
			private void updateData(Cliente newObj, Cliente obj) { //atualiza o NewObj com os dados que veio no Obj
				newObj.setNome(obj.getNome()); //manda o get nome para o set nome do newObj
				newObj.setEmail(obj.getEmail());//manda o get email para o set email do newObj
			}
}
