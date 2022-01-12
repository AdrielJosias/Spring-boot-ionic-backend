//Classe que manipula erros de http "excessoes"
package com.adrieljosias.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adrieljosias.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice //Metódos padroes do controlleradvices
public class ResouceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)//Tratador de excessoes
	public ResponseEntity<StandarError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		
		//Instanciar classe StandarError
		StandarError err = new StandarError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
