package br.com.desafio.sambatech.infrastructure;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.sambatech.core.storage.StorageProperties;
import br.com.desafio.sambatech.domain.service.MediaStorageService;
import br.com.desafio.sambatech.domain.util.exception.StorageException;

@Service
public class LocalMediaStorageService implements MediaStorageService {

	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void salvar(NovaMedia novaMedia) {
		try {
			Path arquivoPath = prepararPath(novaMedia.getNome());
			novaMedia.transferirPara(arquivoPath);
		} catch (IOException e) {
			throw new StorageException("Não foi possível armazenar arquivo", e);
		}
	}
	
	private Path prepararPath(String nomeArquivo) {
		Path path = storageProperties.getLocal().getDiretorioMedia().resolve(Path.of(nomeArquivo));
		return path;
	}
}
