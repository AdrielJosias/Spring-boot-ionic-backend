package com.adrieljosias.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.adrieljosias.cursomc.domain.Cliente;
import com.adrieljosias.cursomc.dto.ClienteDTO;
import com.adrieljosias.cursomc.repositories.ClienteRepository;
import com.adrieljosias.cursomc.resources.exception.FieldMessage;
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;//permite ter o parameto da ID
	@Override
	public void initialize(ClienteUpdate ann) {
	}
	@Autowired
	private ClienteRepository repo;
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
										
		@SuppressWarnings("unchecked")//estrutura da dedaos, chave e valor ex:clientes/2, os atributos sao armazenanod em um MAP por isso usar esse MAP
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id")); //pegar o Id da requisição
		
		List<FieldMessage> list = new ArrayList<>();

  
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) { //nao permite a repetição de email ja cadastrado
			list.add(new FieldMessage("email", "Email já existente"));
		}
		// inclua os testes aqui, inserindo erros na lista

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
 	}
}


