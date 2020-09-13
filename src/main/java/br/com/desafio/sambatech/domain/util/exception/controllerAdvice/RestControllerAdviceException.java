package br.com.desafio.sambatech.domain.util.exception.controllerAdvice;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.desafio.sambatech.domain.util.exception.ApiErrors;
import br.com.desafio.sambatech.domain.util.exception.NaoEncontradoException;
import br.com.desafio.sambatech.domain.util.exception.StorageException;
import br.com.desafio.sambatech.domain.util.response.Response;


@RestControllerAdvice
public class RestControllerAdviceException {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		return ResponseEntity.badRequest().body(prepararRespostaException(new ApiErrors(bindingResult)));
	}
	
	@ExceptionHandler(BindException.class)
	public ResponseEntity<?> handleBindException(BindException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		return ResponseEntity.badRequest().body(prepararRespostaException(new ApiErrors(bindingResult)));
	}
	
	@ExceptionHandler(NaoEncontradoException.class)
	public ResponseEntity<?> handleNaoEncontradoException(NaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(prepararRespostaException(new ApiErrors(ex)));
	}
	
	@ExceptionHandler(StorageException.class)
	public ResponseEntity<?> handleStorageException(StorageException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(prepararRespostaException(new ApiErrors(ex)));
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<?> handleIOException(IOException ex) {
		return ResponseEntity.badRequest().body(prepararRespostaException(new ApiErrors(ex)));
	}
	
	private Response<?> prepararRespostaException(ApiErrors apiErrors) {
		Response<?> response = new Response<>();
		response.setErrors(apiErrors.getErrors());
		return response;
	}
}
