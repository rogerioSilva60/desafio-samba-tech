package br.com.desafio.sambatech.infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.desafio.sambatech.core.storage.StorageProperties;
import br.com.desafio.sambatech.domain.service.MediaStorageService;
import br.com.desafio.sambatech.domain.util.exception.StorageException;

public class LocalMediaStorageService implements MediaStorageService {

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaMedia novaMedia) {
		try {
			Path arquivoPath = prepararPath(novaMedia.getNome());
			novaMedia.transferirPara(arquivoPath);
		} catch (IOException e) {
			throw new StorageException("Não foi possível armazenar arquivo", e);
		}
	}

	@Override
	public void deletar(String nomeArquivo) {
		try {
			Path path = prepararPath(nomeArquivo);
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new StorageException("Não foi possível excluir arquivo", e);
		}
	}

	@Override
	public MediaRecuperada recuperar(String nomeArquivo) {
		try {
			Path path = prepararPath(nomeArquivo);
	        return MediaRecuperada.builder().inputStream(Files.newInputStream(path)).url(path.toString()).build();
	    } catch (Exception e) {
	        throw new StorageException("Não foi possível recuperar arquivo.", e);
	    }
	}
	
	private Path prepararPath(String nomeArquivo) {
		Path path = storageProperties.getLocal().getDiretorioMedia().resolve(Path.of(nomeArquivo));
		return path;
	}

}
