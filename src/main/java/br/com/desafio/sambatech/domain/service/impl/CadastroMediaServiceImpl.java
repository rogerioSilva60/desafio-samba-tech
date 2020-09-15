package br.com.desafio.sambatech.domain.service.impl;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.desafio.sambatech.domain.entity.Media;
import br.com.desafio.sambatech.domain.repository.MediaRepository;
import br.com.desafio.sambatech.domain.service.CadastroMediaService;
import br.com.desafio.sambatech.domain.service.MediaStorageService;
import br.com.desafio.sambatech.domain.service.MediaStorageService.MediaRecuperada;
import br.com.desafio.sambatech.domain.service.MediaStorageService.NovaMedia;
import br.com.desafio.sambatech.domain.util.exception.NaoEncontradoException;

@Service
public class CadastroMediaServiceImpl implements CadastroMediaService {

	private MediaRepository repository;
	private MediaStorageService mediaStorageService;

	public CadastroMediaServiceImpl(MediaRepository repository, MediaStorageService mediaStorageService) {
		this.repository = repository;
		this.mediaStorageService = mediaStorageService;
	}

	@Override
	public Media salvar(Media media, InputStream inputStream) {
		Media mediaSalva = repository.save(media);
		repository.flush();
		
		NovaMedia novaMedia = NovaMedia.builder()
				.nome(media.getNome())
				.inputStream(inputStream)
				.contentType(media.getContentType())
				.build();
		mediaStorageService.armazenar(novaMedia);
		adicionarUrlDoStorage(mediaSalva, novaMedia);
		
		return mediaSalva;
	}

	@Override
	public List<Media> listar(boolean ehTodas) {
		List<Media> lista = ehTodas ? repository.findAll() : repository.findByDeletado(ehTodas);		 
		return lista;
	}

	@Override
	public Media buscar(Long id) {
		Media media = repository.findById(id)
			.orElseThrow(() -> new NaoEncontradoException("Media não encontrada"));
		return media;
	}
	
	@Override
	public Media atualizarDeletado(Long id) {
		Media media = buscar(id);
		media.setDeletado(!media.isDeletado());
		Media mediaAtualizada = repository.save(media);
		return mediaAtualizada;
	}

	@Override
	public Media atualizar(Media media, Long id, InputStream inputStream) {
		Media mediaDoBanco = buscar(id);
		String nomeArquivoAnterior = mediaDoBanco.getNome();
		media.setId(mediaDoBanco.getId());
		media.setDataUpload(mediaDoBanco.getDataUpload());
		media.setDeletado(mediaDoBanco.isDeletado());
		
		Media mediaAtualizada = repository.save(media);
		repository.flush();
		
		NovaMedia novaMedia = NovaMedia.builder()
				.nome(media.getNome())
				.inputStream(inputStream)
				.contentType(media.getContentType())
				.build();
		mediaStorageService.substituir(nomeArquivoAnterior, novaMedia);
		
		adicionarUrlDoStorage(mediaAtualizada, novaMedia);
		return mediaAtualizada;
	}

	@Override
	public void deletar(Long id) {
		Media mediaDoBanco = buscar(id);
		repository.deleteById(id);
		repository.flush();
		
		mediaStorageService.deletar(mediaDoBanco.getNome());
	}
	
	private void adicionarUrlDoStorage(Media mediaSalva, NovaMedia novaMedia) {
		MediaRecuperada recuperar = mediaStorageService.recuperar(novaMedia.getNome());
		mediaSalva.setUrl(recuperar.getUrl());
		repository.save(mediaSalva);
		repository.flush();
	}
}
