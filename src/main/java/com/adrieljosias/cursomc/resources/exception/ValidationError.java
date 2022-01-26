//classe que acrescenta a lista de erro que irra aparecer 
package com.adrieljosias.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

//subclasse de standarerror
public class ValidationError extends StandarError {
	private static final long serialVersionUID = 1L;
	
	//lista da classe fieldmesage que criamos
	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldMessage> getErrors() { //get(errors) essa palavra que ira aparecer no Json do POSTMAN
		return errors;
	}
	
	//adiciona um erro por vez
	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}
