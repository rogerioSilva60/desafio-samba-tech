package br.com.desafio.sambatech.domain.util.exception;

public class NaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NaoEncontradoException() {
		super("Nao encontrado");
	}

	public NaoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}

	public NaoEncontradoException(String message) {
		super(message);
	}

	
}
