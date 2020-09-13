package br.com.desafio.sambatech.domain.util.exception.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.desafio.sambatech.domain.util.exception.ApiErrors;
import br.com.desafio.sambatech.domain.util.exception.NaoEncontradoException;
import br.com.desafio.sambatech.domain.util.response.Response;


@RestControllerAdvice
public class ControllerAdviceException {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		return ResponseEntity.badRequest().body(prepararRespostaException(new ApiErrors(bindingResult)));
	}
	
	@ExceptionHandler(NaoEncontradoException.class)
	public ResponseEntity<?> handleNaoEncontradoException(NaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(prepararRespostaException(new ApiErrors(ex)));
	}
	
	private Response<?> prepararRespostaException(ApiErrors apiErrors) {
		Response<?> response = new Response<>();
		response.setErrors(apiErrors.getErrors());
		return response;
	}
}
