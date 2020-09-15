package br.com.desafio.sambatech.domain.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.util.FileCopyUtils;

import lombok.Builder;
import lombok.Getter;

public interface MediaStorageService {

	void armazenar(NovaMedia novaMedia);
	
	void deletar(String nomeArquivo);
	
	MediaRecuperada recuperar(String nomeArquivo);
	
	default void substituir(String nomeArquivoAntigo, NovaMedia novaMedia) {
		if(nomeArquivoAntigo != null) {
			this.deletar(nomeArquivoAntigo);
		}
		
		this.armazenar(novaMedia);
	} 
	
	@Builder
	@Getter
	class NovaMedia {
		private String nome;
		private InputStream inputStream;
		private String contentType;
		
		public void transferirPara(Path path) throws IOException {
			FileCopyUtils.copy(this.inputStream, Files.newOutputStream(path));
		}
	}
	
	@Builder
	@Getter
	class MediaRecuperada {
		private InputStream inputStream;
		private String url;
		
		public boolean temUrl() {
			return this.url != null;
		}
		
		public boolean temInputStream() {
			return this.inputStream != null;
		}
	}
}
