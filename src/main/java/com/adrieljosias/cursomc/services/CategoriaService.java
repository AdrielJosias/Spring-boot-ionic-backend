package com.adrieljosias.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adrieljosias.cursomc.domain.Categoria;
import com.adrieljosias.cursomc.dto.CategoriaDTO;
import com.adrieljosias.cursomc.repositories.CategoriaRepository;
import com.adrieljosias.cursomc.services.exceptions.DataIntegrityException;
import com.adrieljosias.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	//essa classe esta acessando a classe CategoriaResource que é Objeto acessa aos dados
	@Autowired   //Intancia automaticamente
	private CategoriaRepository repo;
	
	//operacao que busca uma categoria por codigo
	public Categoria find(Integer id) {
		 Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	//metodo de incerir repositorio da nova categoria
	public Categoria insert(Categoria obj) { 
		obj.setId(null); //reforça que será incerido um obj novo
		return repo.save(obj); 
		}
		
	//metodo de atualizar o repositorio
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());//Instancia um Categoria apartir do banco de dados, Verifica se o ID é existente, ou se deverá mandar uma eceção
		updateData(newObj, obj);//metodo auxiliar para atualizar o newobj com base no obj que veio com argumento
		return repo.save(newObj);//salva o newobj atuaizado
	}
	
		//Deleta algo
		public void delete(Integer id) {
			find(id);//verifica se o id existe
			try { //tenta deletar um id, se o id tem atribuos interligados com outras classe ele lança o catch
			repo.deleteById(id);
			}
			catch (DataIntegrityViolationException e) { //mensagem de erro ao tentar excluir uma categoria que possui produtos
				throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");//receber na camada de resouce
			}
	}
		//buscar todas categoria
		public List<Categoria> findAll() {
			return repo.findAll();
		}
		
		//retorna 1 pagina por vez
		public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
					orderBy);// obj que prepara as informações para fazer a consulta que retorna a pagina de dados
			return repo.findAll(pageRequest);//findall considera o pagerequest como argumento e retorna a pagina
		}
		
		//instancia uma categoria apartir de um DTO
		public Categoria fromDTO(CategoriaDTO objDto) {
			return new Categoria(objDto.getId(), objDto.getNome());
		}
		
		private void updateData(Categoria newObj, Categoria obj) { //atualiza o NewObj com os dados que veio no Obj
			newObj.setNome(obj.getNome()); //manda o get nome para o set nome do newObj
		}
}
