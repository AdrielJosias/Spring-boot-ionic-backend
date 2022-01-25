//Classe que manipula erros de http "excessoes"
package com.adrieljosias.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adrieljosias.cursomc.services.exceptions.DataIntegrityException;
import com.adrieljosias.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice //Metódos padroes do controlleradvices
public class ResouceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)//Tratador de excessoes
	public ResponseEntity<StandarError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		
		//Instanciar classe StandarError
		StandarError err = new StandarError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	//criando codigo de erro personalizado, stander error
	@ExceptionHandler(DataIntegrityException.class)//Tratador de excessoes
	public ResponseEntity<StandarError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
		
		//Instanciar classe StandarError
		StandarError err = new StandarError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	
	//criando codigo de erro personalizado, stander error
		@ExceptionHandler(MethodArgumentNotValidException.class)//Tratador de excessoes
		public ResponseEntity<StandarError> Validation(MethodArgumentNotValidException e, HttpServletRequest request) {
			
			//Instanciar classe ValidationError
			ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
			for (FieldError x : e.getBindingResult().getFieldErrors()) {
				err.addError(x.getField(), x.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		}
}
