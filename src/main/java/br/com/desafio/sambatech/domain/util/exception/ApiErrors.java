package br.com.desafio.sambatech.domain.util.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

public class ApiErrors {

	private List <String> errors;
	
	public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<String>();
        bindingResult.getAllErrors().forEach(error -> this.errors.add(error.getDefaultMessage()));
    }
	
	public ApiErrors(NaoEncontradoException ex) {
		this.errors = Arrays.asList(ex.getMessage());
	}

	public ApiErrors(StorageException ex) {
		this.errors = Arrays.asList(ex.getMessage());
	}

	public ApiErrors(IOException ex) {
		this.errors = Arrays.asList(ex.getMessage());
	}

	public ApiErrors(MaxUploadSizeExceededException ex) {
		this.errors = Arrays.asList(ex.getMessage());
	}

	public List<String> getErrors() {
        return errors;
    }
}

