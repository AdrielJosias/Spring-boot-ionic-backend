//Classe auxiliar para carregar os dados para mensagem de erro ao nao inserir corretamento o campo nome de uma categoria
package com.adrieljosias.cursomc.resources.exception;

import java.io.Serializable;

public class FieldMessage  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String message;
	
	public FieldMessage() {
	}

	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fielsName) {
		this.fieldName = fielsName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
