package br.com.desafio.sambatech.domain.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.util.FileCopyUtils;

import lombok.Builder;
import lombok.Getter;

public interface MediaStorageService {

	void salvar(NovaMedia novaMedia);
	
	@Builder
	@Getter
	class NovaMedia {
		private String nome;
		private InputStream inputStream;
		
		public void transferirPara(Path path) throws IOException {
			FileCopyUtils.copy(this.inputStream, Files.newOutputStream(path));
		}
	}
}
